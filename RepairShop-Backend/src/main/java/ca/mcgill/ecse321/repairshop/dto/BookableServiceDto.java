package ca.mcgill.ecse321.repairshop.dto;

public class BookableServiceDto {
    //Service Attributes
    private String name;
    private float cost;
    private int duration;
    private Long Id;

    public BookableServiceDto(){}

    /**
     * creates a service transfer object
     *
     * @param name service name
     * @param cost service cost
     * @param duration service duration
     */
    public BookableServiceDto(String name, float cost,int duration){
        this.cost=cost;
        this.duration=duration;
        this.name=name;
    }

    /**
     * returns the name of service
     *
     * @return name (String)
     */
    public String getName(){
        return this.name;
    }

    /**
     * returns cost of service
     *
     * @return cost (float)
     */
    public float getCost(){
        return this.cost;
    }

    /**
     * returns duration of service
     *
     * @return duration (int)
     */
    public int getDuration(){
        return this.duration;
    }

    /**
     * sets name of service
     *
     * @param name service name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets cost of service
     *
     * @param cost service cost
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     * sets duration of service
     *
     * @param duration service duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * return id of service
     *
     * @return id (long)
     */
    public Long getId() {
        return Id;
    }

    /**
     * sets id of service
     *
     * @param id service id
     */
    public void setId(Long id) {
        Id = id;
    }
}
