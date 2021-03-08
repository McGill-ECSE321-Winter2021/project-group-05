package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.AppointmentDto;
import ca.mcgill.ecse321.repairshop.dto.BillDto;
import ca.mcgill.ecse321.repairshop.dto.CustomerDto;
import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.Bill;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.service.AppointmentService;
import ca.mcgill.ecse321.repairshop.service.BillService;
import ca.mcgill.ecse321.repairshop.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class BillController {
    @Autowired
    private PersonService personService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private BillService billService;

    @PostMapping(value = { "/bill", "/bill/" })
    public BillDto createBill(@PathVariable("id") Long id,
                              @RequestParam(name="customer") CustomerDto customerDto,
                              @RequestParam(name="appointment")AppointmentDto appointmentDto)
            throws IllegalArgumentException {
        Customer customer = personService.getCustomer(customerDto.getId());
        Appointment appointment = appointmentService.getAppointment(appointmentDto.getId());
        float totalCost = 0;
        Bill bill = billService.createBill(customer, appointment, totalCost, appointment.getTimeslot().getDate());
        return null; // todo: convert to dto
    }
}
