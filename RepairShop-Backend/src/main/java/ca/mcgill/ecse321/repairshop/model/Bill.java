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

  /**
   * return date for bill
   *
   * @return date
   */
  public Date getDate() {
    return date;
  }

  /**
   * set date for bill
   *
   * @param date Date
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * return cost for bill
   *
   * @return cost
   */
  public float getTotalCost() {
    return totalCost;
  }

  /**
   * set cost for bill
   *
   * @param totalCost cost
   */
  public void setTotalCost(float totalCost) {
    this.totalCost = totalCost;
  }

  /**
   * return repairshop for bill
   *
   * @return repair shop
   */
  @ManyToOne
  public RepairShop getRepairShop() {
    return repairShop;
  }

  /**
   * set shop for bill
   *
   * @param repairShop repair shop
   */
  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }

  /**
   * set id for bill
   *
   * @param id bill id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * return id for bill
   *
   * @return id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }
}