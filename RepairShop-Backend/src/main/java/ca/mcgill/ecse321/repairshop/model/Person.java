package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.*;

@Entity
public abstract class Person {

  // Person Attributes
  private String email;
  private String username;
  private String password;
  private Long id;
  @Enumerated
  private PersonType personType;

  // Person Associations
  private RepairShop repairShop;

  /**
   * return id for person
   *
   * @return id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  /**
   * set id for person
   *
   * @param id person id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * return email for person
   *
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * set email for person
   *
   * @param email person email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * return username for person
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * set username for person
   *
   * @param username person username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * return passwords for person
   *
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * set password for person
   *
   * @param password person password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * return shop for person
   *
   * @return repair shop
   */
  @ManyToOne(cascade = { CascadeType.ALL })
  public RepairShop getRepairShop() {
    return repairShop;
  }

  /**
   * set shop for person
   *
   * @param repairShop repair shop
   */
  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }

  /**
   * set type of person for person (customer, admin, technician)
   *
   * @param personType type
   */
  public void setPersonType(PersonType personType) {
    this.personType = personType;
  }

  /**
   * return type of person for person (customer, admin, technician)
   *
   * @return type
   */
  public PersonType getPersonType() {
    return this.personType;
  }

}