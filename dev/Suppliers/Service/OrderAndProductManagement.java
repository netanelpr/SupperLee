package Suppliers.Service;


import Suppliers.Structs.Days;
import Suppliers.Structs.OrderStatus;

import java.util.List;

//TODO add the order options to here from the supplierManagement
public interface OrderAndProductManagement {

    /**
     * Return all the details of the product
     * @param barcode The barcode
     * @return SystemProduct or null if there isnt such a product
     */
    SystemProduct getProduct(int barcode);

    /**
     * Create a new order in the system
     * @param supplierId The supplier ID who need to supply the order
     * @param products The product to order
     * @return -1 if cant create the order, otherwise return the order id
     */
    public int createNewOrder(int supplierId, List<ProductInOrderDTO> products);

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
}

