package Presentation;

import java.io.IOException;

public class Main {

    public static void main(String[] args){
        MainMenu mainMenu = new MainMenu();

        try {
            mainMenu.startMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
