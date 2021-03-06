package ca.mcgill.ecse321.repairshop.dto;

public class AppointmentDto {
    private ServiceDto service;
    private CustomerDto customer;
    private TimeSlotDto timeslot;
    private BillDto bill;
    private Long id;

    public AppointmentDto(){}

    public AppointmentDto(ServiceDto service,CustomerDto customer,TimeSlotDto timeslot,BillDto bill, Long id){
        this.service = service;
        this.customer = customer;
        this.timeslot = timeslot;
        this.bill = bill;
        this.id = id;
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

    public Long getId(){
        return this.id;
    }
}
