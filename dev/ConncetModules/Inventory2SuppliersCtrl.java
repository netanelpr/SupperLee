package ConncetModules;
import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Logic.ShortageOrder;
import Inventory.View.InvService;
import Inventory.View.View;
import Result.Result;
import Suppliers.Structs.Days;
import Suppliers.Supplier.ProductInOrder;
import Inventory.Persistence.DummyItem;
import Suppliers.Service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Inventory2SuppliersCtrl implements myObservable {
    private static Inventory2SuppliersCtrl inventory2SuppliersCtrlInstance = null;
    private SupplierManagment mySupplierManger;
    private InvService myInvenoryService;
    private Scanner myScanner;
    private View view;
    public final List<Observer> observers;

    private Inventory2SuppliersCtrl()
    {
        mySupplierManger= new SupplierCtrl();
        myInvenoryService= InvService.getInstance();
        this.myScanner = new Scanner(System.in);
        observers = new ArrayList<>();
        this.view = new View();
        this.register(view);
    }

    public static Inventory2SuppliersCtrl getInstance() {
        if(inventory2SuppliersCtrlInstance == null)
            inventory2SuppliersCtrlInstance = new Inventory2SuppliersCtrl();
        return inventory2SuppliersCtrlInstance;
    }

    public void run(){
        int module = -1;
        while(module != 0) {
            notifyObserver("-- SUPER-LEE main Menu -- press: \n 1) Suppliers \n 2) Inventory \n 0) Exit");
            module = myScanner.nextInt();
            if (module == 1) {//TODO: add your main function
            }
            else if (module == 2) {
                myInvenoryService.mainLoop();
            }
        }
    }

    public List<DummyItem> getAllCatalog()
    {

        //List<AddProduct> productDTOS= mySupplierManger.getCatalog();
        //List<DummyItem> itemsList= new LinkedList<>();
        //for (AddProduct product:
        //     productDTOS) {
        //    DummyItem newDummyItem=new DummyItem(product.barCode,product.name,product.manufacture,product.category,product.sub_category,product.freqSupply,product.originalPrice);
        //    itemsList.add(newDummyItem);
        //}
        //return itemsList;
        return null;
    }
    public Result<Integer> placeNewShortageOrder(ShortageOrder shortageOrder)
    //TODO: talk about the signature, days?, shortageOrder includes Map, ShopNum, order length)
    //public Result<Integer> placeNewShortageOrder(Map<Integer, Integer> shoppingList, Days day)
    {
        notifyObserver("automatic shortage order arrived successfully!");
        notifyObserver(shortageOrder.toString());

        //List<ProductInOrder> productsToOrder;
        //return mySupplierManger.placeShortageOrder(productsToOrder,day);
        return null;

    }

    public Result placePeriodicalOrder(List<ProductInOrder> shoppingList, List<Days> days, int weekPeriod)
    {
        return null;
    }

    public Result receiveSupplierOrder(int orderID)
    {
        return null;
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
