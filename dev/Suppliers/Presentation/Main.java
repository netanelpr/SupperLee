package Suppliers.Presentation;

import Suppliers.DataAccess.ProductMapper;
import Suppliers.Supplier.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void dbExample(){
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:DB/supplier.db")) {
            Product product = null;

            ProductMapper productMapper = new ProductMapper(conn);
            product = productMapper.findById(1);
            System.out.println("barcode: "+product.getBarCode()+", name "+ product.getName()+", manufacture "+ product.getManufacture());

            //need to be the same as product.getBarcode() in this case
            int barcode = productMapper.insert(new Product(100,"aa","water"));
            if(barcode > -1){

                product = productMapper.findById(barcode);
                System.out.println("barcode: "+product.getBarCode()+", name "+ product.getName()+", manufacture "+ product.getManufacture());

            } else {
                System.out.println("Didnt insert the product");
            }

            if(productMapper.deleteById(barcode)){
                System.out.println("The item with the barcode " + barcode + " was deleted");
            } else {
                System.out.println("The item with the barcode " + barcode + " wasnt deleted");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        /*MainMenu mainMenu = new MainMenu();
        mainMenu.startMenu();*/
        dbExample();
    }
}
