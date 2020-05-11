package Suppliers.Supplier;

import Suppliers.DataAccess.ProductMapper;
import Suppliers.DataAccess.SupDBConn;

import java.util.List;

public class ProductsManager {

    private static ProductsManager instance = null;

    private ProductMapper productMapper;
    //TODO remove it or use it to hold upto 100 products.
    //private Map<Integer, Product> productMap; //BarcodeToProductObject

    private ProductsManager(){
        //productMap = new HashMap<>();
        productMapper = new ProductMapper(SupDBConn.getInstance());
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
        return productMapper.getAllBarcodes();
    }

    public boolean exists(int barcode){
        return productMapper.findById(barcode) != null;
    }

    public Product getProduct(int barcode) {
        return productMapper.findById(barcode);
    }
}
