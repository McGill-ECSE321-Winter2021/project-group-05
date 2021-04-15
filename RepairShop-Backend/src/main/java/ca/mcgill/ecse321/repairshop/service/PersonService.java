package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.AdministratorRepository;
import ca.mcgill.ecse321.repairshop.dao.CustomerRepository;
import ca.mcgill.ecse321.repairshop.dao.TechnicianRepository;
import ca.mcgill.ecse321.repairshop.dto.AdministratorDto;
import ca.mcgill.ecse321.repairshop.dto.CustomerDto;
import ca.mcgill.ecse321.repairshop.dto.TechnicianDto;
import ca.mcgill.ecse321.repairshop.model.*;
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
    TechnicianRepository technicianRepository;
    @Autowired
    CustomerRepository customerRepository;

    /**
     * creates a customer account
     *
     * @param email customer email
     * @param username customer username
     * @param password customer password
     * @return customer
     * @throws PersonException
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
        customer.setPersonType(PersonType.CUSTOMER);
        customerRepository.save(customer);
        return customer;
    }

    /**
     * logs in a customer to an existing account
     *
     * @param email customer email
     * @param password customer password
     * @return customer
     * @throws PersonException
     */
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

    /**
     * returns a customer
     *
     * @param email customer email
     * @return customer
     * @throws PersonException
     */
    @Transactional
    public Customer getCustomer(String email) throws PersonException{
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findCustomerByEmail(email));
        if(!customerOptional.isPresent()){
            throw new PersonException("Customer with this email does not exist");
        }
        return customerOptional.get();
    }

    /**
     * updates a customer account
     *
     * @param email customer email
     * @param customerDto customer transfer object
     * @return customer
     * @throws PersonException
     */
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
        customer.setCardNumber(customerDto.getCardNumber());
        customer.setCvv(customerDto.getCvv());
        customer.setExpiry(customerDto.getExpiry());
        customerRepository.save(customer);
        return customer;
    }

    /**
     * gets all customers
     *
     * @return list of customers
     */
    @Transactional
    public List<Customer> getAllCustomers() {
        return RepairShopUtil.toList(customerRepository.findAll());
    }

    /**
     * deletes a customer account
     *
     * @param email customer email
     * @return customer
     * @throws PersonException
     */
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
     * creates a technician
     *
     * @param email technician email
     * @param username tech username
     * @param password tech password
     * @return technician
     * @throws PersonException
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
        technician.setPersonType(PersonType.TECHNICIAN);
        technicianRepository.save(technician);
        return technician;
    }

    /**
     * login to tech account
     *
     * @param email tech email
     * @param password tech password
     * @return technician
     * @throws PersonException
     */
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

    /**
     * finds a technician
     *
     * @param email technician email
     * @return technician
     * @throws PersonException
     */
    @Transactional
    public Technician getTechnician(String email) throws PersonException{
        Optional<Technician> technicianOptional = Optional.ofNullable(technicianRepository.findTechnicianByEmail(email));
        if(!technicianOptional.isPresent()){
            throw new PersonException("Technician with this email does not exist");
        }
        return technicianOptional.get();
    }

    /**
     * deletes a tech account
     *
     * @param email technician email
     * @return technician
     * @throws PersonException
     */
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

    /**
     * returns all technicians
     *
     * @return list of technicians
     */
    @Transactional
    public List<Technician> getAllTechnician() {
        return RepairShopUtil.toList(technicianRepository.findAll());
    }

    /**
     * updates technician account
     *
     * @param email tehcnician email
     * @param technicianDto transfer object
     * @return technician
     * @throws PersonException
     */
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

    /**
     * creates admin account
     *
     * @param email admin email
     * @param username admin username
     * @param password admin password
     * @return admin
     * @throws PersonException
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
        administrator.setPersonType(PersonType.ADMIN);
        administratorRepository.save(administrator);
        return administrator;
    }

    /**
     * login to admin account
     *
     * @param email admin email
     * @param password admin password
     * @return admin
     * @throws PersonException
     */
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

    /**
     * find admin
     *
     * @param email admin email
     * @return admin
     * @throws PersonException
     */
    @Transactional
    public Administrator getAdministrator(String email) throws PersonException {
        Optional<Administrator> administratorOptional = Optional.ofNullable(administratorRepository.findAdministratorByEmail(email));
        if(!administratorOptional.isPresent()){
            throw new PersonException("Administrator with this email does not exist");
        }
        return administratorOptional.get();
    }

    /**
     * delete admin account
     *
     * @param email admin email
     * @return admin
     * @throws PersonException
     */
    @Transactional
    public Administrator deleteAdministrator(String email) throws PersonException{
        Optional<Administrator> administratorOptional = Optional.ofNullable(administratorRepository.findAdministratorByEmail(email));
        if(!administratorOptional.isPresent()){
            throw new PersonException("The Administrator with the given id does not exist");
        }
        Administrator administrator = administratorOptional.get();
        Long id = administrator.getId();
        administratorRepository.deleteById(id);
        return administrator;
    }

    /**
     * get a list of all admins
     *
     * @return list of admins
     */
    @Transactional
    public List<Administrator> getAllAdministrator() {return RepairShopUtil.toList(administratorRepository.findAll()); }

    /**
     * update admin account
     *
     * @param email admin email
     * @param administratorDto transfer object
     * @return admin
     * @throws PersonException
     */
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

    /**
     * return error string
     *
     * @param email user string
     * @param username username
     * @param password password
     * @return error (String)
     */
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

    /**
     * checks for duplicates of the email
     *
     * @param email user email
     * @return String
     */
    private String checkDuplicateEmail(String email){
        if(customerRepository.findCustomerByEmail(email) == null
                || administratorRepository.findAdministratorByEmail(email) == null
                || technicianRepository.findTechnicianByEmail(email) == null){
            return "";
        }
        return "Email has been taken";
    }
}
