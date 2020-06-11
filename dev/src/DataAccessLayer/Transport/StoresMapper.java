package src.DataAccessLayer.Transport;

import src.DataAccessLayer.Transport.DTO.DTO_Store;
import src.DataAccessLayer.Transport.DTO.DTO_Supplier;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class StoresMapper {
   private Connection con = null;

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

   public List<DTO_Store> getAllStores() {
      List<DTO_Store> allStores = new LinkedList<>();
      try {
         if (tryOpen()) {
            Class.forName("org.sqlite.JDBC");
            con.setAutoCommit(false);
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Stores;");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
               DTO_Store s = new DTO_Store(result.getInt("ID"), result.getString("address"),
                       result.getString("phoneNumber"), result.getString("contactName"), result.getInt("area"));
               allStores.add(s);
            }
            statement.close();
            con.close();
            return allStores;

         } else return null;
      } catch (Exception e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         tryClose();
         return null;
      }
   }

   public List<String> getStoresString() {
         List<String> stores = new LinkedList<>();
         try {
            if (tryOpen()) {
               Class.forName("org.sqlite.JDBC");
               con.setAutoCommit(false);
               PreparedStatement statement = con.prepareStatement("SELECT * FROM Stores;");
               ResultSet result = statement.executeQuery();
               while (result.next()) {
                  DTO_Store s = new DTO_Store(result.getInt(1), result.getString(2),result.getString(3), result.getString(4), result.getInt(5));
                  stores.add(s.toString());
               }
               statement.close();
               con.close();
               return stores;
            } else return null;
         } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;

      }

   }

   public boolean validArea(int area) {
      try {
         if (tryOpen()) {
            Class.forName("org.sqlite.JDBC");
            con.setAutoCommit(false);
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Stores WHERE area = ?;");
            statement.setInt(1, area);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
               con.commit();
               con.close();
               statement.close();
               return true;
            } else {
               con.rollback();
               con.close();
               statement.close();
               return false;
            }
         }
      } catch (Exception e) {
         tryClose();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         return false;
      }
      return false;
   }

   public List<DTO_Store> getStoresInArea(int area) {
      List<DTO_Store> StoresInArea = new LinkedList<>();
      try {
         if (tryOpen()) {
            Class.forName("org.sqlite.JDBC");
            con.setAutoCommit(false);
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Stores WHERE area = ?;");
            statement.setInt(1,area);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
               DTO_Store s = new DTO_Store(result.getInt("ID"), result.getString("address"),
                       result.getString("phoneNumber"), result.getString("contactName"), result.getInt("area"));
               StoresInArea.add(s);
            }
            statement.close();
            con.close();
            return StoresInArea;

         } else return null;
      } catch (Exception e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         tryClose();
         return null;
      }

   }

    public int getArea(int storeId) {
       try {
          if (tryOpen()) {
             Class.forName("org.sqlite.JDBC");
             con.setAutoCommit(false);
             PreparedStatement statement = con.prepareStatement("SELECT area FROM Stores WHERE ID = ?");
             statement.setInt(1,storeId);
             ResultSet result = statement.executeQuery();
             if (result.next()) {
                int area = result.getInt(1);
                con.close();
                statement.close();
                return area;
             }
             con.close();
             statement.close();
             return -1;
          } else return -1;
       } catch (Exception e) {
          System.err.println(e.getClass().getName() + ": " + e.getMessage());
          return -1;
       }
    }
}