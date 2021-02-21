package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.*;

@Entity
public class Appointment
{
  private Service service;
  private Customer customer;
  private TimeSlot timeslot;
  private Bill bill;
  private RepairShop repairShop;
  private Long id;

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