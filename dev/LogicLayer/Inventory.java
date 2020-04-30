package LogicLayer;

import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;

public class Inventory {
    private static Inventory instance;

    private Map<Integer, Product> inventory;
    private Map<Integer, Pair<Integer,Integer>> quantities; //Pair[0] = storage , Pair[1] = shelf
    private Map<Integer, Integer> expired; //Id, amount

    private Inventory() {
        inventory = new HashMap<>();
        quantities = new HashMap<>();
        expired = new HashMap<>();
    }
    
    public static Inventory getInventory() {
        if (instance == null)
            instance = new Inventory();
        return instance;
    }
    public static String getProdactName(int prodId) {
        if(!instance.inventory.containsKey(prodId))
            return "product ID doesnt exist";
        return instance.inventory.get(prodId).getName();
    }
    public static int getProductMin(int prodId) {
        if(!instance.inventory.containsKey(prodId))
            return -1;
        return instance.inventory.get(prodId).getMinAmount();
    }

    public static int getAmount(Integer key) {
        if(!instance.inventory.containsKey(key))
            return -1;
        return instance.quantities.get(key).getKey()+instance.quantities.get(key).getValue();
    }

    public  String mannageOrders(Map<Integer,Pair<Integer,Integer>> orders) {
        for(Integer prodId: orders.keySet()){
            addAmountToProduct(prodId,orders.get(prodId).getKey());
            double newPrice= orders.get(prodId).getValue()/orders.get(prodId).getKey();
            double price= inventory.get(prodId).getCostPrice();
            if(price!=newPrice){
                setCostPrice(prodId,newPrice);
            }
        }
        return "Order Completed Successfully";
    }

    private void setCostPrice(Integer prodId, double newPrice) {
        Product product= inventory.get(prodId);
        product.setCostPrice(newPrice);
    }

