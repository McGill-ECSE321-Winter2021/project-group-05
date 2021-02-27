package ca.mcgill.ecse321.repairshop.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "Business")
public class Business
{
  //Business Attributes
  private String name;
  private String address;
  private String phoneNumber;
  private String email;

  //Business Associations
  private List<TimeSlot> timeslot;
  private RepairShop repairShop;
  private Long id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  @OneToMany(cascade={CascadeType.ALL})
  public TimeSlot getTimeslot(int index)
  {
    TimeSlot aTimeslot = timeslot.get(index);
    return aTimeslot;
  }
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @OneToMany()
  public List<TimeSlot> getTimeslot() {
    return timeslot;
  }

  public void setTimeslot(List<TimeSlot> timeslot) {
    this.timeslot = timeslot;
  }

  @OneToOne(cascade = {CascadeType.ALL})
  public RepairShop getRepairShop() {
    return repairShop;
  }

  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

}