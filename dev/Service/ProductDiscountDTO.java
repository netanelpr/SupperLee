package Service;

import java.util.Map;

public class ProductDiscountDTO {
    public int productID;
    public Map<Integer,Double> discountPerAmount;
    public double originalPrice;

    public ProductDiscountDTO(int productID, Map<Integer, Double> discountPerAmont, Double originalPrice) {
        this.productID = productID;
        this.discountPerAmount = discountPerAmont;
        this.originalPrice = originalPrice;
    }


}
