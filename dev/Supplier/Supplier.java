package Supplier;

import Structs.Days;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Supplier {
    private static int SupplierIDsCounter=0;
    private int supId;
    private String incNum;
    private String accountNumber;
    private String paymentInfo;

    //Supplier.Supplier Details
    private String name;

    private List<ContactInfo> contacts;
    private ContractWithSupplier contract;

    // TODO: Consider making this constructor private as you need to verify the data e.g
    //  the incNum is bigger than 0. Then if you make it private create a static constructor.
    public Supplier(String name, String incNum, String accountNumber, String paymentInfo){
        this.name=name;
        this.incNum=incNum;
        this.incNum=incNum;
        this.accountNumber=accountNumber;
        this.paymentInfo=paymentInfo;
        this.supId=SupplierIDsCounter;
        SupplierIDsCounter++;
    }

    public boolean addContactInfo(String name, String phone, String email){
        try {
            this.contacts.add(new ContactInfo(name, phone, email));
        }
        catch (Exception e)
        {
            return false;
        }
        return true;

    }

    /**
     *
     * @param contractDetails
     * @param days
     * @return List of products id with wasnt added to the supplier
     */
    public List<Integer> addContractInfo(String contractDetails, List<Days> days){
        this.contract=new ContractWithSupplier(contractDetails,days);
        //@TODO: delete the return value
        return null;
    }

    /**
     *
     * @param product
     * @return true if the product have been added
     */
    public boolean addProduct(AddProduct product){
        try {
            this.contract.addProduct(product);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public List<ProductDiscounts> getAmountDiscountReport(){
        return contract.getAmountDiscountReport();
    }

    public List<SupplierProductInfo> getAllProducts(){
        List<SupplierProductInfo> supplierProductInfos= new LinkedList<SupplierProductInfo>();
        for (ContractProduct product:
                this.contract.getProducts()) {
            supplierProductInfos.add(new SupplierProductInfo(product.getBarCode(),product.getProductCatalogNumber()));
        }
        return supplierProductInfos;
    }
    // TODO: not in class diagram
    public int getSupId(){
        return supId;
    }

    // TODO: not in class diagram
    public String getPaymentInfo(){
        return paymentInfo;
    }

    // TODO: not in class diagram
    public String getSupplierName(){
        return name;
    }

    public boolean addDiscountToProduct(int barcode,int amount, double discount)
    {
        return contract.addDiscountToProduct(barcode,amount,discount);
    }
}
