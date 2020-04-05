import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SupplierSystem {
    private List<Supplier> suppliers;
    private List<Order> orders;

    public SupplierSystem()
    {
        suppliers= new LinkedList<Supplier>();
        orders= new LinkedList<Order>();
    }

    public int createSupplierCard(String name, String incNum, String AccountNumber, String PaymentInfo) {
        return 0;
    }


    public String getPaymentOptions() {
        return null;
    }


    public List<SupplierDetails> getAllSuppliers() {
        return null;
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
