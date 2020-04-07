package Structs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StructUtils {

    public static Map<String, Days> createDaysMap(){
        Map<String, Days> map = new HashMap<>();

        map.put("Sunday", Days.Sunday);
        map.put("Monday", Days.Monday);
        map.put("Tuesday", Days.Tuesday);
        map.put("Wednesday", Days.Wednesday);
        map.put("Thursday", Days.Thursday);
        map.put("Friday", Days.Friday);
        map.put("Saturday", Days.Saturday);

        return  map;
    }

    /**
     * Create a list of days according to the String[]
     * @param days
     * @return null if there is a string that doesnt match a day, otherwise return the list of days.
     */
    public static List<Days> getDaysList(String[] days){
        Map<String, Days> map = createDaysMap();
        List<Days> list = new LinkedList<>();

        for (String day : days) {
            Days d =  map.getOrDefault(day, null);
            if(d == null){
                return null;
            }

            list.add(d);
        }

        return list;
    }
}
