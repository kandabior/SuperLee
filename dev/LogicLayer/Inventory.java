package LogicLayer;

import DataAccessLayer.InventoryMapper;
import javafx.util.Pair;
import java.time.LocalDate;
import java.util.*;

public class Inventory {

    private static Inventory instance;

    //private Integer branchId;
    //private Map<Integer, Product> inventory;
    //private Map<Integer, Pair<Integer,Integer>> quantities; //Pair[0] = storage , Pair[1] = shelf
    //private Map<Integer, Integer> expired; //Id, amount
    //private Integer dayForWeeklyOrder=1;
    //private Map<Integer,Integer> WeeklyOrder;

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
    }
    /*public static String getProdactName(int branchId,int prodId) {
        if(!instances.containsKey(branchId)){
            return "branch id does not exist";
        }
        Inventory inventory=instances.get(branchId);
        if(!inventory.inventory.containsKey(prodId))
            return "product ID doesnt exist";
        return inventory.inventory.get(prodId).getName();
    }*/
    public static String getProdactName(int branchId,int prodId){
        return InventoryMapper.getProductName(branchId,prodId);
    }
    public static int getProductMin(int branchId,int prodId) {
       /* if(!instances.containsKey(branchId)){
            return -1;
        }
        Inventory inventory=instances.get(branchId);
        if(!inventory.inventory.containsKey(prodId))
            return -1;*/
        return InventoryMapper.getProductMin(branchId, prodId);
        //return inventory.inventory.get(prodId).getMinAmount();
    }
    public static int getAmount(int branchId,Integer prodId) {
        /*if(!instances.containsKey(branchId)){
            return -1;
        }
        Inventory inventory=instances.get(branchId);
        if(!inventory.inventory.containsKey(prodId))
            return -1;*/
        return InventoryMapper.getProductQuantity(branchId, prodId);
        //return inventory.quantities.get(prodId).getKey()+inventory.quantities.get(prodId).getValue();
    }

  /*  public static String CreateNewInventory(Integer branchId) {
        if(instances.containsKey(branchId)){
            return "branch id is already exist";
        }
        instances.put(branchId,new Inventory(branchId));
        return "Branch number: "+branchId+" has successfully initiated.";
    }*/
    public static String CreateNewInventory(Integer branchId) {
         if(InventoryMapper.CreateNewInventory(branchId))
             return "Inventory number: " + branchId + " created successfully";
         return "Can't create this inventory";
  }


    public static List<Integer> getBranchIdsToWeeklyOrders(int dayOfTheWeek) {
        List<Integer> output = new LinkedList<>();
        output = InventoryMapper.getBranchIdsToWeeklyOrders(dayOfTheWeek);
        /*for(Inventory inventory: instances.values()){
            if(inventory.dayForWeeklyOrder==dayOfTheWeek){
                output.add(inventory.branchId);
            }
        }*/
        return output;
    }
    public  String mannageOrders(int branchId, Map<Integer,Pair<Integer,Double>> orders) {
        for(Integer prodId: orders.keySet()){
            addAmountToProduct(branchId, prodId,orders.get(prodId).getKey());
            int amount=orders.get(prodId).getKey();
            double totalPrice= orders.get(prodId).getValue();
            double newPrice= totalPrice/amount;
            double price= inventory.get(prodId).getCostPrice();
            if(price!=newPrice){
                setCostPrice(prodId,newPrice);
            }
        }
        return "Order Completed Successfully for branch: "+branchId;
    }
    private void setCostPrice(Integer prodId, double newPrice) {
        Product product= inventory.get(prodId);
        product.setCostPrice(newPrice);
    }
    public List<Pair<Integer,Integer>> getQuantity(int branchId){ // Pair[0] = productId, Pair[1] = storage + shelf Quantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : quantities.keySet()){
            Pair<Integer,Integer> p = new Pair<>(id,InventoryMapper.getProductQuantity(branchId,id));
            qnty.add(p);
        }
        return qnty;
    }
    public List<Pair<Integer,Integer>> getStorageQuantity(int branchId){ // Pair[0] = productId, Pair[1] = StorageQuantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : quantities.keySet()){
            Pair<Integer,Integer> p = new Pair<>(id,InventoryMapper.getStorageQunatity(branchId,id));
            qnty.add(p);
        }
        return qnty;
    }
    public List<Pair<Integer,Integer>> getShelfQuantity(int branchId){ // Pair[0] = productId, Pair[1] = ShelfQuantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : quantities.keySet()){
            Pair<Integer,Integer> p = new Pair<>(id,InventoryMapper.getShelfQunatity(branchId,id));
            qnty.add(p);
        }
        return qnty;
    }
    public List<Pair<Integer,Integer>> NeedToBuyProducts(int branchId) {   //Pair[0] = productId, Pair[1] = shelf+storage Quantity.  get the products id that their quantity is less than their minimum quantity.
        List<Pair<Integer,Integer>> needToBuy = new LinkedList<>();
        for (Integer id : inventory.keySet()) {
            if (InventoryMapper.getProductMin(branchId,id) > InventoryMapper.getProductQuantity(branchId,id))
                needToBuy.add(new Pair<>(id,InventoryMapper.getProductQuantity(branchId,id)-InventoryMapper.getProductMin(branchId,id)));
        }

        return needToBuy;
    }
    public List<Pair<Integer,Integer>> getProductsByCategories(int branchId, List<String> category){  //Integer = productId
        List<Pair<Integer,Integer>> categoryList = new LinkedList<>();
        for(Integer id : inventory.keySet()){
            boolean found = false;
            for (int i = 0; i<category.size() & !found; i++)
                if(inventory.get(id).getCategory().contains(category.get(i))) {
                    categoryList.add(new Pair<>(id,InventoryMapper.getProductQuantity(branchId,id)));
                    found = true;
                }
        }
        return categoryList;
    }
    public List<Pair<Integer,Integer>> ExpiredProducts(int branchId){  //Integer = productId
        List<Pair<Integer,Integer>> expiredProducts = new LinkedList<>();
        for(Integer id : expired.keySet()){
                expiredProducts.add(new Pair<>(id,InventoryMapper.getExpiredQuantity(branchId,id)));
        }
        return expiredProducts;
    }
    public String setSalePrice(int branchId, int id, Double price) {
        return InventoryMapper.setSalePrice(branchId,id,price);
        //inventory.get(id).setSalePrice(price);
        //return "product " + id + "- price changed to: " + price;
    }
    public String setPriceByCategory(int branchId, List<String> category, Double price){
        List<Pair<Integer,Integer>> cat = getProductsByCategories(branchId,category);
        for(Pair<Integer,Integer> pair : cat){
            inventory.get(pair.getKey()).setSalePrice(price);
        }
        return "categories: " + category.toString() + "price changed to: " + price;
    }
    public String setExpired(int productId, Integer amount){ //removing a specific amount of expired products from the storage inventory to the expired inventory.
        if(!inventory.containsKey(productId))
            return "product ID doesnt exist";
        quantities.put(productId, new Pair<>(quantities.get(productId).getKey() - amount,quantities.get(productId).getValue()));
        expired.put(productId, expired.get(productId)+amount);
        return "product " + productId + "- quantity of " + amount + " was changed to expired";
    }
    public String addProduct(int branchId, int id,int amount, String name, Double costPrice, Double salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place) {
        if(InventoryMapper.addProduct(branchId,id, name, costPrice, salePrice, expDate,  category,  manufacturer, minAmount, place)) {
            InventoryMapper.addNewAmountProductToQuantities(branchId, id, amount);
            return " product id: " + id + " - added to the inventory";
        }
        return "Error - cant add the product";
      /*if(inventory.containsKey(id))
            return "product ID already exist";
        inventory.put(id, new Product(id, name, costPrice, salePrice, expDate, category, manufacturer, minAmount, place));
        quantities.put(id, new Pair<>(0,amount));
        return " product id: " + id + " - added to the inventory";*/
    }
    public String removeProduct(int branchId, int id) {
      return InventoryMapper.removeProduct(branchId,id);
        /*if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        inventory.remove(id);
        quantities.remove(id);
        expired.remove(id);
        return "product id: " + id + " was removed from the inventory";*/
    }
    public String addAmountToProduct(int branchId, int id, int amount){
        if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        InventoryMapper.addAmountToProduct(branchId,id,amount);
        quantities.put(id, new Pair<>(quantities.get(id).getKey(), quantities.get(id).getValue() + amount));
        return "product id: " + id + " - amount of " + amount + " added to the inventory";
    }
    public String removeAmountFromProduct(int branchId, int id, int amount){
        int shelfQnty = InventoryMapper.getShelfQunatity(branchId,id);   //storage , shelf
        int storageQnty = InventoryMapper.getStorageQunatity(branchId,id);
        if(shelfQnty + storageQnty < amount)
            return "cant remove - there is less than " + amount + " items from this product in the inventory";
        if(shelfQnty>=amount){
            return InventoryMapper.removeAmountFromProductShelf(branchId,id,amount);
            //quantities.put(id,new Pair<>(amounts.getKey(),amounts.getValue()-amount));
        }
        else {
            return InventoryMapper.removeAmountFromProductStorage(branchId,id,amount);
            //quantities.put(id,new Pair<>(amounts.getKey()-(amount-amounts.getValue()),0));
        }
        //if(quantities.get(id).getKey() + quantities.get(id).getValue() < inventory.get(id).getMinAmount()) //less than the minimum amount
            //return "product id: " + id + "- amount of " + amount + " was removed from the inventory. (Less than the Minimum amount - Need to buy more from this product)";
        //return "product id: " + id + " - amount of " + amount + " was removed from the inventory";
    }
    public String setCategory(int branchId,int id, List<String> category) {
       /* if(!inventory.containsKey(id))
            return "product ID doesnt exist";*/
        return InventoryMapper.setCategory(branchId,id,category);
        /*inventory.get(id).setCategory(category);
        return "product id: " + id + "- category changed to : " + category.toString();*/
    }

    public String shelfToStorage(int branchId, int id, int amount){
       /* if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        if(quantities.get(id).getValue() < amount)
            return "Cant transform -There is Less than " + amount + " items from product number " + id + " in the shelf";*/
        return InventoryMapper.shelfToStorage(branchId,id,amount);
        /*quantities.put(id, new Pair<>(quantities.get(id).getKey() + amount, quantities.get(id).getValue() - amount));
        return "product id: " + id + " - amount of " + amount + " removed from the shelf to the Storage";*/
    }
    public String storageToShelf(int branchId,int id, int amount){
       /* if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        if(quantities.get(id).getKey() < amount)
            return "Cant transform - There is Less than " + amount + " items from product number " + id + " in the storage";*/
        return InventoryMapper.storageToShelf(branchId,id,amount);
        /*quantities.put(id, new Pair<>(quantities.get(id).getKey() - amount, quantities.get(id).getValue() + amount));
        return "product id: " + id + " - amount of " + amount + " removed from the storage to the shelf";*/
    }
    public String getLastCostPrice(int id){
        if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        return inventory.get(id).getLastCostPrice().toString();
    }
    public String getLastSalePrice(int id){
        if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        return inventory.get(id).getLastSalePrice().toString();
    }
    public Pair<Integer, List<Double>> SalePricesById(int branchId,int id) {
        return new Pair<>(id,InventoryMapper.getSalePrices(branchId,id));
    }
    public Pair<Integer, List<Double>> CostPricesById(int branchId,int id) {
        return new Pair<>(id,InventoryMapper.getCostPrices(branchId,id));
    }
    public String setDefectiveProducts(Integer branchId, Integer prodId, Integer amount) {
        if(!inventory.containsKey(prodId)){
            return "product doesnt exist";
        }
        Pair<Integer,Integer> amounts=quantities.get(prodId);
        if(amounts.getKey()+amounts.getValue()<amount){
            return "Cant transform - There is Less than " + amount + " items from product number " + prodId + " in the inventory";
        }
        if(amounts.getValue()>=amount){
            InventoryMapper.removeAmountFromProductShelf(branchId,prodId,amount);
            //quantities.put(prodId,new Pair<>(amounts.getKey(),amounts.getValue()-amount));
        }
        else {
            InventoryMapper.removeAmountFromProductStorage(branchId,prodId,amount);
            //quantities.put(prodId,new Pair<>(amounts.getKey()-(amount-amounts.getValue()),0));
        }

        if(!InventoryMapper.updateExpired(branchId,prodId,amount)) {
            //expired.put(prodId,expired.get(prodId)+amount);
            InventoryMapper.addExpired(branchId,prodId,amount);
            //expired.put(prodId,amount);
        }
        if(quantities.get(prodId).getKey() + quantities.get(prodId).getValue() < inventory.get(prodId).getMinAmount()) //less than the minimum amount
            return "product id: " + prodId + "- amount of " + amount + " set us expired. (Less than the Minimum amount - Need to buy more from this product)";
        return "product id: " + prodId + " - amount of " + amount + " set us expired";
    }
    public String addToWeeklyOrder(int branchId ,int day,List<Pair<Integer, Integer>> id_amount) {
        List<Integer> noOrder=new LinkedList<>();
        String output="";
        dayForWeeklyOrder=day;
        for(Pair<Integer,Integer> p: id_amount){
            if(inventory.containsKey(p.getKey())) {
                WeeklyOrder.put(p.getKey(), p.getValue());
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
    public String removeFromWeeklyOrder(int branchId, List<Integer> ids){
        for(Integer id: ids){
            if(WeeklyOrder.containsKey(id)){
                WeeklyOrder.remove(id);
            }
        }
        return "Weekly order was update for branch: "+branchId+"\n";
    }
    public List<Pair<Integer, Integer>> getWeeklyOrder(int branchId) {
        List<Pair<Integer, Integer>> output=new LinkedList<>();
        for(Integer id: WeeklyOrder.keySet()){
            output.add(new Pair<>(id,WeeklyOrder.get(id)));
        }
        return output;
    }
    public boolean conteinsProduct(Integer key) {
        return inventory.containsKey(key);
    }

    //Managers
    public static String addInventoryManager(String username, String password){ //TODO remove the static, Inventory controller will hold instance of Singletone Inventory.
        return InventoryMapper.addInventoryManager(username,password);
    }
    public static String addGlobalManager(String username, String password){  //TODO remove the static, Inventory controller will hold instance of Singletone Inventory.
        return InventoryMapper.addGlobalManager(username,password);
    }



    //Tests Methods
    public Pair<Integer,Pair<Integer,Integer>> getQuantityById(Integer id) {
        return new Pair<>(id,quantities.get(id));
    }
    public Pair<Integer, Integer> getQuantityEXPById(Integer id) {
        return new Pair<>(id,expired.get(id));
    }
    public Double getProdSalePrice(int id) {
        return inventory.get(id).getSalePrice();
    }
}
