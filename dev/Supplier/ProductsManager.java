package Supplier;

import java.util.HashMap;
import java.util.Map;

public class ProductsManager {

    private Map<Integer, Product> productMap;

    public  ProductsManager(){
        productMap = new HashMap<>();
    }

    public void addIfAbsent(int productId, String manufacture, String productName){
        throw new UnsupportedOperationException();
    }

    public boolean exists(int productId){
        return productMap.containsKey(productId);
    }
}
