package Suppliers.Presentation.Order;

import Suppliers.Presentation.Menu_Option;
import Suppliers.Service.OrderAndProductManagement;
import Suppliers.Service.SupplierManagment;
import Suppliers.Structs.Days;
import Suppliers.Structs.StructUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        Date deliveryDate;

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String dayinput = readString("Delivery day (dd/MM/yyyy)",reader);
            deliveryDate  = df.parse(dayinput);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
            return;
        } catch (IOException e){
            System.out.println("Error at reading");
            return;
        }

        if(orderAndProductManagement.updateOrderArrivalTime(orderId, deliveryDate)){
            System.out.println("Order was updated");
        } else {
            System.out.println("Order was not updated");
        }
    }
}
