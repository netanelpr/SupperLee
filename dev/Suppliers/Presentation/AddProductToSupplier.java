package Suppliers.Presentation;

import Suppliers.Service.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProductToSupplier extends Menu_Option {

    private SupplierManagment supplierManagment;
    private final OrderAndProductManagement orderAndProductManagement;

    public AddProductToSupplier(SupplierManagment supplierManagment, OrderAndProductManagement orderAndProductManagement) {
        this.supplierManagment = supplierManagment;
        this.orderAndProductManagement = orderAndProductManagement;
    }


    @Override
    public void apply() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int supId = -1, barcode;
        List<Integer> barcodes = orderAndProductManagement.getAllProductBarcodes();
        String supplierProduct;
        String name = null, manufacture = null, category = null, subCategoty = null, size = null;
        SystemProduct systemProduct;
        boolean added = false;

        supId = readInt("Supplier ID", reader);
        if(supId == -1){
            return;
        }
        try {
            barcode = readIntPos("Barcode", "Barcode need to be equal or bigger than 0 ", reader);
            if (barcode < 0) {
                return;
            }
            System.out.println("Supplier product format : <catalog number> <original price> <discount per amount {amount:discount,amount:discount...}>");
            supplierProduct = readString("Enter supplier product", reader);
            if (supplierProduct == null) {
                return;
            }

            String[] input = supplierProduct.split(" ");
            if (input.length != 3) {
                System.out.println("Invalid number of args");
                return;
            }

            double originaPrice = Double.parseDouble(input[1]);
            if (originaPrice < 0) {
                System.out.println("Price need to be bigger than 0");
                return;
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

            boolean newProduct;
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
                added = supplierManagment.addProductToContract(supId,new SupplierProductDTO(barcode, input[0], originaPrice, product, systemProduct));
            }

            added = supplierManagment.addProductToContract(supId,new SupplierProductDTO(barcode, input[0], originaPrice, product));

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
