/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/
package ca.mcgill.ecse321.repairshop.model;

import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;
@Entity
public class TimeSlot
{
  //TimeSlot Attributes
  private Date date;
  private Time startTime;
  private Time endTime;


  //TimeSlot Associations
  private RepairShop repairShop;
  private Long ID;


  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }



  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  @ManyToOne
  public RepairShop getRepairShop() {
    return repairShop;
  }

  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }


  public void setId(Long id) {
    this.ID = id;
  }

  @Id
  @GeneratedValue
  public Long getId() {
    return ID;
  }
}