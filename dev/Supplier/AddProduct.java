package Supplier;

import java.util.HashMap;
import java.util.Map;

public class AddProduct {

    public int barCode;
    public String productCatalogNumber;
    public double originalPrice;
    public String manufacture;
    public String name;
    public ProductDiscounts productDiscounts;

    public AddProduct(int barCode, String productCatalogNumber, double originalPrice ,ProductDiscounts productDiscounts,
                          String manufacture, String name) {
        this.barCode=barCode;
        this.productCatalogNumber = productCatalogNumber;
        this.originalPrice = originalPrice;
        this.manufacture = manufacture;
        this.name = name;
        this.productDiscounts=productDiscounts;
    }
}
