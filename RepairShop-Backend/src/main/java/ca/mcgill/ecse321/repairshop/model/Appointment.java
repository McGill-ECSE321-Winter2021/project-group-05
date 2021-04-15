package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
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

  /**
   * return services for appointment
   *
   * @return list of services
   */
  @ManyToMany
  public List<BookableService> getServices() {
    return services;
  }

  /**
   * sets services for appointment
   *
   * @param services list of services
   */
  public void setServices(List<BookableService> services) {
    this.services = services;
  }

  /**
   * returns a customer for appointment
   *
   * @return customer
   */
  @ManyToOne
  public Customer getCustomer() {
    return customer;
  }

  /**
   * sets a customer for appointment
   *
   * @param customer Customer
   */
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /**
   * returns a timeslot for appointment
   *
   * @return timeslot
   */
  @OneToOne
  public TimeSlot getTimeslot() {
    return timeslot;
  }

  /**
   * set timeslot for appointment
   *
   * @param timeslot Timeslot
   */
  public void setTimeslot(TimeSlot timeslot) {
    this.timeslot = timeslot;
  }

  /**
   * returns bill for appointment
   *
   * @return bill
   */
  @OneToOne
  public Bill getBill() {
    return bill;
  }

  /**
   * sets bill for appointment
   *
   * @param bill Bill
   */
  public void setBill(Bill bill) {
    this.bill = bill;
  }

  /**
   * returns shop for appointment
   *
   * @return Repair Shop
   */
  @ManyToOne(cascade = {CascadeType.ALL})
  public RepairShop getRepairShop() {
    return repairShop;
  }

  /**
   * set shop for appointment
   *
   * @param repairShop repair shop
   */
  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }

  /**
   * set id for appointment
   *
   * @param id appointment id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * get id for appointment
   *
   * @return id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }
}