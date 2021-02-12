/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;


// line 15 "model.ump"
// line 110 "model.ump"
@Entity
public class Business
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Business Attributes
  private String name;
  private String address;
  private String phoneNumber;
  private String email;
  private String id;

  //Business Associations
  private List<TimeSlot> service;
  private RepairShop repairShop;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Business(String aName, String aAddress, String aPhoneNumber, String aEmail, String aId, RepairShop aRepairShop)
  {
    name = aName;
    address = aAddress;
    phoneNumber = aPhoneNumber;
    email = aEmail;
    id = aId;
    service = new ArrayList<TimeSlot>();
    boolean didAddRepairShop = setRepairShop(aRepairShop);
    if (!didAddRepairShop)
    {
      throw new RuntimeException("Unable to create business due to repairShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }


  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getAddress()
  {
    return address;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getEmail()
  {
    return email;
  }

  @Id
  public String getId()
  {
    return id;
  }
  /* Code from template association_GetMany */
  public TimeSlot getService(int index)
  {
    TimeSlot aService = service.get(index);
    return aService;
  }

  @OneToMany(cascade={CascadeType.All})
  public List<TimeSlot> getService()
  {
    List<TimeSlot> newService = Collections.unmodifiableList(service);
    return newService;
  }

  public int numberOfService()
  {
    int number = service.size();
    return number;
  }

  public boolean hasService()
  {
    boolean has = service.size() > 0;
    return has;
  }

  public int indexOfService(TimeSlot aService)
  {
    int index = service.indexOf(aService);
    return index;
  }
  /* Code from template association_GetOne */
  public RepairShop getRepairShop()
  {
    return repairShop;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfService()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addService(TimeSlot aService)
  {
    boolean wasAdded = false;
    if (service.contains(aService)) { return false; }
    service.add(aService);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeService(TimeSlot aService)
  {
    boolean wasRemoved = false;
    if (service.contains(aService))
    {
      service.remove(aService);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceAt(TimeSlot aService, int index)
  {  
    boolean wasAdded = false;
    if(addService(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfService()) { index = numberOfService() - 1; }
      service.remove(aService);
      service.add(index, aService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceAt(TimeSlot aService, int index)
  {
    boolean wasAdded = false;
    if(service.contains(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfService()) { index = numberOfService() - 1; }
      service.remove(aService);
      service.add(index, aService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceAt(aService, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setRepairShop(RepairShop aNewRepairShop)
  {
    boolean wasSet = false;
    if (aNewRepairShop == null)
    {
      //Unable to setRepairShop to null, as business must always be associated to a repairShop
      return wasSet;
    }
    
    Business existingBusiness = aNewRepairShop.getBusiness();
    if (existingBusiness != null && !equals(existingBusiness))
    {
      //Unable to setRepairShop, the current repairShop already has a business, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    RepairShop anOldRepairShop = repairShop;
    repairShop = aNewRepairShop;
    repairShop.setBusiness(this);

    if (anOldRepairShop != null)
    {
      anOldRepairShop.setBusiness(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    service.clear();
    RepairShop existingRepairShop = repairShop;
    repairShop = null;
    if (existingRepairShop != null)
    {
      existingRepairShop.setBusiness(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "email" + ":" + getEmail()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "repairShop = "+(getRepairShop()!=null?Integer.toHexString(System.identityHashCode(getRepairShop())):"null");
  }
}