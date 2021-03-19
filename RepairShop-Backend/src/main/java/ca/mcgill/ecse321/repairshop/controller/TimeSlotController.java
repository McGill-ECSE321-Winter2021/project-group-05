package ca.mcgill.ecse321.repairshop.controller;

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
}
