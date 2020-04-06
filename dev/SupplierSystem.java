import java.time.format.DateTimeFormatter;
import java.util.*;

public class SupplierSystem {

    private Map<Integer, Supplier> suppliers;
    private Map<Integer,Order> orders;

    public SupplierSystem()
    {
        suppliers = new HashMap<>();
        orders = new HashMap<>();
    }

    //TODO: is the name parameter is necessary?
    /**
     * Create new supplier in the system
     * @param name The name of the supplier
     * @param incNum incupartion number
     * @param accountNumber Bank account
     * @param paymentInfo
     * @return -1 if cant create a new supplier otherwise return new supplier ID in the system.
     */
    public int createSupplierCard(String name, int incNum, String accountNumber, String paymentInfo) {
        Supplier sup = new Supplier(name, incNum, accountNumber, paymentInfo);

        if(sup.getSupId() != -1){
            return -1;
        }

        return sup.getSupId();
    }


    //TODO verify the function in the class diagram

    /**
     * Return the payment information of specific supplier.
     * @param supId ID of the supplier
     * @return null if the supplier doesnt exist in the system, otherwise its payment information
     */
    public String getPaymentOptions(int supId) {
        Supplier sup = suppliers.getOrDefault(supId, null);

        if(sup == null){
            return  null;
        }

        return sup.getPaymentInfo();
    }


    /**
     * Return the details for each supplier in the system.
     * @return List<SupplierDetails> for each supplier in the system.
     */
    public List<SupplierDetails> getAllSuppliers() {
        List<SupplierDetails> supDetails = new LinkedList<>();

        for(Integer key : suppliers.keySet()){
            Supplier supplier = suppliers.get(key);
            supDetails.add(new SupplierDetails(
                    supplier.getSupId(),
                    supplier.getSupplierName()));
        }

        return  supDetails;
    }


    public boolean addContactInfo(int supplierID, String contactPersonName, String phoneNumber, String Email) {
        return false;
    }


    public Map<Integer, Integer> addContractInfo(int supplierID, List<Days> days, List<AddProductInfo> products) {
        return null;
    }


    public int ProductToContract(int supplierID, AddProductInfo product) {
        return 0;
    }


    public List<ProductDiscount> getAmountDiscountReport(int supplierID) {
        return null;
    }


    public int createNewOrder(int supplierID, Map<Integer, Integer> products, DateTimeFormatter time) {
        return 0;
    }


    public boolean updateOrderArrivalTime(int supplierID, int orderID, DateTimeFormatter time) {
        return false;
    }


    public boolean updateOrderStatus(OrderStatus status) {
        return false;
    }


    public List<SupplierProduct> getAllSupplierProducts(int supplierID) {
        return null;
    }


    public List<Integer> getPurchaseHistory(int supplierID) {
        return null;
    }


    public List<Days> getProductSupplyTimingInterval(int supplierID) {
        return null;
    }
}
