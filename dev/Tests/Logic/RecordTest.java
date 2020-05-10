package Tests.Logic;

import Inventory.Logic.Inventory;
import Inventory.View.InvService;
import Inventory.Persistence.DummySuppliers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecordTest {

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
    public void updateRecordsSuppliers() {
        DummySuppliers dummySuppliers = new DummySuppliers();
        testInv.getMyRecoredController().updateRecordsSuppliers(dummySuppliers.getArrivedOrders(), testInv, myInvService);
        assertEquals(testInv.getRecords().size(), 4);
    }

    @Test
    public void updateRecordsSuppliersOld() {
        DummySuppliers dummySuppliers = new DummySuppliers();
        testInv.getMyRecoredController().updateRecordsSuppliers(dummySuppliers.getArrivedOrders(), testInv, myInvService);
        testInv.getMyRecoredController().updateRecordsSuppliers(dummySuppliers.getArrivedOrders(), testInv, myInvService);
        assertEquals(testInv.getRecords().size(), 4);
    }

    @Test
    public void updateRecordsSuppliersNewShop() {
        assertEquals(testInv.getRecords().size(), 0);
    }
}