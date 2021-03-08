package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.BusinessRepository;
import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessService {
    @Autowired
    BusinessRepository businessRepository;

    @Transactional
    public Business createBusiness(String name, String address, String phoneNumber, String email, List<TimeSlot> timeslot) {
        Business business = new Business();
        business.setAddress(address);
        business.setEmail(email);
        business.setName(name);
        business.setPhoneNumber(phoneNumber);
        business.setTimeslot(timeslot);
        businessRepository.save(business);
        return business;
    }

    @Transactional
    public Business getBusiness(Long id) {
        Business business = businessRepository.findBusinessById(id);
        return business;
    }

    @Transactional
    public List<Business> getAllBusiness() {
        return RepairShopUtil.toList(businessRepository.findAll());
    }
}
