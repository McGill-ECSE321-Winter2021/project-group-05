package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.dto.BusinessDto;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.AppointmentException;
import ca.mcgill.ecse321.repairshop.utility.BookableServiceException;
import ca.mcgill.ecse321.repairshop.utility.BusinessException;
import ca.mcgill.ecse321.repairshop.utility.PersonException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestRepairShopService {
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private TimeSlotRepository timeSlotRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private AdministratorRepository administratorRepository;
    @Mock
    private TechnicianRepository technicianRepository;
    @Mock
    private BusinessRepository businessRepository;

    @InjectMocks
    private RepairShopService repairShopService;
    @InjectMocks
    private TimeSlotService timeSlotService;
    @InjectMocks
    private PersonService personService;
    @InjectMocks
    private AppointmentService appointmentService;
    @InjectMocks
    private BusinessService businessService;

    private static final String NAME = "TestService";
    private static final String SERVICE_NAME_2 = "Fix fog light";
    private static final String NON_EXISTING_SERVICE = "Fix tail light";
    private static final float COST = 21.3f;
    private static final int DURATION = 10;
    private static final Long EXISTING_SERVICE_ID = 2L;
    private static final Long EXISTING_SERVICE_ID_2 = 3L;
    private static final Long NON_EXISTING_SERVICE_ID = 4L;

    private static final Long TIMESLOT_ID = 0L;
    private static final Long BUSINESS_ID = 0L;
    private static final String CUSTOMER_ID = "johndoe@mail.mcgill.ca";
    private static final String CUSTOMER_ID_2 = "anna-taylorjoy@mcgill.ca";
    private static final Long APPOINTMENT_ID = 0L;

    @BeforeEach
    public void setMockOutPut(){
        lenient().when(serviceRepository.findServiceByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(NAME)){
                BookableService bookableService = new BookableService();
                bookableService.setName(NAME);
                bookableService.setCost(COST);
                bookableService.setDuration(DURATION);
                bookableService.setId(EXISTING_SERVICE_ID);
                return bookableService;
            } else if(invocation.getArgument(0).equals(SERVICE_NAME_2)){
                BookableService bookableService = new BookableService();
                bookableService.setName(SERVICE_NAME_2);
                bookableService.setCost(COST);
                bookableService.setDuration(DURATION);
                bookableService.setId(EXISTING_SERVICE_ID_2);
                return bookableService;
            }
            else{
                return null;
            }
        });

        lenient().when(serviceRepository.findServiceById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(EXISTING_SERVICE_ID)){
                BookableService bookableService = new BookableService();
                bookableService.setName(NAME);
                bookableService.setCost(COST);
                bookableService.setDuration(DURATION);
                bookableService.setId(EXISTING_SERVICE_ID);
                return bookableService;
            }else if(invocation.getArgument(0).equals(EXISTING_SERVICE_ID_2)) {
                BookableService bookableService = new BookableService();
                bookableService.setName(SERVICE_NAME_2);
                bookableService.setCost(COST);
                bookableService.setDuration(DURATION);
                bookableService.setId(EXISTING_SERVICE_ID_2);
                return bookableService;
            }
            else{
                return null;
            }
        });

        // getAllService
        lenient().when(serviceRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
            List<BookableService> services = new ArrayList<>();
            BookableService bookableService = new BookableService();
            bookableService.setName(NAME);
            bookableService.setCost(COST);
            bookableService.setDuration(DURATION);
            bookableService.setId(EXISTING_SERVICE_ID);
            services.add(bookableService);
            return services;
        });

        // getAllAppointments
        lenient().when(appointmentRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
            List<BookableService> services = new ArrayList<>();
            BookableService bookableService = new BookableService();
            bookableService.setName(NAME);
            bookableService.setCost(COST);
            bookableService.setDuration(DURATION);
            bookableService.setId(EXISTING_SERVICE_ID);
            services.add(bookableService);
            Customer customer = new Customer();
            customer.setEmail(CUSTOMER_ID);
            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setDate(Date.valueOf("2021-06-27"));
            timeSlot.setStartTime(Time.valueOf("10:00:00"));
            timeSlot.setEndTime(Time.valueOf("12:00:00"));
            timeSlot.setId(TIMESLOT_ID);
            List <Appointment> appointments = new ArrayList<>();
            Appointment appointment = new Appointment();
            appointment.setCustomer(customer);
            appointment.setServices(services);
            appointment.setTimeslot(timeSlot);
            appointment.setId(APPOINTMENT_ID);
            appointments.add(appointment);

            return appointments;
        });

        // findCustomerByEmail
        lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(CUSTOMER_ID)) {
                Customer customer = new Customer();
                customer.setEmail(CUSTOMER_ID);
                return customer;
            } else if(invocation.getArgument(0).equals(CUSTOMER_ID_2)) {
                Customer customer = new Customer();
                customer.setEmail(CUSTOMER_ID_2);
                return customer;
            }
            else{
                return null;
            }
        });

        // findAppointmentById
        lenient().when(appointmentRepository.findAppointmentById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(APPOINTMENT_ID)) {
                Appointment appointment = new Appointment();
                appointment.setId(APPOINTMENT_ID);
                return appointment;
            }
            else{
                return null;
            }
        });

        // findTimeSlotById
        lenient().when(timeSlotRepository.findTimeSlotById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(TIMESLOT_ID)) {
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setId(TIMESLOT_ID);
                return timeSlot;
            }
            else{
                return null;
            }
        });

        // findBusinessById
        lenient().when(businessRepository.findBusinessById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(BUSINESS_ID)) {
                Business business = new Business();
                business.setId(BUSINESS_ID);
                return business;
            }
            else{
                return null;
            }
        });

        // findByCustomer
        lenient().when(appointmentRepository.findByCustomer(any())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0) instanceof Customer){
                Customer argumentCustomer = (Customer) invocation.getArgument(0);
                if (argumentCustomer.getEmail() != null && argumentCustomer.getEmail().equals(CUSTOMER_ID)){
                    List<Appointment> appointments = new ArrayList<>();
                    Appointment appointment = new Appointment();
                    appointment.setCustomer(argumentCustomer);
                    appointment.setId(APPOINTMENT_ID);
                    appointments.add(appointment);

                    return appointments;
                }
            }
            return null;
        });


        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(serviceRepository.save(any(BookableService.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(appointmentRepository.save(any(Appointment.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(businessRepository.save(any(Business.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(ownerRepository.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(administratorRepository.save(any(Administrator.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(technicianRepository.save(any(Technician.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(timeSlotRepository.save(any(TimeSlot.class))).thenAnswer(returnParameterAsAnswer);
    }

    /**
     * test createService(name, cost, duration); POSITIVE
     */
    @Test
    public void testCreateServiceSuccessfully(){
        try{
            String serviceName = NAME;
            BookableService createdService = repairShopService.createService("Place holder", COST, DURATION);
            createdService.setName(serviceName);
            createdService.setId(EXISTING_SERVICE_ID);
            createdService = null;
            createdService = serviceRepository.findServiceById(EXISTING_SERVICE_ID);

            assertNotNull(createdService);
            assertEquals(serviceName, createdService.getName());
            assertEquals(COST, createdService.getCost());
            assertEquals(DURATION, createdService.getDuration());
            assertEquals(EXISTING_SERVICE_ID, createdService.getId());
        }
        catch (BookableServiceException e){
            fail();
        }

    }

    /**
     * test createService(name, cost, duration); error check; name = null
     */
    @Test
    public void testCreateServiceWithNullName(){

        String error = null;
        BookableService createdService = null;

        try {
            createdService = repairShopService.createService(null, COST, DURATION);
            createdService = null;
            createdService = serviceRepository.findServiceByName(NAME);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }

        assertEquals("Service name cannot be empty", error);
    }


    /**
     * test createService(name, cost, duration); error check; name = "  "
     */
    @Test
    public void testCreateServiceWithEmptyName(){

        String error = null;
        BookableService createdService = null;

        try {
            createdService = repairShopService.createService("    ", COST, DURATION);
            createdService = null;
            createdService = serviceRepository.findServiceByName(NAME);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }
        assertEquals("Service name cannot be empty", error);
    }


    /**
     * test createService(name, cost, duration); error check; negative cost
     */
    @Test
    public void testCreateServiceWithNegativeCost(){

        String error = null;
        BookableService createdService = null;

        try {
            createdService = repairShopService.createService(NAME, -85.99f, DURATION);
            createdService = null;
            createdService = serviceRepository.findServiceByName(NAME);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }
        assertEquals("Service cost cannot be negative", error);
    }


    /**
     * test createService(name, cost, duration); error check; duration = 0
     */
    @Test
    public void testCreateServiceWithZeroDuration(){
        String NAME = "Steering wheel repair";
        float COST = 85.98f;
        int DURATION = 0;
        String error = null;
        BookableService createdService = null;

        try {
            createdService = repairShopService.createService(NAME, COST, DURATION);
            createdService = null;
            createdService = serviceRepository.findServiceByName(NAME);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }
        assertEquals("Service duration cannot be 0", error);
    }

    /**
     * test createService(name, cost, duration); error check; service already exists
     */
    @Test
    public void testCreateServiceThatAlreadyExists(){
        String error = null;
        try{
            String serviceName = NAME;
            BookableService createdService = repairShopService.createService(serviceName, COST, DURATION);
            createdService.setId(EXISTING_SERVICE_ID);
        }
        catch (BookableServiceException e){
            error = e.getMessage();
        }
        assertEquals("Service already exists", error);
    }

    /**
     * test editService(service, newName, newCost, newDuration); POSITIVE
     */
    @Test
    public void testEditServiceSuccessfully() {
        try {
            String OLD_NAME = NAME;
            float OLD_COST = 29.79f;
            int OLD_DURATION = 12;
            BookableService service = repairShopService.createService("Place holder", OLD_COST, OLD_DURATION);
            service.setName(OLD_NAME);
            service.setId(EXISTING_SERVICE_ID);

            String NEW_NAME = "Sound system repair";
            float NEW_COST = 19.79f;
            int NEW_DURATION = 14;

            BookableService editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);

            assertNotNull(editedService);
            assertEquals(NEW_NAME, editedService.getName());
            assertEquals(NEW_COST, editedService.getCost());
            assertEquals(NEW_DURATION, editedService.getDuration());
            assertEquals(EXISTING_SERVICE_ID, editedService.getId());
        }
        catch (BookableServiceException e){
            e.printStackTrace();
            fail();
        }
    }

    /**
     * test editService(service, newName, newCost, newDuration); error check; service = null
     */
    @Test
    public void testEditServiceWithNullService() {
        String OLD_NAME = "Sound system repair";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = null;
        try {
            service = repairShopService.createService(OLD_NAME, OLD_COST, OLD_DURATION);
        } catch (BookableServiceException e) {
            e.printStackTrace();
            fail();
        }
        service.setId(5l);

        service = null;

        String NEW_NAME = "Indicator light repair";
        float NEW_COST = 59.79f;
        int NEW_DURATION = 16;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }

        assertEquals("Please select a service that you want to modify", error);
    }


    /**
     * test editService(service, newName, newCost, newDuration); error check; newName = "   "
     */
    @Test
    public void testEditServiceWithEmptyNewServiceName() {
        String OLD_NAME = "Sound system repair";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = null;
        try {
            service = repairShopService.createService(OLD_NAME, OLD_COST, OLD_DURATION);
        } catch (BookableServiceException e) {
            e.printStackTrace();
            fail();
        }
        service.setId(5l);


        String NEW_NAME = "   ";
        float NEW_COST = 59.79f;
        int NEW_DURATION = 16;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }

        assertEquals("New service name cannot be empty", error);
    }


    /**
     * test editService(service, newName, newCost, newDuration); error check; newName = null
     */
    @Test
    public void testEditServiceWithNullNewServiceName() {
        String OLD_NAME = "Sound system repair";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = null;
        try {
            service = repairShopService.createService(OLD_NAME, OLD_COST, OLD_DURATION);
        } catch (BookableServiceException e) {
            fail();
        }
        service.setId(5l);


        String NEW_NAME = null;
        float NEW_COST = 59.79f;
        int NEW_DURATION = 16;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }

        assertEquals("New service name cannot be empty", error);
    }

    /**
     * test editService(service, newName, newCost, newDuration); error check; negative cost
     */
    @Test
    public void testEditServiceWithNegativeNewCost() {
        String OLD_NAME = "Sound system repair";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = null;
        try {
            service = repairShopService.createService(OLD_NAME, OLD_COST, OLD_DURATION);
        } catch (BookableServiceException e) {
            fail();
        }
        service.setId(5l);


        String NEW_NAME = "Indicator light repair";
        float NEW_COST = -59.79f;
        int NEW_DURATION = 16;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }

        assertEquals("New service cost cannot be negative", error);
    }

    /**
     * test editService(service, newName, newCost, newDuration); error check; newDuration = 0
     */
    @Test
    public void testEditServiceWithZeroNewDuration() {
        String OLD_NAME = "Sound system repair";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = null;
        try {
            service = repairShopService.createService(OLD_NAME, OLD_COST, OLD_DURATION);
        } catch (BookableServiceException e) {
            e.printStackTrace();
            fail();
        }
        service.setId(5l);


        String NEW_NAME = "Indicator light repair";
        float NEW_COST = 59.79f;
        int NEW_DURATION = 0;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }

        assertEquals("New service duration cannot be 0", error);
    }


    /**
     * test editService(service, newName, newCost, newDuration); error check; new service name already exists
     */
    @Test
    public void testEditServiceWithNewNameThatAlreadyExists() {
        String error = null;
        try {
            String OLD_NAME = NAME;
            float OLD_COST = 29.79f;
            int OLD_DURATION = 12;
            BookableService service = repairShopService.createService("Place holder", OLD_COST, OLD_DURATION);
            service.setName(OLD_NAME);
            service.setId(EXISTING_SERVICE_ID);

            String NEW_NAME = SERVICE_NAME_2;
            float NEW_COST = 19.79f;
            int NEW_DURATION = 14;

            BookableService editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);

            assertNotNull(editedService);
            assertEquals(NEW_NAME, editedService.getName());
            assertEquals(NEW_COST, editedService.getCost());
            assertEquals(NEW_DURATION, editedService.getDuration());
            assertEquals(EXISTING_SERVICE_ID, editedService.getId());
        }
        catch (BookableServiceException e){
            error = e.getMessage();
        }

        assertEquals(error, "Service already exists");
    }

    /**
     * test deleteService(service); POSITIVE
     */
    @Test
    public void testDeleteServiceSuccessfully() {
        RepairShop repairShop = new RepairShop();

        Date date = Date.valueOf("2021-05-01");         // create timeSlot
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("12:00:00");
        TimeSlot timeSlot = timeSlotService.createTimeSlot(date, startTime, endTime);
        timeSlot.setId(TIMESLOT_ID);
        timeSlot.setDate(Date.valueOf("2021-02-01"));
        timeSlot.setRepairShop(repairShop);


        String name = "Demo business";                  // create business
        String address = "365 Sherbrooke";
        String phoneNumber = "514-123-4567";
        String businessEmail = "123@repairshop.ca";
        BusinessDto businessDto = new BusinessDto(name, address, phoneNumber, businessEmail, BUSINESS_ID);
        Business business = null;
        try {
            business = businessService.createBusiness(businessDto);
        } catch (BusinessException e) {

        }
        business.setName(name);
        business.setAddress(address);
        business.setEmail(businessEmail);
        business.setPhoneNumber(phoneNumber);
        business.setId(BUSINESS_ID);
        business.setRepairShop(repairShop);


        String username = "johndoe007";                  // create customer
        String password = "password" ;
        String cardNumber = "1234567890123456";
        String cvv = "123";
        Date expiry = Date.valueOf("2023-06-27");
        int noShow = 1;
        Customer customer = null;
        try {
            customer = personService.createCustomer("123@placeholder.com", username, password);
        } catch (PersonException e) {
            fail();
        }
        customer.setEmail(CUSTOMER_ID);
        customer.setCardNumber(cardNumber);
        customer.setCvv(cvv);
        customer.setUsername(username);
        customer.setNoShow(noShow);
        customer.setPassword(password);
        customer.setExpiry(expiry);
        customer.setRepairShop(repairShop);

        List<BookableService> services = new ArrayList<>();
        BookableService service = null;
        try {
            service = repairShopService.createService("Place holder", COST, DURATION);
            service.setName(SERVICE_NAME_2);
            service.setId(10l);

        } catch (BookableServiceException e) {
            fail();
        }

        service.setRepairShop(repairShop);
        services.add(service);


        Appointment appointment1 = null;                          // create appointment
        try {
            appointment1 = appointmentService.createAppointment(services, customer, timeSlot);
        } catch (AppointmentException e) {
            fail();
        }
        appointment1.setServices(services);
        appointment1.setCustomer(customer);
        appointment1.setTimeslot(timeSlot);
        appointment1.setId(APPOINTMENT_ID);
        appointment1.setRepairShop(repairShop);

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment1);


        repairShop.setServices(services);
        repairShop.setAppointments(appointments);

        try {
            repairShopService.deleteBookableService(service);
            assertNull(repairShopService.getService(service.getId()));
        } catch (BookableServiceException e) {
            e.printStackTrace();
            fail();
        }


    }

    /**
     * test deleteService(service); error check; service does not exist
     */
    @Test
    public void testDeleteServiceThatDoesNotExist() {
        String error = null;
        try{
            RepairShop repairShop = new RepairShop();

            TimeSlot timeSlot = new TimeSlot();                 // create new TimeSlot
            Date date = Date.valueOf("2021-03-14");
            Time startTime = Time.valueOf("10:00:00");
            Time endTime = Time.valueOf("12:00:00");
            timeSlot.setDate(date);
            timeSlot.setStartTime(startTime);
            timeSlot.setEndTime(endTime);
            timeSlot.setId(TIMESLOT_ID);
            timeSlot.setRepairShop(repairShop);

            Business business = new Business();                 // create business
            String name = "Demo business";
            String address = "365 Sherbrooke";
            String phoneNumber = "514-123-4567";
            String businessEmail = "123@repairshop.ca";
            business.setName(name);
            business.setAddress(address);
            business.setEmail(businessEmail);
            business.setPhoneNumber(phoneNumber);
            business.setId(BUSINESS_ID);
            business.setRepairShop(repairShop);

            Customer customer = new Customer();                 // create customer
            String customerEmail = "johndoe@mail.mcgill.ca";
            String username = "johndoe007";
            String password = "password" ;
            String cardNumber = "1234567890123456";
            String cvv = "123";
            Date expiry = Date.valueOf("2023-06-27");
            int noShow = 1;
            customer.setEmail(customerEmail);
            customer.setCardNumber(cardNumber);
            customer.setCvv(cvv);
            customer.setUsername(username);
            customer.setNoShow(noShow);
            customer.setPassword(password);
            customer.setExpiry(expiry);

            customer.setRepairShop(repairShop);


            String serviceName = "Sound system repair";             // create service
            float serviceCost = 55.89f;
            int serviceDuration = 18;
            BookableService service1 = repairShopService.createService(serviceName, COST, DURATION);
            service1.setName(serviceName);
            service1.setCost(serviceCost);
            service1.setDuration(serviceDuration);
            service1.setRepairShop(repairShop);


            Appointment appointment1 = new Appointment();           // create appointment
            List<BookableService> services = new ArrayList<>();
            services.add(service1);
            appointment1.setServices(services);
            appointment1.setCustomer(customer);
            appointment1.setTimeslot(timeSlot);
            appointment1.setId(4L);
            appointment1.setRepairShop(repairShop);


            List<TimeSlot> timeSlots = new ArrayList<>();
            timeSlots.add(timeSlot);

            List<Person> persons = new ArrayList<>();
            persons.add(customer);

            List<Appointment> appointments = new ArrayList<>();
            appointments.add(appointment1);

            repairShop.setAppointments(appointments);
            repairShop.setPersons(persons);
            repairShop.setServices(services);
            repairShop.setTimeSlots(timeSlots);
            repairShop.setId(6l);
            repairShop.setBusiness(business);
            service1.setRepairShop(repairShop);
            repairShopService.deleteBookableService(service1);
        } catch(BookableServiceException e) {
            error = e.getMessage();
        }
        assertEquals("Service does not exist", error);

    }

    /**
     * test deleteService(service); error check; service = null
     */
    @Test
    public void testDeleteServiceWithInvalidInput() {
        BookableService bookableService = null;

        try {
            repairShopService.deleteBookableService(bookableService);
        } catch (BookableServiceException e) {
            assertEquals("Cannot delete a service that does not exist", e.getMessage());
        }

    }

    /**
     * test deleteService(service); error check; service with future appointments
     */
    @Test
    public void testDeleteServiceWithFutureAppointments() {
        RepairShop repairShop = new RepairShop();

        Date date = Date.valueOf("2021-06-27");                 // create TimeSlot
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("12:00:00");
        TimeSlot timeSlot = timeSlotService.createTimeSlot(date, startTime, endTime);
        timeSlot.setId(TIMESLOT_ID);
        timeSlot.setRepairShop(repairShop);

        String name = "Demo business";                          // create business
        String address = "365 Sherbrooke";
        String phoneNumber = "514-123-4567";
        String businessEmail = "123@repairshop.ca";
        BusinessDto businessDto = new BusinessDto(name, address, phoneNumber, businessEmail, BUSINESS_ID);
        Business business = null;
        try {
            business = businessService.createBusiness(businessDto);
        } catch (BusinessException e) {

        }
        business.setName(name);
        business.setAddress(address);
        business.setEmail(businessEmail);
        business.setPhoneNumber(phoneNumber);
        business.setId(BUSINESS_ID);
        business.setRepairShop(repairShop);

        String username = "johndoe007";                     // create customer
        String password = "password" ;
        String cardNumber = "1234567890123456";
        String cvv = "123";
        Date expiry = Date.valueOf("2023-06-27");
        int noShow = 1;
        Customer customer = null;
        customer = new Customer();
        try {
            customer = personService.createCustomer("example@placeholder.com", username, password);
        } catch (PersonException e) {
            fail();
        }
        customer.setEmail(CUSTOMER_ID);
        customer.setCardNumber(cardNumber);
        customer.setCvv(cvv);
        customer.setUsername(username);
        customer.setNoShow(noShow);
        customer.setPassword(password);
        customer.setExpiry(expiry);
        customer.setRepairShop(repairShop);

        List<BookableService> services = new ArrayList<>();
        BookableService service = null;
        try {
            service = repairShopService.createService("Place holder", COST, DURATION);
            service.setName(NAME);
            service.setId(EXISTING_SERVICE_ID);

        } catch (BookableServiceException e) {
            fail();
        }

        service.setRepairShop(repairShop);
        services.add(service);


        Appointment appointment1 = null;                    // create appointment
        String error = null;
        try {
            appointment1 = appointmentService.createAppointment(services, customer, timeSlot);
        } catch (AppointmentException e) {
            fail();
        }
        appointment1.setServices(services);
        appointment1.setCustomer(customer);
        appointment1.setTimeslot(timeSlot);
        appointment1.setId(APPOINTMENT_ID);
        appointment1.setRepairShop(repairShop);

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment1);


        repairShop.setServices(services);
        repairShop.setAppointments(appointments);

        try {
            repairShopService.deleteBookableService(service);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }
        assertEquals("Cannot delete a service which still has future appointments", error);
    }


    /**
     * test deleteService(service); error check; service with same-day appointments
     */
    @Test
    public void testDeleteServiceWithAppointmentsOnSameDay() {
        RepairShop repairShop = new RepairShop();

        Date date = Date.valueOf("2021-06-27");                 // create TimeSlot
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("12:00:00");
        TimeSlot timeSlot = timeSlotService.createTimeSlot(date, startTime, endTime);
        timeSlot.setDate(Date.valueOf(LocalDate.now()));
        timeSlot.setId(TIMESLOT_ID);
        timeSlot.setRepairShop(repairShop);

        String name = "Demo business";                          // create business
        String address = "365 Sherbrooke";
        String phoneNumber = "514-123-4567";
        String businessEmail = "123@repairshop.ca";
        BusinessDto businessDto = new BusinessDto(name, address, phoneNumber, businessEmail, BUSINESS_ID);
        Business business = null;
        try {
            business = businessService.createBusiness(businessDto);
        } catch (BusinessException e) {

        }
        business.setName(name);
        business.setAddress(address);
        business.setEmail(businessEmail);
        business.setPhoneNumber(phoneNumber);
        business.setId(BUSINESS_ID);
        business.setRepairShop(repairShop);

        String username = "johndoe007";                     // create customer
        String password = "password" ;
        String cardNumber = "1234567890123456";
        String cvv = "123";
        Date expiry = Date.valueOf("2023-06-27");
        int noShow = 1;
        Customer customer = null;
        customer = new Customer();
        try {
            customer = personService.createCustomer("example@placeholder.com", username, password);
        } catch (PersonException e) {
            fail();
        }
        customer.setEmail(CUSTOMER_ID);
        customer.setCardNumber(cardNumber);
        customer.setCvv(cvv);
        customer.setUsername(username);
        customer.setNoShow(noShow);
        customer.setPassword(password);
        customer.setExpiry(expiry);
        customer.setRepairShop(repairShop);

        List<BookableService> services = new ArrayList<>();
        BookableService service = null;
        try {
            service = repairShopService.createService("Place holder", COST, DURATION);
            service.setName(NAME);
            service.setId(EXISTING_SERVICE_ID);

        } catch (BookableServiceException e) {
            fail();
        }

        service.setRepairShop(repairShop);
        services.add(service);


        Appointment appointment1 = null;                          // create appointment
        String error = null;
        try {
            appointment1 = appointmentService.createAppointment(services, customer, timeSlot);
        } catch (AppointmentException e) {
            fail();
        }
        appointment1.setServices(services);
        appointment1.setCustomer(customer);
        appointment1.setTimeslot(timeSlot);
        appointment1.setId(APPOINTMENT_ID);
        appointment1.setRepairShop(repairShop);

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment1);


        repairShop.setServices(services);
        repairShop.setAppointments(appointments);

        try {
            repairShopService.deleteBookableService(service);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }
        assertEquals("Cannot delete a service which still has future appointments", error);
    }


    /**
     * test getService(Long Id); POSITIVE
     */
    @Test
    public void testGetServiceByIDForExistingService() {
        assertEquals(EXISTING_SERVICE_ID, repairShopService.getService(EXISTING_SERVICE_ID).getId());
        assertEquals(NAME, repairShopService.getService(EXISTING_SERVICE_ID).getName());
        assertEquals(COST, repairShopService.getService(EXISTING_SERVICE_ID).getCost());
        assertEquals(DURATION, repairShopService.getService(EXISTING_SERVICE_ID).getDuration());
    }
    /**
     * test getService(Long Id); NEGATIVE
     */
    @Test
    public void testGetServiceByIdForNonExistingService() {
        assertNull(repairShopService.getService(NON_EXISTING_SERVICE_ID));
    }

    /**
     * test getService(String name); POSITIVE
     */
    @Test
    public void testGetServiceByNameForExistingService() {
        assertEquals(NAME, repairShopService.getService(NAME).getName());
        assertEquals(EXISTING_SERVICE_ID, repairShopService.getService(NAME).getId());
        assertEquals(COST, repairShopService.getService(NAME).getCost());
        assertEquals(DURATION, repairShopService.getService(NAME).getDuration());
    }
    /**
     * test getService(String name); NEGATIVE
     */
    @Test
    public void testGetServiceByNameForNonExistingService() {
        assertNull(repairShopService.getService(NON_EXISTING_SERVICE));
    }

    /**
     * test getAllService(); POSITIVE
     */
    @Test
    public void testGetAllServiceForExistingService() {
        try {
            BookableService service = repairShopService.createService(NAME, COST, DURATION);
            service.setId(EXISTING_SERVICE_ID);
        } catch(BookableServiceException e) {
            e.getMessage();
        }
        assertNotNull(repairShopService.getAllService());
        assertEquals(1, repairShopService.getAllService().size());
        assertEquals(NAME, repairShopService.getAllService().get(0).getName());
        assertEquals(EXISTING_SERVICE_ID, repairShopService.getAllService().get(0).getId());
    }
    /**
     * test getAllService(); NEGATIVE
     */
    @Test
    public void testGetAllServiceForNonExistingService() {
        try {
            BookableService bookableService = repairShopService.createService("Tire rim repair", COST, DURATION);
            bookableService.setCost(55.97f);
            bookableService.setDuration(55);
            bookableService.setId(NON_EXISTING_SERVICE_ID);
            assertNotEquals(bookableService.getName(), repairShopService.getAllService().get(0).getName());
            assertNotEquals(bookableService.getId(), repairShopService.getAllService().get(0).getId());
        }
        catch (BookableServiceException e){
            fail();
        }
    }

}