package ca.mcgill.ecse321.repairshop.dto;

import java.sql.Date;

public class BillDto {
    private Date date;
    private float totalCost;
    private Long id;

    public BillDto(){}

    public BillDto(Date date,float totalCost,Long id){
        this.date=date;
        this.totalCost=totalCost;
        this.id=id;
    }

    public Date getDate() {
        return this.date;
    }

    public float getTotalCost() {
        return this.totalCost;
    }

    public Long getId() {
        return id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
