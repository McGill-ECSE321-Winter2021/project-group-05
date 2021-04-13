package ca.mcgill.ecse321.repairshop.dto;

import ca.mcgill.ecse321.repairshop.model.PersonType;

public class TechnicianDto {
    private String username;
    private String password;
    private String email;
    private Long id;
    private PersonType personType;
    private TimeSlotDto timeSlotDto;

    /**
     * sets person type (technician)
     *
     * @param personType person type
     */
    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    /**
     * return person type (technician)
     *
     * @return person type
     */
    public PersonType getPersonType() {
        return this.personType;
    }

    /**
     * return technician id
     *
     * @return id (Long)
     */
    public Long getId() {
        return id;
    }

    /**
     * set technician id
     *
     * @param id technician id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * returns technican username
     *
     * @return username (String)
     */
    public String getUsername() {
        return username;
    }

    /**
     * setus technician username
     *
     * @param username technician username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * returns tech password
     *
     * @return password (String)
     */
    public String getPassword() {
        return password;
    }


    /**
     * sets password for technician
     *
     * @param password techncian password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * returns email for technician
     *
     * @return email (String)
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets email for technician
     *
     * @param email technician email
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
