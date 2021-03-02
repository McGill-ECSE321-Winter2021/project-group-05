package ca.mcgill.ecse321.repairshop.controller;

import ca.mcgill.ecse321.repairshop.dto.*;
import ca.mcgill.ecse321.repairshop.model.*;
import ca.mcgill.ecse321.repairshop.service.RepairShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class RepairShopRestController {
    @Autowired
    private RepairShopService service;

    @GetMapping(value = { "/appointments", "/appointments/" })
    public List<AppointmentDto> getAllAppointments() {
        return service.getAllAppointment().stream().map(a -> convertToDto(a)).collect(Collectors.toList());
    }

    // TODO: change the path variable
    @PostMapping(value = { "/appointments/{name}", "/appointments/{name}/" })
    public AppointmentDto createAppointment(@PathVariable("name") String name) throws IllegalArgumentException {
       // TODO: question: how should get customer, bills...?
        return null;
    }


    private AppointmentDto convertToDto(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("There is no such Appointment!");
        }
        //service, customer, timeslot, bill
        AppointmentDto appointmentDto = new AppointmentDto(convertToDto(appointment.getService()), convertToDto(appointment.getCustomer()),convertToDto(appointment.getTimeslot()),convertToDto(appointment.getBill()));
        return appointmentDto;
    }

    private TimeSlotDto convertToDto(TimeSlot timeSlot){
        if (timeSlot == null) {
            throw new IllegalArgumentException("There is no such TimeSlot!");
        }
        //service, customer, timeslot bill
        TimeSlotDto timeSlotDto = new TimeSlotDto(timeSlot.getDate(),timeSlot.getStartTime(),timeSlot.getEndTime());
        return timeSlotDto;
    }

    private ServiceDto convertToDto(Service service) {
        if (service == null) {
            throw new IllegalArgumentException("There is no such Service!");
        }
        //service, customer, timeslot bill
        ServiceDto serviceDto = new ServiceDto(service.getName(), service.getCost(),service.getDuration());
        return serviceDto;
    }

    private CustomerDto convertToDto(Customer customer){
        if (customer == null){
            throw new IllegalArgumentException("There is no such Customer!");
        }
        CustomerDto customerDto = new CustomerDto(customer.getEmail(), customer.getUsername(), customer.getPassword(),customer.getCardNumber(), customer.getCvv(), customer.getExpiry(),convertBillToDto(customer.getBills()),convertAppointmentToDto(customer.getAppointments()));
        return customerDto;
    }

    private BillDto convertToDto(Bill bill){
        if (bill == null){
            throw new IllegalArgumentException("There is no such Bill!");
        }
        BillDto billDto = new BillDto(bill.getDate(), bill.getTotalCost(), convertToDto(bill.getCustomer()),convertAppointmentToDto(bill.getAppointments()));
        return billDto;
    }

    private List<BillDto> convertBillToDto(List<Bill> bills){
        List<BillDto> billDtoList= new ArrayList<BillDto>();
        for (Bill b: bills){
            billDtoList.add(convertToDto(b));
        }
        return billDtoList;
    }

    private List<AppointmentDto> convertAppointmentToDto(List<Appointment> appointments){
        List<AppointmentDto> appointmentDtoList= new ArrayList<AppointmentDto>();
        for (Appointment app: appointments){
            appointmentDtoList.add(convertToDto(app));
        }
        return appointmentDtoList;
    }


}
