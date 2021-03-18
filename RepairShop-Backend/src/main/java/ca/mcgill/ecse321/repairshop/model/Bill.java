package ca.mcgill.ecse321.repairshop.model;

import java.sql.Date;
import javax.persistence.*;

@Entity
public class Bill
{
  private Date date;
  private float totalCost;
  private Long id;

  //Bill Associations
  private RepairShop repairShop;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public float getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(float totalCost) {
    this.totalCost = totalCost;
  }

  @ManyToOne
  public RepairShop getRepairShop() {
    return repairShop;
  }

  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }
}