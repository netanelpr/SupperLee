package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;
import Suppliers.Structs.OrderStatus;
import Suppliers.Structs.StructUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UpdateOrderStatus extends Menu_Option {


    private SupplierManagment supplierManagment;

    public UpdateOrderStatus(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
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

        if(supplierManagment.updateOrderStatus(orderId, status)){
            System.out.println("Order was updated");
        } else {
            System.out.println("Order was not updated");
        }
    }
}
