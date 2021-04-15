package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.RepairShop;
import ca.mcgill.ecse321.repairshop.model.TimeSlot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTimeSlotPersistence {


    @Autowired
    private TimeSlotRepository timeSlotRepository;

    /**
     * clear database
     */
    @AfterEach
    public void clearDatabase() {

        timeSlotRepository.deleteAll();
    }

    /**
     * Testing persisting and loading timeslot
     */
    public void testPersistAndLoadTimeSlot(){

        Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
        Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setEndTime(endTime);
        timeSlot.setStartTime(startTime);
        timeSlot.setDate(date);

        RepairShop rs = new RepairShop();
        timeSlot.setRepairShop(rs);

        timeSlotRepository.save(timeSlot);

        Long timeSlotID =timeSlot.getId();

        timeSlot = timeSlotRepository.findTimeSlotById(timeSlotID);

        assertNotNull(timeSlot);
        assertEquals(timeSlotID, timeSlot.getId());
        assertEquals(date.toString(), timeSlot.getDate().toString());
        assertEquals(startTime.toString(), timeSlot.getStartTime().toString());
        assertEquals(endTime.toString(), timeSlot.getEndTime().toString());
        assertNotNull(timeSlot.getRepairShop());
    }
}
