package InterfaceLayer;

import LogicLayer.*;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InventoryController {
    private static InventoryController instance;
    //Global Manager : a manager that can change products include the prices of products
    private Map<String,String> GlobalManager; // userName and Passwords of the inventory managers.

    //inventory Manager : less than Global Manager, a manager that can change products but not prices
    private Map<String,String> inventoryManagers; // userName and Passwords of the inventory managers.

    private ReportMaker reportMaker;
    //private Inventory inventory;

    private Inventory inventory;

    private InventoryController(){
        inventory = Inventory.getInventory();
        reportMaker = ReportMaker.getPrinter();
        inventoryManagers = new HashMap<>();
        GlobalManager = new HashMap<>();
    }

    public static InventoryController getInventoryController() {
        if(instance==null){
            instance=new InventoryController();
        }
        return instance;
    }


    //Managers

    /*public String addInventoryManager(String username, String password){
        try {
            if (inventoryManagers.containsKey(username))
                return "can't register - username already exist";
            inventoryManagers.put(username, password);
            return "Inventory Manager " + username + " - registered successfully";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }*/
    public String addInventoryManager(String username, String password){
        return Inventory.addInventoryManager(username,password);
    }
    /*public String addGlobalManager(String username, String password){
        try {
            if (GlobalManager.containsKey(username))
                return "can't register - username already exist";
            GlobalManager.put(username, password);
            return "Global Manager " + username + " - registered successfully";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }*/
    public String addGlobalManager(String username, String password){
        return Inventory.addGlobalManager(username,password);
    }

    public String removeInventoryManager(String username, String password , String usernameToRemove, String passwordToRemove){
        try {
            if (!inventoryManagers.containsKey(username))
                return "can't remove inventory manager - username doesnt exist";
            if ((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password))) {
                inventoryManagers.remove(usernameToRemove, passwordToRemove);
                return "Inventory Manager - " + username + " removed";
            } else
                return "Only Global Manager can remove Inventory Manager";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String removeGlobalManager(String username, Integer password){
        try {

            if (!GlobalManager.containsKey(username))
                return "can't remove Global Manager - username doesnt exist";
            GlobalManager.remove(username, password);
            return "Global Manager - " + username + " removed";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    //Inventory

    public String CreateNewInventory(int newBranchId) {
        try{
            return Inventory.CreateNewInventory(newBranchId);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String addProduct(int branchId,String username, String password, int prodid, int amount, Double costPrice, Double salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place){
        String prodName;
        try {
            if ((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password)) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username).equals(password))) {
                prodName = Items.getName(prodid);
                if(prodName!=null) {
                    return inventory.addProduct(branchId,prodid, amount, prodName, costPrice, salePrice, expDate, category, manufacturer, minAmount, place);
                }
                return "can't execute the action";

            }
            return "can't add product - you are need to be a Manager";
        }catch (Exception e){
            return "can't execute the action";
        }
    }
    public String removeProduct(int branchId,String username, String password, int prodid){
        try {
            if ((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password)) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username).equals(password)))
                return inventory.removeProduct(branchId,prodid);
            return "can't remove product - you are need to be a Manager";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String addAmountToProduct(int branchId,int id, int amount){
        try {
            return inventory.addAmountToProduct(branchId,id, amount);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String removeAmountFromProduct(int branchId,int id, int amount){
        try{
            return inventory.removeAmountFromProduct(branchId,id,amount);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String setSalePriceById(int branchId,String username, String password ,int prodid, Double price){
        try{
            if(GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password))
                return inventory.setSalePrice(branchId,prodid,price);
            return "can't change price of product - you are need to be a Global Manager";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String setPriceByCategory(int branchId,String username, String password , List<String> category,Double price){
        try {
            if (GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password))
                return inventory.setPriceByCategory(branchId,category, price);
            return "can't change price of product - you are need to be a Global Manager";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String setCategory(int branchId,String username, String password, int id, List<String> category){
        try{
            if((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password)) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username).equals(password)))
                return inventory.setCategory(branchId, id,category);
            return "can't remove product - you are need to be a Manager";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String setDefectiveProducts(int branchId,Integer prodId, Integer amount) {
        try {
            return inventory.setDefectiveProducts(branchId,prodId,amount);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String shelfToStorage(int branchId,int id, int amount){
        try{
            return inventory.shelfToStorage(branchId,id,amount);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String storageToShelf(int branchId,int id, int amount){
        try{
            return inventory.storageToShelf(branchId,id,amount);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String MakeMissingOrder(int branchId) {
        try {
            List<Pair<Integer, Integer>> toBuy= inventory.NeedToBuyProducts(branchId);
            Map<Integer,Pair<Integer,Double>> orders= FacadeController.getFacadeController().makeOrder(branchId,toBuy);
            return inventory.mannageOrders(branchId,orders);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String MakeCistomiezedOrder(int branchId,List<Pair<Integer, Integer>> id_amount) {
        try{
            List<Pair<Integer,Integer>> toOrder=new LinkedList<>();
            List<Integer> noOrder=new LinkedList<>();
            for(Pair<Integer,Integer> prod : id_amount){
                if(inventory.conteinsProduct(prod.getKey())){
                    toOrder.add(prod);
                }
                else{
                    noOrder.add(prod.getKey());
                }
            }
            String output="";
            if (!noOrder.isEmpty()){
                output="cant Order the Products: "+noOrder+"\n";
            }
            Map<Integer,Pair<Integer,Double>> orders=FacadeController.getFacadeController().makeOrder(branchId,toOrder);
            output=output+inventory.mannageOrders(branchId,orders);
            return output;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "can't execute the action";
        }
    }
    public String AddToWeeklyOrder(int branchId,int day, List<Pair<Integer, Integer>> id_amount) {
        return inventory.addToWeeklyOrder(branchId,day,id_amount);
    }
    public String RemoveFromWeeklyOrder(int branchId, List<Integer> ids) {
        return inventory.removeFromWeeklyOrder(branchId,ids);
    }
    public String PromoteDay(Integer dayOfTheWeek) {
        String output="";
        List<Integer> branchIds= Inventory.getIdsToWeeklyOrders(dayOfTheWeek);
        for(Integer branchId : branchIds){
            List<Pair<Integer,Integer>> toOrder= inventory.getWeeklyOrder(branchId);
            if(!toOrder.isEmpty()) {
                Map<Integer, Pair<Integer, Double>> orders = FacadeController.getFacadeController().makeOrder(branchId,toOrder);
                output = output + inventory.mannageOrders(branchId,orders) + "\n";
            }
        }
        return output;
    }
    public String PrintWeeklyOrder(int branchId) {
        try{
            List<Pair<Integer,Integer>> weeklyOrder=inventory.getWeeklyOrder(branchId);
            return reportMaker.PrintWeeklyOrder(branchId,weeklyOrder);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    //Reports

    public String NeedToBuyReport(int branchId){
        try {
            List<Pair<Integer, Integer>> toBuy = inventory.NeedToBuyProducts(branchId);
            return reportMaker.printMissingProducts(branchId,toBuy);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String totalStockReport(int branchId){
        try {
            List<Pair<Integer, Integer>> totalStock = inventory.getQuantity(branchId);
            return reportMaker.printStock(branchId,totalStock);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String ShelfReport(int branchId) {
        try {
            List<Pair<Integer, Integer>> shelfStock = inventory.getShelfQuantity(branchId);
            return reportMaker.printShelfReport(branchId,shelfStock);
        }catch (Exception e){
            return "can't execute the action";
        }
    }
    public String StorageReport(int branchId) {
        try {
            List<Pair<Integer, Integer>> storageStock = inventory.getStorageQuantity(branchId);
            return reportMaker.printStorageReport(branchId,storageStock);
        }
        catch (Exception e){
            return "can't execute the action";
        }

    }
    public String CategoryReport(int branchId,List<String> category){
        try {
            List<Pair<Integer, Integer>> prodByCategories = inventory.getProductsByCategories(branchId,category);
            return reportMaker.printByCategories(branchId,prodByCategories);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String ExpiredReport(int branchId){
        try {
            List<Pair<Integer, Integer>> expiredProducts = inventory.ExpiredProducts(branchId);
            return reportMaker.MakeDefectiveReport(branchId,expiredProducts);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String SalePricesReport(int branchId,Integer id){
        try{
            Pair<Integer,List<Double>> prices= inventory.SalePricesById(branchId, id);
            return reportMaker.printSaleProductPrices(branchId,prices);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String CostPriceReport(int branchId,Integer id){
        try {
            Pair<Integer, List<Double>> prices = inventory.CostPricesById(branchId,id);
            return reportMaker.printCostProductPrices(branchId,prices);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    //Tests Methods

   /* public Pair<Integer,Pair<Integer,Integer>> getquantityById(int branchId,Integer Id){
        return Inventory.getInventory(branchId).getQuantityById(Id);
    }
    public Pair<Integer,Integer> getquantityEXPById(int branchId,Integer Id){
        return Inventory.getInventory(branchId).getQuantityEXPById(Id);
    }
    public Double getProductSalePrice(int branchId,int id) {
        return inventory.getProdSalePrice(branchId,id);
    }*/
}
