package src.DataAccessLayer.Transport.DTO;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DTO_OccupiedDriver {

    private int id;
    private List<Date> dates;

    public DTO_OccupiedDriver(int id){
        this.id = id;
        this.dates = new LinkedList<>();
    }


    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
