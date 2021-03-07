package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import java.util.*;

@Entity
public class Technician extends Person
{
  //Technician Associations
  private List<TimeSlot> timeSlots;
  
  @ManyToMany
  public List<TimeSlot> getTimeSlots() {
    return timeSlots;
  }

  public void setTimeSlots(List<TimeSlot> timeSlots) {
    this.timeSlots = timeSlots;
  }
 
  //adds a single timeslot for a technician
  public void setTimeSlot(TimeSlot timeSlot) {
	 if(this.timeSlots == null) {
		 this.timeSlots = new ArrayList<TimeSlot>();
	 }
	 this.timeSlots.add(timeSlot);
  }
}