package DataAccessLayer;

import java.sql.*;

public class ItemMapper {
    private static Connection conn;

    public static boolean checkIfItemExist(int itemId) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Items WHERE itemId = ?;");
            st.setInt(1, itemId);
            ResultSet res = st.executeQuery();
            st.close();
            if (res.next()) {
                conn.close();
                return true;
            }
            conn.close();
            return false;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public static boolean tryOpen()
    {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void tryClose()
    {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static String getName(Integer id) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("SELECT itemName FROM Items WHERE itemId = ?;");
            st.setInt(1, id);
            ResultSet res = st.executeQuery();
            st.close();

            if (res.next()) {
                conn.close();
               return res.getString("itemName");
            }
            conn.close();
            return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }
}
