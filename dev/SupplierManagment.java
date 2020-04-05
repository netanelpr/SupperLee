import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SupplierManagment {

    public int createSupplierCard(String name, String incNum, String AccountNumber, String PaymentInfo);
    public String getPaymentOptions();
    public List<SupplierDetails> getAllSuppliers();
    public boolean addContactInfo(int supplierID, String contactPersonName, String phoneNumber, String Email);
    public Map<Integer,Double> addContractInfo(int supplierID, List<Days> days, List<AddProductInfo> products);
    public int ProductToContract(int supplierID, AddProductInfo product);
    public List<ProductDiscount> getAmountDiscountReport(int supplierID);
    public int createNewOrder(int supplierID, Map<Integer,Double> products, DateTimeFormatter time);
    public boolean updateOrderArrivalTime(int supplierID, int orderID, DateTimeFormatter time);
    public boolean updateOrderStatus(OrderStatus status);
    public List<SupplierProduct> getAllSupplierProducts(int supplierID);
    public List<Integer> getPurchaseHistory(int supplierID);
    public List<Days> getProductSupplyTimingInterval(int supplierID);




}
