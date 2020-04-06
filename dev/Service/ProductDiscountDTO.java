package Service;

import java.util.Map;

public class ProductDiscountDTO {
    private int productID;
    private Map<Integer,Double> discountPerAmount;
    private double originalPrice;

    public ProductDiscountDTO(int productID, Map<Integer, Double> discountPerAmont, Double originalPrice) {
        this.productID = productID;
        this.discountPerAmount = discountPerAmont;
        this.originalPrice = originalPrice;
    }


}
