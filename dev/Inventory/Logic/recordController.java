package Inventory.Logic;

import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DTO.RecordDTO;
import Inventory.Persistence.Mappers.RecordsMapper;
import Inventory.View.InvService;
import DataAccess.SupInvDBConn;
import Suppliers.Service.OrderDTO;
import Suppliers.Service.ProductInOrderDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class recordController implements myObservable {

    //region fields
    private HashMap<String, List<Record>> records; //item id, records(price&cost)
    public final List<Observer> observers;
    private String shopNum;
    private RecordsMapper myRecordMapper;
    private int idCounter = 0;

    //endregion

    //region constructor
    public recordController(Observer o, String shopNum) {
        this.records = new HashMap<>();
        observers = new ArrayList<>();
        this.register(o);
        this.shopNum = shopNum;
        this.myRecordMapper = new RecordsMapper(SupInvDBConn.getInstance());
    }
    public HashMap<String, List<Record>> getRecords() {
        return records;
    }
    //endregion

    //region record update
    public void updateRecordsSuppliers(OrderDTO order, Inventory inv, InvService invService) {
        Record newRecord;
        double newPrice;
        for (ProductInOrderDTO prod : order.productInOrderDTOList) {
            String id = String.valueOf(prod.barcode);
            if(records.containsKey(id)){
                List<Record> currList = records.get(id);
                Record lastRecord = currList.get(currList.size()-1);
                double oldCost = lastRecord.getCost();
                double newCost = prod.price;
                if(newCost != oldCost) {
                    String[] lastRecordInfo = new String[4];
                    lastRecordInfo[0] = lastRecord.getRecId();
                    lastRecordInfo[1] = lastRecord.getItemId();
                    lastRecordInfo[2] = String.valueOf(lastRecord.getPrice());
                    lastRecordInfo[3] = String.valueOf(lastRecord.getPriceChangeDate());
                    newPrice = inv.askUserPrice(newCost, oldCost, lastRecordInfo, invService);
                    if(newPrice != lastRecord.getPrice())
                        newRecord = new Record(observers, String.valueOf(idCounter++), lastRecordInfo[1], newCost, LocalDate.now(),
                                                newPrice, LocalDate.now(), shopNum);
                    else
                        newRecord = new Record(observers, String.valueOf(idCounter++), lastRecordInfo[1], newCost, LocalDate.now(),
                                                newPrice, changeToDate(lastRecordInfo[3]), shopNum);
                    records.get(id).add(newRecord);
                }
            }
            else {

                newRecord = new Record(observers, String.valueOf(idCounter++), id, prod.price, LocalDate.now(), LocalDate.now(), shopNum);
                records.put(id, new ArrayList<>());
                records.get(id).add(newRecord);
            }
        }
    }
    private LocalDate changeToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    public String[] getLastRecInfo(String id){
        String[] lastRecInfo = new String[2];
        List<Record> recList = records.get(id);
        Record lastRec = recList.get(recList.size()-1);
        lastRecInfo[0] = lastRec.getItemId();
        lastRecInfo[1]= String.valueOf(lastRec.getPrice());
        return lastRecInfo;
    }
    public void setNewPrice(String id, String newPrice, String nameLast, String priceLast) {
//        notifyObserver("which id to set deal?");
//        String id = myScanner.nextLine();
        //notifyObserver(id + ". " + lastRec.getName() + " -> current price: " + lastRec.getPrice() + "\n" +
//                "type new price: ");
        double dNewPrice = Double.parseDouble(newPrice);
        double oldPrice = Double.parseDouble(priceLast);
        List<Record> recList = records.get(id);
        Record lastRec = recList.get(recList.size()-1);
        Record newRecord = new Record(observers, id, nameLast, lastRec.getCost(), LocalDate.now(),
                dNewPrice , LocalDate.now(), shopNum);
        records.get(id).add(newRecord);
        if(dNewPrice  < oldPrice)
            notifyObserver("(~:\tNew Sale\t:~)");
        else
            notifyObserver(")~:\tExpensive you mtfkr\t:~(");
        newRecord.recordItemStatus();
    }
    //endregion

    //region record reports
    public void getRecordsReport(String id) {
        List<Record> recordLst = records.get(id);
        for (Record r : recordLst)
            r.recordItemStatus();
    }
    public void getGeneralRecordsReport() {
        for(String id : records.keySet())
            for (Record r : records.get(id))
                r.recordItemStatus();
    }
    //endregion

    //region observer
    @Override
    public void register(Observer o) {
        observers.add(o);
    }
    @Override
    public void notifyObserver(String msg) {
        observers.forEach(o -> o.onEvent(msg));
    }



    //endregion

    public void loadRecordsFromDB(String shopNum) {
        HashMap<String, List<RecordDTO>> recordsDTO = null;
        try {
            recordsDTO = myRecordMapper.load(shopNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (String id : recordsDTO.keySet()) {
            List <Record> currRecords = new ArrayList<>();
            for (RecordDTO currDTORec : recordsDTO.get(id)) {
                Record rec = new Record(observers, currDTORec);
                currRecords.add(rec);
            }
            records.put(id, currRecords);
        }
    }
}