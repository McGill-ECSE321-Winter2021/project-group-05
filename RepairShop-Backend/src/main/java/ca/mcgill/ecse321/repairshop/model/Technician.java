/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/
package ca.mcgill.ecse321.repairshop.model;

import java.util.*;

// line 76 "model.ump"
// line 156 "model.ump"
public class Technician extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Technician Associations
  private List<TimeSlot> timeSlots;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Technician(String aEmail, String aUsername, String aPassword, String aId, RepairShop aRepairShop)
  {
    super(aEmail, aUsername, aPassword, aId, aRepairShop);
    timeSlots = new ArrayList<TimeSlot>();
  }

  public Technician(){super();}
  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public TimeSlot getTimeSlot(int index)
  {
    TimeSlot aTimeSlot = timeSlots.get(index);
    return aTimeSlot;
  }

  public List<TimeSlot> getTimeSlots()
  {
    List<TimeSlot> newTimeSlots = Collections.unmodifiableList(timeSlots);
    return newTimeSlots;
  }

  public int numberOfTimeSlots()
  {
    int number = timeSlots.size();
    return number;
  }

  public boolean hasTimeSlots()
  {
    boolean has = timeSlots.size() > 0;
    return has;
  }

  public int indexOfTimeSlot(TimeSlot aTimeSlot)
  {
    int index = timeSlots.indexOf(aTimeSlot);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTimeSlots()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasAdded = false;
    if (timeSlots.contains(aTimeSlot)) { return false; }
    timeSlots.add(aTimeSlot);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasRemoved = false;
    if (timeSlots.contains(aTimeSlot))
    {
      timeSlots.remove(aTimeSlot);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTimeSlotAt(TimeSlot aTimeSlot, int index)
  {
    boolean wasAdded = false;
    if(addTimeSlot(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTimeSlotAt(TimeSlot aTimeSlot, int index)
  {
    boolean wasAdded = false;
    if(timeSlots.contains(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    }
    else
    {
      wasAdded = addTimeSlotAt(aTimeSlot, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    timeSlots.clear();
    super.delete();
  }

}