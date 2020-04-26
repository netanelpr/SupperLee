package Tests.Logic;

import inv.Logic.Inventory;
import inv.View.Service;
import inv.Persistence.DummySuppliers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class defectiveTest {

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
    public void updateDefectivesSuppliers() {
        DummySuppliers dummySuppliers = new DummySuppliers();
        testInv.getMyDefectivesController().updateDefectivesSuppliers(dummySuppliers.getArrivedOrders());
        assertEquals(testInv.getDefectives().size(), 4);
    }

    @Test
    public void updateDefectivesSuppliersOld() {
        DummySuppliers dummySuppliers = new DummySuppliers();
        testInv.getMyDefectivesController().updateDefectivesSuppliers(dummySuppliers.getArrivedOrders());
        testInv.getMyDefectivesController().updateDefectivesSuppliers(dummySuppliers.getArrivedOrders());
        assertEquals(testInv.getDefectives().size(), 4);
    }

    @Test
    public void updateDefectivesSuppliersNewShop() {
        assertEquals(testInv.getDefectives().size(), 0);
    }
}