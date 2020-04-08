package Supplier;

import java.util.Map;

public class ProductDiscount {
    public int productID;
    public Map<Integer,Double> discountPerAmount;
    public double originalPrice;

    public ProductDiscount(int productID, Map<Integer, Double> discountPerAmont, Double originalPrice) {
        this.productID = productID;
        this.discountPerAmount = discountPerAmont;
        this.originalPrice = originalPrice;
    }
}
