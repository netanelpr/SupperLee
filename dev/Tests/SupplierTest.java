package Tests;

import Suppliers.Supplier.*;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

class SupplierTest {
    private Supplier supplier;
    public SupplierTest()
    {
    }

    @BeforeEach
    void setUp() {
        supplier=new Supplier("Moshe","123","123","CASH","Avi","054","@com");
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

    @Test
    void addContactInfo() {
        String name = "T1";
        String phoneNumber = "05111111";
        String email = "T1@gmail.com";

        Assert.assertTrue(supplier.addContactInfo(name, phoneNumber, email));

        List<ContactInfo> contacts = supplier.getContacts();

        boolean inContact = false;
        for(ContactInfo c : contacts){
            if(c.getName().equals(name) &&
                c.getPhoneNumber().equals(phoneNumber) &&
                c.getEmail().equals(email)){
                inContact = true;
                break;
            }
        }

        Assert.assertTrue("Didnt add the contact to supplier", inContact);
    }

    @Test
    void removeContactFromSupplier() {
        List<ContactInfo> contacts;

        Assert.assertFalse("Remove true to to remove contact when there is only one ",supplier.RemoveContactFromSupplier("@com"));

        contacts = supplier.getContacts();
        Assert.assertFalse("Remove contact when there is only one ", contacts.isEmpty());

        String name = "T1";
        String phoneNumber = "05111111";
        String email = "T1@gmail.com";

        Assert.assertTrue(supplier.addContactInfo(name, phoneNumber, email));
        supplier.RemoveContactFromSupplier(email);

        Assert.assertEquals(contacts, supplier.getContacts());
    }

    @Test
    void removePaymentOptions() {
        String toAdd = "A";

        String[] initPaymentInfo = supplier.getPaymentInfo().split(" ,");
        Arrays.sort(initPaymentInfo);

        Assert.assertTrue(supplier.addPaymentOptions(new String[]{toAdd}));

        String[] afterAddtion = supplier.getPaymentInfo().split(" ,");
        Assert.assertTrue(afterAddtion.length != initPaymentInfo.length || !Arrays.asList(afterAddtion).contains(toAdd));

    }

    @AfterEach
    void tearDown() {
    }
}