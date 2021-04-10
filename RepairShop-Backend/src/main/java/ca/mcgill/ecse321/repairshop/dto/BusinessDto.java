package ca.mcgill.ecse321.repairshop.dto;

public class BusinessDto {
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private Long id;

    public BusinessDto (){

    }

    /**
     * creates business transfer object
     *
     * @param name business object
     * @param address business address
     * @param phoneNumber business phone number
     * @param email business email
     * @param id business id
     */
    public BusinessDto(String name, String address, String phoneNumber, String email, Long id) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.id = id;
    }

    /**
     * return id of business
     *
     * @return id (long)
     */
    public Long getId() {
        return id;
    }

    /**
     * sets id of business
     *
     * @param id business id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * returns name of business
     *
     * @return name (String)
     */
    public String getName() {
        return name;
    }

    /**
     * sets name of business
     *
     * @param name business name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns address of business
     *
     * @return address (String)
     */
    public String getAddress() {
        return address;
    }

    /**
     * set address of business
     *
     * @param address business address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * returns phone number of business
     *
     * @return phone (String)
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * sets phone number of business
     *
     * @param phoneNumber business phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * returns email of business
     *
     * @return email (String)
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets email of business
     *
     * @param email business email
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
