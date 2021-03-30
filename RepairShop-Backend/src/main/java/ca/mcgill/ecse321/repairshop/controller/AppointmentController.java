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
                                             @RequestParam(value = "timeSlotId") Long timeSlotId,
                                             @RequestParam(value = "serviceNames") List<String> serviceNames) {
        try {
            TimeSlot timeSlot = timeSlotService.getTimeSlot(timeSlotId);
            if (canCancelAndDelete(timeSlot)) {
                Appointment appointment = appointmentService.getAppointment(id);

                List<BookableService> service_new = new ArrayList<>();
                for(String serviceName : serviceNames){
                    BookableService bookableService = repairShopService.getService(serviceName);
                    service_new.add(bookableService);
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
    public ResponseEntity<?> deleteAppointment(@PathVariable("id") Long id) throws AppointmentException {
        Appointment appointment = appointmentService.getAppointment(id);
        if (appointment == null) {
            throw new AppointmentException("Cannot delete a null appointment");
        }
        TimeSlot timeSlot = appointment.getTimeslot();
        // check if it's within 24 hr
        if (canCancelAndDelete(timeSlot)) {
            // find the appointment using id
            appointmentService.deleteAppointment(appointment);
            return new ResponseEntity<>("Appointment has been deleted", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Cannot delete appointment 24hrs before start time", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * create appointment with timeslot
     */

    @PostMapping(value = { "/appointment", "/appointment/" })
    public ResponseEntity<?> createAppointment( @RequestParam(value="customerEmail") String customerEmail,
                                                @RequestParam(value="serviceNames") List<String> serviceNames,
                                                @RequestParam(value="timeSlotId") Long id) {
        try {
            Customer customer = personService.getCustomer(customerEmail);
            //CONVERT BOOKABLE SERVICE DTO --> DAO
            List<BookableService> service = new ArrayList<>();
            for (String name: serviceNames){
                service.add(repairShopService.getService(name));
            }
            TimeSlot timeSlot = timeSlotService.getTimeSlot(id);
            Appointment appointment = appointmentService.createAppointment(service, customer, timeSlot);

            return new ResponseEntity<>(RepairShopUtil.convertToDto(appointment), HttpStatus.OK);
        }catch (AppointmentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = { "/appointment/person/{email}", "/appointment/person/{email}/"})
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

    @GetMapping(value = {"/appointments","/appointments/"})
    public List<AppointmentDto> getAllAppointments(){
        List<AppointmentDto> allApps = new ArrayList<>();
        for(Appointment appointment : appointmentService.getAllAppointments()){
            allApps.add(RepairShopUtil.convertToDto(appointment));
        }
        return allApps;
    }

    @PutMapping(value = { "/appointmentNoShow/{id}", "/appointmentNoShow/{id}/" })
    public ResponseEntity<?> enterNoShow(@PathVariable("id") Long id) throws AppointmentException{
        Appointment appointment = appointmentService.getAppointment(id);
        if (appointment == null) {
            return new ResponseEntity<>("Cannot enter no show for a null appointment", HttpStatus.BAD_REQUEST);
        }
        TimeSlot timeSlot = appointment.getTimeslot();
        if (canEnterNoShow(timeSlot)){
            appointmentService.enterNoShow(appointment);
            return new ResponseEntity<>("No show count updated for customer", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Cannot enter no show at this time", HttpStatus.BAD_REQUEST);
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

        if (yearToday < yearTS || monthToday < monthTS || dayToday <= dayTS ){return true;}
        else{
            if (yearToday > yearTS || monthToday > monthTS || dayToday - dayTS >1){ return false;}
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
        //LocalDate tsDate = timeslot.getDate().toLocalDate();
        LocalDate tsDate = LocalDate.now();
       // LocalTime tsStartTime = timeslot.getStartTime().toLocalTime();
        LocalTime tsStartTime = timeNow.minusHours(2);
        LocalTime tsEnterTime = timeslot.getStartTime().toLocalTime();

        if(today.equals(tsDate) && tsStartTime.plusMinutes(14).isBefore(timeNow)){
            return true;
        }
        return false;
    }
}
