package Inventory.Logic;

import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Persistence.DTO.DefectiveDTO;

import java.time.LocalDate;
import java.util.List;

public class Defective implements myObservable {

    //region fields
    private String defId;
    private String itemId;
    private String shopNum;
    private int quantity;
    private LocalDate updateDate;
    public final List<Observer> observers;
    private boolean expired = false;
    private boolean defective = false;
    //endregion

    //region constructors
    public Defective(List<Observer> observers, String defId, String itemId, int quantity, LocalDate updateDate, Boolean expired, Boolean defective, String shopNum) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.updateDate = updateDate;
        this.observers = observers;
        this.expired = expired;
        this.defective = defective;
        this.shopNum = shopNum;
    }

    public Defective(List<Observer> observers, DefectiveDTO DTO){
        this.defId = DTO.getDefId();
        this.itemId = DTO.getDefId();
        this.quantity = DTO.getQuantity();
        this.updateDate = DTO.getUpdateDate();
        this.expired = DTO.isExpired();
        this.expired = DTO.isDefective();
        this.shopNum = DTO.getShopNum();
        this.observers = observers;
    }

    //endregion

    //region defective functions
    public void defectiveItemStatus() {
        notifyObserver("|------------------------------\n" +
                "| itemId; " + itemId + "\n" +
                " update Date; " + updateDate + " quantity = " + quantity + " ==>");
                if(expired&&defective)
                    notifyObserver(" expired AND has a defect");
                else
                {
                    if(expired)
                        notifyObserver(" has expired");
                    if(defective)
                        notifyObserver(" has defect");
                }

                notifyObserver("|------------------------------");
    }
    public int getQuantity() {
        return quantity;
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
}
