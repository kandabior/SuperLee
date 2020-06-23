package src.BusinessLayer.Inventory;

import src.DataAccessLayer.Inventory.InventoryMapper;

import src.DataAccessLayer.Inventory.ItemMapper;
import javafx.util.Pair;
import java.time.LocalDate;
import java.util.*;

public class Inventory {

    private static Inventory instance;
    private InventoryMapper inventoryMapper;

    private Inventory() {
        inventoryMapper=new InventoryMapper();
    }

    public static Inventory getInventory() {
        if(instance == null){
            instance= new Inventory();
        }
        return instance;
    }

    public static int getProductMinStatic(int branchId, Integer key) {
        return instance.getProductMin(branchId,key);
    }

    public static int getAmountStatic(int branch, Integer key) {
        return instance.getAmount(branch, key);
    }

    public  String getProductName(int branchId,int prodId) {
        if (inventoryMapper.isBranchExist(branchId)) {
            if (inventoryMapper.isInventoryConteinsProd(branchId, prodId)) {
                return inventoryMapper.getProductName(branchId, prodId);
            }
            return "product ID doesnt exist";
        }
        return "branch id does not exist";


    }

    public static String getProductNameStatic(int branchId,int prodId) {
        return instance.getProductName(branchId,prodId);
    }
    public  int getProductMin(int branchId,int prodId) {
        if(inventoryMapper.isBranchExist(branchId)) {
            if(inventoryMapper.isInventoryConteinsProd(branchId,prodId)) {
                return inventoryMapper.getProductMin(branchId, prodId);
            }
        }
        return 0;
    }
    public  int getAmount(int branchId,Integer prodId) {
        if(inventoryMapper.isBranchExist(branchId)) {
            if (inventoryMapper.isInventoryConteinsProd(branchId, prodId)) {
                return inventoryMapper.getProductQuantity(branchId, prodId);
            }
        }
        return 0;

    }

    public  String CreateNewInventory(Integer branchId) {
         if(inventoryMapper.CreateNewInventory(branchId))
             return "Inventory number: " + branchId + " created successfully";
         return "Can't create this inventory";
  }

    //get all the inventories id that the day of the weekly report = dayOfTheWeek
    public  List<Integer> getBranchIdsToWeeklyOrders(int dayOfTheWeek) {
        return inventoryMapper.getBranchIdsToWeeklyOrders(dayOfTheWeek);
    }

