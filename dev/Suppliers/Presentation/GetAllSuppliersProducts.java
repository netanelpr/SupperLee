package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;
import Suppliers.Service.SupplierProductDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class GetAllSuppliersProducts extends Menu_Option {


    private SupplierManagment supplierManagment;

    public GetAllSuppliersProducts(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int supId = readInt("Supplier ID", reader);

        List<SupplierProductDTO> supplierProductDTOS = supplierManagment.getAllSupplierProducts(supId);
        if(supplierProductDTOS != null) {
            for (SupplierProductDTO product : supplierProductDTOS) {
                System.out.println(product.toString());
            }
        } else {
            System.out.println("Invalid supplier id or no contract");
        }
    }
}
