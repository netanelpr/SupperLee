package Tests.Logic;

import Inventory.Logic.Inventory;
import Inventory.View.InvService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class defectiveTest {

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
    public void updateDefectivesSuppliers() {
//        DummySuppliers dummySuppliers = new DummySuppliers();
//        testInv.getMyDefectivesController().updateDefectivesSuppliers(dummySuppliers.getArrivedOrders());
//        assertEquals(testInv.getDefectives().size(), 4);
    }

    @Test
    public void updateDefectivesSuppliersOld() {
//        DummySuppliers dummySuppliers = new DummySuppliers();
//        testInv.getMyDefectivesController().updateDefectivesSuppliers(dummySuppliers.getArrivedOrders());
//        testInv.getMyDefectivesController().updateDefectivesSuppliers(dummySuppliers.getArrivedOrders());
//        assertEquals(testInv.getDefectives().size(), 4);
    }

    @Test
    public void updateDefectivesSuppliersNewShop() {
        assertEquals(testInv.getDefectives().size(), 0);
    }
}