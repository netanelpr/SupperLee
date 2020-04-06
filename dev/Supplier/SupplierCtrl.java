package Supplier;

import Service.*;
import Structs.Days;
import Structs.OrderStatus;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class SupplierCtrl implements SupplierManagment {
    @Override
    public int createSupplierCard(String name, String incNum, String AccountNumber, String PaymentInfo) {
        return 0;
    }

    @Override
    public String getPaymentOptions() {
        return null;
    }

    @Override
    public List<SupplierDetails> getAllSuppliers() {
        return null;
    }

    @Override
    public boolean addContactInfo(int supplierID, String contactPersonName, String phoneNumber, String Email) {
        return false;
    }

    @Override
    public Map<Integer, Double> addContractInfo(int supplierID, List<Days> days, List<AddProductInfo> products) {
        return null;
    }

    @Override
    public int ProductToContract(int supplierID, AddProductInfo product) {
        return 0;
    }

    @Override
    public List<ProductDiscount> getAmountDiscountReport(int supplierID) {
        return null;
    }

    @Override
    public int createNewOrder(int supplierID, List<ProductInOrder> products, DateTimeFormatter time) {
        return 0;
    }

    @Override
    public boolean updateOrderArrivalTime(int supplierID, int orderID, DateTimeFormatter time) {
        return false;
    }

    @Override
    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        return false;
    }

    @Override
    public List<SupplierProduct> getAllSupplierProducts(int supplierID) {
        return null;
    }

    @Override
    public List<Integer> getPurchaseHistory(int supplierID) {
        return null;
    }

    @Override
    public List<Days> getProductSupplyTimingInterval(int supplierID) {
        return null;
    }
}
