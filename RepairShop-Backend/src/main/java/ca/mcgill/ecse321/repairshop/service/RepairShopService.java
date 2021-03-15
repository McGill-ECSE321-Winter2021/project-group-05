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
        if (name == null){
            throw new IllegalArgumentException("service name cannot be empty");
        }
        BookableService service = new BookableService();
        service.setCost(cost);
        service.setName(name);
        service.setDuration(duration);
        serviceRepository.save(service);
        return service;
    }

    /**
     * edit new Service
     */
    @Transactional
    public BookableService editService(BookableService service, String newName, float newCost, int newDuration){
        if (service == null){
            throw new IllegalArgumentException("please select a service that you want to modify");
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
    public BookableService getService(Long id) {
        BookableService service = serviceRepository.findServiceById(id);
        return service;
    }

    @Transactional
    public List<BookableService> getAllService() {
        return RepairShopUtil.toList(serviceRepository.findAll());
    }

    @Transactional
    public void deleteBookableService (BookableService bookableService){
        if (bookableService == null){
            throw new IllegalArgumentException("Cannot delete a null service");
        }

        // cannot delete a service which still have future appointment link to it
        for (Appointment app : bookableService.getRepairShop().getAppointments()){
            // loop through all future appointments in future days
            if(app.getTimeslot().getDate().after(valueOf(LocalDate.now()))
                    || app.getTimeslot().getDate().toString().equals(valueOf(LocalDate.now()).toString())) { // on same day
                for (BookableService b : app.getServices()){
                    // still have future appointments inside the service
                    if (b.getId().equals(bookableService.getId())){
                        throw new IllegalArgumentException("Cannot delete a service which still have future appointments");
                    }
                }
            }

        }
        serviceRepository.deleteById(bookableService.getId());


    }


}
