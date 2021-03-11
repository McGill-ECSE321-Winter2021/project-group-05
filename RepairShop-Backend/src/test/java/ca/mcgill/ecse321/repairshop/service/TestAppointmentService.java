package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.model.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.awt.print.Book;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import org.mockito.stubbing.Answer;



@ExtendWith(MockitoExtension.class)
public class TestAppointmentService {

    // todo: move this to the manager test class
    @Mock
    private AppointmentRepository appointmentDao;
    @Mock
    private BillRepository billDao;
    @Mock
    private BusinessRepository businessDao;
    @Mock
    private AdministratorRepository administratorDao;
    @Mock
    private CustomerRepository customerDao;
    @Mock
    private OwnerRepository ownerDao;
    @Mock
    private ServiceRepository serviceDao;
    @Mock
    private TechnicianRepository technicianDao;
    @Mock
    private TimeSlotRepository timeSlotDao;


    @InjectMocks
    private AppointmentService appointmentService;
    @InjectMocks
    private PersonService personService;
    @InjectMocks
    private RepairShopService repairShopService;
    @InjectMocks
    private TimeSlotService timeSlotService;

    private static final Long APPOINTMENT_KEY = 0L;
    private static final Long APPOINTMENT_NONEXISTING_KEY = -1L;
    private static final String CUSTOMER_EMAIL = "mtl@mcgill.ca";

