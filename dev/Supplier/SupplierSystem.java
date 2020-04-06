package Supplier;

import Service.AddProductInfo;
import Service.ProductInOrder;
import Service.SupplierDetails;
import Service.SupplierProduct;
import Structs.Days;
import Structs.OrderStatus;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class SupplierSystem {

    private Map<Integer, Supplier> suppliers;
    private Map<Integer,Order> orders;

    public SupplierSystem() {
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
     * @return List<Service.SupplierDetails> for each supplier in the system.
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

    //TODO maybe return RESULT or throw exception

    /**
     * Add person contact information.
     * @param supplierId
     * @param contactPersonName
     * @param phoneNumber
     * @param email
     * @return
     */
    public boolean addContactInfo(int supplierId, String contactPersonName, String phoneNumber, String email) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);

        if(supplier == null){
            return false;
        }

        return supplier.addContactInfo(contactPersonName, phoneNumber, email);
    }

    /**
     * Add contract with the supplier
     * @param supplierId
     * @param contractInfo
     * @param days
     * @param products
     * @return
     */
    public Map<Integer, Integer> addContractInfo(int supplierId, String contractInfo, List<Days> days, List<AddProductInfo> products) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);

        if(supplier == null){
            return null;
        }

        return supplier.addContractInfo(contractInfo, days, products);
    }


    public int addProductToContract(int supplierId, AddProductInfo product) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);

        if(supplier == null){
            return -1;
        }

        return supplier.addProduct(product);

    }


    public List<ProductDiscount> getAmountDiscountReport(int supplierId) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);

        if(supplier == null){
            return null;
        }

        return supplier.getAmountDiscountReport();
    }


    public int createNewOrder(int supplierId, List<ProductInOrder> products, DateTimeFormatter time) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);

        if(supplier == null){
            return -1;
        }

        Order order = Order.CreateOrder(products);
        orders.put(order.getOrderId(), order);

        return order.getOrderId();
    }


    public boolean updateOrderArrivalTime(int supplierID, int orderID, DateTimeFormatter time) {
        return false;
    }


    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        Order order = orders.getOrDefault(orderId, null);

        if(order == null){
            return false;
        }

        return order.setStatus(status);
    }


    public List<Product> getAllSupplierProducts(int supplierId) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);
        List<SupplierProduct> supplierProducts = new LinkedList<>();

        if(supplier == null){
            return null;
        }
        return supplier.getAllProducts();
    }


    public List<Integer> getPurchaseHistory(int supplierID) {
        return null;
    }


    public List<Days> getProductSupplyTimingInterval(int supplierID) {
        return null;
    }
}
