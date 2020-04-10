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

    //TODO verify incNum and accountNunber are numbers
    public Supplier(String name, String incNum, String accountNumber, String paymentInfo){
        this.name=name;
        this.incNum=incNum;
        this.accountNumber=accountNumber;
        this.paymentInfo=paymentInfo;
        this.supId=SupplierIDsCounter;
        SupplierIDsCounter++;

        this.contacts = new LinkedList<>();
    }

    //TODO is the return is needed
    public boolean addContactInfo(String name, String phone, String email){
        this.contacts.add(new ContactInfo(name, phone, email));
        return true;
    }

    /**
     *
     * @param contractDetails
     * @param days
     * @return List of products id with wasnt added to the supplier
     */
    public boolean addContractInfo(String contractDetails, List<Days> days){
        this.contract=new ContractWithSupplier(contractDetails,days);

        return true;
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
