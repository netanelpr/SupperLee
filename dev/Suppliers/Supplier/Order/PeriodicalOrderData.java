package Suppliers.Supplier.Order;

import Suppliers.Structs.Days;

import java.util.List;

public class PeriodicalOrderData {
    public List<Days> days;
    public int weekP;

    public PeriodicalOrderData(List<Days> days, int weekP){
        this.days = days;
        this.weekP = weekP;
    }
}
