package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.BillRepository;
import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.Bill;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;

    /**
     * Creates a bill for a given appointment
     * @param appointment
     * @return
     */
    @Transactional
    public Bill createBill(Appointment appointment){
        Bill bill = createInstanceOfBill(appointment);
        bill.setAppointment(appointment);
        billRepository.save(bill);
        return bill;
    }

    /**
     * Gets a bill with the given id
     * @param id
     * @return
     */
    @Transactional
    public Bill getBill(Long id) {
        Bill bill = billRepository.findBillById(id);
        return bill;
    }

    /**
     * Gets all the bill associated with a customer
     * @return
     */
    @Transactional
    public List<Bill> getAllBillOfCustomer(Customer customer) {
        List<Bill> bills = billRepository.findBillByCustomer(customer);
        return bills;
    }

    //HELPER FUNCTIONS

    /**
     * Creates an instance of a bill given an appointment
     * @param appointment
     * @return
     */
    private Bill createInstanceOfBill(Appointment appointment){
        Bill bill = new Bill();
        Customer customer = appointment.getCustomer();
        Date date = appointment.getTimeslot().getDate();
        float totalCost = RepairShopUtil.getTotalCost(appointment);

        bill.setCustomer(customer);
        bill.setDate(date);
        bill.setTotalCost(totalCost);
        return bill;
    }

}
