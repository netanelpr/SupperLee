package Inventory.Persistence.Mappers;

import Inventory.Persistence.DTO.itemDTO;
import Suppliers.Supplier.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ItemsMapper extends AbstractMappers {

    public HashMap<Integer, itemDTO> items; //<id, itemDTO>

    public ItemsMapper(Connection myDB) {
        super();
        items = new HashMap<>();
    }

    @Override
    public HashMap<Integer, itemDTO> load() throws SQLException {
        Connection conn = this.getMyDB();
        String query = "SELECT * FROM Items";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet  = statement.executeQuery();
        return builtDTOfromRes(resultSet);
    }


    @Override
    public void insert() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    private HashMap<Integer, itemDTO> builtDTOfromRes(ResultSet res) throws SQLException {
        HashMap<Integer, itemDTO> items = new HashMap<>();
        itemDTO currItem;
        while(res.next()){
            currItem = new itemDTO(res.findColumn("shopNum"),
                                    res.findColumn("id"),
                                    res.findColumn("quantityShop"),
                                    res.findColumn("quantityStorage"));
            items.put(currItem.getId(), currItem);
        }
        return items;
    }
}
