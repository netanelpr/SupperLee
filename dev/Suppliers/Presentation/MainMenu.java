package Suppliers.Presentation;

import Suppliers.Service.SupplierCtrl;
import Suppliers.Service.SupplierManagment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MainMenu {

    private SupplierManagment supplierManagment;
    private Map<String, Menu_Option> optionMap;
    private List<String> optionToIndex;

    public MainMenu(){
        supplierManagment = new SupplierCtrl();
        optionMap = new HashMap<>();
        optionToIndex = new LinkedList<>();

        createMenuMap();
    }

    private void addMenuOption(String menuName, Menu_Option menu){
        optionMap.put(menuName, menu);
        optionToIndex.add(menuName);
    }

    private void createMenuMap() {

        addMenuOption("Init", new InitWithData(supplierManagment));
        addMenuOption("Create supplier card", new HandleSupplierCard(supplierManagment));

        addMenuOption("Get payment options", new PaymentOptions(supplierManagment));
        addMenuOption("Update payment options", new updatePaymentOptions(supplierManagment));

        addMenuOption("Get all suppliers", new GetAllSuppliers(supplierManagment));

        addMenuOption("Add contact info to supplier", new AddContactInfoToSupplier(supplierManagment));
        addMenuOption("Add contract to supplier", new AddContractToSupplier(supplierManagment));
        addMenuOption("Add product to supplier", new AddProductToSupplier(supplierManagment));
        addMenuOption("Discount report", new GetAmountDiscountReport(supplierManagment));

        addMenuOption("Get all supplier barcode", new GetAllSuppliersProducts(supplierManagment));
        addMenuOption("Get all supplier products detalis", new GetAllSuppliersProductsDetalis(supplierManagment));

        addMenuOption("Create new order", new CreateNewOrder(supplierManagment));
        addMenuOption("Update order arrival day", new UpdateOrderArrivalDay(supplierManagment));
        addMenuOption("Update order status", new UpdateOrderStatus(supplierManagment));

        addMenuOption("Get purchase history from supplier", new PurchaseHistoryFromSupplier(supplierManagment));

        addMenuOption("Return to menu", null);

    }

    public void print_menu(){
        int index = 0;

        System.out.println("+++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Choose from the options");
        for(String menuString : optionToIndex){
            System.out.println(String.format("%d) %s",index, menuString));
            index = index + 1;
        }
    }

    public Menu_Option getMenuWithIndex(int index){
        String menuName = optionToIndex.get(index);
        return optionMap.getOrDefault(menuName, null);
    }

    public void startMenu() {
        String[] argv;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            String input;
            int index = -1;

            print_menu();
            System.out.print("Option: ");
            try {
                input = reader.readLine();
                index = Integer.parseInt(input);
            } catch (IOException e){
                System.out.println("Error reading input");
                continue;
            } catch (NumberFormatException e){
                System.out.println("Not an menu option");
                continue;
            }
            System.out.print("\n");

            if(index > -1 & index < optionToIndex.size()) {
                if (index == optionToIndex.size() - 1) {
                    return;
                }

                Menu_Option option = getMenuWithIndex(index);
                if (option == null) {
                    System.out.println("Invalid function");
                    continue;
                }

                option.apply();
            } else {
                System.out.println("Invalid menu index11");
            }

        }
    }
}
