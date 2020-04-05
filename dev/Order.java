import java.util.List;

public class Order {

    static private int GLOBAL_ORDER_ID = 0;

    private int orderId;
    private List<ProductInOrder> products;

    private Order(List<ProductInOrder> products){
        orderId = GLOBAL_ORDER_ID;
        GLOBAL_ORDER_ID = GLOBAL_ORDER_ID + 1;
        this.products = products;
    }

    /**
     * Order cant be created without at least one product.
     * @return new order
     */
    public static Order CreateOrder(List<ProductInOrder> productsInOrder){
        if(productsInOrder.isEmpty()){
            // modify it if you need to
            throw new NullPointerException("Order cant be empty");
        }

        return new Order(productsInOrder);
    }
}
