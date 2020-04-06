package Supplier;

import java.util.Map;

public class ProductDiscount {
    private int productID;
    private Map<Integer,Double> discountPerAmont;

    public ProductDiscount(int productID, Map<Integer, Double> discountPerAmont, Double originalPrice) {
        this.productID = productID;
        this.discountPerAmont = discountPerAmont;
        this.originalPrice = originalPrice;
    }

    private Double originalPrice;


}
