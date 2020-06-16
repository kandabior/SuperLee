package src.PresentationLayer;

import src.InterfaceLayer.Suppliers.FacadeController;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;

public class SupplierMenu {
    static FacadeController fc = FacadeController.getFacadeController();
    static int supplierIdCounter = fc.getSuppliersCounter() + 1;

    public static boolean displaySupplierMenu(){
        try {
            boolean exit = false;
            int suppId;
            String choice;
            do {
                System.out.println("\nPlease choose an action:");
                System.out.println("1. Add supplier");
                System.out.println("2. Delete supplier");
                System.out.println("3. Manage supplier");
                System.out.println("4. View previous orders");
                System.out.println("5. Show all suppliers");
                System.out.println("6. Main menu");
                System.out.print("Option: ");
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        supplierIdCounter = addSupplier(supplierIdCounter);
                        break;
                    case "2":
                        System.out.print("Supplier's id: ");
                        Scanner scanner2 = new Scanner(System.in);
                        suppId = scanner2.nextInt();
                        deleteSupplier(suppId);
                        break;
                    case "3":
                        System.out.print("Supplier's id: ");
                        Scanner scanner3 = new Scanner(System.in);
                        suppId = scanner3.nextInt();
                        manageSupplier(suppId);
                        break;
                     case "4":
                         showPreviousOrders();
                        break;
                    case "5":
                        showSuppliers();
                        break;
                    case "6":
                        exit = true;
                        break;
                }
            } while (!exit);
            return true;
        }catch (Exception e)
        {
            System.out.println("Invalid input, please try again.");
            return false;
        }
    }

    private static void showSuppliers() {
        List<List<Object>> suppliers = fc.getAllSuppliers();
        if(suppliers.size()==0) {
            System.out.print("There are no suppliers in the system.\n");
        }
        else {
            for (List<Object> list : suppliers) {
                System.out.println("ID: " + list.get(0).toString() + "\tSupplier Name: " + list.get(1) +
                        "\tPhone Number: " + list.get(2) + "\tPayment: " + list.get(3) + "\tSupply Schedule: " + list.get(4) +
                        "\tSupply Location: " + list.get(5) + "\n");
            }
        }
    }

