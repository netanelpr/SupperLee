package Presentation;

import Service.SupplierManagment;
import Service.SupplierProductDTO;

import java.util.List;

public class PurchaseHistoryFromSupplier implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public PurchaseHistoryFromSupplier(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
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

        List<Integer> ordersId = supplierManagment.getPurchaseHistory(supplierId);
        if(ordersId != null) {
            int index = 0;
            System.out.println("The orders id:");
            for (Integer order : ordersId) {
                if(index != 10) {
                    System.out.print(order + ", ");
                } else {
                    System.out.println(order.intValue());
                    index = 0;
                    continue;
                }
                index = index + 1;
            }
        } else {
            System.out.println("Invalid supplier ID");
        }
    }
}
