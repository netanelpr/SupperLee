package Tests;

import Suppliers.Supplier.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

class ContractWithSupplierTest {
    private ContractWithSupplier supplierContract;
    public ContractWithSupplierTest()
    {

    }
    @BeforeEach
    void setUp() {
        supplierContract=new ContractWithSupplier("hello",new LinkedList<>());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addProduct() {
        AddProduct product1= new AddProduct(5,"55",5.3,
                new ProductDiscounts(5,new HashMap<Integer, Double>(),5.3),"osem","bamba");
        AddProduct product2= new AddProduct(16,"55x",5.6,
                new ProductDiscounts(16,new HashMap<Integer, Double>(),5.6),"elit","kifkef");
        boolean contains1=false;
        boolean contains2=false;
        for (int i = 0; i < supplierContract.getProducts().size(); i++) {
            contains1=contains1||supplierContract.getProducts().get(i).getBarCode()==product1.barCode;
            contains2=contains2||supplierContract.getProducts().get(i).getBarCode()==product2.barCode;
        }
        Assert.assertFalse(contains1);
        Assert.assertFalse(contains2);
        supplierContract.addProduct(product1);
        contains1=false;
        contains2=false;
        for (int i = 0; i < supplierContract.getProducts().size(); i++) {
            contains1=contains1||supplierContract.getProducts().get(i).getBarCode()==product1.barCode;
            contains2=contains2||supplierContract.getProducts().get(i).getBarCode()==product2.barCode;
        }
        Assert.assertTrue(contains1);
        Assert.assertFalse(contains2);
        supplierContract.addProduct(product2);
        contains1=false;
        contains2=false;
        for (int i = 0; i < supplierContract.getProducts().size(); i++) {
            contains1=contains1||supplierContract.getProducts().get(i).getBarCode()==product1.barCode;
            contains2=contains2||supplierContract.getProducts().get(i).getBarCode()==product2.barCode;
        }
    }

    @Test
    void addDiscountToProduct() {
        AddProduct product1= new AddProduct(128,"128",128.0,
                new ProductDiscounts(128,new HashMap<Integer, Double>(),128.0),"Salmon","kineret");
        AddProduct product2= new AddProduct(122,"122",10.0,
                new ProductDiscounts(122,new HashMap<Integer, Double>(),10.0),"Water","Eden");
        this.supplierContract.addProduct(product1);
        this.supplierContract.addProduct(product2);
        int index=0;
        boolean contains1=false;
        for (int i = 0; i < supplierContract.getProducts().size(); i++) {
            if(supplierContract.getProducts().get(i).getBarCode()==product1.barCode)
            {
                index=i;
                contains1=true;
            }
        }

        Assert.assertTrue(contains1);
        supplierContract.addDiscountToProduct(128,50,0.3);
        ContractProduct cp=supplierContract.getProducts().get(index);
        boolean secondCheck=cp.getDiscounts().discountPerAmount.containsKey(50);
        Assert.assertTrue(secondCheck);
        if(secondCheck)
        {
            double discount=supplierContract.getProducts().get(index).getDiscounts().discountPerAmount.get(50);
            Assert.assertEquals(0.3,discount,0);
            boolean checkOther=supplierContract.getProducts().stream().filter(x->x.getBarCode()==122).findFirst().orElse(null).getDiscounts().discountPerAmount.containsKey(50);
            Assert.assertFalse(checkOther);

        }


    }
}