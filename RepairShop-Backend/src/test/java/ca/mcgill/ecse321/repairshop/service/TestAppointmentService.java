package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.AppointmentException;
import ca.mcgill.ecse321.repairshop.utility.BookableServiceException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
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
    private static final String CUSTOMER_ID = "mtl@mcgill.ca";
    private static final String CUSTOMER_ID_2 = "mtl@google.ca";
    private static final Long TIMESLOT_ID = 0L;
    private static final String NAME = "Fix tail light";
    private static final String SERVICE_NAME_2 = "Fix fog light";
    private static final Long Service_ID = 1l;
    private static final Long Service_ID_2 = 2L;
    private static final float COST = 12.00f;
    private static final int DURATION = 20;
    private static Long EXISTING_SERVICE_ID_2 = 3L;

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

        // findCustomerById
        lenient().when(customerDao.findCustomerByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(CUSTOMER_ID)) {
                Customer customer = new Customer();
                customer.setEmail(CUSTOMER_ID);
                return customer;
            }else if (invocation.getArgument(0).equals(CUSTOMER_ID_2)) {
                Customer customer = new Customer();
                customer.setEmail(CUSTOMER_ID_2);
                return customer;
            }
            else{
                return null;
            }
        });

        // findTimeSlotById
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

        // findServiceByName
        lenient().when(serviceDao.findServiceByName(anyString())).thenAnswer( (InvocationOnMock invocation) -> {

         

            if(invocation.getArgument(0).equals(NAME)) {

                BookableService service = new BookableService();
                service.setDuration(DURATION);
                service.setCost(COST);
                service.setId(Service_ID_2);
                return service;
            }
        else if (invocation.getArgument(0).equals(SERVICE_NAME_2)) {
                BookableService service = new BookableService();
                return service;
        }
            else{
                return null;
            }
        });

        lenient().when(serviceDao.findServiceById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(Service_ID)){
                BookableService bookableService = new BookableService();
                bookableService.setName(NAME);
                bookableService.setCost(COST);
                bookableService.setDuration(DURATION);
                bookableService.setId(Service_ID);
                return bookableService;
            }else if(invocation.getArgument(0).equals(Service_ID_2)) {
                BookableService bookableService = new BookableService();
                bookableService.setName(SERVICE_NAME_2);
                bookableService.setCost(COST);
                bookableService.setDuration(DURATION);
                bookableService.setId(Service_ID_2);
                return bookableService;
            }
            else{
                return null;
            }
        });

        // findByCustomer
        lenient().when(appointmentDao.findByCustomer(any())).thenAnswer( (InvocationOnMock invocation) -> {
                    if(invocation.getArgument(0) instanceof Customer){
                        Customer argumentCustomer = (Customer) invocation.getArgument(0);
                        if (argumentCustomer.getEmail() != null && argumentCustomer.getEmail().equals(CUSTOMER_ID) ||
                                argumentCustomer.getEmail().equals(CUSTOMER_ID_2)){
                            List<Appointment> appointments = new ArrayList<>();
                            Appointment appointment = new Appointment();
                            appointment.setCustomer(argumentCustomer);
                            appointment.setId(APPOINTMENT_KEY);
                            appointments.add(appointment);

                            return appointments;
                        }
                    }
                    return null;
        });

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
        timeSlot.setId(0L);

        List<BookableService> bookableServices = createTestListServices();
        Customer customer = createTestCustomer();
        Appointment appointment = null;
        try {
            appointment = appointmentService.createAppointment(bookableServices, customer, timeSlot);

        } catch (AppointmentException e) {
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
        assertEquals(0,personService.getAllCustomers().size());
        List<BookableService> services = null;
        assertEquals(0,repairShopService.getAllService().size());
        TimeSlot timeSlot = null;
        assertEquals(0,timeSlotService.getAllTimeSlot().size());
        try {
            appointment = appointmentService.createAppointment(services,customer,timeSlot);
        } catch (AppointmentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        // check error
        assertEquals("Customer, services and timeslot must all be selected for the appointment!", error);
    }

    // NEGATIVE TEST
    @Test
    public void testAppointmentCustomerAndTimeSlotDoNotExist() {
        // CREATING A CUSTOMER WITHOUT SAVING IT TO DATABASE
        String email = "123@mcgill.ca";
        Customer customer = new Customer();
        customer.setEmail(email);
        assertEquals(0, personService.getAllCustomers().size());

        // CREATING A WORKING SERVICE
        List<BookableService> services = createTestListServices();

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

        } catch (AppointmentException e) {
            error = e.getMessage();
        }

        assertNull(appointment);
        // check error
        assertEquals("Customer, Timeslot don't exist!", error);
    }

    // NEGATIVE TEST
    @Test
    public void testAppointmentServiceDoNotExist() {
        // CREATING A WORKABLE CUSTOMER
        Customer customer = createTestCustomer();

        // CREATING A WORKABLE TIMESLOT
        Calendar c = Calendar.getInstance();
        c.set(2021, Calendar.MAY, 1, 9, 0, 0);
        Date appointmentDate = new Date(c.getTimeInMillis());
        LocalTime startTime = LocalTime.parse("09:00");
        c.set(2021, Calendar.MAY, 1, 10, 0, 0);
        LocalTime endTime = LocalTime.parse("10:00");
        TimeSlot timeSlot = timeSlotService.createTimeSlot(appointmentDate,
                Time.valueOf(startTime), Time.valueOf(endTime));
        timeSlot.setId(0L);

        // CREATING A SERVICE WITHOUT SAVING
        BookableService service = new BookableService();
        service.setName("fake service");
        List<BookableService> services = new ArrayList<>();
        services.add(service);
        assertEquals(0, repairShopService.getAllService().size());

        String error = null;
        Appointment appointment= null;
        try {
            appointment = appointmentService.createAppointment(services,customer,timeSlot);

        } catch (AppointmentException e) {
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

        Customer customer = createTestCustomer();

        try {
            assertNotNull(appointmentService.getAppointmentsBookedByCustomer(customer));
            assertEquals(1, appointmentService.getAppointmentsBookedByCustomer(customer).size());
            assertEquals(APPOINTMENT_KEY, appointmentService.getAppointmentsBookedByCustomer(customer).get(0).getId());
        }
        catch (AppointmentException e){
            e.printStackTrace();
            fail();
        }


    }
    // NEGATIVE TEST
    @Test
    public void testGetAppointmentByNullCustomer() {
        // CUSTOMER IS NOT SAVED
        Customer customer = null;
        String error = null;
        List<Appointment> appointments=null;
        try{appointments = appointmentService.getAppointmentsBookedByCustomer(customer);}
        catch (AppointmentException e){
            error = e.getMessage();
        }
        assertNull(appointments);
        assertEquals(error, "customer cannot be null");
    }

    // NEGATIVE TEST
    @Test
    public void testGetAppointmentByNonExistingCustomer() {
       try {
           // CUSTOMER IS NOT SAVED
           Customer customer = new Customer();
           customer.setEmail("mcgill@ca.com");
           assertNull(appointmentService.getAppointmentsBookedByCustomer(customer));
       }
       catch (AppointmentException e){
          fail();
       }
    }

    /**
     * TESTING editAppointment
     */
    // POSITIVE TEST
    @Test
    public void testEditAppointment(){
        try {
        /**
         * The original Appointment
         */
        //CREATING TIMESLOT
        Calendar c = Calendar.getInstance();
        c.set(2021, Calendar.MAY, 1, 9, 0, 0);
        Date appointmentDate = new Date(c.getTimeInMillis());
        LocalTime startTime = LocalTime.parse("09:00");
        c.set(2021, Calendar.MAY, 1, 10, 0, 0);
        LocalTime endTime = LocalTime.parse("10:00");
        TimeSlot timeSlot = timeSlotService.createTimeSlot(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));
        timeSlot.setId(0L);

        List<BookableService> bookableServices = createTestListServices();
        Customer customer = createTestCustomer();
         /**
         * Edited appointment
         */
        //CREATING NEW TIMESLOT
        Calendar c_new = Calendar.getInstance();
        c_new.set(2021, Calendar.MAY, 3, 9, 0, 0);
        Date appointmentDate_new = new Date(c.getTimeInMillis());
        LocalTime startTime_new = LocalTime.parse("09:00");
        c_new.set(2021, Calendar.MAY, 3, 10, 0, 0);
        LocalTime endTime_new = LocalTime.parse("10:00");
        TimeSlot timeSlot_new = timeSlotService.createTimeSlot(appointmentDate_new, Time.valueOf(startTime_new),
                Time.valueOf(endTime_new));
        timeSlot_new.setId(0L);

        String serviceName1 = "RepairWindows";
        float serviceCost1 = 20;
        int serviceDuration1 = 120;
        BookableService service1 = repairShopService.createService(serviceName1,serviceCost1,serviceDuration1);


        List<BookableService> bookableServices_new = new ArrayList<>();
        bookableServices_new.add(service1);

            Appointment appointment = appointmentService.createAppointment(bookableServices, customer, timeSlot);
            appointmentService.editAppointment(appointment, bookableServices_new, timeSlot);
            checkResultAppointment( appointment, bookableServices_new, CUSTOMER_ID_2, timeSlot_new.getDate(),
                    timeSlot_new.getStartTime().toLocalTime(), timeSlot_new.getEndTime().toLocalTime());

        }
        catch (AppointmentException e){
            e.printStackTrace();
            fail();
        }
        catch (BookableServiceException e){
            e.printStackTrace();
            fail();
        }

    }
    // NEGATIVE TEST
    @Test
    public void testEditAppointmentNegative(){
        /**
         * The original Appointment
         */
        //CREATING TIMESLOT
        Calendar c = Calendar.getInstance();
        c.set(2021, Calendar.MAY, 1, 9, 0, 0);
        Date appointmentDate = new Date(c.getTimeInMillis());
        LocalTime startTime = LocalTime.parse("09:00");
        c.set(2021, Calendar.MAY, 1, 10, 0, 0);
        LocalTime endTime = LocalTime.parse("10:00");
        TimeSlot timeSlot = timeSlotService.createTimeSlot(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));
        timeSlot.setId(0L);

        List<BookableService> bookableServices = createTestListServices();
        Customer customer = createTestCustomer();

        String error = null;
        try {
            Appointment appointment = appointmentService.createAppointment(bookableServices, customer, timeSlot);
            appointmentService.editAppointment(appointment,null,null);
        }
        catch (AppointmentException e){
            error = e.getMessage();
            assertEquals("The Appointment must have at least one services",error);
        }

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
        catch (AppointmentException e){
            error = e.getMessage();
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
        timeSlot.setId(TIMESLOT_ID);
        List<BookableService> services = createTestListServices();
        Customer customer = createTestCustomer();

        try {
            Appointment appointment = appointmentService.createAppointment(services,customer,timeSlot);
            appointment.setId(10L);
            appointmentService.deleteAppointment(appointment);
            // AFTER DELETION
            assertNull(appointmentService.getAppointment(appointment.getId()));
        }
        catch (AppointmentException e) {
            // Check that no error occurred
            e.printStackTrace();
            fail();
        }

    }

    /**
     * test enter no-show
     */

    @Test
    public void testWrongEnterNoShow(){
        LocalDate dateToday = LocalDate.now();
        Date appointmentDate = Date.valueOf(dateToday);
        LocalTime startTime = LocalTime.now().minusMinutes(12);
        LocalTime endTime = LocalTime.now().plusMinutes(50);

        TimeSlot timeSlot = timeSlotService.createTimeSlotforTestingNoShow(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));
        timeSlot.setId(TIMESLOT_ID);
        List<BookableService> services = createTestListServices();
        Customer customer = createTestCustomer();



        String error = null;

        try{
            Appointment appointment = appointmentService.createAppointment(services,customer,timeSlot);
            appointmentService.enterNoShow(appointment);
        }catch (AppointmentException e){
            error = e.getMessage();
            assertEquals(error, "Cannot enter no show at this time");
        }

    }

    @Test
    public void testEnterNoShowtoNullAppointment() {

        Appointment appointment = null;
        String error = null;

        try {
            appointmentService.enterNoShow(appointment);
        } catch (AppointmentException e) {
            error = e.getMessage();
            assertEquals(error, "appointment cannot be null");
        }
    }

        @Test
        public void testEnterNoShow(){
        LocalDate dateToday = LocalDate.now();
        Date appointmentDate = Date.valueOf(dateToday);
        LocalTime startTime = LocalTime.now().minusMinutes(22);
        LocalTime endTime = LocalTime.now().plusMinutes(50);

        TimeSlot timeSlot = timeSlotService.createTimeSlotforTestingNoShow(appointmentDate, Time.valueOf(startTime), Time.valueOf(endTime));
        timeSlot.setId(TIMESLOT_ID);
        List<BookableService> services = createTestListServices();
        Customer customer = createTestCustomer();

        String error = null;

        try{
            Appointment appointment = appointmentService.createAppointment(services,customer,timeSlot);
            appointmentService.enterNoShow(appointment);
        }catch (AppointmentException e){
            error = e.getMessage();
            assertEquals(error, null);

        }




    }


    /**
     * PRIVATE HELPERS
     */
    private void checkResultAppointment(Appointment appointment, List<BookableService> bookableServices,
                                        String customerID, Date appointmentDate,
                                        LocalTime startTime, LocalTime endTime) {
        assertNotNull(appointment);
        assertEquals(appointment.getServices().size(),bookableServices.size());
        int totalCost=0;
        for (BookableService b : appointment.getServices()){
            totalCost += b.getCost();
            assertEquals(bookableServices.contains(b),true);
        }
        assertEquals(appointment.getCustomer().getEmail(),customerID);
        assertEquals(appointment.getTimeslot().getDate().toString(),appointmentDate.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        assertEquals(startTime.format(formatter).toString(), appointment.getTimeslot().getStartTime().toString());
        assertEquals(endTime.format(formatter).toString(), appointment.getTimeslot().getEndTime().toString());
        assertEquals(appointment.getBill().getTotalCost(),totalCost);
        assertEquals(appointment.getBill().getDate(),appointment.getTimeslot().getDate());

    }

    private BookableService createTestService(){
        try {

                String serviceName = SERVICE_NAME_2;
                BookableService createdService = repairShopService.createService("Place holder", COST, DURATION);
                createdService.setName(serviceName);
                createdService.setId(EXISTING_SERVICE_ID_2);

            return createdService;
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
        Customer customer = new Customer();                 // create customer
        String username = "johndoe007";
        String password = "password" ;
        String cardNumber = "1234567890123456";
        String cvv = "123";
        int noShow = 1;
        customer.setEmail(CUSTOMER_ID_2);
        customer.setCardNumber(cardNumber);
        customer.setCvv(cvv);
        customer.setUsername(username);
        customer.setNoShow(noShow);
        customer.setPassword(password);
        return customer;
    }
}
