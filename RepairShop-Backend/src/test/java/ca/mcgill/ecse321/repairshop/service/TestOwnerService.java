package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.dto.OwnerDto;
import ca.mcgill.ecse321.repairshop.model.Owner;
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
public class TestOwnerService {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    @InjectMocks
    private PersonService personService;

    private static final String OWNER_EMAIL = "owner@mail.com";
    private static final String OWNER_USERNAME = "owner";
    private static final String OWNER_PASSWORD = "900";
    private static final Long OWNER_ID = 15L;

    @BeforeEach
    public void setMockOutput() {
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

    //positive create Owner
    @Test
    public void testCreateOwner() {
        Owner Owner = new Owner();
        Owner.setEmail("test@gmail.com");
        Owner.setPassword("testpassword");
        Owner.setUsername("testusername");
        Owner.setId(000L);
        Owner createdOwner = null;
        try {
            createdOwner = personService.createOwner("test@gmail.com", "testusername", "testpassword");
        } catch (PersonException e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(createdOwner);
        assertEquals(createdOwner.getEmail(), Owner.getEmail());
    }

    //negative create OWNER [missing email]
    @Test
    public void testCreateOwnerWithoutEmail() {
        Owner Owner = new Owner();
        Owner.setPassword("testpassword");
        Owner.setUsername("testusername");
        Owner.setId(000L);
        Owner createdOWNER = null;
        try {
            createdOWNER = personService.createOwner("", "testusername", "testpassword");
        } catch (PersonException e) {
            assertEquals("Email cannot be empty", e.getMessage());
        }
        assertNull(createdOWNER);
    }

    //negative create customer [missing username]
    @Test
    public void testCreateOwnerWithoutUsername() {
        Owner Owner = new Owner();
        Owner.setPassword("testpassword");
        Owner.setEmail("test@gmail.com");
        Owner.setId(000L);
        Owner createdOWNER = null;
        try {
            createdOWNER = personService.createOwner("test@gmail.com", "", "testpassword");
        } catch (PersonException e) {
            assertEquals("Username cannot be empty", e.getMessage());
        }
        assertNull(createdOWNER);
    }

    //negative create customer [missing password]
    @Test
    public void testCreateOwnerWithoutPassword() {
        Owner Owner = new Owner();
        Owner.setEmail("test@gmail.com");
        Owner.setUsername("testusername");
        Owner.setId(000L);
        Owner createdOWNER = null;
        try {
            createdOWNER = personService.createOwner("test@gmail.com", "testusername", "");
        } catch (PersonException e) {
            assertEquals("Password cannot be empty", e.getMessage());
        }
        assertNull(createdOWNER);
    }

    //positive login test
    @Test
    public void testLoginOwner() {
        Owner Owner = new Owner();
        Owner.setEmail(OWNER_EMAIL);
        Owner.setUsername(OWNER_USERNAME);
        Owner.setPassword(OWNER_PASSWORD);
        Owner.setId(OWNER_ID);
        Owner createdOWNER = null;
        try {
            createdOWNER = personService.loginOwner(Owner.getEmail(), Owner.getPassword());
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(createdOWNER);
    }

    //negative login test [customer does not exist]
    @Test
    public void testLoginOwnerWithWrongEmail() {
        Owner Owner = new Owner();
        Owner.setEmail("incorrect email");
        Owner.setUsername(OWNER_USERNAME);
        Owner.setPassword(OWNER_PASSWORD);
        Owner.setId(OWNER_ID);
        Owner createdOWNER = null;
        try {
            createdOWNER = personService.loginOwner(Owner.getEmail(), Owner.getPassword());
        } catch (PersonException e) {
            assertEquals("Owner does not exist", e.getMessage());
        }
    }

    //negative login test [wrong password]
    @Test
    public void testLoginOwnerWithWrongPassword() {
        Owner Owner = new Owner();
        Owner.setEmail(OWNER_EMAIL);
        Owner.setUsername(OWNER_USERNAME);
        Owner.setPassword("incorrect password");
        Owner.setId(OWNER_ID);
        Owner createdOWNER = null;
        try {
            createdOWNER = personService.loginOwner(Owner.getEmail(), Owner.getPassword());
        } catch (PersonException e) {
            assertEquals("Incorrect password", e.getMessage());
        }
    }

    //positive get test
    @Test
    public void testGetOwner() {
        Owner Owner = new Owner();
        Owner.setEmail(OWNER_EMAIL);
        Owner.setUsername(OWNER_USERNAME);
        Owner.setPassword(OWNER_PASSWORD);
        Owner.setId(OWNER_ID);
        Owner createdOWNER = null;
        try {
            createdOWNER = personService.getOwner(Owner.getEmail());
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(createdOWNER);
    }

    //positive get test
    @Test
    public void testGetOwnerWithWrongEmail() {
        Owner Owner = new Owner();
        Owner.setEmail("incorrect email");
        Owner.setUsername(OWNER_USERNAME);
        Owner.setPassword(OWNER_PASSWORD);
        Owner.setId(OWNER_ID);
        Owner createdOWNER = null;
        try {
            createdOWNER = personService.getOwner(Owner.getEmail());
        } catch (PersonException e) {
            assertEquals("owner with this email does not exist", e.getMessage());
        }
    }

    //positive update test
    @Test
    public void testUpdateOwner() {
        OwnerDto OwnerDto = new OwnerDto();
        OwnerDto.setEmail("new@gmail.com");
        OwnerDto.setPassword("newpassword");
        OwnerDto.setUsername("newusername");
        OwnerDto.setId(1L);
        Owner updatedOWNER = null;
        try {
            updatedOWNER = personService.updateOwner(OWNER_EMAIL, OwnerDto);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(updatedOWNER);
    }

    //negative update test [wrong email]
    @Test
    public void testUpdateOwnerWithWrongEmail() {
        OwnerDto OwnerDto = new OwnerDto();
        OwnerDto.setEmail("new@gmail.com");
        OwnerDto.setPassword("newpassword");
        OwnerDto.setUsername("newusername");
        OwnerDto.setId(1L);
        Owner updatedOWNER = null;
        try {
            updatedOWNER = personService.updateOwner("incorrectEmail@gmail.com", OwnerDto);
        } catch (PersonException e) {
            assertEquals("The Owner with this email does not exist", e.getMessage());
        }
    }

    //negative update test [dulicate email]
    @Test
    public void testUpdateOwnerWithDuplicateEmail() {
        OwnerDto OwnerDto = new OwnerDto();
        OwnerDto.setEmail("new@gmail.com");
        OwnerDto.setPassword("newpassword");
        OwnerDto.setUsername("newusername");
        OwnerDto.setId(1L);
        Owner updatedOWNER = null;
        try {
            updatedOWNER = personService.updateOwner(OWNER_EMAIL, OwnerDto);
        } catch (PersonException e) {
            assertEquals("Email has been taken", e.getMessage());
        }
    }

    //positive delete
    @Test
    public void testDeleteOwner() {
        Owner Owner = null;
        try {
            Owner = personService.deleteOwner(OWNER_EMAIL);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(Owner);
    }

    //nagative delete
    @Test
    public void testDeleteNonExistingOwner() {

        try {
            personService.deleteOwner("nonexisting@mail.ca");
        } catch (PersonException e) {
            assertEquals("The customer with the given id does not exist",e.getMessage());
        }

    }



}