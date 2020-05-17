package Suppliers.Presentation;

import Suppliers.Service.OrderAndProductManagement;
import Suppliers.Service.OrderShipDetails;
import Suppliers.Service.SupplierDetailsDTO;
import Suppliers.Structs.OrderStatus;
import Suppliers.Structs.StructUtils;
import Suppliers.Supplier.Order.AllDetailsOfProductInOrder;
import Suppliers.Supplier.Order.AllOrderDetails;

import java.io.BufferedReader;
import java.io.IOException;
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
        String info = String.format("Order ID: %d\tSupplier: %s\tAddress: %s\n" +
                "Contact name: %s\tPhone number: %s\tEmail: %s\n" +
                "shop number: %d\tDelievry date: %s\n",
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
