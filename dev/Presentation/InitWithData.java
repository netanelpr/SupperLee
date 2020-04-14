package Presentation;

import Service.AddProductInfoDTO;
import Service.ProductDiscountsDTO;
import Service.SupplierManagment;
import Structs.Days;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InitWithData implements  Menu_Option {


    private SupplierManagment supplierManagment;
    private Menu_Option createSupplier;

    public InitWithData(SupplierManagment supplierManagment , Menu_Option createSupplier) {
        this.supplierManagment = supplierManagment;
        this.createSupplier = createSupplier;
    }


    @Override
    public void apply(String[] argv) {
        if(argv.length != 0){
            System.out.println("Invalid number of args");
            return;
        }
        createSuppliers();

    }

    private void createSuppliers(){
        createSupplier.apply(new String[]{"supplier1", "123","12345", "Cash", "Supi", "051111111","supi@supplier1.com"});
        createSupplier.apply(new String[]{"supplier2", "1010","11111", "PAYMENTS,Check", "Supi", "051234567","star@supplier1.com"});

        addProductToSupplier(0);
    }

    private void addProductToSupplier(int supId){
        List<Days> days1 = new LinkedList<>();
        days1.add(Days.Sunday);

        Map<Integer, Double> discount1 = new HashMap<>();
        discount1.put(10,0.01);
        discount1.put(100,0.05);

        List<AddProductInfoDTO> products = new LinkedList<>();
        products.add(new AddProductInfoDTO(1,"1",10.90, new ProductDiscountsDTO(1, discount1, 10.90)
                , "manufacture1", "product1"));
        products.add(new AddProductInfoDTO(2,"100",90, new ProductDiscountsDTO(1, new HashMap<>() , 10.90)
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
