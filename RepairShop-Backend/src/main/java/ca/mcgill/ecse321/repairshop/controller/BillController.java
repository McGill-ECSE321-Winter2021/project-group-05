package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.*;
import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.Bill;
import ca.mcgill.ecse321.repairshop.service.AppointmentService;
import ca.mcgill.ecse321.repairshop.service.BillService;
import ca.mcgill.ecse321.repairshop.service.PersonService;
import ca.mcgill.ecse321.repairshop.utility.BillException;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
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
        try{
            List<Bill> bills = billService.getAllBillsOfCustomer(customerId);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        }catch (BillException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * creates a bill related to an appointment
     * @param appointmentDto
     * @return
     */
    @PostMapping(value = {"/bill/", "/bill"})
    public ResponseEntity<?> createBill(@RequestBody AppointmentDto appointmentDto){
        try{
            Appointment appointment = RepairShopUtil.convertToEntity(appointmentDto);
            Bill bill = billService.createBill(appointment);
            return new ResponseEntity<>(bill, HttpStatus.OK);
        }catch (BillException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = {"/bill/{id}", "/bill/{id"})
    public ResponseEntity<?> updateBill(@RequestBody AppointmentDto appointmentDto){
        try{
            Appointment appointment = RepairShopUtil.convertToEntity(appointmentDto);
            Bill bill = billService.updateBill(appointment);
            return new ResponseEntity<>(bill, HttpStatus.OK);
        }catch (BillException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
