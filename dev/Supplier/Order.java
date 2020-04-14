package Supplier;

import Structs.Days;
import Structs.OrderStatus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Order {

    static private int GLOBAL_ORDER_ID = 0;

    private int orderId;
    private Map<String, ProductInOrder> products;
    private OrderStatus status;
    private Days deliveryDay;

    private Order(List<ProductInOrder> products){
        orderId = GLOBAL_ORDER_ID;
        GLOBAL_ORDER_ID = GLOBAL_ORDER_ID + 1;

        this.products = new HashMap<>();
        for (ProductInOrder product : products) {
            this.products.put(product.getProductCatalogNumber(),
                                new ProductInOrder(product.getBarcode(),
                                                    product.getAmount(),
                                                    product.getProductCatalogNumber()));
        }

        status = OrderStatus.Open;
        deliveryDay = null;


    }

    /**
     * Supplier.Order cant be created without at least one product.
     * @return new order
     */
    public static Order CreateOrder(List<ProductInOrder> productsInOrder){
        if(productsInOrder.isEmpty()){
            return null;
        }

        return new Order(productsInOrder);
    }

    public int getOrderId(){
        return  orderId;
    }

    public boolean setStatus(OrderStatus status){
        this.status=status;
        return true;
    }

    public boolean updateDeliveryDay(Days day){
        this.deliveryDay=day;
        return true;
    }

    public List<String> retrunProductsCatalogNumbers() {
        return new LinkedList<>(this.products.keySet());
    }
}
