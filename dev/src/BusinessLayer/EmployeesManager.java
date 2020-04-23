package BusinessLayer;

import BusinessLayer.Day;
import BusinessLayer.Employee;
import BusinessLayer.ShiftType;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EmployeesManager {
    private List<Employee> employees;
    public EmployeesManager(){
        employees=new LinkedList<>();
    }
    public EmployeesManager(List<Employee> employees){
        this.employees=employees;
    }

    public  String addWorker(String name, String id, String hiringConditions, String bankId, int salary, Date startOfEmployment) {
        if (idExist(id))
            return null;
        Employee e=new Employee(name,id,hiringConditions,bankId,salary,startOfEmployment,employees.size()+1+"");
        employees.add(e);
        return employees.size()+"";
    }
    private  boolean idExist(String id) {
        for (Employee e : employees) {
            if (e.getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getManagers(ShiftType shiftType, Day day){
        List<String> managersAvailable=new LinkedList<>();
        for(Employee e : employeesLackConstrain(shiftType,day)){
            if(e.isSupervisor()){
                managersAvailable.add(e.getName() + ";"+e.getEmployeeId());

            }
        }
        return managersAvailable;
    }
    public List<String> getSpecificRolesEmployees(String role,ShiftType shiftType,Day day){
        List<String> workersAvailable=new LinkedList<>();
        for(Employee e : employeesLackConstrain(shiftType,day)){
            if(e.checkRoleExist(role)){
                workersAvailable.add(e.getName() + ";"+e.getEmployeeId());
            }
        }
        return workersAvailable;
    }
    public String getAllEmpoyees(){
        String output="";
        int i=1;
        for (Employee e : this.employees) {
            output+=i+") "+e.toString();
            i++;
        }
        return output;
    }
    private List<Employee> employeesLackConstrain(ShiftType shiftType,Day day) {
        List<Employee> availableEmployees = new LinkedList<>();
        for (Employee e : this.employees) {
            if(e.aviliable(shiftType,day) && e.isAvailable())
            {
                availableEmployees.add(e);
            }
        }
        return availableEmployees;

    }
    public String getEmployeeDetails(String employeeID){
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
                return e.toString();
        }
        return "no such emplyee";
    }
    public boolean emplyeeExist(String employeeID){
        for (Employee e : this.employees) {
            if (e.getEmployeeId().equals(employeeID))
                return true;
        }
        return false;

    }
    public boolean emplyeeAvailable(String employeeID){
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
                return e.isAvailable();
        }
        return false;
    }
    // employee self fields related methods
    public void addConstrain(String employeeID,ShiftType shiftType,Day day){
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
            {
                e.addConstrain(shiftType,day);
                break;
            }
        }
    }
    public void deleteConstrain(String employeeID,ShiftType shiftType,Day day){
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
            {
                e.deleteConstrain(shiftType,day);
                break;
            }
        }
    }
    public void addRole(String employeeID, String role){
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
            {
                e.addRole(role);
                break;
            }
        }
    }
    public void deleteRole(String employeeID, String role){
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
            {
                e.deleteRole(role);
                break;
            }
        }
    }
    public void changeSalary(String employeeID,int amount){
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
            {
                e.setSalary(amount);
                break;
            }
        }
    }
    public void changeName(String employeeID,String name) {
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
                e.setName(name);
        }
    }
    public void setSupervisor(String employeeID,boolean superivsor) {
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
                e.setSupervisor(superivsor);
        }    }
    public void setHiringConditions(String employeeID,String hiringConditions) {
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
                e.setHiringConditions(hiringConditions);
        }    }
    public void setBankId(String employeeID,String bankId) {
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
                e.setBankId(bankId);
        }
    }
    public void deleteEmployee(String employeeID) {
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
                e.setAvailble(false);
        }    }
}
