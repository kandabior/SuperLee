public class Printer {

    private static Printer instance;

    private Inventory inventory;

    private Printer(Inventory inventory){
        this.inventory=inventory;
    }

    public static Printer getPrinter(Inventory inventory){
        if(instance==null){
            instance=new Printer(inventory);
        }
        return instance;
    }

    public String printStock(){
        return null;
    }
}
