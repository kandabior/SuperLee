package DataAccessLayer.Employee;

import BusinessLayer.EmployeeModule.Employee;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EmployeesMapper {
    private Connection con;

    public Employee getByEID(int employeeID) {
        throw new NotImplementedException();
    }

    public List<EmployeeDTO> getAllEmployeesFromBranch(int branch) {
        List<EmployeeDTO> employeeDTOS=new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Employees WHERE branch = ?;");
                statement.setInt(1, branch);
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                  employeeDTOS.add(getEmployeeFromResultWOConstrainsAndRoles(res));
                }
            }
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        addRoles(employeeDTOS);
        addConstrains(employeeDTOS);
        return employeeDTOS;
    }

    private void addConstrains(List<EmployeeDTO> employeeDTOS) {
        for(EmployeeDTO employeeDTO: employeeDTOS)
        {

            try {
                if (tryOpen()) {
                    Class.forName("org.sqlite.JDBC");
                    con.setAutoCommit(false);
                    PreparedStatement statement = con.prepareStatement("SELECT * FROM EmployeeConstrains WHERE branch = EID;");
                    statement.setInt(1, Integer.parseInt(employeeDTO.getEmployeeId()));
                    ResultSet res = statement.executeQuery();
                    while (res.next()) {
                        employeeDTO.addConstrain(res.getString("Day"),res.getString("typeOfShift") );
                    }
                }
            } catch (Exception e) {
                tryClose();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }


        }
    }

    private void addRoles(List<EmployeeDTO> employeeDTOS) {

        for(EmployeeDTO employeeDTO: employeeDTOS)
        {

            try {
                if (tryOpen()) {
                    Class.forName("org.sqlite.JDBC");
                    con.setAutoCommit(false);
                    PreparedStatement statement = con.prepareStatement("SELECT * FROM EmployeeRoles WHERE branch = EID;");
                    statement.setInt(1, Integer.parseInt(employeeDTO.getEmployeeId()));
                    ResultSet res = statement.executeQuery();
                    List<String> roles=new LinkedList<>();
                    while (res.next()) {
                        roles.add(res.getString("Role"));
                    }
                    employeeDTO.setRoles(roles);
                }
            } catch (Exception e) {
                tryClose();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }


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
            Date startOfEmployment=res.getDate("StartOfEmployment");
            int branch=res.getInt("branch");
            String license=res.getString("license");
            return new EmployeeDTO(isAvailable,name,ID,isSupervisor,hiringConditions,bankID,salary,startOfEmployment,EID.toString(),branch,license);

    }

    public EmployeeDTO getByID(String id) {
        throw new NotImplementedException();
    }

    public EmployeeDTO addEmployee(String name, String id, String hiringConditions, String bankId, int salary,
                                   Date startOfEmployment, int currentBranch, String license){


        throw new NotImplementedException();

    }

    public void updateEmployee(EmployeeDTO e) {
    }




    private boolean tryOpen() {
        try {
            String url = "jdbc:sqlite:transportsAndWorkers.db";
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

}
