package ModulesConntectionInterfaces;

import Sup_Inv.Suppliers.Structs.Days;
import Sup_Inv.Suppliers.Structs.StructUtils;

import java.util.Date;
import java.util.List;

public class RegularOrderDTOforTransport {

    protected int supplierId;
    protected int shopID;
    protected int orderId;
    protected List<Days> daysCanSupply;

    public RegularOrderDTOforTransport(int supplierId, int orderId, int shopID, List<Days> daysCanSupply){
        this.supplierId = supplierId;
        this.shopID = shopID;
        this.orderId = orderId;
        this.daysCanSupply = daysCanSupply;
    }

    public int getSupplierId(){
        return supplierId;
    }

    public int getOrderId(){
        return orderId;
    }

    public int getShopId(){
        return shopID;
    }

    public Date getDeliveryDate(){
        return StructUtils.getTheNearestDate(daysCanSupply);
    }
}

