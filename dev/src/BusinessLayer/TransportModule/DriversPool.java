package BusinessLayer.TransportModule;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DriversPool {

    private LinkedList<Driver> drivers;

    private static DriversPool ourInstance = new DriversPool();

    public static DriversPool getInstance() {
        return ourInstance;
    }

    private DriversPool() {

        drivers = new LinkedList<Driver>();
    }

    public void addDriver(String id, String name , String license){
        Driver d = new Driver(id,name,license);
        drivers.add(d);
    }

    public void deleteDriver(String id){
        Driver dr = null;
        for (Driver d : drivers){
            if(d.getId().equals(id))
                dr = d;
        }
        drivers.remove(dr);
    }

    public List<String> getDrivers(double truckWeight, Date date){//returns the drivers that allowed to drive thisweight
        List<String> output = new LinkedList<>();
            for (Driver d:drivers) {
                if ((truckWeight <= 12 | d.getLicense().equals("C")) & (d.isFree(date))){
                    output.add(d.toString());
            }
        }
            return output;
    }

    public boolean validDriver(String id,double truckWeight, Date date) {
        for (Driver d:drivers) {
            if(d.getId().equals(id) && (d.isFree(date)) & (truckWeight <= 12 | d.getLicense().equals("C")))
                return true;
        }
        return false;
    }

    public String toString(){
        String s = "";
        Iterator itr = drivers.iterator();
        while (itr.hasNext()){
            s = s + itr.next().toString() + "\n";
        }
        return s;
    }

    public String getDriverName(String driverId) {
        for (Driver d:drivers) {
            if(d.getId().equals(driverId))
                return d.getName();
        }
        return "";
    }

    public boolean isUniqueId (String id){
        boolean isUnique = true;
        for (Driver d:drivers){
            if (d.getId().equals(id))
                isUnique = false;
        }
        return isUnique;
    }

    public boolean isValidLicense (String license){
        if (!license.equals("C")&!license.equals("C1"))
            return false;
        return true;
    }


    public void addDateToDriver(String driverId, Date date) {
        Driver driver = null;
        for (Driver d:drivers){
            if (d.getId().equals(driverId))
                 driver = d;
        }
        if(driver != null){
            driver.addDate(date);
        }
    }

    public void freeDriver(String driverId, Date date) {
        Driver driver = null;
        for (Driver d:drivers){
            if (d.getId().equals(driverId))
                driver = d;
        }
        if(driver != null){
            driver.removeDate(date);
        }
    }
}
