package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
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

//    @AfterEach
//    public void clearDatabase() {
//        // First, we clear business to avoid exceptions due to inconsistencies
//        businessRepository.deleteAll();
//
//        // Then we can clear the other tables
//        administratorRepository.deleteAll();
//        technicianRepository.deleteAll();
//        ownerRepository.deleteAll();
//        customerRepository.deleteAll();
//        appointmentRepository.deleteAll();
//        billRepository.deleteAll();
//        serviceRepository.deleteAll();
//        timeSlotRepository.deleteAll();
//    }

    /**
     * testing customer
     */
    @Test
    public void testPersistAndLoadCustomer() {
    	
        String name = "TestCustomer";
        String password = "TestPassword";
        String email = "testemail@123.com";

        // First example for object save/load
        Customer customer = new Customer();
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setEmail(email);

        RepairShop rs = new RepairShop();
        customer.setRepairShop(rs);


        customerRepository.save(customer);
        
        Long id = customer.getId();
        customer = null;
        
        customer = customerRepository.findCustomerById(id);
        
        assertNotNull(customer);
        assertEquals(email,customer.getEmail());
        assertEquals(password,customer.getPassword());
        assertEquals(id,customer.getId());
        assertEquals(name,customer.getUsername());
        assertNotNull(customer.getRepairShop());
    }

    /**
     * testing association between timeslot and technician
     */
    @Test
    public void testPersistAndLoadTechnician() {
    	
        String name = "TestTechnician";
        String password = "TestPassword";
        String email = "testemail@123.com";
        
        TimeSlot timeSlot = new TimeSlot();
        Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
        Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
        timeSlot.setDate(date);

        RepairShop rs = new RepairShop();
        
        timeSlotRepository.save(timeSlot);
        
        Long timeSlotID = timeSlot.getId();

        Technician technician = new Technician();
        technician.setUsername(name);
        technician.setPassword(password);
        technician.setEmail(email);
        technician.setTimeSlot(timeSlot);
        technician.setRepairShop(rs);

        technicianRepository.save(technician);
        
        Long id = technician.getId();
        technician = null;
        
        technician = technicianRepository.findTechnicianById(id);
        
        assertNotNull(technician);
        assertEquals(email,technician.getEmail());
        assertEquals(password,technician.getPassword());
        assertEquals(id,technician.getId());
        assertEquals(name,technician.getUsername());
        assertNotNull(technician.getRepairShop());

        technician = null;
        
        List<Technician> technicianList = technicianRepository.findByTimeSlots(timeSlot);
        
        assertNotNull(technicianList);
        technician = technicianList.get(0);
        assertNotNull(technician);
        assertEquals(email,technician.getEmail());
        assertEquals(password,technician.getPassword());
        assertEquals(id,technician.getId());
        assertEquals(name,technician.getUsername());
        assertNotNull(technician.getRepairShop());
    }
    
    /**
     * testing Administrator
     */
    @Test
    public void testPersistAndLoadAdministrator() {
        
        String name = "TestAdministrator";
        String password = "TestPassword";
        String email = "testemail@123.com";

        Administrator administrator = new Administrator();

        administrator.setUsername(name);
        administrator.setPassword(password);
        administrator.setEmail(email);

        RepairShop rs = new RepairShop();
        administrator.setRepairShop(rs);
        
        administratorRepository.save(administrator);
        
        Long id=administrator.getId();
        administrator = null;
        
        administrator=administratorRepository.findAdministratorById(id);
        
        assertNotNull(administrator);
        assertEquals(email,administrator.getEmail());
        assertEquals(password,administrator.getPassword());
        assertEquals(name,administrator.getUsername());
        assertNotNull(administrator.getRepairShop());

    }

    /**
     * testing Owner
     */
    @Test
    public void testPersistAndLoadOwner() {
    	
        String name = "TestOwner";
        String password = "TestPassword";
        String email = "testemail@123.com";

        Owner owner = new Owner();
        owner.setUsername(name);
        owner.setPassword(password);
        owner.setEmail(email);

        RepairShop rs = new RepairShop();
        owner.setRepairShop(rs);
        
        ownerRepository.save(owner);
        
        Long id = owner.getId();
        owner = null;
        
        owner = ownerRepository.findOwnerById(id); 
        
        assertNotNull(owner);
        assertEquals(email,owner.getEmail());
        assertEquals(password,owner.getPassword());
        assertEquals(id,owner.getId());
        assertEquals(name,owner.getUsername());
        assertNotNull(owner.getRepairShop());

    }

    /**
     * testing Bill
     */
    @Test
    public void testPersistAndLoadBill() {

        String name = "TestCustomer";
        String password = "TestPassword";
        String email = "testemail@123.com";

        Customer customer = new Customer();
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setEmail(email);

        RepairShop rs = new RepairShop();
        customer.setRepairShop(rs);

        customerRepository.save(customer);
        
        Long customerId = customer.getId();
        
        Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        float testCost = 10;

        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setDate(date);
        bill.setTotalCost(testCost);
        bill.setRepairShop(rs);

        billRepository.save(bill);
        
        Long billId = bill.getId();

        bill = null;
        bill = billRepository.findBillById(billId);
        
        assertNotNull(bill);
        assertEquals(billId,bill.getId());
        assertEquals(testCost,bill.getTotalCost());
        assertEquals(name,bill.getCustomer().getUsername());
        assertEquals(email,bill.getCustomer().getEmail());
        assertEquals(password,bill.getCustomer().getPassword());
        assertEquals(customerId,bill.getCustomer().getId());
        assertEquals(date.toString(),bill.getDate().toString());
        assertNotNull(bill.getRepairShop());

    }

    @Test
    public void testPersistAndLoadAppointment() {

        String name = "TestCustomer";
        String password = "TestPassword";
        String email = "testemail@123.com";

        Customer customer = new Customer();
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setEmail(email);
        customerRepository.save(customer);
        Long customerId= customer.getId();

        Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        float testCost = 10;

        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setDate(date);
        bill.setTotalCost(testCost);
        billRepository.save(bill);
        Long billId = bill.getId();

        TimeSlot timeSlot = new TimeSlot();
        Date startDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));

        Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
        Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
        timeSlot.setStartTime(startTime);
        timeSlot.setDate(startDate);
        timeSlot.setEndTime(endTime);
        timeSlotRepository.save(timeSlot);
        Long timeSlotID = timeSlot.getId();

        Service service = new Service();
        float serviceCost = 9;
        int serviceDuration = 45;
        String serviceName = "change tire";

        service.setCost(serviceCost);
        service.setDuration(serviceDuration);
        service.setName(serviceName);
        serviceRepository.save(service);
        Long serviceID=service.getId();

        Appointment appointment = new Appointment();

        appointment.setBill(bill);
        appointment.setCustomer(customer);

        RepairShop rs = new RepairShop();
        appointment.setRepairShop(rs);

        appointment.setService(service);
        appointment.setTimeslot(timeSlot);
        appointmentRepository.save(appointment);
        Long appointmentID= appointment.getId();

        appointment=null;
        appointment=appointmentRepository.findAppointmentById(appointmentID);
        assertNotNull(appointment);
        assertEquals(appointmentID,appointment.getId());
        assertNotNull(appointment.getBill());
        assertNotNull(appointment.getBill().getAppointments());
        assertEquals(billId,appointment.getBill().getId());
        assertEquals(customerId,appointment.getCustomer().getId());
        assertEquals(timeSlotID,appointment.getTimeslot().getId());
        assertEquals(serviceID,appointment.getService().getId());

        appointment=null;
        List<Appointment> appointmentList=appointmentRepository.findByServiceAndBill(service,bill);
        appointment=appointmentList.get(0);
        assertNotNull(appointment);
        assertEquals(appointmentID,appointment.getId());
        assertNotNull(appointment.getBill().getAppointments());
        assertEquals(billId,appointment.getBill().getId());
        assertEquals(customerId,appointment.getCustomer().getId());
        assertEquals(timeSlotID,appointment.getTimeslot().getId());
        assertEquals(serviceID,appointment.getService().getId());
        assertNotNull(appointment.getRepairShop());
    }

    /**
     * Testing persisting and loading a service
     */
    @Test
    public void testPersistAndLoadService() {

        String serviceName = "repair window";
        int duration = 30;
        float cost = 10;
        Service service = new Service();

        service.setName(serviceName);
        service.setDuration(duration);
        service.setCost(cost);

        RepairShop rs = new RepairShop();
        service.setRepairShop(rs);
        serviceRepository.save(service);
        Long serviceId = service.getId();

        service=null;
        service=serviceRepository.findServiceById(serviceId);
        assertNotNull(service);
        assertEquals(serviceId,service.getId());
        assertEquals(serviceName,service.getName());
        assertEquals(duration,service.getDuration());
        assertEquals(cost,service.getCost());
        assertNotNull(service.getRepairShop());
    }
    
    /**
     * Testing persisting and loading business
     */
    @Test
    public void testPersistAndLoadBusiness(){
        String address = "MTL";
        String email = "business@123.com";
        String phone = "514123456";
        String name = "testBusiness";
        Business business = new Business();

        business.setAddress(address);
        business.setEmail(email);
        business.setPhoneNumber(phone);
        business.setName(name);

        RepairShop rs = new RepairShop();
        business.setRepairShop(rs);
        
        businessRepository.save(business);
        
        Long id = business.getId();
        business = null;
        
        business = businessRepository.findBusinessById(id);
        
        assertNotNull(business);
        assertEquals(address,business.getAddress());
        assertEquals(email,business.getEmail());
        assertEquals(id,business.getId());
        assertEquals(phone,business.getPhoneNumber());
        assertEquals(name,business.getName());
        assertNotNull(business.getRepairShop());

    }
    
    /**
     * Testing persisting and loading timeslot
     */
    @Test
    public void testPersistAndLoadTimeSlot(){
    	
        Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
        Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setEndTime(endTime);
        timeSlot.setStartTime(startTime);
        timeSlot.setDate(date);

        RepairShop rs = new RepairShop();
        timeSlot.setRepairShop(rs);

        timeSlotRepository.save(timeSlot);
        
        Long timeSlotID =timeSlot.getId();
        timeSlot = null;
        
        timeSlot = timeSlotRepository.findTimeSlotById(timeSlotID);
        
        assertNotNull(timeSlot);
        assertEquals(timeSlotID, timeSlot.getId());
        assertEquals(date.toString(), timeSlot.getDate().toString());
        assertEquals(startTime.toString(), timeSlot.getStartTime().toString());
        assertEquals(endTime.toString(), timeSlot.getEndTime().toString());
        assertNotNull(timeSlot.getRepairShop());
    }

}