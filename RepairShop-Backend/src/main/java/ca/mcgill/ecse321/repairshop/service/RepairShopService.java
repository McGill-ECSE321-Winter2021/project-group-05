package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.repairshop.dao.ServiceRepository;

import java.time.LocalDate;
import java.util.List;

import static java.sql.Date.valueOf;

@Service
public class RepairShopService {
    @Autowired
    ServiceRepository serviceRepository;

    /**
     * create new Service
     */
    @Transactional
    public BookableService createService(String name, float cost, int duration){
        if (name == null || name.trim().equalsIgnoreCase("")){
            throw new IllegalArgumentException("Service name cannot be empty");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("Service cost cannot be negative");
        }
        if (duration == 0) {
            throw new IllegalArgumentException("Service duration cannot be 0");
        }

        BookableService service = new BookableService();
        service.setCost(cost);
        service.setName(name);
        service.setDuration(duration);
        serviceRepository.save(service);
        return service;
    }

    /**
     * edit existing Service
     */
    @Transactional
    public BookableService editService(BookableService service, String newName, float newCost, int newDuration){
        if (service == null){
            throw new IllegalArgumentException("Please select a service that you want to modify");
        }
        if (newName == null || newName.trim().equalsIgnoreCase("")){
            throw new IllegalArgumentException("New service name cannot be empty");
        }
        if (newCost < 0) {
            throw new IllegalArgumentException("New service cost cannot be negative");
        }
        if (newDuration == 0) {
            throw new IllegalArgumentException("New service duration cannot be 0");
        }

        if (newName != null){
            service.setName(newName);
        }
        service.setCost(newCost);
        service.setDuration(newDuration);
        serviceRepository.save(service);
        return service;
    }

    @Transactional
    public BookableService getService(String name) {
        BookableService service = serviceRepository.findServiceByName(name);
        return service;
    }

    @Transactional
    public List<BookableService> getAllService() {
        return RepairShopUtil.toList(serviceRepository.findAll());
    }

    /**
     * delete existing service
     */
    @Transactional
    public void deleteBookableService (BookableService bookableService){
        if (bookableService == null){
            throw new IllegalArgumentException("Cannot delete a service that does not exist");
        }

        // cannot delete a service which still have future appointment link to it
        for (Appointment app : bookableService.getRepairShop().getAppointments()){
            // loop through all future appointments in future days
            if(app.getTimeslot().getDate().after(valueOf(LocalDate.now()))
                    || app.getTimeslot().getDate().toString().equals(valueOf(LocalDate.now()).toString())) { // on same day
                for (BookableService b : app.getServices()){
                    // still have future appointments inside the service
                    if (b.getName().equals(bookableService.getName())){
                        throw new IllegalArgumentException("Cannot delete a service which still have future appointments");
                    }
                }
            }

        }
        serviceRepository.deleteBookableServiceByName(bookableService.getName());


    }


}
