package Suppliers.Presentation;

import Suppliers.Service.SupplierDetailsDTO;
import Suppliers.Service.SupplierManagment;

import java.util.List;

public class GetAllSuppliers implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public GetAllSuppliers(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
        if(argv.length != 0){
            System.out.println("Invalid number of args");
            return;
        }

        List<SupplierDetailsDTO> supplierDetails = supplierManagment.getAllSuppliers();

        if(supplierDetails != null) {
            for (SupplierDetailsDTO supD : supplierManagment.getAllSuppliers()) {
                System.out.println(supD.toString());
            }
        } else {
            System.out.println("There are not any supplier in the system");
        }
    }
}
