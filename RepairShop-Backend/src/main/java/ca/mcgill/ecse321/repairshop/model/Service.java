package ca.mcgill.ecse321.repairshop.model;

import java.util.*;
import javax.persistence.*;

@Entity
public class Service
{
  //Service Attributes
  private String name;
  private float cost;
  private int duration;
  private Long id;

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

  @OneToMany(mappedBy = "service",cascade = {CascadeType.ALL})
  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }
}