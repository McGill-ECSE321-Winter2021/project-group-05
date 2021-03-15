package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.BusinessRepository;
import ca.mcgill.ecse321.repairshop.dto.BusinessDto;
import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.utility.BusinessException;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {
    @Autowired
    BusinessRepository businessRepository;

    /**
     * creates a business if it does not exist
     * @param businessDto
     * @return
     * @throws BusinessException
     */
    @Transactional
    public Business createBusiness(BusinessDto businessDto) throws BusinessException {
        String error = getErrorFromData(businessDto);
        if(!error.equals("")){
            throw new BusinessException(error);
        }
        List<Business> businesses = RepairShopUtil.toList(businessRepository.findAll());
        if (businesses.size() != 0){
            throw new BusinessException("A business has already been created");
        }
        Business business = convertToEntity(businessDto);
        businessRepository.save(business);
        return business;
    }

    private String getErrorFromData(BusinessDto businessDto) {
        if(businessDto.getName() == null || businessDto.getName().equals("")){
            return "Business name cannot be empty";
        }
        if(businessDto.getAddress() == null || businessDto.getAddress().equals("")){
            return "Business address cannot be empty";
        }
        if(businessDto.getEmail() == null || businessDto.getEmail().equals("")){
            return "Business email cannot be empty";
        }
        if(businessDto.getPhoneNumber() == null || businessDto.getPhoneNumber().equals("")){
            return "Business phone number cannot be empty";
        }
        return "";
    }

    /**
     * gets the business, given the Id
     * @param id
     * @return
     */
    @Transactional
    public Business getBusiness(Long id) {
        Optional<Business> business = businessRepository.findById(id);
        return business.get();
    }

    /**
     * gets the business
     * @return
     * @throws BusinessException
     */
    @Transactional
    public Business getBusiness() throws BusinessException{
        List<Business> businesses = RepairShopUtil.toList(businessRepository.findAll());
        if(businesses.size() < 1){
            throw new BusinessException("Business does not exist, Please create one");
        }
        return businesses.get(0);
    }

    /**
     * edits the business given an id
     * @param id
     * @param businessDto
     * @return
     * @throws BusinessException
     */
    @Transactional
    public Business editBusiness(Long id, BusinessDto businessDto) throws BusinessException{
        String error = getErrorFromData(businessDto);
        if(!error.equals("")){
            throw new BusinessException(error);
        }
        Optional<Business> businesses= businessRepository.findById(id);
        if(!businesses.isPresent()){
            throw new BusinessException("Business does not exist in database, Please create one");
        }
        Business business = businesses.get();
        business.setName(businessDto.getName());
        business.setEmail(businessDto.getEmail());
        business.setPhoneNumber(businessDto.getPhoneNumber());
        business.setAddress(businessDto.getAddress());
        businessRepository.save(business);
        return business;
    }

    /**
     * deletes the business from the system  || just for test purpose
     * @param id
     * @return
     */
    @Transactional
    public Business deleteBusiness(Long id) throws BusinessException{
        Optional<Business> businesses = businessRepository.findById(id);
        if(!businesses.isPresent()){
            throw new BusinessException("Cannot delete because business does not exist");
        }
        Business business = businesses.get();
        businessRepository.deleteById(id);
        return business;
    }

    //HELPER FUNCTION

    /**
     * converts the DTO to a business entity
     * @param businessDto
     * @return
     */
    public Business convertToEntity(BusinessDto businessDto){
        Business business = new Business();
        business.setName(businessDto.getName());
        business.setPhoneNumber(businessDto.getPhoneNumber());
        business.setEmail(businessDto.getEmail());
        business.setAddress(businessDto.getAddress());
        business.setId(businessDto.getId());
        return business;
    }
}
