package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.*;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.service.*;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class AppointmentController {

    @Autowired
    private BillService billService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private TimeSlotService timeSlotService;
    @Autowired
    private RepairShopService repairShopService;
    @Autowired
    private PersonService personService;

    // todo: delete later
    @GetMapping(value = {"/"})
    public String sayHello(){return "hello world";}


    @GetMapping(value = { "/appointments", "/appointments/" })
    public List<AppointmentDto> getAllAppointments() {
        return appointmentService.getAllAppointment().stream().map(a -> RepairShopUtil.convertToDto(a)).collect(Collectors.toList());
    }

    @GetMapping(value = { "/appointment/{id}", "/appointment/{id}/" })
    public AppointmentDto getAppointment(@PathVariable("id") Long id) {
        return RepairShopUtil.convertToDto(appointmentService.getAppointment(id));
    }

    /**
     * edit appointment
     */
    @PutMapping(value = { "/appointment/{id}", "/appointment/{id}/" })
    public void editAppointment(@PathVariable("id") Long id,
                                @RequestParam(name="service") List<BookableServiceDto> serviceDto_add,
                                @RequestParam(name="service") List<BookableServiceDto> serviceDto_delete,
                                @RequestParam TimeSlotDto timeSlotDto) throws IllegalArgumentException {
        TimeSlot timeSlot = timeSlotService.getTimeSlot(timeSlotDto.getId());
        if (canCancelAndDelete(timeSlot)){
            Appointment appointment = appointmentService.getAppointment(id);
            // converting service dto --> dao
            List<BookableService> service_add = new ArrayList<>();
            for (BookableServiceDto s: serviceDto_add){
                service_add.add(repairShopService.getService(s.getId()));
            }
            List<BookableService> service_delete = new ArrayList<>();
            for (BookableServiceDto s: serviceDto_delete){
                service_delete.add(repairShopService.getService(s.getId()));
            }

            appointmentService.editAppointment(appointment,service_add,service_delete,timeSlot);
        }
        throw new IllegalArgumentException("Cannot edit appointment before 24hr");
    }



    /**
     * delete appointment
     */
    @DeleteMapping(value = { "/appointment/{id}", "/appointment/{id}/" })
    public void deleteAppointment(@PathVariable("id") Long id) throws IllegalArgumentException {
        Appointment appointment = appointmentService.getAppointment(id);
        TimeSlot timeSlot = appointment.getTimeslot();
        // check if it's within 24 hr
        if (canCancelAndDelete(timeSlot)) {
            // find the appointment using id
            appointmentService.deleteAppointment(appointment);
        }
        throw new IllegalArgumentException("Cannot delete appointment before 24hr");
    }



    /**
    * create appointment with timeslot
    */

    @PostMapping(value = { "/appointment", "/appointment/" })
    public AppointmentDto createAppointment(@RequestParam(name="customer") CustomerDto customerDto,
                                            @RequestParam(name="service") List<BookableServiceDto> serviceDto,
                                            @RequestParam(name="timeslot")TimeSlotDto timeSlotDto) throws IllegalArgumentException {
        Customer customer = personService.getCustomer(customerDto.getId());
        //CONVERT BOOKABLE SERVICE DTO --> DAO
        List<BookableService> service = new ArrayList<>();
        for (BookableServiceDto s: serviceDto){
            service.add(repairShopService.getService(s.getId()));
        }

        TimeSlot timeSlot = timeSlotService.getTimeSlot(timeSlotDto.getId());
        Appointment appointment = appointmentService.createAppointment(service,customer,timeSlot);

        return RepairShopUtil.convertToDto(appointment);
    }


//    /**
//     * create appointment with date, time
//     */
//    @PostMapping(value = { "/appointment", "/appointment/" })
//    public AppointmentDto createAppointment(@RequestParam(name="customer") CustomerDto customerDto,
//                                            @RequestParam(name="service") ServiceDto serviceDto,
//                                            @RequestParam(name="bill") BillDto billDto,
//                                            @RequestParam Date date,
//                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
//                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime){
//        Customer customer = personService.getCustomer(customerDto.getId());
//        Service service_obj = repairShopService.getService(serviceDto.getId());
//        Bill bill = billService.getBill(billDto.getId());
//        TimeSlot timeSlot = timeSlotService.createTimeSlot(date, Time.valueOf(startTime),Time.valueOf(endTime) );
//        Appointment appointment = appointmentService.createAppointment(service_obj,customer,timeSlot,bill);
//        return convertToDto(appointment);
//    }

    @GetMapping(value = { "/appointments/person/{id}", "/appointments/person/{id}/"})
    public List<AppointmentDto> getAppointmentHistory(@PathVariable("id") Long id) {
        Customer customer = personService.getCustomer(id);
        List<AppointmentDto> apptsCustDtos = new ArrayList<>();
        for(Appointment appointment : appointmentService.getAppointmentsBookedByCustomer(customer)){
            apptsCustDtos.add(RepairShopUtil.convertToDto(appointment));
        }
        return apptsCustDtos;
    }

    /**
     * helper methods
     */

    /**
     * check if the time and date on timeSlot is 24 hours before the current time
     */
    private boolean canCancelAndDelete(TimeSlot timeSlot){

        LocalTime timeNow =  LocalTime.now();
        LocalDate today = LocalDate.now();
        int yearToday = today.getYear();
        int monthToday = today.getMonthValue();
        int dayToday = today.getDayOfMonth();
        int hourToday = timeNow.getHour();



        LocalDate tsDate = timeSlot.getDate().toLocalDate();
        int yearTS = tsDate.getYear();
        int monthTS = tsDate.getMonthValue();
        int dayTS = tsDate.getDayOfMonth();
        LocalTime tsStartTime = timeSlot.getStartTime().toLocalTime();
        int hourTS = tsStartTime.getHour();

        if (yearToday < yearTS || monthToday < monthTS || dayToday <= dayTS ){return false;}
        else{
            if (yearToday > yearTS || monthToday > monthTS || dayToday - dayTS >1){ return true;}
            // if there's only one day before the appointment date
            else{

                // can cancel / edit appointment when there's still 24 hours
                return hourToday<= hourTS;
            }
        }


    }
}
