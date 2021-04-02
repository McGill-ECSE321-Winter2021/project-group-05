package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import java.util.*;

@Entity
public class Technician extends Person
{
  //Technician Associations
  private List<TimeSlot> timeSlots;
  private List<Appointment> appointments;
  
  @OneToMany
  public List<TimeSlot> getTimeSlots() {
    return timeSlots;
  }

  public void setTimeSlots(List<TimeSlot> timeSlots) {
    this.timeSlots = timeSlots;
  }

  @OneToMany
  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }
}