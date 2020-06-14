package Tests.Visual;

import Sup_Inv.Result.Result;
import Sup_Inv.Suppliers.Structs.Days;
import Sup_Inv.Suppliers.Structs.StructUtils;
import Sup_Inv.Suppliers.Supplier.Order.OrderManager;
import Sup_Inv.Suppliers.Supplier.Order.PeriodicalOrder;
import Sup_Inv.Suppliers.Supplier.Order.ProductInOrder;
import Sup_Inv.Suppliers.Supplier.SupplierSystem;

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
        for(ProductInOrder product : order.getProducts()){
            String productStr = String.format("barcode:%d\tcatalog number:%s\tamount: %d\tPrice per unit: %f", product.getBarcode(),
                    product.getProductCatalogNumber(), product.getAmount(), product.getPricePerUnit());
            productsStr = String.format("%s\n%s", productsStr, productStr);
        }

        System.out.println(orderDetails + "\n" + productsStr);
    }

    //TODO delete it manualy
    public static void addPeriodicalOrder(){
        OrderManager orderManager = OrderManager.getInstance();

        List<Days> days = new LinkedList<>();
        //days.add(StructUtils.getDayWithInt(1));
        days.add(StructUtils.getDayWithInt(6));

        List<ProductInOrder> products = new LinkedList<>();
        products.add(new ProductInOrder(11,10,"c1",10));
        products.add(new ProductInOrder(88,150,"c2",12));
        PeriodicalOrder order = PeriodicalOrder.CreatePeriodicalOrder(-1, products, days, 1, 1, new Date());

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

    public static void addPeriodicalOrder2(){
        SupplierSystem supplierSystem = SupplierSystem.getInstance();

        List<Days> days = new LinkedList<>();
        //days.add(StructUtils.getDayWithInt(1));
        days.add(StructUtils.getDayWithInt(6));

        List<ProductInOrder> products = new LinkedList<>();
        products.add(new ProductInOrder(222,10));
        products.add(new ProductInOrder(88,150));
        Result<Integer> res = supplierSystem.createPeriodicalOrder(products, days, 3,1);

        if(res.isOk()){
            System.out.println("Order id:"+ res.getValue());
        } else {
            System.out.println(res.getMessage());
        }
    }
}
