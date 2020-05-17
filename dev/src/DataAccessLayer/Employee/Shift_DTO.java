package DataAccessLayer.Employee;

import java.util.List;
import java.util.Map;

public class Shift_DTO {

    private String date;
    private String shiftType;
    private int shiftManagerID;
    String role;
    String name;
    private int branch;

    public Shift_DTO(String date, String shiftType, int shiftManagerID,String role,String name,int branch){
        this.date = date;
        this.shiftType = shiftType;
        this.shiftManagerID = shiftManagerID;
        this.role = role;
        this.name = name;
        this.branch = branch;
    }

    public String getDate(){return date;}
    public String getShiftType(){return shiftType;}
    public int getShiftManagerID() {return shiftManagerID;}
    public String getRole() { return role;}
    public String getName() {return name;}
    public int getBranch() {return branch;}
}
