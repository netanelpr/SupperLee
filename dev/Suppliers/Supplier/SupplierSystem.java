package Suppliers.Supplier;

import Result.Result;
import Suppliers.Structs.Days;
import Suppliers.Structs.OrderStatus;
import Suppliers.Supplier.Order.Order;
import Suppliers.Supplier.Order.OrderManager;
import Suppliers.Supplier.Order.ProductInOrder;
import Suppliers.Supplier.Order.RegularOrder;

import java.util.*;

public class SupplierSystem {

    private static SupplierSystem instance = null;

    private Map<Integer, Supplier> suppliers;
    private Map<Integer,List<Order>> orders;

    private Map<Integer, Order> orderIdToOrder;

    private ProductsManager productsManager;
    private SupplierManager supplierManager;
    private OrderManager orderManager;

    private final String[] paymentOptions;

    private SupplierSystem() {
        suppliers = new HashMap<>();
        orders = new HashMap<>();
        orderIdToOrder = new HashMap<>();

        supplierManager = SupplierManager.getInstance();
        productsManager = ProductsManager.getInstance();
        orderManager = OrderManager.getInstance();

        paymentOptions = new String[]{"CASH", "BANKTRANSFER","PAYMENTS","+30DAYSPAYMENT","CHECK"};
    }

    public static SupplierSystem getInstance(){
        if(instance == null){
            instance = new SupplierSystem();
        }
        return instance;
    }

    /**
     * Create new supplier in the system
     * @param name The name of the supplier
     * @param incNum incupartion number
     * @param accountNumber Bank account
     * @param paymentInfo payment options by string separated by ,
     * @return -1 if cant create a new supplier otherwise return new supplier ID in the system.
     */
    public int createSupplierCard(String name, String incNum, String address, String accountNumber, String paymentInfo,
                                  String contactName, String phoneNumber,String email) {

        paymentInfo = paymentInfo.toUpperCase();
        String[] paymentOArr = paymentInfo.split(",");
        List<String> paymentOptions = Arrays.asList(this.paymentOptions);
        for(String paymentO : paymentOArr) {
            if (!paymentOptions.contains(paymentO.toUpperCase())) {
                return -1;
            }
        }

        Supplier sup = new Supplier(name,address,incNum,accountNumber,paymentInfo,contactName,phoneNumber,email);
        supplierManager.insert(sup);


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
     * Add payment options to supplier
     * @param supId the supplier ID
     * @param paymentInfo Array of payment options
     * @return true if the payment options was added or already exist, false otherwise
     */
    public boolean addPaymentOptions(int supId, String[] paymentInfo) {
        Supplier sup = suppliers.getOrDefault(supId, null);
        if(sup == null){
            return  false;
        }

        // payment doesnt in the allowed list
        List<String> paymentOptions = Arrays.asList(this.paymentOptions);
        for(String paymentO : paymentInfo) {
            if (!paymentOptions.contains(paymentO.toUpperCase())) {
                return false;
            }
        }

        return sup.addPaymentOptions(paymentInfo);
    }

    /**
     * Remove the payment options from the supplier
     * After the removal there is need to be at least one payment option for the
     * supplier otherwise the method wont remove any option
     * @param supId supplier ID
     * @param paymentInfo Array of payment options to remove
     * @return true if the all the payment are removed, false otherwise
     */
    public boolean removePaymentOptions(int supId, String[] paymentInfo) {
        Supplier sup = suppliers.getOrDefault(supId, null);
        if(sup == null){
            return  false;
        }

        // payment doesnt in the allowed list
        for(String option : paymentInfo){
            if(Arrays.binarySearch(paymentOptions, option.toUpperCase()) < 0){
                return false;
            }
        }

        return sup.RemovePaymentOptions(paymentInfo);
    }


    /**
     * Return the details for each supplier in the system.
     * @return List<Suppliers.InvService.SupplierDetails> for each supplier in the system.
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

    public boolean RemoveContactFromSupplier(int supID,String email)
    {
        return this.suppliers.get(supID).RemoveContactFromSupplier(email);
    }

    /**
     * Add contract with the supplier, only one contract can exist.
     * @param supplierId Suppliers.Supplier id
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
        boolean ans= supplier.addContractInfo(contractInfo,days);
        if(ans) {
            for (AddProduct product : products) {
                if (!supplier.addProduct(product)) {
                    productIdError.add(product.barCode);
                } else {
                    productsManager.addIfAbsent(product);
                }
            }

            return productIdError;
        }
        else {
            return null;
        }

    }

    /**
     * Add a product to the supplier contract
     * @param supplierId Suppliers.Supplier ID
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
     * @param supplierId Suppliers.Supplier ID
     * @return List of ProductDiscount.
     */
    public List<ProductDiscounts> getAmountDiscountReport(int supplierId) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);
        if(supplier == null){
            return null;
        }

