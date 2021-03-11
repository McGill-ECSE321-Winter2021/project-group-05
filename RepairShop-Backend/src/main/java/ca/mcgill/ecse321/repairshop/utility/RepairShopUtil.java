package ca.mcgill.ecse321.repairshop.utility;

import ca.mcgill.ecse321.repairshop.dto.*;
import ca.mcgill.ecse321.repairshop.model.*;

import java.util.ArrayList;
import java.util.List;

public class RepairShopUtil {
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
       AppointmentDto appointmentDto = new AppointmentDto(convertToDto(appointment.getServices()), convertToDto(appointment.getCustomer()),convertToDto(appointment.getTimeslot()),convertToDto(appointment.getBill()), appointment.getId());
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
        BookableServiceDto serviceDto = new BookableServiceDto(service.getName(), service.getCost(),service.getDuration(), service.getId());
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
                customer.getPassword(),customer.getId(),customer.getCardNumber(),
                customer.getCvv(), customer.getExpiry(),convertAppointmentsToDto(customer.getAppointments()));
        return customerDto;
    }


    public static  BillDto convertToDto(Bill bill){
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
}
