package Inventory.Persistence.Mappers;
import Inventory.Persistence.DTO.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class ItemsMapper<T> extends AbstractMappers  {

    //public HashMap<String, ItemDTO> items; //<id, ItemDTO>
    private Connection conn;

    public ItemsMapper(Connection conn) {
        this.conn = conn;
    }

    //region singelton Constructor
    private static ItemsMapper instance = null;
    private ItemsMapper() {
        super();
    }
    public static ItemsMapper getInstance(){
        if(instance == null)
            instance = new ItemsMapper();
        return instance;
    }
    //endregion

    public HashMap<String, ItemDTO> load() {
        return null;
    }

    @Override
    public void insert() {
    }
    public void insert(ItemDTO currDTO) {

        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO items" +
                                                    " (shopNum, id, qShop, qStorage) " +
                                                    "Values (?, ?, ?, ?)")){
            pstmt.setString(1, currDTO.getShopNum());
            pstmt.setString(2, currDTO.getId());
            pstmt.setString(3, currDTO.getQuantityShop());
            pstmt.setString(4, currDTO.getQuantityStorage());

            pstmt.executeUpdate();

        } catch (java.sql.SQLException e) {

        }
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }


}
