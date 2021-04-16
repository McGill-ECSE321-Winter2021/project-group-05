package ca.mcgill.ecse321.repairshop_android.Model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Appointment {
    private List<String> services;
    private Date date;
    private Time endTime;
    private Time startTime;

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
}
