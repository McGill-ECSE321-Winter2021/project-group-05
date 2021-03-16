package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.BookableService;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.model.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestTimeSlotService {
    @Mock
    private TimeSlotRepository timeSlotDao;

    @InjectMocks
    private TimeSlotService timeSlotService;

    private static final Long TIMESLOT_ID = 0L;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(timeSlotDao.findTimeSlotById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(TIMESLOT_ID)) {
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setId(TIMESLOT_ID);
                return timeSlot;
            }
            else{
                return null;
            }
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(timeSlotDao.save(any(TimeSlot.class))).thenAnswer(returnParameterAsAnswer);
    }



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
        assertEquals("start time must be before end time", error);
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
        assertEquals(startTime.toString(), timeSlot.getStartTime().toLocalTime().toString());
        assertEquals(endTime.toString(), timeSlot.getEndTime().toLocalTime().toString());

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

        TimeSlot timeSlot = timeSlotService.createTimeSlot(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));
        String error = null;

        try {
            timeSlotService.deleteTimeSlot(timeSlot);
            assertNull(timeSlotService.getTimeSlot(timeSlot.getId()));
        }
        catch (IllegalArgumentException e){
            error = e.getMessage();
        }



    }

    @Test
    public void testEnterTimeSlotTooLate(){
        LocalDate dateToday = LocalDate.now();
        Date appointmentDate = Date.valueOf(dateToday);
        LocalTime startTime = LocalTime.now().plusHours(3);
        LocalTime endTime = LocalTime.now().plusHours(12);

        String error = null;
        TimeSlot timeSlot = null;

        try {
            timeSlot = timeSlotService.createTimeSlot(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "start time must be at least 2 days from now");
        assertNull(timeSlot);





    }

}
