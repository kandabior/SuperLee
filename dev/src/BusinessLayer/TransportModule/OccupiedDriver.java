package src.BusinessLayer.TransportModule;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class OccupiedDriver {

    private int id;
    private List<Date> dates;


    public OccupiedDriver(int id){
        this.id = id;
        dates = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public boolean isFree(Date date ){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar temp = Calendar.getInstance();
        for (Date d:dates) {
            temp.setTime(d);
            if((calendar.get(Calendar.YEAR) == temp.get(Calendar.YEAR)) & (calendar.get(Calendar.MONTH) == temp.get(Calendar.MONTH)) & (calendar.get(Calendar.DAY_OF_MONTH) == temp.get(Calendar.DAY_OF_MONTH)))
                return false;
        }
               return true;
    }


    public void addDate(Date date) {dates.add(date);    }

    public void removeDate(Date date) {Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar temp = Calendar.getInstance();
        Date toRemove = null;
        for (Date d:dates) {
            temp.setTime(d);
            if((calendar.get(Calendar.YEAR) == temp.get(Calendar.YEAR)) & (calendar.get(Calendar.MONTH) == temp.get(Calendar.MONTH)) & (calendar.get(Calendar.DAY_OF_MONTH) == temp.get(Calendar.DAY_OF_MONTH)))
                toRemove = d;
        }
        if (toRemove != null)
            dates.remove(toRemove);   }
}
