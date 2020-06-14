package Trans_HR.Business_Layer.Workers.Controllers;


import Trans_HR.Business_Layer.Service;
import Trans_HR.Business_Layer.Workers.Modules.Shift;
import Trans_HR.Business_Layer.Workers.Modules.Worker.Worker;
import Trans_HR.Business_Layer.Workers.Utils.InfoObject;
import Trans_HR.Business_Layer.Workers.Utils.enums;
import Trans_HR.Data_Layer.Mapper;

import java.text.SimpleDateFormat;
import java.util.*;


public class ShiftController {

    private int snFactory;
    private int currentStoreSN;

    public ShiftController() {
        this.snFactory = 0;
        this.currentStoreSN = -1;
    }

    public InfoObject printAllShits() {
        InfoObject infoObject = new InfoObject("", true);
        if (Service.getInstance().getShiftHistory(getCurrentStoreSN()).isEmpty()) {
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("There are no shifts to display");
            return infoObject;
        }
        System.out.println("Select shift by SN");
        List<Shift> shiftToDisplay = new LinkedList<>(Service.getInstance().getShiftHistory(getCurrentStoreSN()).values());
        Collections.sort(shiftToDisplay);
        for (Shift shift : shiftToDisplay) {
            SimpleDateFormat day = new SimpleDateFormat("dd-MM-yyyy");
            String date = day.format(shift.getDate());
            System.out.println(shift.getShiftSn() + ". Date: " + date + " Type: " + shift.getShiftType());
        }
        return infoObject;
    }

    private int getSnFactory() {
        return ++this.snFactory;
    }

    public void getShifts(){
        Mapper.getInstance().getAllShifts(this.currentStoreSN);
    }

    public void getShifts(int storeSn){
        Mapper.getInstance().getAllShifts(storeSn);
    }

    public void resetSnFactory(){
        this.snFactory = 0;
    }

    public InfoObject validateNewShiftDate(Date date, enums shiftType) {
        InfoObject infoObject = new InfoObject("", true);
        for (Shift shifty : Service.getInstance().getShiftHistory(getCurrentStoreSN()).values()) {
            if ((shifty.getDate().compareTo(date) == 0) && shifty.getShiftType().equals(shiftType)) {
                infoObject.setIsSucceeded(false);
                infoObject.setMessage("There is already a shift on this date");
                return infoObject;
            }
        }
        Date toDay = new Date();
        if (date.compareTo(toDay) < 0) {
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("Date already passed");
            return infoObject;
        }
        return infoObject;
    }

    public InfoObject createShift(String shiftType, int managerSn, String listOfWorkersSn, String dateToParse) {
        InfoObject infoObject = new InfoObject("", true);
        enums sType;
        try {
            sType = enums.valueOf(shiftType);
        } catch (Exception e) {
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("Invalid shift type");
            return infoObject;
        }

        Date date = parseDate(dateToParse);
        if (date == null) {
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("Invalid date format");
            return infoObject;
        }
        infoObject = validateNewShiftDate(date, sType);
        if (!infoObject.isSucceeded()) {
            return infoObject;
        }

        if (!(Service.getInstance().getWorkerList(getCurrentStoreSN()).containsKey(managerSn))) {
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("Invalid manager SN");
            return infoObject;
        }
        if (!(Service.getInstance().getWorkerList(getCurrentStoreSN()).get(managerSn).getWorkerJobTitle().toUpperCase().equals("MANAGER"))) {
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("Invalid manager SN");
            return infoObject;
        }
        Worker manager = Service.getInstance().getWorkerList(getCurrentStoreSN()).get(managerSn);
        String[] workersSn;
        List<Worker> workersListOfCurrentShift = new LinkedList<>();
     //hadar   List<Integer> workersDB =new LinkedList<>();
        infoObject = validateManagerConstrains(manager, sType, date);
        if (!infoObject.isSucceeded()) {
            return infoObject;
        }
        workersListOfCurrentShift.add(manager);
        if (!listOfWorkersSn.toUpperCase().equals("NONE")) {
            try {
                workersSn = listOfWorkersSn.split(",");
            } catch (Exception e) {
                workersSn = null;
            }
            if (workersSn == null) {
                infoObject.setIsSucceeded(false);
                infoObject.setMessage("Invalid workers SN format input");
                return infoObject;
            }
            if (!workersSn[0].equals("")) {
                // validate workers SN
                for (String workerSn : workersSn) {
                    if (!(Service.getInstance().getWorkerList(getCurrentStoreSN()).containsKey(Integer.parseInt(workerSn)))) {
                        infoObject.setIsSucceeded(false);
                        infoObject.setMessage("There is no worker with serial number " + Integer.parseInt(workerSn));
                        return infoObject;
                    }
                }

                // validate workers starting date and shift date
                for (String workerSn : workersSn) {
                    Date workersDate = Service.getInstance().getWorkerList(getCurrentStoreSN()).get(Integer.parseInt(workerSn)).getWorkerStartingDate();
                    if (date.compareTo(workersDate) < 0) {
                        infoObject.setIsSucceeded(false);
                        infoObject.setMessage("This worker : " + Service.getInstance().getWorkerList(getCurrentStoreSN()).get(Integer.parseInt(workerSn)).getWorkerName() + " Start working only from " +
                                Service.getInstance().getWorkerList(getCurrentStoreSN()).get(Integer.parseInt(workerSn)).getWorkerStartingDate());
                        return infoObject;
                    }
                }

                // validate workers constrains  with shift date
                for (String workerSn : workersSn) {
                    Worker workerToAdd = Service.getInstance().getWorkerList(getCurrentStoreSN()).get(Integer.parseInt(workerSn));
                    if (!(workerToAdd.available(date, sType))) {
                        infoObject.setIsSucceeded(false);
                        infoObject.setMessage(workerToAdd.getWorkerName() + " Can't work on this shift because of his constrains");
                        return infoObject;
                    }
                    if (workersListOfCurrentShift.contains(workerToAdd)) {
                        infoObject.setIsSucceeded(false);
                        infoObject.setMessage(workerToAdd.getWorkerName() + " Already in this shift. Cant add the same worker twice");
                        return infoObject;
                    }
                    //workersDB.add(workerToAdd.getWorkerSn());
                    workersListOfCurrentShift.add(workerToAdd);
                }
            }
        } else {
            workersListOfCurrentShift = new LinkedList<>();
        }
        Shift shiftToAdd = new Shift(date, sType, manager, workersListOfCurrentShift, getSnFactory(),getCurrentStoreSN());

        int type = shiftTypeToInteger(shiftType);

        Mapper.getInstance().insertShift(date,type,managerSn,shiftToAdd.getShiftSn(),getCurrentStoreSN());
        for(Worker x : workersListOfCurrentShift){
            Mapper.getInstance().insert_Shift_Workers(x.getWorkerSn(),shiftToAdd.getShiftSn());
        }

        Service.getInstance().getShiftHistory().put(shiftToAdd.getShiftSn(), shiftToAdd);
        infoObject.setMessage("Shift created successfully");
        return infoObject;
    }

