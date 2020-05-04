package Suppliers.DataAccess;

import Suppliers.Supplier.Product;

import java.sql.*;
import java.util.HashMap;
import java.util.Hashtable;

public class ProductMapper extends AbstractMapper<Product> {

    public ProductMapper(Connection conn) {
        super(conn);
        loadedMap = new HashMap<>();
    }

    @Override
    protected String findStatement() {
        return "SELECT * " +
                "FROM Product " +
                "WHERE barcode = ?";
    }


    @Override
    protected Product buildTFromResultSet(ResultSet res) {
        try {
            return new Product(res.getInt(1),
                                res.getString(2),
                                res.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected String insertStatement() {
        return "INSERT INTO Product (barcode, name, manufacture)  " +
                "Values (?, ?, ?)";
    }

    /*
     * In this case the id is the barcode and we know it. if we dont have the id
     * we need to fill the id in the given object
     */
    @Override
    public int insert(Product product) {
        if(loadedMap.getOrDefault(product.getBarCode(), null) != null){
            return product.getBarCode();
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(insertStatement());

            pstmt.setInt(1,product.getBarCode());
            pstmt.setString(2,product.getName());
            pstmt.setString(3,product.getManufacture());

            pstmt.executeUpdate();

            loadedMap.put(product.getBarCode(), product);
            return product.getBarCode();

        } catch (java.sql.SQLException e) {
            return -1;
        }
    }

    @Override
    protected String deleteStatement() {
        return "DELETE FROM Product " +
                "WHERE barcode = ?";
    }

}
