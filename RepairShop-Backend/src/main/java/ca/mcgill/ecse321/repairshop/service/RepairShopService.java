package ca.mcgill.ecse321.repairshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.repairshop.dao.ServiceRepository;
import ca.mcgill.ecse321.repairshop.dao.BillRepository;
import ca.mcgill.ecse321.repairshop.dao.TimeSlotRepository;
import ca.mcgill.ecse321.repairshop.dao.CustomerRepository;
import ca.mcgill.ecse321.repairshop.dao.AppointmentRepository;
import ca.mcgill.ecse321.repairshop.dao.AdministratorRepository;
import ca.mcgill.ecse321.repairshop.dao.BusinessRepository;
import ca.mcgill.ecse321.repairshop.dao.OwnerRepository;
import ca.mcgill.ecse321.repairshop.dao.TechnicianRepository;

@Service
public class RepairShopService {
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    BillRepository billRepository;
    @Autowired
    TimeSlotRepository timeSlotRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AdministratorRepository administratorRepository;
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    OwnerRepository OwnerRepository;
    @Autowired
    TechnicianRepository TechnicianRepository;

}
