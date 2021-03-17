package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.ServiceRepository;
import ca.mcgill.ecse321.repairshop.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.awt.print.Book;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestRepairShopService {
    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private RepairShopService repairShopService;

    private static String NAME = "TestService";
    private static float COST = 21.3f;
    private static int DURATION = 10;

    @BeforeEach
    public void setMockOutPut(){
        lenient().when(serviceRepository.findServiceByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(NAME)){
                BookableService bookableService = new BookableService();
                bookableService.setName(NAME);
                bookableService.setCost(COST);
                bookableService.setDuration(DURATION);
                return bookableService;
            }else{
                return null;
            }
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
       // lenient().when(serviceRepository.save(any(BookableService.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateServiceSuccessfully(){

        BookableService createdService = repairShopService.createService(NAME, COST, DURATION);
        createdService = null;
        createdService = serviceRepository.findServiceByName(NAME);

        assertNotNull(createdService);
        assertEquals(createdService.getName(), NAME);
        assertEquals(createdService.getCost(), COST);
        assertEquals(createdService.getDuration(), DURATION);
    }

    @Test
    public void testCreateServiceWithNullName(){

        String error = null;
        BookableService createdService = null;

        try {
            createdService = repairShopService.createService(null, COST, DURATION);
            createdService = null;
           createdService = serviceRepository.findServiceByName(NAME);
        } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "Service duration cannot be 0");
    }


    @Test
    public void testEditServiceSuccessfully() {
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
        } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "New service duration cannot be 0");
    }


    @Test
    public void testDeleteServiceSuccessfully() {

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
        BookableService service1 = repairShopService.createService(NAME, COST, DURATION);
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

        repairShopService.deleteBookableService(service1);

        //service1 = serviceRepository.findServiceByName(NAME);
        service1 = repairShopService.getService(NAME);
        assertNull(service1);
    }

    @Test
    public void testDeleteServiceThatDoesNotExist() {
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
        BookableService service1 = repairShopService.createService(NAME, COST, DURATION);
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

        repairShopService.deleteBookableService(service1);
        service1 = repairShopService.getService(NAME);
        //service1 = serviceRepository.findServiceByName(NAME);

        try {
            repairShopService.deleteBookableService(service1);
        } catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Cannot delete a service that does not exist");
        }

    }

    @Test
    public void testdeleteServiceWithInvalidInput() {
        BookableService bookableService = null;

        try {
            repairShopService.deleteBookableService(bookableService);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Cannot delete a service that does not exist");
        }

    }
}