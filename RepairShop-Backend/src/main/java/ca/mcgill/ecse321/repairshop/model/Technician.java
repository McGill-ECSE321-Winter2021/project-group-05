/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/
package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.*;

@Entity
public class Technician extends Person
{

  //Technician Associations
  private List<TimeSlot> timeSlots;
  /*
  private Long ID;
  public void setId(Long id) {
    this.ID = id;
  }
  @Id
  @GeneratedValue
  public Long getId() {
    return ID;
  }

   */


  @ManyToMany
  public List<TimeSlot> getTimeSlots() {
    return timeSlots;
  }

  public void setTimeSlots(List<TimeSlot> timeSlots) {
    this.timeSlots = timeSlots;
  }
}