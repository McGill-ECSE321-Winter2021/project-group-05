package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.Bill;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.model.RepairShop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestBillPersistence {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BillRepository billRepository;

    @AfterEach
    public void clearDatabase() {

        customerRepository.deleteAll();

        billRepository.deleteAll();

    }
    /**
     * testing Bill
     */
    public void testPersistAndLoadBill() {

        String name = "TestCustomer";
        String password = "TestPassword";
        String email = "testemail@123.com";

        Customer customer = new Customer();
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setEmail(email);

        RepairShop rs = new RepairShop();
        customer.setRepairShop(rs);

        customerRepository.save(customer);

        String customerId = customer.getEmail();

        Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        float testCost = 10;

        Bill bill = new Bill();
        bill.setDate(date);
        bill.setTotalCost(testCost);
        bill.setRepairShop(rs);

        billRepository.save(bill);

        Long billId = bill.getId();

        bill = billRepository.findBillById(billId);

        assertNotNull(bill);
        assertEquals(billId,bill.getId());
        assertEquals(testCost,bill.getTotalCost());
        assertEquals(date.toString(),bill.getDate().toString());
        assertNotNull(bill.getRepairShop());

    }
}
