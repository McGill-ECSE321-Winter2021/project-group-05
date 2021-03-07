package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Appointment
{
  private List<BookableService> services;
  private Customer customer;
  private TimeSlot timeslot;
  private Bill bill;
  private RepairShop repairShop;
  private Long id;

  @OneToMany
  public List<BookableService> getServices() {
    return services;
  }

  public void setServices(List<BookableService> services) {
    this.services = services;
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

  @ManyToOne(cascade = {CascadeType.ALL})
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