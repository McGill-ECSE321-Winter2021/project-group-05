package ca.mcgill.ecse321.repairshop.dao;

import java.util.List;

import ca.mcgill.ecse321.repairshop.model.Appointment;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.repairshop.model.Technician;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;

public interface TechnicianRepository extends CrudRepository<Technician, Long>{
    /**
     * returns technicians by timeslot assigned to
     *
     * @param timeSlot timeslot
     * @return list of technicians
     */
    List<Technician> findByTimeSlots(TimeSlot timeSlot);

    /**
     * returns technician by their email
     *
     * @param email technician email
     * @return technician
     */
    Technician findTechnicianByEmail(String email);
}
