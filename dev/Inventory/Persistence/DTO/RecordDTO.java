package Inventory.Persistence.DTO;


import java.time.LocalDate;

public class RecordDTO {

    private int recId;
    private int itemId;
    private double cost;
    private LocalDate costChangeDate;
    private double price;
    private LocalDate priceChangeDate;

    public RecordDTO(int recId, int itemId, double cost, LocalDate costChangeDate, double price, LocalDate priceChangeDate) {
        this.recId = recId;
        this.itemId = itemId;
        this.cost = cost;
        this.costChangeDate = costChangeDate;
        this.price = price;
        this.priceChangeDate = priceChangeDate;
    }

    // region getters&setters
    public int getRecId() {
        return recId;
    }

    public void setRecId(int recId) {
        this.recId = recId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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
