/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/
package ca.mcgill.ecse321.repairshop.model;

<<<<<<< HEAD
import javax.persistence.Entity;
=======
import javax.persistence.Id;
import javax.persistence.OneToMany;
>>>>>>> main
import java.sql.Date;
import java.util.*;

// line 50 "model.ump"
// line 136 "model.ump"
@Entity
public class Customer extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String cardNumber;
  private String cvv;
  private Date expiry;

  //Customer Associations
  private List<Bill> bills;
  private List<Appointment> appointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aEmail, String aUsername, String aPassword, String aId, RepairShop aRepairShop)
  {
    super(aEmail, aUsername, aPassword, aId, aRepairShop);
    cardNumber = null;
    cvv = null;
    expiry = null;
    bills = new ArrayList<Bill>();
    appointments = new ArrayList<Appointment>();
  }

  // empty customer for testing
  public Customer(){super();}


  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCardNumber(String aCardNumber)
  {
    boolean wasSet = false;
    cardNumber = aCardNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setCvv(String aCvv)
  {
    boolean wasSet = false;
    cvv = aCvv;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpiry(Date aExpiry)
  {
    boolean wasSet = false;
    expiry = aExpiry;
    wasSet = true;
    return wasSet;
  }

  public String getCardNumber()
  {
    return cardNumber;
  }

  public String getCvv()
  {
    return cvv;
  }

  public Date getExpiry()
  {
    return expiry;
  }
  /* Code from template association_GetMany */
  public Bill getBill(int index)
  {
    Bill aBill = bills.get(index);
    return aBill;
  }
  @OneToMany
  public List<Bill> getBills()
  {
    List<Bill> newBills = Collections.unmodifiableList(bills);
    return newBills;
  }

  public int numberOfBills()
  {
    int number = bills.size();
    return number;
  }

  public boolean hasBills()
  {
    boolean has = bills.size() > 0;
    return has;
  }

  public int indexOfBill(Bill aBill)
  {
    int index = bills.indexOf(aBill);
    return index;
  }
  /* Code from template association_GetMany */
  public Appointment getAppointment(int index)
  {
    Appointment aAppointment = appointments.get(index);
    return aAppointment;
  }
  @OneToMany
  public List<Appointment> getAppointments()
  {
    List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
    return newAppointments;
  }

  public int numberOfAppointments()
  {
    int number = appointments.size();
    return number;
  }

  public boolean hasAppointments()
  {
    boolean has = appointments.size() > 0;
    return has;
  }

  public int indexOfAppointment(Appointment aAppointment)
  {
    int index = appointments.indexOf(aAppointment);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBills()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Bill addBill(Date aDate, float aTotalCost, String aId, RepairShop aRepairShop)
  {
    return new Bill(aDate, aTotalCost, aId, aRepairShop, this);
  }

  public boolean addBill(Bill aBill)
  {
    boolean wasAdded = false;
    if (bills.contains(aBill)) { return false; }
    Customer existingCustomer = aBill.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aBill.setCustomer(this);
    }
    else
    {
      bills.add(aBill);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBill(Bill aBill)
  {
    boolean wasRemoved = false;
    //Unable to remove aBill, as it must always have a customer
    if (!this.equals(aBill.getCustomer()))
    {
      bills.remove(aBill);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBillAt(Bill aBill, int index)
  {
    boolean wasAdded = false;
    if(addBill(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBills()) { index = numberOfBills() - 1; }
      bills.remove(aBill);
      bills.add(index, aBill);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBillAt(Bill aBill, int index)
  {
    boolean wasAdded = false;
    if(bills.contains(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBills()) { index = numberOfBills() - 1; }
      bills.remove(aBill);
      bills.add(index, aBill);
      wasAdded = true;
    }
    else
    {
      wasAdded = addBillAt(aBill, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(String aId, Service aService, TimeSlot aTimeslot, Bill aBill, RepairShop aRepairShop)
  {
    return new Appointment(aId, aService, this, aTimeslot, aBill, aRepairShop);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    Customer existingCustomer = aAppointment.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aAppointment.setCustomer(this);
    }
    else
    {
      appointments.add(aAppointment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    //Unable to remove aAppointment, as it must always have a customer
    if (!this.equals(aAppointment.getCustomer()))
    {
      appointments.remove(aAppointment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAppointmentAt(Appointment aAppointment, int index)
  {
    boolean wasAdded = false;
    if(addAppointment(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index)
  {
    boolean wasAdded = false;
    if(appointments.contains(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    }
    else
    {
      wasAdded = addAppointmentAt(aAppointment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=bills.size(); i > 0; i--)
    {
      Bill aBill = bills.get(i - 1);
      aBill.delete();
    }
    for(int i=appointments.size(); i > 0; i--)
    {
      Appointment aAppointment = appointments.get(i - 1);
      aAppointment.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "cardNumber" + ":" + getCardNumber()+ "," +
            "cvv" + ":" + getCvv()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "expiry" + "=" + (getExpiry() != null ? !getExpiry().equals(this)  ? getExpiry().toString().replaceAll("  ","    ") : "this" : "null");
  }
}