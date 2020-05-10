package Inventory.Logic;

import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DummyItem;
import Inventory.View.InvService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class recordController implements myObservable {

    //region fields
    private HashMap<String, List<Record>> records; //item id, records(price&cost)
    public final List<Observer> observers;
    //endregion

    //region constructor
    public recordController(Observer o) {
        //this.myScanner = new Scanner(System.in);
        this.records = new HashMap<>();
        observers = new ArrayList<>();
        this.register(o);
    }
    public HashMap<String, List<Record>> getRecords() {
        return records;
    }
    //endregion

    //region record update
    public void updateRecordsSuppliers(HashMap<DummyItem, Integer> supply, Inventory inv, InvService invService) {
        Record newRecord;
        double newPrice;
        for (DummyItem dummyItem : supply.keySet()) {
            String id = dummyItem.getId();
            if(records.containsKey(dummyItem.getId())) {
                List<Record> currList = records.get(id);
                Record lastRecord = currList.get(currList.size()-1);
                double oldCost = lastRecord.getCost();
                double newCost = dummyItem.getCost();
                if(newCost != oldCost) {
                    String[] lastRecordInfo = new String[4];
                    lastRecordInfo[0] = lastRecord.getId();
                    lastRecordInfo[1] = lastRecord.getName();
                    lastRecordInfo[2] = String.valueOf(lastRecord.getPrice());
                    lastRecordInfo[3] = String.valueOf(lastRecord.getPriceChangeDate());
                    newPrice = inv.askUserPrice(newCost, oldCost, lastRecordInfo, invService);
                    if(newPrice != lastRecord.getPrice())
                        newRecord = new Record(observers, lastRecordInfo[0], lastRecordInfo[1], newCost, LocalDate.now(),
                                                newPrice, LocalDate.now());
                    else
                        newRecord = new Record(observers, lastRecordInfo[0], lastRecordInfo[1], newCost, LocalDate.now(),
                                                newPrice, changeToDate(lastRecordInfo[3]));
                    records.get(id).add(newRecord);
                }
            }
            else {
                newRecord = new Record(observers, id, dummyItem.getName(), dummyItem.getCost(), LocalDate.now(), LocalDate.now());
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
//    public Record askUserPrice(double newCost, double oldCost, Record lastRecord) {
//        Record newRecord;
//        notifyObserver("The supplier has changed the cost of " + lastRecord.getName() + "\n" +
//                "old cost: " + oldCost + "$ -- new cost: " + newCost + "$ -- old price: " + lastRecord.getPrice() + "$\n" +
//                "would you like to change price? (y 'new_price' | n)");
//        String ans = myScanner.nextLine();
//        if(!ans.equals("n")) {
//            ans = ans.substring(2);//ans is the new price
//            double newPrice = Double.parseDouble(ans);
//            newRecord = new Record(observers, lastRecord.getId(), lastRecord.getName(), newCost, LocalDate.now(), newPrice, LocalDate.now());
//            notifyObserver("|--------------------------------------------------\n" +
//                    "|price changed! new price for " + lastRecord.getId() + ". " + lastRecord.getName() + ": " + lastRecord.getPrice() + "$\n"+
//                    "|--------------------------------------------------");
//        }
//        else {
//            List<Record> tmplist = records.get(lastRecord.getId());
//            Record lastRec = tmplist.get(tmplist.size()-1);
//            newRecord = new Record(observers, lastRecord.getId(), lastRecord.getName(), newCost, LocalDate.now(), lastRecord.getPrice(), lastRec.getPriceChangeDate());
//            notifyObserver("|--------------------------------------------------\n" +
//                    "|price didnt changed " + "\n" +
//                    "|--------------------------------------------------\n");
//        }
//
//        return newRecord;
//    }
    public String[] getLastRecInfo(String id){
        String[] lastRecInfo = new String[2];
        List<Record> recList = records.get(id);
        Record lastRec = recList.get(recList.size()-1);
        lastRecInfo[0] = lastRec.getName();
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
                dNewPrice , LocalDate.now());
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

    public void loadRecordsFromDB(String shopNum) {
    }
    //endregion
}
