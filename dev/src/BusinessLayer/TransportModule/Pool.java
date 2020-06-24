package src.BusinessLayer.TransportModule;

import src.BusinessLayer.EmployeeModule.Employee;
import src.BusinessLayer.EmployeeModule.EmployeeService;
import src.DataAccessLayer.Transport.DTO.DTO_TransportDoc;
import javafx.util.Pair;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import src.InterfaceLayer.Inventory.InventoryController;
import src.InterfaceLayer.Suppliers.FacadeController;
import src.PresentationLayer.Main;

import java.time.LocalDate;
import java.util.*;


public class Pool {

    private TrucksPool trucksPool;
    private OccupiedDriversPool occupiedDriversPool;
    private DocsPool docsPool;
    private SuppliersPool suppliersPool;
    private StoresPool storesPool;
    private EmployeeService employeeService;

    private static Pool ourInstance = new Pool();


    public static Pool getInstance() {
        return ourInstance;
    }

    private Pool() {
        trucksPool = TrucksPool.getInstance();
        occupiedDriversPool = OccupiedDriversPool.getInstance();
        docsPool = DocsPool.getInstance();
        suppliersPool = SuppliersPool.getInstance();
        storesPool = StoresPool.getInstance();
        employeeService = new EmployeeService();
    }


    public void addTruck(String id, String model, double weight, double maxWeight) {
        trucksPool.addTruck(id, model, weight, maxWeight);
    }

    public void removeTruck(String id) {
        trucksPool.deleteTruck(id);
    }

    public List<String> TtoString() {
        return trucksPool.trucksToString();
    }

    /*public void addDriver(String id, String name, String license) {
        driversPool.addDriver(id, name, license);
    }

    public void removeDriver(String id) {
        driversPool.deleteDriver(id);
    }*/

    public String DtoString() {
        return occupiedDriversPool.toString();
    }

   /* public void addSupplier(String address, String phoneNumber, String contactName, int area) {
        suppliersPool.addSupplier(address, phoneNumber, contactName, area);
    }

    public void addSupplier(int id, String address, String phoneNumber, String contactName, int area) {
        suppliersPool.addSupplier(id, address, phoneNumber, contactName, area);
    }

    public void removeSupplier(int id) {
        suppliersPool.deleteSupplier(id);
    }*/

    public List<String> SupplierToString() {
        return suppliersPool.SupplierstoString();
    }

    public void addStore(String address, String phoneNumber, String contactName, int area) {
        storesPool.addStore(address, phoneNumber, contactName, area);
    }

    public void addStore(int id, String address, String phoneNumber, String contactName, int area) {
        storesPool.addStore(id, address, phoneNumber, contactName, area);
    }

    public void removeStore(int id) {
        storesPool.deleteStore(id);
    }

    public List<String> StoreToString() {
        return storesPool.StorestoString();
    }

    public List<String> getAvailableStores(int area, Date date) {
        List<Integer> availableStores = employeeService.getStores(date);
        return storesPool.getStores(area, availableStores);

    }

   /* public List<String> getSuppliers(int area) {
        return suppliersPool.getSuppliers(area);
    }
*/

    public boolean validStore(int id, int area) {
        return storesPool.validStore(id, area);
    }
/*
    public boolean validSupplier(int id, int area) {
        return suppliersPool.validSupplier(id, area);
    }
*/
    public boolean validTruck(String id, Date date) {
        return trucksPool.validTruck(id, date);
    }

    public List<Integer> getDrivers(String truckId, Date date) {
        double weight = trucksPool.getWeight(truckId);
        String license;
        if (weight > 12)
            license = "C";
        else
            license = "C1";
        List<Integer> output = employeeService.getDrivers(date, license);
        return occupiedDriversPool.validDrivers(output, date);
    }

    public List<Integer> validDriver(List<Integer> drivers, Date date) {
        return occupiedDriversPool.validDrivers(drivers, date);
    }

    public List<String> getTrucks(Date date) {
        return trucksPool.getTrucks(date);
    }

    public String getDriverName(int driverId) throws Exception {
        return employeeService.getDriverName(driverId);
    }

    public int addDoc(int area, Date date, String truckId, int driverId, String driverName, List<Integer> stores, List<Integer> suppliers, List<Integer> ordersId) {
        return docsPool.addDoc(area, date, truckId, driverId, driverName, stores, suppliers, ordersId);
    }

