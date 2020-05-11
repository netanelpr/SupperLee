package Suppliers.DataAccess;

import Suppliers.Structs.OrderStatus;
import Suppliers.Structs.StructUtils;
import Suppliers.Supplier.Order.ProductInOrder;
import Suppliers.Supplier.Order.RegularOrder;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RegularOrderMapper extends AbstractMapper<RegularOrder> {

    public RegularOrderMapper(Connection conn) {
        super(conn);
    }

    @Override
    protected String findStatement() {
        return "SELECT S.*, PIC.barcode, P.contract_id, P.catalog_number, P.amount, P.price_per_unit\n" +
                "FROM Supplier_order AS S JOIN Product_in_order AS P\n" +
                "JOIN Product_in_contract as PIC\n" +
                "ON S.id = P.order_id AND P.catalog_number =  PIC.catalog_number\n" +
                "WHERE S.id IN Regular_supplier_order AND S.id = ?";
    }

    @Override
    protected String deleteStatement() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected RegularOrder buildTFromResultSet(ResultSet res) {
        RegularOrder regularOrder = null;
        int orderId = -1, shopNumber = -1;
        OrderStatus status = null;
        String deliveryDay = "";
        List<ProductInOrder> products = new ArrayList<>();

        try {
            if(res.next()) {
                orderId = res.getInt(1);
                shopNumber = res.getInt(2);
                status = StructUtils.getOrderStatus(res.getInt(3));
                deliveryDay = res.getString(4);
                products.add(new ProductInOrder(res.getInt(5), res.getInt(8), res.getString(7), res.getDouble(9)));
            }

            while (res.next()) {
                products.add(new ProductInOrder(res.getInt(5), res.getInt(8), res.getString(7), res.getDouble(9)));
            }

            regularOrder = RegularOrder.CreateRegularOrder(orderId, products, shopNumber);
            regularOrder.setStatus(status);

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            regularOrder.setDeliveryDay(dateFormat.parse(deliveryDay));

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return regularOrder;
    }

    private String insertIntoOrderStatement(){
        return "INSERT INTO Supplier_order (shop_number, status, delivery_day) " +
                "VALUES (?, ?, ?)";
    }

    private String insertIntoProductInOrderStatement(){
        return "INSERT INTO Product_in_order (order_id, contract_id, catalog_number, amount, price_per_unit) " +
                "VALUES (?, ?, ?, ?, ?)";
    }

    private String insertIdToRegularOrderStatement(){
        return "INSERT INTO Regular_supplier_order (id) " +
                "VALUES (?)";
    }

    @Override
    public int insert(RegularOrder product) {

        int orderId = -1, contractId = product.getContractId();
        int rowAffected;
        boolean rollback = false;
        ResultSet res = null;

        try{
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            //TODO
            throwables.printStackTrace();
        }

        try(PreparedStatement insertPstmt = conn.prepareStatement(insertIntoOrderStatement(), Statement.RETURN_GENERATED_KEYS);
            PreparedStatement productInsertPstmt = conn.prepareStatement(insertIntoProductInOrderStatement());
            PreparedStatement insertRegularIdPstmt = conn.prepareStatement(insertIdToRegularOrderStatement(), Statement.RETURN_GENERATED_KEYS)){
            conn.setAutoCommit(false);

            insertPstmt.setInt(1,product.getShopNumber());
            insertPstmt.setInt(2, StructUtils.getOrderStatusInt(product.getStatus()));
            //TODO edit it
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormat.format(product.getDeliveryDay());
            insertPstmt.setString(3, strDate);

            rowAffected = insertPstmt.executeUpdate();

            // get the material id
            res = insertPstmt.getGeneratedKeys();
            if (res.next()) {
                orderId = res.getInt(1);
            }

            if (rowAffected != 0) {
                for (ProductInOrder productInOrder : product.retrunProducts()) {
                    productInsertPstmt.clearParameters();
                    productInsertPstmt.setInt(1, orderId);
                    productInsertPstmt.setInt(2, contractId);
                    productInsertPstmt.setString(3, productInOrder.getProductCatalogNumber());
                    productInsertPstmt.setInt(4, productInOrder.getAmount());
                    productInsertPstmt.setDouble(5, productInOrder.getPricePerUnit());
                    productInsertPstmt.addBatch();
                }
                productInsertPstmt.executeBatch();

                insertRegularIdPstmt.setInt(1, orderId);
                rowAffected = insertRegularIdPstmt.executeUpdate();
                if (rowAffected == 0) {
                    rollback = true;
                }
            } else {
                rollback = true;
            }


        } catch (SQLException throwables) {
            rollback = true;
            throwables.printStackTrace();
        }

        try{
            if(!rollback) {
                conn.commit();
            } else {
                conn.rollback();
            }
            conn.setAutoCommit(true);
        } catch (SQLException throwables) {
            //TODO
            throwables.printStackTrace();
        }

        return orderId;
    }


    public boolean updateStatus(){
        return true;
    }
}
