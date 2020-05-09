package Suppliers.Service;

public class SystemProduct {

    //TODO add the fields they need

    public int barcode;
    //public double originalPrice;
    public String name;
    public String manufacture;

    public SystemProduct(int barcode, String manufacture, String name) {
        this.barcode = barcode;
        //this.originalPrice = originalPrice;
        this.manufacture = manufacture;
        this.name = name;
    }
}