    private Integer shiftTypeToInteger(String shiftType) {
        Integer DBshiftType=0;
        switch (shiftType) {
            case "MORNING":
                DBshiftType = 1;
                break;

            case "NIGHT":
                DBshiftType = 2;
                break;
        }
        return DBshiftType;
    }

    public InfoObject printShift(int shiftIndex) {
        InfoObject infoObject = new InfoObject("", true);
        if (!(Service.getInstance().getShiftHistory(getCurrentStoreSN()).containsKey(shiftIndex))) {
            infoObject.setMessage("There is no shift with this SN");
            infoObject.setIsSucceeded(false);
            return infoObject;
        }
        Shift shift = Service.getInstance().getShiftHistory(getCurrentStoreSN()).get(shiftIndex);
        shift.printShift();
        if (shift.getShiftWorkers().size() == 1) {
            System.out.println("\n" + "Workers: No workers for this shift");
        } else {
            System.out.println("\n" + "Workers: ");
            for (Worker worker : shift.getShiftWorkers()) {
                if ((shift.getDate().compareTo(new Date()) < 0) | Service.getInstance().getWorkerList(getCurrentStoreSN()).containsValue(worker)) {
                    if (!worker.getWorkerJobTitle().toUpperCase().equals("MANAGER")) {
                        worker.printWorker();
                    }
                }
            }
        }
        System.out.println("\n");
        return infoObject;
    }

    private Date parseDate(String _date) {
        Date date;
        try {
            date = new SimpleDateFormat("d-MM-yyyy").parse(_date);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    private InfoObject validateManagerConstrains(Worker manager, enums shiftType, Date shiftDate) {
        InfoObject infoObject = new InfoObject("", true);
        Date workersDate = manager.getWorkerStartingDate();
        if (shiftDate.compareTo(workersDate) < 0) {
            infoObject.setIsSucceeded(false);
            infoObject.setMessage("This worker : " + manager.getWorkerName() + " Start working only from " +
                    manager.getWorkerStartingDate());
            return infoObject;
        }

        if (!(manager.available(shiftDate, shiftType))) {
            infoObject.setIsSucceeded(false);
            infoObject.setMessage(manager.getWorkerName() + " Can't work on this shift because of his constrains");
            return infoObject;
        }

        return infoObject;
    }

    public InfoObject removeLaterShiftForFiredManagerByManagerSn(int workerSn) {
        InfoObject infoObject = new InfoObject("", true);

        Date today = new Date();
        List<Shift> listOfShiftsRemoved = new LinkedList<>();
        for (Shift shift : Service.getInstance().getShiftHistory(getCurrentStoreSN()).values()) {
            Date shiftDate = shift.getDate();
            // shift date occurs after today
            if (shiftDate.compareTo(today) > 0) {
                if (shift.getManager().getWorkerSn() == workerSn) {
                    listOfShiftsRemoved.add(shift);
                }
            }
        }
        for (Shift shiftToRemove : listOfShiftsRemoved) {
            Service.getInstance().getShiftHistory().remove(shiftToRemove.getShiftSn());
        }
        System.out.println("This shifts has been removed : \n");
        for (Shift deletedShift : listOfShiftsRemoved) {
            deletedShift.printShift();
            System.out.println();
        }

        return infoObject;
    }

    public int getCurrentStoreSN() {
        return currentStoreSN;
    }

    public void setCurrentStoreSN(int currentStoreSN) {
        this.currentStoreSN = currentStoreSN;
    }

    public boolean isStorekeeperAvailable(Date date,String shiftType){
        enums selectedDay;
        enums sType;
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        try {
            selectedDay = enums.valueOf(dayOfWeek);
        }
        catch (Exception e){
            System.out.println("No such day");
            return false;
        }
        try{
            sType = enums.valueOf(shiftType);
        }
        catch (Exception e){
            System.out.println("No such shift type");
            return false;
        }

        for(Shift shift : Service.getInstance().getShiftHistory(getCurrentStoreSN()).values()){
            if(shift.getDate().compareTo(date) == 0 && shift.getShiftType().compareTo(sType) == 0 ){
                for(Worker worker : shift.getShiftWorkers()){
                    if(worker.getWorkerJobTitle().toUpperCase().equals("storekeeper")){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void getAllSN() {

        int Sn = Mapper.getInstance().getShiftSn();
        this.snFactory = ++Sn;
    }
}

