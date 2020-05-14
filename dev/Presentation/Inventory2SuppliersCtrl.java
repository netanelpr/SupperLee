package Presentation;
import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Logic.ShortageOrder;
import Inventory.Persistence.DTO.ItemDTO;
import Inventory.View.InvService;
import Inventory.View.View;
import Result.Result;
import Suppliers.Structs.Days;
import Suppliers.Service.*;

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


    public List<ItemDTO> getAllCatalog()
    {
        /*List<Integer> barcodes=myOrderAndProductManagement.getAllProductBarcodes();

        List<DummyItem> dummyItems = new LinkedList<>();
        for (Integer barcode:
             barcodes) {
            SystemProduct theProduct=myOrderAndProductManagement.getProduct(barcode);
            //((String id, String name, String manufacturer, String category,
            //                     String sub_category, String size, String freqSupply, String cost
            DummyItem newDummyItem= new DummyItem(String.valueOf(theProduct.barcode),theProduct.name,
                    theProduct.manufacture,theProduct.category,theProduct.subCategory,theProduct.size, theProduct.freqSupply);

        }
        return dummyItems;*/
        return null;
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

        return myOrderAndProductManagement.createRegularOrder(productInOrderDTOS,shortageOrder.getShopNum());

    }

    public Result placePeriodicalOrder(List<ProductInOrderDTO> shoppingList, List<Days> days, int weekPeriod, int shopNumber)
    {
        return this.myOrderAndProductManagement.createPeriodicalOrder(shoppingList,days,weekPeriod, shopNumber);
    }

    //TODO: understand how to make this work- which order id? of supplier? of inventory?
    //TODO I delete the result because its didnt work
    public void receiveSupplierOrder(int orderID)
    {
        /*this.myOrderAndProductManagement.changeOrderStatus(orderID, OrderStatus.Close);
        OrderDTO theOrder= this.myOrderAndProductManagement.getOrder(orderID);
        return myInvenoryService.getOrderFromSuppliers(theOrder);*/
        //myInvenoryService.getOrderFromSuppliers();
   }

    public void receiveSuppliersOrderTmp(int orderID){

        List <ProductInOrderDTO> productsLss = new ArrayList();
        ProductInOrderDTO item1 = new ProductInOrderDTO(1, 100, 7.34);
        ProductInOrderDTO item2 = new ProductInOrderDTO(2, 20, 7.6);
        ProductInOrderDTO item3 = new ProductInOrderDTO(3, 70, 798);
        ProductInOrderDTO item4 = new ProductInOrderDTO(4, 200, 9);

        productsLss.add(item1);
        productsLss.add(item2);
        productsLss.add(item3);
        productsLss.add(item4);

        OrderDTO order = new OrderDTO(1, productsLss);

        myInvenoryService.getOrderFromSuppliers(order);


    }


    public Result receiveAllOpenOrders(int shopNum)
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
