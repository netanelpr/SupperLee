import java.util.Map;

public class AddProductInfo {

    private int productCatalogNumber;
    private Double originalPrice;
    private Map<Integer,Double> discountPerAmount;

    public AddProductInfo(int productCatalogNumber, Double originalPrice, Map<Integer, Double> discountPerAmount) {
        this.productCatalogNumber = productCatalogNumber;
        this.originalPrice = originalPrice;
        this.discountPerAmount = discountPerAmount;
    }
}
