/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/
package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;


// line 66 "model.ump"
// line 147 "model.ump"
@Entity
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment Attributes
  private String id;

  //Appointment Associations
  private Service service;
  private Customer customer;
  private TimeSlot timeslot;
  private Bill bill;
  private RepairShop repairShop;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Appointment(){}
  public Appointment(String aId, Service aService, Customer aCustomer, TimeSlot aTimeslot, Bill aBill, RepairShop aRepairShop)
  {
    id = aId;
    boolean didAddService = setService(aService);
    if (!didAddService)
    {
      throw new RuntimeException("Unable to create appointment due to service. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create appointment due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setTimeslot(aTimeslot))
    {
      throw new RuntimeException("Unable to create Appointment due to aTimeslot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBill = setBill(aBill);
    if (!didAddBill)
    {
      throw new RuntimeException("Unable to create appointment due to bill. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddRepairShop = setRepairShop(aRepairShop);
    if (!didAddRepairShop)
    {
      throw new RuntimeException("Unable to create appointment due to repairShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }
  
  @Id
  public String getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  
  @OneToOne
  public Service getService()
  {
    return service;
  }
  /* Code from template association_GetOne */
  @ManyToOne
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  @ManyToOne
  public TimeSlot getTimeslot()
  {
    return timeslot;
  }
  /* Code from template association_GetOne */
  @ManyToOne
  public Bill getBill()
  {
    return bill;
  }
  /* Code from template association_GetOne */
  public RepairShop getRepairShop()
  {
    return repairShop;
  }
  /* Code from template association_SetOneToMany */
  public boolean setService(Service aService)
  {
    boolean wasSet = false;
    if (aService == null)
    {
      return wasSet;
    }

    Service existingService = service;
    service = aService;
    if (existingService != null && !existingService.equals(aService))
    {
      existingService.removeAppointment(this);
    }
    service.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeAppointment(this);
    }
    customer.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTimeslot(TimeSlot aNewTimeslot)
  {
    boolean wasSet = false;
    if (aNewTimeslot != null)
    {
      timeslot = aNewTimeslot;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBill(Bill aBill)
  {
    boolean wasSet = false;
    if (aBill == null)
    {
      return wasSet;
    }

    Bill existingBill = bill;
    bill = aBill;
    if (existingBill != null && !existingBill.equals(aBill))
    {
      existingBill.removeAppointment(this);
    }
    bill.addAppointment(this);
    wasSet = true;
    return wasSet;
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
      existingRepairShop.removeAppointment(this);
    }
    repairShop.addAppointment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Service placeholderService = service;
    this.service = null;
    if(placeholderService != null)
    {
      placeholderService.removeAppointment(this);
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeAppointment(this);
    }
    timeslot = null;
    Bill placeholderBill = bill;
    this.bill = null;
    if(placeholderBill != null)
    {
      placeholderBill.removeAppointment(this);
    }
    RepairShop placeholderRepairShop = repairShop;
    this.repairShop = null;
    if(placeholderRepairShop != null)
    {
      placeholderRepairShop.removeAppointment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "service = "+(getService()!=null?Integer.toHexString(System.identityHashCode(getService())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeslot = "+(getTimeslot()!=null?Integer.toHexString(System.identityHashCode(getTimeslot())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bill = "+(getBill()!=null?Integer.toHexString(System.identityHashCode(getBill())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "repairShop = "+(getRepairShop()!=null?Integer.toHexString(System.identityHashCode(getRepairShop())):"null");
  }
}