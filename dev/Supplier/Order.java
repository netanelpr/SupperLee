package Supplier;

import Structs.OrderStatus;

import java.util.List;
import java.util.Map;

public class Order {

    static private int GLOBAL_ORDER_ID = 0;

    private int orderId;
    private Map<Integer, Integer> products;
    private OrderStatus status;

    //TODO Implement
    private Order(List<ProductInOrder> products){
        orderId = GLOBAL_ORDER_ID;
        GLOBAL_ORDER_ID = GLOBAL_ORDER_ID + 1;

    }

    /**
     * Supplier.Order cant be created without at least one product.
     * @return new order
     */
    public static Order CreateOrder(List<ProductInOrder> productsInOrder){
        if(productsInOrder.isEmpty()){
            // modify it if you need to
            throw new NullPointerException("Supplier.Order cant be empty");
        }

        return new Order(productsInOrder);
    }

    public int getOrderId(){
        return  orderId;
    }

    public boolean setStatus(OrderStatus status){
        throw new UnsupportedOperationException();
    }
}
