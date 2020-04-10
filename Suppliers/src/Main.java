import javafx.util.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {

    static FacadeController fc = FacadeController.getFacadeController();

    public static void main(String[] args) {
        boolean exit;
        System.out.println("Hello!");
        do {
            exit = displayMainMenu();
        } while (!exit);
    }

    private static boolean displayMainMenu(){
        boolean exit = false;
        int supplierIdCounter = 0;
        int orderIdCounter = 0;
        String choice;
        do {
            System.out.println("\nPlease choose a function:");
            System.out.println("1. Add Supplier");
            System.out.println("2. Manage Supplier");
            System.out.println("3. Make Order");
            System.out.println("4. View previous orders");
            System.out.println("5. Quit");
            System.out.print("Option: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine();  // Read user input
            switch (choice) {
                case "1":
                    supplierIdCounter = addSupplier(supplierIdCounter);
                    break;
                case "2":
                    System.out.print("Supplier's id: ");
                    Scanner scanner2 = new Scanner(System.in);
                    int suppId =scanner2.nextInt();
                    manageSupplier(suppId);
                    break;
                case "3":
                    orderIdCounter= addOrder(orderIdCounter);
                    break;
                case "4":
                    showPreviouslyOrders();
                    break;
                case "5":
                    System.out.println("Thank you for using our system.\nFor your information, no data is being saved so far.\nGoodbye!");
                    exit = true;
                    break;
            }
        } while (!exit);
        return true;
    }

    private static void showPreviouslyOrders() {
        int sizeOfOrders = fc.getOrdersSize();
        if (sizeOfOrders == 0)
            System.out.println("No orders were found.");
        else {
            for (int i = 0; i < sizeOfOrders; i++) {
                List<Pair<Integer, Integer>> list = fc.getItemsInOrderById(i);
                int supplierId = fc.getSupplierIdOfOrder(i);
                System.out.println("Supplier's id: " + supplierId + ", order number: " + i);
                for (int j = 0; j < list.size(); j++) {
                    System.out.println("Item's id :" + list.get(j).getKey() + ", quantity: " + list.get(j).getValue());
                }
                System.out.println();
            }
        }
    }

    private static void addItems(int suppId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Item's identifier: ");
        int itemId = scanner.nextInt();
        System.out.print("Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Description (optional): ");
        String itemDescription = scanner.nextLine();
        System.out.print("Quantity of this item: ");
        int itemQuantity = scanner.nextInt();
        fc.addItemToSupplier(suppId, itemId, itemName, itemDescription, itemQuantity);
        System.out.print("Insert more items? [Y/N] ");
    }

    private static int addSupplier(int supplierIdCounter){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Supplier's name: ");
        String suppName = scanner.nextLine();
        System.out.print("Phone number: ");
        String suppPhone = scanner.nextLine();
        System.out.print("Bank account number: ");
        int suppBankAccount = scanner.nextInt();
        System.out.print("Payment method (Cash, Credit etc.): ");
        String suppPayment = scanner.nextLine();
        System.out.print("Supply schedule: ");
        String suppSchedule = scanner.nextLine();
        System.out.print("Supply location: ");
        String suppLocation = scanner.nextLine();

        fc.addSupplier(supplierIdCounter, suppName, suppPhone, suppBankAccount, suppPayment,
                suppSchedule, suppLocation);

        System.out.print("Insert supplier's items? [Y/N] ");
        String addItems = scanner.nextLine();
        while (addItems.equals("Y") | addItems.equals("y")) {
            addItems(supplierIdCounter);
            addItems = scanner.nextLine();
        }
        int size =fc.getItemsListSize(supplierIdCounter);
        LinkedHashMap agreement = new LinkedHashMap();
        if(size > 0) {
            System.out.println("Please insert supplier's agreement (for each item insert it's cost).");
            for (int i = 0; i < size; i++) {
                System.out.print(fc.getItemNameByIndex(supplierIdCounter,i) + ": ");
                double itemPrice = scanner.nextInt();
                fc.addItemToAgreement(supplierIdCounter,fc.getItemIdByIndex(supplierIdCounter,i),itemPrice);
                //agreement.put(items.get(i).getKey().getId(), itemPrice);
            }
        }
        System.out.println("Supplier added successfully. Id is: " + supplierIdCounter);
        return supplierIdCounter++;

    }

    private static void manageSupplier(int suppId){
        boolean backToManageSupplierMenu;
        do{
            backToManageSupplierMenu = displayManageSupplierMenu(suppId);
        } while (!backToManageSupplierMenu);
    }

    private static boolean displayManageSupplierMenu(int suppId){
        String temp, choice;
        boolean backToManageSupplierMenu = false;
        Scanner scanner = new Scanner(System.in);
        if(fc.findSupplier(suppId)) {
            System.out.println("\nPlease choose a function:");
            System.out.println("1. Add items");
            System.out.println("2. Edit agreement");
            System.out.println("3. Add bill of quantities");
            System.out.println("4. Edit bill of quantities");
            System.out.println("5. Main menu");
            System.out.print("Option: ");
            choice = scanner.nextLine();
            switch (choice) {
                case "1": //Add items
                    String addItems = "Y";
                    int counter = fc.getItemsListSize(suppId);
                    int size = counter;
                    while (addItems.equals("Y") | addItems.equals("y")) {
                        addItems(suppId);
                        addItems = scanner.nextLine();
                        size++;
                    }
                    if (size > 0) {
                        System.out.println("Please insert supplier's agreement (for each item insert it's cost).");
                        for (int i = counter; i < size; i++) {
                            System.out.print(fc.getItemNameByIndex(suppId, i) + ": ");
                            temp = scanner.nextLine();
                            double itemPrice = Integer.parseInt(temp);
                            fc.addItemToAgreement(suppId, fc.getItemIdByIndex(suppId, i), itemPrice);
                        }
                    }
                    break;
                case "2": //Edit agreement
                    String toContinue;
                    do {
                        toContinue = editAgreement(suppId);
                    } while (toContinue.equals("Y") | toContinue.equals("y"));
                    break;
                case "3":
                    addBillOfQuantities(suppId);
                    break;
                case "4":
                    editBillOfQuantities(suppId);
                    break;
                case "5":
                    backToManageSupplierMenu = true;
                    break;
            }
        }
        return backToManageSupplierMenu;
    }

    private static void editBillOfQuantities(int suppId) {
        String ans;
        if (!fc.checkBillOfQuantity(suppId)) {
            System.out.println("No bill of quantities was found.");
        } else {
            do {
                Map<Integer, Pair<Integer, Double>> map = fc.getBillOfQuantities(suppId);
                for (Integer itemId : map.keySet()) {
                    String itemName = fc.getItemNameById(suppId, itemId);
                    Integer itemQuantity = map.get(itemId).getKey();
                    Double itemDiscount = map.get(itemId).getValue();
                    System.out.println(itemName + ", id: " + itemId + ", quantity: " + itemQuantity + ", discount: " + itemDiscount);
               }
                System.out.println("Choose the id of the item you wish to change: ");
                Scanner scanner2 = new Scanner(System.in);
                int chooseId = scanner2.nextInt();
                System.out.println("Enter the new amount: ");
                int newAmount = scanner2.nextInt();
                System.out.println("Enter the new discount: ");
                Double newDiscount = scanner2.nextDouble();
                fc.updateBillOfQuantities(suppId, chooseId, new Pair(newAmount, newDiscount));
                System.out.print("Update any more items? [Y/N] ");
                ans = scanner2.nextLine();
            } while (ans.equals("y") | ans.equals("Y"));
        }
    }

    private static void addBillOfQuantities(int suppId) {
        String ans;
        do {
            displayItems(suppId);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose the id of the item you want to add to this bill: ");
            int itemNum = scanner.nextInt() - 1;
            LinkedHashMap<Integer, Double> terms = fc.showSuppItems(suppId);
            int itemId = new ArrayList<>(terms.keySet()).get(itemNum);
            System.out.print("Enter item's amount: ");
            int itemQuantity = scanner.nextInt();
            System.out.print("Enter item's discount(%): ");
            Double itemDiscount = scanner.nextDouble();
            Pair<Integer, Double> pair = new Pair(itemQuantity, itemDiscount);
            if (!fc.checkBillOfQuantity(suppId)) {
                Map<Integer, Pair<Integer, Double>> map = new HashMap();
                map.put(itemId, pair);
                fc.addBillOfQuantities(suppId, map);
            } else {
                fc.addItemToBillOfQuantities(suppId, itemId, itemQuantity, itemDiscount);
            }
            System.out.print("Add items to this bill? [Y/N] ");
            Scanner scanner2 = new Scanner(System.in);
            ans = scanner2.nextLine();
        } while (ans.equals("y") | ans.equals("Y"));
    }

    private static void displayItems(int suppId) {
        LinkedHashMap<Integer, Double> terms = fc.showSuppItems(suppId);
        for(int i=0; i<terms.size(); i++) {
            String itemName = fc.getItemNameByIndex(suppId,i);
            String itemDesc = fc.getItemDescByIndex(suppId,i);
            int itemId= fc.getItemIdByIndex(suppId,i);
            double itemPrice = fc.getPriceOfItem(suppId,itemId);
            System.out.print(i+1 + ". " + itemName + ", ");
            if(itemDesc.length() > 0)
                System.out.print(itemDesc + ", ");
            System.out.print(itemPrice + " NIS\n");
        }
    }

    private static String editAgreement(int suppId){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nChoose the id of the item it's price you wish to change.");
        LinkedHashMap<Integer, Double> terms = fc.showSuppItems(suppId);
        displayItems(suppId);

        System.out.print("Item's number: ");
        int itemNum = scanner.nextInt() - 1;
        int itemId = new ArrayList<>(terms.keySet()).get(itemNum);
        for(int i=0; i<fc.showSuppItems(suppId).size(); i++){
            if(fc.getItemIdByIndex(suppId,i) == itemId){
                String itemName = fc.getItemNameByIndex(suppId,i);
                String itemDesc = fc.getItemDescByIndex(suppId,i);
                double itemPrice = terms.get(itemId);
                System.out.print(itemName + ", ");
                if(itemDesc.length() > 0)
                    System.out.print(itemDesc + ", ");
                System.out.print(itemPrice + "NIS\n");
                System.out.print("New price: ");
                double newPrice = scanner.nextDouble();
                fc.setItemPrice(suppId, itemId, newPrice);
            }
        }
        System.out.print("Price changed successfully. More items to update? [Y/N] ");
        Scanner scanner2 = new Scanner(System.in);
        return  scanner2.nextLine();
    }

    private static int addOrder(int orderIdCounter) {
        System.out.print("\nEnter the id of the supplier from which you want to order: ");
        Scanner scanner = new Scanner(System.in);
        int suppId = scanner.nextInt();
        displayItems(suppId);
        List<Pair<Integer, Integer>> items = new LinkedList<>();
        String choice;
        do {
            System.out.print("Item's number: ");
            int itemNum = scanner.nextInt() - 1;
            int itemId = new ArrayList<>(fc.showSuppItems(suppId).keySet()).get(itemNum);
            System.out.print("Amount: ");
            int itemAmount = scanner.nextInt();
            items.add(new Pair(itemId, itemAmount));
            System.out.print("Do you want to add more items to the order? [Y|N] ");
            Scanner scanner2 = new Scanner(System.in);
            choice = scanner2.nextLine();
        } while (choice.equals("y") | choice.equals("Y"));
        boolean result = fc.addOrder(orderIdCounter, items, suppId);
        if (result) {
            System.out.println("\nOrder was added successfully.\n");
            return orderIdCounter++;
        } else {
            System.out.println("\nOrder failed, pleas try again.\n");
            return orderIdCounter;
        }
    }

}
