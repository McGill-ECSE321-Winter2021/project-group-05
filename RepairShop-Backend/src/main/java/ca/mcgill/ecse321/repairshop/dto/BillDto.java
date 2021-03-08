package ca.mcgill.ecse321.repairshop.dto;

import java.sql.Date;
import java.util.List;

public class BillDto {
    private Date date;
    private float totalCost;
    private Long id;

    //Bill Associations
    private CustomerDto customer;
    private AppointmentDto appointment;

    public BillDto(){}

    public BillDto(Date date,float totalCost,CustomerDto customer,AppointmentDto appointment,Long id){
        this.date=date;
        this.totalCost=totalCost;
        this.customer=customer;
        this.appointment=appointment;
        this.id=id;
    }

    public Date getDate() {
        return this.date;
    }

    public float getTotalCost() {
        return this.totalCost;
    }

    public CustomerDto getCustomer() {
        return this.customer;
    }

    public AppointmentDto getAppointment() {
        return this.appointment;
    }

    public Long getId() {
        return id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public void setAppointment(AppointmentDto appointment) {
        this.appointment = appointment;
    }
}
