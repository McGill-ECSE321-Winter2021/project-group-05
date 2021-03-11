package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.AppointmentDto;
import ca.mcgill.ecse321.repairshop.model.Bill;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.service.AppointmentService;
import ca.mcgill.ecse321.repairshop.service.BillService;
import ca.mcgill.ecse321.repairshop.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BillController {
    @Autowired
    private PersonService personService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private BillService billService;

    /**
     * Gets all the bills of a given customer id
     * @param customerId
     * @return
     */
    @GetMapping(value = {"/bill/{customerId}", "/bill/{customerId}/"})
    public ResponseEntity<?> getAllBillsOfCustomer(@PathVariable("customerId") Long customerId){
        Customer customer = personService.getCustomer(customerId);
        return null; // TODO
    }

    @PostMapping(value = {"/bill/", "/bill"})
    public ResponseEntity<?> createBill(@RequestBody AppointmentDto appointmentDto){
        Bill bill = billService.createBill(appointmentDto);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

}
