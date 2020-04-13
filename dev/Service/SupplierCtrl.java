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
    public int createSupplierCard(String name, String incNum, String accountNumber, String paymentInfo,
                                  String contactName, String phoneNumber,String email) {
        return supplierSystem.createSupplierCard(name, incNum, accountNumber, paymentInfo, contactName, phoneNumber, email);
    }

    @Override
    public String getPaymentOptions() {
        return supplierSystem.getPaymentOptions();
    }

    @Override
    public String getPaymentOptions(int supId) {
        return supplierSystem.getPaymentOptions(supId);
    }

    @Override
    public boolean addPaymentOptions(int supId, String[] paymentInfo) {
        return supplierSystem.addPaymentOptions(supId, paymentInfo);
    }

    @Override
    public boolean removePaymentOptions(int supId, String[] paymentInfo) {
        return supplierSystem.removePaymentOptions(supId, paymentInfo);
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
    public boolean removeContactPerson(int supplierId, String email) {
        return this.supplierSystem.RemoveContactFromSupplier(supplierId,email);
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
    public AddProductInfoDTO getAllInformationAboutSuppliersProduct(int supplierId, int barcode) {
        AddProduct supplierProductInfo= this.supplierSystem.getAllInformationAboutSuppliersProduct(supplierId, barcode);
        return AddPITOToAddProductReverse(supplierProductInfo);
    }

    @Override
    public List<String> getPurchaseHistory(int supplierId) {
        return supplierSystem.getPurchaseHistory(supplierId);
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

        ProductDiscounts product = new ProductDiscounts(addProductInfoDTO.barcode,addProductInfoDTO.discounts.discountPerAmount,addProductInfoDTO.originalPrice);

        return new AddProduct(
                addProductInfoDTO.barcode,
                addProductInfoDTO.productCatalogNumber,
                addProductInfoDTO.originalPrice,
                product,
                addProductInfoDTO.name,
                addProductInfoDTO.manufacture);
    }

    private static AddProductInfoDTO AddPITOToAddProductReverse(AddProduct addProduct){

        if(addProduct == null){
            return null;
        }

        ProductDiscountsDTO productDiscounts = new ProductDiscountsDTO(addProduct.barCode,addProduct.productDiscounts.discountPerAmount,addProduct.originalPrice);

        return new AddProductInfoDTO(
                addProduct.barCode,
                addProduct.productCatalogNumber,
                addProduct.originalPrice,
                productDiscounts,
                addProduct.name,
                addProduct.manufacture);
    }

    private static ProductDiscountsDTO ProductDiscountToDTO(ProductDiscounts productDiscounts){
        return new ProductDiscountsDTO(
                productDiscounts.barCode,
                productDiscounts.discountPerAmount,
                productDiscounts.originalPrice);
    }

    public static ProductInOrder ProductInOrderDTOToPIO(ProductInOrderDTO productInOrderDTO){
        return new ProductInOrder(
                productInOrderDTO.barcode,
                productInOrderDTO.amount);
    }

    public static SupplierProductDTO ContractProductToSupplerProductDTO(SupplierProductInfo product){
        return new SupplierProductDTO(
                product.barCode,
                product.productCatalogNumber);
    }
}
