import javafx.util.Pair;
import sun.awt.image.ImageWatched;

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


    public List<Pair<Integer,Integer>> getQuantity(){ // Pair[0] = productId, Pair[1] = totalQuantity (storage+shelf)
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : quantities.keySet()){
            Pair<Integer,Integer> p = new Pair<>(id,quantities.get(id).getKey() + quantities.get(id).getValue());
            qnty.add(p);
        }
        return qnty;
    }

    public List<Pair<Integer,Integer>> NeedToBuyProducts() {   //Pair[0] = productId, Pair[1] = shelf+storage Quantity.  get the products id that their quantity is equal or less than their minimum quantity.
        List<Pair<Integer,Integer>> needToBuy = new LinkedList<>();
        for (Integer id : inventory.keySet()) {
            if (inventory.get(id).getMinAmount() >= quantities.get(id).getKey() + quantities.get(id).getValue())
                needToBuy.add(new Pair<>(id,quantities.get(id).getKey() + quantities.get(id).getValue()));
        }
        return needToBuy;
    }

    public List<Integer> getProductsByCategories(List<String> category){  //Integer = productId
        List<Integer> categoryList = new LinkedList<>();
        for(Integer id : inventory.keySet()){
            boolean found = false;
            for (int i = 0; i<category.size() & !found; i++)
                if(inventory.get(id).getCategory().contains(category.get(i))) {
                    categoryList.add(id);
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

    public void setPriceById(int id, int price){
        inventory.get(id).setSalePrice(price);
    }

    public void setPriceByCategory(List<String> category, int price){
        List<Integer> cat = getProductsByCategories(category);
        for(Integer id : cat){
            inventory.get(id).setSalePrice(price);
        }
    }
    public void setExpired(int productId, Integer amount){ //removing a specific amount of expired products from the storage inventory to the expired inventory.
        quantities.put(productId, new Pair(quantities.get(productId).getKey() - amount,quantities.get(productId).getValue()));
        expired.put(productId, expired.get(productId)+amount);
    }

}
