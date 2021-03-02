package ca.mcgill.ecse321.repairshop.service;

import ca.mcgill.ecse321.repairshop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.repairshop.dao.ServiceRepository;
import ca.mcgill.ecse321.repairshop.dao.BillRepository;
import ca.mcgill.ecse321.repairshop.dao.TimeSlotRepository;
import ca.mcgill.ecse321.repairshop.dao.CustomerRepository;
import ca.mcgill.ecse321.repairshop.dao.AppointmentRepository;
import ca.mcgill.ecse321.repairshop.dao.AdministratorRepository;
import ca.mcgill.ecse321.repairshop.dao.BusinessRepository;
import ca.mcgill.ecse321.repairshop.dao.OwnerRepository;
import ca.mcgill.ecse321.repairshop.dao.TechnicianRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepairShopService {
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    BillRepository billRepository;
    @Autowired
    TimeSlotRepository timeSlotRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AdministratorRepository administratorRepository;
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    TechnicianRepository technicianRepository;

    /** TODO: use associations to get A list of X
     * Add &/ modify the methods here as we go
     */

    /**
     * Service
     */
    @Transactional
    public ca.mcgill.ecse321.repairshop.model.Service createService(String name, float cost, int duration, List<Appointment> appointments){
        // service is a reserved keywork
        ca.mcgill.ecse321.repairshop.model.Service service = new ca.mcgill.ecse321.repairshop.model.Service();
        service.setCost(cost);
        service.setName(name);
        service.setDuration(duration);
        service.setAppointments(appointments);
        serviceRepository.save(service);
        return service;
    }
    @Transactional
    public ca.mcgill.ecse321.repairshop.model.Service getService(Long id) {
        ca.mcgill.ecse321.repairshop.model.Service service = serviceRepository.findServiceById(id);
        return service;
    }
    @Transactional
    public List<ca.mcgill.ecse321.repairshop.model.Service> getAllService() {
        return toList(serviceRepository.findAll());
    }

    /**
     * Bill
     */
    @Transactional
    public Bill createBill(Customer customer, List<Appointment> appointments, float totalCost, Date date){
        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setDate(date);
        bill.setTotalCost(totalCost);
        bill.setAppointments(appointments);
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
        return toList(billRepository.findAll());
    }

    /**
     * TimeSlot
     */
    @Transactional
    public TimeSlot createTimeSlot(Date date, Time startTime,Time endTime){
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setDate(date);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }
    @Transactional
    public TimeSlot getTimeSlot(Long id) {
        TimeSlot timeSlot = timeSlotRepository.findTimeSlotById(id);
        return timeSlot;
    }
    @Transactional
    public List<TimeSlot> getAllTimeSlot() {
        return toList(timeSlotRepository.findAll());
    }

    /**
     * Appointment
     */
    @Transactional
    public Appointment createAppointment(ca.mcgill.ecse321.repairshop.model.Service service,Customer customer,TimeSlot timeslot,Bill bill) {
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
        return toList(appointmentRepository.findAll());
    }
    /**
     * Business
     */
    @Transactional
    public Business createBusiness(String name,String address,String phoneNumber,String email,List<TimeSlot> timeslot) {
        Business business = new Business();
        business.setAddress(address);
        business.setEmail(email);
        business.setName(name);
        business.setPhoneNumber(phoneNumber);
        business.setTimeslot(timeslot);
        businessRepository.save(business);
        return business;
    }
    @Transactional
    public Business getBusiness(Long id) {
        Business business = businessRepository.findBusinessById(id);
        return business;
    }
    @Transactional
    public List<Business> getAllBusiness() {
        return toList(businessRepository.findAll());
    }
    /**
     * creating person
     * Customer
     */
    @Transactional
    public Customer createCustomer(String email, String username, String password){
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setUsername(username);
        customerRepository.save(customer);
        return customer;
    }
    @Transactional
    public Customer getCustomer(Long id) {
        Customer customer = customerRepository.findCustomerById(id);
        return customer;
    }
    @Transactional
    public List<Customer> getAllCustomer() {
        return toList(customerRepository.findAll());
    }

    /**
     * Owner
     */
    @Transactional
    public Owner createOwner(String email, String username, String password){
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setUsername(username);
        ownerRepository.save(owner);
        return owner;
    }
    @Transactional
    public Owner getOwner(Long id) {
        Owner owner = ownerRepository.findOwnerById(id);
        return owner;
    }
    @Transactional
    public List<Owner> getAllOwner() {
        return toList(ownerRepository.findAll());
    }

    /**
     * Technician
     */
    @Transactional
    public Technician createTechnician(String email, String username, String password){
        Technician technician = new Technician();
        technician.setEmail(email);
        technician.setPassword(password);
        technician.setUsername(username);
        technicianRepository.save(technician);
        return technician;
    }
    @Transactional
    public Technician getTechnician(Long id) {
        Technician technician = technicianRepository.findTechnicianById(id);
        return technician;
    }
    @Transactional
    public List<Technician> getAllTechnician() {
        return toList(technicianRepository.findAll());
    }

    /**
     * Administrator
     */
    @Transactional
    public Administrator createAdministrator(String email, String username, String password){
        Administrator administrator = new Administrator();
        administrator.setEmail(email);
        administrator.setPassword(password);
        administrator.setUsername(username);
        administratorRepository.save(administrator);
        return administrator;
    }
    @Transactional
    public Administrator getAdministrator(Long id) {
        Administrator administrator = administratorRepository.findAdministratorById(id);
        return administrator;
    }
    @Transactional
    public List<Administrator> getAllAdministrator() {
        return toList(administratorRepository.findAll());
    }


    /**
     * helper methos
     */
    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
