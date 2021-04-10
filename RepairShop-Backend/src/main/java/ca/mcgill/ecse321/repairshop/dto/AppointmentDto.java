package ca.mcgill.ecse321.repairshop.dto;

import java.util.List;

public class AppointmentDto {
    private List<BookableServiceDto> services;
    private CustomerDto customer;
    private TimeSlotDto timeslot;
    private BillDto bill;
    private Long id;

    public AppointmentDto(){}

    /**
     * creates an appointment transfer object
     *
     * @param services appointment service(s)
     * @param customer customer reserving appointment
     * @param timeslot appointment timeslot
     * @param bill appointment bill
     * @param id appointment id
     */
    public AppointmentDto(List<BookableServiceDto> services,CustomerDto customer,TimeSlotDto timeslot,BillDto bill, Long id){
        this.services = services;
        this.customer = customer;
        this.timeslot = timeslot;
        this.bill = bill;
        this.id = id;
    }

    /**
     * returns all services of an appointment
     *
     * @return list of services
     */
    public List<BookableServiceDto> getServices(){
        return this.services;
    }

    /**
     * returns customer of appointment
     *
     * @return customer transfer object
     */
    public CustomerDto getCustomer(){
        return this.customer;
    }

    /**
     * returns timeslot of an appointment
     *
     * @return timeslot transfer object
     */
    public TimeSlotDto getTimeSlot(){
        return this.timeslot;
    }

    /**
     * returns bill of an appointment
     *
     * @return bill transfer object
     */
    public BillDto BillDto(){
        return this.bill;
    }

    /**
     * returns id of appointment
     *
     * @return appointment id
     */
    public Long getId(){
        return this.id;
    }
}
