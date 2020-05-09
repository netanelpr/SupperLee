package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class updatePaymentOptions extends Menu_Option {


    private SupplierManagment supplierManagment;

    public updatePaymentOptions(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply() {
        /*
         * <update> <supplier id> <payment options>+
         */

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int supid = -1;

        System.out.println("1) Add\n" +
                "2) Remove");
        int op = readInt("Option",reader);

        if(!(op == 1 | op == 2)){
            System.out.println("Invalid input");
            return;
        }
        supid = readInt("Supplier ID", reader);

        String paymentOptionsStr = readString("Payment options", reader);
        String[] paymentOptions = paymentOptionsStr.split(" ");

        boolean update = false;
        if(op == 2){
            update = supplierManagment.removePaymentOptions(supid, paymentOptions);
        } else { // add
            update = supplierManagment.addPaymentOptions(supid, paymentOptions);
        }

        if(update){
            System.out.println("Options was updated");
        } else {
            System.out.println("Options wasnt updated");
        }

    }
}
