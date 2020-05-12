package Suppliers.Supplier.Order;

import Suppliers.DataAccess.PeriodicalOrderMapper;
import Suppliers.DataAccess.RegularOrderMapper;
import Suppliers.DataAccess.SupDBConn;

import java.util.Date;

public class OrderManager {

    private static OrderManager instance = null;

    private RegularOrderMapper regularOrderMapper;
    private PeriodicalOrderMapper periodicalOrderMapper;

    private OrderManager(){
        regularOrderMapper = new RegularOrderMapper(SupDBConn.getInstance());
        periodicalOrderMapper = new PeriodicalOrderMapper(SupDBConn.getInstance());
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

    public PeriodicalOrder getPeriodicalOrder(int orderId){
        return periodicalOrderMapper.findById(orderId);
    }


    public void createRegularOrder(RegularOrder regularOrder){
        regularOrder.setOrderId(regularOrderMapper.insert(regularOrder));
    }

    public void createPeriodicalOrder(PeriodicalOrder periodicalOrder){
        periodicalOrder.setOrderId(periodicalOrderMapper.insert(periodicalOrder));
    }

    public boolean updateOrderDelivery(int orderrId, Date date){
        return regularOrderMapper.updateDeliveryDate(orderrId, date);
    }
}