package DataAccessLayer;

import java.sql.*;

public class SupplierMapper {

    private static Connection conn;

    /*
    public static void initializeSupplierMapper() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:EOEDdatabase.db");
        } catch (Exception e) {
            System.out.println("DB connection problem.");
        }
        System.out.println("DB opened successfully.");
    }*/

    public static String addSupplier(int id, String name, String phoneNum, String bankAccount, String payment, String suppSchedule, String suppLocation, String address) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("INSERT INTO Suppliers VALUES (?,?,?,?,?,?,?,?);");
            st.setInt(1, id);
            st.setString(2, name);
            st.setString(3, phoneNum);
            st.setString(4, bankAccount);
            st.setString(5, payment);
            st.setString(6, suppSchedule);
            st.setString(7, suppLocation);
            st.setString(8, address);
            int rowNum = st.executeUpdate();
            st.close();
            if (rowNum != 0) {
                conn.commit();
                conn.close();
                return "Supplier " + name + " was added successfully.";
            } else {
                conn.rollback();
                conn.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return "Can't insert " + name + " to the system.";
        }
        return "";
    }
}
