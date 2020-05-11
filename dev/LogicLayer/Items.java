package LogicLayer;

import DataAccessLayer.ItemMapper;

import java.util.HashMap;
import java.util.Map;

public class Items {

    private static Map<Integer, String> items=new HashMap<>();

    public static boolean addItem(Integer id, String name ){
        if(items.containsKey(id)){
            return false;
        }
        items.put(id,name);
        return true;
    }

    public static String getName(Integer id) {
        if (!ItemMapper.checkIfItemExist(id))
            return null;
        return ItemMapper.getName(id);

//        if(!items.containsKey(id)){
//            return null;
//        }
//        return items.get(id);
    }

    public static void PrintAllItems() {
        for ( Integer id: items.keySet()) {
            System.out.println("Product id: "+id+", name: "+items.get(id));
        }
    }


    public static boolean checkIfItemExist(int itemId) {
        return ItemMapper.checkIfItemExist(itemId);
    }
}
