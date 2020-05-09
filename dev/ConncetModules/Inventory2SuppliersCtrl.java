package ConncetModules;
import Result.Result;
import Suppliers.Structs.Days;
import Suppliers.Supplier.AddProduct;
import Suppliers.Supplier.ProductInOrder;
import Suppliers.Supplier.Supplier;
import Suppliers.Supplier.SupplierDetails;
import inv.Persistence.DummyItem;
import inv.View.Service;
import Suppliers.Service.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Inventory2SuppliersCtrl {
    private static Inventory2SuppliersCtrl inventory2SuppliersCtrlInstance =null;
    private SupplierManagment mySupplierManger;
    private Service myInvenoryService;

    private Inventory2SuppliersCtrl()
    {
        mySupplierManger= new SupplierCtrl();
        myInvenoryService= Service.getInstance();
    }

    public static Inventory2SuppliersCtrl getInstance()
    {
        if(inventory2SuppliersCtrlInstance ==null)
        {
            inventory2SuppliersCtrlInstance =new Inventory2SuppliersCtrl();
        }
            return inventory2SuppliersCtrlInstance;
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
    public Result<Integer> placeNewShortageOrder(Map<Integer, Integer> shoppingList, Days day)
    {
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


}
