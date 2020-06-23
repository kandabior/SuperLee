package src.PresentationLayer;

import src.BusinessLayer.Suppliers.Items;
import src.InterfaceLayer.Inventory.InventoryController;
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
        Printer.Print("Inventory:");
        do {
            Printer.Print("\n\nPlease choose an action:\n" +
                    "1. Add product   (Manager Only)\n" +
                    "2. Remove product   (Manager Only)\n" +
                    "3. Change product price by id   (Global Manager Only)\n" +
                    "4. Change product price by category   (Global Manager Only)\n" +
                    "5. Remove amount from Product\n" +
                    "6. Change categories to Product\n" +
                    "7. Transfer amount of Product from the storage to the shelf\n" +
                    "8. Transfer amount of Product from the shelf to the storage\n" +
                    "9. Set amount of defective products\n" +
                    "10. Back to Inventory&reports menu\n");
            choose = scanner.nextInt();
            try {
                switch (choose) {
                    case 1:
                        AddProduct();
                        break;
                    case 2:
                        RemoveProduct();
                        break;
                    case 3:
                        ChangePriceById();
                        break;
                    case 4:
                        changeProductByCategory();
                        break;
                    case 5:
                        removeAmountFromProduct();
                        break;
                    case 6:
                        changeCategory();
                        break;
                    case 7:
                        StorageToShelf();
                        break;
                    case 8:
                        shelfToStorage();
                        break;
                    case 9:
                        setDefectiveProducts();
                        break;
                    case 10:
                        break;
                }
            }
            catch (Exception e) {
                Printer.Print("can't execute the action");
            }
        }
        while (choose != 10) ;
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
                "9. Back to inventory&reports menu\n");
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
        System.out.println("Enter day of the week");
        String day= scanner.next();
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
        Printer.Print(inventoryController.RemoveFromWeeklyOrder(Integer.parseInt(branchId),ids,Integer.parseInt(day)));

    }
    private static void addToWeeklyOrder() {
        boolean stop=false;
                        System.out.println("Enter branch id");
                String branchId= scanner.next();
                System.out.println("Enter a day for weekly order:");
                String day= scanner.next();
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
        Printer.Print(inventoryController.AddToWeeklyOrder(Integer.parseInt(branchId),Integer.parseInt(day),id_amount));
    }
    private static void makeMissingOrder() {
        System.out.println("Enter branch id");
        String branchId= scanner.next();
        System.out.println("Making missing product order..");
        Printer.Print(inventoryController.MakeMissingOrder(Integer.parseInt(branchId)));
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
        Printer.Print(inventoryController.MakeCostomiezedOrder(Integer.parseInt(branchId),id_amount));


    }
    public static void mainLoop() {
        System.out.println("Inventory and Reports Menu");
        int choose;
        do{
            Printer.Print("\n\nPlease choose an action:\n"+
                    "1. Enter Inventory Menu\n" +
                    "2. Order Menu\n" +
                    "3. Enter Reports Menu\n" +
                    "4. Show Global Products\n" +
                    "5. Add Global Product\n" +
                    "6. Back to main menu\n");
            choose = scanner.nextInt();
            try {
                switch (choose) {
                    case 1:
                        inventoryLoop();
                        break;
                    case 2:
                        OrdersLoop();
                        break;
                    case 3:
                        reportsLoop();
                        break;
                    case 4:
                        ShowGlobalProduct();
                        break;
                    case 5:
                        AddGlobalProduct();
                        break;
                    case 6:
                        break;
                }
            }
            catch (Exception e){
                Printer.Print("can't execute the action");
            }
        }
        while(choose!=6);
    }
    private static void OrdersLoop() {
        int choose;
        Printer.Print("Orders Menu:");
        do {
            Printer.Print("\n\nPlease choose an action:\n" +
                    "1. Make missing products Order \n" +
                    "2. Make customized order\n" +
                    "3. Add to weekly order\n" +
                    "4. Remove from weekly order\n"+
                    "5. Print Weekly Order\n" +
                    "6. Cancel order\n"+
                    "7. Back to inventory&reports menu\n");
            choose = scanner.nextInt();
            try {
                switch (choose) {
                    case 1:
                        makeMissingOrder();
                        break;
                    case 2:
                        makeCustomiezedOrder();
                        break;
                    case 3:
                        addToWeeklyOrder();
                        break;
                    case 4:
                        RemoveFromWeeklyOrder();
                        break;
                    case 5:
                        PrintWeeklyOrder();
                        break;
                    case 6:
                        cancelOrder();
                        break;
                    case 7:
                        break;
                }
            }
            catch (Exception e) {
                Printer.Print("can't execute the action");
            }
        }
        while (choose != 7) ;

    }

    private static void cancelOrder() {
        System.out.println("\nEnter order id: ");
        String orderId= scanner.next();
        Printer.Print(inventoryController.cancelOrder(Integer.parseInt(orderId)));
    }

    private static void PrintWeeklyOrder() {
        System.out.println("\nEnter branch id: ");
        String branchId= scanner.next();
        System.out.println("Enter day of the week: ");
        String day= scanner.next();
        Printer.Print(inventoryController.PrintWeeklyOrder(Integer.parseInt(branchId),Integer.parseInt(day)));


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
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("categories: (with ',' between them)");
        String cat=scanner.next();
        List<String> categoriesList= Arrays.asList(cat.split(","));
        Printer.Print(inventoryController.setCategory(Integer.parseInt(branchId),Integer.parseInt(prodId),categoriesList));
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
        Printer.Print("categories: (with ',' between them)");
        String categories=scanner.next();
        Printer.Print("price: ");
        String price=scanner.next();
        List<String> categoriesList= Arrays.asList(categories.split(","));
        Printer.Print(inventoryController.setPriceByCategory(Integer.parseInt(branchId),categoriesList ,Double.parseDouble(price)));
    }
    private static void ChangePriceById() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        System.out.println("Enter branch id: ");
        String branchId= scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("price: ");
        String price=scanner.next();
        Printer.Print(inventoryController.setSalePriceById(Integer.parseInt(branchId),Integer.parseInt(prodId),Double.parseDouble(price)));
    }
    private static void RemoveProduct() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        System.out.println("Enter branch id: ");
        String branchId= scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print(inventoryController.removeProduct(Integer.parseInt(branchId),Integer.parseInt( prodId)));
    }
    private static void AddProduct() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        System.out.println("Enter branch id: ");
        String branchId= scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        /*Printer.Print("name: ");
        String name=scanner.next();*/
        Printer.Print("Quantity: ");
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
        Printer.Print(inventoryController.addProduct(Integer.parseInt(branchId),Integer.parseInt(prodId),Integer.parseInt(amount),Double.parseDouble(costPrice),Double.parseDouble(salePrice), date,categoriesList,manufacturer,Integer.parseInt(minAmount),place));
    }
    public static void initiateSystem(){
        try {

            inventoryController.CreateNewInventory(1);
            inventoryController.addGlobalManager(1,"Erez", "1234");
            inventoryController.addGlobalManager(1,"Or", "1234");
            inventoryController.addInventoryManager(1,"dana", "1234");
            inventoryController.addInventoryManager(1,"david", "1234");

            List<String> cat1 = new LinkedList<>();
            cat1.add("Shimurim");
            LocalDate date1 = LocalDate.of(20, 12, 31);
            inventoryController.addProduct(1,  1, 20, 5.0,
                    8.0, date1, cat1, "Osem", 10, "area1");

            List<String> cat2 = new LinkedList<>();
            cat2.add("Halavi");
            cat2.add("Cartons");
            LocalDate date2 = LocalDate.of(20, 8, 31);
            inventoryController.addProduct(1,  2, 30, 3.0,
                    6.0, date2, cat2, "Tnuva", 20, "area2");
            Printer.Print("System initiate successfully (:");
        }
        catch (Exception e){
            Printer.Print("could not initiate the system..");
        }
    }
    public static void PromoteDay(Integer dayOfTheWeek) {
        Printer.Print(inventoryController.PromoteDay(dayOfTheWeek));
    }

    //Managers
    private static void AddInventoryManager() {
        Printer.Print("Please enter branch Id:");
        String branchId=scanner.next();
        Printer.Print("Please enter new username:");
        String userName=scanner.next();
        Printer.Print("Please enter new password:");
        String password=scanner.next();
        Printer.Print(inventoryController.addInventoryManager(Integer.parseInt(branchId),userName, password));
    }
    private static void AddGlobalManager() {
        Printer.Print("Please enter branch Id:");
        String branchId=scanner.next();
        Printer.Print("Please enter new username:");
        String userName=scanner.next();
        Printer.Print("Please enter new password:");
        String password=scanner.next();
        Printer.Print(inventoryController.addGlobalManager(Integer.parseInt(branchId),userName, password));

    }

    private static void AddGlobalProduct() {
        Printer.Print("\nPlease enter product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease enter product name:\n");
        String amount = scanner.next();
        if (!Items.addItem(Integer.parseInt(prodId), amount)) {
            System.out.println("Product already exists, please enter another product id.");
        }
    }

    private static void ShowGlobalProduct() {
        Items.PrintAllItems();
    }
}
