package ca.mcgill.ecse321.repairshop.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.repairshop.model.Administrator;

public interface AdministratorRepository extends CrudRepository<Administrator, Long>{
    /**
     * returns administrator from corresponding email
     * @param email admin email
     * @return administrator
     */
    Administrator findAdministratorByEmail(String email);
}
