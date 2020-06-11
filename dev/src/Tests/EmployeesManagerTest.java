//package src.Tests;
//
//import src.BusinessLayer.EmployeeModule.EmployeesManager;
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
//
//public class EmployeesManagerTest {
//    private EmployeesManager employeesManager;
//    private Integer employeeAdded;
//    private Connection con;
//    @Before
//    public void setEmployee(){
//        employeesManager=new EmployeesManager();
//        employeesManager.loadBranch(1);
//        Date d2 = null;
//        try {
//            d2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/04/2020");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        employeeAdded =Integer.parseInt(employeesManager.addWorker("test", "9999999", "a lot ", "555", 30000, d2,null));
//    }
//    @Test
//    public void getEmployeeDetails() {
//        employeesManager.getEmployeeDetails(employeeAdded.toString());
//        Assert.assertFalse("get Employee details doesn't work","no such emplyee".equals(employeesManager
//                .getEmployeeDetails(employeeAdded.toString())));
//    }
//
//    @Test
//    public void employeeExist() {
//        Assert.assertTrue(employeesManager.employeeExist(employeeAdded.toString()));
//        Assert.assertFalse(employeesManager.employeeExist((employeeAdded+1)+""));
//
//    }
//    @After
//    public void deleteFromEmployeesTable(){
//        try {
//            String url = "jdbc:sqlite:dev\\EOEDdatabase.db";
//            con = DriverManager.getConnection(url);
//            Class.forName("org.sqlite.JDBC");
//            con.setAutoCommit(false);
//
//            PreparedStatement mainEmployeeTable = con.prepareStatement(
//                    "Delete FROM Employees WHERE EmployeeID = ? ; ");
//
//
//
//            mainEmployeeTable.setString(1,employeeAdded.toString());
//
//            int rowNum = mainEmployeeTable.executeUpdate();
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
//
//    private void tryClose() {
//        try {
//            con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}