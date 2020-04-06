package Service;

import Service.*;
import Structs.Days;
import Structs.OrderStatus;
import Supplier.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class SupplierCtrl implements SupplierManagment {

    @Override
    public int createSupplierCard(String name, String incNum, String accountNumber, String paymentInfo) {
        return 0;
    }

    @Override
    public String getPaymentOptions() {
        return null;
    }

    @Override
    public String getPaymentOptions(int supId) {
        return null;
    }

    @Override
    public List<SupplierDetailsDTO> getAllSuppliers() {
        return null;
    }

    @Override
    public boolean addContactInfo(int supplierId, String contactPersonName, String phoneNumber, String email) {
        return false;
    }

    @Override
    public Map<Integer, Double> addContractInfo(int supplierId, String contractInfo, List<Days> days, List<AddProductInfoDTO> products) {
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
}
