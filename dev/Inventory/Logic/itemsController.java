package Inventory.Logic;

import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DummyItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class itemsController implements myObservable {

    private HashMap<String, Item> items; //item id, item
    public final List<Observer> observers;

    public itemsController(Observer o) {
        //this.myScanner = new Scanner(System.in);
        this.items = new HashMap<>();
        observers = new ArrayList<>();
        this.register(o);
    }
    public HashMap<String, Item> getItems() {
        return items;
    }

    //region update items
    public OrderItem updateInventoryWorkers(String id, int quanMissStock, int quanMissShop) {
        return items.get(id).updateMyQuantities(quanMissStock, quanMissShop, '-');
    }

    public void updateInventorySuppliers(HashMap<DummyItem, Integer> supply) {

        for (DummyItem dummyItem : supply.keySet()) {
            if(items.containsKey(dummyItem.getId())) {
                Item currItem = items.get(dummyItem.getId());
                currItem.updateMyQuantities(supply.get(dummyItem), 0, '+');
            }
            else
            {
                Item newItem = new Item(observers.get(0), dummyItem);
                newItem.updateMyQuantities(supply.get(dummyItem), 0, '+');
                items.put(newItem.getId(), newItem);
            }
        }
    }
    //endregion

    //region report_items
    public void getItemReport() {
        for (String id : items.keySet()) {
            items.get(id).itemStatus();
        }
    }
    public void getItemReportById(String id) {
//        notifyObserver("enter id:");
        items.get(id).itemStatus();
    }
    public void getItemReportByCategory(String cat) {
//        notifyObserver("enter category:");
//        String category = myScanner.nextLine();
        for (String id : items.keySet()) {
            if(items.get(id).getCategory().equals(cat))
                items.get(id).itemStatus();
        }
    }
    public void getItemMissing() {
        for (String id : items.keySet()) {
            if(items.get(id).getIfQuantityMinimum())
                items.get(id).itemStatus();
        }
    }
    //endregion

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
