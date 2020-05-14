package Inventory.Logic;
import DataAccess.SupInvDBConn;
import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DTO.DefectiveDTO;
import Inventory.Persistence.DTO.ItemDTO;
import Inventory.Persistence.DTO.RecordDTO;
import Inventory.Persistence.DummyItem;
import Inventory.Persistence.Mappers.DefectivesMapper;
import Inventory.Persistence.Mappers.ItemToProductMapper;
import Suppliers.Service.OrderDTO;
import Suppliers.Service.ProductInOrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class defectiveController implements myObservable {

    //fields
    private HashMap<String, List<Defective>> defectives; //item id, number of defectives items
    public final List<Observer> observers;
    private String shopNum;
    private DefectivesMapper MyDefectiveMapper;
    private int idCounter = 0;

    //constructor
    public defectiveController(Observer o, String shopNum) {
        this.defectives = new HashMap<>();
        observers = new ArrayList<>();
        this.register(o);
        this.shopNum = shopNum;
        this.MyDefectiveMapper = new DefectivesMapper(SupInvDBConn.getInstance());

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
                Defective newDefectReport = new Defective(observers, String.valueOf(idCounter), id, quantity, java.time.LocalDate.now(), expired, defective, shopNum);
                idCounter++;
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
    public void updateDefectivesSuppliers(OrderDTO order) {
        for (ProductInOrderDTO prod: order.productInOrderDTOList) {
            String id = String.valueOf(prod.barcode);
            if(!defectives.containsKey(id)) {
                Defective newDefectReport = new Defective(observers, String.valueOf(idCounter++), id, 0,
                                                            java.time.LocalDate.now(), false, false, shopNum);
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
        HashMap<String, List<DefectiveDTO>> defctsDTO = null;
        try {
            defctsDTO = MyDefectiveMapper.load(shopNum);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        for (String id : defctsDTO.keySet()) {
            List<Defective> currDefctives = new ArrayList<>();
            for (DefectiveDTO currDTODef : defctsDTO.get(id)) {
                Defective def = new Defective(observers, currDTODef);
                currDefctives.add(def);
            }
            defectives.put(id, currDefctives);
        }

    }
    //endregion
}