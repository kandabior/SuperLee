package src.DataAccessLayer.Inventory;
import javafx.util.Pair;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ItemMapper {

    private static Connection conn;

    public boolean checkIfItemExist(int itemId) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Items WHERE itemId = ?;");
            st.setInt(1, itemId);
            ResultSet res = st.executeQuery();

            if (res.next()) {
                conn.close();
                st.close();
                return true;
            }
            st.close();
            conn.close();
            return false;
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public boolean addItem(int itemId,String name) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("INSERT INTO Items VALUES (?,?);");
            st.setInt(1, itemId);
            st.setString(2, name );
            st.executeUpdate();
            st.close();
            conn.commit();
            conn.close();
            return true;
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                conn.close();
            }
            catch (Exception e2){

            }
            return false;
        }
    }

    public static String getName(Integer id) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("SELECT itemName FROM Items WHERE itemId = ?;");
            st.setInt(1, id);
            ResultSet res = st.executeQuery();
            if (res.next()) {
                String ans =res.getString("itemName");
                st.close();
                conn.close();
               return ans;
            }
            conn.close();
            return null;
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    public List<Pair<Integer, String>> getAllItems() {
        try {
            List<Pair<Integer, String>> output= new LinkedList<>();
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Items;");
            ResultSet res = st.executeQuery();
            while(res.next()) {
                output.add(new Pair<>(res.getInt("itemId"),res.getString("itemName")));
            }
            res.close();
            st.close();
            conn.close();
            return output;
        } catch (Exception e) {
            try {
                conn.close();
            }
            catch (Exception e2) {
            }
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }


    }
}
