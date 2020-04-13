package Service;

import Structs.Days;
import Structs.OrderStatus;
import Supplier.*;

import java.util.LinkedList;
import java.util.List;

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
        return supplierSystem.getPaymentOptions();
    }

    @Override
    public String getPaymentOptions(int supId) {
        return supplierSystem.getPaymentOptions(supId);
    }

    //TODO implement
    @Override
    public boolean addPaymentOptions(int supId, String[] paymentInfo) {
        return false;
    }

    //TODO implement
    @Override
    public boolean removePaymentOptions(int supId, String[] paymentInfo) {
        return false;
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

    //TODO implement
    @Override
    public boolean removeContactPerson(int supplierId, String email) {
        return false;
    }

    @Override
    public List<Integer> addContractToSupplier(int supplierId, String contractInfo, List<Days> days, List<AddProductInfoDTO> products) {
        List<AddProduct> addProducts = new LinkedList<>();

        for (AddProductInfoDTO addProduct : products) {
            addProducts.add(AddPITOToAddProduct(addProduct));
        }

        return supplierSystem.addContractToSupplier(supplierId, contractInfo, days, addProducts);
    }

    @Override
    public boolean addProductToContract(int supplierId, AddProductInfoDTO product) {
        return supplierSystem.addProductToContract(supplierId, AddPITOToAddProduct(product));
    }

    @Override
    public List<ProductDiscountsDTO> getAmountDiscountReport(int supplierId){
        List<ProductDiscounts> productDiscounts = supplierSystem.getAmountDiscountReport(supplierId);

        if(productDiscounts == null){
            return null;
        }

        List<ProductDiscountsDTO> productDiscountsDTOS = new LinkedList<>();
        for(ProductDiscounts productDiscount : productDiscounts){
            productDiscountsDTOS.add(ProductDiscountToDTO(productDiscount));
        }

        return productDiscountsDTOS;
    }

    @Override
    public int createNewOrder(int supplierId, List<ProductInOrderDTO> products) {
        List<ProductInOrder> productInOrders = new LinkedList<>();

        for(ProductInOrderDTO productInOrderDTO : products){
            productInOrders.add(ProductInOrderDTOToPIO(productInOrderDTO));
        }

        return supplierSystem.createNewOrder(supplierId, productInOrders);
    }

    @Override
    public boolean updateOrderArrivalTime(int orderId, Days day) {
        return supplierSystem.updateOrderArrivalTime(orderId, day);
    }

    @Override
    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        return supplierSystem.updateOrderStatus(orderId, status);
    }

    @Override
    public List<SupplierProductDTO> getAllSupplierProducts(int supplierId) {
        List<SupplierProductDTO> productDTOS = new LinkedList<>();
        List<SupplierProductInfo> supplierProducts = supplierSystem.getAllSupplierProducts(supplierId);

        if(supplierProducts == null){
            return null;
        }

        for(SupplierProductInfo product : supplierProducts){
            productDTOS.add(ContractProductToSupplerProductDTO(product));
        }

        return productDTOS;
    }

    @Override
    public List<Integer> getPurchaseHistory(int supplierId) {
        return supplierSystem.getPurchaseHistory(supplierId);
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

        ProductDiscounts product = new ProductDiscounts(addProductInfoDTO.productId,addProductInfoDTO.discounts.discountPerAmount,addProductInfoDTO.originalPrice);

        return new AddProduct(
                addProductInfoDTO.productId,
                addProductInfoDTO.productCatalogNumber,
                addProductInfoDTO.originalPrice,
                product,
                addProductInfoDTO.name,
                addProductInfoDTO.manufacture);
    }

    private static ProductDiscountsDTO ProductDiscountToDTO(ProductDiscounts productDiscounts){
        return new ProductDiscountsDTO(
                productDiscounts.barCode,
                productDiscounts.discountPerAmount,
                productDiscounts.originalPrice);
    }

    public static ProductInOrder ProductInOrderDTOToPIO(ProductInOrderDTO productInOrderDTO){
        return new ProductInOrder(
                productInOrderDTO.productId,
                productInOrderDTO.amount);
    }

    public static SupplierProductDTO ContractProductToSupplerProductDTO(SupplierProductInfo product){
        return new SupplierProductDTO(
                product.barCode,
                product.productCatalogNumber);
    }
}
