package Suppliers.Service;

import Suppliers.Supplier.Product;
import Suppliers.Supplier.ProductsManager;

public class OrderAndProductCtrl implements OrderAndProductManagement {
    private ProductsManager productsManager;

    public OrderAndProductCtrl(){
        productsManager = ProductsManager.getInstance();
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
}
