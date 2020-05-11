package Suppliers.Structs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StructUtils {

    public static Map<String, Days> createDaysMap(){
        Map<String, Days> map = new HashMap<>();

        map.put("SUNDAY", Days.Sunday);
        map.put("MONDAY", Days.Monday);
        map.put("TUESDAY", Days.Tuesday);
        map.put("WEDNESDAY", Days.Wednesday);
        map.put("THURSDAY", Days.Thursday);
        map.put("FRIDAY", Days.Friday);
        map.put("SATURDAY", Days.Saturday);

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
            Days d =  map.getOrDefault(day.toUpperCase(), null);
            if(d == null){
                return null;
            }

            list.add(d);
        }

        return list;
    }

    public static Days getDayWithInt(int day){
        if(day == 1){
            return Days.Sunday;
        }
        if(day == 2){
            return Days.Monday;
        }
        if(day == 3){
            return Days.Tuesday;
        }
        if(day == 4){
            return Days.Wednesday;
        }
        if(day == 5){
            return Days.Thursday;
        }
        if(day == 6){
            return Days.Friday;
        }
        if(day == 7){
            return Days.Saturday;
        }

        return null;
    }

    public static int getDayInt(Days day){
        if(day == Days.Sunday){
            return 1;
        }
        if(day == Days.Monday){
            return 2;
        }
        if(day == Days.Tuesday){
            return 3;
        }
        if(day == Days.Wednesday){
            return 4;
        }
        if(day == Days.Thursday){
            return 5;
        }
        if(day == Days.Friday){
            return 6;
        }
        if(day == Days.Saturday){
            return 7;
        }

        return -1;
    }

    private static Map<String, OrderStatus> createStatusMap(){
        Map<String, OrderStatus> map = new HashMap<>();

        map.put("OPEN", OrderStatus.Open);
        map.put("CLOSE", OrderStatus.Close);

        return  map;
    }

    /**
     * Return orderStatus from string
     * @param orderStatus order Status
     * @return null if there is not order status as the one given
     */
    public static OrderStatus getOrderStatus(String orderStatus){
        Map<String, OrderStatus> map = createStatusMap();

         return map.getOrDefault(orderStatus.toUpperCase(), null);
    }

    /**
     * Return orderStatus by number
     * @param orderStatus order Status
     * @return null if there is not order status as the one given
     */
    public static OrderStatus getOrderStatus(int orderStatus){
        if(orderStatus == 0) {
            return OrderStatus.Close;
        }

        if(orderStatus == 1) {
            return OrderStatus.Open;
        }

        return null;
    }

    public static int getOrderStatusInt(OrderStatus orderStatus){
        if(orderStatus == OrderStatus.Close) {
            return 0;
        }

        if(orderStatus == OrderStatus.Open) {
            return 1;
        }

        return -1;
    }
}
