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

    @Transactional
    public Bill createBill(Customer customer, Appointment appointment, float totalCost, Date date){
        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setDate(date);
        bill.setTotalCost(totalCost);
        bill.setAppointment(appointment);
        billRepository.save(bill);
        return bill;
    }

    @Transactional
    public Bill getBill(Long id) {
        Bill bill = billRepository.findBillById(id);
        return bill;
    }

    @Transactional
    public List<Bill> getAllBill() {
        return RepairShopUtil.toList(billRepository.findAll());
    }

}
