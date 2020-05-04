package Suppliers.Service;

import java.util.Map;

public class ProductDiscountsDTO {
    public int barcode;
    public Map<Integer,Double> discountPerAmount;
    public double originalPrice;

    public ProductDiscountsDTO(int barcode, Map<Integer, Double> discountPerAmont, Double originalPrice) {
        this.barcode = barcode;
        this.discountPerAmount = discountPerAmont;
        this.originalPrice = originalPrice;
    }

    public String toString(){
        String discountStr = "";
        for(Integer amount : discountPerAmount.keySet()){
            discountStr = discountStr + String.format("\t%d : %f\n", amount, discountPerAmount.get(amount));
        }

        return String.format("Product ID: %d\nDiscounts :\n%sOriginal price : %f", barcode, discountStr, originalPrice);
    }


}
