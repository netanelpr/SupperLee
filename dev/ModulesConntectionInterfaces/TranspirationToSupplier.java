package ModulesConntectionInterfaces;

import Sup_Inv.Suppliers.Service.*;

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
        List<Integer> orderIds = orderAndProductCtrl.getAllOpenOrders();

        for(Integer periodicalOrderId: orderAndProductCtrl.getAllOpenPeriodicalOrder()){
            orderIds.remove(periodicalOrderId);
        }

        for(Integer orderId: orderIds){
            OrderShipDetails orderShipDetails = orderAndProductCtrl.orderDetails(orderId);
            orders.add(new RegularOrderDTOforTransport(
                    orderShipDetails.supplier.supplierID,
                    orderShipDetails.orderId,
                    orderShipDetails.shopNumber,
                    orderShipDetails.supplier.supplyDays,
                    Integer.parseInt(orderShipDetails.supplier.area)
                    ));
        }

        return orders;
    }

    public List<PeriodicalOrderDTOforTransport> getPeriodicalOpenOrders(){

        List<PeriodicalOrderDTOforTransport> orders = new LinkedList<>();
        List<Integer> orderIds = orderAndProductCtrl.getAllOpenPeriodicalOrder();
        //TODO: change parseInt to int
        for(Integer orderId: orderIds){
            OrderShipDetails orderShipDetails = orderAndProductCtrl.orderDetails(orderId);
            orders.add(new PeriodicalOrderDTOforTransport(
                    orderShipDetails.supplier.supplierID,
                    orderShipDetails.orderId,
                    orderShipDetails.shopNumber,
                    orderShipDetails.supplier.supplyDays,
                    Integer.parseInt(orderShipDetails.supplier.area)
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
    //TODO: add statuses: open: in store,
    /**
     * Set order as approved and give it an arrival date
     * @param orderId order id
     * @param arrivalDate date that the order will arrive to the store
     */
    public void setOrderStatusAsApproved(int orderId, Date arrivalDate){
        throw new UnsupportedOperationException();
    }

    public void setOrderStatusBackToOpen(int orderId){
        throw new UnsupportedOperationException();
    }

}
