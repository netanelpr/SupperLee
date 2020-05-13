package Inventory.Logic;

import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DTO.ItemDTO;
import Inventory.Persistence.DummyItem;
import Inventory.Persistence.Mappers.ItemToProductMapper;
import DataAccess.SupInvDBConn;
import Inventory.Persistence.Mappers.ItemsMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class itemsController implements myObservable {

    private HashMap<String, Item> items; //item id, item
    private final List<Observer> observers;
    private ItemToProductMapper MyItemToProductMapper;
    private ItemsMapper myItemMapper;

    public itemsController(Observer o) {
        //this.myScanner = new Scanner(System.in);
        this.items = new HashMap<>();
        observers = new ArrayList<>();
        this.register(o);
        this.MyItemToProductMapper = new ItemToProductMapper(SupInvDBConn.getInstance());
        this.myItemMapper = new ItemsMapper((SupInvDBConn.getInstance()));
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    //region update items
    public OrderItem updateInventoryWorkers(String id, int quanMissStock, int quanMissShop) {
        return items.get(id).updateMyQuantities(quanMissStock, quanMissShop, '-');
    }

    public void updateInventorySuppliers(HashMap<ItemDTO, Integer> supply) { //int -> quantity

        for (ItemDTO currDTO : supply.keySet()) {
            if(items.containsKey(currDTO.getId())) {
                Item currItem = items.get(currDTO.getId());
                currItem.updateMyQuantities(supply.get(currDTO), 0, '+');
            }
            else
            {
                Item newItem = new Item(observers.get(0), currDTO);
                newItem.updateMyQuantities(supply.get(currDTO), 0, '+');
                items.put(newItem.getId(), newItem);
                myItemMapper.insert(currDTO);
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

    public void loadItemsFromDB(String shopNum) {
        HashMap<String, ItemDTO> itemsDTO = MyItemToProductMapper.loadInvFromItemsAndProducts(shopNum);
        for (String id : itemsDTO.keySet()) {
            items.put(id, new Item(observers.get(0), itemsDTO.get(id)));
        }
    }
    //endregion

}
