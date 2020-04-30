package LogicLayer;

import java.util.List;

public class Item {
    private int id;
    private String name;
    private static List<Item> itemList;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return this.id; }

    public String getName() { return this.name; }

}
