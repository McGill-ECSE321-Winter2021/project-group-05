package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.AdministratorDto;
import ca.mcgill.ecse321.repairshop.dto.CustomerDto;
import ca.mcgill.ecse321.repairshop.dto.OwnerDto;
import ca.mcgill.ecse321.repairshop.dto.TechnicianDto;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.service.PersonService;
import ca.mcgill.ecse321.repairshop.utility.PersonException;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    //CUSTOMER
    /**
     * Registers a new customer
     * @param customerDto
     * @return
     */
    @PostMapping(value = {"/person/customer/register", "/person/customer/register/"})
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto){
        try {
            Customer customer =  personService.createCustomer(customerDto.getEmail(), customerDto.getUsername(), customerDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login customer
     * @param customerDto
     * @return
     */
    @PostMapping(value = {"/person/customer/login", "/person/customer/login/"})
    public ResponseEntity<?> loginCustomer(@RequestBody CustomerDto customerDto){
        try {
            Customer customer =  personService.loginCustomer(customerDto.getEmail(), customerDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * returns a customer with the given email
     * @param email
     * @return
     */
    @GetMapping(value = {"/person/customer/{email}", "/person/customer/{email}/"})
    public ResponseEntity<?> getCustomer(@PathVariable String email){
        try{
            Customer customer = personService.getCustomer(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * updates the information of a customer in the database
     * @param email
     * @param customerDto
     * @return
     */
    @PutMapping(value = {"/person/customer/{email}", "/person/customer/{email}/"})
    public ResponseEntity<?> updateCustomer(@PathVariable String email,  @RequestBody CustomerDto customerDto){
        try {
            Customer customer = personService.updateCustomer(email, customerDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * deletes a customer from the database
     * @param email
     * @return
     */
    @DeleteMapping(value = {"/person/customer/{email}", "/person/customer/{email}/"})
    public ResponseEntity<?> deleteCustomer(@PathVariable String email){
        try {
            Customer customer = personService.deleteCustomer(email);
            return new ResponseEntity<>("Customer has been deleted", HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //TECHINICIAN

    /**
     * Register a new technician
     * @param technicianDto
     * @return
     */
    @PostMapping(value = {"/person/technician/register", "/person/technician/register/"})
    public ResponseEntity<?> createTechnician(@RequestBody TechnicianDto technicianDto){
        try {
            Technician technician =  personService.createTechnician(technicianDto.getEmail(), technicianDto.getUsername(), technicianDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login technician
     * @param technicianDto
     * @return
     */
    @PostMapping(value = {"/person/technician/login", "/person/technician/login/"})
    public ResponseEntity<?> loginTechnician(@RequestBody TechnicianDto technicianDto){
        try {
            Technician technician =  personService.loginTechnician(technicianDto.getEmail(), technicianDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * updates the information of a technician in the database
     * @param email
     * @param technicianDto
     * @return
     */
    @PutMapping(value = {"/person/technician/{email}", "/person/technician/{email}/"})
    public ResponseEntity<?> updateTechnician(@PathVariable String email,  @RequestBody TechnicianDto technicianDto){
        try {
            Technician technician = personService.updateTechnician(email, technicianDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * gets a technician with the given email
     * @param email
     * @return
     */
    @GetMapping(value = {"/person/technician/{email}", "/person/technician/{email}/"})
    public ResponseEntity<?> getTechnician(@PathVariable String email){
        try {
            Technician technician =  personService.getTechnician(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * deletes a technician with the given email
     * @param email
     * @return
     */
    @DeleteMapping(value = {"/person/technician/{email}", "/person/technician/{email}/"})
    public ResponseEntity<?> deleteTechnician(@PathVariable String email){
        try {
            Technician technician =  personService.deleteTechnician(email);
            return new ResponseEntity<>("Technician has been deleted", HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //ADMINISTRATOR

    /**
     * Registers a new administrator
     * @param administratorDto
     * @return
     */
    @PostMapping(value = {"/person/administrator/register", "/person/administrator/register/"})
    public ResponseEntity<?> createAdministrator(@RequestBody AdministratorDto administratorDto){
        try{
            Administrator administrator = personService.createAdministrator(administratorDto.getEmail(), administratorDto.getUsername(), administratorDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login administrator
     * @param administratorDto
     * @return
     */
    @PostMapping(value = {"/person/administrator/login", "/person/administrator/login/"})
    public ResponseEntity<?> loginAdministrator(@RequestBody AdministratorDto administratorDto) {
        try {
            Administrator administrator = personService.loginAdministrator(administratorDto.getEmail(), administratorDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * updates the information of a administrator in the database
     * @param email
     * @param administratorDto
     * @return
     */
    @PutMapping(value = {"/person/administrator/{email}", "/person/administrator/{email}/"})
    public ResponseEntity<?> updateAdministrator(@PathVariable String email,  @RequestBody AdministratorDto administratorDto){
        try {
            Administrator administrator = personService.updateAdministrator(email, administratorDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * gets an administrator
     * @param email
     * @return
     */

    @GetMapping(value = {"/person/administrator/{email}", "/person/administrator/{email}/"})
    public ResponseEntity<?> getAdministrator(@PathVariable String email){
        try {
            Administrator administrator = personService.getAdministrator(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * deletes an administrator
     * @param email
     * @return
     */
    @DeleteMapping(value = {"/person/administrator/{email}", "/person/administrator/{email}/"})
    public ResponseEntity<?> deleteAdministrator(@PathVariable String email){
        try {
            Administrator administrator =  personService.deleteAdministrator(email);
            return new ResponseEntity<>("Administrator has been deleted", HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Owner

    /**
     * Register the owner
     * @param ownerDto
     * @return
     */
    @PostMapping(value = {"/person/owner/register", "/person/owner/register/"})
    public ResponseEntity<?> createOwner(@RequestBody OwnerDto ownerDto){
        try{
            Owner owner = personService.createOwner(ownerDto.getEmail(), ownerDto.getUsername(), ownerDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(owner), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Login Owner
     * @param ownerDto
     * @return
     */
    @PostMapping(value = {"/person/owner/login", "/person/owner/login/"})
    public ResponseEntity<?> loginOwner(@RequestBody OwnerDto ownerDto) {
        try {
            Owner owner = personService.loginOwner(ownerDto.getEmail(), ownerDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(owner), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * updates the information of a owner in the database
     * @param email
     * @param ownerDto
     * @return
     */
    @PutMapping(value = {"/person/owner/{email}", "/person/owner/{email}/"})
    public ResponseEntity<?> updateOwner(@PathVariable String email,  @RequestBody OwnerDto ownerDto){
        try {
            Owner owner = personService.updateOwner(email, ownerDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(owner), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * gets the owner
     * @param email
     * @return
     */
    @GetMapping(value = {"/person/owner/{email}", "/person/owner/{email}/"})
    public ResponseEntity<?> getOwner(@PathVariable String email){
        try{
            Owner owner = personService.getOwner(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(owner), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * deletes the owner
     * @param email
     * @return
     */
    @DeleteMapping(value = {"/person/owner/{email}", "/person/owner/{email}/"})
    public ResponseEntity<?> deleteOwner(@PathVariable String email){
        try {
            Owner owner =  personService.deleteOwner(email);
            return new ResponseEntity<>("Owner has been deleted", HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
