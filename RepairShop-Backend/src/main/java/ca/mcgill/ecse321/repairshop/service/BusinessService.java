package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.BusinessRepository;
import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import ca.mcgill.ecse321.repairshop.utility.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessService {
    @Autowired
    BusinessRepository businessRepository;

    /**
     * Creates a business in the database if it does not exist
     * @param name
     * @param address
     * @param phoneNumber
     * @param email
     * @param timeslot
     * @return
     */
    @Transactional
    public Business createBusiness(String name, String address, String phoneNumber, String email, List<TimeSlot> timeslot) throws BusinessException {
        if (businessRepository.count() != 1) {
            throw new BusinessException("You can only create one business, Try updating the information");
        }
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
    public Business getBusiness(String name){
        Business business = businessRepository.findBusinessByName(name);
        return business;
    }
}
