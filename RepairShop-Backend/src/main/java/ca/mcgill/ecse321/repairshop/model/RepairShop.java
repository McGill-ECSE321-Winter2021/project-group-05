package ca.mcgill.ecse321.repairshop.model;

import java.util.*;
import javax.persistence.*;

@Entity
public class RepairShop
{
  private Long id;

  //RepairShop Associations
  private List<TimeSlot> timeSlots;
  private Business business;
  private List<Person> persons;
  private List<Bill> bills;
  private List<BookableService> services;
  private List<Appointment> appointments;


  /**
   * return timeslots for repair shop
   *
   * @return list of timeslots
   */
  @OneToMany(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public List<TimeSlot> getTimeSlots() {
    return timeSlots;
  }

  /**
   * set timeslots for repair shop
   *
   * @param timeSlots list of timeslots
   */
  public void setTimeSlots(List<TimeSlot> timeSlots) {
    this.timeSlots = timeSlots;
  }

  /**
   * return business for repair shop
   *
   * @return business
   */
  @OneToOne(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public Business getBusiness() {
    return business;
  }

  /**
   * set business for repair shop
   *
   * @param business shop business
   */
  public void setBusiness(Business business) {
    this.business = business;
  }

  /**
   * return persons for repair shop
   *
   * @return list of users
   */
  @OneToMany(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public List<Person> getPersons() {
    return persons;
  }

  /**
   * set persons for repair shop
   *
   * @param persons list of users
   */
  public void setPersons(List<Person> persons) {
    this.persons = persons;
  }

  /**
   * return bills for repair shop
   *
   * @return list of bills
   */
  @OneToMany(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public List<Bill> getBills() {
    return bills;
  }

  /**
   * set bills for repair shop
   *
   * @param bills list of bills
   */
  public void setBills(List<Bill> bills) {
    this.bills = bills;
  }

  /**
   * return services for repair shop
   *
   * @return list of services
   */
  @OneToMany(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public List<BookableService> getServices() {
    return services;
  }

  /**
   * set services for repair shop
   *
   * @param services list of services
   */
  public void setServices(List<BookableService> services) {
    this.services = services;
  }

  /**
   * return appointments for repair shop
   *
   * @return list of appointments
   */
  @OneToMany(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public List<Appointment> getAppointments() {
    return appointments;
  }

  /**
   * set appointments for repair shop
   *
   * @param appointments list of appointments
   */
  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  /**
   * set id for repair shop
   *
   * @param ID shop id
   */
  public void setId(Long ID) {
    this.id = ID;
  }

  /**
   * return id for repair shop
   *
   * @return id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }
}