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

    /**
     * create a time slot
     *
     * @param date timeslot date
     * @param startTime timeslot start time
     * @param endTime timeslot end time
     * @return timeslot
     * @throws IllegalArgumentException
     */
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

    /**
     * for purposed of testing no show to create a timeslot for now
     *
     * @param date timeslot date
     * @param startTime timeslot start time
     * @param endTime timeslot end time
     * @return timeslot
     */
   @Transactional
    public TimeSlot createTimeSlotforTestingNoShow(Date date, Time startTime, Time endTime){
        TimeSlot timeSlot = createInstanceOfTimeSlot(date, startTime, endTime);
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }

    /**
     * delete a timeslot
     *
     * @param timeslot timeslot
     */
    @Transactional
    public void deleteTimeSlot(TimeSlot timeslot) {
        if(timeslot == null){
            throw new IllegalArgumentException("Timeslot is null");
        }
        timeSlotRepository.deleteById(timeslot.getId());
    }

    /**
     * find timeslot
     *
     * @param id timeslot id
     * @return
     */
    @Transactional
    public TimeSlot getTimeSlot(Long id) {
        TimeSlot timeSlot = timeSlotRepository.findTimeSlotById(id);
        return timeSlot;
    }

    /**
     * find all timeslots
     *
     * @return list of timeslots
     */
    @Transactional
    public List<TimeSlot> getAllTimeSlot() {
        return timeSlotRepository.findAll();
    }

    /**
     * find all nonreserved timeslots
     *
     * @return list of timeslots
     */
    @Transactional
    public List<TimeSlot> getAllOpenTimeSlot() {
        List<TimeSlot> allSlots = getAllTimeSlot();
        List<Appointment> allAppts = RepairShopUtil.toList(appointmentRepository.findAll());
        List<TimeSlot> openSlots = new ArrayList<>();
        boolean legal = true;
        for(TimeSlot slot : allSlots){
            for(Appointment apt : allAppts){
                if(apt.getTimeslot().equals(slot)) {
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



    //HELPER FUNCTIONS

    /**
     * create instance of slot
     *
     * @param date date
     * @param startTime start time
     * @param endTime end time
     * @return slot
     */
    public TimeSlot createInstanceOfTimeSlot(Date date, Time startTime, Time endTime){
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setDate(date);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
        return timeSlot;
    }
}
