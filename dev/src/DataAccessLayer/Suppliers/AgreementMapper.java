package src.DataAccessLayer.Suppliers;
import java.sql.*;
import javafx.util.Pair;
import org.sqlite.SQLiteConfig;

import java.util.*;

public class AgreementMapper {

    private static Connection conn;

    public static boolean tryOpen() {
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db", config.toProperties());

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
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void changeInBillOfQuantities(int suppId, Integer itemId, Pair<Integer, Double> quantity_disc) {
        try {
            if (tryOpen()) {
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("UPDATE ItemsInBills SET quantity = ?, discount = ?" +
                        "WHERE billId = ? AND itemId = ?;");
                st.setInt(1, quantity_disc.getKey().intValue());
                st.setDouble(2, quantity_disc.getValue().doubleValue());
                st.setInt(3, suppId);
                st.setInt(4, itemId);
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
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void deleteFromBillOfQuantities(int suppId, Integer itemId) {
        try {
            if (tryOpen()) {
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("DELETE FROM ItemsInBills WHERE billId = ? AND itemId = ?;");
                st.setInt(1, suppId);
                st.setInt(2, itemId);
                st.executeUpdate();
                conn.commit();
                conn.close();
                st.close();
            }
        } catch (Exception e) {
            tryClose();
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void deleteBillOfQuantities(int suppId) {
        try {
            if (tryOpen()) {
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("DELETE FROM ItemsInBills WHERE billId = ?;");
                st.setInt(1, suppId);
                st.executeUpdate();

                st = conn.prepareStatement("DELETE FROM BillsOfQuantities WHERE billId = ?;");
                st.setInt(1, suppId);
                st.executeUpdate();

                st = conn.prepareStatement("UPDATE Agreements SET billId = ? WHERE suppId = ?;");
                st.setInt(1, 0);
                st.setInt(2, suppId);
                st.executeUpdate();

                conn.commit();
                conn.close();
                st.close();
            }
        } catch (Exception e) {
            tryClose();
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public Map<Integer, Pair<Integer, Double>> getBillOfQuantities(int suppId) {
        Map<Integer, Pair<Integer, Double>> bill = new HashMap<>();
        try {
            if (tryOpen()) {
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT * FROM ItemsInBills WHERE billId = ?;");
                st.setInt(1, suppId);
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    Pair<Integer, Double> p = new Pair<>(res.getInt("quantity"), res.getDouble("discount"));
                    bill.put(res.getInt("itemId"), p);
                }
                conn.commit();
                conn.close();
                st.close();
            }
        } catch (Exception e) {
            tryClose();
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bill;
    }

    public int getBillSize(int suppId) {
        try {
            if (tryOpen()) {
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT count(*) as count FROM ItemsInBills WHERE billId = ?;");
                st.setInt(1, suppId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    int ans = res.getInt("count");
                    conn.close();
                    st.close();
                    return ans;
                }
                conn.close();
                return 0;
            }
            else return 0;
        } catch(Exception e){
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return 0;
        }
    }
}