package ca.mcgill.ecse321.repairshop.dto;

import ca.mcgill.ecse321.repairshop.model.PersonType;

public class AdministratorDto {
    private String email;
    private String username;
    private String password;
    private Long id;
    private PersonType personType;

    /**
     * sets a person type by admin, customer, or technician
     *
     * @param personType type of person
     */
    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    /**
     * returns if person is customer, admin, or techincian (type)
     *
     * @return type of person
     */
    public PersonType getPersonType() {
        return this.personType;
    }

    /**
     * returns admin id
     *
     * @return admin id
     */
    public Long getId() {
        return id;
    }

    /**
     * sets the admin id
     *
     * @param id admin id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * returns the email for an admin
     *
     * @return admin email
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the email for an admin
     *
     * @param email admin email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * returns the username for an admin
     *
     * @return admin username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username for an admin
     *
     * @param username admin username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * returns the password of an admin
     *
     * @return admin password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets password for an admin
     *
     * @param password admin password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
