/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.util.*;

// line 15 "model.ump"
// line 101 "model.ump"
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

  //Business Associations
  private List<TimeSlot> bookableService;
  private RepairShop repairShop;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Business(String aName, String aAddress, String aPhoneNumber, String aEmail, RepairShop aRepairShop)
  {
    name = aName;
    address = aAddress;
    phoneNumber = aPhoneNumber;
    email = aEmail;
    bookableService = new ArrayList<TimeSlot>();
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
  /* Code from template association_GetMany */
  public TimeSlot getBookableService(int index)
  {
    TimeSlot aBookableService = bookableService.get(index);
    return aBookableService;
  }

  public List<TimeSlot> getBookableService()
  {
    List<TimeSlot> newBookableService = Collections.unmodifiableList(bookableService);
    return newBookableService;
  }

  public int numberOfBookableService()
  {
    int number = bookableService.size();
    return number;
  }

  public boolean hasBookableService()
  {
    boolean has = bookableService.size() > 0;
    return has;
  }

  public int indexOfBookableService(TimeSlot aBookableService)
  {
    int index = bookableService.indexOf(aBookableService);
    return index;
  }
  /* Code from template association_GetOne */
  public RepairShop getRepairShop()
  {
    return repairShop;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBookableService()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addBookableService(TimeSlot aBookableService)
  {
    boolean wasAdded = false;
    if (bookableService.contains(aBookableService)) { return false; }
    bookableService.add(aBookableService);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBookableService(TimeSlot aBookableService)
  {
    boolean wasRemoved = false;
    if (bookableService.contains(aBookableService))
    {
      bookableService.remove(aBookableService);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBookableServiceAt(TimeSlot aBookableService, int index)
  {  
    boolean wasAdded = false;
    if(addBookableService(aBookableService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBookableService()) { index = numberOfBookableService() - 1; }
      bookableService.remove(aBookableService);
      bookableService.add(index, aBookableService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBookableServiceAt(TimeSlot aBookableService, int index)
  {
    boolean wasAdded = false;
    if(bookableService.contains(aBookableService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBookableService()) { index = numberOfBookableService() - 1; }
      bookableService.remove(aBookableService);
      bookableService.add(index, aBookableService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBookableServiceAt(aBookableService, index);
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
    bookableService.clear();
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
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "repairShop = "+(getRepairShop()!=null?Integer.toHexString(System.identityHashCode(getRepairShop())):"null");
  }
}