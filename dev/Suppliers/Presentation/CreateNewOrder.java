package Suppliers.Presentation;

import Suppliers.Service.OrderAndProductManagement;
import Suppliers.Service.ProductInOrderDTO;
import Suppliers.Service.SupplierManagment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class CreateNewOrder extends Menu_Option {


    private OrderAndProductManagement orderAndProductManagement;

    public CreateNewOrder(OrderAndProductManagement orderAndProductManagement) {
        this.orderAndProductManagement = orderAndProductManagement;
    }

    @Override
    public void apply() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int supId = readInt("Supplier ID", reader);
        int shopNumber;

        try {
            List<ProductInOrderDTO> products = new LinkedList<>();

            shopNumber = readInt("Enter the shop number", reader);
            if(shopNumber < 0){
                System.out.println("Shop number need to be bigger than -1");
            }

            int numberOfProducts = readInt("Enter the numbers of product you want the order", reader);

            System.out.println("Enter product ID and amount\n\t format <productID> <amount>");
            for(int i=0; i < numberOfProducts; i=i+1){
                String[] productAndAmount = reader.readLine().split(" ");

                if(productAndAmount.length != 2){
                    System.out.println("Invalid product amount format");
                    continue;
                }

                products.add(new ProductInOrderDTO(
                        Integer.parseInt(productAndAmount[0]),
                        Integer.parseInt(productAndAmount[1])));
            }

            //TODO edit to result
            int orderId = orderAndProductManagement.createNewSupplierOrder(supId, products, shopNumber).getValue();
            if(orderId < 0){
                System.out.println("Order was not created");
            } else {
                System.out.println("Order id : "+ orderId);
            }

        } catch (Exception e){
            System.out.println("Error reading input");
        }
    }
}
