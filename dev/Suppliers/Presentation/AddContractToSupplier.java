package Suppliers.Presentation;

import Suppliers.Service.*;
import Suppliers.Structs.Days;
import Suppliers.Structs.StructUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddContractToSupplier extends Menu_Option {


    private SupplierManagment supplierManagment;
    private OrderAndProductManagement orderAndProductManagement;

    public AddContractToSupplier(SupplierManagment supplierManagment, OrderAndProductManagement orderAndProductManagement) {
        this.supplierManagment = supplierManagment;
        this.orderAndProductManagement = orderAndProductManagement;
    }


    @Override
    public void apply() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int supId = readInt("Supplier ID", reader);
        int barcode = -1;
        List<Integer> barcodes = orderAndProductManagement.getAllProductBarcodes();
        String supplierProduct;
        String name = null, manufacture = null, category = null, subCategoty = null, size = null;
        SystemProduct systemProduct;

        boolean newProduct;

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

            System.out.println("Supplier product format : <catalog number> <original price> <discount per amount {amount:discount,amount:discount...}>");
            while(products.size() != numberOfProducts){
                try {
                    barcode = readIntPos("Barcode", "Barcode need to be equal or bigger than 0 ", reader);
                    if (barcode < 0) {
                        continue;
                    }

                    supplierProduct = readString("Enter supplier product", reader);
                    if (supplierProduct == null) {
                        continue;
                    }

                    String[] input = supplierProduct.split(" ");
                    if (input.length != 3) {
                        System.out.println("Invalid number of args");
                        continue;
                    }

                    double originaPrice = Double.parseDouble(input[1]);
                    if (originaPrice < 0) {
                        System.out.println("Price need to be bigger than 0");
                        continue;
                    }

                    Map<Integer, Double> discounts = new HashMap<>();
                    String[] discountArr = input[2].substring(1, input[2].length() - 1).split(",");
                    if (!((discountArr.length == 1) && (discountArr[0].length() == 0))) {
                        for (String discount : discountArr) {
                            String[] discountS = discount.split(":");
                            if (discountS.length != 2) {
                                System.out.println("Invalid format of discount");
                                continue;
                            }

                            discounts.put(Integer.parseInt(discountS[0]), Double.parseDouble(discountS[1]));
                        }
                    }

                    if (!barcodes.contains(barcode)) {
                        System.out.println("This is a new product in the system enter the following info");
                        name = readString("Name", reader);
                        manufacture = readString("Manufacture", reader);
                        category = readString("Categoty", reader);
                        subCategoty = readString("sub categoty", reader);
                        size = readString("Product size", reader);
                        newProduct = true;
                    } else{
                        newProduct = false;
                    }

                    ProductDiscountsDTO product = new ProductDiscountsDTO(barcode, discounts, originaPrice);
                    if(newProduct){
                        systemProduct = new SystemProduct(barcode, name, manufacture, category, subCategoty, size);
                        products.add(new SupplierProductDTO(barcode, input[0], originaPrice, product, systemProduct));
                    }

                    products.add(new SupplierProductDTO(barcode, input[0], originaPrice, product));

                } catch (IOException e) {
                    System.out.println("Error at reading");
                    continue;
                }


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
        } catch (IOException e){
            System.out.println("Error at reading");
        }
    }
}
