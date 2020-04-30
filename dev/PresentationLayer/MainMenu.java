package PresentationLayer;

import LogicLayer.Inventory;

import java.util.Scanner;

public class MainMenu {
    public static Scanner scanner=new Scanner(System.in);


    public static void main(String[]args) {
        mainLoop();
    }

    private static void mainLoop() {

        Printer.Print("Welcome to Erez & Or Digital Storage !\n");
        Printer.Print("Do you Want to initiate the System with 4 managers and 2 product (Corn and Milk)? Y/N");
        String ans = scanner.next();
        if (ans.equals("Y") || ans.equals("y"))
            InventoryMenu.initiateSystem();
        else
            Printer.Print("system was not initiate");
        int choose;
        do {
            Printer.Print("\n\nPlease choose an action:\n" +
                    "1. Enter Inventory Menu\n" +
                    "2. Enter Suppliers Menu\n" +
                    "" +
                    "3. Quit\n");
            choose = scanner.nextInt();
            try {
                switch (choose) {
                    case 1:
                        InventoryMenu.mainLoop();
                        break;
                    case 2:
                        SupplierMenu.displayMainMenu();
                        break;
                    case 3:
                        break;
                }
            } catch (Exception e) {
                Printer.Print("can't execute the action");
            }
        }
        while (choose != 3);
        System.out.println("Shutting down the system..");
    }

}
