package Presentation;

import Service.SupplierManagment;

public class PaymentOptions implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public PaymentOptions(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
        if(argv.length > 1){
            System.out.println("Invalid input");
            return;
        }

        if(argv.length == 0){
            System.out.println("Payment options are: " +
                    supplierManagment.getPaymentOptions());
        } else {
            int supId = -1;
            try {
                supId = Integer.parseInt(argv[0]);
            } catch (NumberFormatException e){
                System.out.println("getPaymentOptions <supplier ID>");
            }

            String paymentOptions = supplierManagment.getPaymentOptions(supId);
            if(paymentOptions == null){
                System.out.println("There isnt such a supplier with id "+ supId);
            } else {
                System.out.println("Payment options are: " +
                        paymentOptions);
            }
        }
    }
}
