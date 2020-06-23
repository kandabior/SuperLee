package src.DataAccessLayer.Inventory;

import javafx.util.Pair;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class InventoryMapper {

    private Connection c;
    public InventoryMapper(){

    }

    //Managers

    public boolean addInventoryManager(int branchId,String username, String password) {
        PreparedStatement stmt = null;
        try {
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO InventoryManagers VALUES (?,?,?);");
            stmt.setInt(1, branchId);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return true;
        } catch (Exception e) {
            if (c != null) {
                try {
                    //System.err.print("Transaction is being rolled back");
                    c.rollback();
                } catch (SQLException excep) {
                }
            }
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
    public boolean addGlobalManager(int branchId,String username, String password){
        PreparedStatement stmt = null;
        try {
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO GlobalManagers VALUES (?,?,?);");
            stmt.setInt(1, branchId);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return true;
        } catch (Exception e) {
            if (c != null) {
                try {
                    //System.err.print("Transaction is being rolled back");
                    c.rollback();
                } catch (SQLException excep) {
                }
            }
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
    public boolean isInventoryManagerExist(int branchId,String username) {
        PreparedStatement stmt = null;
        try {
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT * FROM InventoryManagers WHERE branchId=? AND userName=?;");
            stmt.setInt(1, branchId);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return true;
            } else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch (Exception e) {
            tryClose(c);
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }

    }
    public boolean isGlobalMannagerExist(int branchId,String username) {
        PreparedStatement stmt = null;
        try {
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT * FROM GlobalManagers WHERE branchId=? AND userName=?;");
            stmt.setInt(1, branchId);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return true;
            } else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch (Exception e) {
            tryClose(c);
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }

    }
    public boolean checkGlobalManager(int branchId,String username, String password) {
        PreparedStatement stmt = null;
        try {
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT password FROM GlobalManagers WHERE branchId=? AND userName=?;");
            stmt.setInt(1, branchId);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String realPass=rs.getString("password");
                rs.close();
                stmt.close();
                c.close();
                return realPass.equals(password);
            } else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch (Exception e) {
            tryClose(c);
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }

    }
    public boolean checkInventoryManager(int branchId,String username, String password) {
        PreparedStatement stmt = null;
        try {
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT password FROM InventoryManagers WHERE branchId=? AND userName=?;");
            stmt.setInt(1, branchId);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String realPass=rs.getString("password");
                rs.close();
                stmt.close();
                return realPass.equals(password);
            } else {
                rs.close();
                stmt.close();
                return false;
            }
        } catch (Exception e) {
            tryClose(c);
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
    public boolean removeInventoryManager(int branchId,String usernameToRemove) {
        PreparedStatement stmt = null;
        try {
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("DELETE FROM InventoryManagers WHERE branchId=? AND userName=?;");
            stmt.setInt(1, branchId);
            stmt.setString(2, usernameToRemove);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return true;
        } catch (Exception e) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            ////System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;


    }
    public boolean removeGlobalManager(int branchId,String username, String password) {
        PreparedStatement stmt = null;
        try {
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("DELETE FROM GlobalManagers WHERE branchId=? AND userName=? AND password=?;");
            stmt.setInt(1, branchId);
            stmt.setString(2, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return true;
        } catch (Exception e) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;



    }

    //Inventory
    public  boolean CreateNewInventory(Integer branchId) {
        PreparedStatement stmt = null;
        try {
            
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
                    //System.err.print("Transaction is being rolled back");
                    c.rollback();
                } catch (SQLException excep) {
                }
                //System.out.println(e.getClass().getName() + ": " + e.getMessage());
            }
            return false;
        }
    }
    public  boolean addProduct(int branchId, int id, String name, Double costPrice, Double salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place) {
        PreparedStatement stmt=null;
        try{
            
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
                //System.out.println(cat);
                stmt = c.prepareStatement("INSERT INTO Categories VALUES (?,?,?);");
                stmt.setInt(1, branchId);
                stmt.setInt(2, id);
                stmt.setString(3, cat);
                stmt.executeUpdate();
            }
            stmt = c.prepareStatement("INSERT INTO LastCostPrices VALUES (?,?,?);");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            stmt.setDouble(3, costPrice);
            stmt.executeUpdate();

            stmt = c.prepareStatement("INSERT INTO LastSalePrices VALUES (?,?,?);");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            stmt.setDouble(3, salePrice);
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
            //System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }
    public  String getProductName(int branchId, int prodId) {
        PreparedStatement stmt=null;
        try{
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT name FROM Products WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, prodId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                String output=rs.getString("name");
                rs.close();
                stmt.close();
                c.close();
                return output;
            }
        } catch ( Exception e ) {
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "Product Id :" + prodId + " Does not exist in inventory number " + branchId;
    }
    public  String removeProduct(int branchId, int id) {
        PreparedStatement stmt=null;
        try{
            
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
            stmt = c.prepareStatement("DELETE FROM Expireds WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt = c.prepareStatement("DELETE FROM Categories WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt = c.prepareStatement("DELETE FROM LastCostPrices WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt = c.prepareStatement("DELETE FROM LastSalePrices WHERE branchId=? AND productId=?;");
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
            //System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return "cant removed this product";
    }
    public  boolean addNewAmountProductToQuantities(int branchId, int id, int amount) {
        PreparedStatement stmt=null;
        try{
            
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
            c.close();
            return true;
        } catch ( Exception e ) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }
    public  boolean addAmountToProduct(int branchId, int id, int currentAmount,int toAddAmount) { //adding half to storage half to shelf
        PreparedStatement stmt = null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            stmt = c.prepareStatement("UPDATE Quantities SET storageQuantity=? WHERE branchId=? AND productId=?;");
            stmt.setInt(1, toAddAmount+currentAmount);
            stmt.setInt(2, branchId);
            stmt.setInt(3, id);
            stmt.executeUpdate();
                stmt.close();

            c.close();
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
    public  String removeAmountFromProductShelf(int branchId, int id, int amount) {
        PreparedStatement stmt=null;
        try{
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("UPDATE Quantities SET shelfQuantity = shelfQuantity-? WHERE branchId=? AND productId=? ;");
            stmt.setInt(1, amount);
            stmt.setInt(2, branchId);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            c.close();
            return "Amount of " + amount + " removed successfully";
        } catch ( Exception e ) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return "cant removed this amount of product";
    }
    public  String removeAmountFromProductStorage(int branchId, int id, int amount) {
        PreparedStatement stmt=null;
        try{
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("UPDATE Quantities SET storageQuantity = storageQuantity-? WHERE branchId=? AND productId=? ;");
            stmt.setInt(1, amount);
            stmt.setInt(2, branchId);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            c.close();
            return "Amount of " + amount + " removed successfully";
        } catch ( Exception e ) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return "cant removed this amount of product";
    }
    public  boolean setSalePrice(int branchId, int id, Double price) {
        PreparedStatement stmt=null;
        try{
            
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
            return true;
        } catch ( Exception e ) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }
    public  int getProductMin(int branchId, int prodId) {
        PreparedStatement stmt=null;
        try{
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
                c.close();
                return ans;
            }
        } catch ( Exception e ) {
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            tryClose(c);
        }
        return -1;
    }
    public  String setCategory(int branchId, int id, List<String> category) {
        PreparedStatement stmt=null;
        try{
            
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            //remove previous categories
            for(String cat: category) {
                String sql= "DELETE FROM Categories WHERE branchId= ? AND productID= ?";
                stmt= c.prepareStatement(sql);
                stmt.setInt(1,branchId);
                stmt.setInt(2,id);
                stmt.executeUpdate();
                stmt.close();
            }
            //set new categories
            for(String cat : category) {
                stmt = c.prepareStatement("INSERT INTO Categories VALUES (?,?,?);");
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
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            tryClose(c);
            return "Failed to update categories for product: "+id+" in branch: "+branchId;
        }
    }
    public  boolean updateExpired(Integer branchId, Integer prodId, Integer amount) {
        PreparedStatement stmt=null;
        try{
            
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
                stmt.setInt(1,currentAmount+amount);
                stmt.setInt(2,branchId);
                stmt.setInt(3,prodId);
                int numOfUpdates=stmt.executeUpdate();
                boolean output=false;
                if(numOfUpdates!=0){
                    output=true;
                }
                stmt.close();
                rs.close();
                c.commit();
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
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
    public  boolean addExpired(Integer branchId, Integer prodId, Integer amount) {
        PreparedStatement stmt=null;
        try{
            
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
            c.commit();
            c.close();
            return output;

        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
    public  boolean shelfToStorage(int branchId, int id, int amount) {
        PreparedStatement stmt=null;
        try{
            
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
                    c.commit();
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
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
    public  boolean storageToShelf(int branchId, int id, int amount) {
        PreparedStatement stmt=null;
        try{
            
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
                    c.commit();
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
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }

    }
    public  int getProductQuantity(int branchId, Integer id) {
        PreparedStatement stmt=null;
        try{
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
                rs.close();
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
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
    }
    public  int getShelfQunatity(int branchId, Integer id) {
        PreparedStatement stmt=null;
        try{
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
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }

    }
    public  int getStorageQunatity(int branchId, Integer id) {
        PreparedStatement stmt=null;
        try{
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
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
    }
    public  int getExpiredQuantity(int branchId, Integer id) {
        PreparedStatement stmt=null;
        try{
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
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
    }
    public  List<Double> getSalePrices(int branchId, int id) {
        PreparedStatement stmt=null;
        try{
            List<Double> output=new LinkedList<>();
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT lastSalePrice FROM LastSalePrices WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                output.add(rs.getDouble("lastSalePrice"));
            }
            stmt.close();
            c.close();
            return output;
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return new LinkedList<>();
        }
    }
    public  List<Double> getCostPrices(int branchId, int id) {
        PreparedStatement stmt=null;
        try {
            List<Double> output = new LinkedList<>();
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT lastCostPrice FROM LastCostPrices WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                output.add(rs.getDouble("lastCostPrice"));
            }

            rs.close();
            stmt.close();
            c.close();
            return output;
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return new LinkedList<>();
        }

    }
    public  List<Integer> getBranchIdsToWeeklyOrders(int dayOfTheWeek) {
        PreparedStatement stmt=null;
        try {
            List<Integer> output = new LinkedList<>();
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT branchId FROM Inventory WHERE dayForWeeklyOrder=?;");
            stmt.setInt(1, dayOfTheWeek);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (!output.contains(rs.getInt("branchId"))) {
                    output.add(rs.getInt("branchId"));
                }
            }

            rs.close();
            stmt.close();
            c.close();
            return output;
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return new LinkedList<>();
        }
    }
    private void tryClose(Connection c) {
        try {
            c.rollback();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public  boolean isBranchExist(int branchId) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT branchId FROM Inventory WHERE branchId=?;");
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return true;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }

    }
    public  boolean isInventoryConteinsProd(int branchId, int prodId) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT * FROM Products WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, prodId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return true;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
    public  double getCurrentCostPrices(int branchId, Integer prodId) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT costPrice FROM Products WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, prodId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                double cost=rs.getDouble("costPrice");
                rs.close();
                stmt.close();
                c.close();
                return cost;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return 0;
            }
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }

    }

    public  void setCostPrice(int branchId, Integer prodId, double newPrice) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("UPDATE Products SET costPrice= ? WHERE branchId=? AND productId=?;");
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, branchId);
            stmt.setInt(3, prodId);
            int numOfUpdates=stmt.executeUpdate();
            boolean output1=false;
            if(numOfUpdates!=0){
                output1=true;
            }
            stmt.close();
            c.commit();
            if(output1){
                stmt = c.prepareStatement("INSERT INTO LastCostPrices VALUES (?,?,?)");
                stmt.setInt(1, branchId);
                stmt.setInt(2, prodId);
                stmt.setDouble(3, newPrice);
                stmt.executeUpdate();
                stmt.close();
                c.commit();
            }
            else{
                stmt.close();
                c.close();
            }
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public  List<Integer> getProductsIds(int branchId) {
        PreparedStatement stmt=null;
        try{
            List<Integer> output=new LinkedList<>();
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT productId FROM Products WHERE branchId=?;");
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                output.add(rs.getInt("productId"));
            }
            stmt.close();
            rs.close();
            c.close();
            return output;
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return new LinkedList<>();
        }

    }

    public  List<String> getProductCategories(int branchId, Integer id) {
        PreparedStatement stmt=null;
        try{
            List<String> output=new LinkedList<>();
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT category FROM Categories WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                output.add(rs.getString("category"));
            }
            stmt.close();
            rs.close();
            c.close();
            return output;
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return new LinkedList<>();
        }
    }

    public  List<Integer> getExpiredIds(int branchId) {
        PreparedStatement stmt=null;
        try{
            List<Integer> output=new LinkedList<>();
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT productId FROM Expireds WHERE branchId=?;");
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                output.add(rs.getInt("productId"));
            }
            stmt.close();
            c.close();
            return output;
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return new LinkedList<>();
        }

    }

    public  boolean isExpiredContainProduct(int branchId, int productId) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT * FROM Expireds WHERE branchId=? AND productId=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, productId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return true;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }

    }

    public  boolean setWeeklyOrder(int branchId, int day) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO Inventory VALUES (?,?)");
            stmt.setInt(1, branchId);
            stmt.setInt(2, day);
            int numOfUpdates=stmt.executeUpdate();
            boolean output1=false;
            if(numOfUpdates!=0){
                output1=true;
            }
            stmt.close();
            c.commit();
            c.close();
            return output1;
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public  boolean AddToWeeklyOrder(int branchId, Integer key, Integer value, int day) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt=c.prepareStatement("INSERT INTO WeeklyOrders values (?,?,?,?)");
            stmt.setInt(1,branchId);
            stmt.setInt(2,day);
            stmt.setInt(3,key);
            stmt.setInt(4,value);
            int numOfUpdates=stmt.executeUpdate();
            boolean output=false;
            if(numOfUpdates!=0){
                output=true;
            }
            stmt.close();
            c.commit();
            c.close();
            return output;

        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );

        }
        return false;
    }

    public  boolean isWeeklyContainProd(int branchId,Integer ids,int day) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT * FROM WeeklyOrders WHERE branchId=? AND dayOfTheWeek=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, day);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return true;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }

    }

    public  void removeFromWeeklyOrder(int branchId, Integer id, int day) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt=c.prepareStatement("DELETE FROM WeeklyOrders WHERE branchId=? AND productId=? AND dayOfTheWeek=?");
            stmt.setInt(1,branchId);
            stmt.setInt(2,id);
            stmt.setInt(3,day);
            int numOfUpdates=stmt.executeUpdate();
            boolean output=false;
            if(numOfUpdates!=0){
                output=true;
            }
            stmt.close();
            c.commit();
            c.close();


        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );

        }
    }

    public  List<Pair<Integer, Integer>> getWeeklyOrder(int branchId,int day) {
        PreparedStatement stmt=null;
        try{
            List<Pair<Integer,Integer>> output=new LinkedList<>();
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT productId, amount FROM WeeklyOrders WHERE branchId=? AND dayOfTheWeek=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, day);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                output.add(new Pair<>(rs.getInt("productId"),rs.getInt("amount")));
            }
            rs.close();
            stmt.close();
            c.close();
            return output;
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return new LinkedList<>();
        }


    }


    public boolean weeklyOrderDayExist(int branchId, int day) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT * FROM Inventory WHERE branchId=? AND dayForWeeklyOrder=?;");
            stmt.setInt(1, branchId);
            stmt.setInt(2, day);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return true;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public boolean setItemsIds(int globalItemId, int localItemId) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt=c.prepareStatement("INSERT INTO ItemsIds values (?,?)");
            stmt.setInt(1,globalItemId);
            stmt.setInt(2,localItemId);
            int numOfUpdates=stmt.executeUpdate();
            boolean output=false;
            if(numOfUpdates!=0){
                output=true;
            }
            stmt.close();
            c.commit();
            c.close();
            return output;

        } catch ( Exception e ) {
            tryClose(c);
            return false;
        }
    }

    public boolean getItemsIdsExist(int globalItemId, int localItemId) {
        PreparedStatement stmt=null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT * FROM ItemsIds WHERE globalId=? AND localId=?;");
            stmt.setInt(1, globalItemId);
            stmt.setInt(2, localItemId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return true;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public boolean isOrderExists(int orderId) {
        PreparedStatement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT * FROM Orders WHERE id=?;");
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return true;
            }
            else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        }
        catch ( Exception e ) {
            tryClose(c);
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public boolean cancelOrder(int orderId) {
        PreparedStatement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO CancellationRequests VALUES (?,?);");
            stmt.setInt(1,orderId);
            stmt.setInt(2, 0);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            return true;
        } catch (Exception e) {
            if (c != null) {
                try {
                    //System.err.print("Transaction is being rolled back");
                    c.rollback();
                } catch (SQLException excep) {
                }
            }
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }

    }
}
