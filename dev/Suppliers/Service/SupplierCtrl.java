package Suppliers.Service;

import Suppliers.Structs.Days;
import Suppliers.Structs.OrderStatus;
import Suppliers.Supplier.*;

import java.util.LinkedList;
import java.util.List;

public class SupplierCtrl implements SupplierManagment {

    private SupplierSystem supplierSystem;

    public SupplierCtrl(){
        supplierSystem = SupplierSystem.getInstance();
    }

    @Override
    public int createSupplierCard(String name, String incNum, String address, String accountNumber, String paymentInfo,
                                  String contactName, String phoneNumber,String email) {
        return supplierSystem.createSupplierCard(name, incNum, address, accountNumber, paymentInfo, contactName, phoneNumber, email);
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
    public List<Integer> addContractToSupplier(int supplierId, String contractInfo, List<Days> days, List<AddSupplierProductDTO> products) {
        List<AddProduct> addProducts = new LinkedList<>();

        for (AddSupplierProductDTO addProduct : products) {
            addProducts.add(AddPITOToAddProduct(addProduct));
        }

        return supplierSystem.addContractToSupplier(supplierId, contractInfo, days, addProducts);
    }

    @Override
    public boolean addProductToContract(int supplierId, AddSupplierProductDTO product) {
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
    public AddSupplierProductDTO getAllInformationAboutSuppliersProduct(int supplierId, int barcode) {
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

    private static AddProduct AddPITOToAddProduct(AddSupplierProductDTO addSupplierProductDTO){

        if(addSupplierProductDTO == null){
            return null;
        }

        ProductDiscounts product = new ProductDiscounts(addSupplierProductDTO.barcode, addSupplierProductDTO.discounts.discountPerAmount, addSupplierProductDTO.originalPrice);

        return new AddProduct(
                addSupplierProductDTO.barcode,
                addSupplierProductDTO.productCatalogNumber,
                addSupplierProductDTO.originalPrice,
                product,
                addSupplierProductDTO.name,
                addSupplierProductDTO.manufacture);
    }

    private static AddSupplierProductDTO AddPITOToAddProductReverse(AddProduct addProduct){

        if(addProduct == null){
            return null;
        }

        ProductDiscountsDTO productDiscounts = new ProductDiscountsDTO(addProduct.barCode,addProduct.productDiscounts.discountPerAmount,addProduct.originalPrice);

        return new AddSupplierProductDTO(
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

    public static SupplierProductDTO ContractProductToSupplerProductDTO(SupplierProductInfo product){
        return new SupplierProductDTO(
                product.barCode,
                product.productCatalogNumber);
    }
}
