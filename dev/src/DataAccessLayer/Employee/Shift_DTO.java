package src.DataAccessLayer.Employee;

import java.util.List;
import java.util.Map;

public class Shift_DTO {

    private String date;
    private String shiftType;
    String role;
    String name;
    private int branch;

    public Shift_DTO(String date, String shiftType,String role,String name,int branch){
        this.date = date;
        this.shiftType = shiftType;
        this.role = role;
        this.name = name;
        this.branch = branch;
    }

    public String getDate(){return date;}
    public String getShiftType(){return shiftType;}
    public String getRole() { return role;}
    public String getName() {return name;}
    public int getBranch() {return branch;}
}
