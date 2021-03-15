package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.AppointmentDto;
<<<<<<< HEAD
import ca.mcgill.ecse321.repairshop.dto.CustomerDto;
=======
import ca.mcgill.ecse321.repairshop.model.Appointment;
>>>>>>> ec8db1ecadc66ac1de84fed1333c04140940d199
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
     * @param customerDto
     * @return
     */
    @GetMapping(value = {"/bill/", "/bill"})
    public ResponseEntity<?> getAllBillsOfCustomer(@RequestBody CustomerDto customerDto){
        try{
            List<Bill> bills = billService.getAllBillsOfCustomer(customerDto);
            return new ResponseEntity<>(RepairShopUtil.convertBillToDto(bills), HttpStatus.OK);
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
<<<<<<< HEAD
            Bill bill = billService.createBill(appointmentDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(bill), HttpStatus.OK);
=======
            Appointment appointment = RepairShopUtil.convertToEntity(appointmentDto);
            Bill bill = billService.createBill(appointment);
            return new ResponseEntity<>(bill, HttpStatus.OK);
>>>>>>> ec8db1ecadc66ac1de84fed1333c04140940d199
        }catch (BillException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = {"/bill/{id}", "/bill/{id"})
    public ResponseEntity<?> updateBill(@RequestBody AppointmentDto appointmentDto){
        try{
<<<<<<< HEAD
            Bill bill = billService.updateBill(appointmentDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(bill), HttpStatus.OK);
=======
            Appointment appointment = RepairShopUtil.convertToEntity(appointmentDto);
            Bill bill = billService.updateBill(appointment);
            return new ResponseEntity<>(bill, HttpStatus.OK);
>>>>>>> ec8db1ecadc66ac1de84fed1333c04140940d199
        }catch (BillException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
