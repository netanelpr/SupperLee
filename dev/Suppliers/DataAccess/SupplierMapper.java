package Suppliers.DataAccess;

import Suppliers.Structs.Days;
import Suppliers.Supplier.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SupplierMapper extends AbstractMapper<Supplier> {
    protected WeakValueHashMap<String, ContactInfo> loadedContacts;
    protected WeakValueHashMap<Integer, ContractWithSupplier> loadedContracts;
    protected WeakValueHashMap<Integer, ContractProduct> loadedProductsInContract;
    protected WeakValueHashMap<Integer, DiscountOfProduct> loadedDiscountsOfProducts;
    public SupplierMapper(Connection conn) {
        super(conn);
        loadedContacts            = new WeakValueHashMap<>();
        loadedContracts           = new WeakValueHashMap<>();
        loadedProductsInContract  = new WeakValueHashMap<>();
        loadedDiscountsOfProducts = new WeakValueHashMap<>();
    }

    //Inserts statements
    protected String insertSupplierStatement() {
        return "INSERT INTO Supplier (sup_name,address,account_number,paymentInfo,inc_number)  " +
                "Values (?,?,?,?,?)";
    }
    protected String insertContactStatement() {
        return "INSERT INTO Contact_info (name,phone_number,email,supplier_id)  " +
                "Values (?, ?, ?,?)";
    }
    private String addPaymentInfoStatement() {
        return "INSERT INTO Supplier_paymentOptions (sup_id,paymentOption)  " +
                "Values (?, ?)";
    }

    //Find Statements

    @Override
    protected String findStatement() {
        return "SELECT * " +
                "FROM Supplier join Contact_info On Supplier.id=Contact_info.supplier_id " +
                "WHERE id = 1 " +
                "LIMIT 1";
    }

    protected String findContactStatement()
    {
        return "SELECT * " +
                "FROM Contact_info" +
                "WHERE email = ? " +
                "AND supplier_id= ?";
    }

    protected String getAllSupplierContactsStatement()
    {
        return "SELECT * " +
                "FROM Contact_info" +
                "WHERE  supplier_id = ? ";
    }

    private String getAllSupplierPaymentInfoStatement() {
        return "SELECT * " +
                "FROM Supplier_paymentOptions" +
                "WHERE  sup_id = ? ";
    }

    //Update Statements
    protected String updateStatement() {
        return "UPDATE Supplier " +
                "SET name = ? " +
                "address= ?, inc_number = ? " +
                "WHERE id = ?";
    }

    //DeleteStatements
    @Override
    protected String deleteStatement() {
        return "DELETE FROM Supplier " +
                "WHERE id = ?";
    }

    protected String deleteContactStatement() {
        return "DELETE FROM Contact_info " +
                "WHERE email = ?";
    }

    private String deletePaymentOptionStatement() {
        return "DELETE FROM Supplier_paymentOptions " +
                "WHERE sup_id = ?"+
                "AND paymentOption=?";
    }


    public ContactInfo findContactBySupplierAndEmail(int supplierID,String email){
        ContactInfo res = loadedContacts.getOrDefault(email, null);

        if(res != null){
            return res;
        }

        try(PreparedStatement pstmt = conn.prepareStatement(findStatement())){

            pstmt.setString(1,email);
            pstmt.setInt(2,supplierID);
            ResultSet rs  = pstmt.executeQuery();

            res = buildContactFromResultSet(rs);
            loadedContacts.put(email, res);
            return res;

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return  null;
    }

    public List<ContactInfo> getAllSupplierContacts(int supplierID)
    {
        List<ContactInfo> contacts = new ArrayList<>();

        try(PreparedStatement pstmt = conn.prepareStatement(getAllSupplierContactsStatement())){
            pstmt.setInt(1,supplierID);
            ResultSet res = pstmt.executeQuery();

            while(res.next()){
                ContactInfo contactInfo=buildContactFromResultSet(res);
                contacts.add(contactInfo);
                this.loadedContacts.put(contactInfo.getEmail(),contactInfo);
            }

        } catch (java.sql.SQLException e) {
            return new LinkedList<>();
        }

        return contacts;
    }
    @Override
    //Build Supplier
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

    private ContactInfo buildContactFromResultSet(ResultSet res)
    {
        try {
            if(res.next()) {
                //String name, String phoneNumber, String email, int supID
                return new ContactInfo(res.getString("name"),res.getString("phone_number"),
                        res.getString("email"), res.getInt("supplier_id"));
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

        try(PreparedStatement pstmt = conn.prepareStatement(insertSupplierStatement(), Statement.RETURN_GENERATED_KEYS)){

            pstmt.setString(1, supplier.getSupplierName());
            pstmt.setString(2, supplier.getAddress());
            pstmt.setString(3, supplier.getAccountNumber());
            pstmt.setString(4, supplier.getPaymentInfo().get(0));
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
            System.out.println(e);
            return -1;
        }
    }

    public String insertContactInfo(ContactInfo newContact)
    {
        if (loadedContacts.getOrDefault(newContact.getEmail(), null) != null) {
            return newContact.getEmail();
        }

        try(PreparedStatement pstmt = conn.prepareStatement(insertSupplierStatement())){

            pstmt.setString(1, newContact.getName());
            pstmt.setString(2, newContact.getPhoneNumber());
            pstmt.setString(3, newContact.getEmail());
            pstmt.setInt(4, newContact.getSupID());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                return null;
            }

            this.loadedContacts.put(newContact.getEmail(), newContact);

            return newContact.getEmail();

        } catch (java.sql.SQLException e) {
            return null;
        }
    }

    public List<Days> getSupplyDays(int supplierId) {
        //TODO dont return null here
        throw new UnsupportedOperationException();
    }

    public boolean deleteContactInfo(int supID, String email) {

        try(PreparedStatement pstmt = conn.prepareStatement(deleteContactStatement())){

            pstmt.setString(1, email);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }
            if (loadedContacts.getOrDefault(email, null) != null) {
                loadedContacts.remove(email);
            }
            return true;

        } catch (java.sql.SQLException e) {
            return false;
        }
    }

    public List<String> getAllSupplierPaymentInfo(int supplierID) {
        List<String> paymentInfosOfSup = new ArrayList<>();

        try(PreparedStatement pstmt = conn.prepareStatement(getAllSupplierPaymentInfoStatement())){
            pstmt.setInt(1,supplierID);
            ResultSet res = pstmt.executeQuery();

            while(res.next()){
                paymentInfosOfSup.add(res.getString("paymentOption"));
            }

        } catch (java.sql.SQLException e) {
            return new LinkedList<>();
        }

        return paymentInfosOfSup;
    }


    public boolean addPaymentInfo(int supId, String paymentInfo) {
        try(PreparedStatement pstmt = conn.prepareStatement(addPaymentInfoStatement())){

            pstmt.setInt(1, supId);
            pstmt.setString(2, paymentInfo);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            if (loadedMap.getOrDefault(supId, null) != null) {
                loadedMap.get(supId).addPaymentOptions(paymentInfo);
            }
            return true;

        } catch (java.sql.SQLException e) {
            return false;
        }
    }


    public boolean removePaymentOption(int supId, String paymentInfo) {
        try(PreparedStatement pstmt = conn.prepareStatement(deletePaymentOptionStatement())){

            pstmt.setInt(1, supId);
            pstmt.setString(2, paymentInfo);


            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }
            if (loadedMap.getOrDefault(supId, null) != null) {
                loadedMap.get(supId).RemovePaymentOptions(paymentInfo);
            }
            return true;

        } catch (java.sql.SQLException e) {
            return false;
        }

    }


}
