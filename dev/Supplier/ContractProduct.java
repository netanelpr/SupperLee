package Supplier;

import java.util.List;

public class ContractProduct {
    private int produceId;
    private int productCatalogNumber;
    private List<DiscountOfProduct> discounts;

    public ContractProduct(int produceId, int productCatalogNumber, List<DiscountOfProduct> discounts){

    }

    public void addDiscount(int amount, double discount){
        throw new UnsupportedOperationException();
    }
}
