public class Item {
    private int id;
    private String name;
    private String description;

    public Item(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() { return this.id; }

    public String getName() { return this.name; }

    public String getDescription() { return this.description; }
}
