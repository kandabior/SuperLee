import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.text.SimpleDateFormat;

public class Main {
    public static Scanner scanner=new Scanner(System.in);
    private static Register register;

    public static void main(String[]args) {
        initiateSystem();
        mainLoop();
    }

    private static void mainLoop() {
        Printer.Print("Welcome to Erez & Or Digital Storage !\n");
        int choose;
        do{
            Printer.Print("\n\nPlease choose an action:\n"+
                            "1. Register global manager\n" +
                            "2. Register inventory manager\n" +
                            "3. Add product   (Manager Only)\n" +
                            "4. Remove products   (Manager Only)\n" +
                            "5. Change product price by id   (Global Manager Only)\n" +
                            "6. Change product price by category   (Global Manager Only)\n" +
                            "7. Get cost price history\n" +
                            "8. Get sale price history\n\n" +
                            "Reports:\n"+
                            "9. Print total stock report\n" +
                            "10. print product report by categories\n" +
                            "11. print missing products report\n" +
                            "12. print expired and defective products report\n" +
                            "13. Quit.\n");
            choose=scanner.nextInt();
            switch (choose){
                case 1: AddGlobalManager(); break;
                case 2: AddInventoryManager(); break;
                case 3: AddProduct(); break;
                case 4: RemoveProduct();break;
                case 5: ChangePriceById(); break;
                case 6: changeProductByCategory(); break;
                case 7: LastCostPrice(); break;
                case 8: LastSalePrice(); break;
                case 9: PrintTotalStock();break;
                case 10: PrintProductByCategories(); break;
                case 11: PrintMissingProduct(); break;
                case 12: PrintEXPProducts(); break;
                case 13: Quit(); break;
            }


        }
        while(choose!=11);
    }

    private static void Quit() {
        Printer.Print("Shutting down system...");
    }

    private static void LastCostPrice(){
        Printer.Print("\nPlease Enter Product Id:\n");
        String id = scanner.next();
        Printer.Print(register.getLastCostPrice(Integer.parseInt(id)));
    }
    private static void LastSalePrice(){
        Printer.Print("\nPlease Enter Product Id:\n");
        String id = scanner.next();
        Printer.Print(register.getLastSalePrice(Integer.parseInt(id)));
    }

    private static void PrintEXPProducts() {
        Printer.Print(register.ExpiredReport());
    }

    private static void PrintMissingProduct() {
        Printer.Print(register.NeedToBuyReport());
    }

    private static void PrintProductByCategories() {
        Printer.Print("\nPlease Enter category/ies to print with ',' between them: ");
        Printer.Print("\ncategories: ");
        String categories=scanner.next();
        List<String> categoriesList= Arrays.asList(categories.split(","));
        Printer.Print(register.CategoryReport(categoriesList));
    }

    private static void PrintTotalStock() {
        Printer.Print(register.totalStockReport());
    }

    private static void changeProductByCategory() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        Printer.Print("username: ");
        String userName=scanner.next();
        Printer.Print("password: ");
        String password=scanner.next();
        Printer.Print("categories: (with ',' between them)");
        String categories=scanner.next();
        Printer.Print("price: ");
        String price=scanner.next();
        List<String> categoriesList= Arrays.asList(categories.split(","));
        Printer.Print(register.setPriceByCategory(userName,password,categoriesList ,Integer.parseInt(price)));
    }


    private static void ChangePriceById() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        Printer.Print("username: ");
        String userName=scanner.next();
        Printer.Print("password: ");
        String password=scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("price: ");
        String price=scanner.next();
        Printer.Print(register.setSalePriceById(userName,password,Integer.parseInt(prodId),Integer.parseInt(price)));
    }

    private static void RemoveProduct() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        Printer.Print("username: ");
        String userName=scanner.next();
        Printer.Print("password: ");
        String password=scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("amount: ");
        String amount=scanner.next();
        Printer.Print(register.removeProduct(userName,password,Integer.parseInt( prodId),Integer.parseInt(amount)));
    }

    private static void AddProduct() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        Printer.Print("username: ");
        String userName=scanner.next();
        Printer.Print("password: ");
        String password=scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("name: ");
        String name=scanner.next();
        Printer.Print("amount: ");
        String amount=scanner.next();
        Printer.Print("cost price: ");
        String costPrice=scanner.next();
        Printer.Print("salePrice: ");
        String salePrice=scanner.next();
        Printer.Print("EXP date: ");
        String expdate=scanner.next();
        Printer.Print("categories: (with ',' between them)");
        String categories=scanner.next();
        Printer.Print("manufacturer: ");
        String manufacturer=scanner.next();
        Printer.Print("minimum amount: ");
        String minAmount=scanner.next();
        Printer.Print("place: ");
        String place=scanner.next();
        List<String> categoriesList= Arrays.asList(categories.split(","));
        LocalDate date=null;
        try {
            date = LocalDate.parse(expdate);
        }
        catch (Exception e){}
        Printer.Print(register.addProduct(userName,password,Integer.parseInt(prodId),Integer.parseInt(amount),name,Integer.parseInt(costPrice),Integer.parseInt(salePrice), date,categoriesList,manufacturer,Integer.parseInt(minAmount),place));
    }

    private static void AddInventoryManager() {
        Printer.Print("Please enter username and password:");
        String userName=scanner.next();
        String password=scanner.next();
        Printer.Print(register.addInventoryManager(userName, password));
    }

    private static void AddGlobalManager() {
        Printer.Print("Please enter username and password:");
        String userName=scanner.next();
        String password=scanner.next();
        Printer.Print(register.addGlobalManager(userName, password));

    }

    private static void initiateSystem(){
        register= new Register();

        register.addGlobalManager("Erez", "1234");
        register.addGlobalManager("Or", "1234");
        register.addInventoryManager("yossi", "1234");
        register.addInventoryManager("david", "1234");


        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        cat1.add("Cartons");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",1, 20, "Corn",5,
                8, date1,cat1,"Rami Levi", 50, "area1");

        List<String> cat2 = new LinkedList<>();
        cat2.add("Halavi");
        cat2.add("Cartons");
        LocalDate date2 = LocalDate.of(20, 8,31);
        register.addProduct("Or", "1234",2, 30, "Milk",3,
                6, date2,cat2,"Tnuva", 20, "area2");
    }
}
