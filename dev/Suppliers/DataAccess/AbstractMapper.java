package Suppliers.DataAccess;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract class AbstractMapper<T> {
    protected HashMap<Integer, T> loadedMap;
    protected String url;

    public AbstractMapper(String url){
        this.url = url;
    }

    /**
     * The sql statement for getting the class by its id
     * @return sql statement
     */
    abstract String findStatement();

    public T findById(int id){
        T res = loadedMap.getOrDefault(id, null);

        if(res != null){
            return res;
        }

        PreparedStatement findStatment = null;
        try (Connection conn = DriverManager.getConnection(url)){
          System.out.println("Open db");
          PreparedStatement pstmt = conn.prepareStatement(findStatement());

          pstmt.setInt(1,id);
          ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("barcode") +  "\t" +
                                   rs.getString("name") + "\t" +
                                   rs.getString("manufacture"));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return  null;
    }

    abstract int insert(T domain);
}
