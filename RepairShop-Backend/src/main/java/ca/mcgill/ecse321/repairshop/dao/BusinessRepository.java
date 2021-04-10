package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.Business;
import org.springframework.data.repository.CrudRepository;

public interface BusinessRepository extends CrudRepository<Business, Long> {
	/**
	 * finds business by business id
	 *
	 * @param id business id
	 * @return business
	 */
	Business findBusinessById(Long id);

	/**
	 * finds business by business name
	 *
	 * @param name business name
	 * @return business
	 */
	Business findByName(String name);
}