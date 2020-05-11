package DataAccessLayer;

import javafx.util.Pair;

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

    public static String addInventoryManager(String username, String password) {
        PreparedStatement stmt = null;
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO InventoryManagers VALUES (?,?);");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return "Inventory Manager " + username + " - registered successfully";
        } catch (Exception e) {
            if (c != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    c.rollback();
                } catch (SQLException excep) {
                }
            }
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return "cant register - " + username + " already exist";
        }
    }
    public static String addGlobalManager(String username, String password){
        PreparedStatement stmt = null;
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO GlobalManagers VALUES (?,?);");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return "Global Manager " + username + " - registered successfully";
        } catch (Exception e) {
            if (c != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    c.rollback();
                } catch (SQLException excep) {
                }
            }
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return "cant register - " + username + " already exist";
        }
    }


    //Inventory
    public static boolean CreateNewInventory(Integer branchId) {
        PreparedStatement stmt = null;
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO Inventory VALUES (?,1);");
            stmt.setString(1, String.valueOf(branchId));
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return true;
        } catch (Exception e) {
            if (c != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    c.rollback();
                } catch (SQLException excep) {
                }
                System.out.println(e.getClass().getName() + ": " + e.getMessage());
            }
            return false;
        }
    }
    public static boolean addProduct(int branchId, int id, String name, Double costPrice, Double salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO Products VALUES (?,?,?,?,?,?,?,?,?);");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            stmt.setString(3, name);
            stmt.setDouble(4, costPrice);
            stmt.setDouble(5, salePrice);
            stmt.setString(6, expDate.toString());
            stmt.setString(7, manufacturer);
            stmt.setInt(8, minAmount);
            stmt.setString(9, place);
            stmt.executeUpdate();
            for(String cat : category) {
                stmt = c.prepareStatement("INSERT INTO Categories VALUES (?,?,?);");
                stmt.setInt(1, branchId);
                stmt.setInt(2, id);
                stmt.setString(3, cat);
                stmt.executeUpdate();
            }
            stmt.close();
            c.commit();
            return true;
        } catch ( Exception e ) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }
    public static String getProductName(int branchId, int prodId) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
        stmt = c.prepareStatement("SELECT name FROM Products WHERE branchId=? AND productId=?;");
        stmt.setInt(1, branchId);
        stmt.setInt(2, prodId);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
           return rs.getString("name");
        rs.close();
        stmt.close();
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "Product Id :" + prodId + " Does not exist in inventory number " + branchId;
    }

    public static String removeProduct(int branchId, int id) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("DELETE FROM Products WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt = c.prepareStatement("DELETE FROM Quantities WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt = c.prepareStatement("DELETE FROM Expired WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return "Product removed successfully";
        } catch ( Exception e ) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return "cant removed this product";
    }

    public static boolean addNewAmountProductToQuantities(int branchId, int id, int amount) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO Quantities VALUES (?,?,?,?);");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            stmt.setInt(3, amount/2);
            stmt.setInt(4, amount/2);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return true;
        } catch ( Exception e ) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    public static boolean addAmountToProduct(int branchId, int id, int amount) { //adding half to storage half to shelf
        PreparedStatement stmt = null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("UPDATE Quantities SET storageQuantity=? AND shelfQuantity=? WHERE branchId=? AND productId=?;");
            stmt.setInt(1, amount/2);
            stmt.setInt(2, amount/2);
            stmt.setInt(3, branchId);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return true;
        } catch ( Exception e ) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    public static String removeAmountFromProductShelf(int branchId, int id, int amount) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("UPDATE Quantities SET shelfQuantity = shelfQuantity-? WHERE branchId=? AND productId=? ;");
            stmt.setInt(1, amount);
            stmt.setInt(2, branchId);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return "Amount of " + amount + " removed successfully";
        } catch ( Exception e ) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return "cant removed this amount of product";
    }

    public static String removeAmountFromProductStorage(int branchId, int id, int amount) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("UPDATE Quantities SET storageQuantity = storageQuantity-? WHERE branchId=? AND productId=? ;");
            stmt.setInt(1, amount);
            stmt.setInt(2, branchId);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return "Amount of " + amount + " removed successfully";
        } catch ( Exception e ) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return "cant removed this amount of product";
    }

    public static String setSalePrice(int branchId, int id, Double price) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("UPDATE Products SET salePrice = ? WHERE branchId=? AND productId=?;");
            stmt.setDouble(1, price);
            stmt.setInt(2, branchId);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            stmt = c.prepareStatement("INSERT INTO LastSalePrices VALUES (?,?,?);");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            stmt.setDouble(3, price);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return "product " + id + "- price changed to: " + price;
        } catch ( Exception e ) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return "cant change this product sale price";
    }

    public static int getProductMin(int branchId, int prodId) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT minAmount FROM Products WHERE branchId=? AND productId=?;");
            stmt.setInt(1,branchId);
            stmt.setInt(2, prodId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int ans = Integer.parseInt(rs.getString("minAmount"));
                rs.close();
                stmt.close();
                return ans;
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return -1;
    }

    /////////Until here EREZ



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
