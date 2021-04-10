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

    /**
     * creates timeslot transfer object
     *
     * @param date timeslot date
     * @param startTime timeslot start time
     * @param endTime timeslot end time
     * @param id timeslot id
     */
    public TimeSlotDto(Date date, Time startTime, Time endTime, Long id) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
    }

    /**
     * return end time of timeslot
     *
     * @return end time
     */
    public Time getEndTime() {
        return this.endTime;
    }

    /**
     * returns date of timeslot
     *
     * @return date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * returns start time of timeslot
     *
     * @return start time
     */
    public Time getStartTime() {
        return this.startTime;
    }

    /**
     * returns id of timeslot
     *
     * @return id (Long)
     */
    public Long getId() {
        return id;
    }

}
