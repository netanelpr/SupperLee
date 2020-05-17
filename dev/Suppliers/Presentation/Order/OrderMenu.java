package Suppliers.Presentation.Order;

import Suppliers.Presentation.MenuOfMenus;
import Suppliers.Presentation.RemoveProductsPeriodicalOrder;
import Suppliers.Service.OrderAndProductManagement;
import Suppliers.Service.SupplierManagment;

public class OrderMenu extends MenuOfMenus {

    public OrderMenu(SupplierManagment supplierManagment, OrderAndProductManagement orderAndProductManagement) {
        super(supplierManagment, orderAndProductManagement);
    }

    protected void createMenuMap() {
        addMenuOption("Create new order", new CreateNewOrder(orderAndProductManagement));
        addMenuOption("Create periodical order", new CreatePeriodicalOrder(orderAndProductManagement));

        addMenuOption("Add products to periodical order", new AddProductsToPeriodicalOrder(orderAndProductManagement));
        addMenuOption("Remove products from periodical order", new RemoveProductsPeriodicalOrder(orderAndProductManagement));

        addMenuOption("Update order arrival day", new UpdateOrderArrivalDay(orderAndProductManagement));
        addMenuOption("Update order status", new UpdateOrderStatus(orderAndProductManagement));

        addMenuOption("Get all open order ids", new GetAllOpenOrdersIds(orderAndProductManagement));
        addMenuOption("Get all periodical open order ids", new GetAllOpenPeriodicalOrders(orderAndProductManagement));
        addMenuOption("Get order details", new OrderDetails(orderAndProductManagement));
    }
}