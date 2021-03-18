package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.dto.OwnerDto;
import ca.mcgill.ecse321.repairshop.dto.TechnicianDto;
import ca.mcgill.ecse321.repairshop.model.Owner;
import ca.mcgill.ecse321.repairshop.model.Technician;
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
public class TestTechnicianService {

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

    private static final String TECH_EMAIL = "tech@mail.com";
    private static final String TECH_USERNAME = "tech";
    private static final String TECH_PASSWORD = "678";
    private static final Long TECH_ID = 14L;

    @BeforeEach
    public void setMockOutput() {
        //Technician by email
        lenient().when(technicianRepository.findTechnicianByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TECH_EMAIL)) {
                Technician Technician = new Technician();
                Technician.setEmail(TECH_EMAIL);
                Technician.setUsername(TECH_USERNAME);
                Technician.setPassword(TECH_PASSWORD);
                Technician.setId(TECH_ID);
                return Technician;
            } else {
                return null;
            }
        });
    }

    //CUSTOMER

    //positive create Technician
    @Test
    public void testCreateCustomer() {
        Technician Technician = new Technician();
        Technician.setEmail("test@gmail.com");
        Technician.setPassword("testpassword");
        Technician.setUsername("testusername");
        Technician.setId(000L);
        Technician createdTechnician = null;
        try {
            createdTechnician = personService.createTechnician("test@gmail.com", "testusername", "testpassword");
        } catch (PersonException e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(createdTechnician);
        assertEquals(createdTechnician.getEmail(), Technician.getEmail());
    }

    //negative create Technician [missing email]
    @Test
    public void testCreateCustomerWithoutEmail() {
        Technician Technician = new Technician();
        Technician.setPassword("testpassword");
        Technician.setUsername("testusername");
        Technician.setId(000L);
        Technician createdTechnician = null;
        try {
            createdTechnician = personService.createTechnician("", "testusername", "testpassword");
        } catch (PersonException e) {
            assertEquals("Email cannot be empty", e.getMessage());
        }
        assertNull(createdTechnician);
    }

    //negative create customer [missing username]
    @Test
    public void testCreateCustomerWithoutUsername() {
        Technician Technician = new Technician();
        Technician.setPassword("testpassword");
        Technician.setEmail("test@gmail.com");
        Technician.setId(000L);
        Technician createdTechnician = null;
        try {
            createdTechnician = personService.createTechnician("test@gmail.com", "", "testpassword");
        } catch (PersonException e) {
            assertEquals("Username cannot be empty", e.getMessage());
        }
        assertNull(createdTechnician);
    }

    //negative create customer [missing password]
    @Test
    public void testCreateCustomerWithoutPassword() {
        Technician Technician = new Technician();
        Technician.setEmail("test@gmail.com");
        Technician.setUsername("testusername");
        Technician.setId(000L);
        Technician createdTechnician = null;
        try {
            createdTechnician = personService.createTechnician("test@gmail.com", "testusername", "");
        } catch (PersonException e) {
            assertEquals("Password cannot be empty", e.getMessage());
        }
        assertNull(createdTechnician);
    }

    //positive login test
    @Test
    public void testLoginCustomer() {
        Technician Technician = new Technician();
        Technician.setEmail(TECH_EMAIL);
        Technician.setUsername(TECH_USERNAME);
        Technician.setPassword(TECH_PASSWORD);
        Technician.setId(TECH_ID);
        Technician createdTechnician = null;
        try {
            createdTechnician = personService.loginTechnician(Technician.getEmail(), Technician.getPassword());
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(createdTechnician);
    }

    //negative login test [customer does not exist]
    @Test
    public void testLoginCustomerWithWrongEmail() {
        Technician Technician = new Technician();
        Technician.setEmail("incorrect email");
        Technician.setUsername(TECH_USERNAME);
        Technician.setPassword(TECH_PASSWORD);
        Technician.setId(TECH_ID);
        Technician createdTechnician = null;
        try {
            createdTechnician = personService.loginTechnician(Technician.getEmail(), Technician.getPassword());
        } catch (PersonException e) {
            assertEquals("Technician does not exist", e.getMessage());
        }
    }

    //negative login test [wrong password]
    @Test
    public void testLoginCustomerWithWrongPassword() {
        Technician Technician = new Technician();
        Technician.setEmail(TECH_EMAIL);
        Technician.setUsername(TECH_USERNAME);
        Technician.setPassword("incorrect password");
        Technician.setId(TECH_ID);
        Technician createdTechnician = null;
        try {
            createdTechnician = personService.loginTechnician(Technician.getEmail(), Technician.getPassword());
        } catch (PersonException e) {
            assertEquals("Incorrect password", e.getMessage());
        }
    }

    //positive get test
    @Test
    public void testGetCustomer() {
        Technician Technician = new Technician();
        Technician.setEmail(TECH_EMAIL);
        Technician.setUsername(TECH_USERNAME);
        Technician.setPassword(TECH_PASSWORD);
        Technician.setId(TECH_ID);
        Technician createdTechnician = null;
        try {
            createdTechnician = personService.getTechnician(Technician.getEmail());
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(createdTechnician);
    }

    //positive get test
    @Test
    public void testGetCustomerWithWrongEmail() {
        Technician Technician = new Technician();
        Technician.setEmail("incorrect email");
        Technician.setUsername(TECH_USERNAME);
        Technician.setPassword(TECH_PASSWORD);
        Technician.setId(TECH_ID);
        Technician createdTechnician = null;
        try {
            createdTechnician = personService.getTechnician(Technician.getEmail());
        } catch (PersonException e) {
            assertEquals("Technician with this email does not exist", e.getMessage());
        }
    }

    //positive update test
    @Test
    public void testUpdateCustomer() {
        TechnicianDto TechnicianDto = new TechnicianDto();
        TechnicianDto.setEmail("new@gmail.com");
        TechnicianDto.setPassword("newpassword");
        TechnicianDto.setUsername("newusername");
        TechnicianDto.setId(1L);
        Technician updatedTechnician = null;
        try {
            updatedTechnician = personService.updateTechnician(TECH_EMAIL, TechnicianDto);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(updatedTechnician);
    }

    //negative update test [wrong email]
    @Test
    public void testUpdateCustomerWithWrongEmail() {
        TechnicianDto TechnicianDto = new TechnicianDto();
        TechnicianDto.setEmail("new@gmail.com");
        TechnicianDto.setPassword("newpassword");
        TechnicianDto.setUsername("newusername");
        TechnicianDto.setId(1L);
        Technician updatedTechnician = null;
        try {
            updatedTechnician = personService.updateTechnician("incorrectEmail@gmail.com", TechnicianDto);
        } catch (PersonException e) {
            assertEquals("The Technician with this email does not exist", e.getMessage());
        }
    }

    //positive delete
    @Test
    public void testDeleteCustomer() {
        Technician Technician = null;
        try {
            Technician = personService.deleteTechnician(TECH_EMAIL);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        assertNotNull(Technician);
    }

}