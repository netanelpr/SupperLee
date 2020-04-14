package Tests;


import Structs.OrderStatus;
import Supplier.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private SupplierSystem supplierSystem;
    private int supplierID;
    private List<AddProduct> productList;
    private List<ProductInOrder> productInOrder;
    private int orderID;

    public OrderTest()
    {
        supplierSystem=new SupplierSystem();
        supplierID=supplierSystem.createSupplierCard("Netanel","5A5","123-5","CASH","Netanel","054","@com");
        productList= new LinkedList<AddProduct>();
        productList.add(new AddProduct(
                5,"55",5.3,
                new ProductDiscounts(5,new HashMap<Integer, Double>(),5.3),"osem","bamba"
        ));
        supplierSystem.addContractToSupplier(supplierID,"shalom",new LinkedList<>(),productList);
        productInOrder=new LinkedList<ProductInOrder>();
        productInOrder.add(new ProductInOrder(productList.get(0).barCode,10));
        orderID=supplierSystem.createNewOrder(supplierID,productInOrder);

    }

    @Test
    void setStatus() {
        OrderStatus status=supplierSystem.getOrderStatus(orderID);
        if(status==OrderStatus.Open)
        {
            supplierSystem.updateOrderStatus(orderID,OrderStatus.Close);
            Assert.assertEquals(supplierSystem.getOrderStatus(orderID),OrderStatus.Close);
        }
        else
        {
            supplierSystem.updateOrderStatus(orderID,OrderStatus.Open);
            Assert.assertEquals(supplierSystem.getOrderStatus(orderID),OrderStatus.Open);
        }
    }

    @Test
    void updateDeliveryDay() {

    }

    @Test
    void retrunProductsCatalogNumbers() {
    }
}