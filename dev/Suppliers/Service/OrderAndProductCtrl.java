package Suppliers.Service;

import Result.Result;
import Suppliers.Structs.Days;
import Suppliers.Structs.OrderStatus;
import Suppliers.Supplier.Order.*;
import Suppliers.Supplier.Product;
import Suppliers.Supplier.ProductsManager;
import Suppliers.Supplier.SupplierSystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class OrderAndProductCtrl implements OrderAndProductManagement {
    private final ProductsManager productsManager;
    private final OrderManager orderManager;
    private final SupplierSystem supplierSystem;

    public OrderAndProductCtrl(){
        productsManager = ProductsManager.getInstance();
        orderManager = OrderManager.getInstance();
        supplierSystem = SupplierSystem.getInstance();
    }

    @Override
    public SystemProduct getProduct(int barcode) {
        Product product = productsManager.getProduct(barcode);

        if(product == null){
            return null;
        }

        return productToSystemProduct(product);
    }

    @Override
    public List<Integer> getAllProductBarcodes() {
        return productsManager.getAllBarcodes();
    }

    @Override
    public List<Integer> productsInTheSystem(List<Integer> barcodes) {
        List<Integer> barcds =  productsManager.getAllBarcodes();
        List<Integer> barcodesInSystem = new ArrayList<>();

        for(Integer barcode : barcodes){
            if(barcds.indexOf(barcode) > 0){
                barcodesInSystem.add(barcode.intValue());
            }
        }

        return  barcodesInSystem;

    }

    public SystemProduct productToSystemProduct(Product product){
        return new SystemProduct(product.getBarCode(), product.getManufacture(), product.getName(),
                product.getCategory(),product.getSubCategory(),product.getSize(),
                product.getFreqSupply(), product.getMinPrice());
    }

    @Override
    public Result<Integer> createNewSupplierOrder(int supplierId, List<ProductInOrderDTO> products, int shopNumber) {
        List<ProductInOrder> productInOrders = new LinkedList<>();

        for(ProductInOrderDTO productInOrderDTO : products){
            productInOrders.add(ProductInOrderDTOToPIO(productInOrderDTO));
        }

        return supplierSystem.createNewOrder(supplierId, productInOrders, shopNumber);
    }

    @Override
    public Result<Integer> createRegularOrder(List<ProductInOrderDTO> products, int shopNumber) {
        List<ProductInOrder> productInOrders = new LinkedList<>();

        for(ProductInOrderDTO productInOrderDTO : products){
            productInOrders.add(ProductInOrderDTOToPIO(productInOrderDTO));
        }

        return supplierSystem.createRegularOrder(productInOrders, shopNumber);
    }

    @Override
    public Result<Integer> createPeriodicalOrder(List<ProductInOrderDTO> products, List<Days> days, int weekPeriod, int shopNumber) {
        List<ProductInOrder> productInOrders = new LinkedList<>();

        for(ProductInOrderDTO productInOrderDTO : products){
            productInOrders.add(ProductInOrderDTOToPIO(productInOrderDTO));
        }

        return supplierSystem.createPeriodicalOrder(productInOrders, days, weekPeriod, shopNumber);
    }

    /**
     *
     * @param orderId
     * @return
     */
    public OrderDTO orderArrived(int orderId){
        List<ProductInOrderDTO> products = new ArrayList<>();
        Order order;
        order = orderManager.getRegularOrder(orderId);
        if(order == null) {
            order = orderManager.getPeriodicalOrder(orderId);
        } else {
            updateOrderStatus(orderId, OrderStatus.Close);
        }

        if(order == null){
            //TODO no such orderId
        }

        for(ProductInOrder product : order.getProducts()){
            products.add(ProductInOrderToDTO(product));
        }

        return new OrderDTO(order.getShopNumber(), products);
    }

    @Override
    public boolean updateOrderArrivalTime(int orderId, Date date) {
        return orderManager.updateOrderDelivery(orderId, date);
    }

    @Override
    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        return orderManager.updateOrderStatus(orderId, status);
    }

    @Override
    public List<Integer> getAllOpenOrderIdsByShop(int shopNumber) {
        return orderManager.getAllOpenOrderIdsByShop(shopNumber);
    }

    public static ProductInOrder ProductInOrderDTOToPIO(ProductInOrderDTO productInOrderDTO){
        return new ProductInOrder(
                productInOrderDTO.barcode,
                productInOrderDTO.amount);
    }

    public static ProductInOrderDTO ProductInOrderToDTO(ProductInOrder productInOrder){
        return new ProductInOrderDTO(
                productInOrder.getBarcode(),
                productInOrder.getAmount(),
                productInOrder.getPricePerUnit());
    }
}
