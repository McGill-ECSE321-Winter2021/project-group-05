package ca.mcgill.ecse321.repairshop.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.repairshop.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
    /**
     * returns customer by email
     *
     * @param email customer email
     * @return customer
     */
    Customer findCustomerByEmail(String email);
}
