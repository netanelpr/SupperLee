package Inventory.Persistence.Mappers;

import Suppliers.DataAccess.SupplierDBConn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class AbstractMappers<T> {

    private Connection myDB;

    public AbstractMappers() {
        this.myDB = SupplierDBConn.getInstance();
    }

    //region getters&setters
    public Connection getMyDB() {
        return myDB;
    }

    public void setMyDB(Connection myDB) {
        this.myDB = myDB;
    }
    //endregion

   // public abstract HashMap<Integer, T> load() throws SQLException;
    public abstract void insert();
    public abstract void update();
    public abstract void delete();

}
