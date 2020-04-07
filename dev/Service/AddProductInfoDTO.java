package Service;

import java.util.Map;

public class AddProductInfoDTO {

    public int productId;
    public int productCatalogNumber;
    public double originalPrice;
    public String name;
    public String manufacture;
    public Map<Integer,Double> discountPerAmount;

        public AddProductInfoDTO(int productId, int productCatalogNumber, double originalPrice, Map<Integer, Double> discountPerAmount,
                                 String manufacture, String name) {
        this.productId = productId;
        this.productCatalogNumber = productCatalogNumber;
        this.originalPrice = originalPrice;
        this.manufacture = manufacture;
        this.name = name;

        this.discountPerAmount = discountPerAmount;

    }
}
