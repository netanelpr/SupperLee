package ConncetModules;
import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Logic.ShortageOrder;
import Inventory.View.InvService;
import Inventory.View.View;
import Result.Result;
import Suppliers.Structs.Days;
import Suppliers.Structs.OrderStatus;
import Suppliers.Supplier.Product;
import Suppliers.Supplier.ProductInOrder;
import Inventory.Persistence.DummyItem;
import Suppliers.Service.*;

import java.security.Provider;
import java.util.*;

public class Inventory2SuppliersCtrl implements myObservable {
    private static Inventory2SuppliersCtrl inventory2SuppliersCtrlInstance = null;

    private SupplierManagment mySupplierManagment;
    private OrderAndProductManagement myOrderAndProductManagement;

    private InvService myInvenoryService;
    private Scanner myScanner;
    private View view;
    public final List<Observer> observers;

    private Inventory2SuppliersCtrl()
    {
        mySupplierManagment= new SupplierCtrl();
        myOrderAndProductManagement= new OrderAndProductCtrl();
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
        myInvenoryService.loadDB();


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
        List<Integer> barcodes=myOrderAndProductManagement.getAllProductBarcodes();

        List<DummyItem> dummyItems = new LinkedList<>();
        for (Integer barcode:
             barcodes) {
            SystemProduct theProduct=myOrderAndProductManagement.getProduct(barcode);
            //((String id, String name, String manufacturer, String category,
            //                     String sub_category, String size, String freqSupply, String cost
            DummyItem newDummyItem= new DummyItem(String.valueOf(theProduct.barcode),theProduct.name,
                    theProduct.manufacture,theProduct.category,theProduct.subCategory,theProduct.size, theProduct.freqSupply);

        }
        return dummyItems;
    }
    public Result<Integer> placeNewShortageOrder(ShortageOrder shortageOrder)
    {
        notifyObserver("automatic shortage order arrived successfully!");
        notifyObserver(shortageOrder.toString());

        //List<ProductInOrder> productsToOrder;
        //return mySupplierManger.placeShortageOrder(productsToOrder,day);
        List<ProductInOrderDTO> productInOrderDTOS= new LinkedList<>();
        HashMap<Integer,Integer> order=shortageOrder.getOrder();
        for (Integer barcode:
             order.keySet()) {
            ProductInOrderDTO newProduct= new ProductInOrderDTO(barcode,order.get(barcode));
            productInOrderDTOS.add(newProduct);
        }

        return myOrderAndProductManagement.createRegularNewOrder(productInOrderDTOS,shortageOrder.getShopNum());

    }

    public Result placePeriodicalOrder(List<ProductInOrderDTO> shoppingList, List<Days> days, int weekPeriod)
    {
        return this.myOrderAndProductManagement.createPeriodicalOrder(shoppingList,days,weekPeriod);
    }

    //TODO: understand how to make this work- which order id? of supplier? of inventory?
    public Result receiveSupplierOrder(int orderID)
    {
        this.myOrderAndProductManagement.changeOrderStatus(orderID, OrderStatus.Close);
        OrderDTO theOrder= this.myOrderAndProductManagement.getOrder(orderID);
        return myInvenoryService.getOrderFromSuppliers(theOrder);

    }

    public Result receiveAllOpenOrders(int shopNum)
    {

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
