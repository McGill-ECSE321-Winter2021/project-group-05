package ca.mcgill.ecse321.repairshop.dto;



import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class CustomerDto {
    private String email;
    private String username;
    private String password;

    private String cardNumber;
    private String cvv;
    private Date expiry;

    //Customer Associations
    private List<BillDto> bills;
    private List<AppointmentDto> appointments;

    public CustomerDto(){}

    @SuppressWarnings("unchecked")
    public CustomerDto(String email,String username, String password){
        this(email,username,password, null,null,null,Collections.EMPTY_LIST,Collections.EMPTY_LIST);
    }

    public CustomerDto(String email,String username, String password, String cardNumber, String cvv, Date expiry, List<BillDto> bills,List<AppointmentDto> appointments){
        this.email=email;
        this.username=username;
        this.password=password;
        this.cardNumber=cardNumber;
        this.cvv=cvv;
        this.expiry=expiry;
        this.bills=bills;
        this.appointments=appointments;
    }

    public String getEmail(){
        return this.email;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.getPassword();
    }

    public String getCardNumber(){
        return this.cardNumber;
    }
    public void setCardNumber(String cardNumber){
        this.cardNumber=cardNumber;
    }

    public String getCvv(){
        return this.cvv;
    }
    public void setCvv(String cvv){
        this.cvv=cvv;
    }
    public Date getExpiry(){
        return this.expiry;
    }
    public void setExpiry(Date expiry){
        this.expiry=expiry;
    }
    public List<BillDto> getBills(){
        return this.bills;
    }
    public void setBills(List<BillDto> bills){
        this.bills=bills;
    }
    public List<AppointmentDto> getAppointments(){
        return this.appointments;
    }

    public void setAppointments(List<AppointmentDto> appointments) {
        this.appointments = appointments;
    }
}
