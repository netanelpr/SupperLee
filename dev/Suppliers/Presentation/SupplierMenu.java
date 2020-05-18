package Suppliers.Presentation;

import Suppliers.Service.OrderAndProductManagement;
import Suppliers.Service.SupplierManagment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SupplierMenu extends MenuOfMenus {

    public SupplierMenu(SupplierManagment supplierManagment, OrderAndProductManagement orderAndProductManagement) {
        super(supplierManagment, orderAndProductManagement);
    }

    protected void createMenuMap() {

        addMenuOption("Create supplier card", "c", new HandleSupplierCard(supplierManagment));
        addMenuOption("Get all suppliers", "gas",new GetAllSuppliers(supplierManagment));
        addMenuOption("Get all supplier's contacts", "gasc",new GetAllSupplierContacts(supplierManagment));
        addMenuOption("Get payment options", "gpo",new PaymentOptions(supplierManagment));
        addMenuOption("Update payment options", "upo",new updatePaymentOptions(supplierManagment));



        addMenuOption("Add contact info to supplier", "acis",new AddContactInfoToSupplier(supplierManagment));
        addMenuOption("Add contract to supplier", "acs",new AddContractToSupplier(supplierManagment, orderAndProductManagement));
        addMenuOption("Add product to supplier", "aps",new AddProductToSupplier(supplierManagment, orderAndProductManagement));
        addMenuOption("Discount report", "dr",new GetAmountDiscountReport(supplierManagment));

        addMenuOption("Get all supplier barcode", "gasb" ,new GetAllSuppliersProducts(supplierManagment));
        addMenuOption("Get all supplier products detalis", "gaspd" ,new GetAllSuppliersProductsDetalis(supplierManagment,orderAndProductManagement));

        addMenuOption("Get purchase history from supplier", "gph",new PurchaseHistoryFromSupplier(supplierManagment));
    }
}
