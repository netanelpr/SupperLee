package Supplier;

import Service.AddProductInfoDTO;
import Structs.Days;

import java.util.List;
import java.util.Map;

public class Supplier {

    private int supId;
    private int incNum;
    private String accountNumber;
    private String paymentInfo;

    //Supplier.Supplier Details
    private String name;

    private List<ContactInfo> contacts;
    private ContractWithSupplier contract;

    // TODO: Consider making this constructor private as you need to verify the data e.g
    //  the incNum is bigger than 0. Then if you make it private create a static constructor.
    public Supplier(String name, String incNum, String accountNumber, String paymentInfo){

    }

    public boolean addContactInfo(String name, String phone, String email){
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param contractDetails
     * @param days
     * @param products
     * @return Map from catId to productID
     */
    public Map<Integer, Integer> addContractInfo(String contractDetails, List<Days> days, List<AddProduct> products){
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param product
     * @return ProductId in the system
     */
    public int addProduct(AddProduct product){
        throw new UnsupportedOperationException();
    }

    public List<ProductDiscount> getAmountDiscountReport(){
        throw new UnsupportedOperationException();
    }

    public List<ContractProduct> getAllProducts(){
        throw new UnsupportedOperationException();
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
}
