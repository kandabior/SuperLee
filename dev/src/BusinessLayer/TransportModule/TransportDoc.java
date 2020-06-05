package src.BusinessLayer.TransportModule;

import java.awt.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

public class TransportDoc {

    private static int idCounter = 1;
    public enum Status {PENDING, SUCCESS, FAIL;  }
    private int id;
    private int area;
    private Date date;
    private String truckId;
    private String driverId;
    private String driverName;
    private List<Integer> suppliers;
    private List<Integer> stores;
    private List<Map<Integer, Integer>> items;
    private Status status;
    private double finalWeight;


    public TransportDoc(int area, Date date, String truckId, String driverId, String driverName, List<Integer> stores, List<Integer> suppliers) {
        id = idCounter;
        idCounter++;
        this.area = area;
        this.date = date;
        this.truckId = truckId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.stores = stores;
        this.suppliers = suppliers;
        items = new LinkedList<>();
        status = Status.PENDING;
        finalWeight = -1;
    }
    public TransportDoc(int id,int area, Date date, String truckId, String driverId, String driverName, List<Integer> stores, List<Integer> suppliers) {
        this.id = id;
        this.area = area;
        this.date = date;
        this.truckId = truckId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.stores = stores;
        this.suppliers = suppliers;
        items = new LinkedList<>();
        status = Status.PENDING;
        finalWeight = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
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

    public Status getStatus() {
        return status;
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

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String toString(){
        String weight;
        if(finalWeight == -1)
            weight = "-";
        else
            weight = finalWeight + "";

        return "id: " + id + "\nstatus: " + status + "\narea: " + area + "\ndate: " + date + "\ntruck id: " + truckId +
                "\ndriver id: " + driverId + "\nsuppliers: " + suppliers.toString() + "\nstores: " + stores.toString() +"\nitems: " + items.toString()
                +"\nfinal weight: " + weight;
    }



}
