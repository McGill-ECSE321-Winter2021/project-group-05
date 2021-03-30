package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.dto.AdministratorDto;
import ca.mcgill.ecse321.repairshop.model.Administrator;
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
public class TestAdministratorService {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private TechnicianRepository technicianRepository;


    @InjectMocks
    private PersonService personService;

    private static final String ADMIN_EMAIL = "admin@mail.com";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "456";
    private static final Long ADMIN_ID = 13L;

    @BeforeEach
    public void setMockOutput() {
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
    }

    //CUSTOMER

    //positive create administrator
    @Test
    public void testCreateAdministrator() {
        Administrator administrator = new Administrator();
        administrator.setEmail("test@gmail.com");
        administrator.setPassword("testpassword");
        administrator.setUsername("testusername");
        administrator.setId(000L);
        Administrator createdAdministrator = null;
        try {
            createdAdministrator = personService.createAdministrator("test@gmail.com", "testusername", "testpassword");
        } catch (PersonException e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(createdAdministrator);
        assertEquals(createdAdministrator.getEmail(), administrator.getEmail());
    }

    //negative create admin [missing email]
    @Test
    public void testCreateAdministratorWithoutEmail() {
        Administrator administrator = new Administrator();
        administrator.setPassword("testpassword");
        administrator.setUsername("testusername");
        administrator.setId(000L);
        Administrator createdAdmin = null;
        try {
            createdAdmin = personService.createAdministrator("", "testusername", "testpassword");
        } catch (PersonException e) {
            assertEquals("Email cannot be empty", e.getMessage());
        }
        assertNull(createdAdmin);
    }

    //negative create customer [missing username]
    @Test
    public void testCreateAdministratorWithoutUsername() {
        Administrator administrator = new Administrator();
        administrator.setPassword("testpassword");
        administrator.setEmail("test@gmail.com");
        administrator.setId(000L);
        Administrator createdAdmin = null;
        try {
            createdAdmin = personService.createAdministrator("test@gmail.com", "", "testpassword");
        } catch (PersonException e) {
            assertEquals("Username cannot be empty", e.getMessage());
        }
        assertNull(createdAdmin);
    }

    //negative create customer [missing password]
    @Test
    public void testCreateAdministratorWithoutPassword() {
        Administrator administrator = new Administrator();
        administrator.setEmail("test@gmail.com");
        administrator.setUsername("testusername");
        administrator.setId(000L);
        Administrator createdAdmin = null;
        try {
            createdAdmin = personService.createAdministrator("test@gmail.com", "testusername", "");
        } catch (PersonException e) {
            assertEquals("Password cannot be empty", e.getMessage());
        }
        assertNull(createdAdmin);
    }

    //positive login test
    @Test
    public void testLoginAdministrator() {
        Administrator administrator = new Administrator();
        administrator.setEmail(ADMIN_EMAIL);
        administrator.setUsername(ADMIN_USERNAME);
        administrator.setPassword(ADMIN_PASSWORD);
        administrator.setId(ADMIN_ID);
        Administrator createdAdmin = null;
        try {
            createdAdmin = personService.loginAdministrator(administrator.getEmail(), administrator.getPassword());
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(createdAdmin);
    }

    //negative login test [customer does not exist]
    @Test
    public void testLoginAdministratorWithWrongEmail() {
        Administrator administrator = new Administrator();
        administrator.setEmail("incorrect email");
        administrator.setUsername(ADMIN_USERNAME);
        administrator.setPassword(ADMIN_PASSWORD);
        administrator.setId(ADMIN_ID);
        Administrator createdAdmin = null;
        try {
            createdAdmin = personService.loginAdministrator(administrator.getEmail(), administrator.getPassword());
        } catch (PersonException e) {
            assertEquals("Administrator does not exist", e.getMessage());
        }
    }

    //negative login test [wrong password]
    @Test
    public void testLoginAdministratorWithWrongPassword() {
        Administrator administrator = new Administrator();
        administrator.setEmail(ADMIN_EMAIL);
        administrator.setUsername(ADMIN_USERNAME);
        administrator.setPassword("incorrect password");
        administrator.setId(ADMIN_ID);
        Administrator createdAdmin = null;
        try {
            createdAdmin = personService.loginAdministrator(administrator.getEmail(), administrator.getPassword());
        } catch (PersonException e) {
            assertEquals("Incorrect password", e.getMessage());
        }
    }

    //positive get test
    @Test
    public void testGetAdministrator() {
        Administrator administrator = new Administrator();
        administrator.setEmail(ADMIN_EMAIL);
        administrator.setUsername(ADMIN_USERNAME);
        administrator.setPassword(ADMIN_PASSWORD);
        administrator.setId(ADMIN_ID);
        Administrator createdAdmin = null;
        try {
            createdAdmin = personService.getAdministrator(administrator.getEmail());
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(createdAdmin);
    }

    //positive get test
    @Test
    public void testGetAdministratorWithWrongEmail() {
        Administrator administrator = new Administrator();
        administrator.setEmail("incorrect email");
        administrator.setUsername(ADMIN_USERNAME);
        administrator.setPassword(ADMIN_PASSWORD);
        administrator.setId(ADMIN_ID);
        Administrator createdAdmin = null;
        try {
            createdAdmin = personService.getAdministrator(administrator.getEmail());
        } catch (PersonException e) {
            assertEquals("Administrator with this email does not exist", e.getMessage());
        }
    }

    //positive update test
    @Test
    public void testUpdateAdministrator() {
        AdministratorDto administratorDto = new AdministratorDto();
        administratorDto.setEmail("new@gmail.com");
        administratorDto.setPassword("newpassword");
        administratorDto.setUsername("newusername");
        administratorDto.setId(1L);
        Administrator updatedAdmin = null;
        try {
            updatedAdmin = personService.updateAdministrator(ADMIN_EMAIL, administratorDto);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(updatedAdmin);
    }

    //negative update test [wrong email]
    @Test
    public void testUpdateAdministratorWithWrongEmail() {
        AdministratorDto administratorDto = new AdministratorDto();
        administratorDto.setEmail("new@gmail.com");
        administratorDto.setPassword("newpassword");
        administratorDto.setUsername("newusername");
        administratorDto.setId(1L);
        Administrator updatedAdmin = null;
        try {
            updatedAdmin = personService.updateAdministrator("incorrectEmail@gmail.com", administratorDto);
        } catch (PersonException e) {
            assertEquals("The Administrator with this email does not exist", e.getMessage());
        }
    }

    //negative update test [invalid email]
    @Test
    public void testUpdateAdministratorWithInvalidEmail() {
        AdministratorDto administratorDto = new AdministratorDto();
        administratorDto.setEmail("newgmail.com");
        administratorDto.setPassword("newpassword");
        administratorDto.setUsername("newusername");
        administratorDto.setId(1L);
        Administrator updatedAdmin = null;
        try {
            updatedAdmin = personService.updateAdministrator(ADMIN_EMAIL, administratorDto);
        } catch (PersonException e) {
            assertEquals("Email is not valid", e.getMessage());
        }
    }

    //negative update test [dulicate email]
    @Test
    public void testUpdateAdministratorWithDuplicateEmail() {
        AdministratorDto administratorDto = new AdministratorDto();
        administratorDto.setEmail(ADMIN_EMAIL);
        administratorDto.setPassword("newpassword");
        administratorDto.setUsername("newusername");
        administratorDto.setId(1L);

        try {
           personService.updateAdministrator(ADMIN_EMAIL, administratorDto);
        } catch (PersonException e) {
            assertEquals("Email has been taken", e.getMessage());
        }
    }

    //positive delete
    @Test
    public void testDeleteAdministrator() {
        Administrator administrator = null;
        try {
            administrator = personService.deleteAdministrator(ADMIN_EMAIL);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(administrator);
    }

    //nagative delete
    @Test
    public void testDeleteNonExistingAdministrator() {

        try {
             personService.deleteAdministrator("nonexisting@mail.ca");
        } catch (PersonException e) {
            assertEquals("The Administrator with the given id does not exist",e.getMessage());
        }

    }

}