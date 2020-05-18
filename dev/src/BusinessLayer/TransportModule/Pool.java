package BusinessLayer.TransportModule;

import BusinessLayer.EmployeeModule.Employee;
import BusinessLayer.EmployeeModule.EmployeeService;
import DataAccessLayer.Transport.DTO.DTO_TransportDoc;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


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
        employeeService  = new EmployeeService();
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

    public void addSupplier(String address, String phoneNumber, String contactName, int area) {
        suppliersPool.addSupplier(address, phoneNumber, contactName, area);
    }
    public void addSupplier(int id,String address, String phoneNumber, String contactName, int area) {
        suppliersPool.addSupplier(id,address, phoneNumber, contactName, area);
    }

    public void removeSupplier(int id) {
        suppliersPool.deleteSupplier(id);
    }

    public List<String> SupplierToString() {
        return suppliersPool.SupplierstoString();
    }

    public void addStore(String address, String phoneNumber, String contactName, int area) {
        storesPool.addStore(address, phoneNumber, contactName, area);
    }
    public void addStore(int id,String address, String phoneNumber, String contactName, int area) {
        storesPool.addStore(id,address, phoneNumber, contactName, area);
    }

    public void removeStore(int id) {
        storesPool.deleteStore(id);
    }

    public List<String> StoreToString() {
        return storesPool.StorestoString();
    }

    public List<String> getAvailableStores(int area, Date date) {
        List<Integer> availableStores = employeeService.getStores(date);
        return storesPool.getStores(area,availableStores);

    }

    public List<String> getSuppliers(int area) {
        return suppliersPool.getSuppliers(area);
    }


    public boolean validStore(int id, int area) {
        return storesPool.validStore(id, area);
    }

    public boolean validSupplier(int id, int area) {
        return suppliersPool.validSupplier(id, area);
    }

    public boolean validTruck(String id,Date date) {
        return trucksPool.validTruck(id,date);
    }

    public List<Integer> getDrivers(String truckId,Date date) {
        double weight = trucksPool.getWeight(truckId);
        String license;
        if(weight > 12)
            license = "C";
        else
            license = "C1";
        return employeeService.getDrivers(date,license);
    }

    public List<String> getTrucks(Date date) {
        return trucksPool.getTrucks(date);
    }

    public String getDriverName(int driverId) throws Exception {
        return employeeService.getDriverName(driverId);
    }

    public void addDoc(int area, Date date, String truckId, int driverId, String driverName, List<Integer> stores, List<Integer> suppliers) {
        docsPool.addDoc(area, date, truckId, driverId, driverName, stores, suppliers);
    }
    public void addDoc(int id,int area, Date date, String truckId, String driverId, String driverName, List<Integer> stores, List<Integer> suppliers) {
        docsPool.addDoc(id,area, date, truckId, driverId, driverName, stores, suppliers);
    }
   // public boolean isUniqueDriver(String id) {return driversPool.isUniqueId(id);   }

    public boolean isUniqueTruck(String id) {return trucksPool.isUniqueId(id);  }


    public boolean validTansport(int docId) {return docsPool.validTransport(docId);    }

    public double addWeight(int docId, double total) {
        DTO_TransportDoc dto_doc = docsPool.getDoc(docId);
        double maxWeight = trucksPool.getMaxWeight(dto_doc.getTruckId());
        return docsPool.addWeight(dto_doc, total, maxWeight);
    }

    public List<Integer> getStoresFromDoc(int docId) { return docsPool.getStoresFromDoc(docId);   }

    public List<String> getStoresStrings(List<Integer> stores) {return storesPool.getStoresStrings(stores);  }

    public void addItems(int docId,List<Integer> stores,  List<Map<Integer, Integer>> allItems) {docsPool.addItems(docId, stores, allItems);  }

    public boolean isExistsSupplier(int id){return suppliersPool.isExistsId(id);}
    public boolean isExistStore(int id){return storesPool.isExistsId(id);}
    public void UpdateSupplier(int id,String address, String phoneNumber, String contactName){suppliersPool.Update(id,address,phoneNumber,contactName);}
    public void UpdateStore(int id,String address, String phoneNumber, String contactName){storesPool.Update(id,address,phoneNumber,contactName);}
    public List<String> PrintFailDoc(){return docsPool.getFailDocs();}
    public List<String> PrintPendingDoc(){return docsPool.getPendingDocs();}
    public boolean validArea(int area) {return (storesPool.validArea(area) &  suppliersPool.validArea(area));    }
    //public boolean validLicense(String license){return driversPool.isValidLicense(license);}

    public void addDateToDriver(int driverId, Date date) { occupiedDriversPool.addDateToDriver(driverId,date);    }

    public void addDateToTruck(String truckId, Date date) {trucksPool.addDateToTruck(truckId,date);    }

    public void freeTruckDate(int docId) {
        DTO_TransportDoc td= docsPool.getDoc(docId);
        trucksPool.freeTruck(td.getTruckId(),td.getDate());
    }

    public void freeDriverDate(int docId) {
        DTO_TransportDoc td= docsPool.getDoc(docId);
        occupiedDriversPool.freeDriver(td.getDriverId(),td.getDate());
    }

    public void removeDoc(int id) {docsPool.removeDoc(id);   }

    public List<String> printSuccessDoc() {return docsPool.getSuccessDoc();   }

    public boolean supplierIsBusy(int suppId) {return docsPool.supplierIsBusy(suppId);  }

    public boolean storeIsBusy(int sId) {return docsPool.storeIsBusy(sId);    }

    public boolean driverIsBusy(String driverId) {return docsPool.driverIsBusy(driverId);    }

    public boolean truckIsBusy(String truckId) {return docsPool.truckIsBusy(truckId);    }


    public List<String> getDriversName(List<Integer> availableDrivers) throws Exception {
        List<String> output = new LinkedList<>();
            for (int i = 0; i < availableDrivers.size(); i++) {
                output.add("Id: " + availableDrivers.get(i) + " Name: " + employeeService.getDriverName(availableDrivers.get(i)));
            }
        return output;
    }
}