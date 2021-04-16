package ca.mcgill.ecse321.repairshop_android.Model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Appointment {
    private List<String> services;
    private Date date;
    private Time endTime;
    private Time startTime;
    public int MONTH;
    public int DAY;
    public int YEAR;
    private String customer;
    private String id;

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCustomer(String email) {
        this.customer = email;
    }

    public String getCustomer() {
        return this.customer;
    }
}
