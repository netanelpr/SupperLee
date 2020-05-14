package Suppliers.Supplier;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateSupplierCard {

    private SupplierSystem mySupplierSystem;
    @BeforeEach
    void setUp() {
        mySupplierSystem=SupplierSystem.getInstance();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createSupplierCard() {
        mySupplierSystem.createSupplierCard("Dor","555","Granit","555","Cash","dor","054","@com");
    }
}