package ca.mcgill.ecse321.repairshop.dto;

import java.sql.Date;
import java.sql.Time;

public class TimeSlotDto {
    private Date date;
    private Time startTime;
    private Time endTime;
    private Long id;

    public TimeSlotDto() {
    }

    public TimeSlotDto(Date date, Time startTime, Time endTime, Long id) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
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

    public Long getId() {
        return id;
    }

}
