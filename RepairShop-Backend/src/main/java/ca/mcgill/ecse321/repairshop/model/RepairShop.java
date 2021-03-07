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


  @OneToMany(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public List<TimeSlot> getTimeSlots() {
    return timeSlots;
  }

  public void setTimeSlots(List<TimeSlot> timeSlots) {
    this.timeSlots = timeSlots;
  }

  @OneToOne(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public Business getBusiness() {
    return business;
  }

  public void setBusiness(Business business) {
    this.business = business;
  }

  @OneToMany(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public List<Person> getPersons() {
    return persons;
  }

  public void setPersons(List<Person> persons) {
    this.persons = persons;
  }
  @OneToMany(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public List<Bill> getBills() {
    return bills;
  }

  public void setBills(List<Bill> bills) {
    this.bills = bills;
  }
  @OneToMany(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public List<BookableService> getServices() {
    return services;
  }

  public void setServices(List<BookableService> services) {
    this.services = services;
  }

  @OneToMany(mappedBy = "repairShop",cascade = {CascadeType.ALL})
  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  public void setId(Long ID) {
    this.id = ID;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }
}