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
  private  Long id;

  //TimeSlot Associations
  private RepairShop repairShop;

  /**
   * return date for timeslot
   *
   * @return date
   */
  public Date getDate() {
    return date;
  }

  /**
   * set date for timeslot
   *
   * @param date timeslot date
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * return start time for timeslot
   *
   * @return start time
   */
  public Time getStartTime() {
    return startTime;
  }

  /**
   * set start time for timeslot
   *
   * @param startTime timeslot start time
   */
  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  /**
   * return end time for timeslot
   *
   * @return end time
   */
  public Time getEndTime() {
    return endTime;
  }

  /**
   * set end time for timeslot
   *
   * @param endTime timeslot end time
   */
  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  /**
   * return shop for timeslot
   *
   * @return repair shop
   */
  @ManyToOne(cascade = {CascadeType.ALL})
  public RepairShop getRepairShop() {
    return repairShop;
  }

  /**
   * set shop for timeslot
   *
   * @param repairShop repair shop
   */
  public void setRepairShop(RepairShop repairShop) {
    this.repairShop = repairShop;
  }

  /**
   * set id for timeslot
   *
   * @param id timeslot id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * return id for timeslot
   *
   * @return id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }
}