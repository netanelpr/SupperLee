package Supplier;

import java.util.List;
import java.util.Map;

public class Product {
    //Real product stored in system
    private int barCode;
    private String manufacture;
    private String name;

    public Product(int barCode, String manufacture, String name) {
        this.barCode = barCode;
        this.manufacture = manufacture;
        this.name = name;
    }

    public int getBarCode() {
        return barCode;
    }

    public String getManufacture() {
        return manufacture;
    }

    public String getName() {
        return name;
    }
}
