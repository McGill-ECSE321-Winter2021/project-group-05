package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.dto.BusinessDto;
import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.utility.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TestBusinessService {
    @Mock
    private BusinessRepository businessRepository;

    @InjectMocks
    private BusinessService businessService;

    private static final String NAME = "repairshop";
    private static final String ADDRESS = "Montreal";
    private static final String PHONE_NUMBER = "1234567";
    private static final String EMAIL = "repairshop@mail.com";
    private static final Long ID = 0L;
    private static final Long NONEXISTING_ID=1L;

    @BeforeEach
    public void setMockOutPut(){
        lenient().when(businessRepository.findById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(ID)){
                Business business = new Business();
                business.setName(NAME);
                business.setAddress(ADDRESS);
                business.setPhoneNumber(PHONE_NUMBER);
                business.setEmail(EMAIL);
                business.setId(ID);
                return Optional.of(business);
            }else{
                return Optional.empty();
            }
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(businessRepository.save(any(Business.class))).thenAnswer(returnParameterAsAnswer);
        System.out.println(returnParameterAsAnswer);
    }

    @Test
    public void testCreateBusiness(){
        BusinessDto businessDto = new BusinessDto();
        businessDto.setName(NAME);
        businessDto.setAddress(ADDRESS);
        businessDto.setPhoneNumber(PHONE_NUMBER);
        businessDto.setEmail(EMAIL);
        businessDto.setId(ID);
        Business createdBusiness = null;
        try{
              createdBusiness = businessService.createBusiness(businessDto);
        }catch (BusinessException e){
            e.printStackTrace();
            fail();
        }
          assertNotNull(createdBusiness);
          assertEquals(createdBusiness.getAddress(), businessDto.getAddress());
          assertEquals(createdBusiness.getPhoneNumber(), businessDto.getPhoneNumber());
          assertEquals(createdBusiness.getName(), businessDto.getName());
          assertEquals(createdBusiness.getEmail(), businessDto.getEmail());
    }

    @Test
    public void testCreateBusinessWithBusinessAlreadyExist(){
        BusinessDto businessDto = new BusinessDto();
        businessDto.setName(NAME);
        businessDto.setAddress(ADDRESS);
        businessDto.setPhoneNumber(PHONE_NUMBER);
        businessDto.setEmail(EMAIL);
        businessDto.setId(ID);
        Business createdBusiness = null;
        try{
            createdBusiness = businessService.createBusiness(businessDto);
        }catch (BusinessException e){
            e.printStackTrace();
        }
        assertNotNull(createdBusiness);
        assertEquals(createdBusiness.getAddress(), businessDto.getAddress());
        assertEquals(createdBusiness.getPhoneNumber(), businessDto.getPhoneNumber());
        assertEquals(createdBusiness.getName(), businessDto.getName());
        assertEquals(createdBusiness.getEmail(), businessDto.getEmail());
        try {
            businessService.createBusiness(businessDto);
        }catch (BusinessException e){
            assertEquals("A business has already been created", e.getMessage());
        }

    }
    @Test
    public void testCreateBusinessWithEmptyEmail(){
        BusinessDto businessDto = new BusinessDto();
        businessDto.setName(NAME);
        businessDto.setAddress(ADDRESS);
        businessDto.setPhoneNumber(PHONE_NUMBER);

        Business createdBusiness = null;
        try{
            createdBusiness = businessService.createBusiness(businessDto);
        }catch (BusinessException e){
            assertEquals("Business email cannot be empty", e.getMessage());
        }
    }

    @Test
    public void testCreateBusinessWithEmptyName(){
        BusinessDto businessDto = new BusinessDto();
        businessDto.setEmail(EMAIL);
        businessDto.setAddress(ADDRESS);
        businessDto.setPhoneNumber(PHONE_NUMBER);

        Business createdBusiness = null;
        try{
            createdBusiness = businessService.createBusiness(businessDto);
        }catch (BusinessException e){
            assertEquals("Business name cannot be empty", e.getMessage());
        }
    }

    @Test
    public void testCreateBusinessWithEmptyPhoneNumber(){
        BusinessDto businessDto = new BusinessDto();
        businessDto.setEmail(EMAIL);
        businessDto.setAddress(ADDRESS);
        businessDto.setName(NAME);
        Business createdBusiness = null;
        try{
            createdBusiness = businessService.createBusiness(businessDto);
        }catch (BusinessException e){
            assertEquals("Business phone number cannot be empty", e.getMessage());
        }
    }

    @Test
    public void testCreateBusinessWithEmptyAdress(){
        BusinessDto businessDto = new BusinessDto();
        businessDto.setEmail(EMAIL);
        businessDto.setPhoneNumber(PHONE_NUMBER);
        businessDto.setName(NAME);
        Business createdBusiness = null;
        try{
            createdBusiness = businessService.createBusiness(businessDto);
        }catch (BusinessException e){
            assertEquals("Business address cannot be empty", e.getMessage());
        }
    }

    @Test
    public void testUpdateBusiness(){
        BusinessDto businessDto = new BusinessDto();
        businessDto.setName(NAME);
        businessDto.setAddress(ADDRESS);
        businessDto.setPhoneNumber(PHONE_NUMBER);
        businessDto.setEmail(EMAIL);
        businessDto.setId(ID);

        Business createdBusiness = null;
        try{
            businessService.createBusiness(businessDto);
            createdBusiness = businessService.editBusiness(businessDto.getId(), businessDto);
        }catch (BusinessException e){
            e.printStackTrace();
        }
        assertNotNull(createdBusiness);
        assertEquals(createdBusiness.getAddress(), businessDto.getAddress());
        assertEquals(createdBusiness.getPhoneNumber(), businessDto.getPhoneNumber());
        assertEquals(createdBusiness.getName(), businessDto.getName());
        assertEquals(createdBusiness.getEmail(), businessDto.getEmail());
    }

    /**
     * TESTING getBusiness(id)
     */
    /**
    * POSITIVE
     */
    @Test
    public void testGetBusiness(){
        BusinessDto businessDto = new BusinessDto();
        businessDto.setName(NAME);
        businessDto.setAddress(ADDRESS);
        businessDto.setPhoneNumber(PHONE_NUMBER);
        businessDto.setEmail(EMAIL);
        businessDto.setId(ID);

        Business getBusiness = null;
        try{
            businessService.createBusiness(businessDto);
            getBusiness = businessService.getBusiness(businessDto.getId());
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
        assertNotNull(getBusiness);
        assertEquals(getBusiness.getName(),NAME);
        assertEquals(getBusiness.getAddress(),ADDRESS);
        assertEquals(getBusiness.getPhoneNumber(),PHONE_NUMBER);
        assertEquals(getBusiness.getEmail(),EMAIL);
        assertEquals(getBusiness.getId(),ID);
    }

    /**
     * NEGATIVE
     */
    @Test
    public void testGutBusinessWithInvalidID(){
        Business business=null;
        try{
             business = businessService.getBusiness(NONEXISTING_ID);
        }
        catch (NoSuchElementException e){
            assertEquals(e.getMessage(),"No value present");
            assertNull(business);
        }


    }

    /**
     * TESTING getBusiness
     * POSITIVE
     */
    @Test
    public void testGutBusinessNoParam(){
        BusinessDto businessDto = new BusinessDto();
        businessDto.setName(NAME);
        businessDto.setAddress(ADDRESS);
        businessDto.setPhoneNumber(PHONE_NUMBER);
        businessDto.setEmail(EMAIL);
        businessDto.setId(ID);

        Business getBusiness = null;
        try{
            businessService.createBusiness(businessDto);
            getBusiness = businessService.getBusiness();
        }
        catch (BusinessException e){
            e.printStackTrace();
            fail();
        }
        assertNotNull(getBusiness);
        assertEquals(getBusiness.getName(),NAME);
        assertEquals(getBusiness.getAddress(),ADDRESS);
        assertEquals(getBusiness.getPhoneNumber(),PHONE_NUMBER);
        assertEquals(getBusiness.getEmail(),EMAIL);
        assertEquals(getBusiness.getId(),ID);


    }
    /**
     * NEGATIVE
     */
    @Test
    public void testGutBusinessNoParamNegative(){
        // no business has been created
        Business business =null;
        try {
            business = businessService.getBusiness();
        }
        catch (BusinessException e){
            assertNull(business);
            assertEquals(e.getMessage(),"Business does not exist, Please create one");
        }


    }

    /**
     * TESTING deleteBusiness
     * POSITIVE
     */
    @Test
    public void testDeleteBusiness(){
        BusinessDto businessDto = new BusinessDto();
        businessDto.setEmail(EMAIL);
        businessDto.setPhoneNumber(PHONE_NUMBER);
        businessDto.setName(NAME);
        businessDto.setAddress(ADDRESS);
        businessDto.setId(ID);

        Business deletedBusiness = null;
        try{
            deletedBusiness = businessService.deleteBusiness(businessDto.getId());
        }catch (BusinessException e){
            e.printStackTrace();
        }
        assertNotNull(deletedBusiness);
        assertEquals(deletedBusiness.getAddress(), businessDto.getAddress());
        assertEquals(deletedBusiness.getPhoneNumber(), businessDto.getPhoneNumber());
        assertEquals(deletedBusiness.getName(), businessDto.getName());
        assertEquals(deletedBusiness.getEmail(), businessDto.getEmail());
    }
    /**
     * NEGATIVE
     */
    @Test
    public void testDeleteBusinessNotExist(){
        Business deletedBusiness=null;
        try{
            deletedBusiness=businessService.deleteBusiness(NONEXISTING_ID); // the id doesn't exist
        }catch (BusinessException e){
            assertNull(deletedBusiness);
            assertEquals(e.getMessage(),"Cannot delete because business does not exist");

        }



    }

}
