package Inventory.Logic;

import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DTO.RecordDTO;

import java.time.LocalDate;
import java.util.List;

public class Record implements myObservable {

    //region fields
    private String recId;
    private String itemId;
    private String shopNum;
    private double cost;
    private LocalDate costChangeDate;
    private double price;
    private LocalDate priceChangeDate;
    public final List<Observer> observers;


    public Record(List<Observer> observers, RecordDTO currDTORec) {
        this.recId = currDTORec.getRecId();
        this.itemId = currDTORec.getItemId();
        this.shopNum = currDTORec.getShopNum();
        this.cost = currDTORec.getCost();
        this.costChangeDate = currDTORec.getCostChangeDate();
        this.price = currDTORec.getPrice();
        this.priceChangeDate = currDTORec.getPriceChangeDate();
        this.observers = observers;
    }
    //endregion

    //region getters
    public LocalDate getPriceChangeDate() {
        return priceChangeDate;
    }
    public double getCost() {
        return cost;
    }
    public String getId() {
        return recId;
    }
    public String getItemId() {
        return itemId;
    }
    public double getPrice() {
        return price;
    }
    public String getShopNum() { return shopNum; }
    //endregion

    public Record(List<Observer> observers, String id, String name, double cost, LocalDate costChangeDate, LocalDate priceChangeDate, String shopNum) {
        this.recId = id;
        this.itemId = name;
        this.cost = cost;
        this.costChangeDate = costChangeDate;
        this.price = cost*1.75; //default price
        this.priceChangeDate = priceChangeDate;
        this.observers = observers;
        this.shopNum = shopNum;
    }
    public Record(List<Observer> observers, String id, String itemId, double cost, LocalDate costChangeDate, double newPrice, LocalDate priceChangeDate, String shopNum) {
        this.recId = id;
        this.itemId = itemId;
        this.cost = cost;
        this.costChangeDate = costChangeDate;
        this.price = newPrice; //new price
        this.priceChangeDate = priceChangeDate;
        this.observers = observers;
        this.shopNum = shopNum;
    }

    public void recordItemStatus() {
        notifyObserver("|--$-$-$-$-$-$-$-$-$-$-$-$-$--\n" +
                "| Rec id; " + recId + " item id; " + itemId + "\n" +
                "| cost; " + cost +
                " cost Change Date; " + costChangeDate + "\n" +
                "| price; " + price +
                " price Change Date; " + priceChangeDate + "\n" +
                "|--$-$-$-$-$-$-$-$-$-$-$-$-$--");
    }

    //region observer
    @Override
    public void register(Observer o) {
        observers.add(o);
    }
    @Override
    public void notifyObserver(String msg) {
        observers.forEach(o -> o.onEvent(msg));
    }
    //endregion
}
