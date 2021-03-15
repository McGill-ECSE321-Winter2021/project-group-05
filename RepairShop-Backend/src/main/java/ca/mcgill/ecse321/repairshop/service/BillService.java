package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.AppointmentRepository;
import ca.mcgill.ecse321.repairshop.dao.BillRepository;
import ca.mcgill.ecse321.repairshop.dao.CustomerRepository;
import ca.mcgill.ecse321.repairshop.dto.*;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.BillException;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    CustomerRepository customerRepository;


    /**
     * Creates a bill for a given appointment
     * @param appointmentDto
     * @return
     */
    @Transactional
    public Bill createBill(AppointmentDto appointmentDto) throws BillException {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentDto.getId());
        if(!appointmentOptional.isPresent()){
            throw new BillException("Could not create a bill because no appointment was found");
        }
        Appointment appointment = appointmentOptional.get();
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
    public Bill updateBill(AppointmentDto appointmentDto) throws BillException {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentDto.getId());
        if(!appointmentOptional.isPresent()){
            throw new BillException("Could not find a bill because no appointment was found");
        }
        Appointment appointment = appointmentOptional.get();
        Optional<Bill> billOptional = billRepository.findById(appointment.getBill().getId());
        if(!billOptional.isPresent()){
            throw new BillException("Count not find bill from appointment");
        }
        Bill bill = billOptional.get();
        float totalCost = RepairShopUtil.getTotalCostOfAppointment(appointment);
        bill.setDate(appointment.getTimeslot().getDate());
        bill.setTotalCost(totalCost);
        billRepository.save(bill);
        return bill;
    }

    public List<Bill> getAllBillsOfCustomer(CustomerDto customerDto) throws BillException{
        Customer customer = convertToEntity(customerDto);
        List<Appointment> appointments = appointmentRepository.findByCustomer(customer);
        List<Bill> bills = new ArrayList<>();
        for(Appointment appointment : appointments){
            bills.add(appointment.getBill());
        }
        return bills;
    }

    //HELPER FUNCTIONS

    private List<Bill> getAllBills(List<Appointment> appointments){
        List<Bill> bills = new ArrayList<Bill>();
        for(int i = 0; i < appointments.size(); ++i){
            Bill bill = appointments.get(i).getBill();
            bills.add(bill);
        }
        return bills;
    }

    private Bill createInstanceOfBill(Appointment appointment){
        Bill bill = new Bill();
        Date date = appointment.getTimeslot().getDate();
        float totalCost = RepairShopUtil.getTotalCostOfAppointment(appointment);

        bill.setDate(date);
        bill.setTotalCost(totalCost);
        return bill;
    }
/*
    private Appointment convertToEntity(AppointmentDto appointmentDto){
        Appointment appointment = new Appointment();
        appointment.setServices(convertToListOfEntity(appointmentDto.getServices()));
        appointment.setId(appointmentDto.getId());
        appointment.setTimeslot(convertToEntity(appointmentDto.getTimeSlot()));
        appointment.setCustomer(convertToEntity(appointmentDto.getCustomer()));
        return appointment;
    }

 */

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