    public List<Pair<Integer,Integer>> getQuantity(){ // Pair[0] = productId, Pair[1] = storage + shelf Quantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : quantities.keySet()){
            Pair<Integer,Integer> p = new Pair<>(id,quantities.get(id).getKey() + quantities.get(id).getValue());
            qnty.add(p);
        }
        return qnty;
    }
    public List<Pair<Integer,Integer>> getStorageQuantity(){ // Pair[0] = productId, Pair[1] = StorageQuantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : quantities.keySet()){
            Pair<Integer,Integer> p = new Pair<>(id,quantities.get(id).getKey());
            qnty.add(p);
        }
        return qnty;
    }
    public List<Pair<Integer,Integer>> getShelfQuantity(){ // Pair[0] = productId, Pair[1] = ShelfQuantity
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : quantities.keySet()){
            Pair<Integer,Integer> p = new Pair<>(id,quantities.get(id).getValue());
            qnty.add(p);
        }
        return qnty;
    }

    public List<Pair<Integer,Integer>> NeedToBuyProducts() {   //Pair[0] = productId, Pair[1] = shelf+storage Quantity.  get the products id that their quantity is equal or less than their minimum quantity.
        List<Pair<Integer,Integer>> needToBuy = new LinkedList<>();
        for (Integer id : inventory.keySet()) {
            if (inventory.get(id).getMinAmount() > quantities.get(id).getKey() + quantities.get(id).getValue())
                needToBuy.add(new Pair<>(id,quantities.get(id).getKey() + quantities.get(id).getValue()));
        }

        return needToBuy;
    }
    public List<Pair<Integer,Integer>> getProductsByCategories(List<String> category){  //Integer = productId
        List<Pair<Integer,Integer>> categoryList = new LinkedList<>();
        for(Integer id : inventory.keySet()){
            boolean found = false;
            for (int i = 0; i<category.size() & !found; i++)
                if(inventory.get(id).getCategory().contains(category.get(i))) {
                    categoryList.add(new Pair<>(id,quantities.get(id).getKey()+quantities.get(id).getValue()));
                    found = true;
                }
        }
        return categoryList;
    }
    public List<Pair<Integer,Integer>> ExpiredProducts(){  //Integer = productId
        List<Pair<Integer,Integer>> expiredProducts = new LinkedList<>();
        for(Integer id : expired.keySet()){
                expiredProducts.add(new Pair<>(id,expired.get(id)));
        }
        return expiredProducts;
    }
    public String setSalePrice(int id, Double price) {
        if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        inventory.get(id).setSalePrice(price);
        return "product " + id + "- price changed to: " + price;
    }
    public String setPriceByCategory(List<String> category, Double price){
        List<Pair<Integer,Integer>> cat = getProductsByCategories(category);
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
    public String addProduct(int id,int amount, String name, Double costPrice, Double salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place) {
        if(inventory.containsKey(id))
            return "product ID already exist";
        inventory.put(id, new Product(id, name, costPrice, salePrice, expDate, category, manufacturer, minAmount, place));
        quantities.put(id, new Pair<>(0,amount));
        return " product id: " + id + " - added to the inventory";
    }
    public String removeProduct(int id) {
        if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        inventory.remove(id);
        quantities.remove(id);
        expired.remove(id);
        return "product id: " + id + " was removed from the inventory";
    }
    public String addAmountToProduct(int id, int amount){
        if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        quantities.put(id, new Pair<>(quantities.get(id).getKey(), quantities.get(id).getValue() + amount));
        return "product id: " + id + " - amount of " + amount + " added to the inventory";
    }
    public String removeAmountFromProduct(int id, int amount){
        if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        Pair<Integer,Integer> amounts= quantities.get(id);
        if(amounts.getKey() + amounts.getValue() < amount)
            return "cant remove - there is less than " + amount + " items from this product in the inventory";
        if(amounts.getValue()>=amount){
            quantities.put(id,new Pair<>(amounts.getKey(),amounts.getValue()-amount));
        }
        else {
            quantities.put(id,new Pair<>(amounts.getKey()-(amount-amounts.getValue()),0));
        }
        if(quantities.get(id).getKey() + quantities.get(id).getValue() < inventory.get(id).getMinAmount()) //less than the minimum amount
            return "product id: " + id + "- amount of " + amount + " was removed from the inventory. (Less than the Minimum amount - Need to buy more from this product)";
        return "product id: " + id + " - amount of " + amount + " was removed from the inventory";
    }
    public String setCategory(int id, List<String> category) {
        if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        inventory.get(id).setCategory(category);
        return "product id: " + id + "- category changed to : " + category.toString();
    }
    public String shelfToStorage(int id, int amount){
        if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        if(quantities.get(id).getValue() < amount)
            return "Cant transform -There is Less than " + amount + " items from product number " + id + " in the shelf";
        quantities.put(id, new Pair<>(quantities.get(id).getKey() + amount, quantities.get(id).getValue() - amount));
        return "product id: " + id + " - amount of " + amount + " removed from the shelf to the Storage";
    }
    public String storageToShelf(int id, int amount){
        if(!inventory.containsKey(id))
            return "product ID doesnt exist";
        if(quantities.get(id).getKey() < amount)
            return "Cant transform - There is Less than " + amount + " items from product number " + id + " in the storage";
        quantities.put(id, new Pair<>(quantities.get(id).getKey() - amount, quantities.get(id).getValue() + amount));
        return "product id: " + id + " - amount of " + amount + " removed from the storage to the shelf";
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

    public Pair<Integer, List<Double>> SalePricesById(Integer id) {
        return new Pair<>(id,inventory.get(id).getLastSalePrice());
    }

    public Pair<Integer, List<Double>> CostPricesById(Integer id) {
        return new Pair<>(id,inventory.get(id).getLastCostPrice());
    }

    public String setDefectiveProducts(Integer prodId, Integer amount) {
        if(!inventory.containsKey(prodId)){
            return "product doesnt exist";
        }
        Pair<Integer,Integer> amounts=quantities.get(prodId);
        if(amounts.getKey()+amounts.getValue()<amount){
            return "Cant transform - There is Less than " + amount + " items from product number " + prodId + " in the inventory";
        }
        if(amounts.getValue()>=amount){
            quantities.put(prodId,new Pair<>(amounts.getKey(),amounts.getValue()-amount));
        }
        else {
            quantities.put(prodId,new Pair<>(amounts.getKey()-(amount-amounts.getValue()),0));
        }
        if(expired.containsKey(prodId)){
            expired.put(prodId,expired.get(prodId)+amount);
        }
        else{
            expired.put(prodId,amount);
        }
        if(quantities.get(prodId).getKey() + quantities.get(prodId).getValue() < inventory.get(prodId).getMinAmount()) //less than the minimum amount
            return "product id: " + prodId + "- amount of " + amount + " set us expired. (Less than the Minimum amount - Need to buy more from this product)";
        return "product id: " + prodId + " - amount of " + amount + " set us expired";
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
