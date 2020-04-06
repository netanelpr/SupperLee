package Service;

import Supplier.Category;
import Supplier.Sizes;
import Supplier.SubCategory;

import java.util.Map;

public class AddProductInfoDTO {

    private int productCatalogNumber;
    private Double originalPrice;
    private Map<Integer,Double> discountPerAmount;
    private String name;
    private String manufacture;
    private Category category;
    private SubCategory subCategory;
    private Sizes size;
    private int freqSupply;

    public AddProductInfoDTO(int productCatalogNumber, Double originalPrice, Map<Integer, Double> discountPerAmount, String name, String manufacture, Category category, SubCategory subCategory, Sizes size, int freqSupply) {
        this.productCatalogNumber = productCatalogNumber;
        this.originalPrice = originalPrice;
        this.discountPerAmount = discountPerAmount;
        this.name = name;
        this.manufacture = manufacture;
        this.category = category;
        this.subCategory = subCategory;
        this.size = size;
        this.freqSupply = freqSupply;
    }
}
