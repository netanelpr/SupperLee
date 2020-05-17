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

            System.out.print("Enter supply days : {days of the week (Sunday...) starting with capital letter, suppurated with coma (,) }\n");
            String daysStr = reader.readLine();
            String[] dayArr = daysStr.toUpperCase().split(",");
            List<Days> days = StructUtils.getDaysList(dayArr);
            List<SupplierProductDTO> supplierProductDTOS=startReadingProducts(reader,supId);
            if(supplierProductDTOS==null)
            {
                System.out.println("problem detected adding the products");
                return;
            }
            this.supplierManagment.addContractToSupplier(supId,contractInfo,days,supplierProductDTOS);
            //TODO get the return value from the top and print message


        }
        catch (IOException ioe)
        {
            System.out.println("problem detected");
        }


    }

    private List<SupplierProductDTO> startReadingProducts(BufferedReader reader, int supId) {
        System.out.println("Starting the product adding process...");
        System.out.print("Enter number of product you want to enter : ");
        try {
            int numberOfProducts = Integer.parseInt(reader.readLine());
            List<SupplierProductDTO> products = new LinkedList<>();
            for (int i = 0; i < numberOfProducts; i++) {

                int barcode;
                List<Integer> barcodes = orderAndProductManagement.getAllProductBarcodes();
                String supplierProduct;
                String name = null, manufacture = null, category = null, subCategoty = null, size = null, catalog_number = null, originalPriceString = null;
                SystemProduct systemProduct;

                try {
                    System.out.println("Please enter product's barcode:");
                    barcode = readIntPos("Barcode", "Barcode need to be equal or bigger than 0 ", reader);
                    if (barcode < 0) {
                        return null;
                    }
                    System.out.println("Enter supplier's details about this product:");

                    //TODO why the name is here?
                    name = readString("Product's name:", reader);
                    if (name == null || name.length() <= 0) {
                        return null;
                    }
                    catalog_number = readString("Supplier's catalog number:", reader);
                    if (name == null || name.length() <= 0) {
                        return null;
                    }
                    originalPriceString = readString("Product's main price:", reader);
                    if (originalPriceString == null || originalPriceString.length() <= 0) {
                        return null;
                    }
                    double originaPrice = Double.parseDouble(originalPriceString);
                    if (originaPrice < 0) {
                        System.out.println("Price need to be bigger than 0");
                        return null;
                    }
                    System.out.println("Please enter discounts information:");


                    Map<Integer, Double> discounts = new HashMap<>();
                    while (getDiscount(reader, discounts)) ;


                    boolean newProduct;
                    if (!barcodes.contains(barcode)) {
                        System.out.println("This is a new product in the system enter the following info");

                        manufacture = readString("Manufacture", reader);
                        category = readString("Categoty", reader);
                        subCategoty = readString("sub categoty", reader);
                        size = readString("Product size", reader);
                        newProduct = true;
                    } else {
                        newProduct = false;
                    }

                    ProductDiscountsDTO productDiscounts = new ProductDiscountsDTO(barcode, discounts, originaPrice);

                    if (newProduct) {
                        systemProduct = new SystemProduct(barcode, name, manufacture, category, subCategoty, size);
                        products.add(new SupplierProductDTO(barcode, catalog_number, originaPrice, productDiscounts, systemProduct));

                    } else {
                        products.add(new SupplierProductDTO(barcode, catalog_number, originaPrice, productDiscounts));
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid args");
                    return null;
                } catch (Exception e) {
                    System.out.println("Error reading input");return null;
                }
            }
            return products;
        }
        catch (IOException ioe) {
            System.out.println("problem has detected");
        }
        return null;
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
