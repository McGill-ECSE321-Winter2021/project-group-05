package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestRepairShopPersistence {
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @AfterEach
    public void clearDatabase() {
        // Fisrt, we clear business to avoid exceptions due to inconsistencies
        businessRepository.deleteAll();

        // Then we can clear the other tables
        administratorRepository.deleteAll();
        technicianRepository.deleteAll();
        ownerRepository.deleteAll();
        customerRepository.deleteAll();
        appointmentRepository.deleteAll();
        billRepository.deleteAll();
        serviceRepository.deleteAll();
        timeSlotRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCustomer() {
        /**
         * testing customer
         */
        String name = "TestCustomer";
        String password = "TestPassword";
        String email = "testemail@123.com";
        String id="c1";

        // First example for object save/load
        Customer customer = new Customer();
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setEmail(email);
        customer.setId(id);
        customerRepository.save(customer);

        customer =null;
        customer=customerRepository.findCustomerByID(id);
        assertNotNull(customer);
        assertEquals(email,customer.getEmail());
        assertEquals(password,customer.getPassword());
        assertEquals(id,customer.getId());
        assertEquals(name,customer.getUsername());
    }


    @Test
    public void testPersistAndLoadTechnician() {


        /**
        * tesing association between timeslot and tevchnician
        */
        TimeSlot timeSlot = new TimeSlot();
        Date startDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        String timeSlotID = "t1";
        //TODO: only 1 date?
        Date endDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
        Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
        timeSlot.setStartTime(startTime);
        timeSlot.setStartDate(startDate);
        timeSlot.setEndTime(endTime);
        timeSlot.setEndDate(endDate);
        timeSlot.setId(timeSlotID);
        timeSlotRepository.save(timeSlot);

        /**
         * testing technician
         */
        String name = "TestTechnician";
        String password = "TestPassword";
        String email = "testemail@123.com";
        String id="t1";

        // First example for object save/load
        Technician technician = new Technician();
        technician.setUsername(name);
        technician.setPassword(password);
        technician.setEmail(email);
        technician.setId(id);
        technician.addTimeSlot(timeSlot);
        technicianRepository.save(technician);


        technician =null;
        technician=technicianRepository.findTechnicianByID(id);
        assertNotNull(technician);
        assertEquals(email,technician.getEmail());
        assertEquals(password,technician.getPassword());
        assertEquals(id,technician.getId());
        assertEquals(name,technician.getUsername());

        technician =null;
        List<Technician> technicianList = technicianRepository.findByTimeSlot(timeSlot);
        assertNotNull(technicianList);
        technician=technicianList.get(0);
        assertNotNull(technician);
        assertEquals(email,technician.getEmail());
        assertEquals(password,technician.getPassword());
        assertEquals(id,technician.getId());
        assertEquals(name,technician.getUsername());
    }

    @Test
    public void testPersistAndLoadAdministrator() {
        /**
         * testing Administrator
         */
        String name = "TestAdministrator";
        String password = "TestPassword";
        String email = "testemail@123.com";
        String id="a1";

        // First example for object save/load
        Administrator administrator = new Administrator();
        administrator.setUsername(name);
        administrator.setPassword(password);
        administrator.setEmail(email);
        administrator.setId(id);
        administratorRepository.save(administrator);

        administrator =null;
        administrator=administratorRepository.findAdministratorByID(id);
        assertNotNull(administrator);
        assertEquals(email,administrator.getEmail());
        assertEquals(password,administrator.getPassword());
        assertEquals(id,administrator.getId());
        assertEquals(name,administrator.getUsername());

    }


    @Test
    public void testPersistAndLoadOwner() {
        /**
         * testing Owner
         */
        String name = "TestOwner";
        String password = "TestPassword";
        String email = "testemail@123.com";
        String id="o1";

        // First example for object save/load
        Owner owner = new Owner();
        owner.setUsername(name);
        owner.setPassword(password);
        owner.setEmail(email);
        owner.setId(id);
        ownerRepository.save(owner);

        owner =null;
        owner=ownerRepository.findOwnerByID(id);
        assertNotNull(owner);
        assertEquals(email,owner.getEmail());
        assertEquals(password,owner.getPassword());
        assertEquals(id,owner.getId());
        assertEquals(name,owner.getUsername());

    }

    @Test
    public void testPersistAndLoadBill() {

        /**
         * testing Bill
         */
        String name = "TestCustomer";
        String password = "TestPassword";
        String email = "testemail@123.com";
        String customerId="c2";

        // First example for object save/load
        Customer customer = new Customer();
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setEmail(email);
        customer.setId(customerId);
        customerRepository.save(customer);

        Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        float testCost = 10;
        String billId = "b1";
        // First example for object save/load
        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setDate(date);
        bill.setTotalCost(testCost);
        bill.setId(billId);
        billRepository.save(bill);

        bill =null;
        bill=billRepository.findBillByID(billId);
        assertNotNull(bill);
        assertEquals(billId,bill.getId());
        assertEquals(testCost,bill.getTotalCost());
        //assertEquals(name,bill.getCustomer().getUsername());
        //assertEquals(email,bill.getCustomer().getEmail());
        //assertEquals(password,bill.getCustomer().getPassword());
        assertEquals(customerId,bill.getCustomer().getId());
        assertEquals(date.toString(),bill.getDate().toString());

    }

    @Test
    public void testPersistAndLoadAppointment() {
        String appointmentID="a1";
        String name = "TestCustomer";
        String password = "TestPassword";
        String email = "testemail@123.com";
        String customerId="c2";

        // First example for object save/load
        Customer customer = new Customer();
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setEmail(email);
        customer.setId(customerId);
        customerRepository.save(customer);

        Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        float testCost = 10;
        String billId = "b1";
        // First example for object save/load
        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setDate(date);
        bill.setTotalCost(testCost);
        bill.setId(billId);
        billRepository.save(bill);

        TimeSlot timeSlot = new TimeSlot();
        Date startDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        String timeSlotID = "t1";
        //TODO: only 1 date?
        Date endDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
        Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
        timeSlot.setStartTime(startTime);
        timeSlot.setStartDate(startDate);
        timeSlot.setEndTime(endTime);
        timeSlot.setEndDate(endDate);
        timeSlot.setId(timeSlotID);
        timeSlotRepository.save(timeSlot);

        Service service = new Service();
        float serviceCost = 9;
        int serviceDuration = 45;
        String serviceName = "change tire";
        String serviceID="s1";
        service.setCost(serviceCost);
        service.setDuration(serviceDuration);
        service.setName(serviceName);
        service.setId(serviceID);
        serviceRepository.save(service);

        Appointment appointment = new Appointment();

        appointment.setBill(bill);
        appointment.setCustomer(customer);
        appointment.setId(appointmentID);
        appointment.setService(service);
        appointment.setTimeslot(timeSlot);
        appointmentRepository.save(appointment);

        appointment=null;
        appointment=appointmentRepository.findAppointmentByID(appointmentID);
        assertNotNull(appointment);
        assertEquals(appointmentID,appointment.getId());
        assertNotNull(appointment.getBill().getAppointment(0));
        assertEquals(billId,appointment.getBill().getId());
        assertEquals(customerId,appointment.getCustomer().getId());
        assertEquals(timeSlotID,appointment.getTimeslot().getId());
        assertEquals(serviceID,appointment.getService().getId());

        appointment=null;
        List<Appointment> appointmentList=appointmentRepository.findByServiceAndBill(service,bill);
        appointment=appointmentList.get(0);
        assertNotNull(appointment);
        assertEquals(appointmentID,appointment.getId());
        assertNotNull(appointment.getBill().getAppointment(0));
        assertEquals(billId,appointment.getBill().getId());
        assertEquals(customerId,appointment.getCustomer().getId());
        assertEquals(timeSlotID,appointment.getTimeslot().getId());
        assertEquals(serviceID,appointment.getService().getId());
    }

    @Test
    public void testPersistAndLoadService() {

        String serviceId = "s1";
        String serviceName = "repair window";
        int duration = 30;
        float cost = 10;
        Service service = new Service();
        service.setId(serviceId);
        service.setName(serviceName);
        service.setDuration(duration);
        service.setCost(cost);
        serviceRepository.save(service);

        service=null;
        service=serviceRepository.findServiceByIDString(serviceId);
        assertNotNull(service);
        assertEquals(serviceId,service.getId());
        assertEquals(serviceName,service.getName());
        assertEquals(duration,service.getDuration());
        assertEquals(cost,service.getCost());
    }

    @Test
    public void testPersistAndLoadBusiness(){
        String address = "MTL";
        String email = "buesiness@123.com";
        String id ="b1";
        String phone = "514123456";
        String name = "testBusiness";
        Business business = new Business();

        business.setAddress(address);
        business.setEmail(email);
        business.setId(id);
        business.setPhoneNumber(phone);
        business.setName(name);
        businessRepository.save(business);

        business = null;
        business=businessRepository.findBusinessByID(id);
        assertNotNull(business);
        assertEquals(address,business.getAddress());
        assertEquals(email,business.getEmail());
        assertEquals(id,business.getId());
        assertEquals(phone,business.getPhoneNumber());
        assertEquals(name,business.getName());

    }

    @Test
    public void testPersistAndLoadTimeSlot(){
        Date startDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        String timeSlotID = "t1";
        //TODO: only 1 date?
        Date endDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
        Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setEndTime(endTime);
        timeSlot.setStartTime(startTime);
        timeSlot.setId(timeSlotID);
        timeSlot.setEndDate(endDate);
        timeSlot.setStartDate(startDate);
        timeSlotRepository.save(timeSlot);

        timeSlot = null;
        timeSlot = timeSlotRepository.findTimeSlotByID(timeSlotID);
        assertNotNull(timeSlot);
        assertEquals(timeSlotID, timeSlot.getId());
        assertEquals(endDate.toString(), timeSlot.getEndDate().toString());
        assertEquals(startDate.toString(), timeSlot.getStartDate().toString());
        assertEquals(startTime.toString(), timeSlot.getStartTime().toString());
        assertEquals(endTime.toString(), timeSlot.getEndTime().toString());


    }




}