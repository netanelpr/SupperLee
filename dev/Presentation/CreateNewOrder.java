package Presentation;

import Service.ProductInOrderDTO;
import Service.SupplierManagment;
import Structs.Days;
import Structs.StructUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class CreateNewOrder implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public CreateNewOrder(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
        if(argv.length != 1){
            System.out.println("Invalid number of args");
            return;
        }

        int supId = -1;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            supId = Integer.parseInt(argv[0]);

            List<ProductInOrderDTO> products = new LinkedList<>();
            System.out.print("Enter the numbers of product you want the order :");
            int numberOfProducts = Integer.parseInt(reader.readLine());

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

            System.out.print("Enter the delivery day :");
            String[] dayStr = {reader.readLine()};
            List<Days> days = StructUtils.getDaysList(dayStr);
            if(days == null){
                System.out.println("Invalid day");
                return;
            }
            Days day = days.get(0);

            int orderId = supplierManagment.createNewOrder(supId, products, day);
            if(orderId < 0){
                System.out.println("Order was not created");
            } else {
                System.out.println("Order id : "+ orderId);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid args");
        } catch (Exception e){
            System.out.println("Error reading input");
        }
    }
}
