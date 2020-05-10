package Inventory.Logic;

import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;

import java.time.LocalDate;
import java.util.List;

public class Record implements myObservable {

    //region fields
    private String id;
    private String name;
    private double cost;
    private LocalDate costChangeDate;
    private double price;
    private LocalDate priceChangeDate;
    public final List<Observer> observers;
    //endregion

    //region getters
    public LocalDate getPriceChangeDate() {
        return priceChangeDate;
    }
    public double getCost() {
        return cost;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    //endregion

    public Record(List<Observer> observers, String id, String name, double cost, LocalDate costChangeDate, LocalDate priceChangeDate) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.costChangeDate = costChangeDate;
        this.price = cost*1.75; //default price
        this.priceChangeDate = priceChangeDate;
        this.observers = observers;
    }
    public Record(List<Observer> observers, String id, String name, double cost, LocalDate costChangeDate, double newPrice, LocalDate priceChangeDate) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.costChangeDate = costChangeDate;
        this.price = newPrice; //new price
        this.priceChangeDate = priceChangeDate;
        this.observers = observers;
    }

    public void recordItemStatus() {
        notifyObserver("|--$-$-$-$-$-$-$-$-$-$-$-$-$--\n" +
                "| id; " + id + " name; " + name + "\n" +
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
