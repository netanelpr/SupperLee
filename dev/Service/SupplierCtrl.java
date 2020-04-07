package Service;

import Service.*;
import Structs.Days;
import Structs.OrderStatus;
import Supplier.*;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SupplierCtrl implements SupplierManagment {

    private SupplierSystem supplierSystem;

    public SupplierCtrl(){
        supplierSystem = new SupplierSystem();
    }

    @Override
    public int createSupplierCard(String name, String incNum, String accountNumber, String paymentInfo) {
        return supplierSystem.createSupplierCard(name, incNum, accountNumber, paymentInfo);
    }

    @Override
    public String getPaymentOptions() {
        return null;
    }

    @Override
    public String getPaymentOptions(int supId) {
        return supplierSystem.getPaymentOptions(supId);
    }

    @Override
    public List<SupplierDetailsDTO> getAllSuppliers() {
        List<SupplierDetailsDTO> supDetailsDTO = new LinkedList<>();
        List<SupplierDetails> supplierDetails = supplierSystem.getAllSuppliers();

        for (SupplierDetails supd: supplierDetails) {
            supDetailsDTO.add(supplierDetailsToDTO(supd));
        }

        return supDetailsDTO;
    }

    @Override
    public boolean addContactInfo(int supplierId, String contactPersonName, String phoneNumber, String email) {
        return supplierSystem.addContactInfo(supplierId, contactPersonName, phoneNumber, email);
    }

    @Override
    public Map<Integer, Integer> addContractInfo(int supplierId, String contractInfo, List<Days> days, List<AddProductInfoDTO> products) {
        return null;
    }

    @Override
    public int ProductToContract(int supplierId, AddProductInfoDTO product) {
        return 0;
    }

    @Override
    public List<ProductDiscountDTO> getAmountDiscountReport(int supplierId) {
        return null;
    }

    @Override
    public int createNewOrder(int supplierId, List<ProductInOrderDTO> products, Days day) {
        return 0;
    }

    @Override
    public boolean updateOrderArrivalTime(int supplierID, int orderID, Days day) {
        return false;
    }

    @Override
    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        return false;
    }

    @Override
    public List<SupplierProductDTO> getAllSupplierProducts(int supplierId) {
        return null;
    }

    @Override
    public List<Integer> getPurchaseHistory(int supplierId) {
        return null;
    }

    @Override
    public List<Days> getProductSupplyTimingInterval(int supplierID) {
        return null;
    }

    private static SupplierDetailsDTO supplierDetailsToDTO(SupplierDetails supplierDetails){

        if(supplierDetails == null){
            return null;
        }

        return new SupplierDetailsDTO(
                supplierDetails.supplierId,
                supplierDetails.supplierName
        );
    }

    private static AddProduct AddPITOToAddProduct(AddProductInfoDTO addProductInfoDTO){

        if(addProductInfoDTO == null){
            return null;
        }

        return new AddProduct(
                addProductInfoDTO.productId,
                addProductInfoDTO.productCatalogNumber,
                addProductInfoDTO.originalPrice,
                /*addProductInfoDTO.name,
                addProductInfoDTO.manufacture,
                addProductInfoDTO.category,
                addProductInfoDTO.subCategory,
                addProductInfoDTO.size,
                addProductInfoDTO.freqSupply,*/
                addProductInfoDTO.discountPerAmount
        );
    }
}
