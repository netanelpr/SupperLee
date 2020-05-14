package Inventory.Logic;
import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DTO.ItemDTO;
import Inventory.Persistence.DummyItem;

import java.util.ArrayList;
import java.util.List;


public class Item implements myObservable {

    //region fields
    //supplier item info
    private String id;
    private String name;

    private String shopNum;

    private String manufacturer;
    private String category;
    private String sub_category;
    private String size;
    private String freqBuySupply;
    //item info
    private int quanStrg = 0;
    private int quantShop = 0 ;
    private int totalQuantity = 0;
    private int capacityShop = 50;
    private boolean minimum = false;//for alerts
    public final List<Observer> observers;
    //endregion

    public Item(Observer o, ItemDTO itemDTO) {
        this.observers = new ArrayList<>();
        this.register(o);
        this.id = itemDTO.getId();
        this.name = itemDTO.getName();
        this.manufacturer = itemDTO.getManufacturer();
        this.category = itemDTO.getCategory();
        this.sub_category = itemDTO.getSub_category();
        this.size = itemDTO.getSize();
        this.freqBuySupply = String.valueOf(itemDTO.getFreqBuySupply());
    }

    //region getters
    public String getCategory() { return this.category; }
    public boolean getIfQuantityMinimum() { return this.minimum; }
    public String getId() { return id; }
    public String getName() { return this.name; }
    //endregion

    //region item functions
    public OrderItem updateMyQuantities(int qstrg, int qshop, char c) {
        if(c == '-'){
            this.quanStrg -= qstrg;
            this.quantShop -= qshop;
            //this.totalQuantity -= (qstrg + qshop);
        }
        else{
            this.quanStrg += qstrg;
            this.quantShop += qshop;
            //this.totalQuantity += (qstrg + qshop);
        }
        //TODO increase decrease quantity
        int missInShop = this.capacityShop - this.quantShop;
        if (missInShop > 0 & this.quanStrg > 0)
        {
            if( quanStrg >= missInShop) //enough in storge to fill the shop
            {
                this.quanStrg = quanStrg - missInShop;
                this.quantShop = capacityShop;
            }
            else //taking all storage to fill the shop
            {
                this.quantShop = quantShop + this.quanStrg;
                this.quanStrg = 0;
            }
        }
        if(quanStrg < 0) quanStrg = 0;
        if(quantShop < 0) quantShop = 0;
        totalQuantity = quanStrg+quantShop;

        //TODO: write the func updateItemDB();... because we changed item's quantities here

        if(this.checkMinimumQuant())
            return issueOrderForShortageItem();
        return null;
    }

    private OrderItem issueOrderForShortageItem() {
        int quantityToOrder = (Integer.parseInt(freqBuySupply)*10 + 10) - totalQuantity;
        return new OrderItem(Integer.parseInt(id), quantityToOrder);
    }

    private Boolean checkMinimumQuant() {

        if((totalQuantity) < Integer.parseInt(freqBuySupply)*10)
        {
            minimum = true;
            notifyObserver("|--------------------------------------------------\n" +
                    "|Alert! this product ENDED. order more!");
        }
        else if((totalQuantity) < Integer.parseInt(freqBuySupply)*10 + 10)
        {
            minimum = true;
            notifyObserver("|--------------------------------------------------\n" +
                    "|Alert! this product is about to end, need to order more");
        }
        else if(quanStrg == 0)
        {
            minimum = true;
            notifyObserver("|--------------------------------------------------\n" +
                    "|Alert!! this product ENDED in the storage. order more!");
        }
        else
        {
            minimum = false;
        }
        itemStatusUpdated();
        return minimum;
    }
    public void itemStatus() {
        notifyObserver("|--------------------------------------------------\n" +
                "| id; " + id + " name; " + name + "\n" +
                "| manufacturer; " + manufacturer + "\n" +
                "| quanStrg; " + quanStrg +
                " quantShop; " + quantShop + "\n" +
                "| totalQuantity; " + totalQuantity + "\n" +
                "| category; " + category +
                " sub_category; " + sub_category +
                " size; " + size + "\n" +
                "| freqBuySupply; " + freqBuySupply +
                " minimum; " + minimum + " \n" +
                "|--------------------------------------------------\n");
    }
    public void itemStatusUpdated() {
        notifyObserver("|--------------------------------------------------\n" +
                "| id; " + id + " name; " + name + "\n" +
                "| quanStrg; " + quanStrg +
                " quantShop; " + quantShop + "\n" +
                "| totalQuantity; " + totalQuantity +
                " minimum; " + minimum + " \n" +
                "|--------------------------------------------------\n");
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
