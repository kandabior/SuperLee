package DataAccessLayer.Employee;

import java.util.List;
import java.util.Map;

public class Shift_DTO {

    public class Shift {

        private String date;
        private String shiftType;
        private String shiftManagerID;
        private Map<String,List<String>> RoleInlay;
        private int branch;

        public Shift(String date, String shiftType, String shiftManagerID, Map<String,List<String>> RoleInlay,int branch){
            this.date = date;
            this.shiftType = shiftType;
            this.shiftManagerID = shiftManagerID;
            this.RoleInlay = RoleInlay;
            this.branch = branch;
        }

        public String getDate(){return date;}
        public String getShiftType(){return shiftType;}
        public String getShiftManagerID() {return shiftManagerID;}
        public Map<String,List<String>> getRoleInlay(){return RoleInlay;}
        public int getBranch() {return branch;}
    }

}
