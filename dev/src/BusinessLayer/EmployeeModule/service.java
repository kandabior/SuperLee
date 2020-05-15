package BusinessLayer.EmployeeModule;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class service {

    private shiftManager shiftManager;
    private EmployeesManager employeesManager;

    public service(){
        shiftManager = new shiftManager();
        employeesManager=new EmployeesManager();
    }

    public void editRequirements(String day, String shiftType, Map<String, Integer> roles) {
        shiftManager.editRequirements(stringToDay(day),stringToShiftType(shiftType),roles);
    }

    public Map<String,Integer> requiredRoles(String day, String shiftType) {
        return shiftManager.requiredRoles(stringToDay(day),stringToShiftType(shiftType));
    }

    public List<String> relevantEmployees(String day, String shiftType, String role) {
        return employeesManager.getSpecificRolesEmployees(role,stringToShiftType(shiftType),stringToDay(day));
    }

    public void addShift(Date date, String shiftType, String employeeManager, Map<String, List<String>> workers) {
        shiftManager.addShift(date,stringToShiftType(shiftType),employeeManager,workers);
    }

    public boolean ShiftExists(Date date, String shiftType) {
        return shiftManager.ShiftExists(date,stringToShiftType(shiftType));
    }

    public void deleteShift(Date date, String shiftType) {
        shiftManager.deleteShift(date,stringToShiftType(shiftType));
    }

    public List<String> relevantPersonnelManager(String day, String shiftType) {
        return employeesManager.getManagers(stringToShiftType(shiftType),stringToDay(day));
    }

    public String getShiftHistory() {
        String history = "";
        List<shift> shifts = shiftManager.getShifts();
        for (shift s : shifts) {
            history = history + "\nDate: " + shiftManager.shiftDate(s)+
                    "\nShift type: " + shiftManager.shiftType(s)+
                    "\nPersonnel Manager ID: " + shiftManager.shiftManager(s)+
                    "\nWorkers:\n " + shiftManager.shiftWorkers(s);
        }
        return history;
    }

    public String watchShift(Date date, String shiftType) {
        return shiftManager.shiftDetails(date,stringToShiftType(shiftType));
    }

    public String watchRequirementsRole(String day, String shiftType) {
        return shiftManager.requirementsRoleDetails(stringToDay(day),stringToShiftType(shiftType));
    }
    public boolean employeeExist(String employeeID){
        return employeesManager.employeeExist(employeeID);
    }
    public boolean employeeAvailable(String employeeID){
        return employeesManager.employeeAvailable(employeeID);
    }
    public void addConstrain(String employeeID, String day, String shiftType) {
        employeesManager.addConstrain(employeeID,stringToShiftType(shiftType),stringToDay(day));
    }
    public void deleteConstrain(String employeeID, String shiftType, String day)    {
        employeesManager.deleteConstrain(employeeID,stringToShiftType(shiftType),stringToDay(day));
    }
    public void addRole(String employeeID,String role) {
        employeesManager.addRole(employeeID,role);
    }
    public void deletRole(String employeeID,String role)
    {
        employeesManager.deleteRole(employeeID,role);
    }
    public void changeSalary(String employeeID,int salary)
    {
        employeesManager.changeSalary(employeeID,salary);
    }
    public void changeName(String employeeID,String name)
    {
        employeesManager.changeName(employeeID,name);
    }
    public void setSupervisor(String employeeID,boolean supervisor){
        employeesManager.setSupervisor(employeeID,supervisor);
    }
    public void setHiringConditions(String employeeID,String hiringConditions) {
        employeesManager.setHiringConditions(employeeID,hiringConditions);
    }
    public void setBankId(String employeeID,String bankID) {
        employeesManager.setBankId(employeeID,bankID);
    }
    public void deleteWorker(String employeeID){
        employeesManager.deleteEmployee(employeeID);
    }
    public String addWorker(String name, String ID, String hiringConditions, String bankId, int salary, Date startOfEmployment) throws Exception {
        return employeesManager.addWorker(name,ID,hiringConditions,bankId,salary,startOfEmployment);

    }
    public String employeeDetails(String emolyeeID){
        return employeesManager.getEmployeeDetails(emolyeeID);
    }
    public String getAllEmplyees() {
        return employeesManager.getAllEmployees();
    }
    private Day stringToDay(String day) {
        if (day.equals("Sunday"))
            return Day.Sunday;
        if (day.equals("Monday"))
            return Day.Monday;
        if (day.equals("Thursday"))
            return Day.Thursday;
        if (day.equals("Wednesday"))
            return Day.Wednesday;
        if (day.equals("Tuesday"))
            return Day.Tuesday;
        if (day.equals("Friday"))
            return Day.Friday;
        if (day.equals("Saturday"))
            return Day.Saturday;
        return null;
    }
    private ShiftType stringToShiftType(String shiftType) {
        if (shiftType.equals("Morning"))
            return ShiftType.Morning;
        if (shiftType.equals("Evening"))
            return ShiftType.Evening;
        return null;
    }

    public void loadBranch(int branch) {
        employeesManager.loadBranch(branch);
    }

    public List<Integer> getStoresWithStoreKeeper(Date date) {
        return shiftManager.getStoresWithStoreKeeperAtDate(date);
    }

    public List<Integer> getDriversAvailableAtDate(Date date, String license) {
        return employeesManager.getDriversAvailableAtDate(date,license);

    }

    public String getDriveName(int employeeID) throws Exception {
        return employeesManager.getDriverName(employeeID);
    }
}
