package Transports.src.businessLayer.src;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Driver {

    private String id;
    private String license;
    private String name;
    private List<Date> dates;

    public  Driver(String id, String name , String license){
        this.id = id;
        this.name = name;
        this.license = license;
        dates = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean isFree(Date date ){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar temp = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for (Date d:dates) {
            temp.setTime(d);
            if((calendar.get(Calendar.YEAR) == temp.get(Calendar.YEAR)) & (calendar.get(Calendar.MONTH) == temp.get(Calendar.MONTH)) & (calendar.get(Calendar.DAY_OF_MONTH) == temp.get(Calendar.DAY_OF_MONTH)))
                return false;
        }
               return true;
    }



    public String toString(){
        return "id : " + id + ", name : " + name +", license : " + license ;
    }

    public void addDate(Date date) {dates.add(date);    }

    public void removeDate(Date date) {dates.remove(date);    }
}
