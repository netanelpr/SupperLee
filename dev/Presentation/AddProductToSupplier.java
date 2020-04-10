package Presentation;

import Service.AddProductInfoDTO;
import Service.ProductDiscountsDTO;
import Service.SupplierManagment;
import Structs.Days;
import Structs.StructUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddProductToSupplier implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public AddProductToSupplier(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
        if(argv.length != 1){
            System.out.println("Invalid number of args");
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int supId = -1;
        try {
            supId = Integer.parseInt(argv[0]);

            System.out.println("Product format : <product Id> <catalog number> <original price>\n\t" +
                    "<name> <manufacture> <discount per amount {amount:discount,amount:discount...}>");
            String[] input = reader.readLine().split(" ");

            if(input.length != 6){
                System.out.println("Invalid args");
                return;
            }

            Map<Integer, Double> discounts = new HashMap<>();
            String[] discountArr = input[5].substring(1, input[5].length()-1).split(",");
            for(String discount: discountArr){
                String[] discountS = discount.split(":");
                if(discountS.length != 2){
                    System.out.println("Invalid format of discount");
                    continue;
                }

                discounts.put(Integer.parseInt(discountS[0]), Double.parseDouble(discountS[1]));
            }

            ProductDiscountsDTO product = new ProductDiscountsDTO(Integer.parseInt(input[0]), discounts,  Double.parseDouble(input[2]));
            boolean added = supplierManagment.addProductToContract(supId,
                                            new AddProductInfoDTO(
                                            Integer.parseInt(input[0]),
                                            input[1],
                                            Double.parseDouble(input[2]), product,
                                            input[4], input[3]));

            if(added){
                System.out.println("The product has been added");
            } else {
                System.out.println("The product has not been added");
            }

        } catch (NumberFormatException e){
            System.out.println("Invalid args");
        } catch (Exception e){
            System.out.println("Error reading input");
        }
    }
}
