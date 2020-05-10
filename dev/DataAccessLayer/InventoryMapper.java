package DataAccessLayer;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class InventoryMapper {
    //private static Connection c;

/*    public static void InitialInventoryMapper(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:EOEDdatabase.db");
        } catch ( Exception e ){
            System.out.println("database Connection Problem!!!!");
        }
        System.out.println("database opened successfully");
    }*/

    //Managers


    

    public static String addInventoryManager(String username, String password){
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO InventoryManagers VALUES (?,?);");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return "Global Manager " + username + " - registered successfully";
        } catch ( Exception e ) {
                    if (c != null) {
                        try {
                            System.err.print("Transaction is being rolled back");
                            c.rollback();
                        } catch (SQLException excep) {
                        }
                    }
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return "cant register -" + username + " already exist";
        }
    }
    public static String addGlobalManager(String username, String password){
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO GlobalManagers VALUES (?,?);");
            stmt.setString(1, username);
            stmt.setString(2, password);
            int numRowInserted = stmt.executeUpdate();
            stmt.close();
            if(numRowInserted != 0) {
                c.commit();
                c.close();
                return "Global Manager " + username + " - registered successfully";
            }
            else {
                c.rollback();
                c.close();
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return "cant register -" + username + " already exist";
        }
        return "";
    }


    //Inventory
    public static String CreateNewInventory(Integer branchId) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\erez1\\IdeaProjects\\ADSS_Group_D_ass2\\dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO Inventory VALUES (?,1);");
            stmt.setString(1, String.valueOf(branchId));
            int numRowInserted = stmt.executeUpdate();
            stmt.close();
            if(numRowInserted != 0) {
                c.commit();
                c.close();
                return "branch number : " + branchId + "created successfully";
            }
            else {
                c.rollback();
                c.close();
            }
        } catch ( Exception e ) {
            return  (e.getClass().getName() + ": " + e.getMessage() );
        }
        return "";
    }
    public static String addProduct(int branchId, int id, int amount, String name, Double costPrice, Double salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place) {
        return "TODO//IMPlEMENT";
    }
    public static String getProductName(int branchId, int prodId) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\erez1\\IdeaProjects\\ADSS_Group_D_ass2\\dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
        stmt = c.prepareStatement("SELECT name FROM Products WHERE branchId=? AND productId=?;");
        stmt.setString(1, String.valueOf(branchId));
        stmt.setString(2, String.valueOf(branchId));
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
           return rs.getString("name");
        rs.close();
        stmt.close();
        //c.close();
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "Product Id :" + prodId + " Does not exist in inventory number " + branchId;
    }

    public static String removeProduct(int branchId, int id) {
        return "TODO//IMPlEMENT";
    }

    public static void addAmountToProduct(int branchId, int id, int amount) {
        //("TODO//IMPlEMENT");
    }

    public static String removeAmountFromProductShelf(int branchId, int id, int amount) {
        return "TODO//IMPlEMENT";
    }

    public static String removeAmountFromProductStorage(int branchId, int id, int amount) {
        return "TODO//IMPlEMENT";
    }

    public static String setSalePrice(int branchId, int id, Double price) {
        return "TODO//IMPlEMENT";
    }

    public static int getProductMin(int branchId, int prodId) {
        //"TODO//IMPlEMENT";
        return -1;
    }

    public static int getAmount(int branchId, Integer prodId) {
        //"TODO//IMPlEMENT";
        return -1;
    }

    public static String setCategory(int branchId, int id, List<String> category) {
        return "TODO//IMPlEMENT";
    }

    public static void updateExpired(Integer branchId, Integer prodId, Integer amount) {
        //"TODO//IMPlEMENT";
    }

    public static void addExpired(Integer branchId, Integer prodId, Integer amount) {
        //"TODO//IMPlEMENT";
    }

    public static String shelfToStorage(int branchId, int id, int amount) {
        return "TODO//IMPlEMENT";
    }

    public static String storageToShelf(int branchId, int id, int amount) {
        return "TODO//IMPlEMENT";
    }

    public static int needToBuyProducts(int branchId) {
        //"TODO//IMPlEMENT";
        return -1;
    }

    public static int getProductQuantity(int branchId, Integer id) {
        //"TODO//IMPlEMENT";
        return -1;
    }

    public static int getShelfQunatity(int branchId, Integer id) {
        //"TODO//IMPlEMENT";
        return -1;
    }

    public static int getStorageQunatity(int branchId, Integer id) {
        //"TODO//IMPlEMENT";
        return -1;
    }

    public static int getExpiredQuantity(int branchId, Integer id) {
        //"TODO//IMPlEMENT";
        return -1;
    }

    public static List<Double> getLastPrices(int branchId, int id) {
        //"TODO//IMPlEMENT";
        return null;
    }

    public static List<Double> getCostPrices(int branchId, int id) {
        //"TODO//IMPlEMENT";
        return null;
    }

    public static List<Integer> getIdsToWeeklyOrders(int dayOfTheWeek) {
        //"TODO//IMPlEMENT";
        return null;
    }


}
