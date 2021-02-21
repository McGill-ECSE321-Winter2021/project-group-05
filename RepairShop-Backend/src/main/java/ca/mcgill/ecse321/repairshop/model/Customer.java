/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/
package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
public class Customer extends Person
{


  //Customer Attributes
  private String cardNumber;
  private String cvv;
  private Date expiry;

  //Customer Associations
  private List<Bill> bills;
  private List<Appointment> appointments;
  /*
  private Long ID;
  public void setId(Long id) {
    this.ID = id;
  }
  @Id
  @GeneratedValue
  public Long getId() {
    return ID;
  }

   */


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

  @OneToMany(mappedBy = "customer",cascade = {CascadeType.ALL})
  public List<Bill> getBills() {
    return bills;
  }

  public void setBills(List<Bill> bills) {
    this.bills = bills;
  }

  @OneToMany(mappedBy = "customer",cascade = {CascadeType.ALL})
  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }
}