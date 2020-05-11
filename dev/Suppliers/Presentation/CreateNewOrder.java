package Suppliers.Presentation;

import Result.Result;
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
                return;
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

            Result<Integer> res = orderAndProductManagement.createNewSupplierOrder(supId, products, shopNumber);
            if(res.isOk()){
                System.out.println("Order id : "+ res.getValue());
            } else {
                System.out.println(res.getMessage());
            }

        } catch (Exception e){
            System.out.println("Error reading input");
        }
    }
}
