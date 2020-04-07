public class Printer {
    private static Printer instance;

    private Printer(){
    }

    public static Printer getPrinter(){
        if(instance==null){
            instance=new Printer();
        }
        return instance;
    }

    public String printStock(){
        return null;
    }
}