        return supplier.getAmountDiscountReport();
    }


    /**
     * Create a new order in the system
     * @param supplierId The supplier ID who need to supply the order
     * @param products The product to order
     * @return -1 if cant create the order, otherwise return the order id
     */
    public Result<Integer> createNewOrder(int supplierId, List<ProductInOrder> products, int shopNumber) {
        Supplier supplier = supplierManager.getOrNull(supplierId);
        List<Integer> barcodes = new ArrayList<>();

        if(supplier == null){
            return Result.makeFailure("Supplier doesnt exist");
        }


        for(ProductInOrder product : products){
            barcodes.add(product.getBarcode());
        }
        if(!supplierManager.haveAllBarcodes(supplierId,barcodes)){
            return Result.makeFailure("The supplier do not supply some of this products");
        }

        //TODO check this
        supplier.fillWithCatalogNumber(products);

        RegularOrder regularOrder = RegularOrder.CreateRegularOrder(-1, products, shopNumber);
        if(regularOrder == null){
            return Result.makeFailure("Need to have at least one product");
        }
        regularOrder.setDeliveryDay(supplierManager.getNextDeliveryDate(supplierId));

        orderManager.createRegularOrder(regularOrder);
        if(regularOrder.getOrderId() < 0){
            return Result.makeFailure("Order wasnt created");
        }

        //TODO remove this
        // Add the order to the data
        /*orders.get(supplierId).add(regularOrder);
        orderIdToOrder.put(regularOrder.getOrderId(), regularOrder);*/

        return Result.makeOk("Order was created", regularOrder.getOrderId());
    }

    /**
     * Create a regular order if a supplier have all the products with the cheapest one
     * @param products products
     * @param shopNumber shop number
     * @return orderId if it was created otherwise -1
     */
    public Result<Integer> createRegularOrder(List<ProductInOrder> products, int shopNumber) {
        List<Integer> barcodes = new ArrayList<>();
        List<Integer> suppliersId;
        Supplier sup;

        for(ProductInOrder product : products){
            barcodes.add(product.getBarcode());
        }
        suppliersId = supplierManager.getAllSupplierWithBarcodes(barcodes);
        if(suppliersId.isEmpty()){
            return Result.makeFailure("There isnt one supplier with all of this products");
        }

        int cheapestSupplierId = -1, totalPrice = -1;
        for(int id : suppliersId){
            sup = supplierManager.getById(id);
            int orderPrice = sup.calculateOrderPrice(products);
            if(orderPrice < totalPrice){
                totalPrice = orderPrice;
                cheapestSupplierId = id;
            }
        }

        sup = supplierManager.getById(cheapestSupplierId);
        sup.setPricePerUnit(products);

        RegularOrder regularOrder = RegularOrder.CreateRegularOrder(-1,products, shopNumber);
        orderManager.createRegularOrder(regularOrder);
        if(regularOrder.getOrderId() < 0){
            return Result.makeFailure("Order wasnt created");
        }

        //TODO remove this
        // Add the order to the data
        /*orders.get(supplierId).add(regularOrder);
        orderIdToOrder.put(regularOrder.getOrderId(), regularOrder);*/

        return Result.makeOk("Order was created", regularOrder.getOrderId());

    }

    /**
     * Update the day of order arrival
     * @param orderId The order id
     * @param date The arrival day
     * @return true if it was updated.
     */
    public boolean updateOrderArrivalTime(int orderId, Date date) {
        Order order = orderIdToOrder.getOrDefault(orderId, null);

        if(order == null){
            return false;
        }

        return order.updateDeliveryDay(date);
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
     * @param supplierId Suppliers.Supplier ID
     * @return List with all the orders for the specific supplier
     */
    public List<String> getPurchaseHistory(int supplierId) {
        List<Order> ordersOfSupplier = orders.getOrDefault(supplierId, null);
        Map<String,Integer> productsNoDuplicate=new HashMap<>();



        if(ordersOfSupplier == null){
            return null;
        }

        for(Order order : ordersOfSupplier){
            List<String> productsCatalogNumberInOrder = order.retrunProductsCatalogNumbers();
            for (String s : productsCatalogNumberInOrder) {
                productsNoDuplicate.putIfAbsent(s, 0);
            }
        }
        return new LinkedList<>(productsNoDuplicate.keySet());


    }

    public AddProduct getAllInformationAboutSuppliersProduct(int supplierId, int barcode) {
        Supplier supplier = suppliers.getOrDefault(supplierId, null);

        if(supplier == null){
            return null;
        }
        ContractProduct cp=supplier.getAllInformationAboutSuppliersProduct(barcode);
        Product product=this.productsManager.getProduct(barcode);
        if(cp!=null && product!=null) {
            return new AddProduct(cp.getBarCode(), cp.getProductCatalogNumber(), cp.getDiscounts().originalPrice, cp.getDiscounts(), product.getManufacture(), product.getName());
        }
        else
        {
            return null;
        }

    }

    public OrderStatus getOrderStatus(int orderID)
    {
        Order order=this.orderIdToOrder.get(orderID);
        if(order!=null) {
            return order.getStatus();
        }
        else
        {
            return null;
        }
    }
}
