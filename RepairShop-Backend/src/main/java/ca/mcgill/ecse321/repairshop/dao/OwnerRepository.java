package ca.mcgill.ecse321.repairshop.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.repairshop.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long>{
    Owner findOwnerById(Long id);
    Owner findOwnerByEmail(String email);
}



