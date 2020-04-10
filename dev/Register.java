import javafx.util.Pair;
import jdk.nashorn.internal.objects.Global;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register {

    //Global Manager : a manager that can change products include the prices of products
    private Map<String,String> GlobalManager; // userName and Passwords of the inventory managers.

    //inventory Manager : less than Global Manager, a manager that can change products but not prices
    private Map<String,String> inventoryManagers; // userName and Passwords of the inventory managers.

    private Printer printer;
    private Inventory inventory;

    public Register(){
        inventory = Inventory.getInventory();
        printer = Printer.getPrinter(inventory);
        inventoryManagers = new HashMap<>();
        GlobalManager = new HashMap<>();
    }

    //Managers
    public boolean addInventoryManager(String username, String password){
        if(inventoryManagers.containsKey(username))
            return false;
        inventoryManagers.put(username,password);
        return true;
    }
    public boolean removeInventoryManager(String username, int password){
        if(!inventoryManagers.containsKey(username))
            return false;
        inventoryManagers.remove(username,password);
        return true;
    }
    public boolean addGlobalManager(String username, String password){
        if(GlobalManager.containsKey(username))
            return false;
        GlobalManager.put(username,password);
        return true;
    }
    public boolean removeGlobalManager(String username, int password){
        if(!GlobalManager.containsKey(username))
            return false;
        GlobalManager.remove(username,password);
        return true;
    }


    //Inventory
    public boolean addProduct(String username, String password, int prodid, int amount, String name, int costPrice, int salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place){
        if((GlobalManager.containsKey(username) && GlobalManager.get(username) == password) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username) == password))
             return inventory.addProduct(prodid, amount,name,costPrice,salePrice,expDate,category, manufacturer, minAmount, place);
        return false;
    }
    public boolean removeProduct(String username, String password, int prodid, int amount){
        if((GlobalManager.containsKey(username) && GlobalManager.get(username) == password) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username) == password))
            return inventory.removeProduct(prodid,amount);
        return false;
    }
    public boolean setSalePriceById(String username, String password ,int prodid, int price){
        if(GlobalManager.containsKey(username) && GlobalManager.get(username) == password)
           return inventory.setSalePrice(prodid,price);
        return false;
    }
    public boolean setPriceByCategory(String username, String password , List<String> category,int price){
        if(GlobalManager.containsKey(username) && GlobalManager.get(username) == password)
            return inventory.setPriceByCategory(category, price);
        return false;
    }
    public boolean setCategory(String username, String password, int id, List<String> category){
        if((GlobalManager.containsKey(username) && GlobalManager.get(username) == password) || (inventoryManagers.containsKey(username) && inventoryManagers.get(username) == password))
            return inventory.setCategory(id,category);
        return false;
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
