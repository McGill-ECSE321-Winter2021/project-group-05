package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.Bill;
import ca.mcgill.ecse321.repairshop.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill, String> {
    Bill findBillByID(String id);

    Bill findByAppointmentAndByCustomer(Appointment app, Customer customer);
    List<Bill> findByCustomer(Customer customer);

    boolean existsByAppointmentAndCustomer(Appointment app, Customer customer);


    
}
