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

/*  TODO: to fix
   private AppointmentDto convertToDto(Appointment appointment) {
       if (appointment == null) {
           throw new IllegalArgumentException("There is no such Appointment!");
       }
       //service, customer, timeslot, bill
       AppointmentDto appointmentDto = new AppointmentDto(convertToDto(appointment.getServices()), convertToDto(appointment.getCustomer()),convertToDto(appointment.getTimeslot()),convertToDto(appointment.getBill()), appointment.getId());
       return appointmentDto;
   }
*/
    public static TimeSlotDto convertToDto(TimeSlot timeSlot){
        if (timeSlot == null) {
            throw new IllegalArgumentException("There is no such TimeSlot!");
        }
        //service, customer, timeslot bill
        TimeSlotDto timeSlotDto = new TimeSlotDto(timeSlot.getDate(),timeSlot.getStartTime(),timeSlot.getEndTime(), timeSlot.getId());
        return timeSlotDto;
    }

    public static ServiceDto convertToDto(BookableService service) {
        if (service == null) {
            throw new IllegalArgumentException("There is no such Service!");
        }
        //service, customer, timeslot bill
        ServiceDto serviceDto = new ServiceDto(service.getName(), service.getCost(),service.getDuration(), service.getId());
        return serviceDto;
    }
 /*
    private CustomerDto convertToDto(Customer customer){
        if (customer == null){
            throw new IllegalArgumentException("There is no such Customer!");
        }
        CustomerDto customerDto = new CustomerDto(customer.getEmail(), customer.getUsername(), customer.getPassword(),customer.getId(),customer.getCardNumber(), customer.getCvv(), customer.getExpiry(),convertBillToDto(customer.getBills()),convertAppointmentToDto(customer.getAppointments()));
        return customerDto;
    }
TODO: fix
    private BillDto convertToDto(Bill bill){
        if (bill == null){
            throw new IllegalArgumentException("There is no such Bill!");
        }
        BillDto billDto = new BillDto(bill.getDate(), bill.getTotalCost(), convertToDto(bill.getCustomer()),convertAppointmentToDto(bill.getAppointments()), bill.getId());
        return billDto;
    }


TODO: fix
    private List<BillDto> convertBillToDto(List<Bill> bills){
        List<BillDto> billDtoList= new ArrayList<BillDto>();
        for (Bill b: bills){
            billDtoList.add(convertToDto(b));
        }
        return billDtoList;
    }
TODO: fix
    private List<AppointmentDto> convertAppointmentToDto(List<Appointment> appointments) {
        List<AppointmentDto> appointmentDtoList = new ArrayList<AppointmentDto>();
        for (Appointment app : appointments) {
            appointmentDtoList.add(convertToDto(app));
        }
        return appointmentDtoList;
    }
*/

}
