package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.BookableServiceDto;
import ca.mcgill.ecse321.repairshop.model.BookableService;
import ca.mcgill.ecse321.repairshop.service.RepairShopService;
import ca.mcgill.ecse321.repairshop.utility.BookableServiceException;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ServiceController {
    @Autowired
    private RepairShopService repairShopService;

    /**
     * create bookable service creates a new service
     *
     * @param bookableServiceDto service transfer object as RequestBody
     * @return response entity
     */
    @PostMapping(value = { "/bookableService", "/bookableService/" })
    public ResponseEntity<?> createBookableService(@RequestBody BookableServiceDto bookableServiceDto) {
        try {
           BookableService bookableService = repairShopService.createService(bookableServiceDto.getName(), bookableServiceDto.getCost(),
                   bookableServiceDto.getDuration());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(bookableService), HttpStatus.OK);
        }catch (BookableServiceException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * create bookable service creates a new service
     *
     * @param serviceName as RequestParams
     * @param serviceCost as RequestParams
     * @param serviceDuration as RequestParams
     * @return response entity
     */
    @PostMapping(value = { "/bookableService/app", "/bookableService/app/" })
    public ResponseEntity<?> createBookableService(@RequestParam(value = "serviceName") String serviceName,
                                                   @RequestParam(value = "serviceCost") String serviceCost,
                                                   @RequestParam(value = "serviceDuration") String serviceDuration) {
        try {

            float cost = Float.valueOf(serviceCost);
            int duration = Integer.valueOf(serviceDuration);

            BookableService bookableService = repairShopService.createService(serviceName, cost,
                    duration);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(bookableService), HttpStatus.OK);
        }catch (BookableServiceException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * edit service method edits an existing service
     *
     * @param name service name
     * @param bookableServiceDto service transfer object
     * @return response entity
     * @throws IllegalArgumentException
     * @throws BookableServiceException
     */
    @PutMapping(value = { "/bookableService/{name}", "/bookableService/{name}/" })
    public ResponseEntity<?> editBookableService(@PathVariable("name") String name,
                                @RequestBody BookableServiceDto bookableServiceDto)
            throws IllegalArgumentException, BookableServiceException {
      try{
          BookableService originalService = repairShopService.getService(name);

           BookableService newService =  repairShopService.editService(originalService, bookableServiceDto.getName(),
                   bookableServiceDto.getCost(), bookableServiceDto.getDuration());
           return new ResponseEntity<>(RepairShopUtil.convertToDto(newService), HttpStatus.OK);
        }
      catch (BookableServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * edit service method edits an existing service
     *
     * @param oldName service old name as RequestParam
     * @param newName service new name as RequestParam
     * @param newCost service new cost as RequestParam
     * @param newDuration service new duration as RequestParam
     * @return response entity
     * @throws IllegalArgumentException
     * @throws BookableServiceException
     */
    @PutMapping(value = { "/bookableService/app/", "/bookableService/app" })
    public ResponseEntity<?> editBookableService(@RequestParam(value = "oldName") String oldName,
                                                 @RequestParam(value = "newName") String newName,
                                                 @RequestParam(value = "newCost") String newCost,
                                                 @RequestParam(value = "newDuration") String newDuration)
            throws IllegalArgumentException, BookableServiceException {
        try{
            BookableService originalService = repairShopService.getService(oldName);
            float cost_new = Float.valueOf(newCost);
            int duration_new = Integer.valueOf(newDuration);

            BookableService newService =  repairShopService.editService(originalService, newName,
                    cost_new, duration_new);
            return new ResponseEntity<>(RepairShopUtil.convertToDto(newService), HttpStatus.OK);
        }
        catch (BookableServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * get bookable service method returns service from a name
     *
     * @param name name of service
     * @return response entity
     */
    @GetMapping(value = { "/bookableService/{name}", "/bookableService/{name}/" })
    public BookableServiceDto getBookableService(@PathVariable("name") String name) {
        return RepairShopUtil.convertToDto(repairShopService.getService(name));
    }

    /**
     * gets all bookable services of the repair shop
     *
     * @return response entity
     */
    @GetMapping(value = { "/bookableServices", "/bookableServices/" })
    public List<BookableServiceDto> getAllBookableServices() {
        List<BookableServiceDto> bookableServiceDtoList = new ArrayList<>();
        for (BookableService s: repairShopService.getAllService()){
            bookableServiceDtoList.add(RepairShopUtil.convertToDto(s));
        }
        return bookableServiceDtoList;
    }

    /**
     * delete service method deletes an existing service
     *
     * @param name name of service
     * @return response entity
     */
    @DeleteMapping(value = { "/bookableService/{name}", "/bookableService/{name}/" })
    public ResponseEntity<?> deleteBookableService(@PathVariable("name") String name){
        BookableService bookableService = repairShopService.getService(name);
        if (bookableService == null) {
            return new ResponseEntity<>("Cannot delete a null service", HttpStatus.BAD_REQUEST);
        }
        // find the appointment using id
        try {
            repairShopService.deleteBookableService(bookableService);
        } catch (BookableServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("The service has been successfully deleted", HttpStatus.OK);
    }

    /**
     * deletes an existing service
     *
     * @param name service name as RequestParam
     * @return response entity
     */
    @DeleteMapping(value = { "/bookableService/app", "/bookableService/app/" })
    public ResponseEntity<?> deleteExistingBookableService(@RequestParam(value = "name") String name){
        BookableService bookableService = repairShopService.getService(name);
        if (bookableService == null) {
            return new ResponseEntity<>("Cannot delete a null service", HttpStatus.BAD_REQUEST);
        }
        // find the appointment using id
        try {
            repairShopService.deleteBookableService(bookableService);
        } catch (BookableServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("The service has been successfully deleted", HttpStatus.OK);
    }

    /**
     * get cost of services method returns the cost given a list of services
     *
     * @param serviceNames list of all service names trying to calculate cost for
     * @return
     */
    @GetMapping(value = {"/costOfService", "/costOfService/"})
    public ResponseEntity<?> getCostOfService(@RequestParam(value = "services") List<String> serviceNames){
        float cost = 0f;
        for(String name : serviceNames) {
            BookableService bookableService = repairShopService.getService(name);
            cost += bookableService.getCost();
        }
        return new ResponseEntity<>(cost, HttpStatus.OK);
    }
}
