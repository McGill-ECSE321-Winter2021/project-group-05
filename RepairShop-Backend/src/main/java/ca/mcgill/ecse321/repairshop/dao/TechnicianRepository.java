package ca.mcgill.ecse321.repairshop.dao;

import java.util.List;

import ca.mcgill.ecse321.repairshop.model.Appointment;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.repairshop.model.Technician;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;

public interface TechnicianRepository extends CrudRepository<Technician, Long>{
    List<Technician> findByTimeSlots(TimeSlot timeSlot);
    Technician findTechnicianByEmail(String email);
}
