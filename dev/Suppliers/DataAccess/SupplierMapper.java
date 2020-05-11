package Suppliers.DataAccess;

import Result.Result;
import Suppliers.Supplier.Product;
import Suppliers.Supplier.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierMapper extends AbstractMapper<Supplier> {

    public SupplierMapper(Connection conn) {
        super(conn);
    }

    @Override
    protected String findStatement() {
        return "SELECT * " +
                "FROM Supplier join Contact_info On Supplier.id=Contact_info.supplier_id " +
                "WHERE id = 1 " +
                "LIMIT 1";
    }

    protected String insertSupplierStatement() {
        return "INSERT INTO Supplier (id,name,address,inc_number)  " +
                "Values (?, ?, ?,?)";
    }
    protected String insertContactStatement() {
        return "INSERT INTO Contact_info (name,phone_number,email,supplier_id)  " +
                "Values (?, ?, ?,?)";
    }

    @Override
    protected String deleteStatement() {
        return "DELETE FROM Supplier " +
                "WHERE id = ?";
    }

    protected String updateStatement() {
        return "UPDATE Supplier " +
                "SET name = ? " +
                "address= ?, inc_number = ? " +
                "WHERE id = ?";
    }

    @Override
    protected Supplier buildTFromResultSet(ResultSet res) {
        try {
            if(res.next()) {
                //int supID,String name, String address, String incNum, String accountNumber, String paymentInfo,
                //                    String contactName, String phoneNumber,String email
                return new Supplier(res.getInt("id"),res.getString("sup_name"),
                        res.getString("address"), res.getString("inc_number"),res.getString("account_number"), res.getString("paymentInfo"),
                        res.getString("name"), res.getString("phone_number"), res.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int insert(Supplier supplier) {
        if (loadedMap.getOrDefault(supplier.getSupId(), null) != null) {
            return supplier.getSupId();
        }

        try(PreparedStatement pstmt = conn.prepareStatement(insertSupplierStatement())){

            pstmt.setString(1, supplier.getSupplierName());
            pstmt.setString(2, supplier.getAddress());
            pstmt.setString(3, supplier.getAccountNumber());
            pstmt.setString(4, supplier.getPaymentInfo());
            pstmt.setString(5, supplier.getIncNum());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    supplier.setId(generatedKeys.getInt(1));
                }
                else {
                   return -1;
                }
            }

            loadedMap.put(supplier.getSupId(), supplier);
            return supplier.getSupId();

        } catch (java.sql.SQLException e) {
            return -1;
        }
    }
}
