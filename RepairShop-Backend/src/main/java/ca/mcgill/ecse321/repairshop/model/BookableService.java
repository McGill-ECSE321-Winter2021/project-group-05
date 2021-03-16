package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.*;

@Entity
public class BookableService {
  //Service Attributes
  private String name;
  private float cost;
  private int duration;


  //Service Associations
  private RepairShop repairShop;

  @Id
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getCost() {
    return cost;
  }

  public void setCost(float cost) {
    this.cost = cost;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  @ManyToOne(cascade = {CascadeType.ALL})
  public RepairShop getRepairShop() {
    return repairShop;
  }

  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }
}


