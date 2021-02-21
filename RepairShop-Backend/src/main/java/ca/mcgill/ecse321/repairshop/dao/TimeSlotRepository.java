package ca.mcgill.ecse321.repairshop.dao;


import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.model.Technician;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long> {

    TimeSlot findTimeSlotByID(Long id);

   // List<TimeSlot> findByBusinessAndAppointmentAndTechnician(Business business, Appointment app, Technician technician);

    //boolean existsByBusinessAndAppointmentAndTechnician(Business business, Appointment app, Technician technician);
    
}
