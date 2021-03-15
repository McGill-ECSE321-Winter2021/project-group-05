package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.dto.BusinessDto;
import ca.mcgill.ecse321.repairshop.model.Bill;
import ca.mcgill.ecse321.repairshop.model.BookableService;
import ca.mcgill.ecse321.repairshop.model.Business;
import ca.mcgill.ecse321.repairshop.utility.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import java.util.Optional;

public class TestBillService {
    @Mock
    BillRepository billRepository;
    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    TimeSlotRepository timeSlotRepository;
    @Mock
    ServiceRepository serviceRepository;

    @InjectMocks
    BillService billService;

    private static long BILL_ID = 0L;
    private static long APP_ID = 1L;
    private static long TIMESLOT_ID = 2L;
    private static long CUSTOMER_ID = 3L;
    private static long SERVICE_ID = 4L;

    @BeforeEach
    public void setMockOutput(){
        lenient().when(billRepository.findById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(BILL_ID)){
                Bill bill = new Bill();
                bill.setId(BILL_ID);
                return Optional.of(bill);
            }else{
                return Optional.empty();
            }
        });

        lenient().when((appointmentRepository.findById(anyLong()))).thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(APP_ID)) {

                        Bill bill = new Bill();
                        bill.setId(BILL_ID);
                        BookableService bookableService = new BookableService();
                        bookableService.setDuration(100);
                    }
                      return null;

        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(billRepository.save(any(Bill.class))).thenAnswer(returnParameterAsAnswer);
    }
}
