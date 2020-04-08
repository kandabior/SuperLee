
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[]args) {
        Register register = new Register();

        List<String> cat = new LinkedList<>();
        cat.add("shimurim");
        LocalDate date = LocalDate.of(20, 12,31);

        System.out.println(register.addProduct(100, 2, "Corn",5, 8, date,cat,"Rami Levi", 50, "area7"));

    }
}
