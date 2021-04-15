package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestBookableServicePersistence {

    @Autowired
    private ServiceRepository serviceRepository;


    /**
     * clear database
     */
    @AfterEach
    public void clearDatabase() {
        serviceRepository.deleteAll();
    }






    /**
     * Testing persisting and loading a service
     */
    public void testPersistAndLoadService() {

        String serviceName = "repair window";
        int duration = 30;
        float cost = 10;
        BookableService service = new BookableService();

        service.setName(serviceName);
        service.setDuration(duration);
        service.setCost(cost);

        RepairShop rs = new RepairShop();
        service.setRepairShop(rs);
        serviceRepository.save(service);
        String serviceId = service.getName();

        service=serviceRepository.findServiceByName(serviceId);
        assertNotNull(service);
        assertEquals(serviceId,service.getName());
        assertEquals(serviceName,service.getName());
        assertEquals(duration,service.getDuration());
        assertEquals(cost,service.getCost());
        assertNotNull(service.getRepairShop());
    }
    

    


}