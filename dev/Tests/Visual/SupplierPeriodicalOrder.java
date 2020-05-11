package Tests.Visual;

import Suppliers.Structs.Days;
import Suppliers.Structs.StructUtils;
import Suppliers.Supplier.Order.OrderManager;
import Suppliers.Supplier.Order.PeriodicalOrder;
import Suppliers.Supplier.Order.RegularOrder;
import Suppliers.Supplier.ProductInOrder;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SupplierPeriodicalOrder {
    public static void getOrderById(){
        OrderManager orderManager = OrderManager.getInstance();
        PeriodicalOrder order = orderManager.getPeriodicalOrder(6);

        String orderDetails = String.format("orderId :%d\nShop number: %d\nDelivery date: %s\nStatus: %s\ncontract id: %d\nweekp: %d\nsupply days: %s",
                                    order.getOrderId(), order.getShopNumber(),order.getDeliveryDay(),
                                    order.getStatus().toString(), order.getContractId(),order.getWeekPeriod(), order.getDays().toString());
        String productsStr = "";
        for(ProductInOrder product : order.retrunProducts()){
            String productStr = String.format("barcode:%d\tcatalog number:%s\tamount: %d", product.getBarcode(), product.getProductCatalogNumber(), product.getAmount());
            productsStr = String.format("%s\n%s", productsStr, productStr);
        }

        System.out.println(orderDetails + "\n" + productsStr);
    }

    //TODO delete it manualy
    public static void addPeriodicalOrder(){
        OrderManager orderManager = OrderManager.getInstance();

        List<Days> days = new LinkedList<>();
        days.add(StructUtils.getDayWithInt(1));
        days.add(StructUtils.getDayWithInt(5));

        List<ProductInOrder> products = new LinkedList<>();
        products.add(new ProductInOrder(1,10,"c1"));
        products.add(new ProductInOrder(2,150,"c2"));
        PeriodicalOrder order = PeriodicalOrder.CreatePeriodicalOrder(-1, products, days, 1, 1);

        Calendar c = Calendar.getInstance();
        c.set(2020, Calendar.MAY, 11);
        long time = c.getTimeInMillis();
        order.setDeliveryDay(new Date(time));

        order.setContractId(1);

        orderManager.createPeriodicalOrder(order);

        if(order.getOrderId() < 0){
            System.out.println("Order wasnt created");
        } else {
            System.out.println("Order id:"+ order.getOrderId());
        }
    }
}
