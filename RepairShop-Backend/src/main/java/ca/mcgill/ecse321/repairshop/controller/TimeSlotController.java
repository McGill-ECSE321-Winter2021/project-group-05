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

    /**
     * create timeslot method creates a new available timeslot for appointments
     *
     * @param date      date of timeslot
     * @param startTime start time of timeslot
     * @param endTime   end time of timeslot
     * @return response entity
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/timeSlot", "/timeSlot/"})
    public ResponseEntity<?> createTimeSlot(@RequestParam Date date,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime)
            throws IllegalArgumentException {
        if (canEnterTimeSlot(startTime, endTime, date.toLocalDate())) {
            TimeSlot timeSlot = timeSlotService.createTimeSlot(date, Time.valueOf(startTime), Time.valueOf(endTime));
            return new ResponseEntity<>(RepairShopUtil.convertToDto(timeSlot), HttpStatus.OK);
        } else {
            throw new IllegalArgumentException("StartTime must be before EndTime and startDate must be at least in 2 days");
        }
    }

    /**
     * delete timeslot method deletes an existing timeslot
     *
     * @param id timeslot id
     * @return response entity
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/timeSlot/{id}", "/timeSlot/{id}/"})
    public ResponseEntity<?> deleteTimeSlot(@PathVariable("id") Long id) throws IllegalArgumentException {
        TimeSlot timeSlot = timeSlotService.getTimeSlot(id);
        if (timeSlot == null) {
            return new ResponseEntity<>("Cannot delete null timeslot", HttpStatus.BAD_REQUEST);
        }
        if (canDeleteTimeSlot(timeSlot)) {
            timeSlotService.deleteTimeSlot(timeSlot);
        }
        return new ResponseEntity<>("The timeslot has been successfully deleted", HttpStatus.OK);
    }

    /**
     * get available timeslots returns only timeslots that don't have an appointment
     *
     * @return list of timeslots
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/timeslotAvailable", "/timeslotAvailable/"})
    public List<TimeSlot> getAvailableTimeSlots() throws IllegalArgumentException {
        List<TimeSlot> availableSlots = new ArrayList();
        for (TimeSlot timeSlot : timeSlotService.getAllOpenTimeSlot()) {
            availableSlots.add(timeSlot);
        }
        return availableSlots;
    }

    /**
     * assign slot method API for assigning a timeslot to a technician
     *
     * @param email      technician email
     * @param timeSlotId id of timeslot assigning
     * @return list of timeslots
     */
    @PostMapping(value = {"/assignSlot/{id}", "/assignSlot/{id}/"})
    public List<TimeSlot> assignSlot(@RequestParam(value = "email") String email, @PathVariable("id") Long timeSlotId) {
        TimeSlot timeSlot = timeSlotService.getTimeSlot(timeSlotId);
        Technician technician = technicianRepository.findTechnicianByEmail(email);
        List<TimeSlot> timeSlots = technician.getTimeSlots();
        if (timeSlots == null) {
            timeSlots = new ArrayList<>();
            timeSlots.add(timeSlot);
        } else {
            timeSlots.add(timeSlot);
        }
        technician.setTimeSlots(timeSlots);
        technicianRepository.save(technician);
        return timeSlots;
    }

    /**
     * get all timeslots of technician method API for availability of a technician
     *
     * @param email technician email
     * @return list of assigned timeslots
     */
    @GetMapping(value = {"/timeSlotOfTechnician/{email}/", "/timeSlotOfTechnician/{email}"})
    public List<TimeSlot> getAllTimeSlotOfTechnician(@PathVariable String email) {
        Technician technician = technicianRepository.findTechnicianByEmail(email);
        return technician.getTimeSlots();
    }

    /**
     * give appoinment to technician method assigns an appointment to a technician
     *
     * @param email technician email
     * @param id    timeslot id
     * @return appointment transfer object
     */
    @PutMapping(value = {"/assignAppointment/{id}", "/assignAppointment/{id}/"})
    public AppointmentDto giveAppointmentToTechnician(@RequestParam(value = "email") String email,
                                                      @PathVariable("id") Long id) {
        Appointment appointment = appointmentRepository.findAppointmentById(id);
        Technician technician = technicianRepository.findTechnicianByEmail(email);
        List<Appointment> appointments = technician.getAppointments();
        if (appointments == null) {
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
        technician.setAppointments(appointments);
        //add checks
        technicianRepository.save(technician);
        return RepairShopUtil.convertToDto(appointment);
    }

    /**
     * can enter timeslot method determines if current time is at least a day before start time
     *
     * @param startTime appointment start time
     * @param endTime appointment end time
     * @param date appointment date
     * @return boolean if can enter timeslot
     */
    private boolean canEnterTimeSlot(LocalTime startTime, LocalTime endTime, LocalDate date) {
        if (startTime.isAfter(endTime)) {
            return false;
        }
        LocalDate todayplus1 = LocalDate.now().plusDays(1);

        if (todayplus1.isBefore(date)) {
            return true;
        }
        return true;
    }

    /**
     * can delete timeslot method determines if it is at least a day before an appointment start time
     *
     * @param timeSlot timeslot wanting to be deleted
     * @return boolean if can delete timeslot
     */
    private boolean canDeleteTimeSlot(TimeSlot timeSlot) {

        LocalDate todayplus1 = LocalDate.now().plusDays(1);
        LocalDate tsDate = timeSlot.getDate().toLocalDate();

        if (todayplus1.isBefore(tsDate)) {
            return true;
        }
        return false;
    }

}