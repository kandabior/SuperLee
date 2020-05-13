package BusinessLayer.TransportModule;


import java.util.*;

public class TrucksPool {

    private LinkedList<Truck> trucks;

    private static TrucksPool ourInstance = new TrucksPool();

    public static TrucksPool getInstance() {
        return ourInstance;
    }

    private TrucksPool() {
        trucks = new LinkedList<Truck>();
    }

    public void addTruck(String id, String model , double weight, double maxWeight){
        Truck t = new Truck(id,model,weight,maxWeight);
        trucks.add(t);
    }

    public void deleteTruck(String id){
        Truck tr = null;
        for (Truck t:trucks){
            if(t.getId().equals(id))
                tr = t;
        }
        trucks.remove(tr);
    }
    public String toString(){
        String s = "";
        Iterator itr = trucks.iterator();
        while (itr.hasNext()){
            s = s + itr.next().toString() + "\n";
        }
     return s;
    }

    public boolean isUniqueId (String id){
        boolean isUnique = true;
        for (Truck t:trucks){
            if (t.getId().equals(id))
                isUnique = false;
        }
        return isUnique;
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
