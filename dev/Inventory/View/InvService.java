package Inventory.View;
import ConncetModules.Inventory2SuppliersCtrl;
import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Logic.Inventory;
//imporinv.t DummyItem;
import Inventory.Logic.OrderItem;
import Inventory.Logic.ShortageOrder;
import Inventory.Persistence.DummyItem;
import Inventory.Persistence.DummySuppliers;
import Result.Result;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class InvService implements myObservable {
    //private MainInterface myController;
    private View view;
    private HashMap<String, Inventory> superLeeInvs; //inventory id, inventory
    private Inventory currInv;
    boolean terminateInv = false;
    boolean terminateSys = false;
    private Scanner myScanner;
    public final List<Observer> observers;
    public DummySuppliers tmpSuppliers;
    public Inventory2SuppliersCtrl myInv2Sup;

    //region singelton Constructor
    private static InvService instance = null;
    private InvService(){
        this.view = new View();
        this.superLeeInvs = new HashMap<>();
        observers = new ArrayList<>();
        this.myScanner = new Scanner(System.in);
        this.register(view);
        tmpSuppliers = new DummySuppliers();
    }

    public static InvService getInstance(){
        if(instance == null)
            instance = new InvService();
        return instance;
    }
    //endregion

    public String mainLoop() {
        myInv2Sup = Inventory2SuppliersCtrl.getInstance();
        String ansStr;

        while(!terminateSys) {
            terminateInv = false;
            //region new_register loop
            notifyObserver("Welcome to inventory module, type: (new | register)");
            ansStr = myScanner.nextLine();
            if (ansStr.equals("new")) {
                this.currInv = newShop();
            }
            else if (ansStr.equals("register")) {
                notifyObserver("which shop id?");
                ansStr = myScanner.nextLine();
                if (!superLeeInvs.containsKey(ansStr)) {
                    notifyObserver("does not exist...");
                    terminateInv = true;
                }
                else {
                    this.currInv = superLeeInvs.get(ansStr);
                    notifyObserver("Welcome! keep 2 meters....");

                }
            }
            else {
                notifyObserver("wrong typing!!!");
                return "problem";
            }
            //endregion
            while (!terminateInv) {
                notifyObserver("what are you looking for?");
                ansStr = myScanner.nextLine();

                //region items
                if (ansStr.equals("uis")) {
                    //TODO: DELETE
                    getOrderFromSuppliers();
                    notifyObserver("-- Update Inventory Suppliers --");
                    HashMap<DummyItem, Integer> supply = tmpSuppliers.getArrivedOrders(); //read from jason, only for stage one
                    tmpSuppliers.finishOrder();
                    currInv.updateInventorySuppliers(supply, this);
                } else if (ansStr.equals("uiw")) {
                    notifyObserver("-- Update Inventory Workers --");
                    updInvWorker();
                } else if (ansStr.equals("gr")) {
                    notifyObserver("-- Get General Item Report --");
                    currInv.getItemReport();
                } else if (ansStr.equals("gri")) {
                    notifyObserver("-- Get Item Report By Id--");
                    notifyObserver("enter id:");
                    currInv.getItemReportById(myScanner.nextLine());
                } else if (ansStr.equals("grc")) {
                    notifyObserver("-- Get Item Report By Category--");
                    notifyObserver("enter category:");
                    currInv.getItemReportByCategory(myScanner.nextLine());
                } else if (ansStr.equals("grm")) {
                    notifyObserver("-- Get Missing Item Report--");
                    currInv.getItemMissing();
                }
                //endregion
                //region records
                else if (ansStr.equals("spi")) {
                    notifyObserver("-- Set New Price For Item --");
                    setNewPrice();
                } else if (ansStr.equals("gcp")) {
                    notifyObserver("-- Get Cost & Price Item Report --");
                    currInv.getGeneralRecordsReport();
                } else if (ansStr.equals("gcpi")) {
                    notifyObserver("-- Get Cost & Price Item Report By Id --");
                    notifyObserver("which id to get price & cost report?");
                    String id = myScanner.nextLine();
                    currInv.getRecordsReportById(id);
                }
                //endregion
                //region defectives
                else if (ansStr.equals("dfw")) {
                    notifyObserver("-- Update Defective Items From Worker --");
                    updDef();
                } else if (ansStr.equals("gdri")) {
                    notifyObserver("-- Get Defective Report By Id --");
                    notifyObserver("which id to get defective report?");
                    String id = myScanner.nextLine();
                    currInv.getDefectivesReportById(id);
                } else if (ansStr.equals("gdr")) {
                    notifyObserver("-- Get General Defective Report --");
                    currInv.getDefectivesReport();
                }
                //endregion
                //region quit_exit
                else if (ansStr.equals("exit")) {
                    notifyObserver("-- exiting current inventory... --");
                    terminateInv = true;
                } else if (ansStr.equals("quit")) {
                    notifyObserver("-- Bye Bye thank you for buying yellow --");
                    terminateInv = true;
                    terminateSys = true;
                } else {
                    notifyObserver("-- wrong order --");
                }
                //endregion
            }
        }
        return "quit Inventory";
    }

    private void updDef() {

        notifyObserver("enter defect or expired items quantities: ('id' 'quantity' 'expired?_(y/n)' 'defective?'_(y/n)') || '0' to stop");
        String currItem = myScanner.nextLine();
        String[] splited;

        while(!currItem.equals("0"))
        {
            splited = currItem.split(" ");
            currInv.updateDefectives(splited);
            currItem = myScanner.nextLine();
        }
        notifyObserver("-- finish updating defectives --");
    }



    //region FUNCTIONS
    public Inventory newShop() {
        Inventory newInv = new Inventory(view);
        superLeeInvs.put(newInv.getShopNum(), newInv);
        notifyObserver("YAY! opened new Inv '" + newInv.getShopNum() + "' ,whats next?");
        return newInv;
    }
    private void updInvWorker() {
        String id;
        int quanMissStock;
        int quanMissShop;
        OrderItem currOrderItem;
        ShortageOrder shortageOrder = new ShortageOrder(Integer.parseInt(currInv.getShopNum()));


        notifyObserver("enter updated quantities: ('id' 'quanMissStock' 'quanMissShop') || '0' to stop");

        String currItem = myScanner.nextLine();
        String[] splited;

        while(!currItem.equals("0"))
        {
            splited = currItem.split(" ");
            if(splited.length == 3) {
                id = splited[0];
                quanMissStock = Integer.parseInt(splited[1]);
                quanMissShop = Integer.parseInt(splited[2]);
                currOrderItem = currInv.updateInventoryWorkers(id, quanMissStock, quanMissShop);
                if (currOrderItem != null)
                    shortageOrder.addItemToOrder(currOrderItem);
            }
            else
                notifyObserver("wrong input! type again:");
            currItem = myScanner.nextLine();
        }
        notifyObserver("-- finish updating inventory --");
        if(shortageOrder.getLength() > 0) {
            Result<Integer> res = myInv2Sup.placeNewShortageOrder(shortageOrder);
            if(res.isFailure())
                notifyObserver(res.getMessage());
        }
    }

    public void getOrderFromSuppliers(){
        //TODO: change arg to Order that you send to us
        //TODO: start function with the specific shop
        //notifyObserver("-- Update Inventory Suppliers --");
        //currInv.updateInventorySuppliers(supply, this);
    }

    private void setNewPrice() {
        notifyObserver("which id to set deal?");
        String id = myScanner.nextLine();
        String[] lastRecInfo = currInv.getLastRec(id);
        String nameLast = lastRecInfo[0];
        String priceLast = lastRecInfo[1];
        notifyObserver(id + ". " + nameLast + " -> current price: " + priceLast + "\n" +
                "type new price: ");
        String newPrice = myScanner.nextLine();
        currInv.setNewPrice(id, newPrice, nameLast, priceLast);
    }
    public double askUserPrice(double newCost, double oldCost, String[] lastRecordInfo) {
        String id = lastRecordInfo[0]; String name = lastRecordInfo[1];
        String oldPrice = lastRecordInfo[2];
//        LocalDate oldPriceChangeDate = changeToDate(lastRecordInfo[3]);

        notifyObserver("The supplier has changed the cost of " + name + "\n" +
                "old cost: " + oldCost + "$ -- new cost: " + newCost + "$ -- old price: " + oldPrice + "$\n" +
                "would you like to change price? (y 'new_price' | n)");
        String ans = myScanner.nextLine();
        if(!ans.equals("n")) {
            ans = ans.substring(2);//ans is the new price
            double newPrice = Double.parseDouble(ans);
            //newRecord = new Record(observers, id, name, newCost, LocalDate.now(), newPrice, LocalDate.now());
            notifyObserver("|--------------------------------------------------\n" +
                    "|price changed! new price for " + id + ". " + name + ": " + newPrice + "$\n"+
                    "|--------------------------------------------------");
            return newPrice;
        }
        else {
            //newRecord = new Record(observers, id, name, newCost, LocalDate.now(), Double.parseDouble(oldPrice), oldPriceChangeDate);
            notifyObserver("|--------------------------------------------------\n" +
                    "|price didnt changed " + "\n" +
                    "|--------------------------------------------------\n");
            return Double.parseDouble(oldPrice);
        }
    }

    private LocalDate changeToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }
    //endregion

    //region observer
    @Override
    public void register(Observer o) {
        observers.add(o);
    }
    @Override
    public void notifyObserver(String msg) {
        observers.forEach(o -> o.onEvent(msg));
    }
    //endregion
}
