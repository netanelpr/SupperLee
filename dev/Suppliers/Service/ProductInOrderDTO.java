package Suppliers.Service;

public class ProductInOrderDTO {

    public int barcode;
    public int amount;
    public int price;

    public ProductInOrderDTO(int barcode, int amount, int price){
        this.barcode = barcode;
        this.amount = amount;
        this.price=price;
    }
}
