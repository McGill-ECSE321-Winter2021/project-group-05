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
        //check if email hasn't been taken
        if(customerRepository.findCustomerByEmail(email) != null){
            throw new PersonException("This email has already been taken, try a different one");
        }
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setUsername(username);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public Customer loginCustomer(String email, String password) throws PersonException{
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findCustomerByEmail(email));
        if(!customerOptional.isPresent()){
            throw new PersonException("Customer does not exist");
        }
        Customer customer = customerOptional.get();
        if(!customer.getPassword().equals(password)){
            throw new PersonException("Incorrect password");
        }
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
    public Customer updateCustomer(String email, CustomerDto customerDto) throws PersonException{
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findCustomerByEmail(email));
        if(!customerOptional.isPresent()){
            throw new PersonException("The customer with this id does not exist");
        }
        Customer customer = customerOptional.get();

        String username = customerDto.getUsername();
        String password = customerDto.getPassword();
        //TODO : CHECK FOR OTHER ATTRIBUTES OF THE CUSTOMER I.E CVV ...
        String error = getErrorFromData(email, username, password);
        if(!error.equals("")){
            throw new PersonException(error);
        }
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setEmail(customerDto.getEmail());
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        return RepairShopUtil.toList(customerRepository.findAll());
    }

    @Transactional
    public Customer deleteCustomer(String email) throws PersonException{
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findCustomerByEmail(email));
        if(!customerOptional.isPresent()){
            throw new PersonException("The customer with the given id does not exist");
        }
        Customer customer = customerOptional.get();
        customerRepository.deleteById(email);
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
        if(ownerRepository.findOwnerByEmail(email) != null){
            throw new PersonException("This email has already been taken, try a different one");
        }
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setUsername(username);
        ownerRepository.save(owner);
        return owner;
    }

    @Transactional
    public Owner loginOwner(String email, String password) throws PersonException{
        Optional<Owner> customerOptional = Optional.ofNullable(ownerRepository.findOwnerByEmail(email));
        if(!customerOptional.isPresent()){
            throw new PersonException("Customer does not exist");
        }
        Owner owner = customerOptional.get();
        if(!owner.getPassword().equals(password)){
            throw new PersonException("Incorrect password");
        }
        return owner;
    }

    @Transactional
    public Owner getOwner(String email) throws PersonException{
        Optional<Owner> ownerOptional = Optional.ofNullable(ownerRepository.findOwnerByEmail(email));
        if(!ownerOptional.isPresent()){
            throw new PersonException("The owner with this id does not exist");
        }
        Owner owner = ownerOptional.get();
        return owner;
    }

    @Transactional
    public Owner deleteOwner(String email) throws PersonException{
        Optional<Owner> ownerOptional = Optional.ofNullable(ownerRepository.findOwnerByEmail(email));
        if(!ownerOptional.isPresent()){
            throw new PersonException("The customer with the given id does not exist");
        }
        Owner owner = ownerOptional.get();
        customerRepository.deleteById(email);
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
        if(technicianRepository.findTechnicianByEmail(email) != null){
            throw new PersonException("This email has already been taken, try a different one");
        }

        Technician technician = new Technician();
        technician.setEmail(email);
        technician.setPassword(password);
        technician.setUsername(username);
        technicianRepository.save(technician);
        return technician;
    }

    @Transactional
    public Technician loginTechnician(String email, String password) throws PersonException{
        Optional<Technician> technicianOptional = Optional.ofNullable(technicianRepository.findTechnicianByEmail(email));
        if(!technicianOptional.isPresent()){
            throw new PersonException("Customer does not exist");
        }
        Technician technician = technicianOptional.get();
        if(!technician.getPassword().equals(password)){
            throw new PersonException("Incorrect password");
        }
        return technician;
    }

    @Transactional
    public Technician getTechnician(String email) throws PersonException{
        Optional<Technician> technicianOptional = Optional.ofNullable(technicianRepository.findTechnicianByEmail(email));
        if(!technicianOptional.isPresent()){
            throw new PersonException("The technician with this id does not exist");
        }
        return technicianOptional.get();
    }

    @Transactional
    public Technician deleteTechnician(String email) throws PersonException{
        Optional<Technician> technicianOptional = Optional.ofNullable(technicianRepository.findTechnicianByEmail(email));
        if(!technicianOptional.isPresent()){
            throw new PersonException("The customer with the given id does not exist");
        }
        Technician technician = technicianOptional.get();
        customerRepository.deleteById(email);
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

        if(administratorRepository.findAdministratorByEmail(email) != null){
            throw new PersonException("This email has already been taken, try a different one");
        }
        Administrator administrator = new Administrator();
        administrator.setEmail(email);
        administrator.setPassword(password);
        administrator.setUsername(username);
        administratorRepository.save(administrator);
        return administrator;
    }

    @Transactional
    public Administrator loginAdministrator(String email, String password) throws PersonException{
        Optional<Administrator> administratorOptional = Optional.ofNullable(administratorRepository.findAdministratorByEmail(email));
        if(!administratorOptional.isPresent()){
            throw new PersonException("Administrator does not exist");
        }
        Administrator administrator = administratorOptional.get();
        if(!administrator.getPassword().equals(password)){
            throw new PersonException("Incorrect password");
        }
        return administrator;
    }

    @Transactional
    public Administrator getAdministrator(String email) throws PersonException {
        Optional<Administrator> administratorOptional = Optional.ofNullable(administratorRepository.findAdministratorByEmail(email));
        if(!administratorOptional.isPresent()){
            throw new PersonException("The administrator with the id does not exist");
        }
        return administratorOptional.get();
    }

    @Transactional
    public Administrator deleteAdministrator(String email) throws PersonException{
        Optional<Administrator> administratorOptional = Optional.ofNullable(administratorRepository.findAdministratorByEmail(email));
        if(!administratorOptional.isPresent()){
            throw new PersonException("The customer with the given id does not exist");
        }
        Administrator administrator = administratorOptional.get();
        customerRepository.deleteById(email);
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
        // EMAIL VALIDATION: there must be @
        if (!email.contains("@")){
            return "Email is not valid";
        }
        return "";
    }
}
