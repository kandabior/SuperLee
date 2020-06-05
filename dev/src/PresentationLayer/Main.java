package src.PresentationLayer;

import src.BusinessLayer.Suppliers.Items;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Integer DayOfTheWeek = getCurrentDay();
    public static int plusDay = 0;


    public static void main(String[] args) {
        mainLoop();
    }

    private static void mainLoop() {
        int choose;
        Printer.Print("Welcome To EOEDHRES !\n");
        do {
            Printer.Print("Day: " + (LocalDate.now().plusDays(plusDay).toString()));
            Printer.Print("\nPlease choose an action:\n" +
                    "1. Enter Inventory & Reports Menu\n" +
                    "2. Enter Suppliers Menu\n" +
                    "3. Show Global Products\n" +
                    //לרביעייה הפחות מוצלחת תוסיפו לפה את הקריאה לתפריטים שלכם
                    "4. Add Global Product\n" +
                    "5. Promote Day\n" +
                    "6. Quit\n");
            choose = scanner.nextInt();
            try {
                switch (choose) {
                    case 1:
                        InventoryMenu.mainLoop();
                        break;
                    case 2:
                        SupplierMenu.displaySupplierMenu();
                        break;
                    case 3:
                        ShowGlobalProduct();
                        break;
                    case 4:
                        AddGlobalProduct();
                        break;
                    case 5:
                        plusDay++;
                        InventoryMenu.PromoteDay((DayOfTheWeek + plusDay) % 7);
                    case 6:
                        break;
                }
            } catch (Exception e) {
                Printer.Print("Can't execute the action.");
            }
        }
        while (choose != 6);
        System.out.println("Shutting down the system...");
    }


    private static Integer getCurrentDay() {
        String day = LocalDate.now().getDayOfWeek().toString();
        switch (day) {
            case "SUNDAY":
                return 1;
            case "MONDAY":
                return 2;
            case "TUESDAY":
                return 3;
            case "WEDNESDAY":
                return 4;
            case "THURSDAY":
                return 5;
            case "FRIDAY":
                return 6;
            case "SATURDAY":
                return 7;
        }
        return 1;
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