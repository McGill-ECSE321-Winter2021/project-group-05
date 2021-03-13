package ca.mcgill.ecse321.repairshop.dto;

import java.util.List;

public class AppointmentDto {
    private List<BookableServiceDto> services;
    private CustomerDto customer;
    private TimeSlotDto timeslot;
    private BillDto bill;
    private Long id;

    public AppointmentDto(){}

    public AppointmentDto(List<BookableServiceDto> services,CustomerDto customer,TimeSlotDto timeslot,BillDto bill, Long id){
        this.services = services;
        this.customer = customer;
        this.timeslot = timeslot;
        this.bill = bill;
        this.id = id;
    }

    public List<BookableServiceDto> getServices(){
        return this.services;
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
