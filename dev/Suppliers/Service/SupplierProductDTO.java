package Suppliers.Service;

public class SupplierProductDTO {

    public String productCatalogNumber;
    public int barcode;

    public SupplierProductDTO(int barcode, String productCatalogNumber) {
        this.productCatalogNumber = productCatalogNumber;
        this.barcode = barcode;
    }

    public String toString(){
        return String.format("Barcode : %d, Catalog number : %s", barcode, productCatalogNumber);
    }
}
