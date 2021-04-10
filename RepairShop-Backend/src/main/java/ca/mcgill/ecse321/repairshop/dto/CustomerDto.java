package ca.mcgill.ecse321.repairshop.dto;

import java.sql.Date;

import ca.mcgill.ecse321.repairshop.model.PersonType;

public class CustomerDto {
    private String email;
    private String username;
    private String password;
    private String cardNumber;
    private String cvv;
    private Date expiry;
    private int noShow;
    private Long id;
    private PersonType personType;

    /**
     * sets person type (customer)
     *
     * @param personType person type
     */
    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    /**
     * return person type (customer)
     *
     * @return person type
     */
    public PersonType getPersonType() {
        return this.personType;
    }

    /**
     * return id of customer
     *
     * @return id (Long)
     */
    public Long getId() {
        return id;
    }

    /**
     * sets id of customer
     *
     * @param id customer id
     */
    public void setId(Long id) {
        this.id = id;
    }


    public CustomerDto() {
    }


    @SuppressWarnings("unchecked")
    public CustomerDto(String email, String username, String password) {
        this(email, username, password, null, null, null);
    }

    /**
     * creates customer transfer object
     *
     * @param email customer email
     * @param username customer username
     * @param password customer password
     * @param cardNumber customer card number
     * @param cvv customer card cvv
     * @param expiry customer card expiry
     */
    public CustomerDto(String email, String username, String password, String cardNumber, String cvv, Date expiry) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiry = expiry;

    }

    /**
     * return customer email
     *
     * @return email (String)
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets customer email
     *
     * @param email customer email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * returns customer username
     *
     * @return username (String)
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets username of customer
     *
     * @param username customer username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * returns customer password
     *
     * @return password (String)
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets password of customer
     *
     * @param password customer password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * return customer card number
     *
     * @return card number (String)
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * sets customer card number
     *
     * @param cardNumber customer card number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * return customer card cvv
     *
     * @return cvv (String)
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * sets customer card cvv
     *
     * @param cvv card cvv
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    /**
     * return date of expiry of customer card
     *
     * @return expiry date
     */
    public Date getExpiry() {
        return expiry;
    }

    /**
     * sets date of expiry of customer card
     *
     * @param expiry expiry date of card
     */
    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    /**
     * returns number of no shows for a customer
     *
     * @return no show (int)
     */
    public int getNoShow() {
        return noShow;
    }

    /**
     * set number of no shows for a customer
     *
     * @param noShow customer no shows
     */
    public void setNoShow(int noShow) {
        this.noShow = noShow;
    }
}
