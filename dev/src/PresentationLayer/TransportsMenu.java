package src.PresentationLayer;

import src.BusinessLayer.TransportModule.Pool;

import java.util.*;


public class TransportsMenu {
    private static Pool pool;
    private static Scanner input;
    public static void main() {
       pool = Pool.getInstance();
        input = new Scanner(System.in);

        menu();
    }



    private static void menu() {

        while (true) {
            System.out.println("Welcome to Transport's system!\n" +
                    "Please enter option number:\n" +
                    "1. Update missing employees's Transport\n" +
                    "2. Add weight to transport\n" +
                    "3. View Cancellation Requests\n" +
                    "4. View system data\n"+
                    "5. Add or delete a truck\n" +
                    "6. exit");
            try {
                int option = input.nextInt();
                input.nextLine();
                switch (option) {
                    case 1://new transport
                        System.out.println("Updating...");
                        List<Integer> successful = pool.UpdateWaitingTransports();
                        System.out.println("Update successfuly:\n");
                        for (int i = 0; i < successful.size() ; i++) {
                            System.out.println((i+1) + ". Transport number " + successful.get(i));
                        }
                        /*System.out.print("To order a transport first enter an area which the transport will take place\narea: ");
                        int area = input.nextInt();
                        input.nextLine();
                        if (!pool.validArea(area)) {
                            System.out.print("Invalid area, area must have at least one supplier and one store.");
                            break;
                        }
                        System.out.print("Now, choose a date for the transport\nyear: ");
                        int year = input.nextInt();
                        input.nextLine();
                        System.out.print("month: ");
                        int month = input.nextInt();
                        input.nextLine();
                        System.out.print("day: ");
                        int day = input.nextInt();
                        input.nextLine();
                        Calendar cal1 = Calendar.getInstance();
                        cal1.set(Calendar.YEAR, year);
                        cal1.set(Calendar.MONTH, month - 1);
                        cal1.set(Calendar.DAY_OF_MONTH, day);
                        Date date = cal1.getTime();
                        Date now = new Date();
                        if (date.before(now)) {
                            System.out.println("Invalid date");
                            break;
                        }
                        System.out.println("Now, enter stores id for the transport, one at a time\nStores:");
                        print(pool.getAvailableStores(area,date));
                        List<Integer> stores = new LinkedList<>();
                        int idNum;
                        boolean addMore = true;
                        while (addMore) {
                            System.out.print("Id: ");
                            idNum = input.nextInt();
                            input.nextLine();
                            if (!pool.validStore(idNum, area)) {
                                System.out.println("Invalid id,please enter a correct id");
                                System.out.println("If you want to try again enter 1,else enter somthing else");
                                if (!input.nextLine().equals("1"))
                                    menu();
                            } else {
                                stores.add(idNum);
                                System.out.println("If you want to add another store enter 1,else enter somthing else");
                                addMore = (input.nextLine().equals("1"));
                            }
                        }
                        System.out.println("Now, enter suppliers id for the transport, one at a time\nSuppliers");
                        print(pool.getSuppliers(area));
                        List<Integer> suppliers = new LinkedList<>();
                        addMore = true;
                        while (addMore) {
                            System.out.print("Id: ");
                            idNum = input.nextInt();
                            input.nextLine();
                            if (!pool.validSupplier(idNum, area)) {
                                System.out.println("Invalid id,please enter a correct id");
                                System.out.println("If you want to try again enter 1,else enter somthing else");
                                if (!input.nextLine().equals("1"))
                                    menu();
                            } else {
                                suppliers.add(idNum);
                                System.out.println("If you want to add another supplier enter 1,else enter somthing else");
                                addMore = (input.nextLine().equals("1"));
                            }
                        }
                        System.out.println("Available trucks");
                        print(pool.getTrucks(date));
                        System.out.print("Now, enter the id of the truck you want use\nId:  ");
                        String truckId = input.nextLine();
                        while (!pool.validTruck(truckId, date)) {
                            System.out.println("Invalid id,please enter a correct id");
                            System.out.println("If you want to try again enter 1,else enter somthing else");
                            if (!input.nextLine().equals("1"))
                                menu();
                            else {
                                System.out.print("Id: ");
                                truckId = input.nextLine();
                            }
                        }
                        System.out.println("Now, choose a driver");
                        List<Integer> availableDrivers = pool.getDrivers(truckId, date);
                        List<String> drivers = pool.getDriversName(availableDrivers);
                        System.out.println("Available drivers");
                        print(drivers);//display the drivers that capable of driving this truck
                        if (availableDrivers.isEmpty()) {
                            System.out.println("Sorry, no driver is available at this date\nTry again with a different date.");
                            menu();
                        }
                        System.out.print("Id: ");
                        int driverId = input.nextInt();
                        input.nextLine();
                        //while (!pool.validDriver(driverId, truckId, date)) {
                        while (!availableDrivers.contains(driverId)){
                            System.out.println("Invalid id,please enter a correct id");
                            System.out.println("If you want to try again enter 1,else enter somthing else");
                            if (!input.nextLine().equals("1"))
                                menu();
                            else {
                                System.out.print("Id: ");
                                driverId = input.nextInt();
                                input.nextLine();

                            }
                        }
                        String driverName = pool.getDriverName(driverId);
                        pool.addDateToTruck(truckId, date);
                        pool.addDateToDriver(driverId, date);
                       // pool.addDoc(area, date, truckId, driverId, driverName, stores, suppliers);
                        System.out.println("new transport was added !:)");*/
                        break;
                    case 2://add weight
                        System.out.print("Enter transport id\nId: ");
                        int docId = input.nextInt();
                        input.nextLine();
                        while (!pool.validTransport(docId)) {
                            System.out.println("Invalid id,please enter a correct id");
                            System.out.println("If you want to try again enter 1,else enter somthing else");
                            if (!input.nextLine().equals("1"))
                                menu();
                            System.out.print("Id: ");
                            docId = input.nextInt();
                            input.nextLine();
                        }
                        if(pool.transportIsToday(docId)) {
                            System.out.print("Please enter the truck total weight\nTotal weight: ");
                            double total = input.nextDouble();
                            input.nextLine();
                            double difference = pool.addWeight(docId, total);
                            if (difference == 0) {
                                System.out.println("The weight is valid!");
                            } else {
                                System.out.println("The weight is Invalid! therefore the transport failed,\nthe weight exceeded in " + difference
                                        + " tons,\nyou can see the transport detail under failed transports,\n" +
                                        " and create new one if you want.");
                                pool.freeTruckDate(docId);
                                pool.freeDriverDate(docId);
                            }
                        }
                        else{
                            System.out.println("Weight can be added only at the transport's date!");
                        }
                        break;
                    case 3://View Cancellation Requests
                        System.out.println("Please choose the option of the transport you would like to cancel or -1 to return to menu:");
                        List<Integer> requests = pool.getRequest();
                        List<String> reqString = new LinkedList<>();
                        for (int i = 0; i < requests.size(); i++) {
                            String s = pool.getReqString(requests.get(i));
                            System.out.println(i +". " + s);
                        }
                        System.out.print("Option number: ");
                        int op = input.nextInt();
                        input.nextLine();
                        if(op != -1) {
                            if ((op < 0 | op >= requests.size()) || reqString.get(op).equals("Invalid Order ID"))
                                System.out.println("Invalid option");
                            else {
                                int orderId = requests.get(op);
                                pool.CancelRequest(orderId);
                                System.out.println("Transport Canceled successfully");
                            }
                        }

                        break;
                    case 4://View transport system data
                        System.out.println("Please enter option number:\n" +
                                "1. View pending transports\n" +
                                "2. View failed transports\n" +
                                "3. View successful transports\n" +
                                "4. View trucks\n" +
                                "5. View stores\n");
                        option = input.nextInt();
                        input.nextLine();
                        switch (option) {
                            case 1:
                                System.out.println("Pending transports");
                                print(pool.PrintPendingDoc());
                                break;
                            case 2:
                                System.out.println("Failed transports");
                                print(pool.PrintFailDoc());
                                break;
                            case 3:
                                System.out.println("successful transports");
                                print(pool.printSuccessDoc());
                                break;
                            case 4:
                                System.out.println("trucks");
                                print(pool.TtoString());
                                break;
                            case 5:
                                System.out.println("stores");
                                print(pool.StoreToString());
                                break;
                            default:
                                System.out.println("Invalid input");
                                break;

                        }
                        break;
                    case 5://add/delete truck
                        System.out.println("Please enter option number:\n" +
                                "1. Add truck\n" +
                                "2. Delete truck");
                        option = input.nextInt();
                        input.nextLine();
                        switch (option) {
                            case 1://add truck
                                System.out.println("Enter id, model, weight and max weight of the truck");
                                System.out.print("id: ");
                                String id = input.nextLine();
                                System.out.print("model: ");
                                String model = input.nextLine();
                                System.out.print("weight: ");
                                double weight = input.nextDouble();
                                System.out.print("max weight: ");
                                double maxWeight = input.nextDouble();
                                if (pool.isUniqueTruck(id)) {
                                    pool.addTruck(id, model, weight, maxWeight);
                                    System.out.println("Truck add successfully");
                                } else
                                    System.out.println("This id already exist please try again the next time");
                                break;
                            case 2://delete truck
                                System.out.println("Trucks in the system:");
                                print(pool.TtoString());
                                System.out.print("Enter the id of the truck you want to delete\nid: ");
                                id = input.nextLine();
                                if (pool.isUniqueTruck(id)) //the truck doesnt exists
                                    System.out.println("Truck doesn't exist");
                                else {
                                    if (pool.truckIsBusy(id))
                                        System.out.println("Truck is in a pending transport,therefore - cannot be removed");
                                    else {
                                        pool.removeTruck(id);
                                        System.out.println("Truck removed successfully");
                                    }
                                }
                                break;
                            default:
                                System.out.println("Invalid option number!");
                                break;
                        }
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid option number!");
                        break;
                }
                System.out.println("\n\n");
            }
            catch (Exception e){
                System.out.println("Invalid input!");
                input.nextLine();
            }
        }
    }
    private static void print(List<String> toPrint){
        Iterator iter = toPrint.iterator();
        while (iter.hasNext()){
            System.out.println(iter.next());
        }
    }

}
