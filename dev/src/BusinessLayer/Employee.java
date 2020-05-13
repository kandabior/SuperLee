package BusinessLayer;

import BusinessLayer.Day;
import BusinessLayer.ShiftType;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Employee {
    private boolean isAvailable;
    private String name;
    private String ID;
    private boolean isSupervisor;
    private List<String> roles;
    private String hiringConditions;
    private String bankId;
    private int salary;
    private Date startOfEmployment;
    private String employeeId;
    private List<Constrain> constrains;

    public Employee(String name, String ID,
                    String hiringConditions, String bankId, int salary, Date startOfEmployment, String employeeId) {
        this.name = name;
        this.ID = ID;
        this.isSupervisor = false;
        this.hiringConditions = hiringConditions;
        this.bankId = bankId;
        this.salary = salary;
        this.startOfEmployment = startOfEmployment;
        this.employeeId = employeeId;
        constrains=new LinkedList<>();
        roles = new LinkedList<>();
        isAvailable=true;
    }

    public boolean isAvailable()
    {
        return isAvailable;
    }
    public void addRole(String role){
        if(!checkRoleExist(role))
            roles.add(role);
    }
    public void deleteRole(String role){
        if(checkRoleExist(role))
            roles.remove(role);
    }
    public boolean checkRoleExist(String Role){
        for (String r:roles)
            if (r.equals(Role))
                return true;
        return false;
    }

    public void addConstrain(ShiftType shiftType, Day day){
        if(aviliable(shiftType,day))
        {
            constrains.add(new Constrain(shiftType,day));
        }
    }
    public void deleteConstrain(ShiftType shiftType,Day day){
        Constrain toRemove= getConstrain(shiftType,day);
        if( toRemove!=null)
            constrains.remove(toRemove);

    }
    public Constrain getConstrain(ShiftType shiftType, Day day){
        Constrain sameAttributesConstrain=null;

        for(Constrain c : constrains)
        {
            if (c.getShiftType()==shiftType & c.getDay()==day) {
                sameAttributesConstrain = c;
                break;

            }
        }
        return sameAttributesConstrain;
    }
    public boolean aviliable(ShiftType shiftType, Day day){
        for(Constrain c : constrains)
        {
            if (c.getShiftType()==shiftType & c.getDay()==day) {
                return false;

            }
        }
        return true;
    }
    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                "Available to work? "+isAvailable +
                ", ID='" + ID + "\'\n\t"+
                ", isSupervisor=" + isSupervisor +
                ", roles=" + roles +
                ", hiringConditions='" + hiringConditions + "\'\n\t" +
                ", bankId='" + bankId + '\'' +
                ", salary=" + salary +
                ", startOfEmployment=" + startOfEmployment + "\'\n\t" +
                ", employeeId='" + employeeId + '\'' +
                ", constrains=" + constrains +
                "\n";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSupervisor(boolean supervisor) {
        isSupervisor = supervisor;
    }

    public void setHiringConditions(String hiringConditions) {
        this.hiringConditions = hiringConditions;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public boolean isSupervisor() {
        return isSupervisor;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getHiringConditions() {
        return hiringConditions;
    }

    public String getBankId() {
        return bankId;
    }

    public int getSalary() {
        return salary;
    }

    public Date getStartOfEmployment() {
        return startOfEmployment;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public List<Constrain> getConstrains() {
        return constrains;
    }

    public void setAvailble(boolean avail) {
        isAvailable = avail;
    }
}
