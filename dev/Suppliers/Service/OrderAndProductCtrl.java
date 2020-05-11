package Suppliers.Service;

import Result.Result;
import Suppliers.Structs.Days;
import Suppliers.Structs.OrderStatus;
import Suppliers.Supplier.Product;
import Suppliers.Supplier.ProductInOrder;
import Suppliers.Supplier.ProductsManager;
import Suppliers.Supplier.SupplierSystem;

import java.util.Date;
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

    //TODO
    @Override
    public List<Integer> getAllProductBarcodes() {
        return productsManager.getAllBarcodes();
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

        //TODO edit
        return Result.makeOk("",supplierSystem.createNewOrder(supplierId, productInOrders, shopNumber));
    }

    @Override
    public Result<Integer> createRegularNewOrder(List<ProductInOrderDTO> products, int shopNumber) {
        return null;
    }

    @Override
    public Result<Integer> createPeriodicalOrder(List<ProductInOrderDTO> products, List<Days> days, int weekPeriod) {
        return null;
    }

    @Override
    public boolean updateOrderArrivalTime(int orderId, Date date) {
        return supplierSystem.updateOrderArrivalTime(orderId, date);
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
