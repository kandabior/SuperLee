package src.BusinessLayer.EmployeeModule;

import src.DataAccessLayer.Employee.ShiftMapper;
import src.DataAccessLayer.Employee.Shift_DTO;

import java.util.*;

public class ShiftManager {
    private int currentBranch = -1;
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
            shiftMapper.addRequirements(dayToString(day),stToString(shiftType),rol,roles.get(rol),currentBranch);
        }
    }

    public void existRequirements(Day day,ShiftType shiftType){
        Pair<Day,ShiftType> check = new Pair<Day, ShiftType>(day,shiftType);
        for (Pair<Day,ShiftType> curr : requirements.keySet()) {
            if (curr.newEquals(check)) {
                requirements.remove(curr);
                shiftMapper.deleteReq(dayToString(day),stToString(shiftType),currentBranch);
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
        shiftMapper.addSM(dateToString(date),stToString(shiftType),employeeManager,currentBranch);
    }

    public boolean ShiftExists(Date date, ShiftType shiftType) {
        for (Shift s: Shifts) {
            if(dateToString(s.getDate()).equals(dateToString(date)) && stToString(s.getShiftType()).equals(stToString(shiftType)))
                return true;
        }
        return false;
    }

    public void deleteShift(Date date, ShiftType shiftType) {
        for (Shift s: Shifts) {
            if(dateToString(s.getDate()).equals(dateToString(date)) && stToString(s.getShiftType()).equals(stToString(shiftType))) {
                Shifts.remove(s);
                shiftMapper.deleteShift(dateToString(s.getDate()),stToString(s.getShiftType()),currentBranch);
                shiftMapper.deleteSM(dateToString(date),stToString(shiftType),currentBranch);
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
            return "There is no ShiftType inlay at this date";
        return details;
    }

    public String requirementsRoleDetails(Day day, ShiftType shiftType) {
        String req = "";
        Pair<Day,ShiftType> check = new Pair<Day, ShiftType>(day,shiftType);
        for (Pair<Day,ShiftType> curr : requirements.keySet()) {
            if (curr.newEquals(check)){
                Map<String,Integer> det = requirements.get(curr);
                for (String s : det.keySet()) {
                    req = req + s + " : " + det.get(s) + "\n";
                }
            }
        }
        if (req.equals(""))
            return "No requirements for this day and Shift type";
        return req;
    }

    public List<Integer> getStoresWithStoreKeeperAtDate(Date date) {
        return shiftMapper.getStoresWithStoreKeeperAtDate(date);
    }

    public void loadBranch(int branch){
        currentBranch = branch;
        Shifts = new LinkedList<>();
        List<String[]> shifts_manager = shiftMapper.loadShiftManager(branch);
        if(shifts_manager != null) {
            loadShiftsManager(shifts_manager);
            List<Shift_DTO> shifts_of_the_current_branch = shiftMapper.loadBranch(branch);
            if(shifts_of_the_current_branch != null)
                loadShifts(shifts_of_the_current_branch);
        }
        List<String[]> req = shiftMapper.loadReq(branch);
        loadReq(req);
    }

    private void loadReq(List<String[]> req) {
        requirements = new HashMap<>();
        if (req != null){
            for(String [] s : req) {
                Pair<Day,ShiftType> curr = new Pair<>(stringToDay(s[0]),stringToShiftType(s[1]));
                if(requirements.get(curr) != null)
                    /*if(requirements.get(curr).get(s[2])!= null)
                                requirements.get(p).put(s[2],new Integer(requirements.get(p).get(s[2]).intValue()+Integer.parseInt(s[3])));
                            else*/
                    requirements.get(curr).put(s[2],Integer.parseInt(s[3]));
                else {
                    Map<String,Integer> map = new HashMap<>();
                    map.put(s[2],Integer.parseInt(s[3]));
                    requirements.put(curr,map);
                }
            }
        }
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


    private void loadShiftsManager(List<String[]> shifts_manager) {
        for (String[] s: shifts_manager) {
            Shifts.add(ShiftFromDTO(s));
        }
    }

    public void loadShifts(List<Shift_DTO> shiftsDTO){
        for (Shift_DTO shDTO: shiftsDTO) {
            for (Shift s: Shifts){
                if(dateToString(s.getDate()).equals(shDTO.getDate())&& stToString(s.getShiftType()).equals(shDTO.getShiftType())){
                    if(s.getRoleInlay().get(shDTO.getRole())!= null)
                        s.getRoleInlay().get(shDTO.getRole()).add(shDTO.getName());
                    else {
                        List<String> names = new LinkedList<>();
                        names.add(shDTO.getName());
                        s.getRoleInlay().put(shDTO.getRole(),names);
                    }
                }
            }
        }
    }

    private List<Shift_DTO> shiftDTOfromShift(Shift shift) {
        List<Shift_DTO> sh = new LinkedList<>();
        for (String role : shift.getRoleInlay().keySet()) {
            for (String name: shift.getRoleInlay().get(role)) {
                Shift_DTO curr = new Shift_DTO(dateToString(shift.getDate()),stToString(shift.getShiftType()),role,name,currentBranch);
                sh.add(curr);
            }
        }
        return sh;
    }

    private Shift ShiftFromDTO(String[] shiftsTDO){
        Map<String,List<String>> roleInlay = new HashMap<>();
        Shift curr = new Shift(stringToDate(shiftsTDO[0]),stringToShiftType(shiftsTDO[1]),shiftsTDO[2],roleInlay,Integer.parseInt(shiftsTDO[3]));
        return curr;
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

    private static Date stringToDate(String s){
        String[] parts = s.split("/");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(parts[2]));
        cal.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);//Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[0]));
        return cal.getTime();
    }

    private ShiftType stringToShiftType(String shiftType) {
        if (shiftType.equals("Morning"))
            return ShiftType.Morning;
        if (shiftType.equals("Evening"))
            return ShiftType.Evening;
        return null;
    }

    public List<Integer> getDriversAvailableAtDate(Date date) {
        List <String> dri = shiftMapper. getDriversAvailableAtDate(dateToString(date));
        List<Integer> drivers = new LinkedList<>();
        for (String s: dri) {
            drivers.add(Integer.parseInt(s.substring(s.indexOf(";")+1)));
        }
        return drivers;
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
