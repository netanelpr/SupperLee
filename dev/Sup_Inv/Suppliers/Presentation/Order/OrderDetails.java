package Sup_Inv.Suppliers.Presentation.Order;

import Sup_Inv.Suppliers.Presentation.Menu_Option;
import Sup_Inv.Suppliers.Service.OrderAndProductManagement;
import Sup_Inv.Suppliers.Service.OrderShipDetails;
import Sup_Inv.Suppliers.Service.SupplierDetailsDTO;
import Sup_Inv.Suppliers.Supplier.Order.AllDetailsOfProductInOrder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OrderDetails extends Menu_Option {


    private OrderAndProductManagement orderAndProductManagement;

    public OrderDetails(OrderAndProductManagement orderAndProductManagement) {
        this.orderAndProductManagement = orderAndProductManagement;
    }

    @Override
    public void apply() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int orderId = readInt("Order ID", reader);

        OrderShipDetails orderShipDetails = orderAndProductManagement.orderDetails(orderId);
        if(orderShipDetails == null){
            System.out.println("The order id doesnt exist");
            return;
        }

        SupplierDetailsDTO supplierDetailsDTO = orderShipDetails.supplier;

        if(orderShipDetails.periodicalOrderData != null){
            String periodicalInfo = String.format("This is periodical order\nDelivery day: %s\nWeek period: %d",
                    orderShipDetails.periodicalOrderData.days.toString(), orderShipDetails.periodicalOrderData.weekP);
            System.out.println(periodicalInfo);
        }

        String info = String.format("Order ID: %d \tSupplier: %s \tAddress: %s\n" +
                "Contact name: %s \tPhone number: %s \tEmail: %s\n" +
                "shop number: %d \tDelievry date: %s\n",
                orderShipDetails.orderId, supplierDetailsDTO.supplierName, supplierDetailsDTO.address,
                supplierDetailsDTO.contactName, supplierDetailsDTO.phoneNumber, supplierDetailsDTO.email,
                orderShipDetails.shopNumber, orderShipDetails.deliveryDate);

        System.out.println(info);
        System.out.println("Products\n");
        for(AllDetailsOfProductInOrder details: orderShipDetails.details){
            String productInfo = String.format("Name: %s\tBarcode: %d\tCatalog number: %s\tAmmount: %d\tOriginal price: %f\n" +
                            "\tDiscount price: %f\tDiscount: %f\t",
                    details.name, details.getBarcode(), details.getProductCatalogNumber(), details.getAmount(),
                    details.getOriginalPrice(), details.getPricePerUnit(), details.getDiscount());
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(productInfo);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }
}
