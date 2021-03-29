package ca.mcgill.ecse321.repairshop.dto;

import ca.mcgill.ecse321.repairshop.model.PersonType;

public class AdministratorDto {
    private String email;
    private String username;
    private String password;
    private Long id;
    private PersonType personType;

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public PersonType getPersonType() {
        return this.personType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
