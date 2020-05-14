package PresentationLayer;
import InterfaceLayer.FacadeController;
import LogicLayer.Items;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MainMenu{
    public static Scanner scanner=new Scanner(System.in);
    public static Integer DayOfTheWeek=0;

    public static void main(String[]args) {
        mainLoop();
    }

    private static void mainLoop() {
        Printer.Print("Do you Want to initiate the System? Y/N ");
        String ans = scanner.next();
        if (ans.equals("Y") || ans.equals("y")) {
            initiateSystem();
            InventoryMenu.initiateSystem();
            SupplierMenu.initiateSystem();
        }
        else {
            Printer.Print("system was not initiate");
        }
        int choose;
        Printer.Print("Welcome To EOED Digital Storage And Suppliers Manager!");
        do {
            //ELAD TEST//
            List<Pair<Integer,Integer>> toOrder=new LinkedList<>();
            Pair<Integer,Integer> p = new Pair(1,2);
            Pair<Integer,Integer> p1 = new Pair(2,2);
            toOrder.add(p);
            toOrder.add(p1);
            Map<Integer,Pair<Integer,Double>> orders= FacadeController.getFacadeController().makeOrder(1,toOrder);
            //ELAD TEST//



            Printer.Print("Day: "+(DayOfTheWeek+1));
            Printer.Print("\n\nPlease choose an action:\n" +
                    "1. Enter Inventory & Reports Menu\n" +
                    "2. Enter Suppliers Menu\n" +
                    "3. Show Global Products\n" +
                    "4. Add Global Product\n" +
                    "5. Promote Day\n"+
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
                        DayOfTheWeek=(DayOfTheWeek+1)%7;
                        InventoryMenu.PromoteDay(DayOfTheWeek+1);
                    case 6:
                        break;
                }
            } catch (Exception e) {
                Printer.Print("can't execute the action");
            }
        }
        while (choose != 6);
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

    public static void initiateSystem(){
        Items.addItem(1,"Milk");
        Items.addItem(2,"Corn");
        Items.addItem(3,"Banana");
        Items.addItem(4,"Tomato");
        Items.addItem(5,"Carrot");
        Items.addItem(6,"Chips");
    }

}
