package ca.mcgill.ecse321.repairshop_android.Activities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class JsonHelper {
    /**
     * Converts JSONArray to List
     * @param array
     * @return
     * @throws JSONException
     */
    public static List<String> toList(JSONArray array) throws JSONException {
        List<String> list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }

    /**
     * Converts JSONObject to String
     * @param json
     * @return
     * @throws JSONException
     */
    private static String fromJson(Object json) throws JSONException {
        if(json instanceof  JSONObject) {
            return ((JSONObject) json).get("name").toString();
        }
        return null;
    }
}
