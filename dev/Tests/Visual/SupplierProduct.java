package Tests.Visual;

import Suppliers.Service.OrderAndProductCtrl;
import Suppliers.Supplier.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SupplierProduct {
    public static void dbExample(){
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:DB/supplier.db")) {
            Product product = null;

            /*ProductMapper productMapper = new ProductMapper(conn);
            product = productMapper.findById(1);
            System.out.println("barcode: "+product.getBarCode()+", name "+ product.getName()+", manufacture "+ product.getManufacture());

            //need to be the same as product.getBarcode() in this case
            product = new Product(100,"aa","water");
            int barcode = productMapper.insert(product);
            if(barcode > -1){

                product = productMapper.findById(barcode);
                System.out.println("barcode: "+product.getBarCode()+", name "+ product.getName()+", manufacture "+ product.getManufacture());

                System.out.println("Update the manufacture of product with barcode 100");
                product.setManufacture("TheWaterIsClean");
                if(productMapper.update(product)){
                    product = productMapper.findById(barcode);
                    System.out.println("barcode: "+product.getBarCode()+", name "+ product.getName()+", manufacture "+ product.getManufacture());
                } else {
                    System.out.println("Didnt update the product");
                }

            } else {
                System.out.println("Didnt insert the product");
            }

            if(productMapper.deleteById(barcode)){
                System.out.println("The item with the barcode " + barcode + " was deleted");
            } else {
                System.out.println("The item with the barcode " + barcode + " wasnt deleted");
            }*/

            OrderAndProductCtrl orderAndProductCtrl = new OrderAndProductCtrl();
            System.out.println(orderAndProductCtrl.getAllProductBarcodes());
            System.out.println(orderAndProductCtrl.getProduct(1).size);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
