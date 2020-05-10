package Inventory.Persistence.Mappers;

import Inventory.Persistence.DTO.InventoryDTO;
import Inventory.Persistence.DTO.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class InventoriesMapper extends AbstractMappers {
    private Connection conn;
    //region singelton Constructor
    private static InventoriesMapper instance = null;
    private InventoriesMapper() {
        super();
        this.conn = getMyDB();
    }
    public static InventoriesMapper getInstance(){
        if(instance == null)
            instance = new InventoriesMapper();
        return instance;
    }
    //endregion

    @Override
    public HashMap<String, InventoryDTO> load() {
        String query = "SELECT * " +
                        "FROM Inventory";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            ResultSet resultSet  = statement.executeQuery();
            return buildInvsfromRes(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HashMap<String, InventoryDTO> buildInvsfromRes(ResultSet res) {
        InventoryDTO currInv;
        HashMap<String, InventoryDTO> invs = new HashMap<>(); //<id, inventoryDTO>
        try {
            while(res.next()){
                currInv = new InventoryDTO(res.getString(1), res.getString(2));
                invs.put(currInv.getShopNum(), currInv);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invs;
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

}
