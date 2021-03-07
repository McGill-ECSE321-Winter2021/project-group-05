package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.TechnicianRepository;
import ca.mcgill.ecse321.repairshop.dao.TimeSlotRepository;
import ca.mcgill.ecse321.repairshop.model.Technician;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import ca.mcgill.ecse321.repairshop.utility.ListUtil;
import ca.mcgill.ecse321.repairshop.utility.TimeSlotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class TimeSlotService {
    @Autowired
    TimeSlotRepository timeSlotRepository;
    @Autowired
    TechnicianRepository technicianRepository;

    @Transactional
    public TimeSlot createTimeSlot(Date date, Time startTime, Time endTime){
        TimeSlot timeSlot = createInstanceOfTimeSlot(date, startTime, endTime);
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }

    @Transactional
    public TimeSlot getTimeSlot(Long id) {
        TimeSlot timeSlot = timeSlotRepository.findTimeSlotById(id);
        return timeSlot;
    }

    @Transactional
    public List<TimeSlot> getAllTimeSlot() {
        return ListUtil.toList(timeSlotRepository.findAll());
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

    //checks for dublicate timeslot entry
    public boolean isTimeSlotDuplicated(TimeSlot timeSlotInDb, TimeSlot newTimeSlot){
        Time startTimeOfTimeSlotInDb = timeSlotInDb.getStartTime();
        Time endTimeOfTimeSlotInDb = timeSlotInDb.getEndTime();
        Date dateOfTimeSlotInDb = timeSlotInDb.getDate();

        Time startTimeOfNewTimeSlot = timeSlotInDb.getStartTime();
        Time endTimeOfNewTimeSlot = timeSlotInDb.getEndTime();
        Date dateOfNewTimeSlot = timeSlotInDb.getDate();

        if(!dateOfNewTimeSlot.equals(dateOfTimeSlotInDb)){
            return false;
        }
        if(startTimeOfNewTimeSlot.equals(startTimeOfTimeSlotInDb)
                && endTimeOfNewTimeSlot.equals(endTimeOfTimeSlotInDb)){
            return false;
        }
        return true;
    }
}
