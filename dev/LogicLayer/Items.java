package LogicLayer;

import DataAccessLayer.ItemMapper;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Items {//check

    //private static Map<Integer, String> items=new HashMap<>();
    private static ItemMapper itemMapper=new ItemMapper();



    public static boolean addItem(Integer id, String name ){
        if(itemMapper.checkIfItemExist(id)){
            return false;
        }
        return itemMapper.addItem(id,name);
    }

    public String getName(Integer id) {
        if (!this.itemMapper.checkIfItemExist(id))
            return null;
        return this.itemMapper.getName(id);
//        if(!items.containsKey(id)){
//            return null;
//        }
//        return items.get(id);
    }

    public static void PrintAllItems() {
        List<Pair<Integer,String>> items= itemMapper.getAllItems();
        for ( Pair pair: items) {
            System.out.println("Product id: "+pair.getKey()+", name: "+pair.getValue());
        }
    }


    public boolean checkIfItemExist(int itemId) {
        return this.itemMapper.checkIfItemExist(itemId);
    }
}
