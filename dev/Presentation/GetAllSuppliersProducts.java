package Presentation;

import Service.SupplierDetailsDTO;
import Service.SupplierManagment;
import Service.SupplierProductDTO;

import java.util.List;

public class GetAllSuppliersProducts implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public GetAllSuppliersProducts(SupplierManagment supplierManagment) {
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

        List<SupplierProductDTO> supplierProductDTOS = supplierManagment.getAllSupplierProducts(supplierId);
        if(supplierProductDTOS != null) {
            for (SupplierProductDTO product : supplierProductDTOS) {
                System.out.println(product.toString());
            }
        } else {
            System.out.println("Invalid supplier id or no contract");
        }
    }
}
