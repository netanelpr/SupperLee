package Suppliers.Service;

import Suppliers.Supplier.ContactInfo;

import java.util.List;

public class SupplierDetailsDTO {

    public int supplierID;
    public String supplierName;
    public String incNum;
    public String accountNumber;
    public String address;

    public String contactName;
    public String phoneNumber;
    public String email;

    //TODO remove after edit
    public SupplierDetailsDTO(int supplierID, String supplierName) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
    }

    public SupplierDetailsDTO(int supplierID, String supplierName, String incNum, String accountNumber,
                              String address, String contactName, String phoneNumber, String email) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.incNum = incNum;
        this.accountNumber = accountNumber;
        this.address = address;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String toString(){
        return String.format("SupplierID : %d\nsupplier name : %s\nincorporation number: %s\naccount number : %s\naddress : %s",
                supplierID, supplierName, incNum, accountNumber, accountNumber);
    }
}
