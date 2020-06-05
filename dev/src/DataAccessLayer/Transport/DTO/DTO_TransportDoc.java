package src.DataAccessLayer.Transport.DTO;

import src.BusinessLayer.TransportModule.TransportDoc.Status;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DTO_TransportDoc {

    //public enum Status {PENDING, SUCCESS, FAIL;  };
    private int id;
    private int area;
    private String date;
    private String truckId;
    private int driverId;
    private String driverName;
    private List<Integer> suppliers;
    private List<Integer> stores;
    private List<Map<Integer, Integer>> items;
    private Status status;
    private double finalWeight;


    public DTO_TransportDoc(int id, int area, String date, String truckId, int driverId, String driverName, List<Integer> stores, List<Integer> suppliers, List<Map<Integer,Integer>> items, String status, double finalWeight) {
        this.id = id;
        this.area = area;
        this.date = date;
        this.truckId = truckId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.stores = stores;
        this.suppliers = suppliers;
        this.items = items;
        switch (status) {
            case "PENDING":
                this.status = Status.PENDING;
                break;
            case "SUCCESS":
                this.status = Status.SUCCESS;
                break;
            default: //FAIL
                this.status = Status.FAIL;
        }
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public List<Integer> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Integer> suppliers) {
        this.suppliers = suppliers;
    }

    public List<Integer> getStores() {
        return stores;
    }

    public void setStores(List<Integer> stores) {
        this.stores = stores;
    }

    public List<Map<Integer, Integer>> getItems() {
        return items;
    }

    public void setItems(List<Map<Integer, Integer>> items) {
        this.items = items;
    }

    public Status getStatus() {return status;    }

    public  String getStringStatus(){
        switch (status) {
            case SUCCESS:
                return "SUCCESS";
            case PENDING:
                return "PENDING";
            default: //FAIL
                return "FAIL";
        }
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getFinalWeight() {
        return finalWeight;
    }

    public void setFinalWeight(double finalWeight) {
        this.finalWeight = finalWeight;
    }

    public String toString(){
        String weight;
        if(finalWeight == -1)
            weight = "-";
        else
            weight = finalWeight + "";

        return "id: " + id + "\nstatus: " + status + "\narea: " + area + "\ndate: " + date + "\ntruck id: " + truckId +
                "\ndriver id: " + driverId + "\ndriver name: "+ driverName + "\nsuppliers: " + suppliers.toString() + "\nstores: " + stores.toString() +"\nitems: " + items.toString()
                +"\nfinal weight: " + weight;
    }

}
