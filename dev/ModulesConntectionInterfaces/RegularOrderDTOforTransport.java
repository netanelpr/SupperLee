package ModulesConntectionInterfaces;

import Sup_Inv.Suppliers.Structs.Days;
import Sup_Inv.Suppliers.Structs.StructUtils;

import java.util.Date;
import java.util.List;

public class RegularOrderDTOforTransport {

    protected int supplierId;
    protected int shopID;
    protected int orderId;
    protected Date date;
    protected int supplierArea;


    public RegularOrderDTOforTransport(int supplierId, int orderId, int shopID, Date date,int supplierArea){
        this.supplierId = supplierId;
        this.shopID = shopID;
        this.orderId = orderId;
        this.date = date;
        this.supplierArea=supplierArea;
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

    public Date getDate(){
        return this.date;
    }

    public int getSupplierArea() {
        return supplierArea;
    }
}

