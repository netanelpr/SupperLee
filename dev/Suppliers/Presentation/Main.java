package Suppliers.Presentation;

import Suppliers.DataAccess.ProductMapper;
import Suppliers.Service.OrderAndProductCtrl;
import Suppliers.Service.SystemProduct;
import Suppliers.Supplier.Product;
import Tests.Visual.SupplierRegularOrder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args){
        /*MainMenu mainMenu = new MainMenu();
        mainMenu.startMenu();*/

        SupplierRegularOrder.addRegularOrder();
    }
}
