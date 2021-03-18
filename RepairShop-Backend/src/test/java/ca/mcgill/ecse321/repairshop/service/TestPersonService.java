package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.dto.BusinessDto;
import ca.mcgill.ecse321.repairshop.dto.CustomerDto;
import ca.mcgill.ecse321.repairshop.model.Administrator;
import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.utility.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import java.util.Optional;
import java.util.NoSuchElementException;


public class TestPersonService {

    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private TechnicianRepository technicianRepository;


    @InjectMocks
    private PersonService personService;


    private static final String CUSTOMER_EMAIL = "customer@mail.com";
    private static final String ADMIN_EMAIL = "admin@mail.com";
    private static final String TECH_EMAIL = "tech@mail.com";
    private static final String OWNER_EMAIL = "owner@mail.com";

    private static final long ID = 0L;


    @BeforeEach
    public void setMockOutput() {

        // customer by email
        lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(CUSTOMER_EMAIL)) {
                Customer customer = new Customer();
                customer.setEmail(CUSTOMER_EMAIL);
                return customer;
            } else {
                return null;
            }
        });

        //admin by email

        lenient().when(administratorRepository.findAdministratorByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ADMIN_EMAIL)) {
                Administrator administrator = new Administrator();
                administrator.setEmail(ADMIN_EMAIL);
                return administrator;
            } else {
                return null;
            }
        });

        //technician by email
        lenient().when(technicianRepository.findTechnicianByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TECH_EMAIL)) {
                Administrator administrator = new Administrator();
                administrator.setEmail(TECH_EMAIL);
                return administrator;
            } else {
                return null;
            }
        });


        //owner by email
        lenient().when(ownerRepository.findOwnerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(OWNER_EMAIL)) {
                Administrator administrator = new Administrator();
                administrator.setEmail(OWNER_EMAIL);
                return administrator;
            } else {
                return null;
            }
        });


    }
/*
    @Test
    public void testCreateCustomer() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail(CUSTOMER_EMAIL);

        Customer createdCustomer = null;
        try {
            createdCustomer = personService.createCustomer();
        } catch (BusinessException e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(createdCustomer);
        assertEquals(createdCustomer.getEmail(), customerDto.getEmail());
        }

          */



    @Test
    public void testDeleteCustomer(){

    }

    @Test
    public void testDeleteOwner(){

    }

    @Test
    public void testDeleteAdmin(){

    }


    @Test
    public void testDeleteTechnician(){

    }


    @Test
    public void testGetcustomerWithInvalidEmail() {


    }



    }

