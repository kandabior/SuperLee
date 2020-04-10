
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[]args) {
        initiateSystem();
        mainLoop();

    }

    private static void mainLoop() {

    }

    private static void initiateSystem(){
        Register register = new Register();

        register.addGlobalManager("Erez", "1234");
        register.addGlobalManager("Or", "1234");
        register.addInventoryManager("yossi", "1234");
        register.addInventoryManager("david", "1234");


        List<String> cat1 = new LinkedList<>();
        cat1.add("shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        System.out.println(register.addProduct("Erez", "1234",1, 20, "Corn",5,
                8, date1,cat1,"Rami Levi", 50, "area1"));

        List<String> cat2 = new LinkedList<>();
        cat2.add("Halavi");
        cat2.add("Cartons");
        LocalDate date2 = LocalDate.of(20, 8,31);
        System.out.println(register.addProduct("Or", "1234",2, 30, "Milk",3,
                6, date2,cat2,"Tnuva", 20, "area2"));
    }
}
