package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    TimeSlotRepository timeSlotRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    BillRepository billRepository;


    @Transactional
    public Appointment createAppointment(List<BookableService> services, Customer customer, TimeSlot timeslot) {
        // IF EITHER ARGUMENT IS NULL
        if (services == null || customer == null || timeslot == null){
            throw new IllegalArgumentException("Customer, services and timeslot must all be selected for the appointment!");
        }

        // IF EITHER ARGUMENT IS NOT FOUND IN DATABASE
        if (customerRepository.findCustomerById(customer.getId()) == null ||timeSlotRepository.findTimeSlotById(timeslot.getId()) == null){
            throw new IllegalArgumentException("Bookable Service, Customer, Timeslot don't exist!");
        }
        for (BookableService s : services){
            if (serviceRepository.findServiceById(s.getId())== null){
                throw new IllegalArgumentException("Bookable Service, Customer, Timeslot don't exist!");
            }
        }
        Appointment appointment = new Appointment();
        appointment.setServices(services);
        appointment.setCustomer(customer);
        appointment.setTimeslot(timeslot);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Transactional
    public Appointment getAppointment(Long id) {
        Appointment appointment = appointmentRepository.findAppointmentById(id);
        return appointment;
    }

    @Transactional
    public void editAppointment (Appointment appointment,List<BookableService> service_new,
                                 TimeSlot timeSlot) throws IllegalArgumentException{

        if (service_new.size() >0){
            appointment.setServices(service_new);
            float total_cost=0;
            for (BookableService s: service_new){
                total_cost+=s.getCost();
            }
            Bill new_bill = appointment.getBill();
            new_bill.setTotalCost(total_cost);
            appointment.setBill(new_bill);
        }

        if (timeSlot != null){
            appointment.setTimeslot(timeSlot);
        }

        Bill new_bill = appointment.getBill();
        new_bill.setDate(appointment.getTimeslot().getDate());
        appointment.setBill(new_bill);

        billRepository.save(new_bill);
        timeSlotRepository.save(timeSlot);
        appointmentRepository.save(appointment);


    }

    @Transactional
    public void deleteAppointment (Appointment appointment){

        //delete appointment from the service
        appointmentRepository.deleteById(appointment.getId());
        // todo: need to delete from the customer, service as well?
    }

    @Transactional
    public List<Appointment> getAppointmentsBookedByCustomer(Customer customer) {

        List<Appointment> appointmentsBookedByCustomer = appointmentRepository.findByCustomer(customer);
        return appointmentsBookedByCustomer;
    }
    
    @Transactional
    public void enterNoShow(Appointment appointment){
        int noShow = appointment.getCustomer().getNoShow();
        noShow++;
        appointment.getCustomer().setNoShow(noShow);
        
    }


}
