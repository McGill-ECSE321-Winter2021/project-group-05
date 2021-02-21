package ca.mcgill.ecse321.repairshop.dao;

import java.util.List;

import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import org.springframework.data.repository.CrudRepository;

public interface BusinessRepository extends CrudRepository<Business, Long> {
	
	Business findBusinessById(Long id);

}