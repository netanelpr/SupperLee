package ModulesConntectionInterfaces;

import Sup_Inv.Suppliers.Structs.Days;

import java.util.Date;
import java.util.List;

public class PeriodicalOrderDTOforTransport extends RegularOrderDTOforTransport {

    public PeriodicalOrderDTOforTransport(int supplierId, int orderId, int shopID,
                                          List<Days> daysCanSupply,int areaID){
        super(supplierId, orderId, shopID, daysCanSupply,areaID);
    }

}