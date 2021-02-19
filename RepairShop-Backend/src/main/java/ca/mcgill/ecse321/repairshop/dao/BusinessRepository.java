package ca.mcgill.ecse321.repairshop.dao;

import java.util.List;

import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import org.springframework.data.repository.CrudRepository;

public interface BusinessRepository extends CrudRepository<Business, String> {

	Business findBusinessByID(String id);

	Business findByTimeSlot(TimeSlot timeslot);

	boolean existsByTimeSlot(TimeSlot timeSlot);



}