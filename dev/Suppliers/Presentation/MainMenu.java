package Suppliers.Presentation;

import Suppliers.Presentation.Order.OrderMenu;
import Suppliers.Service.OrderAndProductCtrl;
import Suppliers.Service.OrderAndProductManagement;
import Suppliers.Service.SupplierCtrl;
import Suppliers.Service.SupplierManagment;

public class MainMenu extends MenuOfMenus {

    private MainMenu(SupplierManagment supplierManagment, OrderAndProductManagement orderAndProductManagement) {
        super(supplierManagment, orderAndProductManagement);
    }

    public MainMenu() {
        super(new SupplierCtrl(), new OrderAndProductCtrl());
    }

    protected void createMenuMap() {
        addMenuOption("Supplier menu", "s", new SupplierMenu(supplierManagment, orderAndProductManagement));
        addMenuOption("Order menu", "o",new OrderMenu(supplierManagment, orderAndProductManagement));
    }
}
