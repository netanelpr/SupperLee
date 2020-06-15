package Sup_Inv.Suppliers.Supplier.Order;

import Sup_Inv.Suppliers.Structs.Days;

import java.util.Date;
import java.util.List;

public class PeriodicalOrderData {
    public List<Days> days;
    public int weekP;
    public List<Date> orderDates;

    public PeriodicalOrderData(List<Days> days, int weekP, List<Date> orderDates){
        this.days = days;
        this.weekP = weekP;
        this.orderDates = orderDates;
    }
}
