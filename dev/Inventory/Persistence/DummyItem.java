package Inventory.Persistence;

public class DummyItem {

    private String id;
    private String name;
    private String manufacturer;
    private String category;
    private String sub_category;
    private String size;
    private String freqSupply;
    private String cost;

    public DummyItem(String id, String name, String manufacturer, String category,
                     String sub_category, String size, String freqSupply, String cost) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.category = category;
        this.sub_category = sub_category;
        this.size = size;
        this.freqSupply = freqSupply;
        this.cost = cost;
    }

    //region getters
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getCategory() {
        return category;
    }
    public String getSub_category() {
        return sub_category;
    }
    public String getSize() {
        return size;
    }
    public String getFreqSupply() {
        return freqSupply;
    }
    public double getCost() { return Double.parseDouble(cost); }
    //public double getDefPrice() { return (Double.parseDouble(cost)*1.75); }
    //endregion

}
