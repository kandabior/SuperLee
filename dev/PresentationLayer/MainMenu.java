package PresentationLayer;

import LogicLayer.Inventory;
import LogicLayer.Items;

import java.util.Scanner;

public class MainMenu {
    public static Scanner scanner=new Scanner(System.in);


    public static void main(String[]args) {
        mainLoop();
    }

    private static void mainLoop() {
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
                    "3. Show Global Products\n" +
                    "4. Add Global Product\n" +
                    "5. Quit\n");
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
                        ShowGlobalProduct();
                        break;
                    case 4:
                        AddGlobalProduct();
                        break;
                    case 5:
                        break;
                }
            } catch (Exception e) {
                Printer.Print("can't execute the action");
            }
        }
        while (choose != 3);
        System.out.println("Shutting down the system..");
    }

    private static void AddGlobalProduct() {
        Printer.Print("\nPlease enter Product Id:\n");
        String prodId = scanner.next();
        Printer.Print("\nPlease enter product name:\n");
        String amount = scanner.next();
        if(!Items.addItem(Integer.parseInt(prodId),amount)){
            System.out.println("product already exist, please enter different product id");
        }
    }

    private static void ShowGlobalProduct() {
        Items.PrintAllItems();
    }

}
