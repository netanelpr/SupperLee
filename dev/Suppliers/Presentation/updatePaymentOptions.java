package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;

import java.util.Arrays;

public class updatePaymentOptions implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public updatePaymentOptions(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
        /*
         * <update> <supplier id> <payment options>+
         */
        if(argv.length != 3){
            System.out.println("Invalid number of args");
            return;
        }
        if(!(argv[0].equals("remove") | argv[0].equals("add"))){
            System.out.println("Invalid update option");
            return;
        }

        int supid = -1;
        try{
            supid = Integer.parseInt(argv[1]);
        } catch (NumberFormatException e){
            System.out.println("Invalid supplier ID");
            return;
        }

        String[] paymentOptions;
        paymentOptions = Arrays.copyOfRange(argv,2, argv.length);

        boolean update = false;
        if(argv[0].equals("remove")){
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
