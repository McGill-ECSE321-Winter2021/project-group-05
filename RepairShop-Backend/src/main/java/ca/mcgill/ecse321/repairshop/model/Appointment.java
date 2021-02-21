/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/
package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.*;
@Entity
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
  //Appointment Associations
  private Service service;
  private Customer customer;
  private TimeSlot timeslot;
  private Bill bill;
  private RepairShop repairShop;
  private Long ID;

  @ManyToOne
  public Service getService() {
    return service;
  }

  public void setService(Service service) {
    this.service = service;
  }

  @ManyToOne
  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @OneToOne
  public TimeSlot getTimeslot() {
    return timeslot;
  }

  public void setTimeslot(TimeSlot timeslot) {
    this.timeslot = timeslot;
  }

  @ManyToOne
  public Bill getBill() {
    return bill;
  }

  public void setBill(Bill bill) {
    this.bill = bill;
  }

  @ManyToOne
  public RepairShop getRepairShop() {
    return repairShop;
  }

  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }

  public void setId(Long id) {
    this.ID = id;
  }

  @Id
  @GeneratedValue
  public Long getId() {
    return ID;
  }
}