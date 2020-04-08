package Presentation;

import Service.SupplierCtrl;
import Service.SupplierManagment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        map.put("getPaymentOptions", new PaymentOptions(supplierManagment));
        map.put("getAllSuppliers", new GetAllSuppliers(supplierManagment));
        map.put("addContactInfoToSupplier", new AddContactInfoToSupplier(supplierManagment));
        map.put("addContractToSupplier", new AddContractToSupplier(supplierManagment));
        map.put("addProductToSupplier", new AddProductToSupplier(supplierManagment));
        map.put("discountReport", new GetAmountDiscountReport(supplierManagment));
        map.put("createNewOrder", new CreateNewOrder(supplierManagment));
        map.put("updateOrderArrivalDay", new UpdateOrderArrivalDay(supplierManagment));
        map.put("updateOrderStatus", new UpdateOrderStatus(supplierManagment));
        map.put("getAllSupplierProducts", new GetAllSuppliersProducts(supplierManagment));
        map.put("getPruchaseHistoryFromSupplier", new PurchaseHistoryFromSupplier(supplierManagment));

        return map;

    }

    public void startMenu() throws IOException {
        String[] argv;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            String input = reader.readLine();
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
