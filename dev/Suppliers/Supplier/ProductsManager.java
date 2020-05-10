package Suppliers.Supplier;

import Suppliers.DataAccess.ProductMapper;
import Suppliers.DataAccess.SupplierDBConn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsManager {

    private static ProductsManager instance = null;

    private ProductMapper productMapper;
    //TODO remove it or use it to hold upto 100 products.
    //private Map<Integer, Product> productMap; //BarcodeToProductObject

    private ProductsManager(){
        //productMap = new HashMap<>();
        productMapper = new ProductMapper(SupplierDBConn.getInstance());
    }

    public static ProductsManager getInstance(){
        if(instance == null){
            instance = new ProductsManager();
        }
        return instance;
    }

    public boolean addIfAbsent(AddProduct addProduct){

        return productMapper.insert(new Product(addProduct.barCode, addProduct.manufacture, addProduct.name)) > -1;


        /*if(!productMap.containsKey(addProduct.barCode))
        {
            productMap.put(addProduct.barCode,new Product(addProduct.barCode,addProduct.manufacture,addProduct.name));
            return true;
        }
        else
            return false;*/
    }

    public List<Integer> getAllBarcodes(){
        return productMapper.getAllIds();
    }

    public boolean exists(int barcode){
        return productMapper.findById(barcode) != null;
    }

    public Product getAllInfoAboutProduct(int barcode) {
        return productMapper.findById(barcode);
    }
}
