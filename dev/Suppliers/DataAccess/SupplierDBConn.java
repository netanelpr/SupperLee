package Suppliers.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SupplierDBConn {
    private static Connection conn;

    private SupplierDBConn(){ }

    public static Connection getInstance(){
        try {
            if(conn == null) {
                conn = DriverManager.getConnection("jdbc:sqlite:DB/supplier.db");
            }
            return conn;
        } catch (SQLException throwables) {
            //TODO close the program if the conn get here
            throwables.printStackTrace();
        }
        return null;
    }

    public static void closeConn(){
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
