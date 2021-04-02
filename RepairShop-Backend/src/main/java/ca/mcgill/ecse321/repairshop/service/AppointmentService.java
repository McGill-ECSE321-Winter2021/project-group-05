package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.*;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.utility.AppointmentException;
import ca.mcgill.ecse321.repairshop.utility.RepairShopUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @Autowired
    BillRepository billRepository;
    @Autowired
    TechnicianRepository technicianRepository;

    @Transactional
    public Appointment createAppointment(List<BookableService> services, Customer customer, TimeSlot timeslot)
            throws AppointmentException {
        // IF EITHER ARGUMENT IS NULL
        if (services == null || customer == null || timeslot == null){
            throw new AppointmentException("Customer, services and timeslot must all be selected for the appointment!");
        }

        // IF EITHER ARGUMENT IS NOT FOUND IN DATABASE
        if (customerRepository.findCustomerByEmail(customer.getEmail()) == null
                || timeSlotRepository.findTimeSlotById(timeslot.getId()) == null){
            throw new AppointmentException("Customer, Timeslot don't exist!");
        }
        for (BookableService s : services){
            if (serviceRepository.findServiceByName(s.getName()) == null){
                throw new AppointmentException("Bookable Service, Customer, Timeslot don't exist!");
            }
        }
        Appointment appointment = new Appointment();
        appointment.setServices(services);
        appointment.setCustomer(customer);
        appointment.setTimeslot(timeslot);

        Bill bill = new Bill();
        bill.setDate(timeslot.getDate());
        bill.setTotalCost(RepairShopUtil.getTotalCostOfAppointment(appointment));
        appointment.setBill(bill);
        billRepository.save(bill);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Transactional
    public Appointment getAppointment(Long id) {
        Appointment appointment = appointmentRepository.findAppointmentById(id);
        return appointment;
    }

    @Transactional
    public Appointment editAppointment (Appointment appointment,List<BookableService> service_new,
                                        TimeSlot timeSlot) throws AppointmentException {
        if (service_new == null || service_new.size() == 0){
            throw new AppointmentException("The Appointment must have at least one services");
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
        return appointment;
    }


    @Transactional
    public void deleteAppointment (Appointment appointment) throws AppointmentException {
        if (appointment == null){
            throw new AppointmentException("Cannot delete a null appointment");
        }
        Bill bill = appointment.getBill();
        List<Technician> technicians = RepairShopUtil.toList(technicianRepository.findAll());
        for(Technician technician : technicians){
            for (Appointment appointment1 : technician.getAppointments()){
                if(appointment1.getId() == appointment.getId()){
                    technician.getAppointments().remove(appointment);
                    technicianRepository.save(technician);
                    break;
                }
            }
        }
        appointmentRepository.deleteById(appointment.getId());
        billRepository.deleteById(bill.getId());
    }

    @Transactional
    public List<Appointment> getAppointmentsBookedByCustomer(Customer customer) throws AppointmentException {
        if (customer == null){
            throw new AppointmentException("customer cannot be null");
        }
        List<Appointment> appointmentsBookedByCustomer = appointmentRepository.findByCustomer(customer);
        return appointmentsBookedByCustomer;
    }

    @Transactional
    public void enterNoShow(Appointment appointment) throws AppointmentException {
        Customer customer = appointment.getCustomer();
        int noShow = customer.getNoShow() + 1;
        customer.setNoShow(noShow);
        customerRepository.save(customer);
    }

}
