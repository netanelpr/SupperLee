package Suppliers.DataAccess;

import Suppliers.Supplier.Product;

import java.util.HashMap;
import java.util.Hashtable;

public class ProductMapper extends AbstractMapper<Product> {

    public ProductMapper(String url) {
        super(url);
        loadedMap = new HashMap<>();
    }

    @Override
    String findStatement() {
        return "SELECT * " +
                "FROM Product " +
                "Where barcode = ?";
    }

    @Override
    int insert(Product domain) {
        return 0;
    }
}
