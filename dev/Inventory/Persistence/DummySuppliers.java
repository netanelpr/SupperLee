package Inventory.Persistence;

import java.util.HashMap;

public class DummySuppliers {

    public HashMap<DummyItem, Integer> currentOrders;

    public DummySuppliers(){currentOrders = new HashMap<>();}


    public HashMap<DummyItem, Integer> getArrivedOrders() {

        DummyItem item1 = new DummyItem("1", "milk", "tnuva",
                                        "diary", "drinks", "1 liter", "3", "4.30");
        currentOrders.put(item1, 100);

        DummyItem item6 = new DummyItem("6", "polke", "of tov",
                "meat", "chiken", "0.5 kg", "3", "13.35");
        currentOrders.put(item6, 50);

        DummyItem item11 = new DummyItem("11", "banana", "pri ha galil",
                "fruits", "tropical", "4 piece", "2", "0.80");
        currentOrders.put(item11, 4000);

        DummyItem item16 = new DummyItem("16", "onion", "yerek ha galil",
                "vegetables", "spicy", "1 kg", "3", "1.2");
        currentOrders.put(item16, 800);

        DummyItem item1_2 = new DummyItem("1", "milk", "tnuva",
                "diary", "drinks", "1 liter", "3", "7.30");
        //currentOrders.put(item1_2, 100);

        DummyItem item11_2 = new DummyItem("11", "banana", "pri ha galil",
                "fruits", "tropical", "4 piece", "2", "0.2");
       // currentOrders.put(item11_2, 80);

        return currentOrders;
    }

    public void finishOrder() {
        currentOrders = new HashMap<>();
    }
}
