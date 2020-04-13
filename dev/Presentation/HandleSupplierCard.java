package Presentation;

import Service.SupplierManagment;

public class HandleSupplierCard implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public HandleSupplierCard(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
        /*
         * <name> <inc number> <bank account number> <payment info>
         */
        if(argv.length != 4){
            System.out.println("Invalid input");
            return;
        }

        int supId = supplierManagment.createSupplierCard(argv[0], argv[1], argv[2], argv[3]);
        if(supId < 0){
            System.out.println("Supplier card wasnt created");
        } else {
            System.out.println("New supplier ID: " + supId);
        }
    }
}
