package BusinessLayer.EmployeeModule;

import DataAccessLayer.Employee.ShiftMapper;
import DataAccessLayer.Employee.Shift_DTO;

import java.util.*;

public class ShiftManager {
    private int currentBranch;
    private List<Shift> Shifts;
    private Map<Pair<Day, ShiftType>,Map<String,Integer>> requirements;
    private ShiftMapper shiftMapper;

    public ShiftManager(){
        Shifts = new LinkedList<Shift>();
        requirements = new HashMap<Pair<Day,ShiftType>, Map<String, Integer>>();
        shiftMapper = new ShiftMapper();
    }

    public void editRequirements(Day day, ShiftType shiftType, Map<String, Integer> roles) {
        existRequirements(day,shiftType);
        Pair<Day,ShiftType> newShift = new Pair<Day, ShiftType>(day,shiftType);
        requirements.put(newShift,roles);
        for (String rol:roles.keySet()) {
            shiftMapper.addRequirements(dayToString(day),stToString(shiftType),rol,roles.get(rol));
        }
    }

    public void existRequirements(Day day,ShiftType shiftType){
        Pair<Day,ShiftType> check = new Pair<Day, ShiftType>(day,shiftType);
        for (Pair<Day,ShiftType> curr : requirements.keySet()) {
            if (curr.newEquals(check)) {
                requirements.remove(curr);
                shiftMapper.deleteReq(dayToString(day),stToString(shiftType));
                break;
            }
        }
    }

    public Map<String, Integer> requiredRoles(Day day, ShiftType shiftType) {
        Pair<Day,ShiftType> check = new Pair<Day, ShiftType>(day,shiftType);
        for (Pair<Day,ShiftType> curr : requirements.keySet()) {
            if (curr.newEquals(check))
                return requirements.get(curr);
        }
        return null;
    }

    public void addShift(Date date, ShiftType shiftType, String employeeManager, Map<String, List<String>> workers) {
        Shift shift = new Shift(date,shiftType,employeeManager,workers,currentBranch);
        Shifts.add(shift);
        shiftMapper.add(shiftDTOfromShift(shift));
    }

    public boolean ShiftExists(Date date, ShiftType shiftType) {
        for (Shift s: Shifts) {
            if(s.getDate().equals(date) && s.getShiftType().equals(shiftType))
                return true;
        }
        return false;
    }

    public void deleteShift(Date date, ShiftType shiftType) {
        for (Shift s: Shifts) {
            if(s.getDate().equals(date) && s.getShiftType().equals(shiftType)) {
                Shifts.remove(s);
                shiftMapper.deleteShift(dateToString(s.getDate()),currentBranch,stToString(s.getShiftType()));
            }
        }
    }


    public List<Shift> getShifts(){return Shifts;}
    public Map<Pair<Day,ShiftType>,Map<String,Integer>> getRequirements(){return requirements;}


    public String shiftDate(Shift s) {
        String dateString = s.getDate().toString();
        return dateString;
    }

    public String shiftType(Shift s) {
        if(s.getShiftType().equals(ShiftType.Morning))
            return "Morning";
        else
            return "Evening";
    }

    public String shiftManager(Shift s) {
        return s.getShiftManagerID();
    }

    public String shiftWorkers(Shift s) {
        Map<String,List<String>> workers = s.getRoleInlay();
        String details = "";
        for (String curr : workers.keySet()) {
            details = details + curr + ":\n";
            for (String name : workers.get(curr)) {
                details = details + name +"\n";
            }
        }
        return details;
    }

    public String shiftDetails(Date date, ShiftType shiftType) {
        String details = "";
        for (Shift s : Shifts) {
            if(s.getDate().equals(date) && s.getShiftType().equals(shiftType)){
                details = details + "Personnel Manager ID: " + s.getShiftManagerID();
                Map<String,List<String>> workers = s.getRoleInlay();
                for (String curr : workers.keySet()) {
                    details = details + curr + ":\n";
                    for (String name : workers.get(curr)) {
                        details = details + name +"\n";
                    }
                }
                break;
            }
        }
        if(details.equals(""))
            return "There is no BusinessLayer.EmployeeModule.Shift inlay at this date";
        return details;
    }

    public String requirementsRoleDetails(Day day, ShiftType shiftType) {
        String req = "";
        Pair<Day,ShiftType> check = new Pair<Day, ShiftType>(day,shiftType);
        for (Pair<Day,ShiftType> curr : requirements.keySet()) {
            if (curr.newEquals(check)){
                Map<String,Integer> det = requirements.get(curr);
                for (String s : det.keySet()) {
                    req = req + s + " : " + det.get(s).toString() + "\n";
                }
            }
        }
        if (req.equals(""))
            return "No requirements for this day and BusinessLayer.EmployeeModule.Shift type";
        return req;
    }

    public List<Integer> getStoresWithStoreKeeperAtDate(Date date) {
        return shiftMapper.getStoresWithStoreKeeperAtDate(date);
    }

    public void loadBranch(int branch){
       List<Shift_DTO> shifts_of_the_current_branch = shiftMapper.loadBranch(branch);
       loadShifts(shifts_of_the_current_branch);
    }

    public void loadShifts(List<Shift_DTO> shifts){

    }

    private List<Shift_DTO> shiftDTOfromShift(Shift shift) {
        //TODO
        return null;
    }

    private Shift ShiftFromShiftDTO(List<Shift_DTO> shift_dto){
        //TODO
        return null;
    }

    private String stToString(ShiftType shiftType) {
        switch (shiftType){
            case Morning:
                return "Morning";
            case Evening:
                return "Evening";
            default:
                return null;

        }
    }

    private String dayToString(Day day) {
        switch (day) {
            case Sunday:
                return "Sunday";
            case Monday:
                return "Monday";
            case Tuesday:
                return "Tuesday";
            case Wednesday:
                return "Wednesday";
            case Thursday:
                return "Thursday";
            case Friday:
                return "Friday";
            case Saturday:
                return "Saturday";
            default:
                    return null;
        }
    }

    private String dateToString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1) +"/" + calendar.get(Calendar.YEAR);
    }
}

/*    private static Date stringToDate(String s){
        String[] parts = s.split("/");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(parts[2]));
        cal.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);//Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[0]));
        return cal.getTime();
    }

    private static String dateToString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1) +"/" + calendar.get(Calendar.YEAR);
    }*/
