package BusinessLayer.TransportModule.DTO;

import BusinessLayer.TransportModule.TransportDoc;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DTO_TransportDoc {

    public enum Status {PENDING, SUCCESS, FAIL;  };
    private int id;
    private int area;
    private String date;
    private String truckId;
    private int driverId;
    private String driverName;
    private List<Integer> suppliers;
    private List<Integer> stores;
    private List<Map<Integer, Integer>> items;
    private DTO_TransportDoc.Status status;
    private double finalWeight;

    public DTO_TransportDoc(int id, int area, String date, String truckId,int driverId, String driverName, List<Integer> stores, List<Integer> suppliers, List<Map<Integer,Integer>> items,DTO_TransportDoc.Status status, double finalWeight) {
        this.id = id;
        this.area = area;
        this.date = date;
        this.truckId = truckId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.stores = stores;
        this.suppliers = suppliers;
        this.items = items;
        this.status = status;
        this.finalWeight = finalWeight;
    }
    public DTO_TransportDoc(int id, int area, String date, String truckId,int driverId, String driverName, List<Integer> stores, List<Integer> suppliers) {
        this.id = id;
        this.area = area;
        this.date = date;
        this.truckId = truckId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.stores = stores;
        this.suppliers = suppliers;
        this.items = new LinkedList<>();
        this.status = Status.PENDING;
        this.finalWeight = -1;
    }

}
