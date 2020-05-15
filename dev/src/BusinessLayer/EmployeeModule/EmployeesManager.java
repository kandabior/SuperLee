package BusinessLayer.EmployeeModule;

import DataAccessLayer.Employee.EmployeesMapper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EmployeesManager {
    private List<Employee> employees;
    private EmployeesMapper employeesMapper;
    int currentBranch;
    public EmployeesManager(){
        employees=new LinkedList<>();
        currentBranch=-1;
        employeesMapper=new EmployeesMapper();
    }


    public  String addWorker(String name, String id, String hiringConditions, String bankId,
                             int salary, Date startOfEmployment){
        if(currentBranch==-1)
            return null;
        if (idExist(id))
            return null;
        try{
            Employee e=employeesMapper.addEmployee(name,id,hiringConditions,bankId,salary,startOfEmployment,currentBranch);
            employees.add(e);
            return e.getEmployeeId();
        }
        catch (Exception e) {
            return null;
        }
    }
    private  boolean idExist(String id) {
        Employee e=employeesMapper.getByID(id);
        return (e!=null && e.isAvailable());
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
    public String getAllEmployees(){
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
    public boolean employeeExist(String employeeID){
        for (Employee e : this.employees) {
            if (e.getEmployeeId().equals(employeeID))
                return true;
        }
        return false;

    }
    public boolean employeeAvailable(String employeeID){
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
                updateEmployee(e);
                break;
            }
        }
    }
    public void deleteConstrain(String employeeID,ShiftType shiftType,Day day){
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
            {
                e.deleteConstrain(shiftType,day);
                updateEmployee(e);

                break;
            }
        }
    }
    public void addRole(String employeeID, String role){
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
            {
                e.addRole(role);
                updateEmployee(e);

                break;
            }
        }
    }
    public void deleteRole(String employeeID, String role){
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
            {
                e.deleteRole(role);
                updateEmployee(e);

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
            if(e.getEmployeeId().equals(employeeID)){
                e.setName(name);
            updateEmployee(e);
            }

        }
    }
    public void setSupervisor(String employeeID,boolean supervisor) {
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID)) {
                e.setSupervisor(supervisor);
                updateEmployee(e);

            }
        }    }
    public void setHiringConditions(String employeeID,String hiringConditions) {
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID)) {
                e.setHiringConditions(hiringConditions);
                updateEmployee(e);
            }
        }    }
    public void setBankId(String employeeID,String bankId) {
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID)) {
                e.setBankId(bankId);
                updateEmployee(e);
            }
        }
    }
    public void deleteEmployee(String employeeID) {
        for (Employee e : this.employees) {
            if(e.getEmployeeId().equals(employeeID))
                e.setAvailble(false);
                updateEmployee(e);

        }    }


        // Requires database access
    public String getDriverName(int employeeID) throws Exception {
        Employee driver=employeesMapper.getByEID(employeeID);
        if(driver==null)
        {
            throw new Exception("No such Drive in system!");

        }
        if (driver.getBranch()!=-0)
        {
            throw new Exception("The requested employee isn't a driver!");
        }
        return driver.getName();
    }

    public List<Integer> getDriversAvailableAtDate(Date date, String license) {
        List<Employee> drivers=employeesMapper.getAllEmployeesFromBranch(0);
        List<Integer> drivesOutput=new LinkedList<>();
        for(Employee driver:drivers)
        {
            if(driver.getConstrain(ShiftType.Morning,getDayFromDate(date))==null)
                drivesOutput.add((Integer.parseInt(driver.getEmployeeId())));

        }
        return drivesOutput;
    }

    private Day getDayFromDate(Date date){
        int day=date.getDay();
        switch (day) {
            case 1:
                return Day.Sunday;
            case 2:
                return Day.Monday;
            case 3:
                return Day.Tuesday;
            case 4:
                return Day.Wednesday;
            case 5:
                return Day.Thursday;
            case 6:
                return Day.Friday;
            case 7:
                return Day.Saturday;
            default:
                return Day.Saturday;

        }


    }


    public void loadBranch(int branch) {
        employees=employeesMapper.getAllEmployeesFromBranch(branch);
        this.currentBranch=branch;
    }

    public void updateEmployee(Employee e)
    {
        employeesMapper.updateEmployee(e);
    }
}
