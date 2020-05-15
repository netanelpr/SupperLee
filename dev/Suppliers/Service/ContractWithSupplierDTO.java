package Suppliers.Service;

import Suppliers.Structs.Days;
import Suppliers.Supplier.ContractWithSupplier;

import java.util.List;

public class ContractWithSupplierDTO {

    public List<String> dailyInfo;
    public String contractDetails;

    public ContractWithSupplierDTO(List<String> dailyInfo,String contractDetails)
    {
        this.contractDetails=contractDetails;
        this.dailyInfo=dailyInfo;
    }
}
