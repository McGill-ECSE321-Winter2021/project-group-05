package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.BusinessDto;
import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.service.BusinessService;
import ca.mcgill.ecse321.repairshop.utility.BusinessException;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
public class BusinessController {
    @Autowired
    BusinessService businessService;

    /**
     * get business method gets the business of the system given the name
     *
     * @param id the business id
     * @return response entity
     */
    @GetMapping(value = {"/business/{id}", "/business/{id}/"})
    public ResponseEntity<?> getBusiness(@PathVariable("id") Long id){
        Business business = businessService.getBusiness(id);
        return new ResponseEntity<>(business, HttpStatus.OK);
    }

    /**
     * create business method creates a business if it does not exist
     *
     * @param businessDto is the transfer object for the business
     * @return response entity
     */
    @PostMapping(value = {"/business", "/business/"})
    public ResponseEntity<?> createBusiness(@RequestBody BusinessDto businessDto) {
        try{
            Business business = businessService.createBusiness(businessDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(business), HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * update business method edits the business informations
     *
     * @param id the business id
     * @param businessDto the business transfer object
     * @return response entity
     */
    @PutMapping(value = {"/business/{id}", "/business/{id}/"})
    public ResponseEntity<?> updateBusiness(@PathVariable("id") Long id, @RequestBody BusinessDto businessDto){
        try {
            Business business = businessService.editBusiness(id, businessDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(business), HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete business method deletes a business || NOTE: only for test purpose
     *
     * @param id the business id
     * @return response entity
     */
    @DeleteMapping(value = {"/business/{id}", "/business/{id}/"})
    public ResponseEntity<?> deleteBusiness(@PathVariable("id") Long id){
        try {
            businessService.deleteBusiness(id);
            return new ResponseEntity<>("Business has been successfully deleted", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
