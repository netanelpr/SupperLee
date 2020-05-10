package ConncetModules;

import Inventory.View.InvService;

public class Main {

    public static void main(String[] args) {

//        InvService invService = InvService.getInstance();
//        String mainLoopAns;
//
//        while((mainLoopAns = invService.mainLoop()) != "quit Inventory");

        Inventory2SuppliersCtrl inventory2SuppliersCtrl = Inventory2SuppliersCtrl.getInstance();
        inventory2SuppliersCtrl.run();
    }
}
