package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.dao.AppointmentRepository;
import ca.mcgill.ecse321.repairshop.dao.TimeSlotRepository;
import ca.mcgill.ecse321.repairshop.model.Appointment;
import ca.mcgill.ecse321.repairshop.model.Bill;
import ca.mcgill.ecse321.repairshop.model.Customer;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import ca.mcgill.ecse321.repairshop.utility.ListUtil;
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

    @Transactional
    public Appointment createAppointment(ca.mcgill.ecse321.repairshop.model.Service service, Customer customer, TimeSlot timeslot, Bill bill) {
        Appointment appointment = new Appointment();
        appointment.setService(service);
        appointment.setCustomer(customer);
        appointment.setTimeslot(timeslot);
        timeSlotRepository.save(timeslot);
        return appointment;
    }

    @Transactional
    public Appointment getAppointment(Long id) {
        Appointment appointment = appointmentRepository.findAppointmentById(id);
        return appointment;
    }

    @Transactional
    public List<Appointment> getAllAppointment() {
        return ListUtil.toList(appointmentRepository.findAll());
    }

    @Transactional
    public void editAppointment (Appointment appointment, Bill bill,ca.mcgill.ecse321.repairshop.model.Service service_obj,TimeSlot timeSlot){

        if (bill != null) {
            appointment.setBill(bill);
        }
        if (service_obj != null){
            appointment.setService(service_obj);
        }
        if (timeSlot != null){
            appointment.setTimeslot(timeSlot);
        }
        appointmentRepository.save(appointment);
    }


    @Transactional
    public void deleteAppointment (Appointment appointment){

        //delete appointment from the service

        List<Appointment> appointments = appointmentRepository.findByService(appointment.getService());
        for (Appointment app: appointments){
            // find the target appointment
            if ((app.getId()).equals(appointment.getId())){
                appointments.remove(app);
                break;
            }
        }


        // delete appointment from the customer
        appointments = appointmentRepository.findByCustomer(appointment.getCustomer());
        for (Appointment app: appointments){
            // find the target appointment
            if ((app.getId()).equals(appointment.getId())){
                appointments.remove(app);
                break;
            }
        }

        // delete appointment from the bill

        appointments = appointmentRepository.findByBill(appointment.getBill());
        for (Appointment app: appointments){
            // find the target appointment
            //todo :: check --> appointmentRepository.deleteById(appointment.getId());
            if ((app.getId()).equals(appointment.getId())){
                appointments.remove(app);
                break;
            }
        }

        //delete appointment from the repository

        appointments = getAllAppointment();
        for (Appointment app: appointments){
            // find the target appointment
            if ((app.getId()).equals(appointment.getId())){
                appointments.remove(app);
                break;
            }
        }
        // todo: how to save the database after deletion?
    }
    @Transactional
    public List<Appointment> getAppointmentsBookedByCustomer(Customer customer) {
        List<Appointment> appointmentsBookedByCustomer = new ArrayList<>();
        for (Appointment app : appointmentRepository.findByCustomer(customer)) {
            appointmentsBookedByCustomer.add(app);
        }
        return appointmentsBookedByCustomer;
    }

    @Transactional
    public List<Appointment> getAppointmentsOfService(ca.mcgill.ecse321.repairshop.model.Service service_obj) {
        List<Appointment> appointmentsOfService = new ArrayList<>();
        for (Appointment app : appointmentRepository.findByService(service_obj)) {
            appointmentsOfService.add(app);
        }
        return appointmentsOfService;
    }

    @Transactional
    public List<Appointment> getAppointmentsOfBill(Bill bill){
        List<Appointment> appointmentsOfBill = new ArrayList<>();
        for (Appointment app : appointmentRepository.findByBill(bill)) {
            appointmentsOfBill.add(app);
        }
        return appointmentsOfBill;
    }

//    @Transactional
//    public List<Appointment> getAppointmentsByTimeSlot (TimeSlot timeslot){
//        List<Appointment> appointmentsOfTime = new ArrayList<>();
//        for (Appointment app : appointmentRepository.findByTimeSlot(timeslot)) {
//            appointmentsOfTime.add(app);
//        }
//        return appointmentsOfTime;
//    }
}
