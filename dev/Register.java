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

    private ReportMaker printer;
    private Inventory inventory;

    public Register(){
        inventory = Inventory.getInventory();
        printer = ReportMaker.getPrinter(inventory);
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
    public String removeInventoryManager(String username, int password){
        if(!inventoryManagers.containsKey(username))
            return "can't remove inventory manager - username doesnt exist";
        inventoryManagers.remove(username,password);
        return "Inventory Manager - " + username + " removed";
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
    public String removeProduct(String username, String password, int prodid, int amount){
        if((GlobalManager.containsKey(username) && GlobalManager.get(username).equals(password)) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username).equals(password)))
            return inventory.removeProduct(prodid,amount);
        return "can't remove product - you are need to be a Manager";
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

    //Reports
    public String NeedToBuyReport(){
        List<Pair<Integer,Integer>> toBuy = inventory.NeedToBuyProducts();
        return printer.printMissingProducts(toBuy);
    }

    public String totalStockReport(){
        List<Pair<Integer,Integer>> totalStock = inventory.getQuantity();
        return printer.printStock(totalStock);
    }

    public String CategoryReport(List<String> category){
        List<Pair<Integer,Integer>> prodByCategories = inventory.getProductsByCategories(category);
        return printer.printByCategories(prodByCategories);
    }
    public String ExpiredReport(){
        List<Pair<Integer,Integer>> expiredProducts = inventory.ExpiredProducts();
        return printer.MakeDefectiveReport(expiredProducts);
    }

}
