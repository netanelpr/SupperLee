package Inventory.View;
import ConncetModules.Inventory2SuppliersCtrl;
import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Logic.Inventory;
//imporinv.t DummyItem;
import Inventory.Logic.OrderItem;
import Inventory.Logic.ShortageOrder;
import Inventory.Persistence.DTO.InventoryDTO;
import Inventory.Persistence.DTO.ItemDTO;
import Inventory.Persistence.DummyItem;
import Inventory.Persistence.DummySuppliers;
import Inventory.Persistence.Mappers.InventoriesMapper;
import Result.Result;

import java.sql.SQLException;
import java.text.Format;
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
    private final List<Observer> observers;
    private DummySuppliers tmpSuppliers; //TODO delete at the end!!
    private Inventory2SuppliersCtrl myInv2Sup;
    private InventoriesMapper inventoriesMapper;

    //region singelton Constructor
    private static InvService instance = null;
    private InvService(){
        this.view = new View();
        this.superLeeInvs = new HashMap<>();
        observers = new ArrayList<>();
        this.myScanner = new Scanner(System.in);
        this.register(view);
        tmpSuppliers = new DummySuppliers();
        inventoriesMapper = InventoriesMapper.getInstance();
    }

    public static InvService getInstance(){
        if(instance == null)
            instance = new InvService();
        return instance;
    }
    //endregion

    public void mainLoop() {
        myInv2Sup = Inventory2SuppliersCtrl.getInstance();
        String ansStr;

        while(!terminateSys) {
            terminateInv = false;
            //region new_register loop
            notifyObserver("Welcome to your Super-Lee inventory! \n 1) New shop \n 2) Register your shop");
            ansStr = myScanner.nextLine();
            if (ansStr.equals("1")) {
                this.currInv = newShop();
            }
            else if (ansStr.equals("2")) {
                notifyObserver("Type your shop number:");
                ansStr = myScanner.nextLine();
                if (!superLeeInvs.containsKey(ansStr)) {
                    notifyObserver("I know that you can't wait to be part of Super-Lee, but please remember your shop id...");
                    terminateInv = true;
                }
                else {
                    this.currInv = superLeeInvs.get(ansStr);
                    notifyObserver(String.format("Welcome to shop #%s! keep 2 meters....", ansStr));
                }
            }
            else {
                notifyObserver("wrong typing!");
                terminateInv = true;
            }
            //endregion
            while (!terminateInv) {
                notifyObserver("Choose option:\n-------\n" +
                                    "Items: update and reports quantities\n" +
                                    "\t1) Receive arrived order to inventory \n" +
                                    "\t2) Update quantities in your inventory after stocktaking \n" +
                                    "\t3) Get All Items Report \n" +
                                    "\t4) Get Item Report by id \n" +
                                    "\t5) Get Item Report By Category \n"+
                                    "\t6) Get Shortage Item Report  \n" +
                                    "\t7) Get Item Report By Category \n" +
                                    "\t8) Get Item Report By Category \n" +
                                    "\t9) Get Item Report By Category \n" +
                                    "Records: update and reports\n" +
                                    "\t10) Set New Price For Item \n" +
                                    "\t11) Get Cost & Price All Items Report \n" +
                                    "\t12) Get Cost & Price Item Report By Id \n" +
                                    "Defectives and Expired Items: update and reports\n" +
                                    "\t13) Update defective/expired Items in your inventory\n" +
                                    "\t14) Get All Defective and Expired Report \n" +
                                    "\t15) Get Defective and Expired Report By Id \n" +
                                    "For Exiting...\n" +
                                    "\t^) Type 'exit' for backing the last menu\n" +
                                    "\t^) Type 'quit' to close super lee system\n");
                ansStr = myScanner.nextLine();

                //region items
                //-- Receive arrived order to inventory --
                if (ansStr.equals("1")) {
                    notifyObserver("Type order id:");
                    ansStr = myScanner.nextLine();
                    //Result res =
                    myInv2Sup.receiveSupplierOrder(Integer.parseInt(ansStr));
//                    if(res.isFailure()){
//                        notifyObserver("Cant receive this order from suppliers. error: " + res.getMessage());
//                    }
//                    else {
//                        notifyObserver("order received successfully and arranged in inventory!");
//                    }
                } else if (ansStr.equals("2")) {
                    updInvWorker();
                } else if (ansStr.equals("3")) {
                    notifyObserver("--- All items report ---");
                    currInv.getItemReport();
                } else if (ansStr.equals("4")) {
                    notifyObserver("enter id:");
                    ansStr = myScanner.nextLine();
                    notifyObserver(String.format("-- Item Report By Id : #%s--", ansStr));
                    currInv.getItemReportById(ansStr);
                } else if (ansStr.equals("5")) {
                    notifyObserver("enter category:");
                    ansStr = myScanner.nextLine();
                    notifyObserver(String.format("--- Items Report By Category %s ---", ansStr));
                    currInv.getItemReportByCategory(ansStr);
                } else if (ansStr.equals("6")) {
                    notifyObserver("--- Shortage Item Report ---");
                    currInv.getItemMissing();
                }
                //endregion
                //region records
                else if (ansStr.equals("10")) {
                    setNewPrice();
                } else if (ansStr.equals("11")) {
                    notifyObserver("--- Cost & Price Item Report ---");
                    currInv.getGeneralRecordsReport();
                } else if (ansStr.equals("12")) {
                    notifyObserver("enter id:");
                    ansStr = myScanner.nextLine();
                    notifyObserver(String.format("--- Cost & Price Item Report By Id : %s --", ansStr));
                    currInv.getRecordsReportById(ansStr);
                }
                //endregion
                //region defectives
                else if (ansStr.equals("13")) {
                    updDef();
                } else if (ansStr.equals("15")) {
                    notifyObserver("enter id:");
                    String id = myScanner.nextLine();
                    notifyObserver(String.format("-- Defective/Expired Report By Id : %s--"));
                    currInv.getDefectivesReportById(id);
                } else if (ansStr.equals("14")) {
                    notifyObserver("--- General Defective Report ---");
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
    }


    //region FUNCTIONS
    public Inventory newShop() {
        notifyObserver("choose name for your shop:");
        String name = myScanner.nextLine();
        Inventory newInv = new Inventory(view, name);
        superLeeInvs.put(newInv.getShopNum(), newInv);
        notifyObserver("YAY! opened new Inv number " + newInv.getShopNum() + "\n\n--------");
        return newInv;
    }
    private void updInvWorker() {
        String id;
        int quanMissStock;
        int quanMissShop;
        OrderItem currOrderItem;
        ShortageOrder shortageOrder = new ShortageOrder(Integer.parseInt(currInv.getShopNum()));
        notifyObserver("Type the updated quantities for each item you want, in the following format:\n " +
                            "<'id' 'Amount of missing quantity in stock' 'Amount of missing quantity in shop'>\n " +
                            "<0> when you finish");
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
                notifyObserver("You probably type the wrong format or id that isn't exist, type again in the format:\n" +
                                "<'id' 'Amount of missing quantity in stock' 'Amount of missing quantity in shop'>");
            currItem = myScanner.nextLine();
        }
        notifyObserver("finish updating inventory, sending shortage order to suppliers if needed.");
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
        ItemDTO justForCheck = new ItemDTO("1", "1", "100", "100",
                                            "milk", "tnuva", "diary", "drinks",
                                                "M", 3, 7.9);
        HashMap< ItemDTO, Integer > hashForCheck = new HashMap<>();
        hashForCheck.put(justForCheck, 100);
        currInv.updateInventorySuppliers(hashForCheck, this);
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
    private void updDef() {
        notifyObserver("Enter the defect or expired items quantities for each item you want, in the following format:\n " +
                "<'id' 'quantity' 'expired?(y/n)' 'defective?'(y/n)'> //(y/n) = type just y or n\n " +
                "<0> when you finish");
        String currItem = myScanner.nextLine();
        String[] splited;

        while(!currItem.equals("0"))
        {
            splited = currItem.split(" ");
            currInv.updateDefectives(splited);
            currItem = myScanner.nextLine();
        }
        notifyObserver("--- finish updating defectives and expired ---");
    }
    public double askUserPrice(double newCost, double oldCost, String[] lastRecordInfo) {
        String id = lastRecordInfo[0]; String name = lastRecordInfo[1];
        String oldPrice = lastRecordInfo[2];
//        LocalDate oldPriceChangeDate = changeToDate(lastRecordInfo[3]);

        notifyObserver("The supplier has changed the cost of " + name + "\n" +
                "old cost: " + oldCost + "$ -- new cost: " + newCost + "$ -- old price: " + oldPrice + "$\n" +
                "would you like to change price?\n type: <y 'new_price' | n>");
        String ans = myScanner.nextLine();
        if(!ans.equals("n")) {
            ans = ans.substring(2);//ans is the new price
            double newPrice = Double.parseDouble(ans);
            //newRecord = new Record(observers, id, name, newCost, LocalDate.now(), newPrice, LocalDate.now());
            notifyObserver("price changed! new price for " + id + ". " + name + ": " + newPrice + "$\n");
            return newPrice;
        }
        else {
            //newRecord = new Record(observers, id, name, newCost, LocalDate.now(), Double.parseDouble(oldPrice), oldPriceChangeDate);
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

    public void loadDB() {
        HashMap<String, InventoryDTO> invs = inventoriesMapper.load();
        for (String shopNum : invs.keySet()) {
            superLeeInvs.put(shopNum, new Inventory(view, invs.get(shopNum)));
        }
        for (String inv : superLeeInvs.keySet()) {
            superLeeInvs.get(inv).loadInvDB();
        }

    }
    //endregion
}
