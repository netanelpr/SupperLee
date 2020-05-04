package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;

import java.util.List;

public class PurchaseHistoryFromSupplier implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public PurchaseHistoryFromSupplier(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
        /*
         * <supplier ID>
         */
        if(argv.length != 1){
            System.out.println("Invalid number of args");
            return;
        }

        int supplierId = -1;
        try {
            supplierId = Integer.parseInt(argv[0]);
        } catch (NullPointerException e){
            System.out.println("Invalid supplier ID");
            return;
        }

        List<String> catalogNumbers = supplierManagment.getPurchaseHistory(supplierId);
        if(catalogNumbers != null) {
            int index = 0;
            System.out.println("The catalog numbers ids:");
            for (String catalogN : catalogNumbers) {
                if(index != 10) {
                    System.out.print(catalogN + " ");
                } else {
                    System.out.println(catalogN);
                    index = 0;
                    continue;
                }
                index = index + 1;
            }
            System.out.println();
        } else {
            System.out.println("Invalid supplier ID");
        }
    }
}
