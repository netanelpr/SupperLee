package ModulesConntectionInterfaces;

import Sup_Inv.Suppliers.Service.SupplierDetailsDTO;

import java.util.Date;
import java.util.List;

public class TranspirationToSupplier {


    /**
     * Return list of all the open order that need transpiration
     * @return list with all of the open order
     */
    public List<RegularOrderDTOforTransport> getRegularOpenOrders(){
        throw new UnsupportedOperationException();
    }

    public List<PeriodicalOrderDTOforTransport> getPeriodicalOpenOrders(){
        throw new UnsupportedOperationException();
    }

    /**
     * Return all the information about supplier
     * @param supplierId the supplier id
     * @return all the information about supplier
     */
    public SupplierDetailsDTO getSupplierInfo(int supplierId){
        throw new UnsupportedOperationException();
    }

    /**
     * Set order as approved and give it an arrival date
     * @param orderId order id
     * @param arrivalDate date that the order will arrive to the store
     */
    public void setOrderStatusAsApproved(int orderId, Date arrivalDate){
        throw new UnsupportedOperationException();
    }

    //TODO check what they are doing if the wont be send in the delivery date they said, will they rescheduled it
    public void setOrderStatusAsWontBeSentInTime(int orderId){
        throw new UnsupportedOperationException();
    }
}
