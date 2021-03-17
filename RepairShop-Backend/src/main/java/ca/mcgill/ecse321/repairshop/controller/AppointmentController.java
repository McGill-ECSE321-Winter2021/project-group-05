package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.*;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.service.*;
import ca.mcgill.ecse321.repairshop.utility.AppointmentException;
import ca.mcgill.ecse321.repairshop.utility.PersonException;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
public class AppointmentController {

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


    @GetMapping(value = { "/appointment/{id}", "/appointment/{id}/" })
    public AppointmentDto getAppointment(@PathVariable("id") Long id) {
        return RepairShopUtil.convertToDto(appointmentService.getAppointment(id));
    }

    /**
     * edit appointment
     */
    @PutMapping(value = { "/appointment/{id}", "/appointment/{id}/" })
    public ResponseEntity<?> editAppointment(@PathVariable("id") Long id,
                                             @RequestBody AppointmentDto appointmentDto) {
        try {
            TimeSlot timeSlot = timeSlotService.getTimeSlot(appointmentDto.getTimeSlot().getId());
            if (canCancelAndDelete(timeSlot)) {
                Appointment appointment = appointmentService.getAppointment(id);

                List<BookableServiceDto> newServicesDto = appointmentDto.getServices();
                List<BookableService> service_new = new ArrayList<>();
                for (BookableServiceDto s : newServicesDto) {
                    service_new.add(repairShopService.getService(s.getName()));
                }

                Appointment newAppointment = appointmentService.editAppointment(appointment, service_new, timeSlot);
                return new ResponseEntity<>(RepairShopUtil.convertToDto(newAppointment), HttpStatus.OK);
            }
            throw new AppointmentException("Cannot edit appointment before 24hr");
        }
        catch (AppointmentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * delete appointment
     */
    @DeleteMapping(value = { "/appointment/{id}", "/appointment/{id}/" })
    public void deleteAppointment(@PathVariable("id") Long id) throws AppointmentException {
        Appointment appointment = appointmentService.getAppointment(id);
        if (appointment == null) {
            throw new AppointmentException("Cannot delete a null appointment");
        }

        TimeSlot timeSlot = appointment.getTimeslot();
        // check if it's within 24 hr
        if (canCancelAndDelete(timeSlot)) {
            // find the appointment using id
            appointmentService.deleteAppointment(appointment);
        }
        else{
            throw new AppointmentException("Cannot delete appointment before 24hr");
        }
    }

    /**
     * create appointment with timeslot
     */

    @PostMapping(value = { "/appointment", "/appointment/" })
    public ResponseEntity<?> createAppointment( @RequestParam(value="customerEmail") String customerEmail,
                                                @RequestParam(value="serviceNames") List<String> serviceNames,
                                                @RequestBody TimeSlotDto timeSlotDto) {
        try {
            Customer customer = personService.getCustomer(customerEmail);
            //CONVERT BOOKABLE SERVICE DTO --> DAO
            List<BookableService> service = new ArrayList<>();
            for (String name: serviceNames){
                service.add(repairShopService.getService(name));
            }

            TimeSlot timeSlot = RepairShopUtil.convertToEntity(timeSlotDto);
            Appointment appointment = appointmentService.createAppointment(service, customer, timeSlot);

            return new ResponseEntity<>(RepairShopUtil.convertToDto(appointment), HttpStatus.OK);
        }catch (AppointmentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = { "/appointments/person/{id}", "/appointments/person/{id}/"})
    public List<AppointmentDto> getAppointmentHistory(@PathVariable("email") String email) throws AppointmentException {
        Customer customer = null;
        try {
            customer = personService.getCustomer(email);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        List<AppointmentDto> apptsCustDtos = new ArrayList<>();
        for(Appointment appointment : appointmentService.getAppointmentsBookedByCustomer(customer)){
            apptsCustDtos.add(RepairShopUtil.convertToDto(appointment));
        }
        return apptsCustDtos;
    }


    @PutMapping(value = { "/appointmentNoShow/{id}", "/appointmentNoShow/{id}/" })
    public void enterNoShow(@PathVariable("id") Long id) throws AppointmentException{
        Appointment appointment = appointmentService.getAppointment(id);
        if (appointment == null) {
            throw new AppointmentException("Cannot enter no show for a null appointment");
        }
        TimeSlot timeSlot = appointment.getTimeslot();
        if (canEnterNoShow(timeSlot)){
            appointmentService.enterNoShow(appointment);
        }else{
            throw new AppointmentException("Cannot enter no show at this time");
        }
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
    private boolean canEnterNoShow(TimeSlot timeslot){
        LocalTime timeNow =  LocalTime.now();
        LocalDate today = LocalDate.now();
        LocalDate tsDate = timeslot.getDate().toLocalDate();
        LocalTime tsStartTime = timeslot.getStartTime().toLocalTime();
        LocalTime tsEnterTime = timeslot.getStartTime().toLocalTime();

        if(today.equals(tsDate) && tsStartTime.plusMinutes(14).isBefore(timeNow)){
            return true;
        }
        return false;
    }
}
