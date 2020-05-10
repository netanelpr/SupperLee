package Inventory.Persistence.Mappers;
import Inventory.Persistence.DTO.ItemDTO;
import java.util.HashMap;


public class ItemsMapper extends AbstractMappers {

    //public HashMap<String, ItemDTO> items; //<id, ItemDTO>

    //region singelton Constructor
    private static ItemsMapper instance = null;
    private ItemsMapper() {
        super();
    }
    public static ItemsMapper getInstance(){
        if(instance == null)
            instance = new ItemsMapper();
        return instance;
    }
    //endregion

    public HashMap<String, ItemDTO> load() {
        return null;
    }

    @Override
    public void insert() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }


}
