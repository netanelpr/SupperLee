package Inventory.Logic;

import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DTO.InventoryDTO;
import Inventory.Persistence.DummyItem;
import Inventory.View.InvService;
import Inventory.View.View;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inventory implements myObservable {

    private static int counter = 0;
    private String shopNum;
    private String shopName;
    public final List<Observer> observers;

    private itemsController myItemController;
    private recordController myRecoredController;
    private defectiveController myDefectivesController;


    public Inventory(View view){
        counter++;
        this.shopNum = String.valueOf(counter);
        this.myItemController = new itemsController(view);
        this.myRecoredController = new recordController(view);
        this.myDefectivesController = new defectiveController(view);
        observers = new ArrayList<>();
        this.register(view);
        shopName = "";
    }

    public Inventory(View view, InventoryDTO invDTO){
        counter++;
        this.shopNum = invDTO.getShopNum();
        this.shopName = invDTO.getShopName();
        this.myItemController = new itemsController(view);
        this.myRecoredController = new recordController(view);
        this.myDefectivesController = new defectiveController(view);
        observers = new ArrayList<>();
        this.register(view);
    }


    //region getters
    public String getShopNum() {
        return shopNum;
    }
    public HashMap<String, Item> getItems() {
        return myItemController.getItems();
    }
    public HashMap<String, List<Record>> getRecords() {
        return myRecoredController.getRecords();
    }
    public HashMap<String, List<Defective>> getDefectives() {
        return myDefectivesController.getDefectives();
    }
    public itemsController getMyItemController() {
        return myItemController;
    }
    public recordController getMyRecoredController() {
        return myRecoredController;
    }
    public defectiveController getMyDefectivesController() {
        return myDefectivesController;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    //endregion


    //region updates items Inventory
    public OrderItem updateInventoryWorkers(String id, int quanMissStock, int quanMissShop) {
        return myItemController.updateInventoryWorkers(id, quanMissStock, quanMissShop);
    }
    public void updateInventorySuppliers(HashMap<DummyItem, Integer> supply, InvService invService) {
        myRecoredController.updateRecordsSuppliers(supply, this, invService);
        myItemController.updateInventorySuppliers(supply);
        myDefectivesController.updateDefectivesSuppliers(supply);
    }
    public double askUserPrice(double newCost, double oldCost, String[] lastRecordInfo, InvService invService) {
        return invService.askUserPrice(newCost, oldCost, lastRecordInfo);
    }
    //endregion

    //region reports Inventory
    public void getItemReport() {
        myItemController.getItemReport();
    }
    public void getItemReportById(String id) {
        myItemController.getItemReportById(id);
    }
    public void getItemReportByCategory(String cat) {
        myItemController.getItemReportByCategory(cat);
    }
    public void getItemMissing() {
        myItemController.getItemMissing();
    }
    public void getRecordsReportById(String id) {
        myRecoredController.getRecordsReport(id);
    }
    public void getGeneralRecordsReport() {
        myRecoredController.getGeneralRecordsReport();
    }
    public void getDefectivesReport() {
        myDefectivesController.getDefectivesReport();
    }
    public void getDefectivesReportById(String id) {
        myDefectivesController.getDefectivesReportById(id);
    }
    //endregion

    //region updates records Inventory
    public void setNewPrice(String id, String newPrice, String nameLast, String priceLast) {
        myRecoredController.setNewPrice(id, newPrice, nameLast, priceLast);
    }
    public String[] getLastRec(String id) {
        return myRecoredController.getLastRecInfo(id);
    }
    //endregion

    //region update defectives Inventory
    public void updateDefectives(String[] splited) {
        myDefectivesController.updateDefectives(splited);
    }
    //endregion

    //region observer
    public void register(Observer o) {
        observers.add(o);
    }
    public void notifyObserver(String msg) {
        observers.forEach(o -> o.onEvent(msg));
    }

    public void loadInvDB() {
        myItemController.loadItemsFromDB(shopNum);
        myDefectivesController.loadDefectiveFromDB(shopNum);
        myRecoredController.loadRecordsFromDB(shopNum);
    }
    //endregion

}
