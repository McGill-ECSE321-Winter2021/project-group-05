package ca.mcgill.ecse321.repairshop.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class TimeSlotController {

    @PostMapping(value = {"/timeSlot", "/timeSlot/"})
    public TimeSlotDto createTimeSlot (@RequestParam Date date, @RequestParam @DateTimeFormat(iso  = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime) throws IllegalArgumentException {
        TimeSlot timeSlot = TimeSlotService.createTimeSlot(date, Time.valueOf(startTime), Time.ValueOf(endTime));
        return convertToDto(timeSlot);
    }

}
