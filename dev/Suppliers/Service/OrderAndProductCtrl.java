package Suppliers.Service;

import Suppliers.Structs.Days;
import Suppliers.Structs.OrderStatus;
import Suppliers.Supplier.Product;
import Suppliers.Supplier.ProductInOrder;
import Suppliers.Supplier.ProductsManager;
import Suppliers.Supplier.SupplierSystem;

import java.util.LinkedList;
import java.util.List;

public class OrderAndProductCtrl implements OrderAndProductManagement {
    private ProductsManager productsManager;
    private SupplierSystem supplierSystem;

    public OrderAndProductCtrl(){
        productsManager = ProductsManager.getInstance();
        supplierSystem = SupplierSystem.getInstance();
    }

    @Override
    public SystemProduct getProduct(int barcode) {
        Product product = productsManager.getAllInfoAboutProduct(barcode);

        if(product == null){
            return null;
        }

        return productToSystemProduct(product);
    }

    public SystemProduct productToSystemProduct(Product product){
        return new SystemProduct(product.getBarCode(), product.getManufacture(), product.getName());
    }

    @Override
    public int createNewOrder(int supplierId, List<ProductInOrderDTO> products) {
        List<ProductInOrder> productInOrders = new LinkedList<>();

        for(ProductInOrderDTO productInOrderDTO : products){
            productInOrders.add(ProductInOrderDTOToPIO(productInOrderDTO));
        }

        return supplierSystem.createNewOrder(supplierId, productInOrders);
    }

    @Override
    public boolean updateOrderArrivalTime(int orderId, Days day) {
        return supplierSystem.updateOrderArrivalTime(orderId, day);
    }

    @Override
    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        return supplierSystem.updateOrderStatus(orderId, status);
    }

    public static ProductInOrder ProductInOrderDTOToPIO(ProductInOrderDTO productInOrderDTO){
        return new ProductInOrder(
                productInOrderDTO.barcode,
                productInOrderDTO.amount);
    }
}
