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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAppointmentPersistence {

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;


    /**
     * clear database
     */
    @AfterEach
    public void clearDatabase() {

        technicianRepository.deleteAll();
        customerRepository.deleteAll();
        appointmentRepository.deleteAll();
        billRepository.deleteAll();
        serviceRepository.deleteAll();
        timeSlotRepository.deleteAll();
    }

    /**
     * testing persistence of appointment
     */
    public void testPersistAndLoadAppointment() {

        String name = "TestCustomer";
        String password = "TestPassword";
        String email = "testemail@123.com";

        Customer customer = new Customer();
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setEmail(email);
        customerRepository.save(customer);
        String customerId= customer.getEmail();

        Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        float testCost = 10;

        Bill bill = new Bill();
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

        BookableService service = new BookableService();
        float serviceCost = 9;
        int serviceDuration = 45;
        String serviceName = "change tire";

        service.setCost(serviceCost);
        service.setDuration(serviceDuration);
        service.setName(serviceName);
        serviceRepository.save(service);
        String serviceID=service.getName();

        List<BookableService> services = new ArrayList<BookableService>();
        services.add(service);

        Appointment appointment = new Appointment();

        appointment.setBill(bill);
        appointment.setCustomer(customer);

        RepairShop rs = new RepairShop();
        appointment.setRepairShop(rs);

        appointment.setServices(services);
        appointment.setTimeslot(timeSlot);
        appointmentRepository.save(appointment);
        Long appointmentID= appointment.getId();


        appointment=appointmentRepository.findAppointmentById(appointmentID);
        assertNotNull(appointment);
        assertEquals(appointmentID,appointment.getId());
        assertNotNull(appointment.getBill());
        assertEquals(billId,appointment.getBill().getId());
        assertEquals(customerId,appointment.getCustomer().getEmail());
        assertEquals(timeSlotID,appointment.getTimeslot().getId());
        assertEquals(serviceID,(appointment.getServices().get(0)).getName());


    }
}
