package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.AdministratorRepository;
import ca.mcgill.ecse321.repairshop.dao.CustomerRepository;
import ca.mcgill.ecse321.repairshop.dao.OwnerRepository;
import ca.mcgill.ecse321.repairshop.dao.TechnicianRepository;
import ca.mcgill.ecse321.repairshop.dto.AdministratorDto;
import ca.mcgill.ecse321.repairshop.dto.CustomerDto;
import ca.mcgill.ecse321.repairshop.dto.OwnerDto;
import ca.mcgill.ecse321.repairshop.dto.TechnicianDto;
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
        //check if email has not been taken
        if(!checkDuplicateEmail(email).equals("")){
            throw new PersonException(checkDuplicateEmail(email));
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
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findCustomerByEmail(email));
        if(!customerOptional.isPresent()){
            throw new PersonException("Customer with this email does not exist");
        }
        return customerOptional.get();
    }

    @Transactional
    public Customer updateCustomer(String email, CustomerDto customerDto) throws PersonException{
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findCustomerByEmail(email));
        if(!customerOptional.isPresent()){
            throw new PersonException("The customer with this email does not exist");
        }
        Customer customer = customerOptional.get();

        String username = customerDto.getUsername();
        String password = customerDto.getPassword();
        String error = getErrorFromData(customerDto.getEmail(), username, password);
        if(!error.equals("")){
            throw new PersonException(error);
        }
        //check if email has not been taken
        if(!checkDuplicateEmail(customerDto.getEmail()).equals("")){
            throw new PersonException(checkDuplicateEmail(email));
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
            throw new PersonException("The customer with the given email does not exist");
        }
        Customer customer = customerOptional.get();
        Long id = customer.getId();
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
        //check if email has not been taken
        if(!checkDuplicateEmail(email).equals("")){
            throw new PersonException(checkDuplicateEmail(email));
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
            throw new PersonException("Owner does not exist");
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
            throw new PersonException("owner with this email does not exist");
        }
        Owner owner = ownerOptional.get();
        return owner;
    }

    @Transactional
    public Owner updateOwner(String email, OwnerDto ownerDto) throws PersonException{
        Optional<Owner> ownerOptional = Optional.ofNullable(ownerRepository.findOwnerByEmail(email));
        if(!ownerOptional.isPresent()){
            throw new PersonException("The Owner with this email does not exist");
        }
        Owner owner = ownerOptional.get();

        String username = ownerDto.getUsername();
        String password = ownerDto.getPassword();
        String error = getErrorFromData(ownerDto.getEmail(), username, password);
        if(!error.equals("")){
            throw new PersonException(error);
        }
        //check if email hasn't been taken
        if(!checkDuplicateEmail(ownerDto.getEmail()).equals("")){
            throw new PersonException(checkDuplicateEmail(email));
        }

        owner.setUsername(ownerDto.getUsername());
        owner.setPassword(ownerDto.getPassword());
        owner.setEmail(ownerDto.getEmail());
        ownerRepository.save(owner);
        return owner;
    }

    @Transactional
    public Owner deleteOwner(String email) throws PersonException{
        Optional<Owner> ownerOptional = Optional.ofNullable(ownerRepository.findOwnerByEmail(email));
        if(!ownerOptional.isPresent()){
            throw new PersonException("The customer with the given id does not exist");
        }
        Owner owner = ownerOptional.get();
        Long id = owner.getId();
        ownerRepository.deleteById(id);
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
        if(!checkDuplicateEmail(email).equals("")){
            throw new PersonException(checkDuplicateEmail(email));
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
            throw new PersonException("Technician does not exist");
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
            throw new PersonException("Technician with this email does not exist");
        }
        return technicianOptional.get();
    }

    @Transactional
    public Technician deleteTechnician(String email) throws PersonException{
        Optional<Technician> technicianOptional = Optional.ofNullable(technicianRepository.findTechnicianByEmail(email));
        if(!technicianOptional.isPresent()){
            throw new PersonException("The technician with the this email does not exist");
        }
        Technician technician = technicianOptional.get();
        Long id = technician.getId();
        technicianRepository.deleteById(id);
        return technician;
    }

    @Transactional
    public List<Technician> getAllTechnician() {
        return RepairShopUtil.toList(technicianRepository.findAll());
    }

    @Transactional
    public Technician updateTechnician(String email, TechnicianDto technicianDto) throws PersonException{
        Optional<Technician> technicianOptional = Optional.ofNullable(technicianRepository.findTechnicianByEmail(email));
        if(!technicianOptional.isPresent()){
            throw new PersonException("The Technician with this email does not exist");
        }
        Technician technician = technicianOptional.get();

        String username = technicianDto.getUsername();
        String password = technicianDto.getPassword();
        String error = getErrorFromData(technicianDto.getEmail(), username, password);
        if(!error.equals("")){
            throw new PersonException(error);
        }
        //check if email hasn't been taken
        if(!checkDuplicateEmail(technicianDto.getEmail()).equals("")){
            throw new PersonException(checkDuplicateEmail(email));
        }

        technician.setUsername(technicianDto.getUsername());
        technician.setPassword(technicianDto.getPassword());
        technician.setEmail(technicianDto.getEmail());
        technicianRepository.save(technician);
        return technician;
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
        if(!checkDuplicateEmail(email).equals("")){
            throw new PersonException(checkDuplicateEmail(email));
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
            throw new PersonException("Administrator with this email does not exist");
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
        Long id = administrator.getId();
        administratorRepository.deleteById(id);
        return administrator;
    }

    @Transactional
    public List<Administrator> getAllAdministrator() {return RepairShopUtil.toList(administratorRepository.findAll()); }

    @Transactional
    public Administrator updateAdministrator(String email, AdministratorDto administratorDto) throws PersonException{
        Optional<Administrator> technicianOptional = Optional.ofNullable(administratorRepository.findAdministratorByEmail(email));
        if(!technicianOptional.isPresent()){
            throw new PersonException("The Administrator with this email does not exist");
        }
        Administrator administrator = technicianOptional.get();

        String username = administratorDto.getUsername();
        String password = administratorDto.getPassword();
        String error = getErrorFromData(administratorDto.getEmail(), username, password);
        if(!error.equals("")){
            throw new PersonException(error);
        }
        //check if email hasn't been taken
        if(!checkDuplicateEmail(administratorDto.getEmail()).equals("")){
            throw new PersonException(checkDuplicateEmail(email));
        }

        administrator.setUsername(administratorDto.getUsername());
        administrator.setPassword(administratorDto.getPassword());
        administrator.setEmail(administratorDto.getEmail());
        administratorRepository.save(administrator);
        return administrator;
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

    private String checkDuplicateEmail(String email){
        if(customerRepository.findCustomerByEmail(email) == null
                || ownerRepository.findOwnerByEmail(email) == null
                || administratorRepository.findAdministratorByEmail(email) == null
                || technicianRepository.findTechnicianByEmail(email) == null){
            return "";
        }
        return "Email has been taken";
    }
}
