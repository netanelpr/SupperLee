package Tests.Visual;

import Suppliers.Supplier.Order.OrderManager;
import Suppliers.Supplier.Order.RegularOrder;
import Suppliers.Supplier.ProductInOrder;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SupplierRegularOrder {
    public static void getOrderById(){
        OrderManager orderManager = OrderManager.getInstance();
        RegularOrder order = orderManager.getRegularOrder(1);

        String orderDetails = String.format("orderId :%d\nShop number: %d\nDelivery date: %s\nStatus: %s\ncontract id: %d",
                                    order.getOrderId(), order.getShopNumber(),order.getDeliveryDay(), order.getStatus().toString(), order.getContractId());
        String productsStr = "";
        for(ProductInOrder product : order.retrunProducts()){
            String productStr = String.format("barcode:%d\tcatalog number:%s\tamount: %d\tPrice per unit: %f", product.getBarcode(),
                    product.getProductCatalogNumber(), product.getAmount(), product.getPricePerUnit());
            productsStr = String.format("%s\n%s", productsStr, productStr);
        }

        System.out.println(orderDetails + "\n" + productsStr);
    }

    //TODO delete it manualy
    public static void addRegularOrder(){
        OrderManager orderManager = OrderManager.getInstance();

        List<ProductInOrder> products = new LinkedList<>();
        products.add(new ProductInOrder(1,10,"c1",10));
        products.add(new ProductInOrder(2,150,"c2",12));
        RegularOrder order = RegularOrder.CreateRegularOrder(-1, products, 1);

        Calendar c = Calendar.getInstance();
        c.set(2020, Calendar.MAY, 11);
        long time = c.getTimeInMillis();
        order.setDeliveryDay(new Date(time));

        order.setContractId(1);

        orderManager.createRegularOrder(order);

        if(order.getOrderId() < 0){
            System.out.println("Order wasnt created");
        } else {
            System.out.println("Order id:"+ order.getOrderId());
        }
    }
}
