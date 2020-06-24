package src.PresentationLayer;
import javafx.util.Pair;


import src.BusinessLayer.EmployeeModule.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;

public class EmployeesMenu {

    Service service = Service.getInstance();
    int currentBranch;

    public static void main() {
        EmployeesMenu view = new EmployeesMenu();
        view.mainMenu();
    }

    private void mainMenu(){
        try {
            while (true) {
                System.out.println("Welcome to the employee department!\n" +
                        "Select a branch:\n" +
                        "For Branch Actions, Enter:\t\t1 \n" +
                        "For messages regarding Orders Enter:\t 2\n" +
                        "For messages regarding Employees Enter:\t 3\n" +
                        "To return to the main  menu, enter\t   4\n");
                int selection = numberFromRange(1,4);
                if (selection ==1)
                    branchActions();
                else if (selection == 2)
                    OrdersMessages();
                else if (selection == 3)
                    EmployeesMessages();
                else if (selection == 4)
                    return;
            }
        }catch (Exception e){
            System.out.println("\nSorry, you have entered invalid input in this action\n");
            return;
        }
    }

    private void EmployeesMessages() {
        Scanner ans = new Scanner(System.in);
        System.out.println("Employees Messages:\n");
        List<Pair<Integer,String>> messages=service.getEmployeesMessages();
        for(Pair<Integer,String> message:messages){
            System.out.println("ID of message: " +message.getKey() +"\t  content: "+message.getValue() + "\n");
        }
        try {
            while (true) {
                System.out.println("Enter a message ID to delete it, or -1 to return \n");
                int id = ans.nextInt();
                if (id==-1)
                    return;
                else if(!checkExistsMessage(messages,id))
                {
                    System.out.println("\nSorry, you have entered invalid message ID\n");
                }
                else {
                    deleteMessage(messages,id);
                    service.deleteEmployeeMessage(id);
                    System.out.println("message deleted!");
                }
            }
        }catch (Exception e){
            System.out.println("\nSorry, you have entered invalid input in this action\n");
            return;
        }

    }

    private void deleteMessage(List<Pair<Integer, String>> messages, int id) {
        Pair<Integer, String> temp=null;

        for (Pair<Integer,String> message:messages) {
            if(message.getKey()==id)
                temp=message;
        }
        messages.remove(temp);
    }

    private boolean checkExistsMessage(List<Pair<Integer, String>> messages, int id) {

        for (Pair<Integer,String> message:messages) {
            if(message.getKey()==id)
                return true;
        }
        return false;
    }

    private void OrdersMessages() {
        Scanner ans = new Scanner(System.in);
        System.out.println("Orders Messages:\n");
        List<Pair<Integer,String>> messages=service.getOrdersMessages();
        for(Pair<Integer,String> message:messages){
            System.out.println("ID of message: " +message.getKey() +"\t  content: "+message.getValue() + "\n");
        }
        try {
            while (true) {
                System.out.println("Enter a message ID to respond , or -1 to return \n");
                int id = ans.nextInt();
                if (id==-1)
                    return;
                else if(!checkExistsMessage(messages,id))
                {
                    System.out.println("\nSorry, you have entered invalid message ID\n");
                }
                else {
                    respondOrderMessage(messages,id);
                }
            }
        }catch (Exception e){
            System.out.println("\nSorry, you have entered invalid input in this action\n");
            return;
        }

    }

    private void respondOrderMessage(List<Pair<Integer, String>> messages, int id) {
        System.out.println("Enter respond : 1 - decline , 2 - approve\n");
        int respond =numberFromRange(1,2);
        service.respondOrderMessage(id,respond);
        deleteMessage(messages,id);
    }

    private void branchActions(){
        try {
            while (true) {
                System.out.println("Select a branch:\n" +
                        "for drivers area, enter:\n     0 \n" +
                        "Otherwise enter a store number, a number between\n     1-9\n" +
                        "To return to the main HR menu, enter\n     10\n");
                int branch = numberFromRange(0,10);
                if (branch >= 0 & branch <= 9) {
                    currentBranch = branch;
                    service.loadBranch(branch);
                    startPersonnelManager();
                } else if (branch == 10)
                    return;
                else
                    System.out.println("\nInvalid selection\n");
            }
        }catch (Exception e){
            System.out.println("\nSorry, you have entered invalid input in this action\n");
            return;
        }
    }

    private void startPersonnelManager(){
        boolean stop = false;
        while (!stop) {
            System.out.println("Where to enter?\n" +
                    "1. Employees\n" +
                    "2. Shifts\n" +
                    "3. Back");
            int selectedClass = numberFromRange(1,3);
            switch (selectedClass) {
                case 1:
                    startEmployee();
                    break;
                case 2:
                    startShift();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("\nInvalid selection\n");
                    break;
            }
        }
    }

