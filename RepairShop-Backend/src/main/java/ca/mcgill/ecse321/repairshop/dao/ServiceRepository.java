package ca.mcgill.ecse321.repairshop.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.Service;

public interface ServiceRepository extends CrudRepository<Service, Long>{
    Service findServiceByIDString(Long id);
   // List<Service> findByAppointment(Appointment app);
    //boolean existsByAppointment(Appointment app);
    
    
}



