package Supplier;

import java.util.HashMap;
import java.util.Map;

public class ProductsManager {

    private Map<Integer, Product> productMap; //BarcodeToProductObject

    public  ProductsManager(){
        productMap = new HashMap<>();
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
