package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.BillException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    BillService billService = new BillService();


    @Transactional
    public Appointment createAppointment(List<BookableService> services, Customer customer, TimeSlot timeslot) {
        // IF EITHER ARGUMENT IS NULL
        if (services == null || customer == null || timeslot == null){
            throw new IllegalArgumentException("Customer, services and timeslot must all be selected for the appointment!");
        }

        // IF EITHER ARGUMENT IS NOT FOUND IN DATABASE
        if (customerRepository.findCustomerById(customer.getId()) == null
                || timeSlotRepository.findTimeSlotById(timeslot.getId()) == null){
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
        //try{
            // CREATE BILL FOR APPOINTMENT
            //Bill bill = billService.createBill(appointment);
            //appointment.setBill(bill);
            appointmentRepository.save(appointment);
            return appointment;
        //}
       //catch (BillException b){
         //   throw new IllegalArgumentException("Bill cannot be created");
       //}

    }

    @Transactional
    public Appointment getAppointment(Long id) {
        Appointment appointment = appointmentRepository.findAppointmentById(id);
        return appointment;
    }

    @Transactional
    public void editAppointment (Appointment appointment,List<BookableService> service_new,
                                 TimeSlot timeSlot){
        if (service_new.size() == 0){
            throw new IllegalArgumentException("The Appointment must have at least one services");
        }

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
        if (appointment == null){
            throw new IllegalArgumentException("Cannot delete a null appointment");
        }
        //delete appointment from the service
        appointmentRepository.deleteById(appointment.getId());
        // todo: need to delete from the customer, service as well?
    }

    @Transactional
    public List<Appointment> getAppointmentsBookedByCustomer(Customer customer) {

        List<Appointment> appointmentsBookedByCustomer = appointmentRepository.findByCustomer(customer);
        return appointmentsBookedByCustomer;
    }


}
