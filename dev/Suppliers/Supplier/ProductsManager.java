package Suppliers.Supplier;

import java.util.HashMap;
import java.util.Map;

public class ProductsManager {

    private static ProductsManager instance = null;

    private Map<Integer, Product> productMap; //BarcodeToProductObject

    private ProductsManager(){
        productMap = new HashMap<>();
    }

    public static ProductsManager getInstance(){
        if(instance == null){
            instance = new ProductsManager();
        }
        return instance;
    }

    public boolean addIfAbsent(AddProduct addProduct){
        if(!productMap.containsKey(addProduct.barCode))
        {
            productMap.put(addProduct.barCode,new Product(addProduct.barCode,addProduct.manufacture,addProduct.name));
            return true;
        }
        else
            return false;
    }

    public boolean exists(int barCode){
        return productMap.containsKey(barCode);
    }

    public Product getAllInfoAboutProduct(int barcode) {
        return this.productMap.get(barcode);
    }
}
