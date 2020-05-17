package BusinessLayer.TransportModule;

import DataAccessLayer.Transport.DTO.DTO_OccupiedDriver;
import DataAccessLayer.Transport.OccupiedDriversMapper;

import java.util.*;

public class OccupiedDriversPool {

    private LinkedList<OccupiedDriver> occupiedDrivers;
    private OccupiedDriversMapper mapper;

    private static OccupiedDriversPool ourInstance = new OccupiedDriversPool();

    public static OccupiedDriversPool getInstance() {
        return ourInstance;
    }

    private OccupiedDriversPool() {
        mapper = new OccupiedDriversMapper();
        occupiedDrivers = new LinkedList<OccupiedDriver>();
        List<DTO_OccupiedDriver> DTOoccupiedD= mapper.getOccupiedDriver();
        for (DTO_OccupiedDriver dod : DTOoccupiedD) {
            OccupiedDriver od = new OccupiedDriver(dod.getId());
            occupiedDrivers.add(od);
        }
        for (OccupiedDriver od: occupiedDrivers){
            List<String> datesFromDB = mapper.getOccupiedDriverDates(od.getId());
            List<Date> dates = new LinkedList<>();
            for (String s: datesFromDB){
                dates.add(stringToDate(s));
            }
            od.setDates(dates);
        }
    }

    private static Date stringToDate(String s){
        String[] parts = s.split("/");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(parts[2]));
        cal.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);//Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[0]));
        return cal.getTime();
    }

    private static String dateToString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1) +"/" + calendar.get(Calendar.YEAR);
    }

    public void freeDriver(int driverId, String date) {
        OccupiedDriver driver = null;
        for (OccupiedDriver d:occupiedDrivers){
            if (d.getId() == driverId)
                driver = d;
        }
        if(driver != null){
            driver.removeDate(stringToDate(date));
            mapper.removeDriverAtDate(driverId,date);
        }
    }

    public void addDateToDriver(int driverId, Date date) {
        OccupiedDriver driver = null;
        for (OccupiedDriver d:occupiedDrivers){
            if (d.getId() == driverId)
                driver = d;
        }
        if(driver != null){
            driver.addDate(date);
            mapper.addDateToOccupiedDriver(driverId, dateToString(date));
        }
    }

    /*public void addDriver(String id, String name , String license){
        OccupiedDriver d = new OccupiedDriver(id,name,license);
        drivers.add(d);
    }

    public void deleteDriver(String id){
        OccupiedDriver dr = null;
        for (OccupiedDriver d : drivers){
            if(d.getId().equals(id))
                dr = d;
        }
        drivers.remove(dr);
    }

    public List<String> getDrivers(double truckWeight, Date date){//returns the drivers that allowed to drive thisweight
        List<String> output = new LinkedList<>();
            for (OccupiedDriver d:drivers) {
                if ((truckWeight <= 12 | d.getLicense().equals("C")) & (d.isFree(date))){
                    output.add(d.toString());
            }
        }
            return output;
    }

    public boolean validDriver(String id,double truckWeight, Date date) {
        for (OccupiedDriver d:drivers) {
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
        for (OccupiedDriver d:drivers) {
            if(d.getId().equals(driverId))
                return d.getName();
        }
        return "";
    }

    public boolean isUniqueId (String id){
        boolean isUnique = true;
        for (OccupiedDriver d:drivers){
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




  */
}
