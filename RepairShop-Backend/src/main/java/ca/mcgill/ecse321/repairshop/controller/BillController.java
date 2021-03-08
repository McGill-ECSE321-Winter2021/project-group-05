package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.BillDto;
import ca.mcgill.ecse321.repairshop.service.AppointmentService;
import ca.mcgill.ecse321.repairshop.service.BillService;
import ca.mcgill.ecse321.repairshop.service.PersonService;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Gets all the bill associated with a customer
     */

}
