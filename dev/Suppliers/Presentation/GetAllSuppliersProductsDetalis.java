package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;
import Suppliers.Service.SupplierProductDTO;

import java.util.List;

public class GetAllSuppliersProductsDetalis implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public GetAllSuppliersProductsDetalis(SupplierManagment supplierManagment) {
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

        List<SupplierProductDTO> supplierProductDTOS = supplierManagment.getAllSupplierProducts(supplierId);

        if(supplierProductDTOS != null) {
            for(SupplierProductDTO dto : supplierProductDTOS){
                String productInfo = supplierManagment.getAllInformationAboutSuppliersProduct(supplierId, dto.barcode).shallow_toString();
                System.out.println(productInfo+ "\n");
            }
        } else {
            System.out.println("Invalid supplier id or no contract");
        }
    }
}
