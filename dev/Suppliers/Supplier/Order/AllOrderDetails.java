package Suppliers.Supplier.Order;

import Suppliers.Supplier.Supplier;

import java.util.List;

public class AllOrderDetails {
    public int orderId;
    public int shopNumber;
    public String  deliveryDate;
    public List<AllDetailsOfProductInOrder> details;
    public Supplier supplier;

    public AllOrderDetails(int orderId, int shopNumber, String deliveryDate, Supplier supplier, List<AllDetailsOfProductInOrder> details){
        this.orderId = orderId;
        this.shopNumber = shopNumber;
        this.supplier = supplier;
        this.details = details;
        this.deliveryDate = deliveryDate;
    }
}
