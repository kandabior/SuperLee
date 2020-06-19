package src.BusinessLayer.EmployeeModule;

import javafx.util.Pair;
import src.BusinessLayer.TransportModule.TransportService;

import java.util.*;


public class Service {
    private static Service service;
    private ShiftManager shiftManager;
    private EmployeesManager employeesManager;
    private TransportService transportService;
    public  static Service getInstance()
    {
        if(service==null)
            service=new Service();
        return service;
    }
    private Service(){
        shiftManager = new ShiftManager();
        employeesManager=new EmployeesManager();
        transportService=new TransportService();
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
        List<Shift> Shifts = shiftManager.getShifts();
        for (Shift s : Shifts) {
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
    public void deleteRole(String employeeID, String role)
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
    public String addWorker(String name, String ID, String hiringConditions, String bankId, int salary, Date startOfEmployment,String license) throws Exception {
        return employeesManager.addWorker(name,ID,hiringConditions,bankId,salary,startOfEmployment,license);

    }

    public String employeeDetails(String employeeID){
        return employeesManager.getEmployeeDetails(employeeID);
    }
    public String getAllEmployees() {
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
        shiftManager.loadBranch(branch);
    }

    public List<Integer> getStoresWithStoreKeeper(Date date) {
        return shiftManager.getStoresWithStoreKeeperAtDate(date);
    }

    public List<Integer> getDriversAvailableAtDate(Date date, String license) {
        List<Integer> dr = new LinkedList<>();
        List<Integer> de =  employeesManager.getDriversAvailableAtDate(date,license);
        List<Integer> dS = shiftManager.getDriversAvailableAtDate(date);
        for (Integer dCurr :de){
            if(dS.contains(dCurr))
                dr.add(dCurr);
        }
        return dr;

    }

    public String getDriveName(int employeeID) throws Exception {
        return employeesManager.getDriverName(employeeID);
    }

    public List<Pair<Integer, String>> getEmployeesMessages() {
        return employeesManager.getEmployeesMessages();
    }

    public void deleteEmployeeMessage(int id) {
        employeesManager.deleteEmployeeMessage(id);
    }

    public List<Pair<Integer, String>> getOrdersMessages() {
        List<Integer> messagesInput = employeesManager.getOrdersMessages();
        List<Pair<Integer, String>> output = new LinkedList<>();
        for (Integer orderID : messagesInput)
        {
            String s=transportService.getStringRequest(orderID);
            if(!s.equals("Invalid Order ID"))
                output.add(new Pair<>(orderID,s));
        }
        return output;
    }

    public void respondOrderMessage(int id, int respond) {
        employeesManager.respondOrderMessage(id,respond);

}

    public boolean existsShipment(Date date, int branch) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String d=calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1) +"/" + calendar.get(Calendar.YEAR);
            return transportService.hasTransport(branch,d);
        }

}
