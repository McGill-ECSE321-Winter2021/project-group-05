package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.model.RepairShop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestBusinessPersistence {
    @Autowired
    private BusinessRepository businessRepository;

    /**
     * clear database
     */
    @AfterEach
    public void clearDatabase() {
        // First, we clear business to avoid exceptions due to inconsistencies
        businessRepository.deleteAll();

    }
    /**
     * Testing persisting and loading business
     */
    public void testPersistAndLoadBusiness(){
        String address = "MTL";
        String email = "business@123.com";
        String phone = "514123456";
        String name = "testBusiness";
        Business business = new Business();

        business.setAddress(address);
        business.setEmail(email);
        business.setPhoneNumber(phone);
        business.setName(name);

        RepairShop rs = new RepairShop();
        business.setRepairShop(rs);

        businessRepository.save(business);

        Long id = business.getId();

        business = businessRepository.findBusinessById(id);

        assertNotNull(business);
        assertEquals(address,business.getAddress());
        assertEquals(email,business.getEmail());
        assertEquals(id,business.getId());
        assertEquals(phone,business.getPhoneNumber());
        assertEquals(name,business.getName());
        assertNotNull(business.getRepairShop());

    }
}

