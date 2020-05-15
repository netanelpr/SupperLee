package Suppliers.Supplier;

import Suppliers.DataAccess.SupDBConn;
import Suppliers.Service.ContactInfoDTO;
import Suppliers.Service.SupplierProductDTO;
import Suppliers.Service.SystemProduct;
import Suppliers.Structs.Days;

import Suppliers.Structs.StructUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateSupplierCard {

    private SupplierSystem mySupplierSystem;
    @BeforeEach
    void setUp() {
        mySupplierSystem=SupplierSystem.getInstance();
        Connection myDbc=SupDBConn.getInstance();
        try(PreparedStatement pstmt = myDbc.prepareStatement("Delete from Contact_info"))
        {
            pstmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try(PreparedStatement pstmt = myDbc.prepareStatement("Delete from Supplier_paymentOptions"))
        {
            pstmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try(PreparedStatement pstmt = myDbc.prepareStatement("Delete from supplay_days"))
        {
            pstmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try(PreparedStatement pstmt = myDbc.prepareStatement("Delete from Discount_of_product_in_contract"))
        {
            pstmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try(PreparedStatement pstmt = myDbc.prepareStatement("Delete from Product_in_contract"))
        {
            pstmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try(PreparedStatement pstmt = myDbc.prepareStatement("Delete from Contract"))
        {
            pstmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try(PreparedStatement pstmt = myDbc.prepareStatement("Delete from Supplier"))
        {
            pstmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }





    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createSupplierCard() {
        int supID=mySupplierSystem.createSupplierCard("Dor","555","Granit","555","Cash","dor","054","@com");
        //mySupplierSystem.addContactInfo(23,"lior","054300290","l@com");
        //mySupplierSystem.RemoveContactFromSupplier(23,"l@com");
        //mySupplierSystem.addPaymentOptions(23,"BitApp");
        //mySupplierSystem.removePaymentOptions(23,"BitApp");
        List<Days> daysList= new LinkedList<>();
        daysList.add(Days.Sunday);
        daysList.add(Days.Friday);

        List<AddProduct> products= new LinkedList<>();
        ProductDiscounts productDiscounts= new ProductDiscounts(550,30.0);
        productDiscounts.addDiscountPerAmount(1,0.3);
        //int barCode, String productCatalogNumber, double originalPrice ,ProductDiscounts productDiscounts
        products.add(new AddProduct(550,"CatalogAAA",500,productDiscounts));

        System.out.println(mySupplierSystem.addContractToSupplier(supID,"My cool contract",daysList,products ));

        System.out.println(mySupplierSystem.getSupplierInformation(supID).toString());

        System.out.println(mySupplierSystem.getSupplierContacts(supID));
        List<ContactInfoDTO> contacts=mySupplierSystem.getSupplierContacts(supID);
        for (int i = 0; i <contacts.size() ; i++) {
            System.out.println(contacts.get(i).getEmail());
            System.out.println(contacts.get(i).getPhoneNumber());
            System.out.println(contacts.get(i).getName());
            System.out.println(contacts.get(i).getSupID());
        }

        mySupplierSystem.getSupplierContractInfo(supID);

        System.out.println(mySupplierSystem.getSupplierContractInfo(supID).contractDetails);
        for (String day: mySupplierSystem.getSupplierContractInfo(supID).dailyInfo) {
            System.out.println(day);
        }
        for (int i = 0; i < mySupplierSystem.getSupplyingDaysNumbersBySupID(supID).size(); i++) {
            System.out.println(mySupplierSystem.getSupplyingDaysNumbersBySupID(supID).get(i));
            System.out.println(mySupplierSystem.getSupplyingDaysNamesBySupID(supID).get(i));
        }
        System.out.println("offered:");
        for (int i = 0; i < this.mySupplierSystem.getOfferedPaymentOptions().size(); i++) {
            System.out.println(this.mySupplierSystem.getOfferedPaymentOptions().get(i));
        }
        System.out.println("sups options:");
        for (int i = 0; i < this.mySupplierSystem.getPaymentOptions(supID).size(); i++) {
            System.out.println(this.mySupplierSystem.getPaymentOptions(supID).get(i));
        }

        List<SupplierProductDTO> productDTOS=this.mySupplierSystem.getAllSupplierProducts(supID);
        for (SupplierProductDTO product:productDTOS) {
            System.out.println("barcode "+product.barcode);
            System.out.println("originalPrice: " +product.originalPrice);
            System.out.println("catalogNumber: "+product.productCatalogNumber);
            for (int amount:
                 product.discounts.discountPerAmount.keySet()) {
                System.out.println("amount: "+amount+" discount: "+product.discounts.discountPerAmount.get(amount));
            }
        }




    }
}