package Suppliers.Presentation;

import Suppliers.Service.SupplierProductDTO;
import Suppliers.Service.ProductDiscountsDTO;
import Suppliers.Service.SupplierManagment;
import Suppliers.Structs.Days;
import Suppliers.Structs.StructUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddContractToSupplier extends Menu_Option {


    private SupplierManagment supplierManagment;

    public AddContractToSupplier(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int supId = readInt("Supplier ID", reader);

        try {
            System.out.print("Enter contract info : ");
            String contractInfo = reader.readLine();

            System.out.print("Enter supply days : ");
            String daysStr = reader.readLine();
            String[] dayArr = daysStr.toUpperCase().split(" ");
            List<Days> days = StructUtils.getDaysList(dayArr);

            System.out.print("Enter number of product you want to enter : ");
            int numberOfProducts = Integer.parseInt(reader.readLine());
            List<SupplierProductDTO> products = new LinkedList<>();
            System.out.println("Product format : <product Id> <catalog number> <original price>\n\t" +
                    "<name> <manufacture> <discount per amount {amount:discount,amount:discount...}>");
            for(int i=0; i < numberOfProducts; i=i+1){
                String[] input = reader.readLine().split(" ");

                if(input.length != 6){
                    System.out.println("Invalid number of args");
                    continue;
                }

                Map<Integer, Double> discounts = new HashMap<>();
                String[] discountArr = input[5].substring(1, input[5].length()-1).split(",");
                if(!((discountArr.length == 1) && (discountArr[0].length() == 0))) {
                    for (String discount : discountArr) {
                        String[] discountS = discount.split(":");
                        if (discountS.length != 2) {
                            System.out.println("Invalid format of discount");
                            continue;
                        }

                        discounts.put(Integer.parseInt(discountS[0]), Double.parseDouble(discountS[1]));
                    }
                }

                ProductDiscountsDTO product = new ProductDiscountsDTO(Integer.parseInt(input[0]), discounts,  Double.parseDouble(input[2]));
                products.add(new SupplierProductDTO(
                        Integer.parseInt(input[0]),
                        input[1],
                        Double.parseDouble(input[2]),
                        product,
                        input[4], input[3]));

            }

            List<Integer> productIdError = supplierManagment.addContractToSupplier(supId, contractInfo, days, products);
            if(productIdError == null){
                System.out.println("Already has a contract");
            } else {
                if(productIdError.size() == 0){
                    System.out.println("The contract was added");
                } else {
                    System.out.println("Product that wasnt added due an error : "+ productIdError.toString());
                }
            }

        } catch (NumberFormatException e){
            System.out.println("Invalid args");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error reading input");
        }
    }
}
