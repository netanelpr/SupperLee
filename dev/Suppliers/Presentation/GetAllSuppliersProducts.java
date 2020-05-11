package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;
import Suppliers.Service.SimpleSupplierProductDTO;

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

        List<SimpleSupplierProductDTO> simpleSupplierProductDTOS = supplierManagment.getAllSupplierProducts(supId);
        if(simpleSupplierProductDTOS != null) {
            for (SimpleSupplierProductDTO product : simpleSupplierProductDTOS) {
                System.out.println(product.toString());
            }
        } else {
            System.out.println("Invalid supplier id or no contract");
        }
    }
}
