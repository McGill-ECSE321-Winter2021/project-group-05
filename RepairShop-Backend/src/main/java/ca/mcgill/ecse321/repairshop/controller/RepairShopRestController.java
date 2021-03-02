package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.service.RepairShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
public class RepairShopRestController {


    @CrossOrigin(origins = "*")
    @RestController
    public class EventRegistrationRestController {

        @Autowired
        private RepairShopService service;

    }
}