    public void addDoc(int id, int area, Date date, String truckId, String driverId, String driverName, List<Integer> stores, List<Integer> suppliers) {
        docsPool.addDoc(id, area, date, truckId, driverId, driverName, stores, suppliers);
    }
    // public boolean isUniqueDriver(String id) {return driversPool.isUniqueId(id);   }

    public boolean isUniqueTruck(String id) {
        return trucksPool.isUniqueId(id);
    }


    public boolean validTransport(int docId) {
        return docsPool.validTransport(docId);
    }

    public double addWeight(int docId, double total) {
        DTO_TransportDoc dto_doc = docsPool.getDoc(docId);
        if (dto_doc != null) {
            double maxWeight = trucksPool.getMaxWeight(dto_doc.getTruckId());
            double different = docsPool.addWeight(dto_doc, total, maxWeight);
            List<Integer> ordersId = docsPool.getOrdersId(docId);
            if (different == 0) {
               // informSupplier(ordersId, true);
                int store = docsPool.getStoresFromDoc(docId).get(0);
                Map<Pair<Integer,Integer>,Pair<Integer,Double>> items =  docsPool.getFullItems(docId);
                InventoryController.getInventoryController().getProductsOneBranch(store, items,ordersId);
            }
            else
                informSupplier(ordersId, false);

            return different;
        }
        return -1;
    }

    private void informSupplier(List<Integer> ordersId, boolean status) {
        Iterator<Integer> itr = ordersId.iterator();
        while (itr.hasNext()){
            FacadeController.getFacadeController().updateOrderStatus(itr.next(), status);
        }
    }


    public List<Integer> getStoresFromDoc(int docId) {
        return docsPool.getStoresFromDoc(docId);
    }

    public List<String> getStoresStrings(List<Integer> stores) {
        return storesPool.getStoresStrings(stores);
    }

    public void addItems(int docId, List<Integer> stores, List<Map<Pair<Integer, Integer>, Pair<Integer, Double>>> allItems) {
        docsPool.addItems(docId, stores, allItems);
    }

    public boolean isExistsSupplier(int id) {
        return suppliersPool.isExistsId(id);
    }

    public boolean isExistStore(int id) {
        return storesPool.isExistsId(id);
    }

/*    public void UpdateSupplier(int id, String address, String phoneNumber, String contactName) {
        suppliersPool.Update(id, address, phoneNumber, contactName);
    }*/

    public void UpdateStore(int id, String address, String phoneNumber, String contactName) {
        storesPool.Update(id, address, phoneNumber, contactName);
    }

    public List<String> PrintFailDoc() {
        return docsPool.getFailDocs();
    }

    public List<String> PrintPendingDoc() {
        return docsPool.getPendingDocs();
    }
/*
    public boolean validArea(int area) {
        return (storesPool.validArea(area) & suppliersPool.validArea(area));
    }*/
    //public boolean validLicense(String license){return driversPool.isValidLicense(license);}

    public void addDateToDriver(int driverId, Date date) {
        occupiedDriversPool.addDateToDriver(driverId, date);
    }

    public void addDateToTruck(String truckId, Date date) {
        trucksPool.addDateToTruck(truckId, date);
    }

    public void freeTruckDate(int docId) {
        DTO_TransportDoc td = docsPool.getFailDoc(docId);
        trucksPool.freeTruck(td.getTruckId(), td.getDate());
    }

    public void freeDriverDate(int docId) {
        DTO_TransportDoc td = docsPool.getFailDoc(docId);
        occupiedDriversPool.freeDriver(td.getDriverId(), td.getDate());
    }

    public void removeDoc(int id) {
        docsPool.removeDoc(id);
    }

    public List<String> printSuccessDoc() {
        return docsPool.getSuccessDoc();
    }

    public boolean supplierIsBusy(int suppId) {
        return docsPool.supplierIsBusy(suppId);
    }

    public boolean storeIsBusy(int sId) {
        return docsPool.storeIsBusy(sId);
    }

    public boolean driverIsBusy(String driverId) {
        return docsPool.driverIsBusy(driverId);
    }

    public boolean truckIsBusy(String truckId) {
        return docsPool.truckIsBusy(truckId);
    }


    public List<String> getDriversName(List<Integer> availableDrivers) throws Exception {
        List<String> output = new LinkedList<>();
        for (int i = 0; i < availableDrivers.size(); i++) {
            output.add("Id: " + availableDrivers.get(i) + " Name: " + employeeService.getDriverName(availableDrivers.get(i)));
        }
        return output;


    }

