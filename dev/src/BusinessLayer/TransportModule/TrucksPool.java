package src.BusinessLayer.TransportModule;



import src.DataAccessLayer.Transport.DTO.DTO_Truck;
import src.DataAccessLayer.Transport.TrucksMapper;

import java.util.*;

public class TrucksPool {

    private List<DTO_Truck> DTOtrucks;
    private LinkedList<Truck> trucks;
    private TrucksMapper mapper;
    private static TrucksPool ourInstance = new TrucksPool();

    public static TrucksPool getInstance() {
        return ourInstance;
    }

    private TrucksPool() {
        mapper = new TrucksMapper();
        DTOtrucks = mapper.getTrucks();
        trucks = new LinkedList<>();
        if(DTOtrucks != null) {
            for (DTO_Truck dt : DTOtrucks) {
                Truck t = new Truck(dt.getId(), dt.getModel(), dt.getWeight(), dt.getMaxWeight());
                List<String> datesS = mapper.getTruckDates(t.getId());
                List<Date> dates = new LinkedList<>();
                if (datesS != null) {
                    for (String s : datesS) {
                        dates.add(stringToDate(s));
                    }
                }
                trucks.add(t);
                t.setDates(dates);
            }
        }
    }

    public void addTruck(String id, String model , double weight, double maxWeight){
        Truck t = new Truck(id,model,weight,maxWeight);
        trucks.add(t);
        DTO_Truck dt = new DTO_Truck(id,model,weight,maxWeight);
        mapper.addTruck(dt);
    }

    public void deleteTruck(String id){
        Truck truck = null;
        for (Truck t : trucks){
            if(t.getId().equals(id))
                truck = t;
        }
        trucks.remove(truck);
        mapper.removeTruck(id);
    }
    public List<String> trucksToString(){
        List<String> output = new LinkedList<>();
        for (Truck t : trucks){
            output.add(t.toString());
        }
        return output;
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

    public List<String> getTrucksID(Date date){//returns the trucks that available at this date
        List<String> output = new LinkedList<>();
        for (Truck t:trucks) {
            if (t.isFree(date)){
                output.add(t.getId());
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
            mapper.addDateToTruck(truckId,dateToString(date));
        }
    }

    public void freeTruck(String truckId, String date) {
        Date d = stringToDate(date);
        Truck truck = null;
        for (Truck t:trucks){
            if (t.getId().equals(truckId))
                truck = t;
        }
        if(truck != null){
            truck.removeDate(d);
            mapper.removeTruckDate(truckId,date);
        }
    }

    private static Date stringToDate(String s){
        String[] parts = s.split("/");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(parts[2].substring(0,4)));
        cal.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);//Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[0]));
        return cal.getTime();
    }

    private static String dateToString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1) +"/" + calendar.get(Calendar.YEAR);
    }

}
