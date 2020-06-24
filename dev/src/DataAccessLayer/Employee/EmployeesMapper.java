package src.DataAccessLayer.Employee;

import javafx.util.Pair;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EmployeesMapper {
    private Connection con;

    public EmployeeDTO getByEID(int employeeID){


        EmployeeDTO employeeDTO=null;
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Employees WHERE employeeID = ?;");
                statement.setInt(1,employeeID);
                ResultSet res = statement.executeQuery();
                if (res.next()) {
                    employeeDTO=getEmployeeFromResultWOConstrainsAndRoles(res);
                    con.close();
                    addConstrainForSingle(employeeDTO);
                    addRolesForSingle(employeeDTO);
                }
                else{
                    con.close();
                }
            }
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return employeeDTO;
    }

    public List<EmployeeDTO> getAllEmployeesFromBranch(int branch) {

        List<EmployeeDTO> employeeDTOS=new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Employees WHERE branch =?;");
                statement.setInt(1, branch);
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                  employeeDTOS.add(getEmployeeFromResultWOConstrainsAndRoles(res));
                }
                con.close();

            }
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        for(EmployeeDTO employeeDTO: employeeDTOS)
        {
            addConstrainForSingle(employeeDTO);
            addRolesForSingle(employeeDTO);
        }

        return employeeDTOS;
    }

    public void updateEmployee(EmployeeDTO employeeDTO) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);

                PreparedStatement statement = con.prepareStatement(
"UPDATE Employees SET ID = ? , name = ? , isAvailable = ? , isSupervisor = ? ," +
        " HiringConditions = ? ,  bankID = ? ,     salary  = ? , StartOfEmployment = ? , branch = ? , License = ?" +
        "WHERE EmployeeID = ?  ;  ");



                statement.setString(1,employeeDTO.getID());
                statement.setString(2,employeeDTO.getName());
                statement.setInt(3,employeeDTO.isAvailable()?  1: 0);
                statement.setInt(4,employeeDTO.isSupervisor()? 1:0);
                statement.setString(5,employeeDTO.getHiringConditions());
                statement.setString(6,employeeDTO.getBankId());
                statement.setInt(7,employeeDTO.getSalary());
                statement.setString(8,unParseDate(employeeDTO.getStartOfEmployment()));
                statement.setInt(9,employeeDTO.getBranch());
                statement.setString(10,employeeDTO.getLicense());
                statement.setString(11,employeeDTO.getEmployeeId());

                int rowNum = statement.executeUpdate();
                deleteOldConstrainsAddNew(employeeDTO);
                deleteOldRolesAddNew(employeeDTO);

                if (rowNum != 0) {
                    con.commit();
                    con.close();
                } else {
                    con.rollback();
                    con.close();
                }
            }
        }
        catch (Exception e){
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public EmployeeDTO getByID(String ID){
        EmployeeDTO employeeDTO=null;
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Employees WHERE ID = ?;");
                statement.setString(1,ID);
                ResultSet res = statement.executeQuery();
                if (res.next()) {
                    employeeDTO=getEmployeeFromResultWOConstrainsAndRoles(res);
                    con.close();
                    addConstrainForSingle(employeeDTO);
                    addRolesForSingle(employeeDTO);
                }
                else{
                    con.close();
                }
            }
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return employeeDTO;
    }

    public EmployeeDTO addEmployee(String name, String id, String hiringConditions, String bankId, int salary,
                                   Date startOfEmployment, int branch, String license){
        int employeeID=getNumOfEmployee()+1;
        String dateOfStart=unParseDate(startOfEmployment);
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement
                        ("INSERT INTO Employees (ID,name,isAvailable,EmployeeID,isSupervisor,HiringConditions,bankID,salary,StartOfEmployment,branch,license)" +
                        " VALUES (?,?,?,?,?,?,?,?,?,?,?);");

                statement.setString(1, id);
                statement.setString(2,name );
                statement.setInt(3, 1);
                statement.setInt(4, employeeID);
                statement.setInt(5, 0);
                statement.setString(6, hiringConditions);
                statement.setString(7, bankId);
                statement.setInt(8, salary);
                statement.setString(9, dateOfStart);
                statement.setInt(10, branch);
                statement.setString(11, license);

                int rowNum = statement.executeUpdate();
                if (rowNum != 0) {
                    con.commit();
                    con.close();
                } else {
                    con.rollback();
                    con.close();
                }
                statement.close();

            }
            return getByEID(employeeID);

        }
        catch (Exception e){
            tryClose();
            e.printStackTrace();
            return null;
        }

    }

    public List<Pair<Integer, String>> getEmployeesMessages() {
        List<Pair<Integer, String>> messages=new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM MessagesToEM");

                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    messages.add(new Pair<>(res.getInt("ID"),res.getString("message")));
                }
                con.close();

            }


        }
        catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }



                return messages;
    }

    public void deleteEmployeeMessage(int id) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement deleteStatement = con.prepareStatement("Delete FROM MessagesToEM WHERE ID = ? ;");
                deleteStatement.setInt(1, id);
                int rowNum=deleteStatement.executeUpdate();
                if(rowNum!=0){
                     con.commit();
                     con.close();
                 }
                 else
                     con.close();
            }
        }
        catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }


    }

    public List<Integer> getOrdersMessages(){
        List<Integer> messages=new LinkedList<>();

        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM CancellationRequests where answerEmployee=0");

                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    messages.add(res.getInt("orderId"));
                }
                con.close();
            }
        }
        catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return messages;
    }

    public void respondOrderMessage(int id, int respond) {

        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);

                PreparedStatement statement = con.prepareStatement(
                        "UPDATE CancellationRequests SET answerEmployee = ? WHERE orderId = ? ;  ");
                statement.setInt(1,respond);
                statement.setInt(2,id);
                int rowNum = statement.executeUpdate();
                if (rowNum != 0) {
                    con.commit();
                    con.close();
                } else {
                    con.rollback();
                    con.close();
                }
            }
        }
        catch (Exception e){
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }


    }

    private boolean tryOpen() {
        try {
            String url = "jdbc:sqlite:dev\\EOEDdatabase.db";
            con = DriverManager.getConnection(url);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void tryClose() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private EmployeeDTO getEmployeeFromResultWOConstrainsAndRoles(ResultSet res) throws Exception {
        String ID=res.getString("ID");
        String name=res.getString("name");
        boolean isAvailable=res.getInt("isAvailable")==1;
        Integer EID=res.getInt("EmployeeID");
        boolean isSupervisor=res.getInt("isSupervisor")==1;
        String hiringConditions=res.getString("HiringConditions");
        String bankID=res.getString("bankID");
        int salary=res.getInt("salary");
        Date startOfEmployment= parseDate(res.getString("StartOfEmployment"));
        int branch=res.getInt("branch");
        String license=res.getString("License");

        return new EmployeeDTO(isAvailable,name,ID,isSupervisor,hiringConditions,bankID,salary,startOfEmployment
                ,EID.toString(),branch,license);

    }
    private void deleteOldRolesAddNew(EmployeeDTO employeeDTO) throws Exception {
        PreparedStatement deleteStatement = con.prepareStatement("Delete FROM EmployeeRoles WHERE EID = ? ;");
        deleteStatement.setInt(1,Integer.parseInt(employeeDTO.getEmployeeId()));
        deleteStatement.execute();

        List<String> newRoles=employeeDTO.getRoles();
        if(newRoles!=null) {
            for (String role : newRoles) {
                PreparedStatement insertStatement = con.prepareStatement("INSERT INTO EmployeeRoles (EID,Role) VALUES (?,?) ;");
                insertStatement.setInt(1, Integer.parseInt(employeeDTO.getEmployeeId()));
                insertStatement.setString(2, role);
                insertStatement.execute();
            }
        }
    }
    private void deleteOldConstrainsAddNew(EmployeeDTO employeeDTO) throws Exception {


        PreparedStatement deleteStatement = con.prepareStatement("Delete FROM EmployeeConstrains WHERE EID = ? ;");
        deleteStatement.setInt(1,Integer.parseInt(employeeDTO.getEmployeeId()));
        deleteStatement.execute();

        List<String> newConstrains=employeeDTO.getConstrains();
        if(newConstrains!=null) {
            for (String constrain : newConstrains) {
                PreparedStatement insertStatement = con.prepareStatement("INSERT INTO EmployeeConstrains (EID,Day,typeOfShift) VALUES (?,?,?) ;");
                insertStatement.setInt(1, Integer.parseInt(employeeDTO.getEmployeeId()));
                insertStatement.setString(2, constrain.substring(0,constrain.indexOf(":")));
                insertStatement.setString(3,constrain.substring(constrain.indexOf(":")+1));
                insertStatement.execute();
            }
        }
    }
    private Date parseDate(String startOfEmployment) {
        String woDay=startOfEmployment.substring(startOfEmployment.indexOf("/")+1);

        int day=Integer.parseInt(startOfEmployment.substring(0,startOfEmployment.indexOf("/")));
        int month=Integer.parseInt(woDay.substring(0,startOfEmployment.indexOf("/")));
        int year=Integer.parseInt(woDay.substring(startOfEmployment.indexOf("/")+1));
        return new Date(year,month,day);
    }
    private void addConstrainForSingle(EmployeeDTO employeeDTO){

        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM EmployeeConstrains WHERE EID = ?;");
                statement.setInt(1, Integer.parseInt(employeeDTO.getEmployeeId()));
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    employeeDTO.addConstrain(res.getString("Day"),res.getString("typeOfShift") );
                }
                con.close();

            }
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
    private void addRolesForSingle(EmployeeDTO employeeDTO){
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM EmployeeRoles WHERE EID = ? ;");
                statement.setInt(1, Integer.parseInt(employeeDTO.getEmployeeId()));
                ResultSet res = statement.executeQuery();
                List<String> roles=new LinkedList<>();
                while (res.next()) {
                    roles.add(res.getString("Role"));
                }
                employeeDTO.setRoles(roles);
                con.close();
            }
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
    private int getNumOfEmployee() {
        int output=0;
        EmployeeDTO employeeDTO=null;
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT COUNT(ID) FROM Employees");
                ResultSet res = statement.executeQuery();
                if (res.next()) {
                    output=res.getInt(1);
                }
            }
            con.close();

        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
        }
        return output;
    }
    private String unParseDate(Date startOfEmployment) {
        return new SimpleDateFormat("dd/MM/yyyy").format(startOfEmployment);
    }


}
