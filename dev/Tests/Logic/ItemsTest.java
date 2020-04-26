package Tests.Logic;

import inv.Logic.Inventory;
import inv.Logic.Item;
import inv.View.Service;
import inv.Persistence.DummySuppliers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemsTest {

    Service myService;
    Inventory testInv;

    @Before
    public void setUp() throws Exception {
        myService = Service.getInstance();
        testInv = myService.newShop();
    }

    @After
    public void tearDown() throws Exception {
        myService = Service.getInstance();
        testInv = myService.newShop();
    }

    @Test
    public void updateInventorySuppliers() {
        DummySuppliers dummySuppliers = new DummySuppliers();
        testInv.getMyItemController().updateInventorySuppliers(dummySuppliers.getArrivedOrders());
        assertEquals(testInv.getItems().size(), 4);
    }

    @Test
    public void updateInventorySuppliersOldItems() {
        DummySuppliers dummySuppliers = new DummySuppliers();
        testInv.updateInventorySuppliers(dummySuppliers.getArrivedOrders(), myService);
        testInv.updateInventorySuppliers(dummySuppliers.getArrivedOrders(), myService);
        assertEquals(testInv.getItems().size(), 4);
    }

    @Test
    public void newShop() {
        assertEquals(testInv.getItems().size(), 0);
    }

    @Test
    public void checkItemName() {
        DummySuppliers dummySuppliers = new DummySuppliers();
        testInv.updateInventorySuppliers(dummySuppliers.getArrivedOrders(), myService);
        Item myItem = testInv.getMyItemController().getItems().get("1");
        assertEquals(myItem.getName(), "milk");
    }

}