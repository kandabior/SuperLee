package BusinessLayer;

import java.util.*;

public class shiftManager {
    private List<shift> shifts;
    private Map<Pair<Day, ShiftType>,Map<String,Integer>> requirements;

    public shiftManager(){
        shifts = new LinkedList<shift>();
        requirements = new HashMap<Pair<Day,ShiftType>, Map<String, Integer>>();
    }

    public void editRequirements(Day day, ShiftType shiftType, Map<String, Integer> roles) {
        existRequirements(day,shiftType);
        Pair<Day,ShiftType> newShift = new Pair<Day, ShiftType>(day,shiftType);
        requirements.put(newShift,roles);
    }

    public void existRequirements(Day day,ShiftType shiftType){
        Pair<Day,ShiftType> check = new Pair<Day, ShiftType>(day,shiftType);
        for (Pair<Day,ShiftType> curr : requirements.keySet()) {
            if (curr.newEquals(check)) {
                requirements.remove(curr);
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
        shift shift = new shift(date,shiftType,employeeManager,workers);
        shifts.add(shift);
    }

    public boolean ShiftExists(Date date, ShiftType shiftType) {
        for (shift s:shifts) {
            if(s.getDate().equals(date) && s.getShiftType().equals(shiftType))
                return true;
        }
        return false;
    }

    public void deleteShift(Date date, ShiftType shiftType) {
        for (shift s:shifts) {
            if(s.getDate().equals(date) && s.getShiftType().equals(shiftType))
                shifts.remove(s);
        }
    }


    public List<shift> getShifts(){return shifts;}
    public Map<Pair<Day,ShiftType>,Map<String,Integer>> getRequirements(){return requirements;}


    public String shiftDate(shift s) {
        String dateString = s.getDate().toString();
        return dateString;
    }

    public String shiftType(shift s) {
        if(s.getShiftType().equals(ShiftType.Morning))
            return "Morning";
        else
            return "Evening";
    }

    public String shiftManager(shift s) {
        return s.getShiftManagerID();
    }

    public String shiftWorkers(shift s) {
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
        for (shift s : shifts) {
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
            return "There is no BusinessLayer.shift inlay at this date";
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
            return "No requirements for this day and BusinessLayer.shift type";
        return req;
    }
}
