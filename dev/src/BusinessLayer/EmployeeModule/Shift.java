package BusinessLayer.EmployeeModule;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Shift {

    private Date date;
    private ShiftType shiftType;
    private String shiftManagerID;
    private Map<String,List<String>> RoleInlay;

    public Shift(Date date, ShiftType shiftType, String shiftManagerID, Map<String,List<String>> RoleInlay){
        this.date = date;
        this.shiftType = shiftType;
        this.shiftManagerID = shiftManagerID;
        this.RoleInlay = RoleInlay;
    }

    public Date getDate(){return date;}
    public ShiftType getShiftType(){return shiftType;}
    public String getShiftManagerID() {return shiftManagerID;}
    public Map<String,List<String>> getRoleInlay(){return RoleInlay;}

    //save
}
