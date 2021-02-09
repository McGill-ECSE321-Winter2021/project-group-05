/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/



// line 77 "model.ump"
// line 149 "model.ump"
public abstract class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String email;
  private String username;
  private String password;

  //User Associations
  private RepairShop repairShop;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aEmail, String aUsername, String aPassword, RepairShop aRepairShop)
  {
    email = aEmail;
    username = aUsername;
    password = aPassword;
    boolean didAddRepairShop = setRepairShop(aRepairShop);
    if (!didAddRepairShop)
    {
      throw new RuntimeException("Unable to create user due to repairShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public RepairShop getRepairShop()
  {
    return repairShop;
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
      existingRepairShop.removeUser(this);
    }
    repairShop.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    RepairShop placeholderRepairShop = repairShop;
    this.repairShop = null;
    if(placeholderRepairShop != null)
    {
      placeholderRepairShop.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "repairShop = "+(getRepairShop()!=null?Integer.toHexString(System.identityHashCode(getRepairShop())):"null");
  }
}