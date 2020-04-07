package Supplier;

import java.util.HashMap;
import java.util.Map;

public class AddProduct {

    public int produceId;
    public int productCatalogNumber;
    public double originalPrice;
    public String manufacture;
    public String name;

    private Map<Integer, Double> discounts;

        public AddProduct(int productId, int productCatalogNumber, double originalPrice ,Map<Integer, Double> discounts,
                          String manufacture, String name) {
        this.produceId = productId;
        this.productCatalogNumber = productCatalogNumber;
        this.originalPrice = originalPrice;
        this.manufacture = manufacture;
        this.name = name;


        this.discounts = new HashMap<>();
        for(Integer key : discounts.keySet()){
            this.discounts.put(key, discounts.get(key));
        }
    }
}
