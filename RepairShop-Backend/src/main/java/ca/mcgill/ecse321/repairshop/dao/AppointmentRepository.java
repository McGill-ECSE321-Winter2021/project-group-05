package ca.mcgill.ecse321.repairshop.dao;
import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.Bill;
import ca.mcgill.ecse321.repairshop.model.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long>{
    
    Appointment findAppointmentById(Long id);

    //List<Appointment> findByService(Service service);
    //List<Appointment> findByBill(Bill bill);
    List<Appointment> findByServiceAndBill(Service service, Bill bill);

   // boolean existsByServiceAndBill(Service service,Bill bill);

    
}
