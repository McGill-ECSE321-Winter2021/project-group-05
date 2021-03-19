package ca.mcgill.ecse321.repairshop.utility;

import ca.mcgill.ecse321.repairshop.dto.*;
import ca.mcgill.ecse321.repairshop.model.*;

import java.util.ArrayList;
import java.util.List;

public class RepairShopUtil {

    public static Person currentUser;

    public static Person getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Person currentUser) {
        RepairShopUtil.currentUser = currentUser;
    }


    /**
     * Converts an iterable data type to a list
     * @param iterable
     * @param <T>
     * @return
     */
    public static<T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    public static AppointmentDto convertToDto(Appointment appointment) {
       if (appointment == null) {
           throw new IllegalArgumentException("There is no such Appointment!");
       }
       //service, customer, timeslot, bill
       AppointmentDto appointmentDto = new AppointmentDto(convertToDto(appointment.getServices()),
               convertToDto(appointment.getCustomer()),convertToDto(appointment.getTimeslot()),
               convertToDto(appointment.getBill()), appointment.getId());
       return appointmentDto;
   }

    public static TimeSlotDto convertToDto(TimeSlot timeSlot){
        if (timeSlot == null) {
            throw new IllegalArgumentException("There is no such TimeSlot!");
        }
        //service, customer, timeslot bill
        TimeSlotDto timeSlotDto = new TimeSlotDto(timeSlot.getDate(),timeSlot.getStartTime(),timeSlot.getEndTime(), timeSlot.getId());
        return timeSlotDto;
    }

    public static BookableServiceDto convertToDto(BookableService service) {
        if (service == null) {
            throw new IllegalArgumentException("There is no such Service!");
        }
        //service, customer, timeslot bill
        BookableServiceDto serviceDto = new BookableServiceDto(service.getName(), service.getCost(),service.getDuration());
        serviceDto.setId(service.getId());
        return serviceDto;
    }

    public static List<BookableServiceDto> convertToDto(List<BookableService> services) {
        List<BookableServiceDto> serviceDtos = new ArrayList<>();
        if (services == null) {
            throw new IllegalArgumentException("There is no such list of ervices!");
        }
        for (BookableService s:services){
            serviceDtos.add(convertToDto(s));
        }
        return serviceDtos;
    }

    public static  CustomerDto convertToDto(Customer customer){
        if (customer == null){
            throw new IllegalArgumentException("There is no such Customer!");
        }
        CustomerDto customerDto = new CustomerDto(customer.getEmail(), customer.getUsername(),
                customer.getPassword(),customer.getCardNumber(),
                customer.getCvv(), customer.getExpiry());
        customerDto.setId(customer.getId());
        return customerDto;
    }


    public static BillDto convertToDto(Bill bill){
        if (bill == null){
            throw new IllegalArgumentException("There is no such Bill!");
        }
        BillDto billDto = new BillDto(bill.getDate(), bill.getTotalCost(), bill.getId());
        return billDto;
    }

    public static  BusinessDto convertToDto(Business business){
        if (business == null){
            throw new IllegalArgumentException("There is no such Bill!");
        }
        BusinessDto businessDto = new BusinessDto(business.getName(), business.getAddress(), business.getPhoneNumber(), business.getEmail(), business.getId());
        return businessDto;
    }

    public static  List<BillDto> convertBillToDto(List<Bill> bills){
        List<BillDto> billDtoList= new ArrayList<BillDto>();
        for (Bill b: bills){
            billDtoList.add(convertToDto(b));
        }
        return billDtoList;
    }

    public static  List<AppointmentDto> convertAppointmentsToDto(List<Appointment> appointments) {
        List<AppointmentDto> appointmentDtoList = new ArrayList<AppointmentDto>();
        for (Appointment app : appointments) {
            appointmentDtoList.add(convertToDto(app));
        }
        return appointmentDtoList;
    }

    public static float getTotalCostOfAppointment(Appointment appointment){
        float sum = 0L;
        for(BookableService bookableService : appointment.getServices()){
            sum += bookableService.getCost();
        }
        return sum;
    }

    public static TechnicianDto convertToDto(Technician technician){
        TechnicianDto technicianDto = new TechnicianDto();
        technicianDto.setEmail(technician.getEmail());
        technicianDto.setPassword(technician.getPassword());
        technicianDto.setUsername(technician.getUsername());
        technicianDto.setEmail(technician.getEmail());
        technicianDto.setId(technician.getId());
        return technicianDto;
    }

    public static AdministratorDto convertToDto(Administrator administrator){
        AdministratorDto administratorDto = new AdministratorDto();
        administratorDto.setEmail(administrator.getEmail());
        administratorDto.setPassword(administrator.getPassword());
        administratorDto.setUsername(administrator.getUsername());
        administratorDto.setEmail(administrator.getEmail());
        administratorDto.setId(administrator.getId());
        return administratorDto;
    }
    public static OwnerDto convertToDto(Owner owner){
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setEmail(owner.getEmail());
        ownerDto.setPassword(owner.getPassword());
        ownerDto.setUsername(owner.getUsername());
        ownerDto.setEmail(owner.getEmail());
        ownerDto.setId(owner.getId());
        return ownerDto;
    }


    public static Appointment convertToEntity(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        appointment.setServices(convertToListOfEntity(appointmentDto.getServices()));
        appointment.setId(appointmentDto.getId());
        appointment.setTimeslot(convertToEntity(appointmentDto.getTimeSlot()));
        appointment.setCustomer(convertToEntity(appointmentDto.getCustomer()));
        return appointment;
    }

    public static BookableService convertToEntity(BookableServiceDto bookableServiceDto){
        BookableService bookableService = new BookableService();
        bookableService.setName(bookableServiceDto.getName());
        bookableService.setCost(bookableServiceDto.getCost());
        bookableService.setName(bookableServiceDto.getName());
        bookableService.setDuration(bookableServiceDto.getDuration());
        return bookableService;
    }

    public static List<BookableService> convertToListOfEntity(List<BookableServiceDto> bookableServiceDtos){
        List<BookableService> bookableServices = new ArrayList<>();
        for(BookableServiceDto bookableServiceDto : bookableServiceDtos){
            bookableServices.add(convertToEntity(bookableServiceDto));
        }
        return bookableServices;
    }

    public static TimeSlot convertToEntity(TimeSlotDto timeSlotDto){
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setDate(timeSlotDto.getDate());
        timeSlot.setEndTime(timeSlotDto.getEndTime());
        timeSlot.setStartTime(timeSlotDto.getStartTime());
        timeSlot.setId(timeSlotDto.getId());
        return timeSlot;
    }

    public static Customer convertToEntity(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setNoShow(customerDto.getNoShow());
        customer.setCardNumber(customerDto.getCardNumber());
        customer.setEmail(customerDto.getEmail());
        customer.setEmail(customerDto.getEmail());
        customer.setCvv(customerDto.getCvv());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        return customer;
    }

}
