package PresentationLayer;

import BusinessLayer.TransportModule.Pool;

import java.util.*;
import java.util.Date;


public class UserMenu {
    private static Pool pool;

    public static void main() {
        pool = Pool.getInstance();
        start();
        menu();
    }


    private static void start() {
        pool.addDriver("100","a","C1");
        pool.addDriver("101","b","C");
        pool.addTruck("200","1000",12,20);
        pool.addTruck("201","1000",13,20);
        pool.addStore("ashdod1","111111","a1",1);
        pool.addStore("ashdod2","222222","a2",1);
        pool.addStore("ashdod3","333333","a3",2);
        pool.addSupplier("ashdod4","444444","a4",1);
        pool.addSupplier("ashdod5","555555","a5",1);
        List<Integer> stores = new LinkedList<>();
        stores.add(1);
        stores.add(2);
        List<Integer> suppliers = new LinkedList<>();
        suppliers.add(4);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, 12-1);//Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        //Calendar cal2 = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, 11-1);//Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date2 = cal.getTime();
        pool.addDoc(1,date,"200","100","a",stores,suppliers);
        pool.addDateToDriver("100",date);
        pool.addDateToTruck("200",date);
        pool.addDoc(1,date2,"201","101","b",stores,suppliers);
        pool.addDateToDriver("101",date2);
        pool.addDateToTruck("201",date2);


    }

    private static void menu() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to Transport's system!\nPlease enter option number:\n" +
                    "1. New transport\n2. Add weight to transport\n3. Add items to transport\n4. View system data\n"+
                    "5. Add, delete or update a site\n6. Add or delete a driver\n7. Add or delete a truck\n8. exit");
            try {
                int option = input.nextInt();
                input.nextLine();
                switch (option) {
                    case 1://new transport
                        System.out.print("To order a transport first enter an area which the transport will take place\narea: ");
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
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month - 1);
                        cal.set(Calendar.DAY_OF_MONTH, day);
                        Date date = cal.getTime();
                        Date now = new Date();
                        if (date.before(now)) {
                            System.out.println("Invalid date");
                            break;
                        }
                        System.out.println("Now, enter stores id for the transport, one at a time\nStores:");
                        print(pool.getStores(area));
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
                        List<String> availbleDrivers = pool.getDrivers(truckId, date);
                        System.out.println("Available drivers");
                        print(availbleDrivers);//display the drivers that capable of driving this truck
                        if (availbleDrivers.isEmpty()) {
                            System.out.println("Sorry, no driver is available at this date\nTry again with a different date.");
                            menu();
                        }
                        System.out.print("Id: ");
                        String driverId = input.nextLine();
                        while (!pool.validDriver(driverId, truckId, date)) {
                            System.out.println("Invalid id,please enter a correct id");
                            System.out.println("If you want to try again enter 1,else enter somthing else");
                            if (!input.nextLine().equals("1"))
                                menu();
                            else {
                                System.out.print("Id: ");
                                driverId = input.nextLine();
                            }
                        }
                        String driverName = pool.getDriverName(driverId);
                        pool.addDateToTruck(truckId, date);
                        pool.addDateToDriver(driverId, date);
                        pool.addDoc(area, date, truckId, driverId, driverName, stores, suppliers);
                        System.out.println("new transport was added !:)");
                        break;
                    case 2://add weight
                        System.out.print("Enter transport id\nId: ");
                        int docId = input.nextInt();
                        input.nextLine();
                        while (!pool.validTansport(docId)) {
                            System.out.println("Invalid id,please enter a correct id");
                            System.out.println("If you want to try again enter 1,else enter somthing else");
                            if (!input.nextLine().equals("1"))
                                menu();
                            System.out.print("Id: ");
                            docId = input.nextInt();
                            input.nextLine();
                        }
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
                        break;
                    case 3://add items
                        System.out.print("Enter transport id\nId: ");
                        docId = input.nextInt();
                        input.nextLine();
                        while (!pool.validTansport(docId)) {
                            System.out.println("Invalid id,please enter a correct id");
                            System.out.println("If you want to try again enter 1,else enter somthing else");
                            if (!input.nextLine().equals("1"))
                                menu();
                            System.out.print("Id: ");
                            docId = input.nextInt();
                            input.nextLine();
                        }
                        stores = pool.getStoresFromDoc(docId);
                        print(pool.getStoresStrings(stores));
                        //System.out.println(pool.getStoresStrings(stores).toString());
                        int i = 0;
                        int itemId;
                        int quantity;
                        List<Map<Integer, Integer>> allItems = new LinkedList<>();
                        while (i < stores.size()) {
                            Map<Integer, Integer> items = new HashMap<>();
                            addMore = true;
                            System.out.println("Insert items id and quantitys for store: " + stores.get(i));
                            while (addMore) {
                                System.out.print("Item id: ");
                                itemId = input.nextInt();
                                input.nextLine();
                                System.out.print("Quantity: ");
                                quantity = input.nextInt();
                                input.nextLine();
                                items.put(itemId, quantity);
                                System.out.println("In order to insert more items to store " + stores.get(i) + ", enter 1,else enter something else");
                                addMore = input.nextLine().equals("1");
                            }
                            i++;
                            allItems.add(items);
                        }
                        pool.addItems(docId, allItems);
                        System.out.println("Items was added successfully");
                        break;
                    case 4://view system data
                        System.out.println("Please enter option number:\n" +
                                "1. View pending transports\n2. View failed transports\n3. View successful transports\n4. View drivers\n5. View trucks\n6. View stores\n7. View suppliers\n");
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
                                System.out.println("drivers");
                                System.out.println(pool.DtoString());
                                break;
                            case 5:
                                System.out.println("trucks");
                                System.out.println((pool.TtoString()));
                                break;
                            case 6:
                                System.out.println("stores");
                                System.out.println((pool.StoreToString()));
                                break;
                            case 7:
                                System.out.println("suppliers");
                                System.out.println((pool.SupplierToString()));
                                break;

                        }
                        break;
                    case 5: //add/delete/update site
                        System.out.println("Please enter option number:\n" +
                                "1. Add supplier\n2. Add store\n3. Delete supplier\n4. Delete store\n5. Update supplier\n6. Update store");
                        option = input.nextInt();
                        input.nextLine();
                        switch (option) {
                            case 1://add supplier
                                System.out.println("Enter address, phone number, contact name and area of the Supplier");
                                System.out.print("address: ");
                                String address = input.nextLine();
                                System.out.print("phone number: ");
                                String phoneNumber = input.nextLine();
                                System.out.print("contact name: ");
                                String contactName = input.nextLine();
                                System.out.print("area: ");
                                area = input.nextInt();
                                input.nextLine();
                                pool.addSupplier(address, phoneNumber, contactName, area);
                                System.out.println("Supplier add successfully");
                                break;
                            case 2:// add store
                                System.out.println("Enter address, phone number, contact name and area of the Store");
                                System.out.print("address: ");
                                address = input.nextLine();
                                System.out.print("phone number: ");
                                phoneNumber = input.nextLine();
                                System.out.print("contact name: ");
                                contactName = input.nextLine();
                                System.out.print("area: ");
                                area = input.nextInt();
                                input.nextLine();
                                pool.addStore(address, phoneNumber, contactName, area);
                                System.out.println("Store add successfully");
                                break;
                            case 3:// delete supplier
                                System.out.println(pool.SupplierToString());
                                System.out.println("Enter the id of the supplier you want to delete");
                                idNum = input.nextInt();
                                input.nextLine();
                                if (!pool.isExistsSupplier(idNum))
                                    System.out.println("Invalid supplier id");
                                else {
                                    if (pool.supplierIsBusy(idNum))
                                        System.out.println("Supplier is in a pending transport,therefore - cannot be removed");
                                    else {
                                        pool.removeSupplier(idNum);
                                        System.out.println("Supplier removed successfully");
                                    }
                                }
                                break;
                            case 4://delete store
                                System.out.println(pool.StoreToString());
                                System.out.println("Enter the id of the store you want to delete");
                                idNum = input.nextInt();
                                input.nextLine();
                                if (!pool.isExistStore(idNum))
                                    System.out.println("Invalid store id");
                                else {
                                    if (pool.storeIsBusy(idNum))
                                        System.out.println("Store is in a pending transport,therefore - cannot be removed");
                                    else {
                                        pool.removeStore(idNum);
                                        System.out.println("Store removed successfully");
                                    }
                                }
                                break;
                            case 5: // update supplier
                                System.out.println(pool.SupplierToString());
                                System.out.println("Enter the id of the supplier you want to update");
                                idNum = input.nextInt();
                                input.nextLine();
                                if (!pool.isExistsSupplier(idNum))
                                    System.out.println("Invalid supplier id");
                                else {
                                    System.out.println("Enter address, phone number, contact name and area of the Supplier");
                                    System.out.print("address: ");
                                    address = input.nextLine();
                                    System.out.print("phone number: ");
                                    phoneNumber = input.nextLine();
                                    System.out.print("contact name: ");
                                    contactName = input.nextLine();
                                    pool.UpdateSupplier(idNum, address, phoneNumber, contactName);
                                    System.out.println("Supplier update successfully");
                                }
                                break;
                            case 6: // update store
                                System.out.println(pool.StoreToString());
                                System.out.println("Enter the id of the Store you want to update");
                                idNum = input.nextInt();
                                input.nextLine();
                                if (!pool.isExistStore(idNum))
                                    System.out.println("Invalid store id");
                                else {
                                    System.out.println("Enter address, phone number, contact name and area of the Store");
                                    System.out.print("address: ");
                                    address = input.nextLine();
                                    System.out.print("phone number: ");
                                    phoneNumber = input.nextLine();
                                    System.out.print("contact name: ");
                                    contactName = input.nextLine();
                                    pool.UpdateStore(idNum, address, phoneNumber, contactName);
                                    System.out.println("Store update successfully");
                                }
                                break;
                            default:
                                System.out.println("Invalid option number!");
                                break;
                        }
                        break;
                    case 6:// add/delete driver
                        System.out.println("Please enter option number:\n" +
                                "1. Add driver\n2. Delete driver");
                        option = input.nextInt();
                        input.nextLine();
                        switch (option) {
                            case 1://add driver
                                System.out.println("Enter id, name and license of the driver");
                                System.out.print("id: ");
                                String id = input.nextLine();
                                System.out.print("name: ");
                                String name = input.nextLine();
                                System.out.print("license can be C or C1, license: ");
                                String license = input.nextLine();
                                if (pool.isUniqueDriver(id)) {
                                    if (pool.validLicense(license)) {
                                        pool.addDriver(id, name, license);
                                        System.out.println("Driver add successfully");
                                    } else
                                        System.out.println("This is not a legal license please try again the next time");
                                } else
                                    System.out.println("This id already exist please try again the next time");
                                break;
                            case 2://delete driver
                                System.out.println(pool.DtoString());
                                System.out.print("Enter the id of the driver you want to delete\nid: ");
                                id = input.nextLine();
                                if (pool.isUniqueDriver(id)) // the driver doesnt exists
                                    System.out.println("this driver doesn't exist");
                                else {
                                    if (pool.driverIsBusy(id))
                                        System.out.println("Driver is in a pending transport,therefore - cannot be removed");
                                    else {
                                        pool.removeDriver(id);
                                        System.out.println("Driver removed successfully");
                                    }
                                }
                                break;
                            default:
                                System.out.println("Invalid option number!");
                                break;
                        }
                        break;
                    case 7://add/delete truck
                        System.out.println("Please enter option number:\n" +
                                "1. Add truck\n2. Delete truck");
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
                                System.out.println(pool.TtoString());
                                System.out.println("Enter the id of the truck you want to delete\nid: ");
                                id = input.nextLine();
                                if (pool.isUniqueTruck(id)) //the truck doesnt exists
                                    System.out.println("this truck doesn't exist");
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
                    case 8:
                        return;
                    default:
                        System.out.println("Invalid option number!");
                        break;
                }
                System.out.println("\n\n");
            }
            catch (Exception e){
                System.out.println("Invalid input!");
                menu();
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
