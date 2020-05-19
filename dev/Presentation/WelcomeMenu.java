package Presentation;

import DataAccess.SupInvDBConn;
import Inventory.View.InvService;
import Suppliers.Presentation.MainMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class WelcomeMenu {

    MainMenu supplierMenu;
    InvService inventoryMenu;
    Boolean terminate;

    public WelcomeMenu(){
        supplierMenu = new MainMenu();
        inventoryMenu = InvService.getInstance();
    }

    public void start(){
        terminate = false;
        String option = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        inventoryMenu.loadDB();

        while(!terminate){
            System.out.println("Please choose one of the following options:");
            System.out.println("[s] Supplier menu\n[i] Inventory menu\n[c] Close");
            System.out.print("Option: ");

            try {
                option = reader.readLine();
            } catch (IOException e) {
                System.out.println("Invalid option");
            }
            catch (Exception e)
            {
                System.out.println("Something went wrong");
            }

            if(option.equals("s") || option.equals("S")){
                supplierMenu.apply();
            } else if(option.equals("i") || option.equals("I")){
                terminate = inventoryMenu.mainLoop();
            } else if(option.equals("c") || option.equals("C")){
                break;
            } else {
                System.out.println("Invalid option");
            }
        }
        SupInvDBConn.closeConn();
    }
}
