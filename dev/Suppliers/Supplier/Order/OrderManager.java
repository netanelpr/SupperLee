package Suppliers.Supplier.Order;

import DataAccess.SupInvDBConn;
import Result.Result;
import Suppliers.DataAccess.PeriodicalOrderMapper;
import Suppliers.DataAccess.RegularOrderMapper;
import Suppliers.DataAccess.SupDBConn;
import Suppliers.Structs.OrderStatus;

import java.util.Date;
import java.util.List;

public class OrderManager {

    private static OrderManager instance = null;

    private RegularOrderMapper regularOrderMapper;
    private PeriodicalOrderMapper periodicalOrderMapper;

    private OrderManager(){
        regularOrderMapper = new RegularOrderMapper(SupInvDBConn.getInstance());
        periodicalOrderMapper = new PeriodicalOrderMapper(SupInvDBConn.getInstance());
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

    public boolean updateOrderStatus(int orderId, OrderStatus status){
        return regularOrderMapper.updateOrderStatus(orderId, status);
    }

    public List<Integer> getAllOpenOrderIdsByShop(int shopNumber){
        return regularOrderMapper.getAllOpenOrderIdsByShop(shopNumber);
    }

    public Order getOrderBasicDetails(int orderId) {
        return regularOrderMapper.loadBasicDetails(orderId);
    }

    public List<AllDetailsOfProductInOrder> getAllProductDetails(int orderId) {
        return regularOrderMapper.getAllProductDetails(orderId);
    }

    public boolean isPeriodicalOrder(int orderId){
        return periodicalOrderMapper.isPeriodicalOrder(orderId);
    }

    public List<Integer> addProductsToPeriodicalOrder(int orderId, List<ProductInOrder> productInOrders) {
        return periodicalOrderMapper.addProductsToPeriodicalOrder(orderId, productInOrders);
    }

    public int getTheSupplierOfOrder(int orderId) {
        return regularOrderMapper.getTheSupplierOfOrder(orderId);
    }

    public List<String> removeProductsFromOrder(int orderId, List<String> catalog) {
        return regularOrderMapper.removeProductsFromOrder(orderId, catalog);
    }
}
