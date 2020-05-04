package Suppliers.DataAccess;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract class AbstractMapper<T> {
    protected Connection conn;
    protected HashMap<Integer, T> loadedMap;

    public AbstractMapper(Connection conn){
        this.conn = conn;
    }

    /**
     * The sql statement for getting the class by its id
     * @return sql statement
     */
    protected abstract String findStatement();

    public T findById(int id){
        T res = loadedMap.getOrDefault(id, null);

        if(res != null){
            return res;
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(findStatement());

            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();

            return  buildTFromResultSet(rs);

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return  null;
    }

    protected abstract String deleteStatement();

    public boolean deleteById(int id){
        try {
            PreparedStatement pstmt = conn.prepareStatement(deleteStatement());

            pstmt.setInt(1,id);
            pstmt.executeUpdate();

            //The item is deleted at this point no exception has been thrown
            T res = loadedMap.getOrDefault(id, null);

            if(res != null){
                loadedMap.remove(id);
            }

            return true;

        } catch (java.sql.SQLException e) {
            return  false;
        }
    }

    protected abstract T buildTFromResultSet(ResultSet res);

    protected abstract int insert(T product);
}
