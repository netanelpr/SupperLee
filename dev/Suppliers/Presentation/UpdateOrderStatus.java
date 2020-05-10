package Suppliers.Presentation;

import Suppliers.Service.OrderAndProductManagement;
import Suppliers.Service.SupplierManagment;
import Suppliers.Structs.OrderStatus;
import Suppliers.Structs.StructUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UpdateOrderStatus extends Menu_Option {


    private OrderAndProductManagement orderAndProductManagement;

    public UpdateOrderStatus(OrderAndProductManagement orderAndProductManagement) {
        this.orderAndProductManagement = orderAndProductManagement;
    }

    @Override
    public void apply() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int orderId = readInt("Order ID", reader);
        String statusStr = readString("Enter order status", reader);

        if(statusStr == null){
            return;
        }

        OrderStatus status = StructUtils.getOrderStatus(statusStr);
        if(status == null){
            System.out.println("Invalid order status");
            return;
        }

        if(orderAndProductManagement.updateOrderStatus(orderId, status)){
            System.out.println("Order was updated");
        } else {
            System.out.println("Order was not updated");
        }
    }
}
