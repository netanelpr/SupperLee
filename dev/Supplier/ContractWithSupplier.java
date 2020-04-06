package Supplier;

import Service.AddProductInfoDTO;
import Structs.Days;

import java.util.List;

public class ContractWithSupplier {

    private List<Days> dailyInfo;
    private String contractDetails;

    private List<ContractProduct> products;

    public ContractWithSupplier(String contractDetails, List<Days> days){
    }

    public int addProduct(AddProductInfo product){
        throw new UnsupportedOperationException();
    }

    public void addDiscountToProduct(int productId, int amount, int discount){
        throw new UnsupportedOperationException();
    }
}
