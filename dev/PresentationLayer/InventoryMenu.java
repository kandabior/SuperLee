package PresentationLayer;

import InterfaceLayer.InventoryController;
import LogicLayer.Inventory;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class InventoryMenu {
    public static Scanner scanner=new Scanner(System.in);
    private static InventoryController inventoryController=InventoryController.getInventoryController();
    private static void inventoryLoop() {
        int choose;
        do {
            Printer.Print("\n\nPlease choose an action:\n" +
                    "1. Add new branch\n" +
                    "2. Register global manager\n" +
                    "3. Register inventory manager\n" +
                    "4. Add product   (Manager Only)\n" +
                    "5. Remove product   (Manager Only)\n" +
                    "6. Change product price by id   (Global Manager Only)\n" +
                    "7. Change product price by category   (Global Manager Only)\n" +
                    "8. Make missing products Order \n" +
                    "9. Make customized order\n" +
                    "10. Add to weekly order\n" +
                    "11. Remove from weekly order\n"+
                    "12. Remove amount from Product\n" +
                    "13. Change categories to Product\n" +
                    "14. Transfer amount of Product from the storage to the shelf\n" +
                    "15. Transfer amount of Product from the shelf to the storage\n" +
                    "16. Set amount of defective products\n" +
                    "17. Back to main menu\n");
            choose = scanner.nextInt();
            try {
                switch (choose) {
                    case 1:
                        AddNewBranch();
                    case 2:
                        AddGlobalManager();
                        break;
                    case 3:
                        AddInventoryManager();
                        break;
                    case 4:
                        AddProduct();
                        break;
                    case 5:
                        RemoveProduct();
                        break;
                    case 6:
                        ChangePriceById();
                        break;
                    case 7:
                        changeProductByCategory();
                        break;
                    case 8:
                        makeMissingOrder();
                        break;
                    case 9:
                        makeCustomiezedOrder();
                        break;
                    case 10:
                        addToWeeklyOrder();
                        break;
                    case 11:
                        RemoveFromWeeklyOrder();
                        break;
                    case 12:
                        removeAmountFromProduct();
                        break;
                    case 13:
                        changeCategory();
                        break;
                    case 14:
                        StorageToShelf();
                        break;
                    case 15:
                        shelfToStorage();
                        break;
                    case 16:
                        setDefectiveProducts();
                        break;
                    case 17:
                        break;
                }
            }
            catch (Exception e) {
                Printer.Print("can't execute the action");
            }
        }
        while (choose != 15) ;
    }

    private static void reportsLoop(){
        int choose;
        do {
        Printer.Print("Reports:\n"+
                "1. Print total stock report\n" +
                "2. print storage amount report\n"+
                "3. print shelf amount report\n"+
                "4. print product report by categories\n" +
                "5. print missing products report\n" +
                "6. print expired and defective products report\n" +
                "7. Print sale price report\n"+
                "8. Print cost price report\n"+
                "9. Back to main menu\n");
            choose = scanner.nextInt();
            try {
                switch (choose) {
                    case 1:
                        PrintTotalStock();
                        break;
                    case 2:
                        printStorageStock();
                        break;
                    case 3:
                        printShelfStock();
                        break;
                    case 4:
                        PrintProductByCategories();
                        break;
                    case 5:
                        PrintMissingProduct();
                        break;
                    case 6:
                        PrintEXPProducts();
                        break;
                    case 7:
                        PrintSalePriceReport();
                        break;
                    case 8:
                        PrintCostPriceReport();
                        break;
                    case 9:
                        break;
                }
            }
            catch (Exception e){
                Printer.Print("can't execute the action");
            }
        }
        while(choose!=9);
    }

    private static void RemoveFromWeeklyOrder() {
        boolean stop=false;
        System.out.println("Enter branch id");
        String branchId= scanner.next();
        System.out.println("please enter product id.\n" +
                "To stop insert products, insert '0'.\n");
        List<Integer> ids=new LinkedList<>();
        while(!stop) {
            Printer.Print("product Id: ");
            String prodId = scanner.next();
            if(prodId.equals("0")){
                stop=true;
            }
            else{
                ids.add(Integer.parseInt(prodId));
            }
        }
        inventoryController.RemoveFromWeeklyOrder(Integer.parseInt(branchId),ids);

    }

    private static void addToWeeklyOrder() {
        boolean stop=false;
        System.out.println("Enter branch id");
        String branchId= scanner.next();
        System.out.println("please enter product id and amount with a single ':' between.\n" +
                "To stop insert products, insert '0:0'.\n");
        List<Pair<Integer,Integer>> id_amount=new LinkedList<>();
        while(!stop) {
            Printer.Print("product Id and amount: ");
            String prodId = scanner.next();
            String[] str= (prodId.split(":"));
            if((str[0].equals("0")) && (str[1].equals("0"))){
                stop=true;
            }
            else{
                id_amount.add(new Pair<>(Integer.parseInt(str[0]),Integer.parseInt(str[1])));
            }
        }
        inventoryController.AddToWeeklyOrder(Integer.parseInt(branchId),id_amount);
    }

    private static void makeMissingOrder() {
        System.out.println("Enter branch id");
        String branchId= scanner.next();
        System.out.println("Making missing product order..");
        inventoryController.MakeMissingOrder(Integer.parseInt(branchId));
    }

    private static void makeCustomiezedOrder() {
        boolean stop=false;
        System.out.println("Enter branch id");
        String branchId= scanner.next();
        System.out.println("please enter product id and amount with a single ':' between.\n" +
                "To stop insert products, insert '0:0'.\n");
        List<Pair<Integer,Integer>> id_amount=new LinkedList<>();
        while(!stop) {
            Printer.Print("product Id and amount: ");
            String prodId = scanner.next();
            String[] str= (prodId.split(":"));
            if((str[0].equals("0")) && (str[1].equals("0"))){
                stop=true;
            }
            else{
                id_amount.add(new Pair<>(Integer.parseInt(str[0]),Integer.parseInt(str[1])));
            }
        }
        inventoryController.MakeCistomiezedOrder(Integer.parseInt(branchId),id_amount);


    }

    public static void mainLoop() {
        System.out.println("Inventory and Reports Menu");
        int choose;
        do{
            Printer.Print("\n\nPlease choose an action:\n"+
                    "1. Enter Inventory Menu\n" +
                    "2. Enter Reports Menu\n" +
                    "3. Back to main menu\n");
            choose = scanner.nextInt();
            try {
                switch (choose) {
                    case 1:
                        inventoryLoop();
                        break;
                    case 2:
                        reportsLoop();
                        break;
                    case 3:
                        break;
                }
            }
            catch (Exception e){
                Printer.Print("can't execute the action");
            }
        }
        while(choose!=3);
    }

    private static void AddNewBranch() {
        System.out.println("Please insert branch: ");
        String branchId=scanner.next();
        Printer.Print(inventoryController.CreateNewInventory(Integer.parseInt(branchId)));
    }

    private static void setDefectiveProducts() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        System.out.println("Enter branch id");
        String branchId= scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("amount: ");
        String amount=scanner.next();
        Printer.Print(inventoryController.setDefectiveProducts(Integer.parseInt(branchId),Integer.parseInt(prodId),Integer.parseInt(amount)));
    }


    private static void PrintCostPriceReport() {
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print(("Enter a produce id: "));
        String prodId=scanner.next();
        Printer.Print(inventoryController.CostPriceReport(Integer.parseInt(branchId),Integer.parseInt(prodId)));
    }

    private static void PrintSalePriceReport() {
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print(("Enter a produce id:"));
        String prodId=scanner.next();
        Printer.Print(inventoryController.SalePricesReport(Integer.parseInt(branchId),Integer.parseInt(prodId)));
    }

    private static void changeCategory(){
        Printer.Print("\nPlease Enter the following by given order:\n");
        System.out.println("Enter branch id: ");
        String branchId= scanner.next();
        Printer.Print("username: ");
        String userName=scanner.next();
        Printer.Print("password: ");
        String password=scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("categories: (with ',' between them)");
        String cat=scanner.next();
        List<String> categoriesList= Arrays.asList(cat.split(","));
        Printer.Print(inventoryController.setCategory(Integer.parseInt(branchId),userName,password,Integer.parseInt(prodId),categoriesList));
    }

    private static void shelfToStorage(){
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print("\nPlease Enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease Enter amount to take from the shelf to the storage:\n");
        String amount = scanner.next();
        Printer.Print(inventoryController.shelfToStorage(Integer.parseInt(branchId),Integer.parseInt(prodId),Integer.parseInt(amount)));
    }
    private static void StorageToShelf(){
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print("\nEnter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nEnter amount to take from the storage to the shelf:\n");
        String amount = scanner.next();
        Printer.Print(inventoryController.storageToShelf(Integer.parseInt(branchId),Integer.parseInt(prodId),Integer.parseInt(amount)));
    }
    private static void addAmountToProduct(){
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print("\nPlease Enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease Enter amount to add:\n");
        String amount = scanner.next();
        Printer.Print(inventoryController.addAmountToProduct(Integer.parseInt(branchId),Integer.parseInt(prodId),Integer.parseInt(amount)));
    }
    private static void removeAmountFromProduct(){
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print("\nPlease Enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease Enter amount to remove:\n");
        String amount = scanner.next();
        Printer.Print(inventoryController.removeAmountFromProduct(Integer.parseInt(branchId),Integer.parseInt(prodId),Integer.parseInt(amount)));
    }

    private static void PrintEXPProducts() {
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print(inventoryController.ExpiredReport(Integer.parseInt(branchId)));
    }
    private static void PrintMissingProduct() {
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print(inventoryController.NeedToBuyReport(Integer.parseInt(branchId)));
    }
    private static void PrintProductByCategories() {
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print("\nPlease Enter category/ies to print with ',' between them: ");
        Printer.Print("\ncategories: ");
        String categories=scanner.next();
        List<String> categoriesList= Arrays.asList(categories.split(","));
        Printer.Print(inventoryController.CategoryReport(Integer.parseInt(branchId),categoriesList));
    }
    private static void printShelfStock() {
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print(inventoryController.ShelfReport(Integer.parseInt(branchId)));
    }

    private static void printStorageStock() {
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print(inventoryController.StorageReport(Integer.parseInt(branchId)));

    }
    private static void PrintTotalStock() {
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        Printer.Print(inventoryController.totalStockReport(Integer.parseInt(branchId)));
    }
    private static void changeProductByCategory() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        System.out.println("Enter branch id: ");
        String branchId= scanner.next();
        Printer.Print("username: ");
        String userName=scanner.next();
        Printer.Print("password: ");
        String password=scanner.next();
        Printer.Print("categories: (with ',' between them)");
        String categories=scanner.next();
        Printer.Print("price: ");
        String price=scanner.next();
        List<String> categoriesList= Arrays.asList(categories.split(","));
        Printer.Print(inventoryController.setPriceByCategory(Integer.parseInt(branchId),userName,password,categoriesList ,Double.parseDouble(price)));
    }
    private static void ChangePriceById() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        System.out.println("Enter branch id: ");
        String branchId= scanner.next();
        Printer.Print("username: ");
        String userName=scanner.next();
        Printer.Print("password: ");
        String password=scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("price: ");
        String price=scanner.next();
        Printer.Print(inventoryController.setSalePriceById(Integer.parseInt(branchId),userName,password,Integer.parseInt(prodId),Double.parseDouble(price)));
    }
    private static void RemoveProduct() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        System.out.println("Enter branch id: ");
        String branchId= scanner.next();
        Printer.Print("username: ");
        String userName=scanner.next();
        Printer.Print("password: ");
        String password=scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print(inventoryController.removeProduct(Integer.parseInt(branchId),userName,password,Integer.parseInt( prodId)));
    }
    private static void AddProduct() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        System.out.println("Enter branch id: ");
        String branchId= scanner.next();
        Printer.Print("username: ");
        String userName=scanner.next();
        Printer.Print("password: ");
        String password=scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("name: ");
        String name=scanner.next();
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
        Printer.Print(inventoryController.addProduct(Integer.parseInt(branchId),userName,password,Integer.parseInt(prodId),0,Double.parseDouble(costPrice),Double.parseDouble(salePrice), date,categoriesList,manufacturer,Integer.parseInt(minAmount),place));
    }
    private static void AddInventoryManager() {
        Printer.Print("Please enter new username:");
        String userName=scanner.next();
        Printer.Print("Please enter new password:");
        String password=scanner.next();
        Printer.Print(inventoryController.addInventoryManager(userName, password));
    }
    private static void AddGlobalManager() {
        Printer.Print("Please enter new username:");
        String userName=scanner.next();
        Printer.Print("Please enter new password:");
        String password=scanner.next();
        Printer.Print(inventoryController.addGlobalManager(userName, password));

    }
    public static void initiateSystem(){
        try {

            inventoryController.CreateNewInventory(1);

            inventoryController.addGlobalManager("Erez", "1234");
            inventoryController.addGlobalManager("Or", "1234");
            inventoryController.addInventoryManager("dana", "1234");
            inventoryController.addInventoryManager("david", "1234");

            List<String> cat1 = new LinkedList<>();
            cat1.add("Shimurim");
            LocalDate date1 = LocalDate.of(20, 12, 31);
            inventoryController.addProduct(1, "Erez", "1234", 1, 20, 5.0,
                    8.0, date1, cat1, "Osem", 10, "area1");

            List<String> cat2 = new LinkedList<>();
            cat2.add("Halavi");
            cat2.add("Cartons");
            LocalDate date2 = LocalDate.of(20, 8, 31);
            inventoryController.addProduct(1, "Or", "1234", 2, 30, 3.0,
                    6.0, date2, cat2, "Tnuva", 20, "area2");
            Printer.Print("System initiate successfully (:");
        }
        catch (Exception e){
            Printer.Print("could not initiate the system..");
        }
    }

    public static void PromoteDay(Integer dayOfTheWeek) {
        inventoryController.PromoteDay(dayOfTheWeek);
    }
}
