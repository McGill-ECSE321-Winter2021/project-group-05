package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.ServiceRepository;
import ca.mcgill.ecse321.repairshop.model.BookableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.awt.print.Book;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestRepairShopService {
    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private RepairShopService repairShopService;

    private static String NAME = "RepairShop";
    private static float COST = 21.3f;
    private static int DURATION = 10;

    @BeforeEach
    public void setMockOutPut(){
        lenient().when(serviceRepository.findById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(NAME)){
                BookableService bookableService = new BookableService();
                bookableService.setName(NAME);
                bookableService.setCost(COST);
                bookableService.setDuration(DURATION);
                return Optional.of(bookableService);
            }else{
                return Optional.empty();
            }
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(serviceRepository.save(any(BookableService.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateServiceSuccessfully(){
        //TODO:
//        BookableService bookableService = new BookableService();
//        bookableService.setName(NAME);
//        bookableService.setCost(COST);
//        bookableService.setDuration(DURATION);
        String NAME = "TestService";
        float COST = 55.99f;
        int DURATION = 7;

        BookableService createdService = repairShopService.createService(NAME, COST, DURATION);
//        createdService = null;
//        createdService = serviceRepository.findServiceByName(NAME);

        assertNotNull(createdService);
        assertEquals(createdService.getName(), NAME);
        assertEquals(createdService.getCost(), COST);
        assertEquals(createdService.getDuration(), DURATION);
    }

    @Test
    public void testCreateServiceWithNullName(){
        String NAME = null;
        float COST = 39.99f;
        int DURATION = 17;
        String error = null;
        BookableService createdService = null;

        try {
            createdService = repairShopService.createService(NAME, COST, DURATION);
//        createdService = null;
//        createdService = serviceRepository.findServiceByName(NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Service name cannot be empty");
    }


    @Test
    public void testCreateServiceWithEmptyName(){
        String NAME = "    ";
        float COST = 79.99f;
        int DURATION = 77;
        String error = null;
        BookableService createdService = null;

        try {
            createdService = repairShopService.createService(NAME, COST, DURATION);
//        createdService = null;
//        createdService = serviceRepository.findServiceByName(NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "Service name cannot be empty");
    }


    @Test
    public void testCreateServiceWithNegativeCost(){
        String NAME = "TestService";
        float COST = -85.99f;
        int DURATION = 37;
        String error = null;
        BookableService createdService = null;

        try {
            createdService = repairShopService.createService(NAME, COST, DURATION);
//        createdService = null;
//        createdService = serviceRepository.findServiceByName(NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "Service cost cannot be negative");
    }



    @Test
    public void testCreateServiceWithZeroDuration(){
        String NAME = "TestService";
        float COST = 85.98f;
        int DURATION = 0;
        String error = null;
        BookableService createdService = null;

        try {
            createdService = repairShopService.createService(NAME, COST, DURATION);
//        createdService = null;
//        createdService = serviceRepository.findServiceByName(NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "Service duration cannot be 0");
    }
}