package Inventory.Logic;
import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DummyItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class defectiveController implements myObservable {

    //fields
    private HashMap<String, List<Defective>> defectives; //item id, number of defectives items
    public final List<Observer> observers;
    private String shopNum;


    //constructor
    public defectiveController(Observer o, String shopNum) {
        this.defectives = new HashMap<>();
        observers = new ArrayList<>();
        this.register(o);
        this.shopNum = shopNum;
    }
    public HashMap<String, List<Defective>> getDefectives() {
        return defectives;
    }

    //region defective update
    public void updateDefectives(String[] splited) {
        String id;
        int quantity;

        notifyObserver("enter defect or expired items quantities: ('id' 'quantity' 'expired?_(y/n)' 'defective?'_(y/n)') || '0' to stop");
        Boolean expired = false;
        Boolean defective = false;

        if(splited.length == 4) {
            id = splited[0];
            quantity = Integer.parseInt(splited[1]);
            if(splited[2].equals("y"))
                expired = true;
            else
                expired = false;
            if(splited[3].equals("y"))
                defective = true;
            else
                defective = false;
            if (defectives.containsKey(id)) {
                List<Defective> defList = defectives.get(id);
                Defective lastDef = defList.get(defList.size()-1);
                Defective newDefectReport = new Defective(observers, id, lastDef.getName(), quantity, java.time.LocalDate.now(), expired, defective, shopNum);
                defectives.get(id).add(newDefectReport);
                newDefectReport.defectiveItemStatus();
            }
            else {
                notifyObserver("this id does not exist!");
            }
        }
        else
            notifyObserver("wrong input! type again:");
    }
    public void updateDefectivesSuppliers(HashMap<DummyItem, Integer> supply) {
        for (DummyItem dummyItem : supply.keySet()) {
            String id = dummyItem.getId();
            if(!defectives.containsKey(id)) {
                Defective newDefectReport = new Defective(observers, id, dummyItem.getName(), 0, java.time.LocalDate.now(), shopNum);
                defectives.put(id, new ArrayList<>());
                defectives.get(id).add(newDefectReport);
            }
        }
    }
    //endregion

    //region defective reports
    public void getDefectivesReport() {
        for(String id : defectives.keySet())
            for(Defective def : defectives.get(id))
                if(def.getQuantity() != 0)
                    def.defectiveItemStatus();
    }
    public void getDefectivesReportById(String id) {
        List<Defective> defectLst = defectives.get(id);
        for (Defective dft : defectLst)
            dft.defectiveItemStatus();
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

    public void loadDefectiveFromDB(String shopNum) {

    }
    //endregion
}