package DataAccessLayer;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class InventoryMapper {

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

//    public static List<Pair<Integer,Integer>> getProductQuantity(int branchId) {
//        //"TODO//IMPlEMENT";
//        return -1;
//    }

    public static int getProductQuantity(int branchId, Integer id) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT shelfQuantity, storageQuantity FROM Quantities WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int shelfQuantity = rs.getInt("shelfQuantity");
                int storageQuantity = rs.getInt("storageQuantity");
                stmt.close();
                c.close();
                return shelfQuantity+storageQuantity;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return 0;
            }
        } catch ( Exception e ) {
            tryClose(c);
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
        //TODO: check

    }

    public static int getShelfQunatity(int branchId, Integer id) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT shelfQuantity FROM Quantities WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int shelfQuantity = rs.getInt("shelfQuantity");
                stmt.close();
                c.close();
                return shelfQuantity;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return 0;
            }
        } catch ( Exception e ) {
            tryClose(c);
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }

    }

    public static int getStorageQunatity(int branchId, Integer id) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT storageQuantity FROM Quantities WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int storageQuantity = rs.getInt("storageQuantity");
                stmt.close();
                c.close();
                return storageQuantity;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return 0;
            }
        } catch ( Exception e ) {
            tryClose(c);
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
    }

    public static int getExpiredQuantity(int branchId, Integer id) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT amount FROM Expireds WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int amount = rs.getInt("amount");
                stmt.close();
                c.close();
                return amount;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return 0;
            }
        } catch ( Exception e ) {
            tryClose(c);
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
    }

    public static List<Double> getSalePrices(int branchId, int id) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            List<Double> output=new LinkedList<>();
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT lastSalePrice FROM LastSalePrices WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                output.add(rs.getDouble("lastSalePrice"));
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return output;
            }
            stmt.close();
            c.close();
            return output;
        } catch ( Exception e ) {
            tryClose(c);
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return new LinkedList<>();
        }
    }

    public static List<Double> getCostPrices(int branchId, int id) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            List<Double> output=new LinkedList<>();
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT lastCostPrice FROM LastSalePrices WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                output.add(rs.getDouble("lastCostPrice"));
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return output;
            }
            stmt.close();
            c.close();
            return output;
        } catch ( Exception e ) {
            tryClose(c);
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return new LinkedList<>();
        }

    }

    public static List<Integer> getBranchIdsToWeeklyOrders(int dayOfTheWeek) {
        PreparedStatement stmt=null;
        Connection c = null;
        try{
            List<Integer> output=new LinkedList<>();
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT branchId FROM Inventory WHERE dayForWeeklyOrder=?;");
            stmt.setInt(1, dayOfTheWeek);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                output.add(rs.getInt("branchId"));
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return output;
            }
            stmt.close();
            c.close();
            return output;
        } catch ( Exception e ) {
            tryClose(c);
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return new LinkedList<>();
        }
    }

    private static void tryClose(Connection c) {
        try {
            c.rollback();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
