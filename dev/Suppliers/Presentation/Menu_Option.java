package Suppliers.Presentation;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class Menu_Option {

    /**
     * Start the sub menu
     */
    abstract void apply();

    public String readString(String info, BufferedReader bufferedReader){
        System.out.print(info + ": ");
        try{
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.out.print("Error at reading");
        }

        return null;
    }

    public int readInt(String info, BufferedReader bufferedReader){
        System.out.print(info + ": ");
        try{
            String input = bufferedReader.readLine();
            return Integer.parseInt(input);
        } catch (IOException e) {
            System.out.println("Error at reading");
        } catch (NumberFormatException e){
            System.out.print("Need to be a number");
        }

        return -1;
    }
}
