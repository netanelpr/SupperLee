import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ContractWithSupplier {

    private List<Days> dailyInfo;
    private String contractDetails;

    private List<Product> products;

    public ContractWithSupplier(String contractDetails, List<Days> days){

    }

    public int addProduct(AddProductInfo product){
        throw new NotImplementedException();
    }

    public void addDiscountToProduct(int productId, int amount, int discount){
        throw new NotImplementedException();
    }
}
