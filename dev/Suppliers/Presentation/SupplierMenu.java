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

        addMenuOption("Init", new InitWithData(supplierManagment));
        addMenuOption("Create supplier card", new HandleSupplierCard(supplierManagment));
        addMenuOption("Get all suppliers", new GetAllSuppliers(supplierManagment));
        addMenuOption("Get all supplier's contacts", new GetAllSupplierContacts(supplierManagment));
        addMenuOption("Get payment options", new PaymentOptions(supplierManagment));
        addMenuOption("Update payment options", new updatePaymentOptions(supplierManagment));



        addMenuOption("Add contact info to supplier", new AddContactInfoToSupplier(supplierManagment));
        addMenuOption("Add contract to supplier", new AddContractToSupplier(supplierManagment, orderAndProductManagement));
        addMenuOption("Add product to supplier", new AddProductToSupplier(supplierManagment, orderAndProductManagement));
        addMenuOption("Discount report", new GetAmountDiscountReport(supplierManagment));

        addMenuOption("Get all supplier barcode", new GetAllSuppliersProducts(supplierManagment));
        addMenuOption("Get all supplier products detalis", new GetAllSuppliersProductsDetalis(supplierManagment,orderAndProductManagement));

        addMenuOption("Get purchase history from supplier", new PurchaseHistoryFromSupplier(supplierManagment));
    }
}
