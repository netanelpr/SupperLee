package ModulesConntectionInterfaces;

import Sup_Inv.Suppliers.Structs.Days;

import java.util.Date;
import java.util.List;

//TODO add to supplier area A,B,C,D

public class RegularOrderDTOforTransport {

    public int supplierId;
    public int shopID;
    public int orderId;
    public List<Days> daysCanSupply;

    public RegularOrderDTOforTransport(int supplierId, int orderId, int shopID, List<Days> daysCanSupply){
        this.shopID = shopID;
    }

}

