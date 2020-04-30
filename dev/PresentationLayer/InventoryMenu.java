package PresentationLayer;

import InterfaceLayer.InventoryController;

import java.time.LocalDate;
import java.util.*;

public class InventoryMenu {
    public static Scanner scanner=new Scanner(System.in);
    private static InventoryController inventoryController=InventoryController.getInventoryController();

    private static void inventoryLoop() {
        int choose;
        do {
            Printer.Print("\n\nPlease choose an action:\n" +
                    "1. Register global manager\n" +
                    "2. Register inventory manager\n" +
                    "3. Add product   (Manager Only)\n" +
                    "4. Remove product   (Manager Only)\n" +
                    "5. Change product price by id   (Global Manager Only)\n" +
                    "6. Change product price by category   (Global Manager Only)\n" +
                    "7. Make missing products Order \n" +
                    "8. Remove amount from Product\n" +
                    "9. Change categories to Product\n" +
                    "10. Transfer amount of Product from the storage to the shelf\n" +
                    "11. Transfer amount of Product from the shelf to the storage\n" +
                    "12. Set amount of defective products\n" +
                    "13. Back to main menu\n");
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
                        makeMissingOrder();
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
                    case 13:
                        break;
                }
            }
            catch (Exception e) {
                Printer.Print("can't execute the action");
            }
        }
        while (choose != 13) ;
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

    private static void makeMissingOrder() {
        inventoryController.MakeMissingOrder();
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

    private static void setDefectiveProducts() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print("amount: ");
        String amount=scanner.next();
        Printer.Print(inventoryController.setDefectiveProducts(Integer.parseInt(prodId),Integer.parseInt(amount)));
    }


    private static void PrintCostPriceReport() {
        Printer.Print(("\nPlease enter a produce id to print:"));
        String prodId=scanner.next();
        Printer.Print(inventoryController.CostPriceReport(Integer.parseInt(prodId)));
    }

    private static void PrintSalePriceReport() {
        Printer.Print(("\nPlease enter a produce id to print:"));
        String prodId=scanner.next();
        Printer.Print(inventoryController.SalePricesReport(Integer.parseInt(prodId)));
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
        Printer.Print(inventoryController.setCategory(userName,password,Integer.parseInt(prodId),categoriesList));
    }

    private static void shelfToStorage(){
        Printer.Print("\nPlease Enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease Enter amount to take from the shelf to the storage:\n");
        String amount = scanner.next();
        Printer.Print(inventoryController.shelfToStorage(Integer.parseInt(prodId),Integer.parseInt(amount)));
    }
    private static void StorageToShelf(){
        Printer.Print("\nPlease Enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease Enter amount to take from the storage to the shelf:\n");
        String amount = scanner.next();
        Printer.Print(inventoryController.storageToShelf(Integer.parseInt(prodId),Integer.parseInt(amount)));
    }
    private static void addAmountToProduct(){
        Printer.Print("\nPlease Enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease Enter amount to add:\n");
        String amount = scanner.next();
        Printer.Print(inventoryController.addAmountToProduct(Integer.parseInt(prodId),Integer.parseInt(amount)));
    }
    private static void removeAmountFromProduct(){
        Printer.Print("\nPlease Enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease Enter amount to remove:\n");
        String amount = scanner.next();
        Printer.Print(inventoryController.removeAmountFromProduct(Integer.parseInt(prodId),Integer.parseInt(amount)));
    }

    private static void PrintEXPProducts() {
        Printer.Print(inventoryController.ExpiredReport());
    }
    private static void PrintMissingProduct() {
        Printer.Print(inventoryController.NeedToBuyReport());
    }
    private static void PrintProductByCategories() {
        Printer.Print("\nPlease Enter category/ies to print with ',' between them: ");
        Printer.Print("\ncategories: ");
        String categories=scanner.next();
        List<String> categoriesList= Arrays.asList(categories.split(","));
        Printer.Print(inventoryController.CategoryReport(categoriesList));
    }
    private static void printShelfStock() {
        Printer.Print(inventoryController.ShelfReport());
    }

    private static void printStorageStock() {
        Printer.Print(inventoryController.StorageReport());

    }
    private static void PrintTotalStock() {
        Printer.Print(inventoryController.totalStockReport());
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
        Printer.Print(inventoryController.setPriceByCategory(userName,password,categoriesList ,Integer.parseInt(price)));
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
        Printer.Print(inventoryController.setSalePriceById(userName,password,Integer.parseInt(prodId),Integer.parseInt(price)));
    }
    private static void RemoveProduct() {
        Printer.Print("\nPlease Enter the following by given order:\n");
        Printer.Print("username: ");
        String userName=scanner.next();
        Printer.Print("password: ");
        String password=scanner.next();
        Printer.Print("product Id: ");
        String prodId=scanner.next();
        Printer.Print(inventoryController.removeProduct(userName,password,Integer.parseInt( prodId)));
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
        Printer.Print(inventoryController.addProduct(userName,password,Integer.parseInt(prodId),Integer.parseInt(amount),Integer.parseInt(costPrice),Integer.parseInt(salePrice), date,categoriesList,manufacturer,Integer.parseInt(minAmount),place));
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
        Printer.Print("System initiate successfully (:");

        inventoryController.addGlobalManager("Erez", "1234");
        inventoryController.addGlobalManager("Or", "1234");
        inventoryController.addInventoryManager("dana", "1234");
        inventoryController.addInventoryManager("david", "1234");


        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        inventoryController.addProduct("Erez", "1234",1, 20, 5,
                8, date1,cat1,"Osem", 10, "area1");

        List<String> cat2 = new LinkedList<>();
        cat2.add("Halavi");
        cat2.add("Cartons");
        LocalDate date2 = LocalDate.of(20, 8,31);
        inventoryController.addProduct("Or", "1234",2, 30, 3,
                6, date2,cat2,"Tnuva", 20, "area2");
    }
}
