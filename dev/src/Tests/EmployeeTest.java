//package Tests;
//
//import BusinessLayer.EmployeeModule.Employee;
//import BusinessLayer.EmployeeModule.Service;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
//public class EmployeeTest {
//    private Employee employee;
//    private Service service;
//    private String RavivEID;
//    private String HodayaEID;
//    private Connection con;
//    @Before
//    public void singleEmployee() {
//        Date d2 = null;
//        try {
//            d2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/04/2020");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        employee = new Employee("Raviv", "315", "a lot ", "555", 30000, d2, "1",2);
//    }
//    @Before
//    public void setService() {
//        service = Service.getInstance();
//        Date d2 = null;
//        try {
//            d2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/04/2020");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        try {
//            service.loadBranch(1);
//            RavivEID=service.addWorker("Raviv", "99997", "a lot ", "555", 30000, d2,null);
//            HodayaEID=service.addWorker("Hodaya", "99998", "a lot ", "555", 30000, d2,null);
//            service.setSupervisor(HodayaEID, true);
//            service.addRole(RavivEID, "Chef");
//            service.addRole(HodayaEID, "Chef");
//        }
//        catch (Exception e){
//
//        }
//
//        service.addConstrain(HodayaEID,"Sunday","Morning");
//    }
//    @Test
//    public void roleExists() {
//        Assert.assertFalse(employee.checkRoleExist("Chef"));
//
//        employee.addRole("Chef");
//        Assert.assertTrue("Get role doesn't work after add",employee.checkRoleExist("Chef"));
//
//        employee.deleteRole("Chef");
//        Assert.assertFalse("Get role doesn't work after delete",employee.checkRoleExist("Chef"));
//
//    }
//
//    @Test
//    public void checkHasConstrain() {
//
//
//        List<String> employees= service.relevantEmployees("Sunday","Morning","Chef");
//        Assert.assertTrue("Not good constrains",employees.indexOf("Hodaya;"+HodayaEID)==-1);
//        Assert.assertFalse("Not good constrains",employees.indexOf("Raviv;"+ RavivEID)==-1);
//
//    }
//
//    @Test
//    public void getManagers() {
//        List<String> managers=service.relevantPersonnelManager("Tuesday","Evening");
//        Assert.assertTrue("Not good managers fetcher",managers.indexOf("Hodaya;"+HodayaEID)!=-1);
//    }
//    @Test
//    public void getManagers2() {
//        List<String> managers=service.relevantPersonnelManager("Sunday","Evening");
//        Assert.assertTrue("Not good managers fetcher",managers.indexOf("Raviv;1")==-1);
//    }
//    @Test
//    public void aviliable() {
//        service.deleteWorker("2");
//        List<String> managers=service.relevantPersonnelManager("Sunday","Evening");
//        Assert.assertFalse("Not good managers fetcher",managers.indexOf("Hodaya;2")!=-1);
//    }
//    @After
//    public void deleteFromEmployeesTable(){
//        try {
//            String url = "jdbc:sqlite:dev\\EOEDdatabase.db";
//            con = DriverManager.getConnection(url);
//                Class.forName("org.sqlite.JDBC");
//                con.setAutoCommit(false);
//
//                PreparedStatement mainEmployeeTable = con.prepareStatement(
//                        "Delete FROM Employees WHERE EmployeeID = ? or  EmployeeID = ?;  ");
//
//
//
//                mainEmployeeTable.setString(1,HodayaEID);
//                mainEmployeeTable.setString(2,RavivEID);
//
//                int rowNum = mainEmployeeTable.executeUpdate();
//
//
//                if (rowNum != 0) {
//                    con.commit();
//                    con.close();
//                } else {
//                    con.rollback();
//                    con.close();
//                }
//            }
//
//        catch (Exception e){
//          tryClose();
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//        }
//
//    }
//    @After
//    public void deleteFromEmployeeConstrainsTable(){
//        try {
//            String url = "jdbc:sqlite:dev\\EOEDdatabase.db";
//            con = DriverManager.getConnection(url);
//            Class.forName("org.sqlite.JDBC");
//            con.setAutoCommit(false);
//
//            PreparedStatement constrainsDelete = con.prepareStatement(
//                    "Delete FROM EmployeeConstrains WHERE EID = ? or  EID = ?;  ");
//
//
//
//            constrainsDelete.setString(1,HodayaEID);
//            constrainsDelete.setString(2,RavivEID);
//
//            int rowNum = constrainsDelete.executeUpdate();
//
//
//            if (rowNum != 0) {
//                con.commit();
//            } else {
//                con.rollback();
//            }
//
//            PreparedStatement rolesDelete = con.prepareStatement(
//                    "Delete FROM EmployeeRoles WHERE EID = ? or  EID = ?;  ");
//
//
//
//            rolesDelete.setString(1,HodayaEID);
//            rolesDelete.setString(2,RavivEID);
//
//            rowNum = rolesDelete.executeUpdate();
//
//
//            if (rowNum != 0) {
//                con.commit();
//                con.close();
//            } else {
//                con.rollback();
//                con.close();
//            }
//        }
//
//        catch (Exception e){
//            tryClose();
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//        }
//
//    }
//    private void tryClose() {
//        try {
//            con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//}