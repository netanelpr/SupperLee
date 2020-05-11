package Suppliers.Service;


import Result.Result;
import Suppliers.Structs.Days;
import Suppliers.Structs.OrderStatus;

import java.util.Date;
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
     * A list of all the product barocdes in the system
     * @return list of all the product barocdes in the system, empty list if there isnt any product or null
     */
    List<Integer> getAllProductBarcodes();

    /**
     * Create a new order in the system
     * @param supplierId The supplier ID who need to supply the order
     * @param products The product to order
     * @return -1 if cant create the order, otherwise return the order id
     */
    Result<Integer> createNewSupplierOrder(int supplierId, List<ProductInOrderDTO> products, int shopNumber);

    Result<Integer> createRegularOrder(List<ProductInOrderDTO> products, int shopNumber);

    Result<Integer> createPeriodicalOrder(List<ProductInOrderDTO> products, List<Days> days, int weekPeriod);

    /**
     * Update the day of order arrival
     * @param orderId The order id
     * @param date The arrival day
     * @return true if it was updated.
     */
    public boolean updateOrderArrivalTime(int orderId, Date date);

    /**
     * Update the status of the given order id
     * @param orderId Order id
     * @param status Status
     * @return True if the update was successful
     */
    public boolean updateOrderStatus(int orderId, OrderStatus status);
}

