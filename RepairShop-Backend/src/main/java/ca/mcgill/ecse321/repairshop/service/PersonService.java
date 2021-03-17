package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.AdministratorRepository;
import ca.mcgill.ecse321.repairshop.dao.CustomerRepository;
import ca.mcgill.ecse321.repairshop.dao.OwnerRepository;
import ca.mcgill.ecse321.repairshop.dao.TechnicianRepository;
import ca.mcgill.ecse321.repairshop.dto.CustomerDto;
import ca.mcgill.ecse321.repairshop.model.Administrator;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.model.Owner;
import ca.mcgill.ecse321.repairshop.model.Technician;
import ca.mcgill.ecse321.repairshop.utility.PersonException;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    /**
     * Customer
     */
    @Transactional
    public Customer createCustomer(String email, String username, String password) throws PersonException {
        String error = getErrorFromData(email, username, password);
        if(!error.equals("")){
            throw new PersonException(error);
        }
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setUsername(username);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public Customer getCustomer(String email) throws PersonException{
        // todo: ofNullable?????
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findCustomerByEmail(email));
        if(!customerOptional.isPresent()){
            throw new PersonException("Customer with this id does not exist");
        }
        return customerOptional.get();
    }

    @Transactional
    public Customer updateCustomer(Long id, CustomerDto customerDto) throws PersonException{
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(!customerOptional.isPresent()){
            throw new PersonException("The customer with this id does not exist");
        }
        Customer customer = customerOptional.get();
        String email = customer.getEmail();
        String username = customer.getUsername();
        String password = customer.getPassword();
        //TODO : CHECK FOR OTHER ATTRIBUTES OF THE CUSTOMER I.E CVV ...
        if(email.equals("") || email == null){
            throw new PersonException("Email cannot be empty");
        }
        if(username.equals("") || username == null){
            throw new PersonException("Username cannot be empty");
        }
        if(password.equals("") || password == null) {
            throw new PersonException("Password cannot be empty");
        }
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        return RepairShopUtil.toList(customerRepository.findAll());
    }

    @Transactional
    public Customer deleteCustomer(Long id) throws PersonException{
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(!customerOptional.isPresent()){
            throw new PersonException("The customer with the given id does not exist");
        }
        Customer customer = customerOptional.get();
        customerRepository.deleteById(id);
        return customer;
    }



    /**
     * Owner
     */
    @Transactional
    public Owner createOwner(String email, String username, String password) throws PersonException{
        String error = getErrorFromData(email, username, password);
        if(!error.equals("")){
            throw new PersonException(error);
        }
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setUsername(username);
        ownerRepository.save(owner);
        return owner;
    }

    @Transactional
    public Owner getOwner(Long id) throws PersonException{
        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        if(!ownerOptional.isPresent()){
            throw new PersonException("The owner with this id does not exist");
        }
        Owner owner = ownerOptional.get();
        return owner;
    }

    @Transactional
    public Owner deleteOwner(Long id) throws PersonException{
        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        if(!ownerOptional.isPresent()){
            throw new PersonException("The customer with the given id does not exist");
        }
        Owner owner = ownerOptional.get();
        customerRepository.deleteById(id);
        return owner;
    }

    @Transactional
    public List<Owner> getAllOwner() {
        return RepairShopUtil.toList(ownerRepository.findAll());
    }

    /**
     * Technician
     */
    @Transactional
    public Technician createTechnician(String email, String username, String password) throws PersonException{
        String error = getErrorFromData(email, username, password);
        if(!error.equals("")){
            throw new PersonException(error);
        }

        Technician technician = new Technician();
        technician.setEmail(email);
        technician.setPassword(password);
        technician.setUsername(username);
        technicianRepository.save(technician);
        return technician;
    }

    @Transactional
    public Technician getTechnician(Long id) throws PersonException{
        Optional<Technician> technicianOptional = technicianRepository.findById(id);
        if(!technicianOptional.isPresent()){
            throw new PersonException("The technician with this id does not exist");
        }
        return technicianOptional.get();
    }

    @Transactional
    public Technician deleteTechnician(Long id) throws PersonException{
        Optional<Technician> technicianOptional = technicianRepository.findById(id);
        if(!technicianOptional.isPresent()){
            throw new PersonException("The customer with the given id does not exist");
        }
        Technician technician = technicianOptional.get();
        customerRepository.deleteById(id);
        return technician;
    }

    @Transactional
    public List<Technician> getAllTechnician() {
        return RepairShopUtil.toList(technicianRepository.findAll());
    }

    /**
     * Administrator
     */
    @Transactional
    public Administrator createAdministrator(String email, String username, String password) throws PersonException{
        String error = getErrorFromData(email, username, password);
        if(!error.equals("")){
            throw new PersonException(error);
        }
        Administrator administrator = new Administrator();
        administrator.setEmail(email);
        administrator.setPassword(password);
        administrator.setUsername(username);
        administratorRepository.save(administrator);
        return administrator;
    }

    @Transactional
    public Administrator getAdministrator(Long id) throws PersonException {
        Optional<Administrator> administratorOptional = administratorRepository.findById(id);
        if(!administratorOptional.isPresent()){
            throw new PersonException("The administrator with the id does not exist");
        }
        return administratorOptional.get();
    }

    @Transactional
    public Administrator deleteAdministrator(Long id) throws PersonException{
        Optional<Administrator> administratorOptional = administratorRepository.findById(id);
        if(!administratorOptional.isPresent()){
            throw new PersonException("The customer with the given id does not exist");
        }
        Administrator administrator = administratorOptional.get();
        customerRepository.deleteById(id);
        return administrator;
    }

    @Transactional
    public List<Administrator> getAllAdministrator() {
        return RepairShopUtil.toList(administratorRepository.findAll());
    }

    //HELPER METHODS
    private String getErrorFromData(String email, String username, String password){
        if(email == null || email.equals("") ){
           return "Email cannot be empty";
        }
        if(username == null || username.equals("") ){
            return "Username cannot be empty";
        }
        if(password == null || password.equals("") ) {
            return "Password cannot be empty";
        }
        return "";
    }
}
