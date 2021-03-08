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
     * Gets the business of the system given the name
     * @param id
     * @return
     */
    @GetMapping(value = {"/business/{id}", "/business/{id}/"})
    public BusinessDto getBusiness(@PathVariable("id") Long id){
        Business business = businessService.getBusiness(id);
        return RepairShopUtil.convertToDto(business);
    }

    /**
     * Gets the busines of the system
     * @return
     */
    @GetMapping(value = {"/business/", "/business"})
    public ResponseEntity<?> getBusiness(){
        try {
            Business business = businessService.getBusiness();
            return new ResponseEntity<>(RepairShopUtil.convertToDto(business), HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * creates a business if it does not exist
     * @param businessDto
     * @return
     */
    @PostMapping(value = {"/business", "/businesss/"})
    public ResponseEntity<?> createBusiness(@RequestBody BusinessDto businessDto) {
        try{
            Business business = businessService.createBusiness(businessDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(business), HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * edits the business informations
     * @param id
     * @param businessDto
     * @return
     */
    @PutMapping(value = {"/business/{id}", "/business/{name}/"})
    public ResponseEntity<?> updateBusiness(@PathVariable("id") Long id, @RequestBody BusinessDto businessDto){
        try {
            Business business = businessService.editBusiness(id, businessDto);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(business), HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * deletes a business || NOTE: only for test purpose
     * @param id
     * @return
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
