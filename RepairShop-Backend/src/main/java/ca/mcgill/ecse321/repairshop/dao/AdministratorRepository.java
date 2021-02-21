package ca.mcgill.ecse321.repairshop.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.repairshop.model.Administrator;
import org.springframework.stereotype.Repository;

public interface AdministratorRepository extends CrudRepository<Administrator, Long>{

    Administrator findAdministratorById(Long id);
    
}
