/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.sql.Date;
import java.util.*;

// line 39 "model.ump"
// line 116 "model.ump"
public class Bill
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Bill Attributes
  private Date date;
  private float totalCost;

  //Bill Associations
  private RepairShop repairShop;
  private List<Customer> customers;
  private List<Appointment> appointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Bill(Date aDate, float aTotalCost, RepairShop aRepairShop)
  {
    date = aDate;
    totalCost = aTotalCost;
    boolean didAddRepairShop = setRepairShop(aRepairShop);
    if (!didAddRepairShop)
    {
      throw new RuntimeException("Unable to create bill due to repairShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    customers = new ArrayList<Customer>();
    appointments = new ArrayList<Appointment>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotalCost(float aTotalCost)
  {
    boolean wasSet = false;
    totalCost = aTotalCost;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public float getTotalCost()
  {
    return totalCost;
  }
  /* Code from template association_GetOne */
  public RepairShop getRepairShop()
  {
    return repairShop;
  }
  /* Code from template association_GetMany */
  public Customer getCustomer(int index)
  {
    Customer aCustomer = customers.get(index);
    return aCustomer;
  }

  public List<Customer> getCustomers()
  {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
  }

  public int numberOfCustomers()
  {
    int number = customers.size();
    return number;
  }

  public boolean hasCustomers()
  {
    boolean has = customers.size() > 0;
    return has;
  }

  public int indexOfCustomer(Customer aCustomer)
  {
    int index = customers.indexOf(aCustomer);
    return index;
  }
  /* Code from template association_GetMany */
  public Appointment getAppointment(int index)
  {
    Appointment aAppointment = appointments.get(index);
    return aAppointment;
  }

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
  /* Code from template association_SetOneToMany */
  public boolean setRepairShop(RepairShop aRepairShop)
  {
    boolean wasSet = false;
    if (aRepairShop == null)
    {
      return wasSet;
    }

    RepairShop existingRepairShop = repairShop;
    repairShop = aRepairShop;
    if (existingRepairShop != null && !existingRepairShop.equals(aRepairShop))
    {
      existingRepairShop.removeBill(this);
    }
    repairShop.addBill(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomers()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) { return false; }
    customers.add(aCustomer);
    if (aCustomer.indexOfBill(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCustomer.addBill(this);
      if (!wasAdded)
      {
        customers.remove(aCustomer);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeCustomer(Customer aCustomer)
  {
    boolean wasRemoved = false;
    if (!customers.contains(aCustomer))
    {
      return wasRemoved;
    }

    int oldIndex = customers.indexOf(aCustomer);
    customers.remove(oldIndex);
    if (aCustomer.indexOfBill(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCustomer.removeBill(this);
      if (!wasRemoved)
      {
        customers.add(oldIndex,aCustomer);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCustomerAt(Customer aCustomer, int index)
  {  
    boolean wasAdded = false;
    if(addCustomer(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerAt(Customer aCustomer, int index)
  {
    boolean wasAdded = false;
    if(customers.contains(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCustomerAt(aCustomer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(Service aService, Customer aCustomer, TimeSlot aTimeslot, RepairShop aRepairShop)
  {
    return new Appointment(aService, aCustomer, aTimeslot, this, aRepairShop);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    Bill existingBill = aAppointment.getBill();
    boolean isNewBill = existingBill != null && !this.equals(existingBill);
    if (isNewBill)
    {
      aAppointment.setBill(this);
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
    //Unable to remove aAppointment, as it must always have a bill
    if (!this.equals(aAppointment.getBill()))
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
    RepairShop placeholderRepairShop = repairShop;
    this.repairShop = null;
    if(placeholderRepairShop != null)
    {
      placeholderRepairShop.removeBill(this);
    }
    ArrayList<Customer> copyOfCustomers = new ArrayList<Customer>(customers);
    customers.clear();
    for(Customer aCustomer : copyOfCustomers)
    {
      aCustomer.removeBill(this);
    }
    for(int i=appointments.size(); i > 0; i--)
    {
      Appointment aAppointment = appointments.get(i - 1);
      aAppointment.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "totalCost" + ":" + getTotalCost()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "repairShop = "+(getRepairShop()!=null?Integer.toHexString(System.identityHashCode(getRepairShop())):"null");
  }
}