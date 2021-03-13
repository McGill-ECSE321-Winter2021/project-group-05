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
    public ResponseEntity<?> createBill createTimeSlot (@RequestParam Date date,
                                       @RequestParam @DateTimeFormat(iso  = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime)
            throws IllegalArgumentException {
        if(canEnterTimeSlot(startTime, endTime)){
            TimeSlot timeSlot = timSlotService.createTimeSlot(date, Time.valueOf(startTime), Time.valueOf(endTime));
            return convertToDto(timeSlot);
        }else{
            throw new IllegalArgumentException("StartTime must be before EndTime");
        }

    }
    private TimeSlotDto convertToDto(TimeSlot timeSlot){
        if (timeSlot == null){
            throw new IllegalArgumentException("There is no such TimeSlot!");
        }
        TimeSlotDto timeSlotDto = new TimeSlotDto(timeSlot.getDate(),timeSlot.getStartTime(),timeSlot.getEndTime(), timeSlot.getId());
        return timeSlotDto;
    }

    @DeleteMapping(value = { "/timeSlot/{id}", "/timeSlot/{id}/" })
    private void deleteTimeSlot(@PathVariable("id") Long id) throws IllegalArgumentException{
        TimeSlot timeSlot = timeSlotService.getTimeSlot(id);
        if (timeSlot == null) {
            throw new IllegalArgumentException("Cannot delete a null timeSlot");
        }
        if(canDeleteTimeSlot(timeSlot)){
            timeSlotService.deleteTimeSlot(timeSlot);
        }

    }

    private boolean canEnterTimeSlot(LocalTime startTime, LocalTime endTime){
        if(startTime.isAfter(endTime)) {
            return false;
        }
        return true;
    }

    private boolean canDeleteTimeSlot(TimeSlot timeSlot){
        LocalTime timeNow =  LocalTime.now();
        LocalTime startTime = timeSlot.getStartTime().toLocalTime();
        LocalDate today = LocalDate.now();
        LocalDate tsDate = timeSlot.getDate().toLocalDate();

        if(today.isBefore(tsDate)){
            return true;
        }
        return false;
    }
}
