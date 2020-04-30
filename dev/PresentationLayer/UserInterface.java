package PresentationLayer;

import IntefaceLayer.Register;
import java.time.LocalDate;
import java.util.*;

public class UserInterface {
    public static Scanner scanner=new Scanner(System.in);
    private static Register register;

    public static void main(String[]args) {
        mainLoop();
    }

    private static void mainLoop() {
        register= new Register();
        Printer.Print("Welcome to Erez & Or Digital Storage !\n");
        Printer.Print("Do you Want to initiate the System with 4 managers and 2 product (Corn and Milk)? Y/N");
        String ans = scanner.next();
        if(ans.equals("Y")||ans.equals("y"))
            initiateSystem();
        else
            Printer.Print("system was not initiate");
        int choose;
        do{
            Printer.Print("\n\nPlease choose an action:\n"+
                            "1. Register global manager\n" +
                            "2. Register inventory manager\n" +
                            "3. Add product   (Manager Only)\n" +
                            "4. Remove products   (Manager Only)\n" +
                            "5. Change product price by id   (Global Manager Only)\n" +
                            "6. Change product price by category   (Global Manager Only)\n" +
                            "7. Add amount to Product\n" +
                            "8. Remove amount from Product\n" +
                            "9. Change categories to Product\n" +
                            "10. Transfer amount of Product from the storage to the shelf\n" +
                            "11. Transfer amount of Product from the shelf to the storage\n" +
                            "12. Set amount of defective products\n" +
                            "Reports:\n"+
                            "13. Print total stock report\n" +
                            "14. print storage amount report\n"+
                            "15. print shelf amount report\n"+
                            "16. print product report by categories\n" +
                            "17. print missing products report\n" +
                            "18. print expired and defective products report\n" +
                            "19. Print sale price report\n"+
                            "20. Print cost price report\n"+
                            "21. Quit.\n");
            choose = scanner.nextInt();
            try {
                switch (choose) {
                    case 1:
                        AddGlobalManager();
                        break;
                    case 2:
                        AddInventoryManager();
                        break;
                    case 3:
                        AddProduct();
                        break;
                    case 4:
                        RemoveProduct();
                        break;
                    case 5:
                        ChangePriceById();
                        break;
                    case 6:
                        changeProductByCategory();
                        break;
                    case 7:
                        addAmountToProduct();
                        break;
                    case 8:
                        removeAmountFromProduct();
                        break;
                    case 9:
                        changeCategory();
                        break;
                    case 10:
                        StorageToShelf();
                        break;
                    case 11:
                        shelfToStorage();
                        break;
                    case 12:
                        setDefectiveProducts();
                        break;
                    //case 13: LastSalePrice(); break;
                    case 13:
                        PrintTotalStock();
                        break;
                    case 14:
                        printStorageStock();
                        break;
                    case 15:
                        printShelfStock();
                        break;
                    case 16:
                        PrintProductByCategories();
                        break;
                    case 17:
                        PrintMissingProduct();
                        break;
                    case 18:
                        PrintEXPProducts();
                        break;
                    case 19:
                        PrintSalePriceReport();
                        break;
                    case 20:
                        PrintCostPriceReport();
                        break;
                    case 21:
                        Quit();
                        break;
                }
            }
            catch (Exception e){
                Printer.Print("can't execute the action");
            }
        }
        while(choose!=21);
    }

