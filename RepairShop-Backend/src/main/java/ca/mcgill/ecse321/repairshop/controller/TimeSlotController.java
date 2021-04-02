package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dao.AppointmentRepository;
import ca.mcgill.ecse321.repairshop.dao.TechnicianRepository;
import ca.mcgill.ecse321.repairshop.dto.AppointmentDto;
import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.Technician;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import ca.mcgill.ecse321.repairshop.service.TimeSlotService;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TimeSlotController {
    @Autowired
    private TimeSlotService timeSlotService;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping(value = {"/timeSlot", "/timeSlot/"})
    public ResponseEntity<?> createTimeSlot (@RequestParam Date date,
                                             @RequestParam @DateTimeFormat(iso  = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime)
            throws IllegalArgumentException {
        if(canEnterTimeSlot(startTime, endTime, date.toLocalDate())){
            TimeSlot timeSlot = timeSlotService.createTimeSlot(date, Time.valueOf(startTime), Time.valueOf(endTime));
            return new ResponseEntity<>(RepairShopUtil.convertToDto(timeSlot), HttpStatus.OK);
        }
        else{
            throw new IllegalArgumentException("StartTime must be before EndTime and startDate must be at least in 2 days");
        }
    }


    @DeleteMapping(value = { "/timeSlot/{id}", "/timeSlot/{id}/" })
    public ResponseEntity<?> deleteTimeSlot(@PathVariable("id") Long id) throws IllegalArgumentException{
        TimeSlot timeSlot = timeSlotService.getTimeSlot(id);
        if (timeSlot == null) {
           return new ResponseEntity<>("Cannot delete null timeslot", HttpStatus.BAD_REQUEST);
        }
        if(canDeleteTimeSlot(timeSlot)){
            timeSlotService.deleteTimeSlot(timeSlot);
        }
        return new ResponseEntity<>("The timeslot has been successfully deleted", HttpStatus.OK);
    }

   @GetMapping(value = { "/timeslotAvailable", "/timeslotAvailable/"})
    public List<TimeSlot> getAvailableTimeSlots()throws IllegalArgumentException{
        List<TimeSlot> availableSlots = new ArrayList();
        for(TimeSlot timeSlot : timeSlotService.getAllOpenTimeSlot()){
            availableSlots.add(timeSlot);
        }
        return availableSlots;
    }

    //API for assigning a timeslot to a technician
    @PostMapping(value = {"/assignSlot/{id}", "/assignSlot/{id}/"})
    public List<TimeSlot> assignSlot(@RequestParam(value = "email") String email, @PathVariable("id") Long timeSlotId ){
        TimeSlot timeSlot = timeSlotService.getTimeSlot(timeSlotId);
        Technician technician = technicianRepository.findTechnicianByEmail(email);
        List<TimeSlot> timeSlots = technician.getTimeSlots();
        if(timeSlots == null){
            timeSlots = new ArrayList<>();
            timeSlots.add(timeSlot);
        }else {
            timeSlots.add(timeSlot);
        }
        technician.setTimeSlots(timeSlots);
        technicianRepository.save(technician);
        return timeSlots;
    }

    //API for availability of a technician
    @GetMapping(value = {"/timeSlotOfTechnician/{email}/", "/timeSlotOfTechnician/{email}"})
    public List<TimeSlot> getAllTimeSlotOfTechnician(@PathVariable String email){
        Technician technician = technicianRepository.findTechnicianByEmail(email);
        return technician.getTimeSlots();
    }

    /**
     * Assigns an appointment to a technician
     */
    @PutMapping(value = {"/assignAppointment/{id}", "/assignAppointment/{id}/"})
    public AppointmentDto giveAppointmentToTechnician(@RequestParam(value = "email") String email,
                                                            @PathVariable("id") Long id){
        Appointment appointment = appointmentRepository.findAppointmentById(id);
        Technician technician = technicianRepository.findTechnicianByEmail(email);
        List<Appointment> appointments = technician.getAppointments();
        if(appointments == null){
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
        technician.setAppointments(appointments);
        //add checks
        technicianRepository.save(technician);
        return RepairShopUtil.convertToDto(appointment);
    }


    private boolean canEnterTimeSlot(LocalTime startTime, LocalTime endTime, LocalDate date){
        if(startTime.isAfter(endTime)) {
            return false;
        }
        LocalTime timeNow =  LocalTime.now();
        LocalDate todayplus1 = LocalDate.now().plusDays(1);

        if(todayplus1.isBefore(date)){
            return true;
        }
        return true;
    }

    private boolean canDeleteTimeSlot(TimeSlot timeSlot){
        LocalTime timeNow =  LocalTime.now();
        LocalTime startTime = timeSlot.getStartTime().toLocalTime();
        LocalDate todayplus1 = LocalDate.now().plusDays(1);
        LocalDate tsDate = timeSlot.getDate().toLocalDate();

        if(todayplus1.isBefore(tsDate)){
            return true;
        }
        return false;
    }

//        List<Technician> allAvailableTechnicians = RepairShopUtil.toList(technicianRepository.findAll());
//
//        boolean flag = false;
//        for(Technician technician : allAvailableTechnicians){
//            List<TimeSlot> timeSlots = technician.getTimeSlots();
//            for(TimeSlot techTimeSlot : timeSlots){
//                System.out.println(techTimeSlot.getId());
//                if(techTimeSlot.getDate().equals(timeslot.getDate())){
//                    List<Appointment> allTechAppointments = technician.getAppointments();
//                    if(allTechAppointments.isEmpty()){
//                        allTechAppointments.add(appointment);
//                        technician.setAppointments(allTechAppointments);
//                        System.out.println("we're ");
//                        appointmentRepository.save(appointment);
//                        technicianRepository.save(technician);
//                        flag = true;
//                        break;
//                    }else {
//                        for (Appointment oldApp : allTechAppointments) {
//                            if (! (oldApp.getTimeslot().getDate().equals(appointment.getTimeslot().getDate()))
//                                || appointment.getTimeslot().getStartTime().after(oldApp.getTimeslot().getEndTime())) {
//                                allTechAppointments.add(appointment);
//                                flag = true;
//                                technician.setAppointments(allTechAppointments);
//                                System.out.println("booked in else ");
//                                appointmentRepository.save(appointment);
//                                technicianRepository.save(technician);
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//            if(flag){
//                break;
//            }
//       }
//        if(flag == false){
//            throw new AppointmentException("No technician is available at the specified date");
//        }
//         && (techTimeSlot.getEndTime().before(timeslot.getStartTime()) || techTimeSlot.getEndTime().equals(timeslot.getStartTime()))
//                && (techTimeSlot.getStartTime().equals(timeslot.getStartTime()) || techTimeSlot.getStartTime().before(timeslot.getStartTime()))

    // appointmentRepository.save(appointment);
}
