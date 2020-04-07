import javafx.util.Pair;
import sun.awt.image.ImageWatched;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Inventory {
    private static Inventory instance;
    private Map<Integer, Pair<Product, Pair<Integer,Integer>>> storage; //Map<Integer = productID>  , in the second Pair : pair[0] = storageQnty, pair[1] = shelfQnty

    private Inventory() {
        storage = new HashMap<>();
    }
    public static Inventory getInventory() {
        if (instance == null)
            instance = new Inventory();
        return instance;
    }

    public Map<Integer, Pair<Product, Pair<Integer,Integer>>> getStorage() {
        return storage;
    }

    public List<Pair<Integer,Integer>> getQuantity(){ // Pair[0] = productId, Pair[1] = totalQuantity (storage+shelf)
        List<Pair<Integer,Integer>> qnty = new LinkedList<>();
        for(Integer id : storage.keySet()){
            Pair<Integer,Integer> p = new Pair<>(id,storage.get(id).getValue().getKey() +storage.get(id).getValue().getValue() );
            qnty.add(p);
        }
        return qnty;
    }

    public List<Pair<Integer,Integer>> NeedToBuyProducts() {   //get the products id that their quantity is equal or less than their minimum quantity. Pair[0] = productId, Pair[1] = shelf+storage Quantity
        List<Pair<Integer,Integer>> needToBuy = new LinkedList<>();
        for (Integer id : storage.keySet()) {
            if (storage.get(id).getValue().getKey()+storage.get(id).getValue().getKey() <= storage.get(id).getKey().getMinAmount())
                needToBuy.add(new Pair<>(id,storage.get(id).getValue().getKey()+storage.get(id).getValue().getKey()));
        }
        return needToBuy;
    }

    public List<Integer> getProductsByCategories(List<String> category){  //Integer = productId
        List<Integer> categoryList = new LinkedList<>();
        for(Integer id : storage.keySet()){
            boolean found = false;
            for (int i = 0; i<category.size() & !found; i++)
                if(storage.get(id).getKey().getCategory().contains(category.get(i))) {
                    categoryList.add(id);
                    found = true;
                }
        }
        return categoryList;
    }

    public List<Integer> ExpiredProducts(){  //Integer = productId
        List<Integer> expiredProducts = new LinkedList<>();
        for(Integer id : storage.keySet()){
            if(storage.get(id).getKey().isExpirated())
                expiredProducts.add(id);
        }
        return expiredProducts;
    }

    public void setPriceById(int id, int price){
        storage.get(id).getKey().setSalePrice(price);
    }

    public void setPriceByCategory(String category, int price){
        for(Integer id : storage.keySet()){
            if(storage.get(id).getKey().getCategory().contains(category))
                storage.get(id).getKey().setSalePrice(price);
        }
    }

}
