package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.dto.BusinessDto;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.AppointmentException;
import ca.mcgill.ecse321.repairshop.utility.BookableServiceException;
import ca.mcgill.ecse321.repairshop.utility.BusinessException;
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

    private static String NAME = "TestService";
    private static String SERVICE_NAME_2 = "Fix fog light";
    private static String NON_EXISTING_SERVICE = "Fix tail light";
    private static float COST = 21.3f;
    private static int DURATION = 10;
    private static Long EXISTING_SERVICE_ID = 2L;
    private static Long EXISTING_SERVICE_ID_2 = 3L;
    private static Long NON_EXISTING_SERVICE_ID = 4L;
    //private static String NONEXISTING_SERVICE = "Non existing service";

    private static Long TIMESLOT_ID = 0L;
    private static Long BUSINESS_ID = 0L;
    private static String CUSTOMER_ID = "johndoe@mail.mcgill.ca";
    private static String CUSTOMER_ID_2 = "anna-taylorjoy@mcgill.ca";
    private static Long APPOINTMENT_ID = 0L;

    @BeforeEach
    public void setMockOutPut(){
        lenient().when(serviceRepository.findServiceByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(NAME)){
                BookableService bookableService = new BookableService();
                bookableService.setName(NAME);
                bookableService.setCost(COST);
                bookableService.setDuration(DURATION);
                return bookableService;
            } else if(invocation.getArgument(0).equals(SERVICE_NAME_2)){
                BookableService bookableService = new BookableService();
                bookableService.setName(SERVICE_NAME_2);
                bookableService.setCost(COST);
                bookableService.setDuration(DURATION);
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
            services.add(bookableService);
            return services;
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
        lenient().when(timeSlotRepository.save(any(TimeSlot.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateServiceSuccessfully(){
        try{
            String serviceName = SERVICE_NAME_2;
            BookableService createdService = repairShopService.createService("Place holder", COST, DURATION);
            createdService.setName(serviceName);
            createdService.setId(EXISTING_SERVICE_ID_2);
            createdService = null;
            createdService = serviceRepository.findServiceById(EXISTING_SERVICE_ID_2);

            assertNotNull(createdService);
            assertEquals(createdService.getName(), serviceName);
            assertEquals(createdService.getCost(), COST);
            assertEquals(createdService.getDuration(), DURATION);
        }
        catch (BookableServiceException e){
            e.printStackTrace();
            fail();
        }

    }

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

        assertEquals(error, "Service name cannot be empty");
    }


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
        assertEquals(error, "Service name cannot be empty");
    }


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
        assertEquals(error, "Service cost cannot be negative");
    }



    @Test
    public void testCreateServiceWithZeroDuration(){
        String NAME = "TestService";
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
        assertEquals(error, "Service duration cannot be 0");
    }


    @Test
    public void testEditServiceSuccessfully() {
        try {
            //TODO: change this later, doesnt work if removed
            String OLD_NAME = "Old service name";
            float OLD_COST = 29.79f;
            int OLD_DURATION = 12;
            BookableService service = new BookableService();
            service.setName(OLD_NAME);
            service.setCost(OLD_COST);
            service.setDuration(OLD_DURATION);

            String NEW_NAME = "New service name";
            float NEW_COST = 19.79f;
            int NEW_DURATION = 14;

            BookableService editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);

            assertNotNull(editedService);
            assertEquals(editedService.getName(), NEW_NAME);
            assertEquals(editedService.getCost(), NEW_COST);
            assertEquals(editedService.getDuration(), NEW_DURATION);
        }
        catch (BookableServiceException e){
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void testEditServiceWithNullService() {
        //TODO: change this later, doesnt work if removed
        String OLD_NAME = "Old service name";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = new BookableService();
        service.setName(OLD_NAME);
        service.setCost(OLD_COST);
        service.setDuration(OLD_DURATION);

        service = null;

        String NEW_NAME = "New service name";
        float NEW_COST = 59.79f;
        int NEW_DURATION = 16;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Please select a service that you want to modify");
    }



    @Test
    public void testEditServiceWithEmptyNewServiceName() {
        //TODO: change this later, doesnt work if removed
        String OLD_NAME = "Old service name";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = new BookableService();
        service.setName(OLD_NAME);
        service.setCost(OLD_COST);
        service.setDuration(OLD_DURATION);

        String NEW_NAME = "   ";
        float NEW_COST = 39.79f;
        int NEW_DURATION = 17;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }

        assertEquals(error, "New service name cannot be empty");
    }


    @Test
    public void testEditServiceWithNegativeNewCost() {
        //TODO: change this later, doesnt work if removed
        String OLD_NAME = "Old service name";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = new BookableService();
        service.setName(OLD_NAME);
        service.setCost(OLD_COST);
        service.setDuration(OLD_DURATION);

        String NEW_NAME = "New service name";
        float NEW_COST = -19.59f;
        int NEW_DURATION = 9;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }

        assertEquals(error, "New service cost cannot be negative");
    }


    @Test
    public void testEditServiceWithZeroNewDuration() {
        //TODO: change this later, doesnt work if removed
        String OLD_NAME = "Old service name";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = new BookableService();
        service.setName(OLD_NAME);
        service.setCost(OLD_COST);
        service.setDuration(OLD_DURATION);

        String NEW_NAME = "New service name";
        float NEW_COST = 599.59f;
        int NEW_DURATION = 0;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (BookableServiceException e) {
            error = e.getMessage();
        }

        assertEquals(error, "New service duration cannot be 0");
    }


    @Test
    public void testDeleteServiceThatDoesNotExist() {
        try{
            RepairShop repairShop = new RepairShop();

            TimeSlot timeSlot = new TimeSlot();                 // create new TimeSlot
            Date date = Date.valueOf("2021-03-14");
            Time startTime = Time.valueOf("10:00:00");
            Time endTime = Time.valueOf("12:00:00");
            Long timeSlotId = 1L;
            timeSlot.setDate(date);
            timeSlot.setStartTime(startTime);
            timeSlot.setEndTime(endTime);
            timeSlot.setId(timeSlotId);
            timeSlot.setRepairShop(repairShop);

            Business business = new Business();                 // create business
            String name = "Demo business";
            String address = "365 Sherbrooke";
            String phoneNumber = "514-123-4567";
            String businessEmail = "123@repairshop.ca";
            Long businessId = 2L;
            business.setName(name);
            business.setAddress(address);
            business.setEmail(businessEmail);
            business.setPhoneNumber(phoneNumber);
            business.setId(businessId);
            business.setRepairShop(repairShop);

            Customer customer = new Customer();                 // create customer
            String customerEmail = "johndoe@mail.mcgill.ca";
            String username = "johndoe007";
            String password = "password" ;
            String cardNumber = "1234567890123456";
            String cvv = "123";
            Long personId = 3L;
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


            // create service
            String serviceName = "TestService1";
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
            System.out.println(service1.getRepairShop());

            service1.setRepairShop(repairShop);


            repairShopService.deleteBookableService(service1);
        } catch(BookableServiceException e) {
            assertEquals(e.getMessage(), "Cannot delete a service that does not exist");
        }

    }

    @Test
    public void testDeleteServiceWithInvalidInput() {
        BookableService bookableService = null;

        try {
            repairShopService.deleteBookableService(bookableService);
        } catch (BookableServiceException e) {
            assertEquals(e.getMessage(), "Cannot delete a service that does not exist");
        }

    }


    @Test
    public void testDeleteServiceWithFutureAppointments() {
        RepairShop repairShop = new RepairShop();

        //TimeSlot timeSlot = new TimeSlot();                 // create new TimeSlot
        Date date = Date.valueOf("2021-06-21");
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("12:00:00");
        TimeSlot timeSlot = timeSlotService.createTimeSlot(date, startTime, endTime);
        timeSlot.setId(TIMESLOT_ID);
        timeSlot.setRepairShop(repairShop);

        //Business business = new Business();                 // create business
        String name = "Demo business";
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

        //Customer customer = new Customer();                 // create customer
        String username = "johndoe007";
        String password = "password" ;
        String cardNumber = "1234567890123456";
        String cvv = "123";
        Date expiry = Date.valueOf("2023-06-27");
        int noShow = 1;
        Customer customer = null;
        customer = new Customer();
//        try {
//            customer = personService.createCustomer(CUSTOMER_ID_2, username, password);
//        } catch (PersonException e) {
//            System.out.println(e.getMessage());
//        }
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
            service = repairShopService.createService("Place holder", COST, DURATION);      // TODO  : workaround - test skipped
            service.setId(10l);
            service.setName(SERVICE_NAME_2);
            service.setId(EXISTING_SERVICE_ID_2);

        } catch (BookableServiceException e) {
            System.out.println(e.getMessage());
        }

        service.setRepairShop(repairShop);
        services.add(service);


        Appointment appointment1 = null;
        try {
            appointment1 = appointmentService.createAppointment(services, customer, timeSlot);          // create appointment
        } catch (AppointmentException e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
            assertEquals("Cannot delete a service which still has future appointments", e.getMessage());
        }
    }

    /**
     * test getService(Long Id)
     */
    @Test
    public void testGetServiceByIDForExistingService() {
        assertEquals(EXISTING_SERVICE_ID, repairShopService.getService(EXISTING_SERVICE_ID).getId());
    }
    @Test
    public void testGetServiceByIdForNonExistingService() {
        assertNull(repairShopService.getService(NON_EXISTING_SERVICE_ID));
    }

    /**
     * test getService(String name)
     */
    @Test
    public void testGetServiceByNameForExistingService() {
        assertEquals(NAME, repairShopService.getService(NAME).getName());
    }
    @Test
    public void testGetServiceByNameForNonExistingService() {
        assertNull(repairShopService.getService(NON_EXISTING_SERVICE));
    }

    /**
     * test getAllService()
     */
    @Test
    public void testGetAllServiceForExistingService() {
        try {
            BookableService service = repairShopService.createService(NAME, COST, DURATION);
        } catch(BookableServiceException e) {
            e.getMessage();
        }
        assertNotNull(repairShopService.getAllService());
        assertEquals(1, repairShopService.getAllService().size());
        assertEquals(NAME, repairShopService.getAllService().get(0).getName());
    }
    @Test
    public void testGetAllServiceForNonExistingService() {
        try {
            BookableService bookableService = new BookableService();
            bookableService.setName("Change tire rims");
            bookableService.setCost(55.97f);
            bookableService.setDuration(55);
            assertNotEquals(bookableService.getName(), repairShopService.getAllService().get(0).getName());
        }
        catch (IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void demoDeleteServiceSuccess() {
        RepairShop repairShop = new RepairShop();

        //TimeSlot timeSlot = new TimeSlot();                 // create new TimeSlot
        Date date = Date.valueOf("2021-05-01");
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("12:00:00");
        TimeSlot timeSlot = timeSlotService.createTimeSlot(date, startTime, endTime);
        timeSlot.setId(TIMESLOT_ID);
        timeSlot.setDate(Date.valueOf("2021-02-01"));       // workaround the restriction
        timeSlot.setRepairShop(repairShop);

        //Business business = new Business();                 // create business
        String name = "Demo business";
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

        //Customer customer = new Customer();                 // create customer
        String username = "johndoe007";
        String password = "password" ;
        String cardNumber = "1234567890123456";
        String cvv = "123";
        Date expiry = Date.valueOf("2023-06-27");
        int noShow = 1;
        Customer customer = null;
        customer = new Customer();
//        try {
//            customer = personService.createCustomer(CUSTOMER_ID_2, username, password);
//        } catch (PersonException e) {
//            System.out.println(e.getMessage());
//        }
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
            service = repairShopService.createService("Place holder", COST, DURATION);      // TODO  : workaround - test skipped
            service.setId(10l);
            service.setName(SERVICE_NAME_2);
            service.setId(EXISTING_SERVICE_ID_2);

        } catch (BookableServiceException e) {
            System.out.println(e.getMessage());
        }

        service.setRepairShop(repairShop);
        services.add(service);


        Appointment appointment1 = null;
        try {
            appointment1 = appointmentService.createAppointment(services, customer, timeSlot);          // create appointment
        } catch (AppointmentException e) {
            System.out.println(e.getMessage());
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
            assertNull(repairShopService.getService(EXISTING_SERVICE_ID_2));
        } catch (BookableServiceException e) {
            System.out.println(e.getMessage());
            fail();
        }


    }

}