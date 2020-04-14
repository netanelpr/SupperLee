package Supplier;

import Service.AddProductInfoDTO;
import Service.SupplierProductDTO;
import Structs.Days;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContractWithSupplier {

    private List<Days> dailyInfo;
    private String contractDetails;
    private List<ContractProduct> products;

    public ContractWithSupplier(String contractDetails, List<Days> days){
        this.dailyInfo=days;
        this.contractDetails=contractDetails;
        this.products=new LinkedList<ContractProduct>();
    }
    //int produceId, String manufacture, String name,  int barcode, int productCatalogNumber, double originalPrice
    public boolean addProduct(AddProduct product){
        if(this.products.stream()
                .filter(x->x.getProductCatalogNumber().equals(product.productCatalogNumber))
                .findAny()
                .orElse(null)==null)
        {
            products.add(new ContractProduct(product.barCode,product.productCatalogNumber,product.productDiscounts));
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean addDiscountToProduct(int barcode, int amount, double discount){
        ContractProduct productToChange=this.products.stream()
                .filter(p->p.getBarCode()==barcode)
                .findFirst()
                .orElse(null);
        if(productToChange==null)
        {
            return false;
        }
        else
        {
            return productToChange.addDiscountToProduct(amount,discount);
        }
    }

    public List<Days> getDailyInfo() {
        return dailyInfo;
    }

    public String getContractDetails() {
        return contractDetails;
    }

    public List<ContractProduct> getProducts() {
        return products;
    }

    public List<ProductDiscounts> getAmountDiscountReport() {
        List<ProductDiscounts> barCodeToProductDiscounts= new LinkedList<ProductDiscounts>();
        for (ContractProduct cp:
             this.products) {
            barCodeToProductDiscounts.add(cp.getDiscounts());
        }
        return barCodeToProductDiscounts;
    }

    /**
     * Check if there is a product id in this contract
     * @param barcode barcode
     * @return true if the there is barcode
     */
    public boolean hasProduct(int barcode) {
        for(ContractProduct product : products){
            if(product.getBarCode() == barcode){
                return true;
            }
        }
        return false;

    }

    /**
     * fill the catalogNumber filed according to the product id
     * @param products products
     */
    public void fillWithCatalogNumber(List<ProductInOrder> products) {
        for(ContractProduct contractProduct : this.products){
            for(ProductInOrder product : products){
                if(product.getBarcode() == contractProduct.getBarCode()){
                    product.setProductCatalogNumber(contractProduct.getProductCatalogNumber());
                    break;
                }
            }
        }
    }
}