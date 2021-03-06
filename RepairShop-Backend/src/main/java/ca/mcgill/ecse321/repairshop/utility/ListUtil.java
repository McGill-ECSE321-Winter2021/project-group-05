package ca.mcgill.ecse321.repairshop.utility;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    /**
     * Converts an iterable data type to a list
     * @param iterable
     * @param <T>
     * @return
     */
    public static<T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}
