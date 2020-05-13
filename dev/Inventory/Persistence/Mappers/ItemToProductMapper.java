package Inventory.Persistence.Mappers;

import Inventory.Persistence.DTO.ItemDTO;
import Suppliers.DataAccess.ProductMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ItemToProductMapper {
    private Connection conn;
    private ItemsMapper itemsMapper;
    private ProductMapper productMapper;


public ItemToProductMapper(Connection conn){
    this.conn = conn;
}
    //region getters & setters

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public ItemsMapper getItemsMapper() {
        return itemsMapper;
    }

    public void setItemsMapper(ItemsMapper itemsMapper) {
        this.itemsMapper = itemsMapper;
    }

    public ProductMapper getProductMapper() {
        return productMapper;
    }

    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }


    //endregion

    public HashMap<String, ItemDTO> loadInvFromItemsAndProducts(String shopNum) {
        String query = "SELECT * " +
                        "FROM Items " +
                            "JOIN Product " +
                                "ON Items.id = Product.barcode " +
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

    private HashMap<String, ItemDTO> builtDTOfromRes(ResultSet res) throws SQLException {

        String shopNum, id, quantityShop, quantityStorage;
        String name, manufacturer, category, sub_category, size; int freqBuySupply; double minPrice;

        ItemDTO currItem;
        HashMap<String, ItemDTO> itemsDTO = new HashMap<>();

        while(res.next()){
            shopNum = res.getString(res.findColumn("shopNum"));
            id = res.getString(res.findColumn("id"));
            quantityShop = res.getString(res.findColumn("qShop"));
            quantityStorage = res.getString(res.findColumn("qStorage"));
            name = res.getString(res.findColumn("name"));
            manufacturer = res.getString(res.findColumn("manufacture"));
            category = res.getString(res.findColumn("categoty"));
            sub_category = res.getString(res.findColumn("subCategoty"));
            size = res.getString(res.findColumn("size"));
            freqBuySupply = res.getInt(res.findColumn("freqSupply"));
            minPrice = res.getDouble(res.findColumn("minPrice"));

            currItem = new ItemDTO(shopNum, id, quantityShop, quantityStorage,
                                    name, manufacturer, category, sub_category, size, freqBuySupply, minPrice);
            itemsDTO.put(currItem.getId(), currItem);
        }
        return itemsDTO;
    }
}
