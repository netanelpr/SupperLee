package Suppliers.Service;

public class SystemProduct {

    //TODO add the fields they need

    public int barcode;
    public String name;
    public String manufacture;
    public String catagory;
    public String subCatagory;
    public int freqSupply;
    public double minPrice;

    public SystemProduct(int barcode, String manufacture, String name, String catagory,
                         String subCatagory, int freqSupply, double minPrice) {
        this.barcode = barcode;
        //this.originalPrice = originalPrice;
        this.manufacture = manufacture;
        this.name = name;
    }
}
