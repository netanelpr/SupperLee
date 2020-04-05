import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Product {

    private int productCatalogNumber;
    private int systemProductId;
    private int originalPrice;

    private List<DiscountOfProduct> discounts;

    public Product(int productCatalogId, int systemProductId, int originalPrice){

    }

    public void addDiscount(int amount, double discount){
        throw new NotImplementedException();
    }
}
