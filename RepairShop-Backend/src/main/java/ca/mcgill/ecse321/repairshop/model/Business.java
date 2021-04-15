package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.*;

@Entity
public class Business
{
  //Business Attributes
  private String name;
  private String address;
  private String phoneNumber;
  private String email;
  private Long id;

  //Business Associations
  private RepairShop repairShop;

  /**
   * return name for business
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * set name for business
   *
   * @param name business name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * get address for business
   *
   * @return address
   */
  public String getAddress() {
    return address;
  }

  /**
   * set address for business
   *
   * @param address business address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * get number for business
   *
   * @return phone number
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * set number for business
   *
   * @param phoneNumber business phone number
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * get email for business
   *
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * set email for business
   *
   * @param email business email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * return repair shop for business
   *
   * @return shop
   */
  @OneToOne(cascade = {CascadeType.ALL})
  public RepairShop getRepairShop() {
    return repairShop;
  }

  /**
   * set shop for business
   *
   * @param repairShop repair shop
   */
  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }

  /**
   * set id for business
   *
   * @param id business id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * return id for business
   *
   * @return business id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

}