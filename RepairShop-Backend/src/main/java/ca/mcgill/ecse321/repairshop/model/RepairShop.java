/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/
package ca.mcgill.ecse321.repairshop.model;

import java.util.*;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;


/**
 * ECSE 321 Domain model draft 2
 */
@Entity
public class RepairShop
{


  //RepairShop Associations
  private List<TimeSlot> timeSlots;
  private Business business;
  private List<Person> persons;
  private List<Bill> bills;
  private List<Service> services;
  private List<Appointment> appointments;
  private Long ID;


  @OneToMany(cascade = {CascadeType.ALL})
  public List<TimeSlot> getTimeSlots() {
    return timeSlots;
  }

  public void setTimeSlots(List<TimeSlot> timeSlots) {
    this.timeSlots = timeSlots;
  }
  @OneToOne(cascade = {CascadeType.ALL})
  public Business getBusiness() {
    return business;
  }

  public void setBusiness(Business business) {
    this.business = business;
  }

  @OneToMany(cascade = {CascadeType.ALL})
  public List<Person> getPersons() {
    return persons;
  }

  public void setPersons(List<Person> persons) {
    this.persons = persons;
  }
  @OneToMany(cascade = {CascadeType.ALL})
  public List<Bill> getBills() {
    return bills;
  }

  public void setBills(List<Bill> bills) {
    this.bills = bills;
  }
  @OneToMany(cascade = {CascadeType.ALL})
  public List<Service> getServices() {
    return services;
  }

  public void setServices(List<Service> services) {
    this.services = services;
  }

  @OneToMany(cascade = {CascadeType.ALL})
  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }



  public void setId(Long ID) {
    this.ID = ID;
  }

  @Id
  @GeneratedValue
  public Long getId() {
    return ID;
  }
}