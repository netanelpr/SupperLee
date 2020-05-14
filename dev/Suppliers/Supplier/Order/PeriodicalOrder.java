package Suppliers.Supplier.Order;

import Suppliers.Structs.Days;

import java.util.Date;
import java.util.List;

public class PeriodicalOrder extends Order {

    private List<Days> days;
    private int weekPeriod;

    protected PeriodicalOrder(int orderId, List<ProductInOrder> products, List<Days> days, int weekPeriod, int shopNumber, Date deliveryDay){
        super(orderId, products, shopNumber);
        this.days = days;
        this.weekPeriod = weekPeriod;
        this.setDeliveryDay(deliveryDay);
    }

    public static PeriodicalOrder CreatePeriodicalOrder(int orderId, List<ProductInOrder> productsInOrder, List<Days> days,
                                                        int weekPeriod, int shopNumber, Date deliveryDay){
        if(productsInOrder.isEmpty() | days.isEmpty()){
            return null;
        }

        if(weekPeriod < 1){
            return null;
        }



        return new PeriodicalOrder(orderId, productsInOrder, days, weekPeriod, shopNumber, deliveryDay);
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
}
