package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.AdministratorRepository;
import ca.mcgill.ecse321.repairshop.dao.CustomerRepository;
import ca.mcgill.ecse321.repairshop.dao.OwnerRepository;
import ca.mcgill.ecse321.repairshop.dao.TechnicianRepository;
import ca.mcgill.ecse321.repairshop.model.Administrator;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.model.Owner;
import ca.mcgill.ecse321.repairshop.model.Technician;
import ca.mcgill.ecse321.repairshop.utility.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    AdministratorRepository administratorRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    TechnicianRepository technicianRepository;
    @Autowired
    CustomerRepository customerRepository;

    //todo --> delete this
    public void createCustomer(Customer customer){
        customerRepository.save(customer);
    }
    /**
     * creating person
     * Customer
     */
    @Transactional
    public Customer createCustomer(String email, String username, String password){
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setUsername(username);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public Customer getCustomer(Long id) {
        Customer customer = customerRepository.findCustomerById(id);
        return customer;
    }
    
    @Transactional
    public List<Customer> getAllCustomer() {
        return ListUtil.toList(customerRepository.findAll());
    }

    /**
     * Owner
     */
    @Transactional
    public Owner createOwner(String email, String username, String password){
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setUsername(username);
        ownerRepository.save(owner);
        return owner;
    }

    @Transactional
    public Owner getOwner(Long id) {
        Owner owner = ownerRepository.findOwnerById(id);
        return owner;
    }

    @Transactional
    public List<Owner> getAllOwner() {
        return ListUtil.toList(ownerRepository.findAll());
    }

    /**
     * Technician
     */
    @Transactional
    public Technician createTechnician(String email, String username, String password){
        Technician technician = new Technician();
        technician.setEmail(email);
        technician.setPassword(password);
        technician.setUsername(username);
        technicianRepository.save(technician);
        return technician;
    }

    @Transactional
    public Technician getTechnician(Long id) {
        Technician technician = technicianRepository.findTechnicianById(id);
        return technician;
    }

    @Transactional
    public List<Technician> getAllTechnician() {
        return ListUtil.toList(technicianRepository.findAll());
    }

    /**
     * Administrator
     */
    @Transactional
    public Administrator createAdministrator(String email, String username, String password){
        Administrator administrator = new Administrator();
        administrator.setEmail(email);
        administrator.setPassword(password);
        administrator.setUsername(username);
        administratorRepository.save(administrator);
        return administrator;
    }

    @Transactional
    public Administrator getAdministrator(Long id) {
        Administrator administrator = administratorRepository.findAdministratorById(id);
        return administrator;
    }

    @Transactional
    public List<Administrator> getAllAdministrator() {
        return ListUtil.toList(administratorRepository.findAll());
    }
}
