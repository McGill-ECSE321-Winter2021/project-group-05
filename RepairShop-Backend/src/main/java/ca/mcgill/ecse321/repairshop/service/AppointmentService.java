package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.AppointmentRepository;
import ca.mcgill.ecse321.repairshop.dao.CustomerRepository;
import ca.mcgill.ecse321.repairshop.dao.ServiceRepository;
import ca.mcgill.ecse321.repairshop.dao.TimeSlotRepository;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    // TODO: is this really needed?
    @Transactional
    public List<Appointment> getAllAppointment() {
        return RepairShopUtil.toList(appointmentRepository.findAll());
    }


    @Transactional
    public void editAppointment (Appointment appointment,List<BookableService> service_add, List<BookableService> service_delete,TimeSlot timeSlot) throws IllegalArgumentException{
        List<BookableService> services = appointment.getServices();
        if (services.size() + service_add.size() - service_delete.size() <=0){
            throw new IllegalArgumentException("Appointment must contain at least one service");
        }
        float total_cost=appointment.getBill().getTotalCost();
        Bill new_bill = new Bill();
        if (service_add != null){
           for (BookableService s: service_add){
               services.add(s);
               total_cost += s.getCost();
           }
        }

        if (service_delete != null){
            for (BookableService s: service_delete){
                services.remove(s);
                total_cost -= s.getCost();
            }
        }

        appointment.setServices(services);


        if (timeSlot != null){
            appointment.setTimeslot(timeSlot);
        }

        new_bill.setTotalCost(total_cost);
        new_bill.setDate(appointment.getTimeslot().getDate());
        new_bill.setCustomer(appointment.getCustomer());
        new_bill.setAppointment(appointment);

        appointment.setBill(new_bill);

        appointmentRepository.save(appointment);
    }


    @Transactional
    public void deleteAppointment (Appointment appointment){
        if (appointment == null) {
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
