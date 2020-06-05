package src.BusinessLayer.Suppliers;

import src.DataAccessLayer.Inventory.ItemMapper;
import javafx.util.Pair;
import java.util.List;

public class Items {

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
