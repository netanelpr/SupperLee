package Sup_Inv.Suppliers.Supplier.Order;

import Sup_Inv.Suppliers.Structs.Days;
import Sup_Inv.Suppliers.Structs.StructUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PeriodicalOrder extends Order {

    private List<Days> days;
    private int weekPeriod;
    private List<Date> orderDates;

    protected PeriodicalOrder(int orderId, List<ProductInOrder> products, List<Days> days, int weekPeriod, int shopNumber,
                              Date deliveryDay, List<Date> orderDates){
        super(orderId, products, shopNumber);
        this.days = days;
        this.weekPeriod = weekPeriod;
        this.setDeliveryDay(deliveryDay);
        this.orderDates = orderDates;
    }

    public static PeriodicalOrder CreatePeriodicalOrder(int orderId, List<ProductInOrder> productsInOrder, List<Days> days,
                                                        int weekPeriod, int shopNumber, Date deliveryDay){
        if(productsInOrder.isEmpty() | days.isEmpty()){
            return null;
        }

        if(weekPeriod < 1 | weekPeriod > 3){
            return null;
        }

        int deliveryDates;
        if(weekPeriod == 2) {
            deliveryDates = 4;
        } else {
            deliveryDates = 2;
        }

        List<Date> orderDates = new LinkedList<>();
        Date date = Calendar.getInstance().getTime();

        for(int j=0; j < weekPeriod; j++){
            date = StructUtils.getTheNearestDateWithWeekPeriod(date, days, weekPeriod);
            orderDates.add(date);
        }

        return new PeriodicalOrder(orderId, productsInOrder, days, weekPeriod, shopNumber, deliveryDay, orderDates);
    }

    public static PeriodicalOrder CreatePeriodicalOrder(int orderId, List<ProductInOrder> productsInOrder, List<Days> days,
                                                        int weekPeriod, int shopNumber, Date deliveryDay, List<Date> orderDates){
        if(productsInOrder.isEmpty() | days.isEmpty()){
            return null;
        }

        if(weekPeriod < 1 | weekPeriod > 3){
            return null;
        }

        return new PeriodicalOrder(orderId, productsInOrder, days, weekPeriod, shopNumber, deliveryDay, orderDates);
    }

    public int getWeekPeriod() {
        return weekPeriod;
    }

    public void setWeekPeriod(int weekPeriod) {
        this.weekPeriod = weekPeriod;
    }

    public List<Days> getDays() {
        return days;
    }

    public void setDays(List<Days> days) {
        this.days = days;
    }

    public List<Date> getOrderDates() {
        return orderDates;
    }
}
