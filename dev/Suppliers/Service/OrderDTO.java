package Suppliers.Service;

import java.util.List;

public class OrderDTO {

    public List<ProductInOrderDTO> productInOrderDTOList;
    public int shopID;

    public OrderDTO(int shopID, List<ProductInOrderDTO> productInOrderDTOS){
        this.shopID = shopID;
        this.productInOrderDTOList = productInOrderDTOS;
    }

}
