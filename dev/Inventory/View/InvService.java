package Inventory.View;
import Presentation.Inventory2SuppliersCtrl;
import Inventory.Interfaces.Observer;
import Inventory.Interfaces.myObservable;
import Inventory.Logic.Inventory;
//imporinv.t DummyItem;
import Inventory.Logic.OrderItem;
import Inventory.Logic.ShortageOrder;
import Inventory.Persistence.DTO.InventoryDTO;
import Inventory.Persistence.DTO.ItemDTO;
import Inventory.Persistence.Mappers.InventoriesMapper;
import Result.Result;
import Suppliers.Service.OrderDTO;

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
    private boolean terminateInv = false;
    private boolean terminateSys = false;
    private boolean terminate = false;
    private Scanner myScanner;
    private final List<Observer> observers;
    private Inventory2SuppliersCtrl myInv2Sup;
    private InventoriesMapper inventoriesMapper;
    private String ansStr;

    //region singelton Constructor
    private static InvService instance = null;
    private InvService(){
        this.view = new View();
        this.superLeeInvs = new HashMap<>();
        observers = new ArrayList<>();
        this.myScanner = new Scanner(System.in);
        this.register(view);
        inventoriesMapper = InventoriesMapper.getInstance();
    }

    public static InvService getInstance(){
        if(instance == null)
            instance = new InvService();
        return instance;
    }
    //endregion

    public Boolean mainLoop() {
        this.myInv2Sup = Inventory2SuppliersCtrl.getInstance();

        while(!terminateSys) {
            terminateInv = false;
            //region new_register loop
            notifyObserver("--------------\nWelcome to your Super-Lee inventory!\n--------------\n" +
                    "Please choose one of the following options:\n" +
                    "\t(n) New shop \n \t(r) Register your shop");
            ansStr = myScanner.nextLine();
            if (ansStr.equals("n") || ansStr.equals("N")) {
                this.currInv = newShop();
                inventoriesMapper.insert(new InventoryDTO(currInv.getShopNum(), currInv.getShopName()));
            }
            else if (ansStr.equals("r") || ansStr.equals("R")) {
                String shops = "Super lee shops:\n";
                for (String shop : superLeeInvs.keySet())
                     shops += String.format("\t%s. %s\t", shop, superLeeInvs.get(shop).getShopName());
                shops += "\n";
                notifyObserver(shops + "Type your shop number:");
                ansStr = myScanner.nextLine();
                if (!superLeeInvs.containsKey(ansStr)) {
                    notifyObserver("I know that you can't wait to be part of Super-Lee, but please remember your shop id...");
                    terminateInv = true;
                }
                else {
                    this.currInv = superLeeInvs.get(ansStr);
                    notifyObserver(String.format("Welcome to shop # %s! : %s", ansStr, currInv.getShopName()));
                }
            }
            else {
                notifyObserver("wrong typing!");
                terminateInv = true;
            }
            //endregion
            while (!terminateInv) {
                notifyObserver("\n__'" + currInv.getShopName() + "' inventory__\n" +
                        "Please choose one of the following options:\n-------\n" +
                        "\t(i) Items: update and reports quantities\n" +
                        "\t(r) Records: update and reports\n" +
                        "\t(d) Defectives and Expired Items: update and reports\n" +
                        "\t(b) Back to suppliers-inventory menu\n" +
                        "\t(c) Close\n");
                ansStr = myScanner.nextLine();
                if(ansStr.equals("i") || ansStr.equals("I")) {
                    terminate = itemsFunctions();
                    if(terminate) return terminate;
                }
                else if(ansStr.equals("r") || ansStr.equals("R")){
                    terminate = recordsFunctions();
                    if(terminate) return terminate;
                }
                else if(ansStr.equals("d") || ansStr.equals("D")) {
                    terminate = defectivesFunctions();
                    if(terminate) return terminate;
                }
                else if(ansStr.equals("b") || ansStr.equals("B")) {
                    terminateInv = true;
                    terminateSys = true;
                }
                else if(ansStr.equals("c") || ansStr.equals("C")) {
                    terminateInv = true;
                    terminateSys = true;
                    terminate = true;
                }
                else
                    notifyObserver("-- wrong order --");
            }
        }
        terminateSys = false;
        terminateInv = false;
        return terminate;
    }


    //region items
    private boolean itemsFunctions() {
        while(!terminateSys) {
            notifyObserver(
                    "\n__Items__\nPlease choose one of the following options:\n" +
                            "\t(p) Print all open orders for your shop\n" +
                            "\t(r) Receive arrived order to inventory \n" +
                            "\t(u) Update quantities in your inventory after stocktaking \n" +
                            "\t(gr) Get All Items Report \n" +
                            "\t(gi) Get Item Report by id \n" +
                            "\t(gc) Get Item Report By Category \n" +
                            "\t(gs) Get Shortage Item Report  \n" +
                            "\t(b) Back to inventory menu \n" +
                            "\t(c) Close \n");

            ansStr = myScanner.nextLine();
            if (ansStr.equals("p") || ansStr.equals("P")) {
                int shop = Integer.parseInt(currInv.getShopName());
                String orders = "";
                notifyObserver("Open orders shop # " + shop + ":");
                List<Integer> openOrders = myInv2Sup.receiveAllOpenOrders(shop);
                for (Integer o : openOrders)
                    orders += (o + ", ");
                orders.substring(0, orders.length()-2);
                notifyObserver(orders);
            }
            else if (ansStr.equals("r") || ansStr.equals("R")) {
                notifyObserver("Type order id:");
                ansStr = myScanner.nextLine();
                myInv2Sup.receiveSupplierOrder(Integer.parseInt(ansStr));
                notifyObserver("order received successfully and arranged in inventory!");
            }
            else if (ansStr.equals("u") || ansStr.equals("U")) {
                updInvWorker();
            }
            else if (ansStr.equals("gr") || ansStr.equals("GR")) {
                notifyObserver("--- All items report ---");
                currInv.getItemReport();
            }
            else if (ansStr.equals("gi") || ansStr.equals("GI")) {
                notifyObserver("enter id:");
                ansStr = myScanner.nextLine();
                notifyObserver(String.format("-- Item Report By Id : #%s--", ansStr));
                currInv.getItemReportById(ansStr);
            }
            else if (ansStr.equals("gc") || ansStr.equals("GC")) {
                notifyObserver("enter category:");
                ansStr = myScanner.nextLine();
                notifyObserver(String.format("--- Items Report By Category %s ---", ansStr));
                currInv.getItemReportByCategory(ansStr);
            }
            else if (ansStr.equals("gs") || ansStr.equals("GS")) {
                notifyObserver("--- Shortage Item Report ---");
                currInv.getItemMissing();
            }
            else if (ansStr.equals("b") || ansStr.equals("B")) {terminateSys=true;}
            else if (ansStr.equals("c") || ansStr.equals("C")) {terminateSys=true; terminate = true;}
            else {notifyObserver("wrong order");}
        }
        if(!terminate)
            terminateSys = false;
        return terminate;
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
                if(currOrderItem != null && currOrderItem.getId() == -1)
                    notifyObserver("wrong id - item isn't exist, type again");
                else if (currOrderItem != null)
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
    public void getOrderFromSuppliers(OrderDTO order){
        //notifyObserver("-- Update Inventory Suppliers --");
        //HashMap<ItemDTO, Integer> supply = new HashMap<>();
        currInv.updateInventorySuppliers(order, this);
    }
    //endregion
    //region records
    private boolean recordsFunctions() {
        while(!terminateSys) {
            notifyObserver(
                    "__Records__\nPlease choose one of the following options:\n" +
                            "\t(s) Set New Price For Item \n" +
                            "\t(gr) Get Cost & Price All Items Report \n" +
                            "\t(gi) Get Cost & Price Item Report By Id \n" +
                            "\t(b) Back to inventory menu \n" +
                            "\t(c) Close \n");
            ansStr = myScanner.nextLine();
                if (ansStr.equals("s") || ansStr.equals("S")) {
                setNewPrice();
            }
                else if (ansStr.equals("gr") || ansStr.equals("GR")) {
                notifyObserver("--- Cost & Price Item Report ---");
                currInv.getGeneralRecordsReport();
            }
                else if (ansStr.equals("gi") || ansStr.equals("GI")) {
                notifyObserver("enter id:");
                ansStr = myScanner.nextLine();
                notifyObserver(String.format("--- Cost & Price Item Report By Id : %s --", ansStr));
                currInv.getRecordsReportById(ansStr);
            }
                else if (ansStr.equals("b") || ansStr.equals("B")) {terminateSys=true;}
                else if (ansStr.equals("c") || ansStr.equals("C")) {terminateSys=true; terminate = true;}
                else {notifyObserver("wrong order");}
        }
        if(!terminate)
            terminateSys = false;
        return terminate;
    }
    private void setNewPrice() {
        notifyObserver("enter id: ");
        String id = myScanner.nextLine();
        String[] lastRecInfo = currInv.getLastRec(id);
        String nameLast = lastRecInfo[0];
        String priceLast = lastRecInfo[1];
        notifyObserver("item #" + id + " -> current price: " + priceLast + "\n" +
                "type new price: ");
        String newPrice = myScanner.nextLine();
        currInv.setNewPrice(id, newPrice, nameLast, priceLast);
    }
    //endregion
    //region defectives
    private boolean defectivesFunctions() {
        while(!terminateSys) {
            notifyObserver(
                    "__Defectives-Expired__\nPlease choose one of the following options:n" +
                            "\t(u) Update defective/expired Items in your inventory \n" +
                            "\t(gr) Get All Defective and Expired Report\n" +
                            "\t(gi) Get Defective and Expired Report By Id\n" +
                            "\t(b) Back to inventory menu \n" +
                            "\t(c) Close \n");
            ansStr = myScanner.nextLine();
            if (ansStr.equals("u") || ansStr.equals("U")) {
                updDef();
            }
            else if (ansStr.equals("gr") || ansStr.equals("GR")) {
                notifyObserver("--- General Defective Report ---");
                currInv.getDefectivesReport();
            }
            else if (ansStr.equals("gi") || ansStr.equals("GI")) {
                notifyObserver("enter id:");
                String id = myScanner.nextLine();
                notifyObserver("-- Defective/Expired Report By Id : "+ id + "--");
                currInv.getDefectivesReportById(id);
            }
            else if (ansStr.equals("b") || ansStr.equals("B")) {terminateSys=true;}
            else if (ansStr.equals("c") || ansStr.equals("C")) {terminateSys=true; terminate = true;}
            else {notifyObserver("wrong order");}
        }
        if(!terminate)
            terminateSys = false;
        return terminate;
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
    //endregion

    //region FUNCTIONS
    public Inventory newShop() {
        notifyObserver("Choose name for your shop:");
        String name = myScanner.nextLine();
        Inventory newInv = new Inventory(view, name);
        superLeeInvs.put(newInv.getShopNum(), newInv);
        notifyObserver(String.format("YAY! opened new Inv # %s : %s \n--------", newInv.getShopNum(), name));
        return newInv;
    }
    public double askUserPrice(double newCost, double oldCost, String[] lastRecordInfo) {
        String id = lastRecordInfo[0]; String name = lastRecordInfo[1];
        String oldPrice = lastRecordInfo[2];
//        LocalDate oldPriceChangeDate = changeToDate(lastRecordInfo[3]);

        notifyObserver("The supplier has changed the cost of item # " + name + "\n" +
                "old cost: " + oldCost + "$ -- new cost: " + newCost + "$ -- old price: " + oldPrice + "$\n" +
                "would you like to change price?\n type: <y 'new_price' | n>");
        String ans = myScanner.nextLine();
        if(!ans.equals("n")) {
            ans = ans.substring(2);//ans is the new price
            double newPrice = Double.parseDouble(ans);
            //newRecord = new Record(observers, id, name, newCost, LocalDate.now(), newPrice, LocalDate.now());
            notifyObserver("price changed! new price for item # " + id + ": " + newPrice + "$\n");
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
