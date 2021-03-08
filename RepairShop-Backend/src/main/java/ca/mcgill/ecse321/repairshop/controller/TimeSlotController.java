package ca.mcgill.ecse321.repairshop.controller;


import ca.mcgill.ecse321.repairshop.dto.TimeSlotDto;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import ca.mcgill.ecse321.repairshop.service.BillService;
import ca.mcgill.ecse321.repairshop.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

@CrossOrigin(origins = "*")
@RestController
public class TimeSlotController {
    @Autowired
    private TimeSlotService timSlotService;

    @PostMapping(value = {"/timeSlot", "/timeSlot/"})
    public TimeSlotDto createTimeSlot (@RequestParam Date date,
                                       @RequestParam @DateTimeFormat(iso  = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime)
            throws IllegalArgumentException {
        TimeSlot timeSlot = timSlotService.createTimeSlot(date, Time.valueOf(startTime), Time.valueOf(endTime));
        return convertToDto(timeSlot);
    }
    private TimeSlotDto convertToDto(TimeSlot timeSlot){
        if (timeSlot == null){
            throw new IllegalArgumentException("There is no such TimeSlot!");
        }
        TimeSlotDto timeSlotDto = new TimeSlotDto(timeSlot.getDate(),timeSlot.getStartTime(),timeSlot.getEndTime(), timeSlot.getId());
        return timeSlotDto;
    }
}