    // IN ORDER TO TEST GET GETAPPOINTMENT
    @BeforeEach
    public void setMockOutput() {

        // findAppointmentById
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

        // findByCustomer
        lenient().when(appointmentDao.findByCustomer(any())).thenAnswer( (InvocationOnMock invocation) -> {
                    if(invocation.getArgument(0) instanceof Customer){
                        Customer argumentCustomer = (Customer) invocation.getArgument(0);
                        if (argumentCustomer.getEmail().equals(CUSTOMER_EMAIL)){
                            Appointment appointment = new Appointment();
                            appointment.setCustomer(argumentCustomer);
                            return appointment;
                        }
                    }
                    return null;
        });

        // TODO: don't need to find by service/ bill

        // todo: move this to manager class
        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(appointmentDao.save(any(Appointment.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(administratorDao.save(any(Administrator.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(billDao.save(any(Bill.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(businessDao.save(any(Business.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(technicianDao.save(any(Technician.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(serviceDao.save(any(BookableService.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(timeSlotDao.save(any(TimeSlot.class))).thenAnswer(returnParameterAsAnswer);
    }

    /**
     * TESTING CREATE APPOINTMENT
     */

    // POSITIVE TEST
    @Test
    public void testCreateAppointment() {
        //CREATING TIMESLOT
        Calendar c = Calendar.getInstance();
        c.set(2021, Calendar.MAY, 1, 9, 0, 0);
        Date appointmentDate = new Date(c.getTimeInMillis());
        LocalTime startTime = LocalTime.parse("09:00");
        c.set(2021, Calendar.MAY, 1, 10, 0, 0);
        LocalTime endTime = LocalTime.parse("10:00");
        TimeSlot timeSlot = timeSlotService.createTimeSlot(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));

        List<BookableService> bookableServices = createTestListServices();
        Customer customer = createTestCustomer();
        Appointment appointment = null;
        try {
            appointment = appointmentService.createAppointment(bookableServices, customer, timeSlot);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }
        checkResultAppointment(appointment,bookableServices,
                customer.getEmail(),appointmentDate, startTime,endTime);
    }


    // NEGATIVE TEST
    @Test
    public void testCreateAppointmentNull() {

        String error = null;
        Appointment appointment = null;
        Customer customer = null;
        assertEquals(0,personService.getAllCustomer().size());
        List<BookableService> services = null;
        assertEquals(0,repairShopService.getAllService().size());
        TimeSlot timeSlot = null;
        assertEquals(0,timeSlotService.getAllTimeSlot().size());
        try {
            appointment = appointmentService.createAppointment(services,customer,timeSlot);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        // check error
        assertEquals("Customer, services and timeslot must all be selected for the appointment!", error);
    }

    // NEGATIVE TEST
    @Test
    public void testAppointmentCustomerAndServiceAndTimeSlotDoNotExist() {
        // CREATING A CUSTOMER WITHOUT SAVING IT TO DATABASE
        String email = "123@mcgill.ca";
        Customer customer = new Customer();
        customer.setEmail(email);
        assertEquals(0, personService.getAllCustomer().size());

        // CREATING A SERVICE WITHOUT SAVING
        BookableService service = new BookableService();
        service.setName("fake service");
        List<BookableService> services = new ArrayList<>();
        services.add(service);
        assertEquals(0, repairShopService.getAllService().size());

        // CREATING A TIMESLOT WITHOUT SAVING
        TimeSlot timeSlot = new TimeSlot();
        Calendar c = Calendar.getInstance();
        c.set(2021, Calendar.OCTOBER, 16, 9, 00, 0);
        Date appointmentDate = new Date(c.getTimeInMillis());
        LocalTime startTime = LocalTime.parse("09:00");
        c.set(2021, Calendar.OCTOBER, 16, 10, 00, 59);
        LocalTime endTime = LocalTime.parse("08:59");
        timeSlot.setEndTime(Time.valueOf(startTime));
        timeSlot.setEndTime(Time.valueOf(endTime));
        timeSlot.setDate(appointmentDate);
        assertEquals(0, timeSlotService.getAllTimeSlot().size());


        String error = null;
        Appointment appointment= null;
        try {
            appointment = appointmentService.createAppointment(services,customer,timeSlot);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(appointment);
        // check error
        assertEquals("Bookable Service, Customer, Timeslot don't exist!", error);
    }


    /**
     * TESTING getAppointment
     */
    // POSITIVE TEST
    @Test
    public void testGetExistingAppointment() {
        assertEquals(APPOINTMENT_KEY, appointmentService.getAppointment(APPOINTMENT_KEY).getId());
    }
    // NEGATIVE TEST
    @Test
    public void testGetNonExistingAppointment() {
        assertNull(appointmentService.getAppointment(APPOINTMENT_NONEXISTING_KEY));
    }

    /**
     * TESTING getAppointmentsBookedByCustomer
     */
    // POSITIVE TEST
    @Test
    public void testGetAppointmentByExisitingCustomer() {
        /* todo: ... how to test this...
        assertNotNull(appointmentService.getAppointmentsBookedByCustomer(personService.getAllCustomer().get(0))));
        assertEquals(1, appointmentService.getAppointmentsBookedByCustomer(personService.getAllCustomer().get(0)).size());
        assertEquals(APPOINTMENT_KEY, appointmentService.getAppointmentsBookedByCustomer(personService.getAllCustomer().get(0)).get(0).getId());

         */
    }
    // NEGATIVE TEST
    @Test
    public void testGetAppointmentByNonExistingCustomer() {
        // CUSTOMER IS NOT SAVED
        Customer customer = new Customer();
        customer.setEmail("mcgill@ca.com");
        assertNull(appointmentService.getAppointmentsBookedByCustomer(customer));
    }

    /**
     * TESTING editAppointment
     */
        // todo!!!!!!!!!!!!!!!!!!!!!!!!!
    @Test
    public void testEditAppointment(){

    }


    /**
     * TESTING deleteAppointment
     */
    // NEGATIVE TEST
    @Test
    public void testDeleteNullAppointment(){
        Appointment appointment = null;
        String error = null;
        try {
            appointmentService.deleteAppointment(appointment);
        }
        catch (IllegalArgumentException e){
            assertEquals("Cannot delete a null appointment",error);
        }
    }
    // POSITIVE TEST
    @Test
    public void testDeleteAppointment(){
        //CREATING TIMESLOT
        Calendar c = Calendar.getInstance();
        c.set(2021, Calendar.MAY, 1, 9, 0, 0);
        Date appointmentDate = new Date(c.getTimeInMillis());
        LocalTime startTime = LocalTime.parse("09:00");
        c.set(2021, Calendar.MAY, 1, 10, 0, 0);
        LocalTime endTime = LocalTime.parse("10:00");

        TimeSlot timeSlot = timeSlotService.createTimeSlot(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));
        List<BookableService> services = createTestListServices();
        Customer customer = createTestCustomer();


        Appointment appointment = appointmentService.createAppointment(services,customer,timeSlot);

        try {
            appointmentService.deleteAppointment(appointment);
        }
        catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }

        // AFTER DELETION
        assertEquals(null, appointmentService.getAppointment(appointment.getId()));
        // appointment is also removed from the customer side
        for (Appointment app : customer.getAppointments()){
            assertNotEquals(app.getId(),appointment.getId());
        }
    }

    /**
     * PRIVATE HELPERS
     */
    private void checkResultAppointment(Appointment appointment, List<BookableService> bookableServices,
                                        String customerEmail, Date appointmentDate,
                                        LocalTime startTime, LocalTime endTime) {
        assertNotNull(appointment);
        assertEquals(appointment.getServices().size(),bookableServices.size());
        for (BookableService b : appointment.getServices()){
            assertEquals(bookableServices.contains(b),true);
        }
        assertEquals(appointment.getCustomer().getEmail(),customerEmail);
        assertEquals(appointment.getTimeslot().getDate().toString(),appointmentDate.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        assertEquals(startTime.format(formatter).toString(), appointment.getTimeslot().getStartTime().toString());
        assertEquals(endTime.format(formatter).toString(), appointment.getTimeslot().getEndTime().toString());
    }

    private BookableService createTestService(){
        String serviceName = "WashCar";
        float serviceCost = 10;
        int serviceDuration = 60;
        return  repairShopService.createService(serviceName,serviceCost, serviceDuration, null);
    }

    private List<BookableService> createTestListServices(){
        List<BookableService> bookableServices = new ArrayList<>();
        bookableServices.add(createTestService());
        return bookableServices;
    }

    private Customer createTestCustomer(){
        // CREATING CUSTOMER
        String customerEmail = "ecse321@mtl.ca";
        String customerUsername = "Bob";
        String customerPassword = "abc123";
        return personService.createCustomer(customerEmail, customerUsername, customerPassword);
    }


}
