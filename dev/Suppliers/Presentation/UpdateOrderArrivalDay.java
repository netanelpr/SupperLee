package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;
import Suppliers.Structs.Days;
import Suppliers.Structs.StructUtils;

import java.util.List;

public class UpdateOrderArrivalDay implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public UpdateOrderArrivalDay(SupplierManagment supplierManagment) {
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
            orderId = Integer.parseInt(argv[0]);

            String[] dayStr = {argv[1]};
            List<Days> days = StructUtils.getDaysList(dayStr);
            if(days == null){
                System.out.println("Invalid day");
                return;
            }
            Days day = days.get(0);

            if(supplierManagment.updateOrderArrivalTime(orderId, day)){
                System.out.println("Order was updated");
            } else {
                System.out.println("Order was not updated");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid order ID");
        }
    }
}
