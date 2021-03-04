package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping(value = {"/customer"})
    public void createCustomer(@RequestBody Customer customer){
        personService.createCustomer(customer);
    }

    @GetMapping(path = "/customer")
    public List<Customer> getC(){
      return personService.getAllCustomer();
    }
}
