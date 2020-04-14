package Presentation;

import Service.AddProductInfoDTO;
import Service.ProductDiscountsDTO;
import Service.SupplierCtrl;
import Service.SupplierManagment;
import Structs.Days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MainMenu {

    private SupplierManagment supplierManagment;
    private Map<String, Menu_Option> optionMap;

    public MainMenu(){
        supplierManagment = new SupplierCtrl();
        optionMap = createMenuMap();
    }

    private Map<String, Menu_Option> createMenuMap() {
        Map<String, Menu_Option> map = new HashMap<>();

        map.put("createSupplierCard", new HandleSupplierCard(supplierManagment));

        Menu_Option createSup = map.getOrDefault("createSupplierCard", null);
        map.put("init", new InitWithData(supplierManagment, createSup));

        map.put("getPaymentOptions", new PaymentOptions(supplierManagment));
        map.put("updatePaymentOptions", new updatePaymentOptions(supplierManagment));

        map.put("getAllSuppliers", new GetAllSuppliers(supplierManagment));

        map.put("addContactInfoToSupplier", new AddContactInfoToSupplier(supplierManagment));
        map.put("addOrReplaceContractToSupplier", new AddContractToSupplier(supplierManagment));
        map.put("addProductToSupplier", new AddProductToSupplier(supplierManagment));
        map.put("discountReport", new GetAmountDiscountReport(supplierManagment));

        map.put("getAllSupplierBarcode", new GetAllSuppliersProducts(supplierManagment));
        map.put("getAllSupplierProductsDetalis", new GetAllSuppliersProductsDetalis(supplierManagment));

        map.put("createNewOrder", new CreateNewOrder(supplierManagment));
        map.put("updateOrderArrivalDay", new UpdateOrderArrivalDay(supplierManagment));
        map.put("updateOrderStatus", new UpdateOrderStatus(supplierManagment));

        map.put("getPruchaseHistoryFromSupplier", new PurchaseHistoryFromSupplier(supplierManagment));

        return map;

    }

    public void startMenu() {
        String[] argv;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            String input;
            try {
                input = reader.readLine();
            } catch (IOException e){
                System.out.println("Error reading input");
                continue;
            }
            argv = input.split(" ");

            Menu_Option option = optionMap.getOrDefault(argv[0], null);
            if(option == null){
                System.out.println("Invalid function");
                continue;
            }
            option.apply(Arrays.copyOfRange(argv, 1, argv.length));
        }
    }
}
