package Supplier;

public class ProductInOrder {

    private int barcode;
    private int amount;
    private String productCatalogNumber;

    public ProductInOrder(int barcode, int amount){
        this.barcode = barcode;
        this.amount = amount;
        productCatalogNumber = null;
    }

    public ProductInOrder(int barcode, int amount, String productCatalogNumber){
        this.barcode = barcode;
        this.amount = amount;
        this.productCatalogNumber = productCatalogNumber;
    }

    public int getBarcode() {
        return barcode;
    }

    public int getAmount() {
        return amount;
    }

    public String getProductCatalogNumber() {
        return productCatalogNumber;
    }

    public void setProductCatalogNumber(String productCatalogNumber) {
        this.productCatalogNumber = productCatalogNumber;
    }
}
