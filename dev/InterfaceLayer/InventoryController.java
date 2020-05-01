package InterfaceLayer;

import LogicLayer.*;
import PresentationLayer.SupplierMenu;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryController {
    private static InventoryController instance;
    //Global Manager : a manager that can change products include the prices of products
    private Map<String,String> GlobalManager; // userName and Passwords of the inventory managers.

    //inventory Manager : less than Global Manager, a manager that can change products but not prices
    private Map<String,String> inventoryManagers; // userName and Passwords of the inventory managers.

    private ReportMaker reportMaker;
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
    public String addInventoryManager(String username, String password){
        try {
            if (inventoryManagers.containsKey(username))
                return "can't register - username already exist";
            inventoryManagers.put(username, password);
            return "Inventory Manager " + username + " - registered successfully";
        }
        catch (Exception e){
            return "can't execute the action";
        }
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
    public String addGlobalManager(String username, String password){
        try {
            if (GlobalManager.containsKey(username))
                return "can't register - username already exist";
            GlobalManager.put(username, password);
            return "Global Manager " + username + " - registered successfully";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    /*public String removeGlobalManager(String username, Integer password){
        try {

            if (!GlobalManager.containsKey(username))
                return "can't remove Global Manager - username doesnt exist";
            GlobalManager.remove(username, password);
            return "Global Manager - " + username + " removed";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }*/


    //Inventory
    public String addProduct(String username, String password, int prodid, int amount, Double costPrice, Double salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place){
        String prodName;
        try {
            if ((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password)) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username).equals(password))) {
                prodName = Items.getName(prodid);
                if(prodName!=null) {
                    return inventory.addProduct(prodid, amount, prodName, costPrice, salePrice, expDate, category, manufacturer, minAmount, place);
                }
                return "can't execute the action";

            }
            return "can't add product - you are need to be a Manager";
        }catch (Exception e){
            return "can't execute the action";
        }
    }
    public String removeProduct(String username, String password, int prodid){
        try {
            if ((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password)) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username).equals(password)))
                return inventory.removeProduct(prodid);
            return "can't remove product - you are need to be a Manager";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String addAmountToProduct(int id, int amount){
        try {
            return inventory.addAmountToProduct(id, amount);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String removeAmountFromProduct(int id, int amount){
        try{
            return inventory.removeAmountFromProduct(id,amount);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String setSalePriceById(String username, String password ,int prodid, Double price){
        try{
            if(GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password))
                return inventory.setSalePrice(prodid,price);
            return "can't change price of product - you are need to be a Global Manager";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String setPriceByCategory(String username, String password , List<String> category,Double price){
        try {
            if (GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password))
                return inventory.setPriceByCategory(category, price);
            return "can't change price of product - you are need to be a Global Manager";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String setCategory(String username, String password, int id, List<String> category){
        try{
            if((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password)) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username).equals(password)))
                return inventory.setCategory(id,category);
            return "can't remove product - you are need to be a Manager";
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String setDefectiveProducts(Integer prodId, Integer amount) {
        try {
            return inventory.setDefectiveProducts(prodId,amount);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    public String shelfToStorage(int id, int amount){
        try{
            return inventory.shelfToStorage(id,amount);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    public String storageToShelf(int id, int amount){
        try{
            return inventory.storageToShelf(id,amount);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    public String MakeMissingOrder() {
        try {
            List<Pair<Integer, Integer>> toBuy= inventory.NeedToBuyProducts();
            Map<Integer,Pair<Integer,Integer>> orders= FacadeController.getFacadeController().makeOrder(toBuy);
            return inventory.mannageOrders(orders);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    public String MakeCistomiezedOrder(List<Pair<Integer, Integer>> id_amount) {
        try{
            Map<Integer,Pair<Integer,Integer>> orders=FacadeController.getFacadeController().makeOrder(id_amount);
            return inventory.mannageOrders(orders);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }


    //Reports
    public String NeedToBuyReport(){
        try {
            List<Pair<Integer, Integer>> toBuy = inventory.NeedToBuyProducts();
            return reportMaker.printMissingProducts(toBuy);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    public String totalStockReport(){
        try {
            List<Pair<Integer, Integer>> totalStock = inventory.getQuantity();
            return reportMaker.printStock(totalStock);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    public String ShelfReport() {
        try {
            List<Pair<Integer, Integer>> shelfStock = inventory.getShelfQuantity();
            return reportMaker.printShelfReport(shelfStock);
        }catch (Exception e){
            return "can't execute the action";
        }
    }

    public String StorageReport() {
        try {
            List<Pair<Integer, Integer>> storageStock = inventory.getStorageQuantity();
            return reportMaker.printStorageReport(storageStock);
        }
        catch (Exception e){
            return "can't execute the action";
        }

    }

    public String CategoryReport(List<String> category){
        try {
            List<Pair<Integer, Integer>> prodByCategories = inventory.getProductsByCategories(category);
            return reportMaker.printByCategories(prodByCategories);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    public String ExpiredReport(){
        try {
            List<Pair<Integer, Integer>> expiredProducts = inventory.ExpiredProducts();
            return reportMaker.MakeDefectiveReport(expiredProducts);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    public String SalePricesReport(Integer id){
        try{
            Pair<Integer,List<Double>> prices= inventory.SalePricesById(id);
            return reportMaker.printSaleProductPrices(prices);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }
    public String CostPriceReport(Integer id){
        try {
            Pair<Integer, List<Double>> prices = inventory.CostPricesById(id);
            return reportMaker.printCostProductPrices(prices);
        }
        catch (Exception e){
            return "can't execute the action";
        }
    }

    //Tests Methods

    public Pair<Integer,Pair<Integer,Integer>> getquantityById(Integer Id){
        return inventory.getQuantityById(Id);
    }

    public Pair<Integer,Integer> getquantityEXPById(Integer Id){
        return inventory.getQuantityEXPById(Id);
    }

    public Double getProductSalePrice(int id) {
        return inventory.getProdSalePrice(id);
    }
}
