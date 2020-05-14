package Inventory.Logic;

import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DTO.ItemDTO;
import Inventory.Persistence.Mappers.ItemToProductMapper;
import DataAccess.SupInvDBConn;
import Inventory.Persistence.Mappers.ItemsMapper;
import Suppliers.Service.OrderDTO;
import Suppliers.Service.ProductInOrderDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class itemsController implements myObservable {

    private HashMap<String, Item> items; //item id, item
    private final List<Observer> observers;
    private ItemToProductMapper myItemToProductMapper;
    private ItemsMapper myItemMapper;

    public itemsController(Observer o) {
        //this.myScanner = new Scanner(System.in);
        this.items = new HashMap<>();
        observers = new ArrayList<>();
        this.register(o);
        this.myItemToProductMapper = new ItemToProductMapper(SupInvDBConn.getInstance());
        this.myItemMapper = new ItemsMapper((SupInvDBConn.getInstance()));
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    //region update items
    public OrderItem updateInventoryWorkers(String id, int quanMissStock, int quanMissShop) {
        return items.get(id).updateMyQuantities(quanMissStock, quanMissShop, '-');
    }

    public void updateInventorySuppliers(OrderDTO order) { //int -> quantity

        for (ProductInOrderDTO p: order.productInOrderDTOList) {
            if(items.containsKey(String.valueOf(p.barcode))) {
                Item currItem = items.get(String.valueOf(p.barcode));
                currItem.updateMyQuantities(p.amount, 0, '+');
            }
            else
            {
                ItemDTO itemDTO = (myItemToProductMapper.loadById(String.valueOf(p.barcode), String.valueOf(order.shopID)));
                Item newItem = new Item(observers.get(0), itemDTO);
                newItem.updateMyQuantities(p.amount, 0, '+');
                items.put(newItem.getId(), newItem);
                myItemMapper.insert(itemDTO);
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
        HashMap<String, ItemDTO> itemsDTO = myItemToProductMapper.loadInvFromItemsAndProducts(shopNum);
        for (String id : itemsDTO.keySet()) {
            items.put(id, new Item(observers.get(0), itemsDTO.get(id)));
        }
    }
    //endregion


}
