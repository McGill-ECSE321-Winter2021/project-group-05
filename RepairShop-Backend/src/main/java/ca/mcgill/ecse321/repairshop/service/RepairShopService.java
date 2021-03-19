package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.AppointmentRepository;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.BookableServiceException;
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
    @Autowired
    AppointmentRepository appointmentRepository;

    /**
     * create new Service
     */
    @Transactional
    public BookableService createService(String name, float cost, int duration) throws BookableServiceException {
        if (name == null || name.trim().equalsIgnoreCase("")){
            throw new BookableServiceException("Service name cannot be empty");
        }
        if (cost < 0) {
            throw new BookableServiceException("Service cost cannot be negative");
        }
        if (duration == 0) {
            throw new BookableServiceException("Service duration cannot be 0");
        }
        if(serviceRepository.findServiceByName(name) != null){
            throw new BookableServiceException("Service already exists");
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
    public BookableService editService(BookableService service, String newName, float newCost, int newDuration) throws BookableServiceException {
        if (service == null){
            throw new BookableServiceException("Please select a service that you want to modify");
        }
        if (newName == null || newName.trim().equalsIgnoreCase("")){
            throw new BookableServiceException("New service name cannot be empty");
        }
        if (newCost < 0) {
            throw new BookableServiceException("New service cost cannot be negative");
        }
        if (newDuration == 0) {
            throw new BookableServiceException("New service duration cannot be 0");
        }

        if(serviceRepository.findServiceByName(newName) != null){
            throw new BookableServiceException("Service already exists");
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
    public BookableService getService(Long Id) {
        BookableService service = serviceRepository.findServiceById(Id);
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
    public void deleteBookableService (BookableService bookableService) throws BookableServiceException {
        if (bookableService == null){
            throw new BookableServiceException("Cannot delete a service that does not exist");
        }

        if(serviceRepository.findServiceById(bookableService.getId()) == null) {
            throw new BookableServiceException("Service does not exist");
        } else {

            // cannot delete a service which still have future appointment link to it
            for (Appointment appointment : appointmentRepository.findAll()) {

                // loop through all future appointments in future days
                if (appointment.getTimeslot().getDate().toString().equals(valueOf(LocalDate.now()).toString())
                        || appointment.getTimeslot().getDate().after(valueOf(LocalDate.now()))) { // on same day
                    for (BookableService service : appointment.getServices()) {
                        // still have future appointments inside the service
                        if (service.getName().equals(bookableService.getName())) {
                            throw new BookableServiceException("Cannot delete a service which still has future appointments");
                        }
                    }
                }
            }
        }

        serviceRepository.deleteById(bookableService.getId());

    }


}
