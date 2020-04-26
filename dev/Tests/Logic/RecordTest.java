package Tests.Logic;

import inv.Logic.Inventory;
import inv.View.Service;
import inv.Persistence.DummySuppliers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecordTest {

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
    public void updateRecordsSuppliers() {
        DummySuppliers dummySuppliers = new DummySuppliers();
        testInv.getMyRecoredController().updateRecordsSuppliers(dummySuppliers.getArrivedOrders(), testInv, myService);
        assertEquals(testInv.getRecords().size(), 4);
    }

    @Test
    public void updateRecordsSuppliersOld() {
        DummySuppliers dummySuppliers = new DummySuppliers();
        testInv.getMyRecoredController().updateRecordsSuppliers(dummySuppliers.getArrivedOrders(), testInv, myService);
        testInv.getMyRecoredController().updateRecordsSuppliers(dummySuppliers.getArrivedOrders(), testInv, myService);
        assertEquals(testInv.getRecords().size(), 4);
    }

    @Test
    public void updateRecordsSuppliersNewShop() {
        assertEquals(testInv.getRecords().size(), 0);
    }
}