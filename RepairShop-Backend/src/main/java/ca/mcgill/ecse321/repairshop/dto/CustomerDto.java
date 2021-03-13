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
    private Long id;
    private int noShow;

    public CustomerDto(){}

    @SuppressWarnings("unchecked")
    public CustomerDto(String email,String username, String password, Long id){
        this(email,username,password, id, null,null,null);
    }

    public CustomerDto(String email, String username,
                       String password, Long id,
                       String cardNumber, String cvv, Date expiry){
        this.email=email;
        this.username=username;
        this.password=password;
        this.cardNumber=cardNumber;
        this.cvv=cvv;
        this.expiry=expiry;
        this.id = id;
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

    public Long getId(){
        return this.id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNoShow() {
        return noShow;
    }

    public void setNoShow(int noShow) {
        this.noShow = noShow;
    }

}
