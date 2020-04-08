package Supplier;

import java.util.List;

public class ContractProduct {
    public int produceId;
    public int productCatalogNumber;
    public List<DiscountOfProduct> discounts;

    public ContractProduct(int produceId, int productCatalogNumber, List<DiscountOfProduct> discounts){

    }

    public void addDiscount(int amount, double discount){
        throw new UnsupportedOperationException();
    }
}
