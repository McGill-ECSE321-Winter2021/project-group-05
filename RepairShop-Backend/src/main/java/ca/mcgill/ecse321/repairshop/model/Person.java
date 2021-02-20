/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/
package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.Entity;
import javax.persistence.Id;

// line 82 "model.ump"
// line 162 "model.ump"
@Entity
public abstract class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String email;
  private String username;
  private String password;
  private String id;

  //Person Associations
  private RepairShop repairShop;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aEmail, String aUsername, String aPassword, String aId, RepairShop aRepairShop)
  {
    email = aEmail;
    username = aUsername;
    password = aPassword;
    id = aId;
    boolean didAddRepairShop = setRepairShop(aRepairShop);
    if (!didAddRepairShop)
    {
      throw new RuntimeException("Unable to create person due to repairShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  // for testing
  public Person(){}

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

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
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

  @Id
  public String getId()
  {
    return id;
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
      existingRepairShop.removePerson(this);
    }
    repairShop.addPerson(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    RepairShop placeholderRepairShop = repairShop;
    this.repairShop = null;
    if(placeholderRepairShop != null)
    {
      placeholderRepairShop.removePerson(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "repairShop = "+(getRepairShop()!=null?Integer.toHexString(System.identityHashCode(getRepairShop())):"null");
  }
}