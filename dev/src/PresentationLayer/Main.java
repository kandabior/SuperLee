package src.PresentationLayer;

import src.BusinessLayer.Suppliers.Items;
import src.InterfaceLayer.Suppliers.FacadeController;

import java.time.LocalDate;
import java.util.Calendar;
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
        Printer.Print("Welcome To EOEHRES !\n");
        do {
            Printer.Print("\nDay: " + (LocalDate.now().plusDays(plusDay).toString()));
            Printer.Print("\nPlease choose an action:\n" +
                    "1. Enter Inventory & Reports Menu\n" +
                    "2. Enter Suppliers Menu\n" +
                    "3. Enter Transports Menu\n"+
                    "4. Enter Personal manager Menu\n"+
                    "5. Add New Branch\n"+
                    "6. Promote Day\n" +
                    "7. Quit\n");
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
                        TransportsMenu.main();
                        break;
                    case 4:
                        EmployeesMenu.main();
                        break;
                    case 5:
                        AddNewBranch();
                    case 6:
                        plusDay++;
                        SupplierMenu.PromoteDay(LocalDate.now().plusDays(plusDay));
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

    private static void AddNewBranch() {


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
                return 0;
        }
        return 1;
    }


}