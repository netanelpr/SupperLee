package Suppliers.Presentation;

import Suppliers.Service.AddSupplierProductDTO;
import Suppliers.Service.ProductDiscountsDTO;
import Suppliers.Service.SupplierManagment;
import Suppliers.Structs.Days;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InitWithData extends Menu_Option {


    private SupplierManagment supplierManagment;

    public InitWithData(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply() {
        createSuppliers();
    }

    private void createSuppliers(){
        int supId = -1;
        supId = supplierManagment.createSupplierCard("supplier1", "123","12345", "Cash", "Supi",
                "051111111","supi@supplier1.com");

        if(supId > -1){
            System.out.println("New supplier ID: " + supId);
        } else {
            System.out.println("supplier with id " + supId + " wasnt created");
        }

        supId = supplierManagment.createSupplierCard("supplier2", "1010","11111", "PAYMENTS,Check", "Supi",
                "051234567","star@supplier1.com");
        if(supId > -1){
            System.out.println("New supplier ID: " + supId);
        } else {
            System.out.println("supplier with id " + supId + " wasnt created");
        }

        addProductToSupplier(0);
    }

    private void addProductToSupplier(int supId){
        List<Days> days1 = new LinkedList<>();
        days1.add(Days.Sunday);

        Map<Integer, Double> discount1 = new HashMap<>();
        discount1.put(10,0.01);
        discount1.put(100,0.05);

        List<AddSupplierProductDTO> products = new LinkedList<>();
        products.add(new AddSupplierProductDTO(1,"1",10.90, new ProductDiscountsDTO(1, discount1, 10.90)
                , "manufacture1", "product1"));
        products.add(new AddSupplierProductDTO(2,"100",90, new ProductDiscountsDTO(1, new HashMap<>() , 10.90)
                , "manufacture1", "product2"));

        List<Integer> notAdded = supplierManagment.addContractToSupplier(supId, "contract1",days1, products);
        if(notAdded == null){
            System.out.println("The contract wasnt added");
        } else {
            if(!notAdded.isEmpty()){
                System.out.println("The product that wasnt added : " + notAdded.toString());
            } else {
                System.out.println("The contract was added");
            }
        }
    }
}
