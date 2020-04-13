package Presentation;

import Service.ProductInOrderDTO;
import Service.SupplierManagment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class InitWithData implements  Menu_Option {


    private SupplierManagment supplierManagment;

    public InitWithData(SupplierManagment supplierManagment) {
        this.supplierManagment = supplierManagment;
    }


    @Override
    public void apply(String[] argv) {
        if(argv.length != 0){
            System.out.println("Invalid number of args");
            return;
        }
    }
}
