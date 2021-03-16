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
    private static BookableService bookableService = null;

    @BeforeEach
    public void setMockOutPut(){
        lenient().when(serviceRepository.findById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(NAME)){
                bookableService = new BookableService();
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


    @Test
    public void testEditServiceSuccessfully() {
        //TODO: change this later, doesnt work if removed
        String OLD_NAME = "Old service name";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = new BookableService();
        service.setName(OLD_NAME);
        service.setCost(OLD_COST);
        service.setDuration(OLD_DURATION);

        String NEW_NAME = "New service name";
        float NEW_COST = 19.79f;
        int NEW_DURATION = 14;

        BookableService editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);

        assertNotNull(editedService);
        assertEquals(editedService.getName(), NEW_NAME);
        assertEquals(editedService.getCost(), NEW_COST);
        assertEquals(editedService.getDuration(), NEW_DURATION);
    }


    @Test
    public void testEditServiceWithNullService() {
        //TODO: change this later, doesnt work if removed
        String OLD_NAME = "Old service name";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = new BookableService();
        service.setName(OLD_NAME);
        service.setCost(OLD_COST);
        service.setDuration(OLD_DURATION);

        service = null;

        String NEW_NAME = "New service name";
        float NEW_COST = 59.79f;
        int NEW_DURATION = 16;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Please select a service that you want to modify");
    }



    @Test
    public void testEditServiceWithEmptyNewServiceName() {
        //TODO: change this later, doesnt work if removed
        String OLD_NAME = "Old service name";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = new BookableService();
        service.setName(OLD_NAME);
        service.setCost(OLD_COST);
        service.setDuration(OLD_DURATION);

        String NEW_NAME = "   ";
        float NEW_COST = 39.79f;
        int NEW_DURATION = 17;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "New service name cannot be empty");
    }


    @Test
    public void testEditServiceWithNegativeNewCost() {
        //TODO: change this later, doesnt work if removed
        String OLD_NAME = "Old service name";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = new BookableService();
        service.setName(OLD_NAME);
        service.setCost(OLD_COST);
        service.setDuration(OLD_DURATION);

        String NEW_NAME = "New service name";
        float NEW_COST = -19.59f;
        int NEW_DURATION = 9;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "New service cost cannot be negative");
    }


    @Test
    public void testEditServiceWithZeroNewDuration() {
        //TODO: change this later, doesnt work if removed
        String OLD_NAME = "Old service name";
        float OLD_COST = 29.79f;
        int OLD_DURATION = 12;
        BookableService service = new BookableService();
        service.setName(OLD_NAME);
        service.setCost(OLD_COST);
        service.setDuration(OLD_DURATION);

        String NEW_NAME = "New service name";
        float NEW_COST = 599.59f;
        int NEW_DURATION = 0;
        String error = null;

        BookableService editedService = null;

        try {
            editedService = repairShopService.editService(service, NEW_NAME, NEW_COST, NEW_DURATION);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "New service duration cannot be 0");
    }
}