    public  String manageOrders(int branchId, Map<Pair<Integer,Integer>,Pair<Integer,Double>> orders) {
        if(inventoryMapper.isBranchExist(branchId)) {
            for (Pair<Integer,Integer> prodId : orders.keySet()) {
                addAmountToProduct(branchId, prodId.getKey(), orders.get(prodId).getKey());
                int amount = orders.get(prodId).getKey();
                double totalPrice = orders.get(prodId).getValue();
                double newPrice = totalPrice / amount;
                List<Double> listPrices = inventoryMapper.getCostPrices(branchId, prodId.getKey());
                double currentPrice= inventoryMapper.getCurrentCostPrices(branchId,prodId.getKey());
                if (!listPrices.contains(newPrice) && currentPrice!=newPrice) {
                    setCostPrice(branchId, prodId.getKey(), newPrice);
                }
                if(!inventoryMapper.getItemsIdsExist(prodId.getKey(),prodId.getValue()))
                    inventoryMapper.setItemsIds(prodId.getKey(),prodId.getValue());
            }
            return "Order Completed Successfully for branch: " + branchId;
        }
        else{
            return "branch id does not exist";
        }
    }
    private  void setCostPrice(int branchId,Integer prodId, double newPrice) {
        inventoryMapper.setCostPrice(branchId,prodId,newPrice);
    }
    public  List<Pair<Integer,Integer>> getQuantity(int branchId){ // Pair[0] = productId, Pair[1] = storage + shelf Quantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : inventoryMapper.getProductsIds(branchId)){
            Pair<Integer,Integer> p = new Pair<>(id,inventoryMapper.getProductQuantity(branchId,id));
            qnty.add(p);
        }
        return qnty;
    }
    public  List<Pair<Integer,Integer>> getStorageQuantity(int branchId){ // Pair[0] = productId, Pair[1] = StorageQuantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : inventoryMapper.getProductsIds(branchId)){
            Pair<Integer,Integer> p = new Pair<>(id,inventoryMapper.getStorageQunatity(branchId,id));
            qnty.add(p);
        }
        return qnty;
    }
    public   List<Pair<Integer,Integer>> getShelfQuantity(int branchId){ // Pair[0] = productId, Pair[1] = ShelfQuantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : inventoryMapper.getProductsIds(branchId)){
            Pair<Integer,Integer> p = new Pair<>(id,inventoryMapper.getShelfQunatity(branchId,id));
            qnty.add(p);
        }
        return qnty;
    }
    public   List<Pair<Integer,Integer>> NeedToBuyProducts(int branchId) {   //Pair[0] = productId, Pair[1] = shelf+storage Quantity.  get the products id that their quantity is less than their minimum quantity.
        List<Pair<Integer,Integer>> needToBuy = new LinkedList<>();
        for (Integer id : inventoryMapper.getProductsIds(branchId)) {
            if (inventoryMapper.getProductMin(branchId,id) > inventoryMapper.getProductQuantity(branchId,id))
                needToBuy.add(new Pair<>(id,inventoryMapper.getProductQuantity(branchId,id)));
        }
        return needToBuy;
    }
    public   List<Pair<Integer,Integer>> getProductsByCategories(int branchId, List<String> category){  //Integer = productId
        List<Pair<Integer,Integer>> prodectList = new LinkedList<>();
        for(Integer id : inventoryMapper.getProductsIds(branchId)){
            boolean found = false;
            List<String> categoryList=inventoryMapper.getProductCategories(branchId,id);
            for (int i = 0; i<category.size() & !found; i++) {
                if (categoryList.contains(category.get(i))) {
                    prodectList.add(new Pair<>(id, inventoryMapper.getProductQuantity(branchId, id)));
                    found = true;
                }
            }
        }
        return prodectList;
    }
    public   List<Pair<Integer,Integer>> ExpiredProducts(int branchId){  //Integer = productId
        List<Pair<Integer,Integer>> expiredProducts = new LinkedList<>();
        for(Integer id : inventoryMapper.getExpiredIds(branchId)){
                expiredProducts.add(new Pair<>(id,inventoryMapper.getExpiredQuantity(branchId,id)));
        }
        return expiredProducts;
    }
    public   String setSalePrice(int branchId, int id, Double price) {
        if(inventoryMapper.isInventoryConteinsProd(branchId,id)) {
            if (inventoryMapper.setSalePrice(branchId, id, price)) {
                return "product " + id + "- price changed to: " + price;
            }
        }
        return "could not set sale price to product "+id;
    }
    public   String setPriceByCategory(int branchId, List<String> category, Double price){
        List<Pair<Integer,Integer>> cat = getProductsByCategories(branchId,category);
        for(Pair<Integer,Integer> pair : cat){
            inventoryMapper.setSalePrice(branchId,pair.getKey(),price);
        }
        return "all prices for categories: " + category.toString() + " changed to: " + price;
    }
    public  String setExpired(int branchId, int productId, Integer amount){ //removing a specific amount of expired products from the storage inventory to the expired inventory.
        if(!inventoryMapper.isInventoryConteinsProd(branchId,productId)) {
            return "product ID doesnt exist";
        }
        int currentAmount=inventoryMapper.getProductQuantity(branchId,productId);
        if(amount<=currentAmount) {
            if(inventoryMapper.getShelfQunatity(branchId,productId)>amount){
                inventoryMapper.removeAmountFromProductShelf(branchId,productId,amount);
            }
            else{
                int shelfRemove= inventoryMapper.getShelfQunatity(branchId,productId);
                int storageRemove= amount-shelfRemove;
                inventoryMapper.removeAmountFromProductShelf(branchId,productId,shelfRemove);
                inventoryMapper.removeAmountFromProductStorage(branchId,productId,storageRemove);
            }
            if(inventoryMapper.isExpiredContainProduct(branchId,productId)){
                inventoryMapper.updateExpired(branchId,productId,amount);
            }
            else{
                inventoryMapper.addExpired(branchId, productId, amount);
            }
            return "product " + productId + "- quantity of " + amount + " was changed to expired";
        }
        else{
            return "current amount of product "+productId+" less then "+amount;
        }

    }
    public String addProduct(int branchId, int id,int amount, String name, Double costPrice, Double salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place) {
        if(inventoryMapper.isBranchExist(branchId)) {
            if(!inventoryMapper.isInventoryConteinsProd(branchId,id)) {
                if (inventoryMapper.addProduct(branchId, id, name, costPrice, salePrice, expDate, category, manufacturer, minAmount, place)) {
                    inventoryMapper.addNewAmountProductToQuantities(branchId, id, amount);
                    return " product id: " + id + " - added to the inventory";
                }
                return "cant execute the action";
            }
            return "product ID already exist";
        }
        return "Error - branch id does not exist";
    }
    public String removeProduct(int branchId, int id) {
        if (inventoryMapper.isBranchExist(branchId)) {
            if (inventoryMapper.isInventoryConteinsProd(branchId, id)) {
                return inventoryMapper.removeProduct(branchId, id);
            }
            return "product ID doesnt exist";
        }
        return "branch id does not exist";
    }
    public  String addAmountToProduct(int branchId, int id, int amount) {
        if (inventoryMapper.isInventoryConteinsProd(branchId, id)) {
            int currAmount= inventoryMapper.getStorageQunatity(branchId,id);
            inventoryMapper.addAmountToProduct(branchId, id,currAmount, amount);
            return "product id: " + id + " - amount of " + amount + " added to the inventory";
        }
        return "product id does not exist";
    }

    public  String removeAmountFromProduct(int branchId, int id, int amount){
        if (inventoryMapper.isBranchExist(branchId)) {
            if (inventoryMapper.isInventoryConteinsProd(branchId, id)) {
                int shelfQnty = inventoryMapper.getShelfQunatity(branchId, id);   //storage , shelf
                int storageQnty = inventoryMapper.getStorageQunatity(branchId, id);
                if (shelfQnty + storageQnty < amount)
                    return "cant remove - there is less than " + amount + " items from this product in the inventory";
                if (shelfQnty >= amount) {
                    inventoryMapper.removeAmountFromProductShelf(branchId, id, amount);
                } else {
                    inventoryMapper.removeAmountFromProductStorage(branchId, id, amount);
                }
                if (inventoryMapper.getProductQuantity(branchId, id) < inventoryMapper.getProductMin(branchId, id)) { //less than the minimum amount
                    return "product id: " + id + "- amount of " + amount + " was removed from the inventory. (Less than the Minimum amount - Need to buy more from this product)";
                }
                return "product id: " + id + " - amount of " + amount + " was removed from the inventory";
            }
            return "product id does not exist";
        }
        return "branch id does not exist";
    }
    public  String setCategory(int branchId,int id, List<String> category) {
        if (inventoryMapper.isBranchExist(branchId)) {
            if (inventoryMapper.isInventoryConteinsProd(branchId, id)) {
                return inventoryMapper.setCategory(branchId, id, category);
            }
            return "product id does not exist";
        }
        return "branch id does not exist";
    }
    public  String shelfToStorage(int branchId, int id, int amount){
        if (inventoryMapper.isBranchExist(branchId)) {
            if (inventoryMapper.isInventoryConteinsProd(branchId, id)) {
                if(inventoryMapper.shelfToStorage(branchId,id,amount)){
                    return "product id: " + id + " - amount of " + amount + " removed from the shelf to the Storage";
                }
                else{
                    return "cant execute the action";
                }
            }
            return "product id does not exist";
        }
        return "branch id does not exist";
    }
    public  String storageToShelf(int branchId,int id, int amount){
        if (inventoryMapper.isBranchExist(branchId)) {
            if (inventoryMapper.isInventoryConteinsProd(branchId, id)) {
                if(inventoryMapper.storageToShelf(branchId,id,amount)){
                    return "product id: " + id + " - amount of " + amount + " removed from the storage to the shelf";
                }
                else{
                    return "cant execute the action";
                }
            }
            return "product id does not exist";
        }
        return "branch id does not exist";
    }
    public  String getLastCostPrice(int branchId,int id) {
        if (inventoryMapper.isBranchExist(branchId)) {
            if (inventoryMapper.isInventoryConteinsProd(branchId, id)) {
                return Double.toString(inventoryMapper.getCurrentCostPrices(branchId,id));
            }
            return "product id does not exist";
        }
        return "branch id does not exist";
    }
    public  String getLastSalePrice(int branchId,int id){
        if (inventoryMapper.isBranchExist(branchId)) {
            if (inventoryMapper.isInventoryConteinsProd(branchId, id)) {
                return Double.toString(inventoryMapper.getCurrentCostPrices(branchId,id));
            }
            return "product id does not exist";
        }
        return "branch id does not exist";
    }
    public Pair<Integer, List<Double>> SalePricesById(int branchId,int id) {
        return new Pair<>(id,inventoryMapper.getSalePrices(branchId,id));
    }
    public Pair<Integer, List<Double>> CostPricesById(int branchId,int id) {
        return new Pair<>(id,inventoryMapper.getCostPrices(branchId,id));
    }
    /*public String setDefectiveProducts(Integer branchId, Integer prodId, Integer amount) {
        if (inventoryMapper.isBranchExist(branchId)) {
            if (inventoryMapper.isInventoryConteinsProd(branchId, id)) {
                Pair<Integer, Integer> amounts = quantities.get(prodId);
                if (amounts.getKey() + amounts.getValue() < amount) {
                    return "Cant transform - There is Less than " + amount + " items from product number " + prodId + " in the inventory";
                }
                if (amounts.getValue() >= amount) {
                    inventoryMapper.removeAmountFromProductShelf(branchId, prodId, amount);
                    //quantities.put(prodId,new Pair<>(amounts.getKey(),amounts.getValue()-amount));
                } else {
                    inventoryMapper.removeAmountFromProductStorage(branchId, prodId, amount);
                    //quantities.put(prodId,new Pair<>(amounts.getKey()-(amount-amounts.getValue()),0));
                }

                if (!inventoryMapper.updateExpired(branchId, prodId, amount)) {
                    //expired.put(prodId,expired.get(prodId)+amount);
                    inventoryMapper.addExpired(branchId, prodId, amount);
                    //expired.put(prodId,amount);
                }
                if (quantities.get(prodId).getKey() + quantities.get(prodId).getValue() < inventory.get(prodId).getMinAmount()) //less than the minimum amount
                    return "product id: " + prodId + "- amount of " + amount + " set us expired. (Less than the Minimum amount - Need to buy more from this product)";
                return "product id: " + prodId + " - amount of " + amount + " set us expired";
            }
    }*/
    public String addToWeeklyOrder(int branchId ,int day,List<Pair<Integer, Integer>> id_amount) {
        if (inventoryMapper.isBranchExist(branchId)) {
            List<Integer> noOrder=new LinkedList<>();
            String output="";
            if(!inventoryMapper.weeklyOrderDayExist(branchId,day)) {
                inventoryMapper.setWeeklyOrder(branchId, day);
            }
            for(Pair<Integer,Integer> p: id_amount){
                if(inventoryMapper.isInventoryConteinsProd(branchId,p.getKey())) {
                    inventoryMapper.AddToWeeklyOrder(branchId,p.getKey(), p.getValue(),day);
                }
                else{
                    noOrder.add(p.getKey());
                }
            }
            if(!noOrder.isEmpty()){
                output="cant add the Products: "+noOrder+"\n";
            }
            output=output+ "Weekly order was update for branch: "+branchId+"\n";
            return output;
        }
        return "branch id does not exist";
    }
    public String removeFromWeeklyOrder(int branchId, List<Integer> ids,int day){
        if (inventoryMapper.isBranchExist(branchId)) {
            for (Integer id : ids) {
                if (inventoryMapper.isWeeklyContainProd(branchId,id,day)) {
                    inventoryMapper.removeFromWeeklyOrder(branchId,id,day);
                }
            }
            return "Weekly order was update for branch: " + branchId + "\n";
        }
        return "branch id does not exist";
    }
    public List<Pair<Integer, Integer>> getWeeklyOrder(int branchId, int day) {
        if (inventoryMapper.isBranchExist(branchId)) {
            return inventoryMapper.getWeeklyOrder(branchId,day);
            /*List<Pair<Integer, Integer>> output = new LinkedList<>();
            for (Integer id : WeeklyOrder.keySet()) {
                output.add(new Pair<>(id, WeeklyOrder.get(id)));
            }
            return output;*/
        }
        return null;
    }
    public  boolean containsProduct(int branchId,Integer key) {
        return inventoryMapper.isInventoryConteinsProd(branchId,key);
    }

    //Managers
    public String addInventoryManager(int branchId,String username, String password){
        if(!inventoryMapper.isInventoryManagerExist(branchId,username) && inventoryMapper.isBranchExist(branchId)) {
            if(inventoryMapper.addInventoryManager(branchId,username,password)) {
                return "Inventory Manager " + username + " - registered successfully";
            }
            return "cant execute the action";
        }
        else{
            return "can't register to the current branch id - Try again ";
        }
    }
    public  String addGlobalManager(int branchId,String username, String password){
        if(!inventoryMapper.isGlobalMannagerExist(branchId,username) && inventoryMapper.isBranchExist(branchId)) {
            if( inventoryMapper.addGlobalManager(branchId,username, password)){
                return "Global Manager " + username + " - registered successfully";
            }
            return "cant execute the action";
        }
        else{
            return "can't register to the current branch id - Try again ";
        }
    }
    public String removeInventoryManager(int branchId,String username, String password, String usernameToRemove) {

        if (!inventoryMapper.isInventoryManagerExist(branchId,username))
            return "can't remove inventory manager - username doesnt exist";
        if (inventoryMapper.checkGlobalManager(branchId,username,password)) {
            if(inventoryMapper.removeInventoryManager(branchId,usernameToRemove)) {
                return "Inventory Manager - " + username + " removed";
            }
            return "cant execute the action";
        } else
            return "Only Global Manager can remove Inventory Manager";
    }
    public String removeGlobalManager(int branchId,String username, String password) {
        if (!inventoryMapper.isGlobalMannagerExist(branchId,username)) {
            return "can't remove Global Manager - username doesnt exist";
        }
        if(inventoryMapper.removeGlobalManager(branchId,username, password)) {
            return "Global Manager - " + username + " removed";
        }
        else{
            return "cant execute the action";
        }

    }
    public boolean checkGlobalManager(int branchId,String username, String password) {
        return inventoryMapper.checkGlobalManager(branchId, username, password);
    }
    public boolean checkInventoryManager(int branchId,String username, String password) {
        return inventoryMapper.checkInventoryManager(branchId, username, password);
    }
    public String getItemName(int prodid) {
        return ItemMapper.getName(prodid);
    }

    public List<Pair<Integer, Integer>> NeedToBuyProductsForOrder(int branchId) {
        List<Pair<Integer,Integer>> needToBuy = new LinkedList<>();
        for (Integer id : inventoryMapper.getProductsIds(branchId)) {
            if (inventoryMapper.getProductMin(branchId,id) > inventoryMapper.getProductQuantity(branchId,id))
                needToBuy.add(new Pair<>(id,inventoryMapper.getProductMin(branchId,id)-inventoryMapper.getProductQuantity(branchId,id)));
        }
        return needToBuy;

    }

    public boolean cancelOrder(int orderId) {
        if(inventoryMapper.isOrderExists(orderId)){
            return inventoryMapper.cancelOrder(orderId);
        }
        else{
            return false;
        }
    }



/*
    //Tests Methods
    public Pair<Integer,Pair<Integer,Integer>> getQuantityById(Integer id) {
        return new Pair<>(id,quantities.get(id));
    }
    public Pair<Integer, Integer> getQuantityEXPById(Integer id) {
        return new Pair<>(id,expired.get(id));
    }
    public Double getProdSalePrice(int id) {
        return inventory.get(id).getSalePrice();
    }*/
}
