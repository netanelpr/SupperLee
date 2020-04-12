package Service;

import Structs.Days;
import Structs.OrderStatus;

import java.util.List;

public interface SupplierManagment {

    //TODO edit this method by adding contact info for one person 7.
    /**
     * Create new supplier in the system
     * @param name The name of the supplier
     * @param incNum incupartion number
     * @param accountNumber Bank account
     * @param paymentInfo Payment info
     * @return -1 if cant create a new supplier otherwise return new supplier ID in the system.
     */
    public int createSupplierCard(String name, String incNum, String accountNumber, String paymentInfo);

    public String getPaymentOptions();

    /**
     * Return the payment information of specific supplier.
     * @param supId ID of the supplier
     * @return null if the supplier doesnt exist in the system, otherwise its payment information
     */
    public String getPaymentOptions(int supId);

    //TODO implement 1.
    /**
     * Add all or noting of the payments info to the supplier
     * @param supId supplier ID
     * @param paymentInfo list of payments options
     * @return true if added false otherwise
     */
    public boolean addPaymentOptions(int supId, String[] paymentInfo);

    //TODO implement 1.
    /**
     * Remove all or noting of the payments info from supplier
     * @param supId supplier ID
     * @param paymentInfo list of payments options
     * @return true if added false otherwise
     */
    public boolean removePaymentOptions(int supId, String[] paymentInfo);

    /**
     * Return the details for each supplier in the system.
     * @return List<Service.SupplierDetails> for each supplier in the system.
     */
    public List<SupplierDetailsDTO> getAllSuppliers();

    /**
     * Add person contact information to specific supplier
     * @param supplierId ID for the supplier
     * @param contactPersonName Person name
     * @param phoneNumber Phone number
     * @param email Email
     * @return True if the contact as been added.
     */
    public boolean addContactInfo(int supplierId, String contactPersonName, String phoneNumber, String email);

    //TODO implemet 2.
    /**
     * Remove contact person by email if there are more then 1 in the system for the
     * specifed supplier
     * @param supplierId supplier ID
     * @param email the email of the contact person
     * @return true if the contact has been removed false otherwise.
     */
    public boolean removeContactPerson(int supplierId,String email);
    /**
     * Add contract with the supplier, only one contract can exist.
     * @param supplierId Supplier id
     * @param contractInfo Contract details
     * @param days List of days he can supply items.
     * @param products List of product he supply
     * @return List of products id that wasnt added to the system, null if the contract wasnt added
     */
    public List<Integer> addContractToSupplier(int supplierId, String contractInfo, List<Days> days, List<AddProductInfoDTO> products);

    /**
     * Add a product to the supplier contract
     * @param supplierId Supplier ID
     * @param product Data of the product
     * @return -1 if the product wasnt added, otherwise the product id in the system
     */
    public boolean addProductToContract(int supplierId, AddProductInfoDTO product);

    /**
     * Return to each product the given supplier his discount per amount
     * @param supplierId Supplier ID
     * @return List of ProductDiscount.
     */
    public List<ProductDiscountsDTO> getAmountDiscountReport(int supplierId);

    //TODO remove the day in the method 5.
    /**
     * Create a new order in the system
     * @param supplierId The supplier ID who need to supply the order
     * @param products The product to order
     * @param day the day to deliver it
     * @return -1 if cant create the order, otherwise return the order id
     */
    public int createNewOrder(int supplierId, List<ProductInOrderDTO> products, Days day);

    /**
     * Update the day of order arrival
     * @param orderId The order id
     * @param day The arrival day
     * @return true if it was updated.
     */
    public boolean updateOrderArrivalTime(int orderId, Days day);

    /**
     * Update the status of the given order id
     * @param orderId Order id
     * @param status Status
     * @return True if the update was successful
     */
    public boolean updateOrderStatus(int orderId, OrderStatus status);

    /**
     * Return all the supplier products
     * @param supplierId supplier ID
     * @return List with all the supplier product info
     */
    public List<SupplierProductDTO> getAllSupplierProducts(int supplierId);

    //TODO implement function that return all the details of product by supid,catalog 4.

    //TODO edit getPurchaseHistoy to return what we wrote in the description 6.
    /**
     * Return all the catalog ID from a given suppler
     * @param supplierId Supplier ID
     * @return List with all the products catalog numbers that the system has order which
     * contains them
     */
    public List<Integer> getPurchaseHistory(int supplierId);

    //TODO check what it is
    public List<Days> getProductSupplyTimingInterval(int supplierID);

}
