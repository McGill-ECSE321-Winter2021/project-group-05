package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.BillRepository;
import ca.mcgill.ecse321.repairshop.dto.*;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;

    /**
     * Creates a bill for a given appointment
     * @param appointmentDto
     * @return
     */
    @Transactional
    public Bill createBill(AppointmentDto appointmentDto){
        Appointment appointment = convertToEntity(appointmentDto);
        Bill bill = createInstanceOfBill(appointment);
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

    private Bill createInstanceOfBill(Appointment appointment){
        Bill bill = new Bill();
        Customer customer = appointment.getCustomer();
        Date date = appointment.getTimeslot().getDate();
        float totalCost = RepairShopUtil.getTotalCostOfAppointment(appointment);

        bill.setCustomer(customer);
        bill.setDate(date);
        bill.setTotalCost(totalCost);
        bill.setAppointment(appointment);
        return bill;
    }

    private Appointment convertToEntity(AppointmentDto appointmentDto){
        Appointment appointment = new Appointment();
        appointment.setServices(convertToListOfEntity(appointmentDto.getServices()));
        appointment.setId(appointmentDto.getId());
        appointment.setTimeslot(convertToEntity(appointmentDto.getTimeSlot()));
        appointment.setCustomer(convertToEntity(appointmentDto.getCustomer()));
        return appointment;
    }

    private BookableService convertToEntity(BookableServiceDto bookableServiceDto){
      BookableService bookableService = new BookableService();
      bookableService.setName(bookableServiceDto.getName());
      bookableService.setCost(bookableServiceDto.getCost());
      bookableService.setId(bookableServiceDto.getId());
      bookableService.setDuration(bookableServiceDto.getDuration());
      return bookableService;
    }

    private List<BookableService> convertToListOfEntity(List<BookableServiceDto> bookableServiceDtos){
        List<BookableService> bookableServices = new ArrayList<>();
        for(BookableServiceDto bookableServiceDto : bookableServiceDtos){
            bookableServices.add(convertToEntity(bookableServiceDto));
        }
        return bookableServices;
    }

    private TimeSlot convertToEntity(TimeSlotDto timeSlotDto){
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setDate(timeSlotDto.getDate());
        timeSlot.setEndTime(timeSlotDto.getEndTime());
        timeSlot.setStartTime(timeSlotDto.getStartTime());
        timeSlot.setId(timeSlotDto.getId());
        return timeSlot;
    }

    private Customer convertToEntity(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setNoShow(customerDto.getNoShow());
        customer.setCardNumber(customerDto.getCardNumber());
        customer.setEmail(customerDto.getEmail());
        customer.setId(customerDto.getId());
        customer.setCvv(customerDto.getCvv());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        return customer;
    }
}
