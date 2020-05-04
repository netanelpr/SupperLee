package Suppliers.Service;

public class SupplierDetailsDTO {

    private int supplierID;
    private String supplierName;

    public SupplierDetailsDTO(int supplierID, String supplierName) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
    }

    public String toString(){
        return String.format("SupplierID : %d, supplier name : %s", supplierID, supplierName);
    }
}
