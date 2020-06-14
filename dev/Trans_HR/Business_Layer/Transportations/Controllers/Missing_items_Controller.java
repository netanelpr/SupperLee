package Trans_HR.Business_Layer.Transportations.Controllers;
import Trans_HR.Business_Layer.Service;
import Trans_HR.Business_Layer.Transportations.Modules.MissingItems;
import Trans_HR.Business_Layer.Transportations.Utils.Buisness_Exception;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Missing_items_Controller {

    private static class Singelton_Missing {
        private static Missing_items_Controller instance = new Missing_items_Controller();
    }
    private Missing_items_Controller() {
        // initialization code..
    }
    public static Missing_items_Controller getInstance() {
        return Singelton_Missing.instance;
    }

  //  private ConcurrentHashMap<Integer,MissingItems> HashMissingItems= new ConcurrentHashMap<>();


    public List<String> getMissingItemsStores() throws Buisness_Exception
    {
        Service service=Service.getInstance();
        service.upload_MissingItems();

        if(service.getMissing().size()==0){
            throw new Buisness_Exception("The are no missing items in any store");
        }
        else {
            List<Integer> id_stores_list = new LinkedList<>();
            List<String> output = new LinkedList<String>();
            for (MissingItems missingItems : service.getMissing().values()) {
                Integer storeId = missingItems.getStoreId();
                if (!id_stores_list.contains(storeId)) {
                    id_stores_list.add(storeId);
                    String store = service.getHashStoresMap().get(storeId).getName();
                    String area = service.getHashStoresMap().get(storeId).getArea().getAreaName();
                    String line = storeId + ". " + store + ", area: " + area + ".";
                    output.add(line);
                }
            }
            return output;
        }
    }

//    public void add_to_items_file(HashMap<String,Integer> itemsList,Integer store,Integer supplier){
//        Service service=Service.getInstance();
//        service.getItemsFile().add(new ItemsFile(itemsList,service.getHashStoresMap().get(store),service.getSuppliersMap().get(supplier)));
//    }
}
