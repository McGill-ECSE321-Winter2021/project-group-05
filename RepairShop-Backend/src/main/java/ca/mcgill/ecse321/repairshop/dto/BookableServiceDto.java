package ca.mcgill.ecse321.repairshop.dto;

import ca.mcgill.ecse321.repairshop.model.BookableService;

public class BookableServiceDto {
    //Service Attributes
    private String name;
    private float cost;
    private int duration;

    public BookableServiceDto(){}

    public BookableServiceDto(String name, float cost,int duration){
        this.cost=cost;
        this.duration=duration;
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public float getCost(){
        return this.cost;
    }

    public int getDuration(){
        return this.duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}
