package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.*;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.service.*;
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

    @GetMapping(value = { "/appointments", "/appointments/" })
    public List<AppointmentDto> getAllAppointments() {
        return appointmentService.getAllAppointment().stream().map(a -> convertToDto(a)).collect(Collectors.toList());
    }

    /**
     * edit appointment
     */
    @PutMapping(value = { "/appointment/{id}", "/appointment/{id}/" })
    public void editAppointment(@PathVariable("id") Long id,
                                @RequestParam(name="service") ServiceDto serviceDto,
                                @RequestParam(name="bill") BillDto billDto,
                                @RequestParam TimeSlotDto timeSlotDto) throws IllegalArgumentException {
        TimeSlot timeSlot = timeSlotService.getTimeSlot(timeSlotDto.getId());
        if (canCancelAndDelete(timeSlot)){
            Appointment appointment = appointmentService.getAppointment(id);
            BookableService service_obj = repairShopService.getService(serviceDto.getId());
            Bill bill = billService.getBill(billDto.getId());
            appointmentService.editAppointment(appointment,bill,service_obj,timeSlot);
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
                                            @RequestParam(name="service") ServiceDto serviceDto,
                                            @RequestParam(name="bill") BillDto billDto,
                                            @RequestParam TimeSlotDto timeSlotDto) throws IllegalArgumentException {
        Customer customer = personService.getCustomer(customerDto.getId());
        BookableService service_obj = repairShopService.getService(serviceDto.getId());
        Bill bill = billService.getBill(billDto.getId());
        TimeSlot timeSlot = timeSlotService.getTimeSlot(timeSlotDto.getId());
        Appointment appointment = appointmentService.createAppointment(service_obj,customer,timeSlot,bill);
        return convertToDto(appointment);
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
    private AppointmentDto convertToDto(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("There is no such Appointment!");
        }
        //service, customer, timeslot, bill
        AppointmentDto appointmentDto = new AppointmentDto(convertToDto(appointment.getService()), convertToDto(appointment.getCustomer()),convertToDto(appointment.getTimeslot()),convertToDto(appointment.getBill()), appointment.getId());
        return appointmentDto;
    }

    private TimeSlotDto convertToDto(TimeSlot timeSlot){
        if (timeSlot == null) {
            throw new IllegalArgumentException("There is no such TimeSlot!");
        }
        //service, customer, timeslot bill
        TimeSlotDto timeSlotDto = new TimeSlotDto(timeSlot.getDate(),timeSlot.getStartTime(),timeSlot.getEndTime(), timeSlot.getId());
        return timeSlotDto;
    }

    private ServiceDto convertToDto(BookableService service) {
        if (service == null) {
            throw new IllegalArgumentException("There is no such Service!");
        }
        //service, customer, timeslot bill
        ServiceDto serviceDto = new ServiceDto(service.getName(), service.getCost(),service.getDuration(), service.getId());
        return serviceDto;
    }

    private CustomerDto convertToDto(Customer customer){
        if (customer == null){
            throw new IllegalArgumentException("There is no such Customer!");
        }
        CustomerDto customerDto = new CustomerDto(customer.getEmail(), customer.getUsername(), customer.getPassword(),customer.getId(),customer.getCardNumber(), customer.getCvv(), customer.getExpiry(),convertBillToDto(customer.getBills()),convertAppointmentToDto(customer.getAppointments()));
        return customerDto;
    }

    private BillDto convertToDto(Bill bill){
        if (bill == null){
            throw new IllegalArgumentException("There is no such Bill!");
        }
        BillDto billDto = new BillDto(bill.getDate(), bill.getTotalCost(), convertToDto(bill.getCustomer()),convertAppointmentToDto(bill.getAppointments()), bill.getId());
        return billDto;
    }

    private List<BillDto> convertBillToDto(List<Bill> bills){
        List<BillDto> billDtoList= new ArrayList<BillDto>();
        for (Bill b: bills){
            billDtoList.add(convertToDto(b));
        }
        return billDtoList;
    }

    private List<AppointmentDto> convertAppointmentToDto(List<Appointment> appointments) {
        List<AppointmentDto> appointmentDtoList = new ArrayList<AppointmentDto>();
        for (Appointment app : appointments) {
            appointmentDtoList.add(convertToDto(app));
        }
        return appointmentDtoList;
    }
}
