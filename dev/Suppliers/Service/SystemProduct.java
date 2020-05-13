package Suppliers.Service;

public class SystemProduct {

    //TODO add the fields they need

    public int barcode;
    public String name;
    public String manufacture;
    public String category;
    public String subCategory;
    public String size;
    public int freqSupply;
    public double minPrice;

    public SystemProduct(int barcode, String manufacture, String name, String category,
                         String subCategory, String size) {
        this.barcode = barcode;
        this.manufacture = manufacture;
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.size = size;
        this.freqSupply = -1;
        this.minPrice = -1;
    }

    public SystemProduct(int barcode, String manufacture, String name, String category,
                         String subCategory, String size, int freqSupply, double minPrice) {
        this.barcode = barcode;
        this.manufacture = manufacture;
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.size = size;
        this.freqSupply = freqSupply;
        this.minPrice = minPrice;
    }
}
