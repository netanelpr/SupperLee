package Inventory.Logic;
import DataAccess.SupInvDBConn;
import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DTO.DefectiveDTO;
import Inventory.Persistence.Mappers.DefectivesMapper;
import Suppliers.Service.OrderDTO;
import Suppliers.Service.ProductInOrderDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class defectiveController implements myObservable {

    //fields
    private HashMap<String, List<Defective>> defectives; //item id, number of defectives items
    public final List<Observer> observers;
    private String shopNum;
    private DefectivesMapper myDefectiveMapper;
    private int defIdCounter = 0;

    //constructor
    public defectiveController(Observer o, String shopNum) {
        this.defectives = new HashMap<>();
        observers = new ArrayList<>();
        this.register(o);
        this.shopNum = shopNum;
        this.myDefectiveMapper = new DefectivesMapper(SupInvDBConn.getInstance());

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
                Defective newDefectReport = new Defective(observers, String.valueOf(defIdCounter), id, quantity, new Date(System.currentTimeMillis()), expired, defective, shopNum);
                defIdCounter++;
                defectives.get(id).add(newDefectReport);
                myDefectiveMapper.insert(new DefectiveDTO(newDefectReport));
                newDefectReport.defectiveItemStatus();
            }
            else {
                notifyObserver("this id does not exist!");
            }
        }
        else
            notifyObserver("wrong format input! type again:");
    }
    public void updateDefectivesSuppliers(OrderDTO order) {
        Defective newDefectReport;
        for (ProductInOrderDTO prod: order.productInOrderDTOList) {
            String id = String.valueOf(prod.barcode);
            if(!defectives.containsKey(id)) { //init the defective list for items in the first time they arrived to system
                defectives.put(id, new ArrayList<>());
                newDefectReport = new Defective(observers, String.valueOf(defIdCounter++), id, 0,
                        new Date(System.currentTimeMillis()), false, false, shopNum);
                defectives.get(id).add(newDefectReport);
                myDefectiveMapper.insert(new DefectiveDTO(newDefectReport));
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
        HashMap<String, List<DefectiveDTO>> defctsDTO = null;
        defctsDTO = myDefectiveMapper.load(shopNum);
        for (String itemId : defctsDTO.keySet()) {
            List<Defective> currDefctives = new ArrayList<>();
            for (DefectiveDTO currDTODef : defctsDTO.get(itemId)) {
                Defective def = new Defective(observers, currDTODef);
                currDefctives.add(def);
            }
            defectives.put(itemId, currDefctives);
            defIdCounter++;
        }
    }
    //endregion
}