package LogicLayer;

import DataAccessLayer.InventoryMapper;
import javafx.util.Pair;
import java.time.LocalDate;
import java.util.*;

public class Inventory {

    //private static Inventory instance;

    //private Integer branchId;
    //private Map<Integer, Product> inventory;
    //private Map<Integer, Pair<Integer,Integer>> quantities; //Pair[0] = storage , Pair[1] = shelf
    //private Map<Integer, Integer> expired; //Id, amount
    //private Integer dayForWeeklyOrder=1;
    //private Map<Integer,Integer> WeeklyOrder;
/*
    private Inventory() {
        inventory = new HashMap<>();
        quantities = new HashMap<>();
        expired = new HashMap<>();
        WeeklyOrder= new HashMap<>();
    }

    public static Inventory getInventory() {
        if(instance == null){
            return new Inventory();
        }
        return instance;
    }*/

    public static String getProdactName(int branchId,int prodId) {
        if (InventoryMapper.isBranchExist(branchId)) {
            if (InventoryMapper.isInventoryConteinsProd(branchId, prodId)) {
                return InventoryMapper.getProductName(branchId, prodId);
            }
            return "product ID doesnt exist";
        }
        return "branch id does not exist";


    }
    public static int getProductMin(int branchId,int prodId) {
        if(InventoryMapper.isBranchExist(branchId)) {
            if(InventoryMapper.isInventoryConteinsProd(branchId,prodId)) {
                return InventoryMapper.getProductMin(branchId, prodId);
            }
        }
        return 0;
    }
    public static int getAmount(int branchId,Integer prodId) {
        if(InventoryMapper.isBranchExist(branchId)) {
            if (InventoryMapper.isInventoryConteinsProd(branchId, prodId)) {
                return InventoryMapper.getProductQuantity(branchId, prodId);
            }
        }
        return 0;

    }

    public static String CreateNewInventory(Integer branchId) {
         if(InventoryMapper.CreateNewInventory(branchId))
             return "Inventory number: " + branchId + " created successfully";
         return "Can't create this inventory";
  }

    //get all the inventories id that the day of the weekly report = dayOfTheWeek
    public static List<Integer> getBranchIdsToWeeklyOrders(int dayOfTheWeek) {
        return InventoryMapper.getBranchIdsToWeeklyOrders(dayOfTheWeek);
    }

    public static String mannageOrders(int branchId, Map<Integer,Pair<Integer,Double>> orders) {
        for(Integer prodId: orders.keySet()){
            addAmountToProduct(branchId, prodId,orders.get(prodId).getKey());
            int amount=orders.get(prodId).getKey();
            double totalPrice= orders.get(prodId).getValue();
            double newPrice= totalPrice/amount;
            double currentPrice= InventoryMapper.getCurrentCostPrices(branchId,prodId);
            if(currentPrice!=newPrice){
                setCostPrice(branchId,prodId,newPrice);
            }
        }
        return "Order Completed Successfully for branch: "+branchId;
    }
    private static void setCostPrice(int branchId,Integer prodId, double newPrice) {
        InventoryMapper.setCostPrice(branchId,prodId,newPrice);
    }

