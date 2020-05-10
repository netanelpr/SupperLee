package Inventory.Persistence.DTO;


import java.time.LocalDate;

public class RecordDTO {

    private String recId;
    private String itemId;
    private String shopNum;
    private double cost;
    private LocalDate costChangeDate;
    private double price;
    private LocalDate priceChangeDate;


    public RecordDTO(String recId, String itemId, String shopNum, double cost, LocalDate costChangeDate, double price, LocalDate priceChangeDate) {
        this.recId = recId;
        this.itemId = itemId;
        this.shopNum = shopNum;
        this.cost = cost;
        this.costChangeDate = costChangeDate;
        this.price = price;
        this.priceChangeDate = priceChangeDate;
    }

    // region getters&setters

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getShopNum() {
        return shopNum;
    }

    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getCostChangeDate() {
        return costChangeDate;
    }

    public void setCostChangeDate(LocalDate costChangeDate) {
        this.costChangeDate = costChangeDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getPriceChangeDate() {
        return priceChangeDate;
    }

    public void setPriceChangeDate(LocalDate priceChangeDate) {
        this.priceChangeDate = priceChangeDate;
    }

    //endregion

}
