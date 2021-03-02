package ca.mcgill.ecse321.repairshop.dto;

import java.sql.Date;
import java.sql.Time;

public class TimeSlotDto {
    private Date date;
    private Time startTime;
    private Time endTime;

    public TimeSlotDto() {
    }

    public TimeSlotDto(Date date, Time startTime, Time endTime) {
        this.date = date;
        this.endTime = endTime;
        this.endTime = endTime;
    }

    public Time getEndTime() {
        return this.endTime;
    }

    public Date getDate() {
        return this.date;
    }

    public Time getStartTime() {
        return this.startTime;
    }
}
