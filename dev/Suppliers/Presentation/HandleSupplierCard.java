package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HandleSupplierCard extends Menu_Option {


    private SupplierManagment supplierManagment;

    public HandleSupplierCard(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply() {
        /*
         * <name> <inc number> <bank account number> <payment info>
         * <contactName> <phoneNumber> <email>
         */
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String name, incNum, bankAccount, address, payment, contactName, phoneNumber, email;

            name = readString("Name", reader);
            incNum = readString("Inc number", reader);
            address = readString("address", reader);
            bankAccount = readString("Bank account Number", reader);
            payment = readString("Payment options", reader);
            contactName = readString("Contact name", reader);
            phoneNumber = readString("Phone number", reader);
            email = readString("Email", reader);



            int supId = supplierManagment.createSupplierCard(name, incNum, address, bankAccount, payment,
                    contactName, phoneNumber, email);
            if (supId < 0) {
                System.out.println("Suppliers.Supplier card wasnt created");
            } else {
                System.out.println("New supplier ID: " + supId);
            }
        } catch (IOException e) {
            System.out.println("Error at reading");
        }
    }
}
