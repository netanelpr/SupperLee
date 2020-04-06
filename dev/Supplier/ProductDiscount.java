package Supplier;

import java.util.Map;

public class ProductDiscount {
    private int productID;
    private Map<Integer,Double> discountPerAmount;
    private double originalPrice;

    public ProductDiscount(int productID, Map<Integer, Double> discountPerAmont, Double originalPrice) {
        this.productID = productID;
        this.discountPerAmount = discountPerAmont;
        this.originalPrice = originalPrice;
    }
}
