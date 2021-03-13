package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.BookableService;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestTimeSlotService {
    @InjectMocks
    private TimeSlotService timeSlotService;

    // TODO: test for creating timeSlot in the past --> fail

    @Test
    public void testTimeSlotEndTimeBeforeStartTime() {
        // CREATING A TIMESLOT WHICH ENDS BEFORE EVEN STRATS
        Calendar c = Calendar.getInstance();
        c.set(2021, Calendar.OCTOBER, 16, 9, 00, 0);
        Date appointmentDate = new Date(c.getTimeInMillis());
        LocalTime startTime = LocalTime.parse("09:00");
        c.set(2021, Calendar.OCTOBER, 16, 8, 59, 59);
        LocalTime endTime = LocalTime.parse("08:59");

        TimeSlot timeSlot = null;
        String error = null;

        try {
            timeSlot = timeSlotService.createTimeSlot(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(timeSlot);
        // check error
        assertEquals("TimeSlot end time cannot before start time!", error);
    }

    @Test
    public void testTimeSlotStartTimeBeforeEndTime() {
        Calendar c = Calendar.getInstance();
        c.set(2021, Calendar.OCTOBER, 16, 9, 00, 0);
        Date appointmentDate = new Date(c.getTimeInMillis());
        LocalTime startTime = LocalTime.parse("09:00");
        c.set(2021, Calendar.OCTOBER, 16, 9, 59, 59);
        LocalTime endTime = LocalTime.parse("09:59");

        TimeSlot timeSlot = null;
        String error = null;

        try {
            timeSlot = timeSlotService.createTimeSlot(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNotNull(timeSlot);
        assertEquals(startTime.format(formatter).toString(), timeSlot.getStartTime().toString());
        assertEquals(endTime.format(formatter).toString(), timeSlot.getEndTime().toString());

    }

    @Test
    public void testDeleteNullTimeSlot(){
        TimeSlot timeSlot = null;
        String error = null;

        try {
            timeSlotService.deleteTimeSlot(timeSlot);
        }
        catch (IllegalArgumentException e){
            error = e.getMessage();
            assertEquals("Timeslot is null",error);
        }

    }

    @Test
    public void testDeleteTimeSlot(){
        Calendar c = Calendar.getInstance();
        c.set(2021, Calendar.OCTOBER, 16, 9, 00, 0);
        Date appointmentDate = new Date(c.getTimeInMillis());
        LocalTime startTime = LocalTime.parse("09:00");
        c.set(2021, Calendar.OCTOBER, 16, 9, 59, 59);
        LocalTime endTime = LocalTime.parse("09:59");

        timeSlot = timeSlotService.createTimeSlot(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));

        try {
            timeSlotService.deleteTimeSlot(timeSlot);
            assertNull(timeSlotService.getTimeSlot(timeSlot.getId()));
        }
        catch (IllegalArgumentException e){
            error = e.getMessage();
        }



    }

}
