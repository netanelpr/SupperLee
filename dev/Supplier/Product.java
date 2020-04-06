package Supplier;

import java.util.List;

public class Product {

    private int produceId;
    private int productCatalogNumber;
    private int systemProductId;
    private int originalPrice;
    private String name;
    private String manufacture;
    private Category category;
    private SubCategory subCategory;
    private Sizes size;
    private int freqSupply;

    private List<DiscountOfProduct> discounts;

    public Product(int productId, int productCatalogNumber, int systemProductId, int originalPrice, String name, String manufacture, Category category, SubCategory subCategory, Sizes size, int freqSupply, List<DiscountOfProduct> discounts) {
        this.produceId = productId;
        this.productCatalogNumber = productCatalogNumber;
        this.systemProductId = systemProductId;
        this.originalPrice = originalPrice;
        this.name = name;
        this.manufacture = manufacture;
        this.category = category;
        this.subCategory = subCategory;
        this.size = size;
        this.freqSupply = freqSupply;
        this.discounts = discounts;
    }
}
