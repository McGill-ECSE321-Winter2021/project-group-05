package ca.mcgill.ecse321.repairshop.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.repairshop.model.Technician;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;

public interface TechnicianRepository extends CrudRepository<Technician, Long>{
    Technician findTechnicianByID(Long id);
    List<Technician> findByTimeSlot(TimeSlot timeSlot);
    //boolean existsByTimeSlot(TimeSlot timeSlot);
    
}
