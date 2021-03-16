package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.BookableServiceDto;
import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.BookableService;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import ca.mcgill.ecse321.repairshop.service.RepairShopService;
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
     * create new service
     */
    @PostMapping(value = { "/bookableService", "/bookableService/" })
    public ResponseEntity<?> createBookableService(@RequestBody BookableServiceDto bookableServiceDto)
            throws IllegalArgumentException {
        try {

           BookableService bookableService = repairShopService.createService(bookableServiceDto.getName(), bookableServiceDto.getCost(),
                   bookableServiceDto.getDuration());
            return new ResponseEntity<>(RepairShopUtil.convertToDto(bookableService), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * edit service
     */
    @PutMapping(value = { "/bookableService/{id}", "/bookableService/{id}/" })
    public ResponseEntity<?> editBookableService(@PathVariable("id") Long id,
                                @RequestBody BookableServiceDto bookableServiceDto)
            throws IllegalArgumentException {

      try{
          BookableService originalService = repairShopService.getService(id);

           BookableService newService =  repairShopService.editService(originalService, bookableServiceDto.getName(),
                   bookableServiceDto.getCost(), bookableServiceDto.getDuration());
           return new ResponseEntity<>(RepairShopUtil.convertToDto(newService), HttpStatus.OK);
        }
      catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Cannot edit appointment before 24hr");
        }
    }

    @GetMapping(value = { "/bookableService/{id}", "/bookableService/{id}/" })
    public BookableServiceDto getBookableService(@PathVariable("id") Long id) {
        return RepairShopUtil.convertToDto(repairShopService.getService(id));
    }

    @GetMapping(value = { "/bookableServices", "/bookableServices" })
    public List<BookableServiceDto> getAllBookableServices() {
        List<BookableServiceDto> bookableServiceDtoList = new ArrayList<>();
        for (BookableService s: repairShopService.getAllService()){
            bookableServiceDtoList.add(RepairShopUtil.convertToDto(s));
        }
        return bookableServiceDtoList;
    }

    /**
     * delete service
     */
    @DeleteMapping(value = { "/bookableService/{id}", "/bookableService/{id}/" })
    public void deleteBookableService(@PathVariable("id") Long id) throws IllegalArgumentException {
        BookableService bookableService = repairShopService.getService(id);
        if (bookableService == null) {
            throw new IllegalArgumentException("Cannot delete a null service");
        }
        // find the appointment using id
        repairShopService.deleteBookableService(bookableService);

    }
}
