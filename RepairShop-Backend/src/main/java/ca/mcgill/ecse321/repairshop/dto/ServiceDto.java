package ca.mcgill.ecse321.repairshop.dto;

import ca.mcgill.ecse321.repairshop.model.Service;

public class ServiceDto {
    //Service Attributes
    private String name;
    private float cost;
    private int duration;

    public ServiceDto(){}

    public ServiceDto(String name, float cost,int duration){
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
    public float getDuration(){
        return this.duration;
    }


}
