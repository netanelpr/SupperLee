package Suppliers.Supplier;

import Suppliers.DataAccess.SupDBConn;
import Suppliers.DataAccess.SupplierMapper;
import Suppliers.Structs.Days;
import Suppliers.Structs.StructUtils;

import java.util.Date;
import java.util.List;

public class SupplierManager {

    private static SupplierManager instance = null;

    private SupplierMapper supplierMapper;

    private SupplierManager(){
        supplierMapper = new SupplierMapper(SupDBConn.getInstance());
    }

    public static SupplierManager getInstance(){
        if(instance == null){
            instance = new SupplierManager();
        }
        return instance;
    }

    public Supplier getById(int supplierId){
        return supplierMapper.findById(supplierId);
    }

    public int insert(Supplier supplier){
        return supplierMapper.insert(supplier);
    }

    public Supplier getOrNull(int supplierId){
        //TODO implement
        return null;
    }

    /**
     * Check if supplier have all the products
     * @param supplierId supplier id
     * @param barcodes list of barcodes
     * @return true if the supplier have all the barcodes
     */
    public boolean haveAllBarcodes(int supplierId, List<Integer> barcodes){
        //TODO implement
        return true;
    }

    /**
     * Return all the supplier ids which have all the barcodes
     * @param barcodes the barcodes to check
     * @return Return list with all the supplier ids which have all the barcodes
     */
    public List<Integer> getAllSupplierWithBarcodes(List<Integer> barcodes){
        //TODO implement return empty list is there isnt any one
        /*
         * sql query
         * SELECT S.id
         * FROM Supplier AS S
         * WHERE (SELECT barcode
		 *        FROM Suppliers_products as SP
		 *         WHERE SP.supplier_id = S.id) IN (?)
		 * Use conn.createArrayOf(...)
         */
        return null;
    }

    /**
     * Return all the supplier ids which have all the barcodes
     * @param supplierIds the supplier ids to check with
     * @param barcodes the barcodes to check
     * @return Return list with all the supplier ids which have all the barcodes
     */
    public List<Integer> getAllSupplierWithBarcodes(List<Integer> supplierIds, List<Integer> barcodes){
        //TODO implement return empty list is there isnt any one
        return null;
    }

    /**
     * Return all the supplier ids which supply in the given days
     * @param days the days to check with
     * @return Return list with all the supplier ids which supply in the given days
     */
    public List<Integer> getAllSupplierWithSupplyDays(List<Days> days) {
        //TODO implement return empty list is there isnt any one
        return  null;
    }

    public int getIdByContract(int contractId) {
        //TODO implement
        return -1;
    }

    public Supplier loadSupplierAndContacts(int supplierId) {
        //TODO implement
        return null;
    }
}
