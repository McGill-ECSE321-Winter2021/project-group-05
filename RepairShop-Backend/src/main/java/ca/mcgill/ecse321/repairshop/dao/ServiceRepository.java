package ca.mcgill.ecse321.repairshop.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.repairshop.model.BookableService;

public interface ServiceRepository extends CrudRepository<BookableService, Long>{
    BookableService findServiceByName(String name);
    BookableService findServiceById(Long Id);
}