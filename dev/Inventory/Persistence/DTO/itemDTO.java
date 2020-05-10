package Inventory.Persistence.DTO;

public class itemDTO {

    private int shopNum;
    private int id;
    private int quantityShop;
    private int quantityStorage;


    public itemDTO(int shopNum, int id, int quantityShop, int quantityStorage) {
        this.shopNum = shopNum;
        this.id = id;
        this.quantityShop = quantityShop;
        this.quantityStorage = quantityStorage;
    }

    //region getters&setters
    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantityShop() {
        return quantityShop;
    }

    public void setQuantityShop(int quantityShop) {
        this.quantityShop = quantityShop;
    }

    public int getQuantityStorage() {
        return quantityStorage;
    }

    public void setQuantityStorage(int quantityStorage) {
        this.quantityStorage = quantityStorage;
    }
    //endregion

}
