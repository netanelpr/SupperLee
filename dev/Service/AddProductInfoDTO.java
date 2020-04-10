package Service;

import java.util.Map;

public class AddProductInfoDTO {

    public int productId;
    public String productCatalogNumber;
    public double originalPrice;
    public String name;
    public String manufacture;
    public ProductDiscountsDTO discounts;

        public AddProductInfoDTO(int productId, String productCatalogNumber, double originalPrice, ProductDiscountsDTO discountPerAmount,
                                 String manufacture, String name) {
        this.productId = productId;
        this.productCatalogNumber = productCatalogNumber;
        this.originalPrice = originalPrice;
        this.manufacture = manufacture;
        this.name = name;

        this.discounts = discountPerAmount;

    }
}
