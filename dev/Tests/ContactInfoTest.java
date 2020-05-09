package Tests;

import Suppliers.Supplier.ContactInfo;
import org.junit.Assert;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class ContactInfoTest {
    private ContactInfo contactInfo;

    @BeforeEach
    void setUp() {
        contactInfo=new ContactInfo("David","054","@com");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPhoneNumber() {
        Assert.assertEquals(contactInfo.getPhoneNumber(),"054");
    }

    @Test
    void getEmail() {
        Assert.assertEquals(contactInfo.getEmail(),"@com");
    }

    @Test
    void getName() {
        Assert.assertEquals(contactInfo.getName(),"David");
    }
}