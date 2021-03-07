package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long>{
    Appointment findAppointmentById(Long id);
    List<Appointment> findByServiceAndBill(BookableService service, Bill bill);
    List<Appointment> findByCustomer(Customer customer);
    List<Appointment> findByService(BookableService service);
    List<Appointment> findByBill(Bill bill);
    //List<Appointment> findByTimeSlot(TimeSlot timeslot);
}
