package LogicLayer;

import java.util.Map;

public class Items {

    private static Map<Integer, String> items;

    public static boolean addItem(Integer id, String name ){
        if(items.containsKey(id)){
            return false;
        }
        items.put(id,name);
        return true;
    }

    public static String getName(Integer id){
        if(!items.containsKey(id)){
            return null;
        }
        return items.get(id);
    }

    public static void PrintAllItems() {
        for ( Integer id: items.keySet()) {
            System.out.println("Product id: "+id+", name: "+items.get(id));
        }
    }



}
