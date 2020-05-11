package Suppliers.Supplier.Order;

import Suppliers.Structs.Days;
import Suppliers.Supplier.ProductInOrder;

import java.util.List;

public class PeriodicalOrder extends Order {

    private List<Days> days;
    private int weekPeriod;

    protected PeriodicalOrder(int orderId, List<ProductInOrder> products, List<Days> days, int weekPeriod, int shopNumber){
        super(orderId, products, shopNumber);
        this.days = days;
        this.weekPeriod = weekPeriod;
    }

    public static Order CreateRegularOrder(int orderId, List<ProductInOrder> productsInOrder, List<Days> days, int weekPeriod, int shopNumber){
        if(productsInOrder.isEmpty() | days.isEmpty()){
            return null;
        }

        if(weekPeriod < 1){
            return null;
        }



        return new PeriodicalOrder(orderId, productsInOrder, days, weekPeriod, shopNumber);
    }
}
