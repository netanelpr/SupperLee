package Tests;


import Suppliers.Structs.OrderStatus;
import Suppliers.Supplier.*;
import Suppliers.Supplier.Order.ProductInOrder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

class OrderTest {
    private SupplierSystem supplierSystem;
    private int supplierID;
    private List<AddProduct> productList;
    private List<ProductInOrder> productInOrder;
    private int orderID;

    public OrderTest()
    {
        supplierSystem=SupplierSystem.getInstance();
        supplierID=supplierSystem.createSupplierCard("Actor","5A5", "City1","123-5","CASH","Actor","054","@com");
        productList= new LinkedList<AddProduct>();
        productList.add(new AddProduct(
                5,"55",5.3,
                new ProductDiscounts(5,new HashMap<Integer, Double>(),5.3),"osem","bamba"
        ));
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