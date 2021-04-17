package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.*;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.service.*;
import ca.mcgill.ecse321.repairshop.utility.AppointmentException;
import ca.mcgill.ecse321.repairshop.utility.PersonException;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public String sayHello() {
        return "hello world";
    }

    @GetMapping(value = {"/appointment/{id}", "/appointment/{id}/"})
    public AppointmentDto getAppointment(@PathVariable("id") Long id) {
        return RepairShopUtil.convertToDto(appointmentService.getAppointment(id));
    }

    /**
     * edit appointment method allows appointments to be edited by any user
     *
     * @param id           the current appointment id
     * @param timeSlotId   the id of the corresponding timeslot of the appointment
     * @param serviceNames all the services attached to current appointment
     * @return ResponseEntity corresponding to edited appointment
     * @throws AppointmentException
     */
    @PutMapping(value = {"/appointment/{id}", "/appointment/{id}/"})
    public ResponseEntity<?> editAppointment(@PathVariable("id") Long id,
                                             @RequestParam(value = "timeSlotId") Long timeSlotId,
                                             @RequestParam(value = "serviceNames") List<String> serviceNames) {
        try {
            TimeSlot timeSlot = timeSlotService.getTimeSlot(timeSlotId);
            if (canCancelAndDelete(timeSlot)) {
                Appointment appointment = appointmentService.getAppointment(id);

                List<BookableService> service_new = new ArrayList<>();
                for (String serviceName : serviceNames) {
                    BookableService bookableService = repairShopService.getService(serviceName);
                    service_new.add(bookableService);
                }

                Appointment newAppointment = appointmentService.editAppointment(appointment, service_new, timeSlot);
                return new ResponseEntity<>(RepairShopUtil.convertToDto(newAppointment), HttpStatus.OK);
            }
            throw new AppointmentException("Cannot edit appointment before 24hr");
        } catch (AppointmentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * edit appointment method allows appointments to be edited by any user
     *
     * @param id           the current appointment id
     * @param timeSlotId   the id of the corresponding timeslot of the appointment
     * @param serviceNames all the services attached to current appointment
     * @return ResponseEntity corresponding to edited appointment
     * @throws AppointmentException
     */
    @PutMapping(value = {"/appointment/", "/appointment"})
    public ResponseEntity<?> editAppointment2(@RequestParam(value = "id") Long id,
                                             @RequestParam(value = "timeSlotId") Long timeSlotId,
                                             @RequestParam(value = "serviceNames") List<String> serviceNames) {
        try {
            TimeSlot timeSlot = timeSlotService.getTimeSlot(timeSlotId);
            if (canCancelAndDelete(timeSlot)) {
                Appointment appointment = appointmentService.getAppointment(id);

                List<BookableService> service_new = new ArrayList<>();
                for (String serviceName : serviceNames) {
                    BookableService bookableService = repairShopService.getService(serviceName);
                    service_new.add(bookableService);
                }

                Appointment newAppointment = appointmentService.editAppointment(appointment, service_new, timeSlot);
                return new ResponseEntity<>(RepairShopUtil.convertToDto(newAppointment), HttpStatus.OK);
            }
            throw new AppointmentException("Cannot edit appointment before 24hr");
        } catch (AppointmentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * delete appointment method cancels an appointment, can be used by any user
     *
     * @param id the appointment id for the appointment to be deleted
     * @throws AppointmentException
     */
    @DeleteMapping(value = {"/appointment/{id}", "/appointment/{id}/"})
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
        } else {
            return new ResponseEntity<>("Cannot delete appointment 24hrs before start time", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * delete appointment method cancels an appointment, can be used by any user
     *
     * @param id the appointment id for the appointment to be deleted
     * @throws AppointmentException
     */
    @DeleteMapping(value = {"/appointment", "/appointment/"})
    public ResponseEntity<?> deleteAppointment2(@RequestParam(value = "id") Long id) throws AppointmentException {
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
        } else {
            return new ResponseEntity<>("Cannot delete appointment 24hrs before start time", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * create appointment method creates an appointment to a current timeslot for a specific customer with a list of services for the appointment
     *
     * @param customerEmail is the email of the customer making an appointment
     * @param serviceNames is the list of all services desired by customer for this appointment
     * @param startTime is the start time of the timeslot of the appointment
     * @param date is the date of the timeslot of the appointment
     * @return appointment created
     * @throws AppointmentException
     */

    @PostMapping(value = {"/appointment", "/appointment/"})
    public ResponseEntity<?> createAppointment(@RequestParam(value = "customerEmail") String customerEmail,
                                               @RequestParam(value = "serviceNames") List<String> serviceNames,
                                               @RequestParam(value = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
                                               @RequestParam(value = "date") java.sql.Date date) {
        try {
            Customer customer = personService.getCustomer(customerEmail);
            //CONVERT BOOKABLE SERVICE DTO --> DAO
            List<BookableService> service = new ArrayList<>();
            int duration = 0;
            for (String name : serviceNames) {
                service.add(repairShopService.getService(name));
                duration += repairShopService.getService(name).getDuration();
            }
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            String time = (setEndTimeOfAppointmentBasedOnDuration(startTime.toString(), duration));
            java.sql.Time endTime = new java.sql.Time(formatter.parse(time).getTime());
            TimeSlot timeSlot = timeSlotService.createTimeSlot(date, Time.valueOf(startTime), endTime);
            //TimeSlot timeSlot = timeSlotService.getTimeSlot(id)

            Appointment appointment = appointmentService.createAppointment(service, customer, timeSlot);

            return new ResponseEntity<>(RepairShopUtil.convertToDto(appointment), HttpStatus.OK);
        } catch (AppointmentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ParseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * create appointment method creates an appointment to a current timeslot for a specific customer with a list of services for the appointment
     *
     * @param customerEmail is the email of the customer making an appointment
     * @param serviceNames is the list of all services desired by customer for this appointment
     * @param startTime is the start time of the timeslot of the appointment
     * @param date is the date of the timeslot of the appointment
     * @return appointment created
     * @throws AppointmentException
     */

    @PostMapping(value = {"/appointment/app", "/appointment/app/"})
    public ResponseEntity<?> createAppointmentapp(@RequestParam(value = "customerEmail") String customerEmail,
                                               @RequestParam(value = "serviceNames") List<String> serviceNames,
                                               @RequestParam(value = "startTime") String startTime,
                                               @RequestParam(value = "date") String date) {
        try {
            Customer customer = personService.getCustomer(customerEmail);
            //CONVERT BOOKABLE SERVICE DTO --> DAO
            List<BookableService> service = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date d = null;

            try {
                d = sdf.parse("date");
            } catch (ParseException ex) {
            }
            int duration = 0;
            for (String name : serviceNames) {
                service.add(repairShopService.getService(name));
                duration += repairShopService.getService(name).getDuration();
            }
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            String time = (setEndTimeOfAppointmentBasedOnDuration(startTime.toString(), duration));
            java.sql.Time endTime = new java.sql.Time(formatter.parse(time).getTime());
            TimeSlot timeSlot = timeSlotService.createTimeSlot((java.sql.Date) d, Time.valueOf(startTime), endTime);
            //TimeSlot timeSlot = timeSlotService.getTimeSlot(id)

            Appointment appointment = appointmentService.createAppointment(service, customer, timeSlot);

            return new ResponseEntity<>(RepairShopUtil.convertToDto(appointment), HttpStatus.OK);
        } catch (AppointmentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ParseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * get appointment history method returns all appointments for one specific custer
     *
     * @param email is the customer email
     * @return list of appointments
     * @throws AppointmentException
     */

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

    /**
     * Enter no show method allows technician or admin to register a no show for a customer if they are at least 15 minutes late for their appointment
     *
     * @param id is the id of the appointment
     * @return response entity
     * @throws AppointmentException
     */

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
     * get appointments of technician method returns all the appointments assigned to one technician
     * used for the web
     *
     * @param email is the technicians email
     * @return list of appointments
     * @throws PersonException
     */
    @GetMapping(value = {"/appointmentOfTechnician/{emai}/", "/appointmentOfTechnician/{email}"})
    public List<AppointmentDto> getAppointmentOfTechnician (@PathVariable String email) throws PersonException {
        Technician technician = personService.getTechnician(email);
        List<Appointment> appointments = technician.getAppointments();
        List<AppointmentDto> list = new ArrayList<>();
        for(Appointment appointment : appointments){
            list.add(RepairShopUtil.convertToDto(appointment));
        }
        return list;
    }

    /**
     * get appointments of technician method returns all the appointments assigned to one technician
     * used for tha app
     *
     * @param email is the technicians email
     * @return list of appointments
     * @throws PersonException
     */
    @GetMapping(value = {"/appointmentOfTechnician", "/appointmentOfTechnician/}"})
    public List<AppointmentDto> getAppointmentOfTechnician2(@RequestParam(value = "email") String email) throws PersonException {
        Technician technician = personService.getTechnician(email);
        List<Appointment> appointments = technician.getAppointments();
        List<AppointmentDto> list = new ArrayList<>();
        for(Appointment appointment : appointments){
            list.add(RepairShopUtil.convertToDto(appointment));
        }
        return list;
    }

    /**
     * get all appointment methods returns all created appointments regardless of customer or technician
     *
     * @return list of appointments
     */
    @GetMapping(value = {"/allappointments/", "/allappointments"})
    public List<AppointmentDto> getAllAppointments(){
        List<Appointment> appointments = appointmentService.getAllAppointments();
        List<AppointmentDto> list = new ArrayList<>();
        for(Appointment appointment : appointments){
            list.add(RepairShopUtil.convertToDto(appointment));
        }
        return list;
    }

    /**
     * helper methods
     */

    /**
     * can cancel and delete method check if the time and date on timeSlot is 24 hours before the current time
     *
     * @param timeSlot is the timeslot for the appointment looking to be edited or canceled
     * @return boolean if can or can't delete
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

    /**
     * can enter no show method checks that it is at least 15 minutes after start of appointment
     *
     * @param timeslot the timeslot of corresponding appointment
     * @return boolean if can enter no show
     */
    private boolean canEnterNoShow(TimeSlot timeslot){
        LocalTime timeNow =  LocalTime.now();
        LocalDate today = LocalDate.now();
        LocalDate timeSlotDate = timeslot.getDate().toLocalDate();
        LocalTime timeSlotStartTime = timeslot.getStartTime().toLocalTime();
        if(today.equals(timeSlotDate) && timeSlotStartTime.plusMinutes(14).isAfter(timeNow)){
            return true;
        }
        return false;

    }

    /**
     * set end time based on duration method calculates the end time of an appointment based on when the appointment starts and the services chosen
     *
     * @param startTime is the start time of the appointment
     * @param duration is the duration of the service(s) for the appointment
     * @return String corresponding to end time
     */
    private static String setEndTimeOfAppointmentBasedOnDuration(String startTime, int duration){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = df.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, duration);
        String newTime = df.format(cal.getTime());
        return newTime;
    }
}
