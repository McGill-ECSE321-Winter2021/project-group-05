package ca.mcgill.ecse321.repairshop.utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TimeSlotException extends Exception{
    public TimeSlotException(String message){
        super(message);
    }
}
