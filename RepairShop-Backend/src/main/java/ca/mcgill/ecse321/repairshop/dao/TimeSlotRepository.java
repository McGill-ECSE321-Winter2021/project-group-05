package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long> {
    TimeSlot findTimeSlotById(Long id);
    List<TimeSlot> findAll();
}
