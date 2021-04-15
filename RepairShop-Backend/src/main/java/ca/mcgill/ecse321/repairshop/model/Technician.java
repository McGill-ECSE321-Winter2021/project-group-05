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

  /**
   * return timeslots for technicians
   *
   * @return list of timeslots
   */
  @OneToMany
  public List<TimeSlot> getTimeSlots() {
    return timeSlots;
  }

  /**
   * set timeslots for technicians
   *
   * @param timeSlots list of timeslots
   */
  public void setTimeSlots(List<TimeSlot> timeSlots) {
    this.timeSlots = timeSlots;
  }

  /**
   * return appointments for technicians
   *
   * @return list of appointments
   */
  @OneToMany
  public List<Appointment> getAppointments() {
    return appointments;
  }

  /**
   * set appointments for technicians
   *
   * @param appointments list of appointments
   */
  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }
}