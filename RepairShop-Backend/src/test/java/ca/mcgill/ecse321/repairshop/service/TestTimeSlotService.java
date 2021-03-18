package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.BookableService;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import ca.mcgill.ecse321.repairshop.utility.AppointmentException;
import ca.mcgill.ecse321.repairshop.utility.BookableServiceException;
import ca.mcgill.ecse321.repairshop.utility.PersonException;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestTimeSlotService {
    @Mock
    private TimeSlotRepository timeSlotDao;

    @Mock
    private AppointmentRepository appointmentDao;

    @Mock
    private CustomerRepository customerDao;

    @Mock
    private ServiceRepository serviceDao;

    @InjectMocks
    private TimeSlotService timeSlotService;

    @InjectMocks
    private AppointmentService appointmentService;

    @InjectMocks
    private PersonService personService;

    @InjectMocks
    private RepairShopService repairShopService;

    private static final Long TIMESLOT_ID = 0L;
    private static final Long APPOINTMENT_KEY = 0L;
    private static final Long APPOINTMENT_NONEXISTING_KEY = -1L;

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

        lenient().when(appointmentDao.findAppointmentById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(APPOINTMENT_KEY)) {
                Appointment appointment = new Appointment();
                appointment.setId(APPOINTMENT_KEY);
                return appointment;
            }
            else{
                return null;
            }
        });

        lenient().when(timeSlotDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
            List<TimeSlot> timeSlots = new ArrayList<>();
            TimeSlot timeslot = new TimeSlot();
            Calendar c = Calendar.getInstance();
            c.set(2021, Calendar.OCTOBER, 16, 9, 00, 0);
            Date appointmentDate = new Date(c.getTimeInMillis());
            LocalTime startTime = LocalTime.parse("09:00");
            c.set(2021, Calendar.OCTOBER, 16, 9, 59, 59);
            LocalTime endTime = LocalTime.parse("09:59");
            timeslot.setStartTime(Time.valueOf(startTime));
            timeslot.setEndTime(Time.valueOf(endTime));
            timeslot.setDate(appointmentDate);
            timeSlots.add(timeslot);
            return timeSlots;
        });

        lenient().when(appointmentDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
            TimeSlot timeslot = new TimeSlot();
            Calendar c = Calendar.getInstance();
            c.set(2021, Calendar.OCTOBER, 16, 9, 00, 0);
            Date appointmentDate = new Date(c.getTimeInMillis());
            LocalTime startTime = LocalTime.parse("09:00");
            c.set(2021, Calendar.OCTOBER, 16, 9, 59, 59);
            LocalTime endTime = LocalTime.parse("09:59");
            timeslot.setStartTime(Time.valueOf(startTime));
            timeslot.setEndTime(Time.valueOf(endTime));
            timeslot.setDate(appointmentDate);
            Customer customer = new Customer();
            customer.setEmail("hello@abc.ca");
            customer.setUsername("abc1");
            customer.setPassword("P00wwwwwwwwc");
            BookableService service = new BookableService();
            service.setName("hello");
            service.setCost(10);
            service.setDuration(15);
            List<BookableService> serviceList = new ArrayList<>();
            serviceList.add(service);

            Appointment appointment = new Appointment();
            appointment.setServices(serviceList);
            appointment.setCustomer(customer);
            appointment.setTimeslot(timeslot);
            List<Appointment> appointmentList = new ArrayList<>();
            appointmentList.add(appointment);
            return appointmentList;
        });



        lenient().when(customerDao.findCustomerByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals("hello@gmail.com")) {
                Customer customer = new Customer();
                customer.setEmail("hello@gmail.com");
                return customer;
            }
            else{
                return null;
            }
        });

        lenient().when(serviceDao.findServiceByName(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals("testService")) {
                BookableService service = new BookableService();
                return service;
            }
            else{
                return null;
            }
        });



        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(timeSlotDao.save(any(TimeSlot.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(appointmentDao.save(any(Appointment.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(serviceDao.save(any(BookableService.class))).thenAnswer(returnParameterAsAnswer);

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

        }
        assertEquals("Timeslot is null",error);

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

        }
        catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertNull(timeSlotService.getTimeSlot(timeSlot.getId()));



    }

    @Test
    public void testEnterTimeSlotTooLate(){
        LocalDate dateToday = LocalDate.now();
        Date appointmentDate = Date.valueOf(dateToday);
        LocalTime startTime = LocalTime.now().plusMinutes(1);
        LocalTime endTime = LocalTime.now().plusMinutes(5);

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

    @Test
    public void testGetOpenSlots(){
        Calendar c = Calendar.getInstance();
        c.set(2021, Calendar.OCTOBER, 16, 9, 00, 0);
        Date appointmentDate = new Date(c.getTimeInMillis());
        LocalTime startTime = LocalTime.parse("15:00");
        c.set(2021, Calendar.OCTOBER, 16, 9, 59, 59);
        LocalTime endTime = LocalTime.parse("16:59");



        String error = null;
        timeSlotService.createTimeSlot(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));

        //List<TimeSlot> listTimeSlot = null;
        int actual = 0;
        try {

            List<TimeSlot> listTimeSlot = timeSlotService.getAllOpenTimeSlot();
            if(listTimeSlot == null){
                actual = 0;
            }else{
                actual = listTimeSlot.size();
            }

        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        //int actual = listTimeSlot.size();
        int expected = 1;
        assertEquals(error, null);
        assertEquals(expected, actual);


    }

   

    private BookableService createTestService(){
        try {
            float serviceCost = 10;
            int serviceDuration = 60;
            BookableService service = repairShopService.createService("testservice", serviceCost, serviceDuration);

            return service;
        }
        catch (BookableServiceException e){
            e.printStackTrace();
            return null;
        }
    }

    private List<BookableService> createTestListServices(){
        List<BookableService> bookableServices = new ArrayList<>();
        bookableServices.add(createTestService());
        return bookableServices;
    }

    private Customer createTestCustomer(){
        // CREATING CUSTOMER
        String customerUsername = "Bob";
        String customerPassword = "abc123";
        Customer testCustomer = null;
        try {
            testCustomer = personService.createCustomer("flor@abc.com", customerUsername, customerPassword);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        testCustomer.setEmail("flor@abc.com");
        return testCustomer;
    }


}
