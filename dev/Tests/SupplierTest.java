package Tests;

import Structs.Days;
import Supplier.*;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SupplierTest {
    private Supplier supplier;
    public SupplierTest()
    {
        supplier=new Supplier("Moshe","123","123","CASH","Avi","054","@com");
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void getSupplierName() {
        Assert.assertEquals("Moshe",supplier.getSupplierName());
        Assert.assertNotEquals("hjk",supplier.getSupplierName());
    }

    @Test
    void hasProduct() {
        Assert.assertFalse(supplier.hasProduct(0));


        supplier.addProduct(new AddProduct
                (0,"AXX",5.3,
                        new ProductDiscounts(0,new HashMap<Integer, Double>(),5.3),
                        "A","bamba"));
        Assert.assertFalse(supplier.hasProduct(0));

        this.supplier.addContractInfo("ghj",new LinkedList<>());
        supplier.addProduct(new AddProduct
                (0,"AXX",5.3,
                        new ProductDiscounts(0,new HashMap<Integer, Double>(),5.3),
                        "A","bamba"));
        Assert.assertTrue(supplier.hasProduct(0));
    }

    void addContactInfo() {

    }

    @Test
    void addContractInfo() {
    }

    @Test
    void removePaymentOptions() {
    }

    @Test
    void removeContactFromSupplier() {
    }

    @AfterEach
    void tearDown() {
    }
}