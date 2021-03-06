package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.repairshop.dao.ServiceRepository;
import ca.mcgill.ecse321.repairshop.dao.AppointmentRepository;

import java.util.List;

@Service
public class RepairShopService {
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    AppointmentRepository appointmentRepository;

    /**
     * Service
     */
    @Transactional
    public ca.mcgill.ecse321.repairshop.model.Service createService(String name, float cost, int duration, List<Appointment> appointments){
        // service is a reserved keywork
        ca.mcgill.ecse321.repairshop.model.Service service = new ca.mcgill.ecse321.repairshop.model.Service();
        service.setCost(cost);
        service.setName(name);
        service.setDuration(duration);
        service.setAppointments(appointments);
        serviceRepository.save(service);
        return service;
    }

    @Transactional
    public ca.mcgill.ecse321.repairshop.model.Service getService(Long id) {
        ca.mcgill.ecse321.repairshop.model.Service service = serviceRepository.findServiceById(id);
        return service;
    }

    @Transactional
    public List<ca.mcgill.ecse321.repairshop.model.Service> getAllService() {
        return ListUtil.toList(serviceRepository.findAll());
    }
}
