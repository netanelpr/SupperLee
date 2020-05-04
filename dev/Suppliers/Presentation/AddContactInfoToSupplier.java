package Suppliers.Presentation;

import Suppliers.Service.SupplierManagment;

public class AddContactInfoToSupplier implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public AddContactInfoToSupplier(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
        if(argv.length != 4){
            System.out.println("Invalid number of args");
            return;
        }

        int supId = -1;
        try {
            supId = Integer.parseInt(argv[0]);
        } catch (NumberFormatException e){
            System.out.println("Invalid supplier ID");
            return;
        }

        if(supplierManagment.addContactInfo(supId, argv[1], argv[2], argv[3])){
            System.out.println("The contact has been added");
        } else {
            System.out.println("The contact wasnt added");
        }
    }
}
