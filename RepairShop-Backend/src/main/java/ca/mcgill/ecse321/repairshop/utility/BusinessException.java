package ca.mcgill.ecse321.repairshop.utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessException extends Exception{
    /**
     * return an exception
     *
     * @param message exception
     */
    public BusinessException(String message){
        super(message);
    }
}
