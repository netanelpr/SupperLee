package Suppliers.DataAccess;

import Suppliers.Structs.Days;
import Suppliers.Structs.OrderStatus;
import Suppliers.Structs.StructUtils;
import Suppliers.Supplier.Order.PeriodicalOrder;
import Suppliers.Supplier.Order.ProductInOrder;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class PeriodicalOrderMapper extends AbstractMapper<PeriodicalOrder> {

    public PeriodicalOrderMapper(Connection conn) {
        super(conn);
    }

    @Override
    protected String findStatement() {
        return "SELECT S.*, PSO.weekp, PIC.barcode, P.contract_id, P.catalog_number, P.amount, P.price_per_unit\n" +
                "FROM Supplier_order AS S JOIN Periodical_supplier_order AS PSO\n" +
                "JOIN Product_in_order AS P JOIN Product_in_contract as PIC\n" +
                "\tON S.id = P.order_id AND P.catalog_number =  PIC.catalog_number\n" +
                "\tAND PSO.id = S.id\n" +
                "\tWHERE PSO.id = ?";
    }

    protected String findAllDays(){
        return "SELECT delivery_weekly_day AS days\n" +
                "FROM Periodical_supplier_order_days AS PSOD\n" +
                "WHERE PSOD.order_id = ?";
    }

    @Override
    public PeriodicalOrder findById(int id) {
        List<Days> days = new LinkedList<>();

        PeriodicalOrder res = loadedMap.getOrDefault(id, null);

        if(res != null){
            return res;
        }



        try(PreparedStatement pstmt = conn.prepareStatement(findStatement());
            PreparedStatement daysPstmt = conn.prepareStatement(findAllDays())){

            ResultSet rs;

            daysPstmt.setInt(1, id);
            rs = daysPstmt.executeQuery();

            while (rs.next()){
                days.add(StructUtils.getDayWithInt(rs.getInt(1)));
            }

            pstmt.setInt(1,id);
            rs  = pstmt.executeQuery();

            res = buildTFromResultSet(rs, days);
            loadedMap.put(id, res);
            return res;

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return  null;
    }

    @Override
    protected String deleteStatement() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected PeriodicalOrder buildTFromResultSet(ResultSet res) {
        throw new UnsupportedOperationException();
    }

    protected PeriodicalOrder buildTFromResultSet(ResultSet res, List<Days> days) {
        PeriodicalOrder periodicalOrder = null;
        int orderId = -1, shopNumber = -1, weekP = -1;
        OrderStatus status = null;
        String deliveryDay = "";
        List<ProductInOrder> products = new ArrayList<>();

        try {
            if(res.next()) {
                orderId = res.getInt(1);
                shopNumber = res.getInt(2);
                status = StructUtils.getOrderStatus(res.getInt(3));
                deliveryDay = res.getString(4);
                weekP = res.getInt(5);
                products.add(new ProductInOrder(res.getInt(6), res.getInt(9), res.getString(8), res.getDouble(10)));
            }

            while (res.next()) {
                products.add(new ProductInOrder(res.getInt(6), res.getInt(9), res.getString(8), res.getDouble(10)));
            }

            DateFormat dateFormat = new SimpleDateFormat(StructUtils.dateFormat());
            Date deliveryDate = dateFormat.parse(deliveryDay);
            if(Calendar.getInstance().getTime().compareTo(deliveryDate) > 0) {
                deliveryDate = getNextDate(dateFormat.parse(deliveryDay), days, weekP);
                updateDeliveryDate(orderId, deliveryDate);
            }


            periodicalOrder = PeriodicalOrder.CreatePeriodicalOrder(orderId, products, days, weekP, shopNumber, deliveryDate);
            periodicalOrder.setStatus(status);



        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return periodicalOrder;
    }

    private String insertIntoOrderStatement(){
        return "INSERT INTO Supplier_order (shop_number, status, delivery_day) " +
                "VALUES (?, ?, ?)";
    }

    private String insertIntoProductInOrderStatement(){
        return "INSERT INTO Product_in_order (order_id, contract_id, catalog_number, amount, price_per_unit) " +
                "VALUES (?, ?, ?, ?, ?)";
    }

    private String insertIdToPeriodicalOrderStatement(){
        return "INSERT INTO Periodical_supplier_order (id, weekp) " +
                "VALUES (?, ?)";
    }

    private String insertPeriodicalOrderDaysStatement(){
        return "INSERT INTO Periodical_supplier_order_days (order_id, delivery_weekly_day) " +
                "VALUES (?, ?)";
    }

    private String isValidShopStatement(){
        return "SELECT id from Inventory  " +
                "WHERE id = (?)";
    }

    public boolean isValidShopNumber(int shopNumber){
        try(PreparedStatement pstmt = conn.prepareStatement(isValidShopStatement())){
            pstmt.setString(1,""+shopNumber);
            ResultSet res = pstmt.executeQuery();

            if(res.next()){
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public int insert(PeriodicalOrder product) {

        int orderId = -1, contractId = product.getContractId();
        int rowAffected;
        boolean rollback = false;
        ResultSet res = null;

        if(!isValidShopNumber(product.getShopNumber())){
            return -2;
        }

        try{
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            //TODO
            throwables.printStackTrace();
        }

        try(PreparedStatement insertPstmt = conn.prepareStatement(insertIntoOrderStatement(), Statement.RETURN_GENERATED_KEYS);
            PreparedStatement productInsertPstmt = conn.prepareStatement(insertIntoProductInOrderStatement());
            PreparedStatement insertPeriodicalIdPstmt = conn.prepareStatement(insertIdToPeriodicalOrderStatement(), Statement.RETURN_GENERATED_KEYS);
            PreparedStatement insertDaysPstmt = conn.prepareStatement(insertPeriodicalOrderDaysStatement())){
            conn.setAutoCommit(false);

            insertPstmt.setInt(1,product.getShopNumber());
            insertPstmt.setInt(2, StructUtils.getOrderStatusInt(product.getStatus()));

            String strDate = StructUtils.dateToForamt(product.getDeliveryDay());
            insertPstmt.setString(3, strDate);

            rowAffected = insertPstmt.executeUpdate();

            // get the material id
            res = insertPstmt.getGeneratedKeys();
            if (res.next()) {
                orderId = res.getInt(1);
            }

            if (rowAffected != 0) {
                for (ProductInOrder productInOrder : product.getProducts()) {
                    productInsertPstmt.clearParameters();
                    productInsertPstmt.setInt(1, orderId);
                    productInsertPstmt.setInt(2, contractId);
                    productInsertPstmt.setString(3, productInOrder.getProductCatalogNumber());
                    productInsertPstmt.setInt(4, productInOrder.getAmount());
                    productInsertPstmt.setDouble(5, productInOrder.getPricePerUnit());
                    productInsertPstmt.addBatch();
                }
                productInsertPstmt.executeBatch();

                for (Days day : product.getDays()) {
                    System.out.println(day);
                    insertDaysPstmt.clearParameters();
                    insertDaysPstmt.setInt(1, orderId);
                    insertDaysPstmt.setInt(2, StructUtils.getDayInt(day));
                    insertDaysPstmt.addBatch();
                }
                insertDaysPstmt.executeBatch();

                insertPeriodicalIdPstmt.setInt(1, orderId);
                insertPeriodicalIdPstmt.setInt(2, product.getWeekPeriod());
                rowAffected = insertPeriodicalIdPstmt.executeUpdate();
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


    private String updateDeliveryDateStatement(){
        return "UPDATE Supplier_order\n" +
                "SET delivery_day = ?\n" +
                "WHERE id = ?";
    }

    public boolean updateDeliveryDate(int orderId, Date date){
        try(PreparedStatement ptsmt = conn.prepareStatement(updateDeliveryDateStatement())){

            ptsmt.setString(1,StructUtils.dateToForamt(date));
            ptsmt.setInt(2, orderId);

            ptsmt.executeUpdate();
            return true;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private Date getNextDate(Date date, List<Days> days, int weekP){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        while(c.getTime().compareTo(date) > 0){
            int nearestDayInDays = StructUtils.nearestDayInDays(days);

            if(nearestDayInDays != 8){

                if(c.get(Calendar.DAY_OF_WEEK) + nearestDayInDays < 8){
                    weekP = 0;
                } else{
                    weekP = weekP - 1;
                }

                c.add(Calendar.DATE, nearestDayInDays + (7 * weekP));
                return c.getTime();
            }
        }

        return c.getTime();

    }

    private String isPeriodicalOrder(){
        return "SELECT *\n" +
                "FROM Periodical_supplier_order\n" +
                "WHERE id = ?";
    }

    public boolean isPeriodicalOrder(int orderId) {
        try(PreparedStatement ptsmt = conn.prepareStatement(isPeriodicalOrder())){

            ptsmt.setInt(1,orderId);

            ResultSet rs = ptsmt.executeQuery();
            if(rs.next()) {
                return true;
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private String getTheOrderContractStatement(){
        return "SELECT DISTINCT(contract_id)\n" +
                "FROM Product_in_order\n" +
                "WHERE order_id = ?";
    }

    public int getTheOrderContract(int orderId) {
        try(PreparedStatement ptsmt = conn.prepareStatement(getTheOrderContractStatement())){

            ptsmt.setInt(1, orderId);

            ResultSet res = ptsmt.executeQuery();

            if(res.next()){
                return res.getInt(1);
            }

        } catch (SQLException e) {
        }
        return -1;
    }

    public List<Integer> addProductsToPeriodicalOrder(int orderId, List<ProductInOrder> productInOrders) {
        int contractId = getTheOrderContract(orderId);
        List<Integer> wasntAdded = new ArrayList<>();

        for(ProductInOrder product : productInOrders) {
            try (PreparedStatement ptsmt = conn.prepareStatement(insertIntoProductInOrderStatement())) {

                ptsmt.setInt(1, orderId);
                ptsmt.setInt(2, contractId);
                ptsmt.setString(3, product.getProductCatalogNumber());
                ptsmt.setInt(4, product.getAmount());
                ptsmt.setDouble(5, product.getPricePerUnit());

                ptsmt.executeUpdate();

            } catch (SQLException e) {
                wasntAdded.add(product.getBarcode());
            }
        }
        return wasntAdded;
    }

    private String allOpenOrdersStatement(){
        return "SELECT S.id\n" +
                "FROM Supplier_order AS S JOIN Periodical_supplier_order AS P\n" +
                "WHERE status = ? AND S.id = P.id";
    }

    public List<Integer> getAllOpenOrders() {
        ResultSet rs;
        List<Integer> orderIds = new ArrayList<>();

        try(PreparedStatement ptsmt = conn.prepareStatement(allOpenOrdersStatement())){
            ptsmt.setInt(1, StructUtils.getOrderStatusInt(OrderStatus.Open));

            rs = ptsmt.executeQuery();
            while(rs.next()){
                orderIds.add(rs.getInt(1));
            }
        } catch (SQLException throwables) {
        }

        return orderIds;
    }
}
