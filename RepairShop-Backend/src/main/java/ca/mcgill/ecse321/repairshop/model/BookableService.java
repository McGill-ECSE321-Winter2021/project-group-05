package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.*;

@Entity
public class BookableService {
  //Service Attributes
  private String name;
  private float cost;
  private int duration;
  private Long id;

  private RepairShop repairShop;

  /**
   * return id for service
   *
   * @return id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  /**
   * set id for service
   *
   * @param id service id
   */
  public void setId(Long id) {
    this.id = id;
  }


  /**
   * return name for service
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * set name for service
   *
   * @param name service name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * return cost for service
   *
   * @return cost
   */
  public float getCost() {
    return cost;
  }

  /**
   * set cost for service
   *
   * @param cost service cost
   */
  public void setCost(float cost) {
    this.cost = cost;
  }

  /**
   * set duration for service
   *
   * @return duration
   */
  public int getDuration() {
    return duration;
  }

  /**
   * set duration for service
   *
   * @param duration service duration
   */
  public void setDuration(int duration) {
    this.duration = duration;
  }

  /**
   * return shop for service
   *
   * @return shop
   */
  @ManyToOne(cascade = {CascadeType.ALL})
  public RepairShop getRepairShop() {
    return repairShop;
  }

  /**
   * set shop for service
   *
   * @param repairShop repair shop
   */
  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }
}


