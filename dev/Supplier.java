import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Map;

public class Supplier {

    private int supId;
    private int incNum;
    private String accountNumber;
    private String paymentInfo;

    private List<ContactInfo> contacts;
    private ContractWithSupplier contract;

    public Supplier(String name, int incNum, String accountNumber, String paymentInfo){

    }

    public boolean addContactInfo(String name, String phone, String email){
        throw new NotImplementedException();
    }

    /**
     *
     * @param contractDetails
     * @param days
     * @param products
     * @return Map from catId to productID
     */
    public Map<Integer, Integer> addContractInfo(String contractDetails, List<Days> days, List<AddProductInfo> products){
        throw new NotImplementedException();
    }

    /**
     *
     * @param product
     * @return ProductId in the system
     */
    public int addProduct(AddProductInfo product){
        throw new NotImplementedException();
    }

    public List<ProductDiscount> getAmountDiscountReport(int supId){
        throw new NotImplementedException();
    }
}
