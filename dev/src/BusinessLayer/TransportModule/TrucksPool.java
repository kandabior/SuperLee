package BusinessLayer.TransportModule;


import BusinessLayer.TransportModule.DTO.DTO_Supplier;
import BusinessLayer.TransportModule.DTO.DTO_Truck;
import DataAccessLayer.Transport.TrucksMapper;

import java.util.*;

public class TrucksPool {

    private LinkedList<Truck> trucks;
    private TrucksMapper mapper;

    private static TrucksPool ourInstance = new TrucksPool();

    public static TrucksPool getInstance() {
        return ourInstance;
    }

    private TrucksPool() {
        trucks = new LinkedList<Truck>();
    }

    public void addTruck(String id, String model , double weight, double maxWeight){
        DTO_Truck t = new DTO_Truck(id , model, weight, maxWeight);
        mapper.addTruck(t);
    }

    public void deleteTruck(String id){
        Truck tr = null;
        for (Truck t:trucks){
            if(t.getId().equals(id))
                tr = t;
        }
        trucks.remove(tr);
    }
    public List<String> TruckstoString(){
       return mapper.getTrucksString();
    }

    public boolean isUniqueId (String id){
        return mapper.isUniqueTruck(id);
    }


    public boolean validTruck(String id,Date date) {
        for (Truck t:trucks) {
            if(t.getId().equals(id) && (t.isFree(date)))
                return true;
        }
        return false;
    }

    public double getWeight(String truckId) {
        for (Truck t:trucks) {
            if (t.getId().equals(truckId))
                return t.getWeight();
        }
        return -1;
    }

    public double getMaxWeight(String truckId) {
        for (Truck t:trucks) {
            if (t.getId().equals(truckId))
                return t.getMaxWeight();
        }
        return -1;
    }

    public List<String> getTrucks(Date date){//returns the trucks that available at this date
        List<String> output = new LinkedList<>();
        for (Truck t:trucks) {
            if (t.isFree(date)){
                output.add(t.toString());
            }
        }
        return output;
    }


    public void addDateToTruck(String truckId, Date date) {
        Truck truck = null;
        for (Truck t:trucks){
            if (t.getId().equals(truckId))
                truck = t;
        }
        if(truck != null){
            truck.addDate(date);
        }
    }

    public void freeTruck(String truckId, Date date) {
        Truck truck = null;
        for (Truck t:trucks){
            if (t.getId().equals(truckId))
                truck = t;
        }
        if(truck != null){
            truck.removeDate(date);
        }
    }
}
