package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.AppointmentRepository;
import ca.mcgill.ecse321.repairshop.dao.TechnicianRepository;
import ca.mcgill.ecse321.repairshop.dao.TimeSlotRepository;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSlotService {
    @Autowired
    TimeSlotRepository timeSlotRepository;
    @Autowired
    TechnicianRepository technicianRepository;
    @Autowired
    AppointmentRepository appointmentRepository;

    @Transactional
    public TimeSlot createTimeSlot(Date date, Time startTime, Time endTime) throws IllegalArgumentException{
        if(startTime.toLocalTime().isAfter(endTime.toLocalTime())) {
            throw new IllegalArgumentException("start time must be before end time");
        }
        LocalTime timeNow =  LocalTime.now();
        LocalDate todayplus2 = LocalDate.now().plusDays(2);

        if(date.toLocalDate().isBefore(todayplus2)){
            throw new IllegalArgumentException("start time must be at least 2 days from now");
        }
        TimeSlot timeSlot = createInstanceOfTimeSlot(date, startTime, endTime);
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }

   @Transactional
    public TimeSlot createTimeSlotforTestingNoShow(Date date, Time startTime, Time endTime){
        TimeSlot timeSlot = createInstanceOfTimeSlot(date, startTime, endTime);
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }

    @Transactional
    public void deleteTimeSlot(TimeSlot timeslot) {
        if(timeslot == null){
            throw new IllegalArgumentException("Timeslot is null");
        }
        timeSlotRepository.deleteById(timeslot.getId()); //??????
    }

    @Transactional
    public TimeSlot getTimeSlot(Long id) {
        TimeSlot timeSlot = timeSlotRepository.findTimeSlotById(id);
        return timeSlot;
    }

    @Transactional
    public List<TimeSlot> getAllTimeSlot() {
        return timeSlotRepository.findAll();
    }

    @Transactional
    public List<TimeSlot> getAllOpenTimeSlot() {
        List<TimeSlot> allSlots = getAllTimeSlot();
        List<Appointment> allAppts = RepairShopUtil.toList(appointmentRepository.findAll());
        List<TimeSlot> openSlots = new ArrayList<>();
        boolean legal = true;
        for(TimeSlot slot : allSlots){
            for(Appointment a : allAppts){
                if(a.getTimeslot().equals(slot)) {
                    legal = false;
                }
            }
            if(legal){
                openSlots.add(slot);
            }
            legal = true;

        }
        return openSlots;
    }

    /**
     * creation of a timeslot by the administrator and assigning it to a technician
     */

    //HELPER FUNCTIONS

    //Construct a timeSlot
    public TimeSlot createInstanceOfTimeSlot(Date date, Time startTime, Time endTime){
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setDate(date);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
        return timeSlot;
    }
}
