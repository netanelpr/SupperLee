package Supplier;

import java.util.List;
import java.util.Map;

public class Product {

    public int produceId;
    public String manufacture;
    public String name;

    public Product(int productId, String name, String manufacture) {
        this.produceId = productId;
        this.name = name;
        this.manufacture = manufacture;
    }
}
