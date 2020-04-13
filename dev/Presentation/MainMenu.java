package Presentation;

import Service.AddProductInfoDTO;
import Service.ProductDiscountsDTO;
import Service.SupplierCtrl;
import Service.SupplierManagment;
import Structs.Days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MainMenu implements Menu_Option{

    private SupplierManagment supplierManagment;
    private Map<String, Menu_Option> optionMap;

    public MainMenu(){
        supplierManagment = new SupplierCtrl();
        optionMap = createMenuMap();
    }

    private Map<String, Menu_Option> createMenuMap() {
        Map<String, Menu_Option> map = new HashMap<>();

        map.put("init", this);
        map.put("createSupplierCard", new HandleSupplierCard(supplierManagment));

        map.put("getPaymentOptions", new PaymentOptions(supplierManagment));
        map.put("updatePaymentOptions", new updatePaymentOptions(supplierManagment));

        map.put("getAllSuppliers", new GetAllSuppliers(supplierManagment));

        map.put("addContactInfoToSupplier", new AddContactInfoToSupplier(supplierManagment));
        map.put("addContractToSupplier", new AddContractToSupplier(supplierManagment));
        map.put("addProductToSupplier", new AddProductToSupplier(supplierManagment));
        map.put("discountReport", new GetAmountDiscountReport(supplierManagment));

        map.put("createNewOrder", new CreateNewOrder(supplierManagment));
        map.put("updateOrderArrivalDay", new UpdateOrderArrivalDay(supplierManagment));
        map.put("updateOrderStatus", new UpdateOrderStatus(supplierManagment));

        map.put("getAllSupplierProductsId", new GetAllSuppliersProducts(supplierManagment));
        map.put("getAllSupplierProductsDetalis", new GetAllSuppliersProductsDetalis(supplierManagment));

        map.put("getPruchaseHistoryFromSupplier", new PurchaseHistoryFromSupplier(supplierManagment));

        return map;

    }

    public void startMenu() {
        String[] argv;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            String input;
            try {
                input = reader.readLine();
            } catch (IOException e){
                System.out.println("Error reading input");
                continue;
            }
            argv = input.split(" ");

            Menu_Option option = optionMap.getOrDefault(argv[0], null);
            if(option == null){
                System.out.println("Invalid function");
                continue;
            }
            option.apply(Arrays.copyOfRange(argv, 1, argv.length));
        }
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
        Menu_Option option;
        option = optionMap.get("createSupplierCard");
        option.apply(new String[]{"supplier1", "123","12345", "Cash", "Supi", "051111111","supi@supplier1.com"});
        option.apply(new String[]{"supplier2", "1010","11111", "PAYMENTS,Check", "Supi", "051234567","star@supplier1.com"});

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
