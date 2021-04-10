package ca.mcgill.ecse321.repairshop.dto;

import java.sql.Date;

public class BillDto {
    private Date date;
    private float totalCost;
    private Long id;

    public BillDto(){}

    /**
     * creates a bill transfer object
     *
     * @param date date of bill
     * @param totalCost total cost of bill
     * @param id bill id
     */
    public BillDto(Date date,float totalCost,Long id){
        this.date=date;
        this.totalCost=totalCost;
        this.id=id;
    }

    /**
     * returs the date of a bill
     *
     * @return date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * returns total cost of bill
     *
     * @return total cost (float)
     */
    public float getTotalCost() {
        return this.totalCost;
    }

    /**
     * returns id of bill
     *
     * @return bill id (long)
     */
    public Long getId() {
        return id;
    }

    /**
     * sets date of bill
     *
     * @param date bill date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * sets total cost of bill
     *
     * @param totalCost bill cost
     */
    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * sets id of bill
     *
     * @param id bill id
     */
    public void setId(Long id) {
        this.id = id;
    }
}
