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

    public String toString(){
        String discountStr = "";
        for(Integer amount : discountPerAmount.keySet()){
            discountStr = discountStr + String.format("\t%d : %f\n", amount, discountPerAmount.get(amount));
        }

        return String.format("Product ID: %d\nDiscounts : %s\nOriginal price : %f", productID, discountStr, originalPrice);
    }


}
