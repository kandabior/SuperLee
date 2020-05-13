package BusinessLayer.TransportModule;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Truck {

    private String id;
    private String model;
    private double weight;
    private double maxWeight;
    private List<Date> dates;

    public  Truck(String id, String model , double weight, double maxWeight){
        this.id = id;
        this.model = model;
        this.weight = weight;
        this.maxWeight = maxWeight;
        dates = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
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
        return "id : " + id + ", model : " + model +", weight : " + weight + ", max weight : " + maxWeight;
    }


    public void addDate(Date date) {dates.add(date);   }

    public void removeDate(Date date) {dates.remove(date);        }
}
