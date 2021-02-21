package ca.mcgill.ecse321.repairshop.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.Bill;
import ca.mcgill.ecse321.repairshop.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
    Customer findCustomerByID(Long id);
    
    //Customer findByBill(Bill bill);
    //Customer findByAppointment(Appointment app);
    //Customer findByAppointmentAndBill(Bill bill, Appointment app);
    
    //boolean existByAppointment(Appointment app);
    //boolean existByBill(Bill bill);
    //boolean existsByAppointmentAndBill(Appointment app, Bill bill);
}
