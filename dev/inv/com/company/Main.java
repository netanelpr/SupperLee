package inv.com.company;

import inv.View.Service;

public class Main {

    public static void main(String[] args) {

        Service service = Service.getInstance();
        String mainLoopAns;

        while((mainLoopAns = service.mainLoop()) != "quit inv");

    }
}
