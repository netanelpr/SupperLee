package Supplier;

import java.util.HashMap;
import java.util.Map;

public class ProductsManager {

    private Map<Integer, Product> productMap;

    public  ProductsManager(){
        productMap = new HashMap<>();
    }
}