    private static void setDefectiveProducts() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("amount: ");
        String amount=scanner.next();
        Printer.Print(register.setDefectiveProducts(Integer.parseInt(prodId),Integer.parseInt(amount)));
    }

    private static void Quit() {
        Printer.Print("Shutting down system...");
    }

    private static void PrintCostPriceReport() {
        Printer.Print(("\nPlease enter a produce id to print:"));
        String prodId=scanner.next();
        Printer.Print(register.CostPriceReport(Integer.parseInt(prodId)));
    }

    private static void PrintSalePriceReport() {
        Printer.Print(("\nPlease enter a produce id to print:"));
        String prodId=scanner.next();
        Printer.Print(register.SalePricesReport(Integer.parseInt(prodId)));
    }

    private static void changeCategory(){
        Printer.Print("\nPlease Enter the following by given order:\n");
        Printer.Print("username: ");
        String userName=scanner.next();
        Printer.Print("password: ");
        String password=scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("categories: (with ',' between them)");
        String cat=scanner.next();
        List<String> categoriesList= Arrays.asList(cat.split(","));
        Printer.Print(register.setCategory(userName,password,Integer.parseInt(prodId),categoriesList));
    }

    private static void shelfToStorage(){
        Printer.Print("\nPlease Enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease Enter amount to take from the shelf to the storage:\n");
        String amount = scanner.next();
        Printer.Print(register.shelfToStorage(Integer.parseInt(prodId),Integer.parseInt(amount)));
    }
    private static void StorageToShelf(){
        Printer.Print("\nPlease Enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease Enter amount to take from the storage to the shelf:\n");
        String amount = scanner.next();
        Printer.Print(register.storageToShelf(Integer.parseInt(prodId),Integer.parseInt(amount)));
    }
    private static void addAmountToProduct(){
        Printer.Print("\nPlease Enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease Enter amount to add:\n");
        String amount = scanner.next();
        Printer.Print(register.addAmountToProduct(Integer.parseInt(prodId),Integer.parseInt(amount)));
    }
    private static void removeAmountFromProduct(){
        Printer.Print("\nPlease Enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease Enter amount to remove:\n");
        String amount = scanner.next();
        Printer.Print(register.removeAmountFromProduct(Integer.parseInt(prodId),Integer.parseInt(amount)));
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
    private static void printShelfStock() {
        Printer.Print(register.ShelfReport());
    }

    private static void printStorageStock() {
        Printer.Print(register.StorageReport());

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
        Printer.Print(register.removeProduct(userName,password,Integer.parseInt( prodId)));
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
        Printer.Print("EXP date (format of DD/MM/YYYY): ");
        String expdate=scanner.next();
        Printer.Print("categories (with ',' between them with no spaces):");
        String categories=scanner.next();
        Printer.Print("manufacturer: ");
        String manufacturer=scanner.next();
        Printer.Print("minimum amount: ");
        String minAmount=scanner.next();
        Printer.Print("place: ");
        String place=scanner.next();
        List<String> categoriesList= Arrays.asList(categories.split(","));
        String []dateArr=expdate.split("/");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[2]),Integer.parseInt(dateArr[1]),Integer.parseInt(dateArr[0]));
        Printer.Print(register.addProduct(userName,password,Integer.parseInt(prodId),Integer.parseInt(amount),name,Integer.parseInt(costPrice),Integer.parseInt(salePrice), date,categoriesList,manufacturer,Integer.parseInt(minAmount),place));
    }
    private static void AddInventoryManager() {
        Printer.Print("Please enter new username:");
        String userName=scanner.next();
        Printer.Print("Please enter new password:");
        String password=scanner.next();
        Printer.Print(register.addInventoryManager(userName, password));
    }
    private static void AddGlobalManager() {
        Printer.Print("Please enter new username:");
        String userName=scanner.next();
        Printer.Print("Please enter new password:");
        String password=scanner.next();
        Printer.Print(register.addGlobalManager(userName, password));

    }
    private static void initiateSystem(){
        Printer.Print("System initiate successfully (:");

        register.addGlobalManager("Erez", "1234");
        register.addGlobalManager("Or", "1234");
        register.addInventoryManager("dana", "1234");
        register.addInventoryManager("david", "1234");


        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",1, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");

        List<String> cat2 = new LinkedList<>();
        cat2.add("Halavi");
        cat2.add("Cartons");
        LocalDate date2 = LocalDate.of(20, 8,31);
        register.addProduct("Or", "1234",2, 30, "Milk",3,
                6, date2,cat2,"Tnuva", 20, "area2");
    }
}
