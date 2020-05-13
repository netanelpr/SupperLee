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

    public DefectiveDTO(String defId, String itemId, String shopNum, int quantity, LocalDate updateDate, boolean expired, boolean defective) {
        this.defId = defId;
        this.itemId = itemId;
        this.shopNum = shopNum;
        this.quantity = quantity;
        this.updateDate = updateDate;
        this.expired = expired;
        this.defective = defective;
    }

    //region getters&setters

    public String getDefId() {
        return defId;
    }

    public String getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public boolean isDefective() {
        return defective;
    }

    public String getShopNum() {
        return shopNum;
    }


    //endregion

}
