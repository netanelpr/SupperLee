package Tests;


import Sup_Inv.Suppliers.Structs.OrderStatus;
import Sup_Inv.Suppliers.Supplier.*;
import Sup_Inv.Suppliers.Supplier.Order.OrderManager;
import Sup_Inv.Suppliers.Supplier.Order.ProductInOrder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

class OrderTest {
    private SupplierSystem supplierSystem;
    private OrderManager orderManager;
    private int supplierID;
    private List<AddProduct> productList;
    private List<ProductInOrder> productInOrder;
    private int orderID;

    public OrderTest()
    {
        supplierSystem=SupplierSystem.getInstance();
        orderManager = OrderManager.getInstance();
        supplierID=supplierSystem.createSupplierCard("Actor","5A5", "City1","123-5","CASH","Actor","054","@com");
        productList= new LinkedList<AddProduct>();
        //TODO systemproduct
        productList.add(new AddProduct(
                5,"55",5.3,
                new ProductDiscounts(5,new HashMap<Integer, Double>(),5.3)));
        supplierSystem.addContractToSupplier(supplierID,"shalom",new LinkedList<>(),productList);
        productInOrder=new LinkedList<ProductInOrder>();
        productInOrder.add(new ProductInOrder(productList.get(0).barCode,10));
        orderID=supplierSystem.createNewOrder(supplierID,productInOrder,1).getValue();

    }

    @Test
    void setStatus() {
        OrderStatus status=supplierSystem.getOrderStatus(orderID);
        if(status==OrderStatus.Open)
        {
            orderManager.updateOrderStatus(orderID,OrderStatus.Close);
            Assert.assertEquals(supplierSystem.getOrderStatus(orderID),OrderStatus.Close);
        }
        else
        {
            orderManager.updateOrderStatus(orderID,OrderStatus.Open);
            Assert.assertEquals(supplierSystem.getOrderStatus(orderID),OrderStatus.Open);
        }
    }



    @Test
    void retrunProductsCatalogNumbers() {

        List<String> catalogNumbersLst=supplierSystem.getPurchaseHistory(supplierID);
        boolean checkCatalogNumbers=false;
        for (int i = 0; i < catalogNumbersLst.size(); i++) {
            if(catalogNumbersLst.get(i).equals("55"))
            {
                checkCatalogNumbers=true;
            }

        }

        Assert.assertTrue(checkCatalogNumbers);
    }
}