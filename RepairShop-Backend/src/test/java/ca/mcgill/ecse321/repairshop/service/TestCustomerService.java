package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.dto.AdministratorDto;
import ca.mcgill.ecse321.repairshop.dto.CustomerDto;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.PersonException;

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


@ExtendWith(MockitoExtension.class)
public class TestCustomerService {

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
    private static final String CUSTOMER_USERNAME = "customer";
    private static final String CUSTOMER_PASSWORD = "123";
    private static final Long CUSTOMER_ID = 12L;

    private static final String ADMIN_EMAIL = "admin@mail.com";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "456";
    private static final Long ADMIN_ID = 13L;

    private static final String TECH_EMAIL = "tech@mail.com";
    private static final String TECH_USERNAME = "tech";
    private static final String TECH_PASSWORD = "678";
    private static final Long TECH_ID = 14L;

    private static final String OWNER_EMAIL = "owner@mail.com";
    private static final String OWNER_USERNAME = "owner";
    private static final String OWNER_PASSWORD = "900";
    private static final Long OWNER_ID = 15L;


    @BeforeEach
    public void setMockOutput() {

        // customer by email
        lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(CUSTOMER_EMAIL)) {
                Customer customer = new Customer();
                customer.setEmail(CUSTOMER_EMAIL);
                customer.setUsername(CUSTOMER_USERNAME);
                customer.setPassword(CUSTOMER_PASSWORD);
                customer.setId(CUSTOMER_ID);
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
                administrator.setUsername(ADMIN_USERNAME);
                administrator.setPassword(ADMIN_PASSWORD);
                administrator.setId(ADMIN_ID);
                return administrator;
            } else {
                return null;
            }
        });

        //technician by email
        lenient().when(technicianRepository.findTechnicianByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TECH_EMAIL)) {
                Technician technician = new Technician();
                technician.setEmail(TECH_EMAIL);
                technician.setUsername(TECH_USERNAME);
                technician.setPassword(TECH_PASSWORD);
                technician.setId(TECH_ID);
                return technician;
            } else {
                return null;
            }
        });


        //owner by email
        lenient().when(ownerRepository.findOwnerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(OWNER_EMAIL)) {
                Owner owner = new Owner();
                owner.setEmail(OWNER_EMAIL);
                owner.setUsername(OWNER_USERNAME);
                owner.setPassword(OWNER_PASSWORD);
                owner.setId(OWNER_ID);
                return owner;
            } else {
                return null;
            }
        });


    }

    //CUSTOMER

    //positive create customer
    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setEmail("test@gmail.com");
        customer.setPassword("testpassword");
        customer.setUsername("testusername");
        customer.setId(000L);
        Customer createdCustomer = null;
        try {
            createdCustomer = personService.createCustomer("test@gmail.com", "testusername", "testpassword");
        } catch (PersonException e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(createdCustomer);
        assertEquals(createdCustomer.getEmail(), customer.getEmail());
    }

    //negative create customer [missing email]
    @Test
    public void testCreateCustomerWithoutEmail() {
        Customer customer = new Customer();
        customer.setPassword("testpassword");
        customer.setUsername("testusername");
        customer.setId(000L);
        Customer createdCustomer = null;
        try {
            createdCustomer = personService.createCustomer("", "testusername", "testpassword");
        } catch (PersonException e) {
            assertEquals("Email cannot be empty", e.getMessage());
        }
        assertNull(createdCustomer);
    }

    //negative create customer [missing username]
    @Test
    public void testCreateCustomerWithoutUsername() {
        Customer customer = new Customer();
        customer.setPassword("testpassword");
        customer.setEmail("test@gmail.com");
        customer.setId(000L);
        Customer createdCustomer = null;
        try {
            createdCustomer = personService.createCustomer("test@gmail.com", "", "testpassword");
        } catch (PersonException e) {
            assertEquals("Username cannot be empty", e.getMessage());
        }
        assertNull(createdCustomer);
    }

    //negative create customer [missing password]
    @Test
    public void testCreateCustomerWithoutPassword() {
        Customer customer = new Customer();
        customer.setEmail("test@gmail.com");
        customer.setUsername("testusername");
        customer.setId(000L);
        Customer createdCustomer = null;
        try {
            createdCustomer = personService.createCustomer("test@gmail.com", "testusername", "");
        } catch (PersonException e) {
            assertEquals("Password cannot be empty", e.getMessage());
        }
        assertNull(createdCustomer);
    }

    //positive login test
    @Test
    public void testLoginCustomer() {
        Customer customer = new Customer();
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setUsername(CUSTOMER_USERNAME);
        customer.setPassword(CUSTOMER_PASSWORD);
        customer.setId(CUSTOMER_ID);
        Customer createdCustomer = null;
        try {
            createdCustomer = personService.loginCustomer(customer.getEmail(), customer.getPassword());
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(createdCustomer);
    }

    //negative login test [customer does not exist]
    @Test
    public void testLoginCustomerWithWrongEmail() {
        Customer customer = new Customer();
        customer.setEmail("incorrect email");
        customer.setUsername(CUSTOMER_USERNAME);
        customer.setPassword(CUSTOMER_PASSWORD);
        customer.setId(CUSTOMER_ID);
        Customer createdCustomer = null;
        try {
            createdCustomer = personService.loginCustomer(customer.getEmail(), customer.getPassword());
        } catch (PersonException e) {
            assertEquals("Customer does not exist", e.getMessage());
        }
    }

    //negative login test [wrong password]
    @Test
    public void testLoginCustomerWithWrongPassword() {
        Customer customer = new Customer();
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setUsername(CUSTOMER_USERNAME);
        customer.setPassword("incorrect password");
        customer.setId(CUSTOMER_ID);
        Customer createdCustomer = null;
        try {
            createdCustomer = personService.loginCustomer(customer.getEmail(), customer.getPassword());
        } catch (PersonException e) {
            assertEquals("Incorrect password", e.getMessage());
        }
    }

    //positive get test
    @Test
    public void testGetCustomer() {
        Customer customer = new Customer();
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setUsername(CUSTOMER_USERNAME);
        customer.setPassword(CUSTOMER_PASSWORD);
        customer.setId(CUSTOMER_ID);
        Customer createdCustomer = null;
        try {
            createdCustomer = personService.getCustomer(customer.getEmail());
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(createdCustomer);
    }

    //positive get test
    @Test
    public void testGetCustomerWithWrongEmail() {
        Customer customer = new Customer();
        customer.setEmail("incorrect email");
        customer.setUsername(CUSTOMER_USERNAME);
        customer.setPassword(CUSTOMER_PASSWORD);
        customer.setId(CUSTOMER_ID);
        Customer createdCustomer = null;
        try {
            createdCustomer = personService.getCustomer(customer.getEmail());
        } catch (PersonException e) {
            assertEquals("Customer with this email does not exist", e.getMessage());
        }
    }

    //positive update test
    @Test
    public void testUpdateCustomer() {
        CustomerDto customer = new CustomerDto();
        customer.setEmail("new@gmail.com");
        customer.setPassword("newpassword");
        customer.setUsername("newusername");
        customer.setId(1L);
        Customer updatedCustomer = null;
        try {
            updatedCustomer = personService.updateCustomer(CUSTOMER_EMAIL, customer);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(updatedCustomer);
    }

    //negative update test [wrong email]
    @Test
    public void testUpdateCustomerWithWrongEmail() {
        CustomerDto customer = new CustomerDto();
        customer.setEmail("new@gmail.com");
        customer.setPassword("newpassword");
        customer.setUsername("newusername");
        customer.setId(1L);
        Customer updatedCustomer = null;
        try {
            updatedCustomer = personService.updateCustomer("incorrectEmail@gmail.com", customer);
        } catch (PersonException e) {
            assertEquals("The customer with this email does not exist", e.getMessage());
        }
    }

    //negative update test [dulicate email]
    @Test
    public void testUpdatCustomerWithDuplicateEmail() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail(CUSTOMER_EMAIL);
        customerDto.setPassword("newpassword");
        customerDto.setUsername("newusername");
        customerDto.setId(1L);

        try {
            personService.updateCustomer(CUSTOMER_EMAIL, customerDto);
        } catch (PersonException e) {
            assertEquals("Email has been taken", e.getMessage());
        }
    }

    //positive delete
    @Test
    public void testDeleteCustomer() {
        Customer customer = null;
        try {
            customer = personService.deleteCustomer(CUSTOMER_EMAIL);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(customer);
    }

    //nagative delete
    @Test
    public void testDeleteNonExistingAdministrator() {


        try {
            personService.deleteCustomer("nonexisting@mail.ca");
        } catch (PersonException e) {
            assertEquals("The customer with the given email does not exist",e.getMessage());
        }

    }
}