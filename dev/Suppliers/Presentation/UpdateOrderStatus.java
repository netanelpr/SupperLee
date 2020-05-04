package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;
import Suppliers.Structs.OrderStatus;
import Suppliers.Structs.StructUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UpdateOrderStatus implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public UpdateOrderStatus(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
        if(argv.length != 2){
            System.out.println("Invalid number of args");
            return;
        }

        int orderId = -1;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            orderId = Integer.parseInt(argv[0]);

            OrderStatus status = StructUtils.getOrderStatus(argv[1]);
            if(status == null){
                System.out.println("Invalid order status");
                return;
            }

            if(supplierManagment.updateOrderStatus(orderId, status)){
                System.out.println("Order was updated");
            } else {
                System.out.println("Order was not updated");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid order id");
        }
    }
}
