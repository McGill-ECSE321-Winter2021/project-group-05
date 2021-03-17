package ca.mcgill.ecse321.repairshop.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.repairshop.model.Administrator;

public interface AdministratorRepository extends CrudRepository<Administrator, String>{

    Administrator findAdministratorByEmail(String email);
}
