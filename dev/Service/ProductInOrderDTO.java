package Service;

public class ProductInOrderDTO {

    public int barcode;
    public int amount;

    public ProductInOrderDTO(int barcode, int amount){
        this.barcode = barcode;
        this.amount = amount;
    }
}
