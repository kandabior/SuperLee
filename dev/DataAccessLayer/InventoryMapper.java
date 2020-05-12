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
            c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\erez1\\IdeaProjects\\ADSS_Group_D_ass2\\dev\\EOEDdatabase.db");
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
            c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\erez1\\IdeaProjects\\ADSS_Group_D_ass2\\dev\\EOEDdatabase.db");
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
            c = DriverManager.getConnection("jdbc:sqlite:./EOEDdatabase.db");
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
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            //remove previous categories
            for(String cat: category) {
                String sql= "DELETE FROM Categories WHERE branchId= ? AND productID= ? AND category=?";
                stmt= c.prepareStatement(sql);
                stmt.setInt(1,branchId);
                stmt.setInt(2,id);
                stmt.setString(3,cat);
                stmt.executeUpdate();
                stmt.close();
            }
            //set new categories
            for(String cat : category) {
                stmt = c.prepareStatement("INSERT INTO Categiries VALUES (?,?,?);");
                stmt.setInt(1,branchId);
                stmt.setInt(2,id);
                stmt.setString(3,cat);
                stmt.executeUpdate();
            }

            stmt.close();

            c.commit();
            c.close();
            return "Categories updated successfully for product: "+id+" in branch: "+branchId;

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            tryClose(c);
            return "Failed to update categories for product: "+id+" in branch: "+branchId;
        }
    }

    public static boolean updateExpired(Integer branchId, Integer prodId, Integer amount) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT amount FROM Expireds WHERE branchId=? AND productId=?;");
            stmt.setString(1, String.valueOf(branchId));
            stmt.setString(2, String.valueOf(prodId));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int currentAmount= Integer.parseInt(rs.getString("amount"));
                stmt.close();
                stmt=c.prepareStatement("UPDATE Expireds SET amount=? WHERE branchId=? AND productId=?;");
                stmt.setInt(1,currentAmount);
                stmt.setInt(2,branchId);
                stmt.setInt(3,prodId);
                int numOfUpdates=stmt.executeUpdate();
                boolean output=false;
                if(numOfUpdates!=0){
                    output=true;
                }
                stmt.close();
                rs.close();
                c.close();
                return output;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            tryClose(c);
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        //"TODO//check";
    }

    public static boolean addExpired(Integer branchId, Integer prodId, Integer amount) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt=c.prepareStatement("INSERT INTO Expireds values (?,?,?)");
            stmt.setInt(1,branchId);
            stmt.setInt(2,prodId);
            stmt.setInt(3,amount);
            int numOfUpdates=stmt.executeUpdate();
            boolean output=false;
            if(numOfUpdates!=0){
                output=true;
            }
            stmt.close();
            c.close();
            return output;

        } catch ( Exception e ) {
            tryClose(c);
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        //TODO//IMPlEMENT";
    }

    public static boolean shelfToStorage(int branchId, int id, int amount) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT shelfQuantity, storageQuantity FROM Quantities WHERE branchId=? AND productId=?;");
            stmt.setString(1, String.valueOf(branchId));
            stmt.setString(2, String.valueOf(id));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int shelfQuantity = Integer.parseInt(rs.getString("shelfQuantity"));
                int storageQuantity = Integer.parseInt(rs.getString("storageQuantity"));
                stmt.close();
                if (shelfQuantity >= amount) {
                    stmt = c.prepareStatement("UPDATE Quantities SET shelfQuantity=?, storageQuantity=? WHERE branchId=? AND productId=?;");
                    stmt.setInt(1, shelfQuantity-amount);
                    stmt.setInt(2, storageQuantity+amount);
                    stmt.setInt(3, branchId);
                    stmt.setInt(4, id);
                    int numOfUpdates = stmt.executeUpdate();
                    boolean output = false;
                    if (numOfUpdates != 0) {
                        output = true;
                    }
                    stmt.close();
                    rs.close();
                    c.close();
                    return output;
                } else {
                    rs.close();
                    stmt.close();
                    c.close();
                    return false;
                }
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            tryClose(c);
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        //TODO: check
    }

    public static boolean storageToShelf(int branchId, int id, int amount) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT shelfQuantity, storageQuantity FROM Quantities WHERE branchId=? AND productId=?;");
            stmt.setString(1, String.valueOf(branchId));
            stmt.setString(2, String.valueOf(id));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int shelfQuantity = Integer.parseInt(rs.getString("shelfQuantity"));
                int storageQuantity = Integer.parseInt(rs.getString("storageQuantity"));
                stmt.close();
                if (storageQuantity >= amount) {
                    stmt = c.prepareStatement("UPDATE Quantities SET shelfQuantity=?, storageQuantity=? WHERE branchId=? AND productId=?;");
                    stmt.setInt(1, shelfQuantity+amount);
                    stmt.setInt(2, storageQuantity-amount);
                    stmt.setInt(3, branchId);
                    stmt.setInt(4, id);
                    int numOfUpdates = stmt.executeUpdate();
                    boolean output = false;
                    if (numOfUpdates != 0) {
                        output = true;
                    }
                    stmt.close();
                    rs.close();
                    c.close();
                    return output;
                } else {
                    rs.close();
                    stmt.close();
                    c.close();
                    return false;
                }
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            tryClose(c);
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        //TODO: check
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

    private static void tryClose(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
