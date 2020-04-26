package inv.Logic;

import inv.Interfaces.Observer;
import inv.Interfaces.myObservable;

import java.time.LocalDate;
import java.util.List;

public class Defective implements myObservable {

    //region fields
    private String id;
    private String name;
    private int quantity;
    private LocalDate updateDate;
    public final List<Observer> observers;
    private boolean expired = false;
    private boolean defective = false;
    //endregion

    //region constructors
    public Defective(List<Observer> observers, String id, String name, int quantity, LocalDate updateDate, Boolean expired, Boolean defective) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.updateDate = updateDate;
        this.observers = observers;
        this.expired = expired;
        this.defective = defective;
    }
    public Defective(List<Observer> observers, String id, String name, int quantity, LocalDate updateDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.updateDate = updateDate;
        this.observers = observers;
    }
    //endregion

    //region defective functions
    public void defectiveItemStatus() {
        notifyObserver("|------------------------------\n" +
                "| id; " + id + " name; " + name + "\n" +
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
    public String getName() {
        return name;
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
