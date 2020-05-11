package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;
import Suppliers.Service.SimpleSupplierProductDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class GetAllSuppliersProductsDetalis extends Menu_Option {


    private SupplierManagment supplierManagment;

    public GetAllSuppliersProductsDetalis(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int supId = readInt("Supplier ID", reader);


        List<SimpleSupplierProductDTO> simpleSupplierProductDTOS = supplierManagment.getAllSupplierProducts(supId);

        if(simpleSupplierProductDTOS != null) {
            for(SimpleSupplierProductDTO dto : simpleSupplierProductDTOS){
                String productInfo = supplierManagment.getAllInformationAboutSuppliersProduct(supId, dto.barcode).shallow_toString();
                System.out.println(productInfo+ "\n");
            }
        } else {
            System.out.println("Invalid supplier id or no contract");
        }
    }
}
