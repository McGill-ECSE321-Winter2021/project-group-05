package ca.mcgill.ecse321.repairshop.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.repairshop.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{

    Customer findCustomerByEmail(String email);
}
