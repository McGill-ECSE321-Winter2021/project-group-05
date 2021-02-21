/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/
package ca.mcgill.ecse321.repairshop.model;

import java.util.*;
import javax.persistence.*;

@Entity
public class Service
{
  private Long id;

  //Service Attributes
  private String name;
  private float cost;
  private int duration;


  //Service Associations
  private RepairShop repairShop;
  private List<Appointment> appointments;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getCost() {
    return cost;
  }

  public void setCost(float cost) {
    this.cost = cost;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public void setId(long id) {
    this.id = id;
  }

  @ManyToOne
  public RepairShop getRepairShop() {
    return repairShop;
  }

  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }

  @OneToMany
  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }


  @Id
  @GeneratedValue
  public Long getId() {
    return id;
  }
}