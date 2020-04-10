package Presentation;

import Service.ProductDiscountsDTO;
import Service.SupplierManagment;

import java.util.List;

public class GetAmountDiscountReport implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public GetAmountDiscountReport(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
        if(argv.length != 1){
            System.out.println("Invalid number of args args");
            return;
        }

        int supId = -1;
        try {
            supId = Integer.parseInt(argv[0]);
        } catch (NumberFormatException e){
            System.out.println("Invalid args");
            return;
        }

        List<ProductDiscountsDTO> discountDTOList = supplierManagment.getAmountDiscountReport(supId);
        if(discountDTOList == null){
            System.out.println("No such supplier ID");
            return;
        }

        for (ProductDiscountsDTO discountDTO : discountDTOList){
            System.out.println(discountDTO.toString());
        }


    }
}
