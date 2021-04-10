package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long> {
    /**
     * returns timeslot by id
     *
     * @param id timeslot id
     * @return timeslot
     */
    TimeSlot findTimeSlotById(Long id);

    /**
     * returns all timeslots
     *
     * @return list of timeslots
     */
    List<TimeSlot> findAll();
}
