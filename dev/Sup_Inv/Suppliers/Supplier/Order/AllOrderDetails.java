package Sup_Inv.Suppliers.Supplier.Order;

import Sup_Inv.Suppliers.Supplier.Supplier;

import java.util.List;

public class AllOrderDetails {
    public int orderId;
    public int shopNumber;
    public String  deliveryDate;
    public List<AllDetailsOfProductInOrder> details;
    public PeriodicalOrderData periodicalOrderData;
    public Supplier supplier;

    public AllOrderDetails(int orderId, int shopNumber, String deliveryDate, Supplier supplier,
                           List<AllDetailsOfProductInOrder> details, PeriodicalOrderData periodicalOrderData){
        this.orderId = orderId;
        this.shopNumber = shopNumber;
        this.supplier = supplier;
        this.details = details;
        this.deliveryDate = deliveryDate;
        this.periodicalOrderData = periodicalOrderData;
    }
}
