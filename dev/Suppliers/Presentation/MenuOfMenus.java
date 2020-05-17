package Suppliers.Presentation;

import Suppliers.Service.OrderAndProductCtrl;
import Suppliers.Service.OrderAndProductManagement;
import Suppliers.Service.SupplierCtrl;
import Suppliers.Service.SupplierManagment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class MenuOfMenus extends Menu_Option {
    protected SupplierManagment supplierManagment;
    protected OrderAndProductManagement orderAndProductManagement;

    protected Map<String, Menu_Option> optionMap;
    protected List<String> optionToIndex;

    public MenuOfMenus(SupplierManagment supplierManagment, OrderAndProductManagement orderAndProductManagement){
        this.supplierManagment = supplierManagment;
        this.orderAndProductManagement = orderAndProductManagement;

        optionMap = new HashMap<>();
        optionToIndex = new LinkedList<>();

        createMenuMap();
        addMenuOption("Return to menu", null);
    }

    protected void addMenuOption(String menuName, Menu_Option menu){
        optionMap.put(menuName, menu);
        optionToIndex.add(menuName);
    }

    abstract protected void createMenuMap();

    public void print_menu(){
        int index = 1;

        System.out.println("\n+++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Choose from the options");
        for(String menuString : optionToIndex){
            System.out.println(String.format("%d) %s",index, menuString));
            index = index + 1;
        }
    }

    protected Menu_Option getMenuWithIndex(int index){
        String menuName = optionToIndex.get(index);
        return optionMap.getOrDefault(menuName, null);
    }

    public void apply() {
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
            System.out.println();

            if(index > 0 & index <= optionToIndex.size()) {
                if (index == optionToIndex.size()) {
                    return;
                }

                Menu_Option option = getMenuWithIndex(index-1);
                if (option == null) {
                    System.out.println("Invalid function");
                    continue;
                }

                option.apply();
            } else {
                System.out.println("Invalid menu index");
            }
        }

    }
}
