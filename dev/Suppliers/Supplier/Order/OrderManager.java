package Suppliers.Supplier.Order;

import Suppliers.DataAccess.RegularOrderMapper;
import Suppliers.DataAccess.SupDBConn;

public class OrderManager {

    private static OrderManager instance = null;

    private RegularOrderMapper regularOrderMapper;

    private OrderManager(){
        regularOrderMapper = new RegularOrderMapper(SupDBConn.getInstance());
    }

    public static OrderManager getInstance(){
        if(instance == null){
            instance = new OrderManager();
        }
        return instance;
    }

    public RegularOrder getRegularOrder(int orderId){
        return regularOrderMapper.findById(orderId);
    }

    public void createRegularOrder(RegularOrder regularOrder){
        regularOrder.setOrderId(regularOrderMapper.insert(regularOrder));
    }
}
