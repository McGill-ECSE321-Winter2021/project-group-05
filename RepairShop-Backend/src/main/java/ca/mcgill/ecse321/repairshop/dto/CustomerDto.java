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
    private int noShow;

    public CustomerDto(){}

    @SuppressWarnings("unchecked")
    public CustomerDto(String email,String username, String password){
        this(email,username,password, null,null,null);
    }

    public CustomerDto(String email, String username,
                       String password,
                       String cardNumber, String cvv, Date expiry){
        this.email=email;
        this.username=username;
        this.password=password;
        this.cardNumber=cardNumber;
        this.cvv=cvv;
        this.expiry=expiry;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public int getNoShow() {
        return noShow;
    }

    public void setNoShow(int noShow) {
        this.noShow = noShow;
    }
}
