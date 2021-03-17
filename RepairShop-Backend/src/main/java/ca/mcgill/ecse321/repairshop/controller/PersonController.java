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
     * creates a customer
     * @param customerDto
     * @return
     */
    @PostMapping(value = {"/person/customer/", "/person/customer"})
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto){
        try {
            Customer customer =  personService.createCustomer(customerDto.getEmail(), customerDto.getUsername(), customerDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    /**
     * returns a customer with the given id
     * @param email
     * @return
     */
    @GetMapping(value = {"/person/customer/{email}", "/person/customer/{email}/"})
    public ResponseEntity<?> getCustomer(@PathVariable String email){
        try{
            Customer customer = personService.getCustomer(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    /**
     * updates the information of a customer in the database
     * @param email
     * @param customerDto
     * @return
     */
    @PutMapping(value = {"/person/customer/{email}", "/person/customer/{email}/"})
    public ResponseEntity<?> updateCustomer(@PathVariable String email,  CustomerDto customerDto){
        try {
            Customer customer = personService.updateCustomer(email, customerDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    /**
     * deletes a customer from the database
     * @param email
     * @return
     */
    @DeleteMapping(value = {"/person/customer/{id}", "/person/customer/{id}/"})
    public ResponseEntity<?> deleteCustomer(@PathVariable String email){
        try {
            Customer customer = personService.deleteCustomer(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(customer), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    //TECHINICIAN

    /**
     * creates a technician
     * @param technicianDto
     * @return
     */
    @PostMapping(value = {"/person/technician", "/person/technician/"})
    public ResponseEntity<?> createTechnician(TechnicianDto technicianDto){
        try {
            Technician technician =  personService.createTechnician(technicianDto.getEmail(), technicianDto.getUsername(), technicianDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    /**
     * gets a technician with the given id
     * @param email
     * @return
     */
    @GetMapping(value = {"/person/technician/{email}", "/person/technician/{email}/"})
    public ResponseEntity<?> getTechnician(@PathVariable String email){
        try {
            Technician technician =  personService.getTechnician(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    /**
     * deletes a technician with the given id
     * @param email
     * @return
     */
    @DeleteMapping(value = {"/person/technician/{email}", "/person/technician/{email}/"})
    public ResponseEntity<?> deleteTechnician(@PathVariable String email){
        try {
            Technician technician =  personService.deleteTechnician(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(technician), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    //ADMINISTRATOR

    /**
     * creates an administrator
     * @param administratorDto
     * @return
     */
    @PostMapping(value = {"/person/administrator/", "/person/administrator"})
    public ResponseEntity<?> createAdministrator(AdministratorDto administratorDto){
        try{
            Administrator administrator = personService.createAdministrator(administratorDto.getEmail(), administratorDto.getUsername(), administratorDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    /**
     * gets an administrator
     * @param email
     * @return
     */
    @GetMapping(value = {"/person/administrator/{email}", "/person/administrator/{email}/"})
    public ResponseEntity<?> getAdministrator(String email){
        try {
            Administrator administrator = personService.getAdministrator(email);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
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
            return new ResponseEntity<>(RepairShopUtil.convertToDto(administrator), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }


    //Owner

    /**
     * creates the owner
     * @param ownerDto
     * @return
     */
    @PostMapping(value = {"/person/owner/", "/person/owner"})
    public ResponseEntity<?> createOwner(OwnerDto ownerDto){
        try{
            Owner owner = personService.createOwner(ownerDto.getEmail(), ownerDto.getUsername(), ownerDto.getPassword());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(owner), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
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
            return new ResponseEntity<>(RepairShopUtil.convertToDto(owner), HttpStatus.OK);
        } catch (PersonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

}
