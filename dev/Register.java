import javafx.util.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register {

    //Global Manager : a manager that can change products include the prices of products
    private Map<String,String> GlobalManager; // userName and Passwords of the inventory managers.

    //inventory Manager : less than Global Manager, a manager that can change products but not prices
    private Map<String,String> inventoryManagers; // userName and Passwords of the inventory managers.

    private ReportMaker reportMaker;
    private Inventory inventory;

    public Register(){
        inventory = Inventory.getInventory();
        reportMaker = ReportMaker.getPrinter();
        inventoryManagers = new HashMap<>();
        GlobalManager = new HashMap<>();
    }

    //Managers
    public String addInventoryManager(String username, String password){
        if(inventoryManagers.containsKey(username))
            return "can't register - username already exist";
        inventoryManagers.put(username,password);
        return "Inventory Manager " + username  +" - registered successfully";
    }
    public String removeInventoryManager(String username, String password , String usernameToRemove, String passwordToRemove){
        if(!inventoryManagers.containsKey(username))
            return "can't remove inventory manager - username doesnt exist";
        if((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password))) {
            inventoryManagers.remove(usernameToRemove, passwordToRemove);
            return "Inventory Manager - " + username + " removed";
        }
        else
            return "Only Global Manager can remove Inventory Manager";
    }
    public String addGlobalManager(String username, String password){
        if(GlobalManager.containsKey(username))
            return "can't register - username already exist";
        GlobalManager.put(username,password);
        return "Global Manager " + username  +" - registered successfully";
    }
    public String removeGlobalManager(String username, int password){
        if(!GlobalManager.containsKey(username))
            return "can't remove Global Manager - username doesnt exist";
        GlobalManager.remove(username,password);
        return "Global Manager - " + username + " removed";
    }


    //Inventory
    public String addProduct(String username, String password, int prodid, int amount, String name, int costPrice, int salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place){
        if((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password)) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username).equals(password)))
            return inventory.addProduct(prodid, amount,name,costPrice,salePrice,expDate,category, manufacturer, minAmount, place);
        return "can't add product - you are need to be a Manager";
    }
    public String removeProduct(String username, String password, int prodid){
        if((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password)) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username).equals(password)))
            return inventory.removeProduct(prodid);
        return "can't remove product - you are need to be a Manager";
    }
    public String addAmountToProduct(int id, int amount){
        return inventory.addAmountToProduct(id, amount);
    }
    public String removeAmountFromProduct(int id, int amount){
        return inventory.removeAmountFromProduct(id,amount);
    }
    public String setSalePriceById(String username, String password ,int prodid, int price){
        if(GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password))
           return inventory.setSalePrice(prodid,price);
        return "can't change price of product - you are need to be a Global Manager";
    }
    public String setPriceByCategory(String username, String password , List<String> category,int price){
        if(GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password))
            return inventory.setPriceByCategory(category, price);
        return "can't change price of product - you are need to be a Global Manager";
    }
    public String setCategory(String username, String password, int id, List<String> category){
        if((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password)) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username).equals(password)))
            return inventory.setCategory(id,category);
        return "can't remove product - you are need to be a Manager";
    }
    public String setDefectiveProducts(Integer prodId, Integer amount) {
        return inventory.setDefectiveProducts(prodId,amount);
    }
    public String getLastCostPrice(int prodId){
        return inventory.getLastCostPrice(prodId);
    }
    public String getLastSalePrice(int prodId){
        return inventory.getLastSalePrice(prodId);
    }
    public String shelfToStorage(int id, int amount){
        return inventory.shelfToStorage(id,amount);
    }

    public String storageToShelf(int id, int amount){
        return inventory.storageToShelf(id,amount);
    }
    //Reports
    public String NeedToBuyReport(){
        List<Pair<Integer,Integer>> toBuy = inventory.NeedToBuyProducts();
        return reportMaker.printMissingProducts(toBuy);
    }
    public String totalStockReport(){
        List<Pair<Integer,Integer>> totalStock = inventory.getQuantity();
        return reportMaker.printStock(totalStock);
    }
    public String CategoryReport(List<String> category){
        List<Pair<Integer,Integer>> prodByCategories = inventory.getProductsByCategories(category);
        return reportMaker.printByCategories(prodByCategories);
    }

    public String ExpiredReport(){
        List<Pair<Integer,Integer>> expiredProducts = inventory.ExpiredProducts();
        return reportMaker.MakeDefectiveReport(expiredProducts);
    }

    public String SalePricesReport(Integer id){
        Pair<Integer,List<Integer>> prices= inventory.SalePricesById(id);
        return reportMaker.printProductPrices(prices);
    }

    public String CostPriceReport(Integer id){
        Pair<Integer,List<Integer>> prices= inventory.CostPricesById(id);
        return reportMaker.printProductPrices(prices);
    }
}
