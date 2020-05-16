package Suppliers.Presentation;

import Suppliers.Service.*;

import java.io.BufferedReader;
import java.io.IOException;
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



    public void apply(int supplierID)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        startReadingProducts(reader,supplierID);
    }

    public void apply() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int supId = -1;
        supId = readInt("Supplier ID", reader);
        if(supId == -1){
            return;
        }
        startReadingProducts(reader,supId);

    }

    private void startReadingProducts(BufferedReader reader, int supId) {
        int barcode;
        List<Integer> barcodes = orderAndProductManagement.getAllProductBarcodes();
        String supplierProduct;
        String name = null, manufacture = null, category = null, subCategoty = null, size = null, catalog_number=null, originalPriceString=null;
        SystemProduct systemProduct;
        boolean added = false;


        try {
            System.out.println("Please enter product's barcode:");
            barcode = readIntPos("Barcode", "Barcode need to be equal or bigger than 0 ", reader);
            if (barcode < 0) {
                return;
            }
            System.out.println("Enter supplier's details about this product:");

            name=readString("Product's name:",reader);
            if(name==null || name.length()<=0)
            {
                return;
            }
            catalog_number=readString("Supplier's catalog number:",reader);
            if(name==null || name.length()<=0)
            {
                return;
            }
            originalPriceString=readString("Product's main price:",reader);
            if(originalPriceString==null || originalPriceString.length()<=0)
            {
                return;
            }
            double originaPrice = Double.parseDouble(originalPriceString);
            if (originaPrice < 0) {
                System.out.println("Price need to be bigger than 0");
                return;
            }
            System.out.println("Please enter discounts information:");
            //System.out.println("Supplier product format : <catalog number> <original price> <discount per amount {amount:discount,amount:discount...}>");

//            supplierProduct = readString("Enter supplier product", reader);
//            if (supplierProduct == null) {
//                return;
//            }
//
//            String[] input = supplierProduct.split(" ");
//            if (input.length != 3) {
//                System.out.println("Invalid number of args");
//                return;
//            }



            Map<Integer, Double> discounts = new HashMap<>();
            while (getDiscount(reader,discounts));

//            String[] discountArr = input[2].substring(1, input[2].length() - 1).split(",");
//            if (!((discountArr.length == 1) && (discountArr[0].length() == 0))) {
//                for (String discount : discountArr) {
//                    String[] discountS = discount.split(":");
//                    if (discountS.length != 2) {
//                        System.out.println("Invalid format of discount");
//                        continue;
//                    }
//
//                    discounts.put(Integer.parseInt(discountS[0]), Double.parseDouble(discountS[1]));
//                }
//            }

            boolean newProduct;
            if (!barcodes.contains(barcode)) {
                System.out.println("This is a new product in the system enter the following info");

                manufacture = readString("Manufacture", reader);
                category = readString("Categoty", reader);
                subCategoty = readString("sub categoty", reader);
                size = readString("Product size", reader);
                newProduct = true;
            } else{
                newProduct = false;
            }
            if(newProduct)
            {
                systemProduct = new SystemProduct(barcode, name, manufacture, category, subCategoty, size);
                added=this.orderAndProductManagement.addProductToSystem(systemProduct);
            }
            ProductDiscountsDTO product = new ProductDiscountsDTO(barcode, discounts, originaPrice);
            added =added && supplierManagment.addProductToContract(supId,new SupplierProductDTO(barcode, catalog_number, originaPrice, product));

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

    public boolean getDiscount(BufferedReader reader, Map<Integer, Double> discounts) throws IOException {
        String answer=readString("Do you want to enter a discount? [y/n]",reader);
        if(answer==null || answer.length()<=0 || !answer.toUpperCase().equals("Y"))
        {
            return false;
        }
        else
        {
            String amount=readString("enter amount for discount: ",reader);
            String discount=readString("enter discount precentage: ",reader);
            if(amount==null || discount==null || amount.length()<=0 || discount.length()<=0)
            {
                return false;
            }
            else {
                int amountInt;
                double discountDoub;
                try {
                    amountInt = Integer.parseInt(amount);
                    discountDoub = Double.parseDouble(discount);
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
                discounts.put(amountInt, discountDoub);
                return true;
            }
        }
    }
}
