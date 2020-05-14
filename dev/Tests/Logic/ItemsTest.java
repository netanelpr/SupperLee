package Tests.Logic;

import Inventory.Logic.Inventory;
import Inventory.Logic.Item;
import Inventory.View.InvService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemsTest {

    InvService myInvService;
    Inventory testInv;

    @Before
    public void setUp() throws Exception {
        myInvService = InvService.getInstance();
        testInv = myInvService.newShop();
    }

    @After
    public void tearDown() throws Exception {
        myInvService = InvService.getInstance();
        testInv = myInvService.newShop();
    }

    @Test
    public void updateInventorySuppliers() {
//        DummySuppliers dummySuppliers = new DummySuppliers();
        //testInv.getMyItemController().updateInventorySuppliers(dummySuppliers.getArrivedOrders());
        assertEquals(testInv.getItems().size(), 4);
    }

    @Test
    public void updateInventorySuppliersOldItems() {
//        DummySuppliers dummySuppliers = new DummySuppliers();
        //testInv.updateInventorySuppliers(dummySuppliers.getArrivedOrders(), myInvService);
        //testInv.updateInventorySuppliers(dummySuppliers.getArrivedOrders(), myInvService);
        assertEquals(testInv.getItems().size(), 4);
    }

    @Test
    public void newShop() {
        assertEquals(testInv.getItems().size(), 0);
    }

    @Test
    public void checkItemName() {
//        DummySuppliers dummySuppliers = new DummySuppliers();
        //TODO to come back
        //testInv.updateInventorySuppliers(dummySuppliers.getArrivedOrders(), myInvService);
        Item myItem = testInv.getMyItemController().getItems().get("1");
        assertEquals(myItem.getName(), "milk");
    }

}