//    private static void updateOrderStatus() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter id of the arrived order: ");
//        int choice = scanner.nextInt();
//        if (fc.checkIfOrderExists(choice)) {
//            if(fc.updateOrderStatus(choice))
//            {
//                System.out.println("Status order is updated to COMPLETE.");
//            }
//            else
//                System.out.println("Status order Fail to change");
//        } else {
//            System.out.println("Order id was not found.");
//        }
//    }

    private static void deleteSupplier(int suppId) {
        if(fc.deleteSupplier(suppId))
            System.out.println("Supplier was deleted.");
        else
            System.out.println("Supplier id does not exist in the system.");
    }

    private static void showPreviousOrders() {

        int sizeOfOrders = fc.getOrdersSize();
        if (sizeOfOrders == 0)
            System.out.println("No orders were found.");
        else {
            for (int i = 0; i < sizeOfOrders; i++) {

                List<List<Object>> list = fc.getOrdersLineByOrderIndex(i);
                List<Object> suppList = fc.getSupplierDetails(i);
                int supplierId = (int)suppList.get(0);
                String supplierName = (String)suppList.get(1);
                String supplierAddress = (String)suppList.get(2);
                int orderId = (int)suppList.get(3);
                String orderDate = (String) suppList.get(4);
                String suppPhone =(String)suppList.get(5);
                int branchId= (int)suppList.get(6);

                System.out.println("------------------------------------------------------------------------------\n");
                System.out.println("Supplier's name: " + supplierName + "\t" + "Supplier's address: " + supplierAddress + "\tOrderId: " + orderId + "\tBranch Id: " + branchId + "\n");
                System.out.println("Supplier's id: " + supplierId + "\t" + "Date: " + orderDate + "\t" + "Supplier's phone: " + suppPhone + "\n");
                System.out.println("\nItem Id\t\tItem Name\tQuantity\tPrice\t\tDiscount\tFinal Cost\n");
                for (int j = 0; j < list.size(); j++) {
                    System.out.println(list.get(j).get(0) + "\t\t" + list.get(j).get(1) + "\t\t" + list.get(j).get(2) + "\t\t" + list.get(j).get(3) + "\t\t" + list.get(j).get(4) + "\t\t" + list.get(j).get(5) + "\n");
                }
                System.out.println("Total amount: " + fc.getTotalOrderMoney(orderId));
                System.out.println("Status: " + fc.getOrderStatus(orderId));
                System.out.println("\n------------------------------------------------------------------------------\n");
            }
        }
    }

    private static int addItems(int suppId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Item's Global identifier: ");
        int itemId = scanner.nextInt();
        while (fc.validateItemId(suppId, itemId)) {
            System.out.println("This supplier already has this item, Please enter another one.");
            System.out.print("Item's identifier: ");
            itemId = scanner.nextInt();
        }
        if(fc.checkIfItemExist(itemId))
        {
            scanner = new Scanner(System.in);
            System.out.print("Enter local identifier: ");
            int itemLocalId = scanner.nextInt();

            while(!fc.addItemToSupplier(suppId, itemId,itemLocalId))
            {
                System.out.print("\nThis supplier already has this Local Item ID, Please try again ");
                System.out.print("\nEnter local identifier: ");
                itemLocalId = scanner.nextInt();
            }
            String name = fc.getItemNameById(itemId);
            System.out.print("Name: " + name);
            System.out.print("\nInsert more items? [Y/N] ");
        }
        else
        {
            System.out.print("Item was not found. Insert more items? [Y/N] ");
        }
        return itemId;
    }

    private static int addSupplier(int supplierIdCounter) {
        int[] daysInt = {};
        Scanner scanner = new Scanner(System.in);
        System.out.print("Supplier's name: ");
        String suppName = scanner.nextLine();
        System.out.print("Phone number: ");
        String suppPhone = scanner.nextLine();
        System.out.print("Bank account number: ");
        int suppBankAccount = scanner.nextInt();
        System.out.print("Payment method (Cash, Credit etc.): ");
        scanner = new Scanner(System.in);
        String suppPayment = scanner.nextLine();
        System.out.println("Supplier's schedule & transportation: \nA - Fixed days, transports solo\nB - Fixed days, super's transportation\n" +
                "C - When there's an order, transports solo\nD - When there's an order, super's transportation");
        String suppType = scanner.nextLine();
        if (!(suppType.equals("A") | suppType.equals("B") | suppType.equals("C") | suppType.equals("D"))) {
            System.out.println("Invalid input. Please try again.");
            return supplierIdCounter;
        }
        if (suppType.equals("A") | suppType.equals("B")) {// TODO check failed scenario
            System.out.print("Supply days: (insert 1-7, with spaces) ");
            String suppSchedule = scanner.nextLine();
            String[] days = suppSchedule.split(" ");
            daysInt = new int[days.length];
            for (int i = 0; i < days.length; i++) {
                daysInt[i] = Integer.parseInt(days[i]);
            }
        }
        System.out.print("Supply location: ");
        String suppLocation = scanner.nextLine();
        if (suppName.isEmpty() | suppPhone.isEmpty() | suppPayment.isEmpty() | suppType.isEmpty() | suppLocation.isEmpty()) {
            System.out.println("One or more of supplier's details is empty. Please try again.");
        } else {
            if (fc.addSupplier(supplierIdCounter, suppName, suppPhone, suppBankAccount, suppPayment, suppType, suppLocation)) {
                if (((suppType.equals("A") | suppType.equals("B")) && fc.addSupplierDays(supplierIdCounter, daysInt)) |
                        ((suppType.equals("C") | suppType.equals("D")))) {
                    System.out.println("Supplier added successfully. Id is: " + supplierIdCounter);
                    System.out.print("Insert supplier's items? [Y/N] ");
                    String toAdd = scanner.nextLine();
                    while (toAdd.equals("Y") | toAdd.equals("y")) {
                        addItems(supplierIdCounter);
                        toAdd = scanner.nextLine();
                    }
                    int size = fc.getItemsListSize(supplierIdCounter);
                    if (size > 0) {
                        List<String> supplierItemsName = fc.getSupplierItemsNames(supplierIdCounter);
                        List<Integer> supplierItemsId = fc.getSupplierItemsId(supplierIdCounter);
                        System.out.println("Please insert supplier's agreement (for each item insert it's cost).");
                        for (int i = 0; i < size; i++) {
                            System.out.print(supplierItemsName.get(i) + ": ");
                            double itemPrice = scanner.nextInt();
                            if (!fc.addItemToAgreement(supplierIdCounter, supplierItemsId.get(i), itemPrice))
                                System.out.println("Error: Item '" + supplierItemsName.get(i) + "' does not belong to the agreement.");
                        }
                    }
                    supplierIdCounter++;
                } else
                    System.out.println("Supplier cannot be added to the system.");
            }
        }
        return supplierIdCounter;
    }

    private static void manageSupplier(int suppId){
        boolean backToManageSupplierMenu;
        if(!fc.findSupplier(suppId)) {
            System.out.println("Supplier with this id does not exist in the system.");
            return;
        }
        do{
            backToManageSupplierMenu = displayManageSupplierMenu(suppId);
        } while (!backToManageSupplierMenu);
    }

    private static boolean displayManageSupplierMenu(int suppId){
        String choice;
        boolean backToManageSupplierMenu = false;
        Scanner scanner = new Scanner(System.in);
        if(fc.findSupplier(suppId)) {
            System.out.println("\nPlease choose a function:");
            System.out.println("1. Add items");
            System.out.println("2. Edit agreement");
            System.out.println("3. Add bill of quantities");
            System.out.println("4. Edit bill of quantities");
            System.out.println("5. Show suppliers items");
            System.out.println("6. Back");
            System.out.print("Option: ");
            choice = scanner.nextLine();
            switch (choice) {
                case "1": //Add items (to supplier's list)
                    String toAdd= "Y";
                    int counter = fc.getItemsListSize(suppId);
                    int newItems = counter;
                    List<Integer> newItemsIds = new LinkedList<>();
                    while (toAdd.equals("Y") | toAdd.equals("y")) {
                        newItemsIds.add(addItems(suppId));
                        newItems+=newItemsIds.size();
                        toAdd = scanner.nextLine();
                    }
                    if(fc.getItemsListSize(suppId) == 0) {
                        System.out.println("Supplier has no items.");
                        break;
                    }
                    if (newItems > counter) { //add items to agreement
                        System.out.println("Please insert supplier's agreement (for each item insert it's cost).");
                        for (int i = 0; i < newItemsIds.size(); i++) {
                            System.out.print(fc.getItemNameById((newItemsIds.get(i))) + ": ");
                            double itemPrice = scanner.nextDouble();
                            if (!fc.addItemToAgreement(suppId, newItemsIds.get(i), itemPrice))
                                System.out.println("Error: Item '" + fc.getItemNameById((newItemsIds.get(i))) + "' does not belong to the agreement.");
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
                    showSuppliersItems(suppId);
                    break;
                case "6":
                    backToManageSupplierMenu = true;
                    break;
            }
        }
        return backToManageSupplierMenu;
    }

    private static void showSuppliersItems(int suppId) {
        displayItems(suppId);
    }

    private static void editBillOfQuantities(int suppId) {
        String ans = "";
        if (!fc.checkBillOfQuantity(suppId)) {
            System.out.println("No bill of quantities was found.");
        } else {
            do {
                Map<Integer, Pair<Integer, Double>> map = fc.getBillOfQuantities(suppId);
                for (Integer itemId : map.keySet()) {
                    String itemName = fc.getItemNameById(itemId);
                    Integer itemQuantity = map.get(itemId).getKey();
                    Double itemDiscount = map.get(itemId).getValue();
                    System.out.println(itemName + ", id: " + itemId + ", quantity: " + itemQuantity + ", discount: " + itemDiscount);
                }
                System.out.print("Choose the id of the item you wish to change: ");
                Scanner scanner2 = new Scanner(System.in);
                int itemId = scanner2.nextInt();
                while (!fc.validateItemIdInBill(suppId, itemId)) {
                    System.out.print("This supplier Does not have this item.\nEnter another one\n");
                    System.out.print("Item's identifier: ");
                    itemId = scanner2.nextInt();
                }
                System.out.print("Change [c] or delete [d]? ");
                scanner2 = new Scanner(System.in);
                String choice = scanner2.nextLine();
                if (choice.equals("C") | choice.equals("c"))
                    changeInBillOfQuantities(suppId, itemId);
                else if (choice.equals("D") | choice.equals("d"))
                    ans = deleteFromBillOfQuantities(suppId, itemId);
                else {
                    System.out.println("Invalid input.");
                    ans = "n";
                }
                if (!ans.equals("n")) {
                    System.out.print("Update any more items? [Y/N] ");
                    ans = scanner2.nextLine();
                }
            } while (ans.equals("y") | ans.equals("Y"));
        }
    }

    private static void changeInBillOfQuantities(int suppId, int itemId){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the new amount: ");
        int newAmount = scanner.nextInt();
        System.out.println("Enter the new discount: ");
        Double newDiscount = scanner.nextDouble();
        fc.changeInBillOfQuantities(suppId, itemId, new Pair(newAmount, newDiscount));
    }

    private static String deleteFromBillOfQuantities(int suppId, int itemId){
        fc.deleteFromBillOfQuantities(suppId, itemId);
        if(fc.getBillSize(suppId) == 0) {
            fc.deleteBillOfQuantities(suppId);
            System.out.println("No more items in this bill of quantities, therefor it was deleted.");
            return "n";
        }
        return "y";
    }

    private static void addBillOfQuantities(int suppId) {
        String ans;
        do {
            displayItems(suppId);
            Scanner scanner = new Scanner(System.in);
            System.out.print("Choose the id of the item you want to add to this bill: ");
            int itemId = scanner.nextInt();
            while (!fc.validateItemId(suppId, itemId)) {
                System.out.print("This supplier does not have this item, please enter another one. To exit enter -1.\n");
                System.out.print("Item's identifier: ");
                itemId = scanner.nextInt();
                if(itemId == -1) {
                    displayManageSupplierMenu(suppId);
                    return;
                }
            }
            System.out.print("Enter item's amount: ");
            int itemQuantity = scanner.nextInt();
            System.out.print("Enter item's discount: (i.e. 0.5) ");
            Double itemDiscount = scanner.nextDouble();
            Pair<Integer, Double> pair = new Pair(itemQuantity, itemDiscount);
            if (!fc.checkBillOfQuantity(suppId)) {
                Map<Integer, Pair<Integer, Double>> map = new HashMap();
                map.put(itemId, pair);
                fc.createBillOfQuantities(suppId, map);
            } else {
                fc.addItemToBillOfQuantities(suppId, itemId, itemQuantity, itemDiscount);
            }
            System.out.print("Add more items to this bill? [Y/N] ");
            Scanner scanner2 = new Scanner(System.in);
            ans = scanner2.nextLine();
        } while (ans.equals("y") | ans.equals("Y"));
    }

    private static void displayItems(int suppId) {
        LinkedHashMap<Integer, Double> terms = fc.showSuppItems(suppId);
        List<Integer> listLocalId = getLocalItemsIds(suppId,terms);
        Iterator<Integer> iter = terms.keySet().iterator();
        for (int i = 0; i < terms.size(); i++) {
            if (iter.hasNext()) {
                int itemId = iter.next();
                String itemName = fc.getItemNameById(itemId);
                double itemPrice = terms.get(itemId);
                System.out.println("Global Id: "+itemId+" Local Item Id: "+listLocalId.get(i) +" item name: "+ itemName + ", " + itemPrice + " NIS");
                if(itemName=="apple") System.out.println("  ,--./,-.\n" +
                        " / #      \\\n" +
                        "|          |\n" +
                        " \\        /\n" +
                        "  `._,._,'");
                if(itemName.equals("banana")) System.out.println("" +
                        "        .-.\n" +
                        "       /  |\n" +
                        "      |  /\n" +
                        "   .'\\|.-; _\n" +
                        "  /.-.;\\  |\\|\n" +
                        "  '   |'._/ `\n" +
                        "      |  \\\n" +
                        "       \\  |\n" +
                        "        '-'");

                if(itemName.equals("chocolate ")) System.out.println("" +
                        "  _____________,-.___     _\n" +
                        "     |____        { {]_]_]   [_]\n" +
                        "     |___ `-----.__\\ \\_]_]_    . `\n" +
                        "     |   `-----.____} }]_]_]_   ,\n" +
                        "     |_____________/ {_]_]_]_] , `\n" +
                        "                    `-'");
                if(itemName.equals("pizza")) System.out.println("" +
                        "// \"\"--.._\n" +
                        "||  (_)  _ \"-._\n" +
                        "||    _ (_)    '-.\n" +
                        "||   (_)   __..-'\n" +
                        " \\\\__..--\"\"");
                if(itemName.equals("cheese")) System.out.println(" " +
                        "          ___\n" +
                        "        .'o O'-._\n" +
                        "       / O o_.-`|\n" +
                        "      /O_.-'  O |\n" +
                        "      | o   o .-`\n" +
                        "      |o O_.-'\n" +
                        "      '--`");
                if(itemName.equals("milk")) System.out.println("" +
                        "   _________\n" +
                        "  | _______ |\n" +
                        " / \\         \\\n" +
                        "/___\\_________\\\n" +
                        "|   | \\       |\n" +
                        "|   |  \\      |\n" +
                        "|   |   \\     |\n" +
                        "|   | M  \\    |\n" +
                        "|   |     \\   |\n" +
                        "|   |\\  I  \\  |\n" +
                        "|   | \\     \\ |\n" +
                        "|   |  \\  L  \\|\n" +
                        "|   |   \\     |\n" +
                        "|   |    \\  K |\n" +
                        "|   |     \\   |\n" +
                        "|   |      \\  |\n" +
                        "|___|_______\\_|");
                if(itemName.equals("tomato")) System.out.println("" +
                        "  ,\n" +
                        "                  /.\\\n" +
                        "                 //_`\\\n" +
                        "            _.-`| \\ ``._\n" +
                        "        .-''`-.       _.'`.\n" +
                        "      .'      / /'\\/`.\\    `. \n" +
                        "     /   .    |/         `.  \\\n" +
                        "    '   /                  \\  ;\n" +
                        "   :   '            \\       : :\n" +
                        "   ;  ;             ;      /  .\n" +
                        "    ' :             .     '  /\n" +
                        "     \\ \\           /       .'\n" +
                        "      `.`        .'      .'\n" +
                        "        `-..___....----`");
                if(itemName.equals("baloon")) System.out.println("" +
                        "        ,,,,,,,,,,,,,\n" +
                        "    .;;;;;;;;;;;;;;;;;;;,.\n" +
                        "  .;;;;;;;;;;;;;;;;;;;;;;;;,\n" +
                        ".;;;;;;;;;;;;;;;;;;;;;;;;;;;;.\n" +
                        ";;;;;@;;;;;;;;;;;;;;;;;;;;;;;;' .............\n" +
                        ";;;;@@;;;;;;;;;;;;;;;;;;;;;;;;'.................\n" +
                        ";;;;@@;;;;;;;;;;;;;;;;;;;;;;;;'...................\n" +
                        "`;;;;@;;;;;;;;;;;;;;;@;;;;;;;'.....................\n" +
                        " `;;;;;;;;;;;;;;;;;;;@@;;;;;'..................;....\n" +
                        "   `;;;;;;;;;;;;;;;;@@;;;;'....................;;...\n" +
                        "     `;;;;;;;;;;;;;@;;;;'...;.................;;....\n" +
                        "        `;;;;;;;;;;;;'   ...;;...............;.....\n" +
                        "           `;;;;;;'        ...;;..................\n" +
                        "              ;;              ..;...............\n" +
                        "              `                  ............\n" +
                        "             `                      ......\n" +
                        "            `                         ..\n" +
                        "           `                           '\n" +
                        "          `                           '\n" +
                        "         `                           '\n" +
                        "        `                           `\n" +
                        "        `                           `,\n" +
                        "        `\n" +
                        "         `\n" +
                        "           `.");
            }
        }
    }

    private static List<Integer> getLocalItemsIds(int suppId,  LinkedHashMap<Integer, Double> terms) {
        List<Integer> temp = new LinkedList<>();
        Iterator<Integer> iter = terms.keySet().iterator();
        for (int i = 0; i < terms.size(); i++) {
            if (iter.hasNext())
                temp.add(iter.next());
        }
        return fc.getLocalItemsIds(suppId,temp);
    }


    private static String editAgreement(int suppId){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nChoose the id of the item it's price you wish to change.");
        LinkedHashMap<Integer, Double> terms = fc.showSuppItems(suppId);
        displayItems(suppId);

        System.out.print("Enter global item's id: ");
        int itemId = scanner.nextInt();
        while (!fc.validateItemId(suppId, itemId)) {
            System.out.println("This supplier does not have this item, enter another one. To exit enter -1.");
            System.out.print("Item's identifier: ");
            itemId = scanner.nextInt();
            if(itemId == -1){
                displayManageSupplierMenu(suppId);
                return "n";
            }
        }
        Iterator<Integer> iter = terms.keySet().iterator();
        for(int i = 0; i < terms.size(); i++){
            if(iter.hasNext() && iter.next() == itemId) {
                System.out.print(fc.getItemNameById(itemId) + ": " + terms.get(itemId) + " NIS\nNew price: ");
                double newPrice = scanner.nextDouble();
                fc.setItemPrice(suppId, itemId, newPrice);
            }
        }
        System.out.print("Price changed successfully. More items to update? [Y/N] ");
        Scanner scanner2 = new Scanner(System.in);
        return scanner2.nextLine();
    }

    public static void PromoteDay(LocalDate day) {
        fc.PromoteDay(day);
    }

//    public static void initiateSystem() {
//        FacadeController.getFacadeController().addSupplier(supplierIdCounter++,"itay","05550004",
//                23423,"cash","Sunday","Beer-Sheva");
//        FacadeController.getFacadeController().addSupplier(supplierIdCounter++,"moshe","2342352425",
//                23333423,"cash","Saturday","Tel Aviv");
//        FacadeController.getFacadeController().addItemToAgreement(1,1,2.0);
//        FacadeController.getFacadeController().addItemToSupplier(1,1);
//        FacadeController.getFacadeController().addItemToAgreement(1,2,5.0);
//        FacadeController.getFacadeController().addItemToSupplier(1,2);
//        FacadeController.getFacadeController().addItemToAgreement(1,3,10.0);
//        FacadeController.getFacadeController().addItemToSupplier(1,3);
//        Map<Integer, Pair<Integer,Double>> map = new HashMap<>();
//        Pair<Integer,Double> p = new Pair<>(10, 0.7);
//        map.put(2,p);
//        FacadeController.getFacadeController().createBillOfQuantities(1,map);
//        FacadeController.getFacadeController().addItemToAgreement(2,1,6.0);
//        FacadeController.getFacadeController().addItemToSupplier(2,1);
//        FacadeController.getFacadeController().addItemToAgreement(2,2,7.0);
//        FacadeController.getFacadeController().addItemToSupplier(2,2);
//        FacadeController.getFacadeController().addItemToAgreement(2,3,5.0);
//        FacadeController.getFacadeController().addItemToSupplier(2,3);
//
//
//    }

}
