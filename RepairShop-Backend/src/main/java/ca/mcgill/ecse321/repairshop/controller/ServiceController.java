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
     * create new service
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
     * edit service
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
            throw new BookableServiceException("Cannot edit appointment before 24hr");
        }
    }

    @GetMapping(value = { "/bookableService/{name}", "/bookableService/{name}/" })
    public BookableServiceDto getBookableService(@PathVariable("name") String name) {
        return RepairShopUtil.convertToDto(repairShopService.getService(name));
    }

    @GetMapping(value = { "/bookableServices", "/bookableServices/" })
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
        return new ResponseEntity<>("The service has been succesfully deleted", HttpStatus.OK);
    }
}
