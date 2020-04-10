package Supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContractProduct {
    private int barCode;
    private String productCatalogNumber;
    private Map<Integer,DiscountOfProduct> discounts;
    private double originalPrice;


    public ContractProduct(int barCode, String productCatalogNumber, ProductDiscounts discounts) {
        this.barCode = barCode;
        this.productCatalogNumber = productCatalogNumber;
        this.discounts=new HashMap<Integer, DiscountOfProduct>();
        for (Integer amount:discounts.discountPerAmount.keySet()) {
            this.discounts.put(amount,new DiscountOfProduct(amount,discounts.discountPerAmount.get(amount)));
        }
        this.originalPrice=discounts.originalPrice;
    }

    public int getBarCode() {
        return barCode;
    }

    public String getProductCatalogNumber() {
        return productCatalogNumber;
    }

    public ProductDiscounts getDiscounts() {
        Map<Integer,Double> discountsSummary = new HashMap<>();
        for (Integer amount:this.discounts.keySet()) {
            discountsSummary.put(amount,discounts.get(amount).getDiscount());
        }
        return new ProductDiscounts(this.barCode,discountsSummary,originalPrice);
    }

    public boolean addDiscountToProduct(int amount, double discount) {
        if(this.discounts.containsKey(amount))
        {
            this.discounts.get(amount).setDiscount(discount);
        }
        else
        {
            this.discounts.put(amount,new DiscountOfProduct(amount,discount));
        }
        return true;
    }
}
