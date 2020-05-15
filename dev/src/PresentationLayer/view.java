package PresentationLayer;


import BusinessLayer.EmployeeModule.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;

public class view {

    BusinessLayer.EmployeeModule.service service = new service();

    public static void main() {
        view view = new view();
        view.startSystem();
    }


    private void startSystem(){
        try {
            createDB();//TODO remove
            while (true) {
                System.out.println("Welcome to the \"Super-Lee\" system!\n" +
                        "Select a branch:\n" +
                        "for drivers area, enter:\n     0 \n" +
                        "Otherwise enter a store number, a number between\n     1-9\n" +
                        "Return to previous menu Insert\n     10\n");
                Scanner ans = new Scanner(System.in);
                int branch = ans.nextInt();
                if (branch >= 0 & branch <= 9) {
                    service.loadBranch(branch);
                    break;
                } else if (branch == 10)
                    return;
                else
                    System.out.println("\nInvalid selection\n");
            }
            startPersonnelManager();
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
                Scanner ans = new Scanner(System.in);
                int selectedClass = ans.nextInt();
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
            int selectedAction = Integer.parseInt(ans.nextLine());
            switch (selectedAction) {
                case 1:
                    System.out.println(service.getAllEmplyees());
                    break;
                case 2:
                    System.out.println("enter employee ID:");
                    String ID=ans.nextLine();
                    if(!service.employeeExist(ID)&& service.employeeAvailable(ID))
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

    private void addNewWorker() {
        Scanner ans = new Scanner(System.in);
        System.out.println("Write the following worker's details:");
        String name = inputNotEmpty("name");
        String ID = inputNotEmpty("ID");
        String hiringConditions =inputNotEmpty("hiring conditions:");
        String bankId = inputNotEmpty("bank Id");
        int salary = Integer.parseInt(inputNotEmpty("salary"));
        String date;// = ans.nextLine();
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
        String emplyeeId =service.addWorker(name,ID,hiringConditions,bankId,salary,startEmployment);
        if(emplyeeId==null)
        {
            System.out.println("ID is in use! aborted...");
        }
        else
            System.out.println("Success! new worker ID: "+emplyeeId);
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
            Scanner ans = new Scanner(System.in);
            int selectedAction = ans.nextInt();
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
                    System.out.println("enter new role:");
                    service.addRole(ID,ans.nextLine());
                    break;
                case 5:
                    System.out.println("enter role to delete:");
                    service.deletRole(ID,ans.nextLine());
                    break;
                case 6:
                    service.deleteWorker(ID);
                    System.out.println("Employee deleted");
                    contin=false;
                    break;
                case 7:
                    System.out.println("enter new salary:");
                    service.changeSalary(ID,ans.nextInt());
                    break;
                case 8:
                    System.out.println("enter new name:");
                    service.changeName(ID,ans.nextLine());
                    break;
                case 9:
                    service.setSupervisor(ID,true);
                    break;
                case 10:
                    service.setSupervisor(ID,false);
                    break;
                case 11:
                    System.out.println("enter new bank details:");
                    service.setBankId(ID,ans.nextLine());
                    break;
                case 12:
                    System.out.println("enter new hiring conditions");
                    service.setHiringConditions(ID,ans.nextLine());
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
                    "1. Creating/Change role requirements for shift (by day and type of shift)\n" +
                    "2. Creating a new shift and role inlay\n" +
                    "3. View role requirements for shift by day and shift type\n" +
                    "4. Watch inlay for shift by date and type of shift\n" +
                    "5. View all shift history\n" +
                    "6. Back");
            Scanner ans = new Scanner(System.in);
            int selectedAction = ans.nextInt();
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
        String shiftType = chooseShiftType();
        while (shiftType == null){
            shiftType = chooseShiftType();
        }
        Map<String,Integer> roles = new HashMap<String, Integer>();
        boolean stop = false;
        while (!stop){
            String role = roleFromList();
            while (role == null){
                role = roleFromList();
            }
            System.out.println("How Many Employees in the role "+role+" needed?");
            Scanner ans1 = new Scanner(System.in);
            int numOfEmployee = ans1.nextInt();
            roles.put(role,numOfEmployee);
            String ask = "Need more roles? Select y/n";
            String needMore = yesOrNo(ask);
            if(needMore.equals("n"))
                stop = true;
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

        String shiftType = chooseShiftType();
        while (shiftType == null){
            shiftType = chooseShiftType();
        }

        boolean Exists = service.ShiftExists(date, shiftType);
        if (Exists) {
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
                System.out.println("There is no manager available for a shift, a shift cannot exist without a manager.");
                return;
            }
            else {
                Map<String, List<String>> workers = new HashMap<String, List<String>>();

                Map<String, Integer> roles = service.requiredRoles(day, shiftType);
                if (roles == null) {
                    System.out.println("There are no role requirements for this shift, The shift is kept with a manager only");
                    service.addShift(date, shiftType, employeeManager, workers);
                } else {
                    boolean success = true;
                    for (String currRole : roles.keySet()) {
                        List<String> relevantEmployees = service.relevantEmployees(day, shiftType, currRole);
                        if (relevantEmployees == null || relevantEmployees.size() == 0) {
                            System.out.println("\nThere is no employee available for the role "+currRole+", you can change shift requirements\n");
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
                                String Employee = chooseFromList(relevantEmployees);
                                relevantEmployees.remove(Employee);
                                employees.add(Employee);

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
        if (relevant_personnel_manager == null)
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
        System.out.println("Choose the day:\n"+
                "1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday");
        Scanner ans = new Scanner(System.in);
        int selectedDay = ans.nextInt();
        switch (selectedDay) {
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
            default:
                System.out.println("\nInvalid selection\n");
                return null;
        }
    }

    private String chooseShiftType() {
        System.out.println("Choose the shift type:\n"+
                "1.Morning\n2.Evening");
        Scanner ans = new Scanner(System.in);
        int selectedShiftType = ans.nextInt();
        switch (selectedShiftType){
            case 1:
                return "Morning";
            case 2:
                return "Evening";
            default:
                System.out.println("\nInvalid selection\n");
                return null;
        }
    }


    private String chooseFromList(List<String> list){
        for (int t = 0; t < list.size(); t++) {
            int numbering = t + 1;
            System.out.println(numbering + ". " + list.get(t));
        }
        Scanner ans = new Scanner(System.in);
        int selected = ans.nextInt();
        String choose = list.get(selected - 1);
        return choose;
    }

    private Date dateFromUser() {
        Date date = null;
        System.out.println("What's the date of the shift?\n" +
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
        System.out.println("Choose a role:\n"+
                "1. cashier\n" +
                "2. storekeeper\n" +
                "3. rate\n" +
                "4. baker\n" +
                "5. Shop worker\n" +
                "6. Guard");
        Scanner ans = new Scanner(System.in);
        int r = ans.nextInt();
        switch (r) {
            case 1:
                return "cashier";
            case 2:
                return "storekeeper";
            case 3:
                return "rate";
            case 4:
                return "baker";
            case 5:
                return "Shop worker";
            case 6:
                return "Guard";
            default:
                System.out.println("\nInvalid selection\n");
                return null;
        }
    }

    private String yesOrNo(String ask){
        while (true) {
            System.out.println(ask);
            Scanner ans2 = new Scanner(System.in);
            String needMore = ans2.nextLine();
            if (needMore.equals("n")) {
                return "n";
            } else if (needMore.equals("y")) {
                return "y";
            } else {
                System.out.println("\nExpected to receive y / n\n");
            }
        }
    }

    private String inputNotEmpty(String in){
        while (true) {
            System.out.println(in);
            if(in.equals("salary"))
                System.out.println("(Please make sure to enter a number..)");
            Scanner ans = new Scanner(System.in);
            String ret = ans.nextLine();
            if(!ret.equals(""))
                return ret;
            else
                System.out.println("Employee's " + in + " cannot be empty");
        }
    }



    //TODO remove , just for check
    private void createDB() {
        addHistory();
        addRequirements();
        createEmloyees();
    }

    private void createEmloyees() {
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2020");
            d2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/04/2020");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        service.addWorker("Raviv","315","a lot ","555",30000,d1);
        service.addWorker("Hodaya","257","a few ","777",30000,d2);
        service.setSupervisor("1",true);
        service.setSupervisor("2",true);

        service.addRole("2","cashier");
        service.addRole("2","storekeeper");

    }

    private void addHistory() {
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2020");
            d2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/04/2020");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String m = "Morning";
        String e = "Evening";

        String empMan1 = "m1";
        String empMan2 = "m2";

        Map<String,List<String>> wo1 = new HashMap<String, List<String>>();
        List<String> names1 = new LinkedList<String>();
        names1.add("yossi");
        List<String> names2 = new LinkedList<String>();
        names2.add("Moshe");
        names2.add("Dani");
        wo1.put("cashier",names1);
        wo1.put("storekeeper",names2);

        Map<String,List<String>> wo2 = new HashMap<String, List<String>>();
        List<String> names3 = new LinkedList<String>();
        names3.add("Ran");
        List<String> names4 = new LinkedList<String>();
        names4.add("Dani");
        wo2.put("cashier",names3);
        wo2.put("storekeeper",names4);

        service.addShift(d1,m,empMan1,wo1);
        service.addShift(d2,e,empMan2,wo2);
    }

    private void addRequirements() {
        String sun = "Sunday";
        String mon = "Monday";
        String thu = "Thursday";
        String wen = "Wednesday";
        String tue = "Tuesday";
        String fri = "Friday";
        String sat = "Saturday";

        String m = "Morning";
        String e = "Evening";

        Map<String, Integer> r1 = new HashMap<String, Integer>();
        r1.put("cashier",1);
        Map<String, Integer> r2 = new HashMap<String, Integer>();
        r2.put("cashier",1);
        r2.put("storekeeper",2);
        Map<String, Integer> r3 = new HashMap<String, Integer>();
        r3.put("cashier",1);
        r3.put("storekeeper",1);
        Map<String, Integer> r4 = new HashMap<String, Integer>();
        r4.put("storekeeper",1);
        Map<String, Integer> r5 = new HashMap<String, Integer>();
        r5.put("cashier",1);
        r5.put("storekeeper",3);
        Map<String, Integer> r6 = new HashMap<String, Integer>();
        r6.put("cashier",2);
        Map<String, Integer> r7 = new HashMap<String, Integer>();
        r7.put("cashier",1);
        r7.put("storekeeper",4);

        service.editRequirements(sun,m,r1);
        service.editRequirements(mon,m,r2);
        service.editRequirements(thu,e,r3);
        service.editRequirements(wen,m,r4);
        service.editRequirements(tue,e,r5);
        service.editRequirements(fri,m,r6);
        service.editRequirements(sat,m,r7);
    }
}