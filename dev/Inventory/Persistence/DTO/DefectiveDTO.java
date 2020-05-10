package Inventory.Persistence.DTO;


import java.time.LocalDate;

public class DefectiveDTO {

    private int defId;
    private int itemId;
    private int quantity;
    private LocalDate updateDate;
    private boolean expired;
    private boolean defective;

    public DefectiveDTO(int defId, int itemId, int quantity, LocalDate updateDate, boolean expired, boolean defective) {
        this.defId = defId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.updateDate = updateDate;
        this.expired = expired;
        this.defective = defective;
    }

    //region getters&setters
    public int getDefId() {
        return defId;
    }

    public void setDefId(int defId) {
        this.defId = defId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
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
    //endregion

}
