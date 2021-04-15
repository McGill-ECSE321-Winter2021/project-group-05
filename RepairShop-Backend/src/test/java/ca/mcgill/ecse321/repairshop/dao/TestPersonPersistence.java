package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.*;
import org.junit.jupiter.api.AfterEach;
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
public class TestPersonPersistence {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    /**
     * clear database
     */
    @AfterEach
    public void clearDatabase() {

        // Then we can clear the other tables
        administratorRepository.deleteAll();
        technicianRepository.deleteAll();
        customerRepository.deleteAll();

    }
    /**
     * testing customer persistence
     */

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

        String id = customer.getEmail();
        customer = null;

        customer = customerRepository.findCustomerByEmail(id);

        assertNotNull(customer);
        assertEquals(email,customer.getEmail());
        assertEquals(password,customer.getPassword());
        assertEquals(id,customer.getEmail());
        assertEquals(name,customer.getUsername());
        assertNotNull(customer.getRepairShop());
    }

    /**
     * testing association between timeslot and technician
     */
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
        technician.setRepairShop(rs);

        technicianRepository.save(technician);

        String id = technician.getEmail();
        technician = null;

        technician = technicianRepository.findTechnicianByEmail(id);

        assertNotNull(technician);
        assertEquals(email,technician.getEmail());
        assertEquals(password,technician.getPassword());
        assertEquals(id,technician.getEmail());
        assertEquals(name,technician.getUsername());
        assertNotNull(technician.getRepairShop());

        technician = null;

        List<Technician> technicianList = technicianRepository.findByTimeSlots(timeSlot);

        assertNotNull(technicianList);
        technician = technicianList.get(0);
        assertNotNull(technician);
        assertEquals(email,technician.getEmail());
        assertEquals(password,technician.getPassword());
        assertEquals(id,technician.getEmail());
        assertEquals(name,technician.getUsername());
        assertNotNull(technician.getRepairShop());
    }

    /**
     * testing Administrator persistence
     */
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

        String id=administrator.getEmail();
        administrator = null;

        administrator=administratorRepository.findAdministratorByEmail(id);

        assertNotNull(administrator);
        assertEquals(email,administrator.getEmail());
        assertEquals(password,administrator.getPassword());
        assertEquals(name,administrator.getUsername());
        assertNotNull(administrator.getRepairShop());

    }


}
