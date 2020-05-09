package Suppliers.Service;

public class ProductInOrderDTO {

    public int barcode;
    public int amount;
    //public int price;

    //TODO
    public ProductInOrderDTO(int barcode, int amount){
        this.barcode = barcode;
        this.amount = amount;
        //this.price=price;
    }
}
