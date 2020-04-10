
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    private static Register register;
    public static void main(String[]args) {
        initiateSystem();
        mainLoop();

    }

    private static void mainLoop() {
        Printer.Print("Welcom to erez & or SuperMarket!\n" +
                            "please choose an action\n");
        int choose=0;
        do{
            Printer.Print(
                    "1. Register global manager.\n" +
                            "2. Register inventory manager.\n" +
                            "3. Add product.\n" +
                            "4. Remove products.\n" +
                            "5. Change product price by id.\n" +
                            "6. Change product price by category.\n" +
                            "7. Print total stock report.\n" +
                            "8. print product report by categories.\n" +
                            "9. print missing products.\n" +
                            "10. print expired and defective products.\n" +
                            "11. Quit.\n");
            choose=scanner.nextInt();
            switch (choose){
                case 1: AddGlobalManager(); break;
                case 2: AddInventoryManager(); break;
                case 3: AddProduct(); break;
                case 4: RemoveProduct();break;
                case 5: ChangePriceById(); break;
                case 6: changeProductByCategory(); break;
                case 7: PrintTotalStock();break;
                case 8: PrintProductByCategories(); break;
                case 9: PrintMissingProduct(); break;
                case 10: PrintEXPProducts(); break;
                case 11: Quit(); break;
            }


        }
        while(choose!=11);

    }

    private static void Quit() {
        Printer.Print("Shutting down system...");
    }

    private static void PrintEXPProducts() {
        Printer.Print(register.ExpiredReport());
    }

    private static void PrintMissingProduct() {
        Printer.Print(register.NeedToBuyReport());
    }

    private static void PrintProductByCategories() {

    }

    private static void PrintTotalStock() {
    }

    private static void changeProductByCategory() {

    }


    private static void ChangePriceById() {

    }

    private static void RemoveProduct() {

    }

    private static void AddProduct() {

    }

    private static void AddInventoryManager() {

    }

    private static void AddGlobalManager() {


    }

    private static void initiateSystem(){
        register= new Register();

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
