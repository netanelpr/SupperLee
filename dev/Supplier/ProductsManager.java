package Supplier;

import java.util.HashMap;
import java.util.Map;

public class ProductsManager {

    private Map<Integer, Product> productMap;

    public  ProductsManager(){
        productMap = new HashMap<>();
    }

    /**
     * Add a product to the system if it does not exist
     * @param productId Product id
     * @param manufacture The manufacture of the product
     * @param productName The product name
     * @return true if the product have been added
     */
    public boolean addIfAbsent(int productId, String manufacture, String productName){
        throw new UnsupportedOperationException();
    }

    public boolean exists(int productId){
        return productMap.containsKey(productId);
    }
}
