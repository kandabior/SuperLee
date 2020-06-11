//package Tests;
//
//import BusinessLayer.EmployeeModule.Day;
//import BusinessLayer.EmployeeModule.ShiftType;
//import BusinessLayer.EmployeeModule.ShiftManager;
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
//import java.util.HashMap;
//
//public class ShiftManagerTest {
//    private ShiftManager shiftManager;
//    private Connection con;
//    private int testBranch=999;
//    @Before
//    public void setShiftManager(){
//        shiftManager=new ShiftManager();
//        HashMap<String, Integer> roles = new HashMap();
//        roles.put("chef", 5);
//        shiftManager.loadBranch(testBranch); //test branch
//        shiftManager.editRequirements(Day.Friday, ShiftType.Morning, roles);
//        shiftManager.editRequirements(Day.Monday, ShiftType.Evening, roles);
//
//    }
//
//    @Test
//    public void DoesExistRequirements() {
//        shiftManager.existRequirements(Day.Friday, ShiftType.Morning);
//        Assert.assertEquals(1, shiftManager.getRequirements().size());
//    }
//
//    @Test
//    public void DoesNotExistRequirements() {
//        shiftManager.existRequirements(Day.Monday, ShiftType.Morning);
//        Assert.assertEquals(2, shiftManager.getRequirements().size());
//    }
//    @Test
//    public void shiftNotExist() {
//        Date d2 = null;
//        try {
//            d2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/04/2020");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Assert.assertFalse(shiftManager.ShiftExists(d2, ShiftType.Morning));
//    }
//
//
//    @After
//    public void deleteAdded(){
//        try {
//            String url = "jdbc:sqlite:dev\\EOEDdatabase.db";
//            con = DriverManager.getConnection(url);
//            Class.forName("org.sqlite.JDBC");
//            con.setAutoCommit(false);
//
//            PreparedStatement mainEmployeeTable = con.prepareStatement(
//                    "Delete FROM Requirments WHERE branch = ? ; ");
//
//
//
//            mainEmployeeTable.setInt(1,testBranch);
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
//}
