package ca.mcgill.ecse321.repairshop.dto;


import ca.mcgill.ecse321.repairshop.model.Appointment;

public class AppointmentDto {
    private ServiceDto service;
    private CustomerDto customer;
    private TimeSlotDto timeslot;
    private BillDto bill;

    public AppointmentDto(){}

    public AppointmentDto(ServiceDto service,CustomerDto customer,TimeSlotDto timeslot,BillDto bill){
        this.service = service;
        this.customer = customer;
        this.timeslot = timeslot;
        this.bill = bill;
    }

    public ServiceDto getService(){
        return this.service;
    }
    public CustomerDto getCustomer(){
        return this.customer;
    }
    public TimeSlotDto getTimeSlot(){
        return this.timeslot;
    }
    public BillDto BillDto(){
        return this.bill;
    }




}
