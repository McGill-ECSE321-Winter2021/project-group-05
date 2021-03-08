package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.BusinessDto;
import ca.mcgill.ecse321.repairshop.dto.TimeSlotDto;
import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.service.BusinessService;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BusinessController {
    @Autowired
    BusinessService businessService;

    /**
     * Gets the business of the system given the name
     * @param name
     * @return
     */
    @GetMapping(value = {"/business/{name}", "/business/{name}/"})
    public BusinessDto getBusiness(@PathVariable("name") String name){
        Business business = businessService.getBusiness(name);
        return RepairShopUtil.convertToDto(business);
    }

    /**
     * Creates a business
     * @param name
     * @param address
     * @param phoneNumber
     * @param email
     * @param timeSlotDtoList
     * @return
     */
    @PostMapping(value = {"/business/{name}", "/business/{name}/"})
    public BusinessDto createBusiness(String name, String address, String phoneNumber, String email, List<TimeSlotDto> timeSlotDtoList){
        return null;
    }
}