    private void startEmployee(){
        boolean contin=true;
        while (contin) {
            System.out.println("Select the action you want to take:\n" +
                    "1. View all employees details\n" +
                    "2. View single employee details and change them\n" +
                    "3. Add new worker\n" +
                    "4. Back");
            Scanner ans = new Scanner(System.in);
            int selectedAction = numberFromRange(1,4);
            switch (selectedAction) {
                case 1:
                    System.out.println(service.getAllEmployees());
                    break;
                case 2:
                    System.out.println("enter employee ID:");
                    String ID=ans.nextLine();
                    if(!(service.employeeExist(ID)|| service.employeeAvailable(ID)))
                        System.out.println("not exist/available ID!");
                    else{
                        handleSingleEmplyee(ID);
                    }
                    break;
                case 3:
                    addNewWorker();
                    break;
                case 4:
                    contin=false;
                    break;
                default:
                    break;
            }

        }
    }

    private void addNewWorker(){
        Scanner ans = new Scanner(System.in);
        System.out.println("Write the following worker's details:");
        String name = inputNotEmpty("name");
        String ID = inputNotEmpty("ID");
        String hiringConditions =inputNotEmpty("hiring conditions:");
        String bankId = inputNotEmpty("bank Id");
        int salary = inputNumber("salary");
        String date;
        Date startEmployment=new Date();
        boolean correctdate=false;
        while(!correctdate)
            try {
                System.out.println("start of employment:"+
                        "Please enter the date in the format dd/mm/yyyy");
                date = ans.nextLine();
                startEmployment= new SimpleDateFormat("dd/MM/yyyy").parse(date);
                correctdate=true;
            } catch (ParseException e) {
                System.out.println("\nInvalid date\n");
            }
        String licence = null;
        if(currentBranch == 0)
            licence = choseLicence();
        try {
            String emplyeeId = service.addWorker(name, ID, hiringConditions, bankId, salary, startEmployment,licence);
            if (emplyeeId == null) {
                System.out.println("ID is in use! aborted...");
            } else {
                service.addRole(emplyeeId,"driver");
                System.out.println("Success! new worker ID: " + emplyeeId);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleSingleEmplyee(String ID) {
        boolean contin=true;
        while (contin) {
            System.out.println("Select the action you want to take:\n" +
                    "1. View all employee details\n" +
                    "2. Add Constrain\n" +
                    "3. Remove Constrain\n" +
                    "4. Add role\n" +
                    "5. Remove role\n" +
                    "6. Remove worker\n" +
                    "7. Change salary\n" +
                    "8. Change name\n" +
                    "9. Set as supervisor\n" +
                    "10. Downgrade from supervisor\n" +
                    "11. Change bank details\n" +
                    "12. Change hiring conditions\n" +
                    "13. Back");
            String ask;
            int selectedAction = numberFromRange(1,13);
            switch (selectedAction) {
                case 1:
                    System.out.println(service.employeeDetails(ID));
                    break;
                case 2:
                    addConstrain(ID);
                    break;
                case 3:
                    removeConstrain(ID);
                    break;
                case 4:
                    if(currentBranch == 0)
                        System.out.println("This is a drivers branch\n" +
                                "There are no additional roles to add.");
                    else {
                        //ask = "enter new role:";
                        String role = roleFromList();
                        service.addRole(ID, role);
                    }
                    break;
                case 5:
                    if(currentBranch == 0)
                        System.out.println("This is a drivers branch\n" +
                                "There are no roles to delete.");
                    else {
                        //ask = "enter role to delete:";
                        String role1 = roleFromList();
                        service.deleteRole(ID, role1);
                    }
                    break;
                case 6:
                    service.deleteWorker(ID);
                    System.out.println("Employee deleted");
                    contin=false;
                    break;
                case 7:
                    ask = "enter new salary:";
                    service.changeSalary(ID,inputNumber(ask));
                    break;
                case 8:
                    ask = "enter new name:";
                    service.changeName(ID,inputNotEmpty(ask));
                    break;
                case 9:
                    service.setSupervisor(ID,true);
                    break;
                case 10:
                    service.setSupervisor(ID,false);
                    break;
                case 11:
                    ask = "enter new bank details:";
                    service.setBankId(ID,inputNotEmpty(ask));
                    break;
                case 12:
                    ask = "enter new hiring conditions";
                    service.setHiringConditions(ID,inputNotEmpty(ask));
                    break;
                case 13:
                    contin=false;
                    break;
                default:
                    break;
            }

        }
    }

    private void removeConstrain(String id) {
        String day=chooseDay();
        while (day==null){
            day=chooseDay();
        }
        String shiftType=chooseShiftType();
        while (shiftType==null){
            shiftType=chooseShiftType();
        }
        service.deleteConstrain(id,shiftType,day);

    }

    private void addConstrain(String id) {
        String day=chooseDay();
        while (day==null){
            day=chooseDay();
            System.out.println("\nInvalid option\n");
        }
        String shiftType=chooseShiftType();
        while (shiftType==null){
            System.out.println("\nInvalid option\n");
            shiftType=chooseShiftType();
        }
        service.addConstrain(id,day,shiftType);


    }

    private void startShift(){
        boolean stop = false;
        while (!stop) {
            System.out.println("Select the action you want to take:\n" +
                    "1. Creating/Change role requirements for Shift (by day and type of Shift)\n" +
                    "2. Creating a new Shift and role inlay\n" +
                    "3. View role requirements for Shift by day and Shift type\n" +
                    "4. Watch inlay for Shift by date and type of Shift\n" +
                    "5. View all Shift history\n" +
                    "6. Back");
            int selectedAction = numberFromRange(1,6);
            switch (selectedAction) {
                case 1:
                    editRequirements();
                    return;
                case 2:
                    inlayForShift();
                    return;
                case 3:
                    watchRequirementsRole();
                    return;
                case 4:
                    watchShift();
                    return;
                case 5:
                    history();
                    return;
                case 6:
                    return;
                default:
                    System.out.println("\nInvalid selection\n");
                    break;
            }
        }
    }

    private void editRequirements(){
        String day = chooseDay();
        while (day == null){
            day = chooseDay();
        }
        String shiftType = null;
        if(currentBranch == 0)
            shiftType = "Morning";
        else {
            shiftType = chooseShiftType();
            while (shiftType == null) {
                shiftType = chooseShiftType();
            }
        }
        Map<String,Integer> roles = new HashMap<String, Integer>();
        if(currentBranch == 0) {
            int num = inputNumber("This is a drivers branch\n" + "How many drivers would you want to shift (other than a shift manager)?");
            roles.put("driver",num);
        }
        else {
            boolean stop = false;
            while (!stop) {
                String role = roleFromList();
                while (role == null) {
                    role = roleFromList();
                }
                String ask = "How Many Employees in the role " + role + " needed?";
                int numOfEmployee = inputNumber(ask);
                roles.put(role, numOfEmployee);
                String ask1 = "Need more roles? Select y/n";
                String needMore = yesOrNo(ask1);
                if (needMore.equals("n"))
                    stop = true;
            }
        }
        service.editRequirements(day,shiftType,roles);
        System.out.println("Done\n\n");
    }

    private void inlayForShift() {
        Date date = dateFromUser();
        while (date == null){
            date = dateFromUser();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String day = dayByNumber(dayOfWeek);

        String shiftType = null;
        if(currentBranch == 0)
            shiftType = "Morning";
        else {
            shiftType = chooseShiftType();
            while (shiftType == null) {
                shiftType = chooseShiftType();
            }
        }
        boolean Exists = service.ShiftExists(date, shiftType);
        if (Exists) {
            if(shiftType=="Morning" && service.existsShipment(date,currentBranch)){
                System.out.println("There is a shipment in that day, therefore a change cannot be made");
                return;
            }
            String ask = "There is already an inlay for this date, would you like to replace the existing one? Select y/n";
            String needMore = yesOrNo(ask);
            if (needMore.equals("n")) {
                return;
            } else {
                service.deleteShift(date, shiftType);
                Exists = false;
            }
        }
        if (!Exists) {
            String employeeManager = chooseEmployeeManager(day, shiftType);
            if(employeeManager == null){
                System.out.println("There is no manager available for a Shift, a Shift cannot exist without a manager.");
                return;
            }
            else {
                Map<String, List<String>> workers = new HashMap<String, List<String>>();

                Map<String, Integer> roles = service.requiredRoles(day, shiftType);
                if (roles == null) {
                    System.out.println("There are no role requirements for this Shift, The Shift is kept with a manager only");
                    service.addShift(date, shiftType, employeeManager, workers);
                } else {
                    boolean success = true;
                    List<String> selectedEmployees = new LinkedList<>();
                    for (String currRole : roles.keySet()) {
                        List<String> relevantEmployees = service.relevantEmployees(day, shiftType, currRole);
                        relevantEmployees.removeAll(selectedEmployees);
                        if (relevantEmployees == null || relevantEmployees.size() == 0) {
                            System.out.println("\nThere is no employee available for the role "+currRole+", you can change Shift requirements\n");
                            success = false;
                            return;
                        } else {
                            int numOfEmployee = roles.get(currRole);
                            List<String> employees = new LinkedList<String>();
                            if(numOfEmployee > relevantEmployees.size()) {
                                System.out.println("\nThere are not enough employees available for the job " +currRole +",\n"+
                                        "Therefore this inlay cannot be performed\n");
                                return;
                            }
                            while (numOfEmployee > 0) {
                                System.out.println("Select the employees for the role " + currRole + " :\n" +
                                        numOfEmployee + " more left to choose for this role");
                                String Employee = null;
                                while (Employee == null) {
                                    Employee = chooseFromList(relevantEmployees);
                                }
                                relevantEmployees.remove(Employee);
                                employees.add(Employee);
                                selectedEmployees.add(Employee);

                                numOfEmployee--;
                            }
                            workers.put(currRole, employees);
                        }
                    }
                    if(success)
                        service.addShift(date, shiftType, employeeManager, workers);
                }
            }
        }
    }

    private String chooseEmployeeManager(String day, String shiftType) {
        List<String> relevant_personnel_manager = service.relevantPersonnelManager(day, shiftType);
        if (relevant_personnel_manager == null || relevant_personnel_manager.size()==0)
            return null;
        else {
            System.out.println("Select the Shift manager:");
            String manager = chooseFromList(relevant_personnel_manager);
            return manager;
        }
    }


    private void history() {
        System.out.println(service.getShiftHistory());
    }

    private void watchShift() {
        Date date = dateFromUser();
        while (date == null){
            date = dateFromUser();
        }
        String shiftType = chooseShiftType();
        while (shiftType == null){
            shiftType = chooseShiftType();
        }
        System.out.println(service.watchShift(date, shiftType));
        return;
    }

    private void watchRequirementsRole() {
        String day = chooseDay();
        while (day == null){
            day = chooseDay();
        }
        String shiftType = chooseShiftType();
        while (shiftType == null){
            shiftType = chooseShiftType();
        }
        System.out.println(service.watchRequirementsRole(day,shiftType));
        return;
    }

    private String chooseDay() {
        List<String> days = new LinkedList<>();
        days.add("Sunday");
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        System.out.println("Choose the day:");
        return chooseFromList(days);
    }

    private String chooseShiftType() {
        List<String> shiftTypes = new LinkedList<>();
        shiftTypes.add("Morning");
        shiftTypes.add("Evening");
        System.out.println("Choose the Shift type:");
        return chooseFromList(shiftTypes);
    }


    private String chooseFromList(List<String> list) {
        for (int t = 0; t < list.size(); t++) {
            int numbering = t + 1;
            System.out.println(numbering + ". " + list.get(t));
        }
        int selected = numberFromRange(1, list.size());
        String choose = list.get(selected - 1);
        return choose;
    }

    private Integer numberFromRange(int start,int end){
        while (true) {
            try {
                Scanner ans = new Scanner(System.in);
                int answer = ans.nextInt();
                if (answer >= start & answer <= end)
                    return answer;
                else
                    System.out.println("Expected to get a number in range " + start + "-" + end);
            } catch (Exception e) {
                System.out.println("Expected to get a number in range " + start + "-" + end);
            }
        }
    }

    private Date dateFromUser() {
        Date date = null;
        System.out.println("What's the date of the Shift?\n" +
                "Please enter the date in the format dd/mm/yyyy");
        Scanner d = new Scanner(System.in);
        String dd = d.nextLine();
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dd);
        } catch (ParseException e) {
            System.out.println("\nInvalid date\n");
        }
        return date;
    }


    private String dayByNumber(int day){
        switch (day) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";

        }
        return null;
    }

    private String roleFromList(){
        List<String> roles = new LinkedList<>();
        roles.add("cashier");
        roles.add("storekeeper");
        roles.add("rate");
        roles.add("baker");
        roles.add("Shop worker");
        roles.add("Guard");
        System.out.println("Choose a role:");
        return chooseFromList(roles);
    }

    private String yesOrNo(String ask){
        while (true) {
            System.out.println(ask);
            Scanner ans2 = new Scanner(System.in);
            String needMore = ans2.nextLine();
            if (needMore.equals("n")| (needMore.equals("y"))) {
                return needMore;
            } else {
                System.out.println("\nExpected to receive y / n\n");
            }
        }
    }

    private String inputNotEmpty(String in){
        while (true) {
            System.out.println(in);
            Scanner ans = new Scanner(System.in);
            String ret = ans.nextLine();
            if(!ret.equals(""))
                return ret;
            else
                System.out.println("Employee's " + in + " cannot be empty");
        }
    }

    private int inputNumber(String in){
        while (true) {
            System.out.println(in);
            Scanner ans = new Scanner(System.in);
            int ret;
            try {
                ret = ans.nextInt();
                return ret;
            }
            catch (Exception e){
                System.out.println("\nInvalid input\nPlease make sure to enter a number..\n");
            }
        }
    }

    private String choseLicence() {
        List<String> shiftTypes = new LinkedList<>();
        shiftTypes.add("C");
        shiftTypes.add("C1");
        System.out.println("Select the employee's license:");
        return chooseFromList(shiftTypes);
    }
}