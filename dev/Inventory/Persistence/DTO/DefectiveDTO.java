package Inventory.Persistence.DTO;


import java.time.LocalDate;

public class DefectiveDTO {

    private String defId;
    private String itemId;
    private int quantity;
    private LocalDate updateDate;
    private boolean expired;
    private boolean defective;
    private String shopNum;

    public DefectiveDTO(String defId, String itemId, int quantity, LocalDate updateDate, boolean expired, boolean defective) {
        this.defId = defId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.updateDate = updateDate;
        this.expired = expired;
        this.defective = defective;
    }

    //region getters&setters

    public String getDefId() {
        return defId;
    }

    public void setDefId(String defId) {
        this.defId = defId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isDefective() {
        return defective;
    }

    public void setDefective(boolean defective) {
        this.defective = defective;
    }

    public String getShopNum() {
        return shopNum;
    }

    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }

    //endregion

}
