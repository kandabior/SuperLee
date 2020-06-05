package src.DataAccessLayer.Transport.DTO;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DTO_Truck {

        private String id;
        private String model;
        private double weight;
        private double maxWeight;
        private List<Date> dates;


        public double getMaxWeight() {
            return maxWeight;
        }

        public void setMaxWeight(double maxWeight) {
            this.maxWeight = maxWeight;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
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

        public  DTO_Truck(String id, String model , double weight, double maxWeight){
            this.id = id;
            this.model = model;
            this.weight = weight;
            this.maxWeight = maxWeight;
            dates = new LinkedList<>();
        }

        public String toString(){
            return "id : " + id + ", model : " + model +", weight : " + weight + ", max weight : " + maxWeight;
        }
}

