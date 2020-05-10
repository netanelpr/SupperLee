package Suppliers.Presentation;

import Suppliers.Service.OrderAndProductManagement;
import Suppliers.Service.SupplierManagment;
import Suppliers.Structs.Days;
import Suppliers.Structs.StructUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class UpdateOrderArrivalDay extends Menu_Option {


    private OrderAndProductManagement orderAndProductManagement;

    public UpdateOrderArrivalDay(OrderAndProductManagement orderAndProductManagement) {
        this.orderAndProductManagement = orderAndProductManagement;
    }


    @Override
    public void apply() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int orderId = readInt("Order ID", reader);
        String daysinput = readString("Enter days",reader);

        String[] dayStr = {daysinput};
        List<Days> days = StructUtils.getDaysList(dayStr);
        if(days == null){
            System.out.println("Invalid day");
            return;
        }
        Days day = days.get(0);

        if(orderAndProductManagement.updateOrderArrivalTime(orderId, day)){
            System.out.println("Order was updated");
        } else {
            System.out.println("Order was not updated");
        }
    }
}
