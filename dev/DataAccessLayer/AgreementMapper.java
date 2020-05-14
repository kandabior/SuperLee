package DataAccessLayer;
import java.sql.*;

import DTO.SupplierDTO;
import com.sun.istack.internal.localization.NullLocalizable;
import javafx.util.Pair;

import java.text.DecimalFormat;
import java.util.*;

public class AgreementMapper {
    private static Connection conn;

    public static boolean tryOpen() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void tryClose() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItemToBillOfQuantities(int billId, int itemId, int itemQuantity, Double itemDiscount) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("INSERT INTO ItemsInBills VALUES (?,?,?,?);");
                st.setInt(1, billId);
                st.setInt(2, itemId);
                st.setInt(3, itemQuantity);
                st.setDouble(4, itemDiscount);
                int rowNum = st.executeUpdate();
                if (rowNum != 0) {
                    conn.commit();
                    conn.close();
                    st.close();
                } else {
                    conn.rollback();
                    conn.close();
                    st.close();
                }
            }
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}