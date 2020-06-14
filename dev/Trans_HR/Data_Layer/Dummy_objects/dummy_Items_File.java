package Trans_HR.Data_Layer.Dummy_objects;

import javafx.util.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class dummy_Items_File {

    private int Sn;
    private int supplier_id;
    private int store_id;
    private List<Pair<String, Integer>> Items;

    public dummy_Items_File(int Sn, int supplier_id,int store_id, List<Pair<String,Integer>> items){
        this.Sn=Sn;
        this.supplier_id=supplier_id;
        this.store_id=store_id;
        this.Items=items;
    }

    public dummy_Items_File(int SN, int store_id, int supplier_id){
        this.Sn = SN;
        this.store_id=store_id;
        this.supplier_id=supplier_id;
        this.Items = new LinkedList<>();
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getSn() {
        return Sn;
    }

    public void setSn(int sn) {
        Sn = sn;
    }

    public List<Pair<String, Integer>> getItems() {
        return Items;
    }

    public void setItems(List<Pair<String, Integer>> items) {
        Items = items;
    }
}
