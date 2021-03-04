package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.TimeSlotRepository;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import ca.mcgill.ecse321.repairshop.utility.ListUtil;
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

    @Transactional
    public TimeSlot createTimeSlot(Date date, Time startTime, Time endTime){
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setDate(date);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
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
}
