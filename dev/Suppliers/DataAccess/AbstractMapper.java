package Suppliers.DataAccess;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract class AbstractMapper<T> {
    protected Connection conn;

    protected WeakValueHashMap<Integer, T> loadedMap;

    public AbstractMapper(Connection conn){
        this.conn = conn;
        loadedMap = new WeakValueHashMap<>();
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

    /**
     * Delete a product from DB by its id
     * @param id The id of the product
     * @return true if the row was deleted
     */
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

    /**
     * Build T from the given ResultSet
     * @param res resultset that was return from the query
     * @return The new T
     */
    protected abstract T buildTFromResultSet(ResultSet res);

    /**
     * Insert a product to DB
     * Set its id if the product was inserted
     * @param product The product to insert
     * @return The id of the product
     */
    protected abstract int insert(T product);
}
