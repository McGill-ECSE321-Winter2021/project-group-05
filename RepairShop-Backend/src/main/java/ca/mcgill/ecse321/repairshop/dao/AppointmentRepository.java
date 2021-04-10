package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long>{
    /**
     * finds an appointment by appointment id
     *
     * @param id appointment id
     * @return appointment
     */
    Appointment findAppointmentById(Long id);

    /**
     * finds all appointments by customer
     *
     * @param customer the customer assigned to appointment
     * @return list of appointments
     */
    List<Appointment> findByCustomer(Customer customer);

}
