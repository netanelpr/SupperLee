package ModulesConntectionInterfaces;

import Sup_Inv.Suppliers.Service.*;
import Sup_Inv.Suppliers.Structs.OrderStatus;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TranspirationToSupplier {

    OrderAndProductManagement orderAndProductCtrl;
    SupplierManagment supplierCtrl;

    public TranspirationToSupplier(){
        orderAndProductCtrl = new OrderAndProductCtrl();
        supplierCtrl = new SupplierCtrl();
    }

    /**
     * Return list of all the open order that need transpiration
     * @return list with all of the open order
     */
    public List<RegularOrderDTOforTransport> getRegularOpenOrders(){
        List<RegularOrderDTOforTransport> orders = new LinkedList<>();
        List<Integer> orderIds = orderAndProductCtrl.getAllWaitingShippingRegularOrders();

        for(Integer orderId: orderIds){
            OrderShipDetails orderShipDetails = orderAndProductCtrl.orderDetails(orderId);
            orders.add(new RegularOrderDTOforTransport(
                    orderShipDetails.supplier.supplierID,
                    orderShipDetails.orderId,
                    orderShipDetails.shopNumber,
                    orderShipDetails.supplier.supplyDays,
                    orderShipDetails.supplier.area
            ));
        }

        return orders;
    }

    public List<PeriodicalOrderDTOforTransport> getPeriodicalOpenOrders(){

        List<PeriodicalOrderDTOforTransport> orders = new LinkedList<>();
        List<Integer> orderIds = orderAndProductCtrl.getAllWaitingShippingPeriodicalOrders();
        for(Integer orderId: orderIds){
            OrderShipDetails orderShipDetails = orderAndProductCtrl.orderDetails(orderId);
            orders.add(new PeriodicalOrderDTOforTransport(
                    orderShipDetails.supplier.supplierID,
                    orderShipDetails.orderId,
                    orderShipDetails.shopNumber,
                    orderShipDetails.supplier.supplyDays,
                    orderShipDetails.supplier.area
            ));
        }

        return orders;
    }

    /**
     * Return all the information about supplier
     * @param supplierId the supplier id
     * @return all the information about supplier
     */
    public SupplierDetailsDTO getSupplierInfo(int supplierId){
        return supplierCtrl.getSupplierInfo(supplierId);
    }

    /**
     * Set order as approved and give it an arrival date
     * @param orderId order id
     * @param arrivalDate date that the order will arrive to the store
     */
    public void setOrderStatusAsShipped(int orderId, Date arrivalDate){
        if(orderAndProductCtrl.updateOrderStatus(orderId, OrderStatus.Open)){
            orderAndProductCtrl.updateOrderArrivalTime(orderId, arrivalDate);
        }
    }

    //TODO may change the name
    public void setOrderStatusBackToOpen(int orderId){
        orderAndProductCtrl.updateOrderStatus(orderId, OrderStatus.WaitingForShipping);
    }

}
