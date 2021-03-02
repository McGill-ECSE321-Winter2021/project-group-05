package ca.mcgill.ecse321.repairshop.dto;



import java.sql.Date;
import java.util.List;

public class BillDto {
    private Date date;
    private float totalCost;

    //Bill Associations
    private CustomerDto customer;
    private List<AppointmentDto> appointments;

    public BillDto(){}

    public BillDto(Date date,float totalCost,CustomerDto customer,List<AppointmentDto> appointments){
        this.date=date;
        this.totalCost=totalCost;
        this.customer=customer;
        this.appointments=appointments;
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

    public List<AppointmentDto> getAppointments() {
        return this.appointments;
    }
}
