package Inventory.Persistence.Mappers;
import Inventory.Logic.Defective;
import Inventory.Persistence.DTO.DefectiveDTO;
import Inventory.Persistence.DTO.RecordDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefectivesMapper extends AbstractMappers {

    private Connection conn;

    public DefectivesMapper(Connection conn) {
        this.conn = conn;
    }


    public HashMap<String, List<DefectiveDTO>> load(String shopNum) throws SQLException {
        String query = "SELECT * " +
                "FROM Defectives " +
                "WHERE shopNum = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, shopNum);
            ResultSet resultSet  = statement.executeQuery();
            return builtDTOfromRes(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HashMap<String, List<DefectiveDTO>> builtDTOfromRes(ResultSet res) throws SQLException {

        String defId, itemId, shopNum, tmpDate;
        int quantity;
        LocalDate updateDate;
        boolean expired, defective;

        DefectiveDTO currDef;
        HashMap<String, List<DefectiveDTO>> DefsDTO = new HashMap<>();

        while(res.next()){
            List<DefectiveDTO> tmpDefLst = new ArrayList<>();
            defId = res.getString(res.findColumn("defId"));
            itemId = res.getString(res.findColumn("itemId"));
            shopNum = res.getString(res.findColumn("shopNum"));
            quantity = res.getInt(res.findColumn("quantity"));
            expired = res.getBoolean(res.findColumn("expired"));
            defective = res.getBoolean(res.findColumn("defective"));
            tmpDate = res.getString(res.findColumn("updateDate"));
            updateDate = LocalDate.parse(tmpDate);
            currDef = new DefectiveDTO(defId, itemId, shopNum, quantity, updateDate, expired, defective);

            if(DefsDTO.keySet().contains(itemId))
                DefsDTO.get(itemId).add(currDef);
            else{
                tmpDefLst.add(currDef);
                DefsDTO.put(currDef.getDefId(), tmpDefLst);
            }
        }
        return DefsDTO;
    }


    @Override
    public void insert() {

    }

    @Override
    public void update() {

    }

    public void update(DefectiveDTO defDTO){


        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Records" +
                " (defId, itemId, quantity, updateDate, expired, defective, shopNum) " +
                "Values (?, ?, ?, ?, ?, ?, ?)")){
            pstmt.setString(1, defDTO.getDefId());
            pstmt.setString(2, defDTO.getItemId());
            pstmt.setInt(3, defDTO.getQuantity());
            //TODO deal with the dates and types!
            //pstmt.setDate(4, defDTO.getUpdateDate());
            pstmt.setBoolean(5, defDTO.isExpired());
            pstmt.setBoolean(6, defDTO.isDefective());
            pstmt.setString(7, defDTO.getShopNum());
            pstmt.executeUpdate();

        } catch (java.sql.SQLException e) { }
    }

    @Override
    public void delete() {

    }
}
