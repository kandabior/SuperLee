import javafx.util.Pair;
import sun.awt.image.ImageWatched;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public List<Pair<Integer,Integer>> getQuantity(){ // Pair[0] = productId, Pair[1] = StorageQuantity
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
    public int getShelfQntyByProdId(int prodId){
        if(!quantities.containsKey(prodId))
            return -1;
        return quantities.get(prodId).getValue();
    }
    public int getStorageQntyByProdId(int prodId){
        if(!quantities.containsKey(prodId))
            return -1;
        return quantities.get(prodId).getKey();
    }

    public List<Pair<Integer,Integer>> NeedToBuyProducts() {   //Pair[0] = productId, Pair[1] = shelf+storage Quantity.  get the products id that their quantity is equal or less than their minimum quantity.
        List<Pair<Integer,Integer>> needToBuy = new LinkedList<>();
        for (Integer id : inventory.keySet()) {
            if (inventory.get(id).getMinAmount() >= quantities.get(id).getKey() + quantities.get(id).getValue())
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
                    categoryList.add(new Pair<>(id,quantities.get(i).getKey()+quantities.get(i).getValue()));
                    found = true;
                }
        }
        return categoryList;
    }

    public List<Pair<Integer,Integer>> ExpiredProducts(){  //Integer = productId
        List<Pair<Integer,Integer>> expiredProducts = new LinkedList<>();
        for(Integer id : expired.keySet()){
                expiredProducts.add(new Pair(id,expired.get(id)));
        }
        return expiredProducts;
    }

    public boolean setPriceById(int id, int price){
        if(!inventory.containsKey(id))
            return false;
        inventory.get(id).setSalePrice(price);
        return true;
    }

    public boolean setPriceByCategory(List<String> category, int price){
        List<Pair<Integer,Integer>> cat = getProductsByCategories(category);
        for(Pair<Integer,Integer> pair : cat){
            inventory.get(pair.getKey()).setSalePrice(price);
        }
        return true;
    }
    public boolean setExpired(int productId, Integer amount){ //removing a specific amount of expired products from the storage inventory to the expired inventory.
        if(!inventory.containsKey(productId))
            return false;
        quantities.put(productId, new Pair(quantities.get(productId).getKey() - amount,quantities.get(productId).getValue()));
        expired.put(productId, expired.get(productId)+amount);
        return true;
    }

    public boolean addProduct(int id,int amount, String name, int costPrice, int salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place) {
        if(!inventory.containsKey(id)) {
            inventory.put(id, new Product(id, name, costPrice, salePrice, expDate, category, manufacturer, minAmount, place));
            quantities.put(id, new Pair(0,amount));
        }
        else {
            quantities.put(id, new Pair(quantities.get(id).getKey(), quantities.get(id).getValue() + amount));
        }
        return true;
    }

    public boolean removeProduct(int id, int amount) {
        if(!inventory.containsKey(id))
            return false;
        quantities.put(id, new Pair(quantities.get(id).getKey(),quantities.get(id).getValue() - amount));
        return true;
    }

    public boolean setSalePrice(int id, int price) {
        if(!inventory.containsKey(id))
            return false;
        inventory.get(id).setSalePrice(price);
        return true;
    }

    public boolean setCategory(int id, List<String> category) {
        if(!inventory.containsKey(id))
            return false;
        inventory.get(id).setCategory(category);
        return true;
    }
}
