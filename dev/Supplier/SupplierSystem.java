package Supplier;

import Structs.Days;
import Structs.OrderStatus;
import java.util.*;

public class SupplierSystem {

    //TODO check if to make the add product an atomic procedure
    // add to both supplier contract and the products.
    private Map<Integer, Supplier> suppliers;
    private Map<Integer,List<Order>> orders;

    private Map<Integer, Order> orderIdToOrder;

    private ProductsManager productsManager;
    private String[] paymentOptions;

    public SupplierSystem() {
        suppliers = new HashMap<>();
        orders = new HashMap<>();
        orderIdToOrder = new HashMap<>();

        productsManager = new ProductsManager();
        paymentOptions = new String[]{"CASH", "BANKTRANFOR","PAYMENTS"};
    }

    /**
     * Create new supplier in the system
     * @param name The name of the supplier
     * @param incNum incupartion number
     * @param accountNumber Bank account
     * @param paymentInfo
     * @return -1 if cant create a new supplier otherwise return new supplier ID in the system.
     */
    public int createSupplierCard(String name, String incNum, String accountNumber, String paymentInfo) {

        if(Arrays.binarySearch(paymentOptions,paymentInfo.toUpperCase()) < 0){
            return -1;
        }

        Supplier sup = new Supplier(name, incNum, accountNumber, paymentInfo);

        if(sup.getSupId() < 0){
            return -1;
        }

        orders.put(sup.getSupId(), new LinkedList<>());
        suppliers.put(sup.getSupId(),sup);
        return sup.getSupId();
    }

    public String getPaymentOptions(){
        return String.join(" ", paymentOptions);
    }

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
    // TODO maybe return just the id
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
    /**
     * Add person contact information to specific supplier
     * @param supplierId ID for the supplier
     * @param contactPersonName Person name
     * @param phoneNumber Phone number
     * @param email Email
     * @return True if the contact as been added.
     */
    public boolean addContactInfo(int supplierId, String contactPersonName, String phoneNumber, String email) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);

        if(supplier == null){
            return false;
        }

        return supplier.addContactInfo(contactPersonName, phoneNumber, email);
    }

    /**
     * Add contract with the supplier, only one contract can exist.
     * @param supplierId Supplier id
     * @param contractInfo Contract details
     * @param days List of days he can supply items.
     * @param products List of product he supply
     * @return List of products id that cant be add to the system, or return null if cant add the contract to the
     *          supplier already have one.
     */
    public List<Integer> addContractToSupplier(int supplierId, String contractInfo, List<Days> days, List<AddProduct> products) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);

        List<Integer> productIdError = new LinkedList<>();

        if(supplier == null){
            return null;
        }
        supplier.addContractInfo(contractInfo,days);
        for(AddProduct product : products){
            if(!supplier.addProduct(product))
            {
                productIdError.add(product.barCode);
            }
            else
            {
                productsManager.addIfAbsent(product);
            }
        }
        return productIdError;
    }

    /**
     * Add a product to the supplier contract
     * @param supplierId Supplier ID
     * @param product Data of the product
     * @return true if the product have been added
     */
    public boolean addProductToContract(int supplierId, AddProduct product) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);

        if(supplier == null){
            return false;
        }
        boolean ans=false;
        if(ans=supplier.addProduct(product))
        {
            productsManager.addIfAbsent(product);
        }
        return ans;

    }


    /**
     * Return for each product his discount per amount
     * @param supplierId Supplier ID
     * @return List of ProductDiscount.
     */
    public List<ProductDiscounts> getAmountDiscountReport(int supplierId) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);
        if(supplier == null){
            return null;
        }

        return supplier.getAmountDiscountReport();
    }


    //TODO check the day param, mabye the suppler dont do deliveries in this day.
    /**
     * Create a new order in the system
     * @param supplierId The supplier ID who need to supply the order
     * @param products The product to order
     * @param deliveryDay the day to deliver it
     * @return -1 if cant create the order, otherwise return the order id
     */
    public int createNewOrder(int supplierId, List<ProductInOrder> products, Days deliveryDay) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);

        if(supplier == null){
            return -1;
        }

        Order order = Order.CreateOrder(products, deliveryDay);
        if(order == null){
            return -1;
        }

        // Add the order to the data
        orders.get(supplierId).add(order);
        orderIdToOrder.put(order.getOrderId(), order);

        return order.getOrderId();
    }

    /**
     * Update the day of order arrival
     * @param orderId The order id
     * @param day The arrival day
     * @return true if it was updated.
     */
    public boolean updateOrderArrivalTime(int orderId, Days day) {
        Order order = orderIdToOrder.getOrDefault(orderId, null);

        if(order == null){
            return false;
        }

        return order.updateDeliveryDay(day);
    }


    /**
     * Update the status of the given order id
     * @param orderId Order id
     * @param status Status
     * @return True if the update was successful
     */
    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        Order order = orderIdToOrder.getOrDefault(orderId, null);

        if(order == null){
            return false;
        }

        return order.setStatus(status);
    }


    /**
     * Return all the supplier products
     * @param supplierId supplier ID
     * @return List with all the supplier product info
     */
    public List<SupplierProductInfo> getAllSupplierProducts(int supplierId) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);

        if(supplier == null){
            return null;
        }
        return supplier.getAllProducts();
    }


    /**
     * Return all the orders ID from a given suppler
     * @param supplierId Supplier ID
     * @return List with all the orders for the specific supplier
     */
    public List<Integer> getPurchaseHistory(int supplierId) {
        List<Order> ordersOfSupplier = orders.getOrDefault(supplierId, null);
        List<Integer> ordersId = new LinkedList<>();

        if(ordersOfSupplier == null){
            return null;
        }

        for(Order order : ordersOfSupplier){
            ordersId.add(order.getOrderId());
        }

        return ordersId;
    }


    public List<Days> getProductSupplyTimingInterval(int supplierID) {
        return null;
    }
}
