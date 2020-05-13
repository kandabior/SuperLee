package DataAccessLayer;

import DTO.OrderDTO;
import DTO.OrderLineDTO;

import java.sql.*;
import java.util.*;

public class OrderMapper {
    private static Connection conn;

    public int getSize() {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT count(*) as num FROM Orders;");
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    int ans = res.getInt("num");
                    st.close();
                    conn.close();
                    return ans;
                }
                st.close();
                conn.close();
                return 0;
            }
            else return 0;
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return 0;
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


    public List<List<Object>> getOrdersLineByOrderID(int id) {
        List<List<Object>> returnList = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT *  FROM  OrderLines WHERE orderId = ?  ;");
                st.setInt(1, id);
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    List<Object> temp = new LinkedList<>();
                    temp.add(res.getInt("itemId"));
                    temp.add(res.getString("itemName"));
                    temp.add(res.getInt("itemQuantity"));
                    temp.add(res.getDouble("itemCost"));
                    temp.add(res.getDouble("itemDiscount"));
                    temp.add(res.getDouble("finalCost"));
                    returnList.add(temp);
                }
                st.close();
                conn.close();
                return returnList;
            }
            else return null;
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }


    public List<Object> getSupplierDeatails(int id) {
        List<Object> suppList = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT *  FROM  Orders WHERE id = ?  ;");
                st.setInt(1, id);
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    suppList.add(res.getInt("suppId"));
                    suppList.add(res.getString("suppName"));
                    suppList.add(res.getString("address"));
                    suppList.add(res.getInt("id"));
                    suppList.add(res.getString("orderDate"));
                    suppList.add(res.getString("phoneNumber"));
                    suppList.add(res.getInt("branchId"));
                }
                st.close();
                conn.close();
                return suppList;
            }
            else return null;
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }


    }

    public double getTotalOrderMoney(int orderId) {
        Double totalCost =-1.0;
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT SUM(finalCost) as total FROM OrderLines WHERE orderId = ?  ;");
                st.setInt(1, orderId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    totalCost = res.getDouble("total");
                }
                st.close();
                conn.close();
                return totalCost;
            }
            else return totalCost;
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return totalCost;
        }
    }

    public String getStatud(int orderId) {
        String status ="";
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT status  FROM Orders WHERE id = ?  ;");
                st.setInt(1, orderId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    status = res.getString("status");
                }
                st.close();
                conn.close();
                return status;
            }
            else return status;
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return status;
        }


    }

    public boolean insertOrder(OrderDTO o) {
        //this.orderMapper.insert(o.getId(),o.getBranchId(),o.getSuppName(),o.getSuppId(),o.getOrderDate(),o.getAddress(),totalCost,o.getPhoneNumber(),o.getStatus());
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("INSERT INTO Orders VALUES (?,?,?,?,?,?,?,?,?);");
                st.setInt(1, o.getId());
                st.setInt(2, o.getBranchId());
                st.setString(3, o.getSuppName());
                st.setInt(4, o.getSuppId());
                st.setString(5, o.getOrderDate());
                st.setString(6, o.getAddress());
                st.setDouble(7, o.getTotalCost());
                st.setString(7, o.getPhoneNumber());
                st.setString(7, o.getStatus());
                int rowNum = st.executeUpdate();
                st.close();
                if (rowNum != 0) {
                    conn.commit();
                    conn.close();
                    return true;
                } else {
                    conn.rollback();
                    conn.close();

                }
            } else return false;
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;
    }


    public boolean insertOrderLines(OrderLineDTO o) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("INSERT INTO OrderLines VALUES (?,?,?,?,?,?,?,?);");
                st.setInt(1, o.getOrderLineId());
                st.setInt(2, o.getItemId());
                st.setString(3, o.getItemName());
                st.setInt(4, o.getItemQuantity());
                st.setDouble(5, o.getItemCost());
                st.setDouble(6, o.getItemDiscount());
                st.setDouble(7, o.getFinalCost());
                st.setInt(8, o.getOrderId());
                int rowNum = st.executeUpdate();
                st.close();
                if (rowNum != 0) {
                    conn.commit();
                    conn.close();
                    return true;
                } else {
                    conn.rollback();
                    conn.close();

                }
            } else return false;
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;



    }
}