    public static List<Pair<Integer,Integer>> getQuantity(int branchId){ // Pair[0] = productId, Pair[1] = storage + shelf Quantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : InventoryMapper.getProductsIds(branchId)){
            Pair<Integer,Integer> p = new Pair<>(id,InventoryMapper.getProductQuantity(branchId,id));
            qnty.add(p);
        }
        return qnty;
    }
    public static List<Pair<Integer,Integer>> getStorageQuantity(int branchId){ // Pair[0] = productId, Pair[1] = StorageQuantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : InventoryMapper.getProductsIds(branchId)){
            Pair<Integer,Integer> p = new Pair<>(id,InventoryMapper.getStorageQunatity(branchId,id));
            qnty.add(p);
        }
        return qnty;
    }
    public static  List<Pair<Integer,Integer>> getShelfQuantity(int branchId){ // Pair[0] = productId, Pair[1] = ShelfQuantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : InventoryMapper.getProductsIds(branchId)){
            Pair<Integer,Integer> p = new Pair<>(id,InventoryMapper.getShelfQunatity(branchId,id));
            qnty.add(p);
        }
        return qnty;
    }
    public static  List<Pair<Integer,Integer>> NeedToBuyProducts(int branchId) {   //Pair[0] = productId, Pair[1] = shelf+storage Quantity.  get the products id that their quantity is less than their minimum quantity.
        List<Pair<Integer,Integer>> needToBuy = new LinkedList<>();
        for (Integer id : InventoryMapper.getProductsIds(branchId)) {
            if (InventoryMapper.getProductMin(branchId,id) > InventoryMapper.getProductQuantity(branchId,id))
                needToBuy.add(new Pair<>(id,InventoryMapper.getProductQuantity(branchId,id)-InventoryMapper.getProductMin(branchId,id)));
        }
        return needToBuy;
    }

    public static  List<Pair<Integer,Integer>> getProductsByCategories(int branchId, List<String> category){  //Integer = productId
        List<Pair<Integer,Integer>> prodectList = new LinkedList<>();
        for(Integer id : InventoryMapper.getProductsIds(branchId)){
            boolean found = false;
            List<String> categoryList=InventoryMapper.getProductCategories(branchId,id);
            for (int i = 0; i<category.size() & !found; i++) {
                if (categoryList.contains(category.get(i))) {
                    prodectList.add(new Pair<>(id, InventoryMapper.getProductQuantity(branchId, id)));
                    found = true;
                }
            }
        }
        return prodectList;
    }

    public static  List<Pair<Integer,Integer>> ExpiredProducts(int branchId){  //Integer = productId
        List<Pair<Integer,Integer>> expiredProducts = new LinkedList<>();
        for(Integer id : InventoryMapper.getExpiredIds(branchId)){
                expiredProducts.add(new Pair<>(id,InventoryMapper.getExpiredQuantity(branchId,id)));
        }
        return expiredProducts;
    }
    public static  String setSalePrice(int branchId, int id, Double price) {
        if(InventoryMapper.setSalePrice(branchId,id,price)) {
            return "product " + id + "- price changed to: " + price;
        }
        return "could not set sale price to product "+id;
    }
    public static  String setPriceByCategory(int branchId, List<String> category, Double price){
        List<Pair<Integer,Integer>> cat = getProductsByCategories(branchId,category);
        for(Pair<Integer,Integer> pair : cat){
            InventoryMapper.setSalePrice(branchId,pair.getKey(),price);
        }
        return "all prices for categories: " + category.toString() + " changed to: " + price;
    }

    public static String setExpired(int branchId, int productId, Integer amount){ //removing a specific amount of expired products from the storage inventory to the expired inventory.
        if(InventoryMapper.isInventoryConteinsProd(branchId,productId)) {
            return "product ID doesnt exist";
        }
        int currentAmount=InventoryMapper.getProductQuantity(branchId,productId);
        if(amount<=currentAmount) {
            if(InventoryMapper.getShelfQunatity(branchId,productId)>amount){
                InventoryMapper.removeAmountFromProductShelf(branchId,productId,amount);
            }
            else{
                int shelfRemove= InventoryMapper.getShelfQunatity(branchId,productId);
                int storageRemove= amount-shelfRemove;
                InventoryMapper.removeAmountFromProductShelf(branchId,productId,shelfRemove);
                InventoryMapper.removeAmountFromProductStorage(branchId,productId,storageRemove);
            }
            if(InventoryMapper.isExpiredContainProduct(branchId,productId)){
                InventoryMapper.updateExpired(branchId,productId,amount);
            }
            else{
                InventoryMapper.addExpired(branchId, productId, amount);
            }
            return "product " + productId + "- quantity of " + amount + " was changed to expired";
        }
        else{
            return "current amount of product "+productId+" less then "+amount;
        }

    }
    public String addProduct(int branchId, int id,int amount, String name, Double costPrice, Double salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place) {
        if(InventoryMapper.isBranchExist(branchId)) {
            if(!InventoryMapper.isInventoryConteinsProd(branchId,id)) {
                if (InventoryMapper.addProduct(branchId, id, name, costPrice, salePrice, expDate, category, manufacturer, minAmount, place)) {
                    InventoryMapper.addNewAmountProductToQuantities(branchId, id, amount);
                    return " product id: " + id + " - added to the inventory";
                }
                return "cant execute the action";
            }
            return "product ID already exist";
        }
        return "Error - branch id does not exist";
    }

    public String removeProduct(int branchId, int id) {
        if (InventoryMapper.isBranchExist(branchId)) {
            if (InventoryMapper.isInventoryConteinsProd(branchId, id)) {
                return InventoryMapper.removeProduct(branchId, id);
            }
            return "product ID doesnt exist";
        }
        return "branch id does not exist";
    }

    public static String addAmountToProduct(int branchId, int id, int amount){
        if (InventoryMapper.isBranchExist(branchId)) {
            if (InventoryMapper.isInventoryConteinsProd(branchId, id)) {
                InventoryMapper.addAmountToProduct(branchId, id, amount);
                return "product id: " + id + " - amount of " + amount + " added to the inventory";
            }
            return "product id does not exist";
        }
        return "branch id does not exist";
    }
    public static String removeAmountFromProduct(int branchId, int id, int amount){
        if (InventoryMapper.isBranchExist(branchId)) {
            if (InventoryMapper.isInventoryConteinsProd(branchId, id)) {
                int shelfQnty = InventoryMapper.getShelfQunatity(branchId, id);   //storage , shelf
                int storageQnty = InventoryMapper.getStorageQunatity(branchId, id);
                if (shelfQnty + storageQnty < amount)
                    return "cant remove - there is less than " + amount + " items from this product in the inventory";
                if (shelfQnty >= amount) {
                    InventoryMapper.removeAmountFromProductShelf(branchId, id, amount);
                } else {
                    InventoryMapper.removeAmountFromProductStorage(branchId, id, amount);
                }
                if (InventoryMapper.getProductQuantity(branchId, id) < InventoryMapper.getProductMin(branchId, id)) { //less than the minimum amount
                    return "product id: " + id + "- amount of " + amount + " was removed from the inventory. (Less than the Minimum amount - Need to buy more from this product)";
                }
                return "product id: " + id + " - amount of " + amount + " was removed from the inventory";
            }
            return "product id does not exist";
        }
        return "branch id does not exist";
    }
    public static String setCategory(int branchId,int id, List<String> category) {
        if (InventoryMapper.isBranchExist(branchId)) {
            if (InventoryMapper.isInventoryConteinsProd(branchId, id)) {
                return InventoryMapper.setCategory(branchId, id, category);
            }
            return "product id does not exist";
        }
        return "branch id does not exist";
    }


    public static String shelfToStorage(int branchId, int id, int amount){
        if (InventoryMapper.isBranchExist(branchId)) {
            if (InventoryMapper.isInventoryConteinsProd(branchId, id)) {
                if(InventoryMapper.shelfToStorage(branchId,id,amount)){
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

    public static String storageToShelf(int branchId,int id, int amount){
        if (InventoryMapper.isBranchExist(branchId)) {
            if (InventoryMapper.isInventoryConteinsProd(branchId, id)) {
                if(InventoryMapper.shelfToStorage(branchId,id,amount)){
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

    public static String getLastCostPrice(int branchId,int id) {
        if (InventoryMapper.isBranchExist(branchId)) {
            if (InventoryMapper.isInventoryConteinsProd(branchId, id)) {
                return Double.toString(InventoryMapper.getCurrentCostPrices(branchId,id));
            }
            return "product id does not exist";
        }
        return "branch id does not exist";
    }

    public static String getLastSalePrice(int branchId,int id){
        if (InventoryMapper.isBranchExist(branchId)) {
            if (InventoryMapper.isInventoryConteinsProd(branchId, id)) {
                return Double.toString(InventoryMapper.getCurrentCostPrices(branchId,id));
            }
            return "product id does not exist";
        }
        return "branch id does not exist";
    }

    public Pair<Integer, List<Double>> SalePricesById(int branchId,int id) {
        return new Pair<>(id,InventoryMapper.getSalePrices(branchId,id));
    }

    public Pair<Integer, List<Double>> CostPricesById(int branchId,int id) {
        return new Pair<>(id,InventoryMapper.getCostPrices(branchId,id));
    }
    /*public String setDefectiveProducts(Integer branchId, Integer prodId, Integer amount) {
        if (InventoryMapper.isBranchExist(branchId)) {
            if (InventoryMapper.isInventoryConteinsProd(branchId, id)) {
                Pair<Integer, Integer> amounts = quantities.get(prodId);
                if (amounts.getKey() + amounts.getValue() < amount) {
                    return "Cant transform - There is Less than " + amount + " items from product number " + prodId + " in the inventory";
                }
                if (amounts.getValue() >= amount) {
                    InventoryMapper.removeAmountFromProductShelf(branchId, prodId, amount);
                    //quantities.put(prodId,new Pair<>(amounts.getKey(),amounts.getValue()-amount));
                } else {
                    InventoryMapper.removeAmountFromProductStorage(branchId, prodId, amount);
                    //quantities.put(prodId,new Pair<>(amounts.getKey()-(amount-amounts.getValue()),0));
                }

                if (!InventoryMapper.updateExpired(branchId, prodId, amount)) {
                    //expired.put(prodId,expired.get(prodId)+amount);
                    InventoryMapper.addExpired(branchId, prodId, amount);
                    //expired.put(prodId,amount);
                }
                if (quantities.get(prodId).getKey() + quantities.get(prodId).getValue() < inventory.get(prodId).getMinAmount()) //less than the minimum amount
                    return "product id: " + prodId + "- amount of " + amount + " set us expired. (Less than the Minimum amount - Need to buy more from this product)";
                return "product id: " + prodId + " - amount of " + amount + " set us expired";
            }
    }*/
    public String addToWeeklyOrder(int branchId ,int day,List<Pair<Integer, Integer>> id_amount) {
        if (InventoryMapper.isBranchExist(branchId)) {
            List<Integer> noOrder=new LinkedList<>();
            String output="";
            InventoryMapper.setWeeklyOrder(branchId,day);
            for(Pair<Integer,Integer> p: id_amount){
                if(InventoryMapper.isInventoryConteinsProd(branchId,p.getKey())) {
                    InventoryMapper.AddToWeeklyOrder(branchId,p.getKey(), p.getValue());
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
    public String removeFromWeeklyOrder(int branchId, List<Integer> ids){
        if (InventoryMapper.isBranchExist(branchId)) {
            for (Integer id : ids) {
                if (InventoryMapper.isWeeklyContainProd(branchId,id)) {
                    InventoryMapper.removeFromWeeklyOrder(branchId,id);
                }
            }
            return "Weekly order was update for branch: " + branchId + "\n";
        }
        return "branch id does not exist";
    }

    public List<Pair<Integer, Integer>> getWeeklyOrder(int branchId) {
        if (InventoryMapper.isBranchExist(branchId)) {
            return InventoryMapper.getWeeklyOrder(branchId);
            /*List<Pair<Integer, Integer>> output = new LinkedList<>();
            for (Integer id : WeeklyOrder.keySet()) {
                output.add(new Pair<>(id, WeeklyOrder.get(id)));
            }
            return output;*/
        }
        return null;
    }

    public static boolean conteinsProduct(int branchId,Integer key) {
        return InventoryMapper.isInventoryConteinsProd(branchId,key);
    }

    //Managers
    public static String addInventoryManager(String username, String password){ //TODO remove the static, Inventory controller will hold instance of Singletone Inventory.
        return InventoryMapper.addInventoryManager(username,password);
    }
    public static String addGlobalManager(String username, String password){  //TODO remove the static, Inventory controller will hold instance of Singletone Inventory.
        return InventoryMapper.addGlobalManager(username,password);
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
