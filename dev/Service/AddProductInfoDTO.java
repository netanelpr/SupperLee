package Service;

import java.util.Map;

public class AddProductInfoDTO {

    public int barcode;
    public String productCatalogNumber;
    public double originalPrice;
    public String name;
    public String manufacture;
    public ProductDiscountsDTO discounts;

    public AddProductInfoDTO(int barcode, String productCatalogNumber, double originalPrice, ProductDiscountsDTO discountPerAmount,
                                 String manufacture, String name) {
        this.barcode = barcode;
        this.productCatalogNumber = productCatalogNumber;
        this.originalPrice = originalPrice;
        this.manufacture = manufacture;
        this.name = name;

        this.discounts = discountPerAmount;

    }

    public String toString(){
        return String.format("barcode: %d\ncatalog number : %s\noriginal price : %f\n"+
                        discounts.toString()+"\nmanufacture : %s\nname : %s", barcode, productCatalogNumber, originalPrice, manufacture, name);
    }
}
