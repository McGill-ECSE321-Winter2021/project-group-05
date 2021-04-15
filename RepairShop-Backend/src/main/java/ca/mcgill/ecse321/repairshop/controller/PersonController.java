package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.AdministratorDto;
import ca.mcgill.ecse321.repairshop.dto.CustomerDto;
import ca.mcgill.ecse321.repairshop.dto.TechnicianDto;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.service.PersonService;
import ca.mcgill.ecse321.repairshop.utility.PersonException;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    // CUSTOMER
    /**
     * create customer method registers a new customer
     * used for the web
     *
     * @param customerDto the customer transfer object
     * @return response entity
     */
    @PostMapping(value = { "/person/customer/register", "/person/customer/register/" })
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto) {
        try {
            Customer customer = personService.createCustomer(customerDto.getEmail(), customerDto.getUsername(),
                    customerDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * create customer method registers a new customer
     * used for the app
     *
     * @param username the username of the new customer
     * @param password the password of the new customer
     * @param email the email of the new customer
     * @return response entity
     */
    @PostMapping(value = { "/person/customer/register/app", "/person/customer/register/app/" })
    public ResponseEntity<?> createCustomer(@RequestParam(value="username")String username,
                                            @RequestParam(value="email") String email,
                                            @RequestParam(value="password") String password) {
        try {
            Customer customer = personService.createCustomer(email, username,
                    password);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login customer method logs in a customer to their account
     * Used in the web
     *
     * @param customerDto  the customer transfer object
     * @return response entity
     */
    @PostMapping(value = { "/person/customer/login", "/person/customer/login/" })
    public ResponseEntity<?> loginCustomer(@RequestBody CustomerDto customerDto) {
        try {
            Customer customer = personService.loginCustomer(customerDto.getEmail(), customerDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login customer method logs in a customer to their account
     * Used in the android app
     *
     * @param email  the email of the customer
     * @param password password of the customer
     * @return response entity
     */
    @PostMapping(value = { "/person/customer/login/app", "/person/customer/login/app/" })
    public ResponseEntity<?> loginCustomer(@RequestParam(value="email")String email, @RequestParam(value="password") String password ) {
        try {
            Customer customer = personService.loginCustomer(email, password);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * get customer method returns a customer with the given email
     * 
     * @param email the customer email
     * @return customer response entity
     */
    @GetMapping(value = { "/person/customer/{email}", "/person/customer/{email}/" })
    public ResponseEntity<?> getCustomer(@PathVariable String email) {
        try {
            Customer customer = personService.getCustomer(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * update customer method updates the information of a customer in the database
     * used for the web
     *
     * @param email the customer email
     * @param customerDto the customer transfer object
     * @return response entity
     */
    @PutMapping(value = { "/person/customer/{email}", "/person/customer/{email}/" })
    public ResponseEntity<?> updateCustomer(@PathVariable String email, @RequestBody CustomerDto customerDto) {
        try {
            Customer customer = personService.updateCustomer(email, customerDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * update customer method updates the information of a customer in the database
     * used for the app
     *
     * @param oldEmail the customer's old email
     * @param newEmail the customer's new email
     * @param password the customer's new password
     * @param username the customer's new username
     * @return response entity
     */
    @PutMapping(value = { "/person/customer/", "/person/customer" })
    public ResponseEntity<?> updateCustomer(@RequestParam(value="oldEmail") String oldEmail,
                                            @RequestParam(value="newEmail") String newEmail,
                                            @RequestParam(value="password")String password,
                                            @RequestParam(value="username")String username) {
        try {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setEmail(newEmail);
            customerDto.setPassword(password);
            customerDto.setUsername(username);
            Customer customer = personService.updateCustomer(oldEmail, customerDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete customer method deletes a customer from the database
     * 
     * @param email customer email
     * @return response entity
     */
    @DeleteMapping(value = { "/person/customer/{email}", "/person/customer/{email}/" })
    public ResponseEntity<?> deleteCustomer(@PathVariable String email) {
        try {
            Customer customer = personService.deleteCustomer(email);
            return new ResponseEntity<>("Customer has been deleted", HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // TECHINICIAN

    /**
     * create technician method Register a new technician
     * 
     * @param technicianDto technician transfer object
     * @return response entity technician
     */
    @PostMapping(value = { "/person/technician/register", "/person/technician/register/" })
    public ResponseEntity<?> createTechnician(@RequestBody TechnicianDto technicianDto) {
        try {
            Technician technician = personService.createTechnician(technicianDto.getEmail(),
                    technicianDto.getUsername(), technicianDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login technician method logs in the technician
     * used for the website
     *
     * @param technicianDto technician transfer object
     * @return response entity
     */
    @PostMapping(value = { "/person/technician/login", "/person/technician/login/" })
    public ResponseEntity<?> loginTechnician(@RequestBody TechnicianDto technicianDto) {
        try {
            Technician technician = personService.loginTechnician(technicianDto.getEmail(),
                    technicianDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login technician method logs in the technician
     * used for the app
     *
     * @param email email of the technician
     * @param password password of the technician
     * @return response entity
     */
    @PostMapping(value = { "/person/technician/login/app", "/person/technician/login/app/" })
    public ResponseEntity<?> loginTechnician(@RequestParam(value="email")String email,
                                             @RequestParam(value="password") String password) {
        try {
            Technician technician = personService.loginTechnician(email, password);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * update technician method updates the information of a technician in the database
     * used for website
     *
     * @param email the technician email
     * @param technicianDto the technician transfer object
     * @return response entity
     */
    @PutMapping(value = { "/person/technician/{email}", "/person/technician/{email}/" })
    public ResponseEntity<?> updateTechnician(@PathVariable String email, @RequestBody TechnicianDto technicianDto) {
        try {
            Technician technician = personService.updateTechnician(email, technicianDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * update technician method updates the information of a technician in the database
     * used for app
     *
     * @param oldEmail the technician's old email
     * @param newEmail the technician's new email
     * @param password the technician's new password
     * @param username the technician's new password
     * @return response entity
     */
    @PutMapping(value = { "/person/technician", "/person/technician/" })
    public ResponseEntity<?> updateTechnician(@RequestParam(value="oldEmail") String oldEmail,
                                              @RequestParam(value="newEmail") String newEmail,
                                              @RequestParam(value="password") String password,
                                              @RequestParam(value="username") String username) {
        try {
            TechnicianDto technicianDto = new TechnicianDto();
            technicianDto.setEmail(newEmail);
            technicianDto.setPassword(password);
            technicianDto.setUsername(username);

            Technician technician = personService.updateTechnician(oldEmail, technicianDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * get technician method gets a technician with the given email
     * 
     * @param email technician email
     * @return response entity
     */
    @GetMapping(value = { "/person/technician/{email}", "/person/technician/{email}/" })
    public ResponseEntity<?> getTechnician(@PathVariable String email) {
        try {
            Technician technician = personService.getTechnician(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete technician deletes a technician with the given email
     * 
     * @param email technician email
     * @return response entity
     */
    @DeleteMapping(value = { "/person/technician/{email}", "/person/technician/{email}/" })
    public ResponseEntity<?> deleteTechnician(@PathVariable String email) {
        try {
            Technician technician = personService.deleteTechnician(email);
            return new ResponseEntity<>("Technician has been deleted", HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // ADMINISTRATOR

    /**
     * create admin method registers a new administrator
     * 
     * @param administratorDto admin transfer object
     * @return response entity
     */
    @PostMapping(value = { "/person/administrator/register", "/person/administrator/register/" })
    public ResponseEntity<?> createAdministrator(@RequestBody AdministratorDto administratorDto) {
        try {
            Administrator administrator = personService.createAdministrator(administratorDto.getEmail(),
                    administratorDto.getUsername(), administratorDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login administrator method logs in admin to their account
     * used for web
     *
     * @param administratorDto the admin transfer object
     * @return response entity
     */
    @PostMapping(value = { "/person/administrator/login", "/person/administrator/login/" })
    public ResponseEntity<?> loginAdministrator(@RequestBody AdministratorDto administratorDto) {
        try {
            Administrator administrator = personService.loginAdministrator(administratorDto.getEmail(),
                    administratorDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login administrator method logs in admin to their account
     * Used for the App
     *
     * @param email email of the admin
     * @param password password of the admin
     * @return response entity
     */
    @PostMapping(value = { "/person/administrator/login/app", "/person/administrator/login/app/" })
    public ResponseEntity<?> loginAdministrator(@RequestParam(value="email")String email, @RequestParam("password") String password) {
        try {
            Administrator administrator = personService.loginAdministrator(email, password);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * update admin method updates the information of a administrator in the database
     * used for web
     *
     * @param email admin email
     * @param administratorDto admin transfer object
     * @return response entity
     */
    @PutMapping(value = { "/person/administrator/{email}", "/person/administrator/{email}/" })
    public ResponseEntity<?> updateAdministrator(@PathVariable String email,
            @RequestBody AdministratorDto administratorDto) {
        try {
            Administrator administrator = personService.updateAdministrator(email, administratorDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * update admin method updates the information of a administrator in the database
     * used for app
     *
     * @param oldEmail admin's old email
     * @param newEmail admin's new email
     * @param password admin's new password
     * @param username admin's new username
     * @return response entity
     */
    @PutMapping(value = { "/person/administrator", "/person/administrator/" })
    public ResponseEntity<?> updateAdministrator(@RequestParam(value="oldEmail") String oldEmail,
                                                 @RequestParam(value="newEmail") String newEmail,
                                                 @RequestParam(value="password") String password,
                                                 @RequestParam(value="username") String username) {
        try {
            AdministratorDto administratorDto = new AdministratorDto();
            administratorDto.setEmail(newEmail);
            administratorDto.setPassword(password);
            administratorDto.setUsername(username);

            Administrator administrator = personService.updateAdministrator(oldEmail, administratorDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * get an administrator method gets an administrator from an email
     * 
     * @param email admin email
     * @return response entity
     */

    @GetMapping(value = { "/person/administrator/{email}", "/person/administrator/{email}/" })
    public ResponseEntity<?> getAdministrator(@PathVariable String email) {
        try {
            Administrator administrator = personService.getAdministrator(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete admin method deletes an administrator
     * 
     * @param email admin email
     * @return response entity
     */
    @DeleteMapping(value = { "/person/administrator/{email}", "/person/administrator/{email}/" })
    public ResponseEntity<?> deleteAdministrator(@PathVariable String email) {
        try {
            Administrator administrator = personService.deleteAdministrator(email);
            return new ResponseEntity<>("Administrator has been deleted", HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * get all technicans method return list of all technicians
     *
     * @return list of technicians
     */
    @GetMapping(value = {"/person/technician/", "/person/technician"})
    public ResponseEntity<?> getAllTechnicians(){
        List<Technician> technicianList = personService.getAllTechnician();
        List<TechnicianDto> list = new ArrayList<>();
        for(Technician technician : technicianList){
            list.add(RepairShopUtil.convertToDto(technician));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