    private static String dateToString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
    }

    public void freeTruckDate(String truckId, Date date) {
        trucksPool.freeTruck(truckId, dateToString(date));
    }

    public void freeDriverDate(int driverId, Date date) {
        occupiedDriversPool.freeDriver(driverId, dateToString(date));
    }

    // List<orderId,suppId, List<date>|null,Map<Pair<int,int>, Pair<int,int>>
    public void makeOrders(int storId, List<List<Object>> transportOrders) throws Exception {
        int day = 0;
        int area = storesPool.getArea(storId);
        if (area != -1) {
            while (!transportOrders.isEmpty() & day < 7) {
                List<List<Object>> temp = new LinkedList<>();
                List<Object> order;
                List<Integer> ordersId = new LinkedList<>();
                List<Map<Pair<Integer, Integer>, Pair<Integer, Double>>> items = new LinkedList<>();
                for (int i = 0; i < transportOrders.size(); i++) {
                    order = transportOrders.get(i);
                    List<Integer> dates = (List<Integer>) order.get(2);
                    if (dates == null || dates.contains(day)) {
                        temp.add(order);
                        transportOrders.remove(order);
                        ordersId.add((Integer) order.get(0));
                        items.add((Map<Pair<Integer, Integer>, Pair<Integer, Double>>) order.get(3));
                    }
                }
                if (!ordersId.isEmpty()) {
                    //if (day == 0)
                     //   day = 7;
                    LocalDate systemDate = LocalDate.now().plusDays(Main.plusDay);
                    //Calendar cal = Calendar.getInstance();
                    while (getCurrentDay(systemDate.getDayOfWeek().toString()) != day) {
                        systemDate = systemDate.plusDays(1);
                    }
                    //Date date = cal.getTime();
                    Calendar cal = Calendar.getInstance();
                    cal.set(systemDate.getYear(), systemDate.getMonthValue() - 1, systemDate.getDayOfMonth());
                    Date date = cal.getTime();
                    List<Integer> stores = new LinkedList<>();
                    stores.add(storId);
                    List<Integer> suppliers = new LinkedList<>();
                    Iterator<List<Object>> iter = temp.iterator();
                    while (iter.hasNext()) {
                        suppliers.add((Integer) iter.next().get(1));
                    }
                    int hasStoreKeeper = 0;
                    int hasDriver = 0;
                    int hasTruck = 0;
                    List<Integer> availableStores = employeeService.getStores(date);
                    if (availableStores.contains(storId)) // has storeKeeper
                        hasStoreKeeper = 1;
                    List<String> trucks = trucksPool.getTrucksID(date);
                    String truckId = "";
                    if (!trucks.isEmpty()) {
                        truckId = trucks.get(0); // first truck
                        hasTruck = 1;
                        addDateToTruck(truckId, date);
                    }
                    int driverId = -1;
                    String driverName = "";
                    if (hasTruck == 1) {
                        List<Integer> availableDriver = getDrivers(truckId, date);
                        if (!availableDriver.isEmpty()) {
                            hasDriver = 1;
                            driverId = availableDriver.get(0);
                            driverName = getDriverName(driverId);
                            addDateToDriver(driverId, date);
                        }

                        int docId = addDoc(area, date, truckId, driverId, driverName, stores, suppliers, ordersId);

                        if (hasDriver == 0 | hasStoreKeeper == 0) {
                            docsPool.addMissingEmployees(docId, hasStoreKeeper, hasDriver);
                            docsPool.addMissingMsg(storId, docId, hasStoreKeeper, hasDriver, date);
                        }
                        List<Map<Pair<Integer, Integer>, Pair<Integer, Double>>> transportsItems = mapsUnion(items);
                        addItems(docId, stores, transportsItems);
                    } else {//no truck
                        informSupplier(ordersId, false);
                    }
                }
               // if(day ==7)
                //    day = 0;
                    day++;
            }
        }
    }

    private List<Map<Pair<Integer, Integer>, Pair<Integer, Double>>> mapsUnion(List<Map<Pair<Integer, Integer>, Pair<Integer, Double>>> items) {
        List<Map<Pair<Integer, Integer>, Pair<Integer, Double>>> output = new LinkedList<>();
        Iterator<Map<Pair<Integer, Integer>, Pair<Integer, Double>>> iter = items.iterator();
        Map<Pair<Integer, Integer>, Pair<Integer, Double>> curr;
        Map<Pair<Integer, Integer>, Pair<Integer, Double>> allItems = new HashMap<>();
        while (iter.hasNext()) {
            curr = iter.next();
            for (Map.Entry<Pair<Integer, Integer>, Pair<Integer, Double>> entry : curr.entrySet()) {
                allItems.put(entry.getKey(), entry.getValue());
            }
        }
        output.add(allItems);
        return output;
    }

    public List<Integer> UpdateWaitingTransports() throws Exception {
        int driverId;
        String driverName;
        List<Integer> output = new LinkedList<>();
        List<Integer> waiting = docsPool.getWaitingTransportIds();
        Iterator<Integer> itr = waiting.iterator();
        while (itr.hasNext()) {
            int tid = itr.next();
            DTO_TransportDoc doc = docsPool.getDoc(tid);
            Date date1 = stringToDate(doc.getDate());
            LocalDate date2 = stringToLocalDate(doc.getDate());
            List<Integer> ordersId = docsPool.getOrdersId(doc.getId());
            if (date2.isBefore(LocalDate.now().plusDays(Main.plusDay))) {
                docsPool.removeFromWaitingTransport(doc.getId());
                docsPool.updateFailTransport(doc.getId());
                informSupplier(ordersId, false);
                if (doc.getDriverId() != -1) {
                    freeDriverDate(doc.getDriverId(), date1);
                    freeTruckDate(doc.getTruckId(), date1);
                }
            }
            else { // date hasnt passed
                if (employeeService.getStores(date1).contains(doc.getStores().get(0))) {
                    double weight = trucksPool.getWeight (doc.getTruckId());
                    String license;
                    if (weight > 12)
                        license = "C";
                    else
                        license = "C1";
                    List<Integer> drivers = employeeService.getDrivers(date1, license);
                    drivers = occupiedDriversPool.validDrivers(drivers, date1);
                    if(!drivers.isEmpty()){
                        driverId = drivers.get(0);
                        driverName = employeeService.getDriverName(driverId);
                        addDateToDriver(driverId,date1);
                        docsPool.addDriverToTransport(doc.getId(), driverId, driverName);
                        docsPool.removeFromWaitingTransport(doc.getId());
                        output.add(doc.getId());
                    }
                }
            }
        }

        return output;

    }

    private static Date stringToDate(String s){
        String[] parts = s.split("/");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(parts[2].substring(0,4)));
        cal.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);//Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[0]));
        return cal.getTime();
    }


    public boolean transportIsToday(int docId) {
        String stringDate = docsPool.getTransportDate(docId);
        LocalDate transportDate = stringToLocalDate(stringDate);
        return LocalDate.now().plusDays(Main.plusDay).isEqual(transportDate);
    }

    public boolean hasTransport(int brunchId, String date) {
        List<Integer> goodT;
        List<Integer> badT;
        if(brunchId == 0)
            goodT = docsPool.hasTransport(date);
        else
            goodT =  docsPool.hasTransport(brunchId, date);
        badT = docsPool.getWaitingTransportIds();
       goodT.removeAll(badT);
        return !goodT.isEmpty();
    }

    public List<Integer> getRequest() {
        return docsPool.getRequest();
    }

    public String getReqString(Integer orderId) {
        return docsPool.getReqString(orderId);
    }

    public void CancelRequest(int orderId) {
        int docId = getTransportId(orderId);
        DTO_TransportDoc doc = docsPool.getDoc(docId);
        List<Integer> orders = docsPool.getOrdersId(docId);
        docsPool.removeRequest(orderId);
        docsPool.removeFromWaitingTransport(doc.getId());
        informSupplier(orders, false);
        docsPool.updateFailTransport(docId);
        if (doc.getDriverId() != -1) {
            freeDriverDate(doc.getDriverId(), stringToDate(doc.getDate()));
            freeTruckDate(doc.getTruckId(),stringToDate(doc.getDate()));
        }

    }

    private int getTransportId(int orderId) {
       return docsPool.getTransportId(orderId);
    }

    private LocalDate stringToLocalDate(String stringDate){
        String[] parts = stringDate.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        return LocalDate.of(year, month, day);
    }

    private Integer getCurrentDay(String day) {

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