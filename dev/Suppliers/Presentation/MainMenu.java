package Suppliers.Presentation;

import Suppliers.Service.OrderAndProductCtrl;
import Suppliers.Service.OrderAndProductManagement;
import Suppliers.Service.SupplierCtrl;
import Suppliers.Service.SupplierManagment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MainMenu extends MenuOfMenus {

    private MainMenu(SupplierManagment supplierManagment, OrderAndProductManagement orderAndProductManagement) {
        super(supplierManagment, orderAndProductManagement);
    }

    public MainMenu() {
        super(new SupplierCtrl(), new OrderAndProductCtrl());
    }

    protected void createMenuMap() {
        addMenuOption("Supplier menu", new SupplierMenu(supplierManagment, orderAndProductManagement));
        addMenuOption("Order menu", new OrderMenu(supplierManagment, orderAndProductManagement));
    }
}
