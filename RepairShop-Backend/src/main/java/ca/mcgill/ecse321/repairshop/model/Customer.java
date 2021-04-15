/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/
package ca.mcgill.ecse321.repairshop.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import java.sql.Date;

@Entity
public class Customer extends Person
{
  //Customer Attributes
  private String cardNumber;
  private String cvv;
  private Date expiry;
  private int noShow;

  /**
   * return card number for customer
   *
   * @return card number
   */
  public String getCardNumber() {
    return cardNumber;
  }

  /**
   * set no show for customer
   *
   * @param noShow no show
   *
   */
  public void setNoShow(int noShow) {this.noShow = noShow;}

  /**
   * return no show for customer
   *
   * @return no show
   */
  public int getNoShow() { return noShow; }

  /**
   * set card number for customer
   *
   * @param cardNumber customer card number
   */
  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  /**
   * return card cvv for customer
   *
   * @return cvv
   */
  public String getCvv() {
    return cvv;
  }

  /**
   * set card cvv for customer
   *
   * @param cvv customer card cvv
   */
  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  /**
   * return date of expiry for customer card
   *
   * @return date of expiry
   */
  public Date getExpiry() {
    return expiry;
  }

  /**
   * set date of expiry for customer card
   *
   * @param expiry date of expiry of card of customer
   */
  public void setExpiry(Date expiry) {
    this.expiry = expiry;
  }

}