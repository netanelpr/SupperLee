package ModulesConntectionInterfaces;

import Sup_Inv.Suppliers.Structs.Days;

import java.util.Date;
import java.util.List;

public class PeriodicalOrderDTOforTransport extends RegularOrderDTOforTransport {

    public int supplierId;
    public int shopID;
    public int orderId;
    public List<Days> daysCanSupply;
    public List<Date> datesToDelivery;

    public PeriodicalOrderDTOforTransport(int supplierId, int orderId, int shopID,
                                          List<Days> daysCanSupply, List<Date> datesToDelivery){
        super(supplierId, orderId, shopID, daysCanSupply);
        this.datesToDelivery = datesToDelivery;
    }

}

