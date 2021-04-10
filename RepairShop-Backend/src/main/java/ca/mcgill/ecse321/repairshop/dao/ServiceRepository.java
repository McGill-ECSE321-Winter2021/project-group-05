package ca.mcgill.ecse321.repairshop.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.repairshop.model.BookableService;

public interface ServiceRepository extends CrudRepository<BookableService, Long>{
    /**
     * returns service by name
     *
     * @param name service name
     * @return service
     */
    BookableService findServiceByName(String name);

    /**
     * returns service by service id
     *
     * @param Id service id
     * @return service
     */
    BookableService findServiceById(Long Id);
}