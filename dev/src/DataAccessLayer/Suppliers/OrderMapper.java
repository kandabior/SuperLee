package src.DataAccessLayer.Suppliers;


import javafx.util.Pair;
import src.DataAccessLayer.Suppliers.DTO.OrderDTO;
import DataAccessLayer.Suppliers.DTO.OrderLineDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class OrderMapper {
    private static Connection conn;

    public int getSize() {
        try {
            if (tryOpen()) {
                //Class.forName("org.sqlite.JDBC");
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
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
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
    public static void tryClose()    {
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
                //Class.forName("org.sqlite.JDBC");
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
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }


    public List<Object> getSupplierDetails(int id) {
        List<Object> suppList = new LinkedList<>();
        try {
            if (tryOpen()) {
                //Class.forName("org.sqlite.JDBC");
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
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }


    }

    public double getTotalOrderMoney(int orderId) {
        Double totalCost =-1.0;
        try {
            if (tryOpen()) {
                //Class.forName("org.sqlite.JDBC");
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
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return totalCost;
        }
    }

    public String getStatus(int orderId) {
        String status ="";
        try {
            if (tryOpen()) {
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
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return status;
        }


    }

    public boolean insertOrder(OrderDTO o) {
        try {
            if (tryOpen()) {
                //Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("INSERT INTO Orders VALUES (?,?,?,?,?,?,?,?,?);");
                st.setInt(1, o.getId());
                st.setInt(2, o.getBranchId());
                st.setString(3, o.getSuppName());
                st.setInt(4, o.getSuppId());
                st.setString(5, o.getOrderDate());
                st.setString(6, o.getAddress());
                st.setDouble(7, o.getTotalCost());
                st.setString(8, o.getPhoneNumber());
                st.setString(9, o.getStatus());
                int rowNum = st.executeUpdate();
                st.close();
                if (rowNum != 0) {
                    //updateCounter
                    st = conn.prepareStatement("UPDATE Counters SET counter = ? WHERE name = ?;");
                    st.setInt(1, o.getId()+1);
                    st.setString(2, "orders");
                    st.executeUpdate();
                    ///
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
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;
    }


    public boolean insertOrderLines(OrderLineDTO o) {
        try {
            if (tryOpen()) {
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("INSERT INTO OrderLines VALUES (?,?,?,?,?,?,?);");
                st.setInt(1, o.getItemId());
                st.setString(2, o.getItemName());
                st.setInt(3, o.getItemQuantity());
                st.setDouble(4, o.getItemCost());
                st.setDouble(5, o.getItemDiscount());
                st.setDouble(6, o.getFinalCost());
                st.setInt(7, o.getOrderId());
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
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;



    }

    public int getOrderIdCounter() {
        int counter = 0;
        try {
            if (tryOpen()) {
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT counter  FROM Counters WHERE name = ?;");
                st.setString(1, "orders");
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    counter = res.getInt("counter");
                }
                st.close();
                conn.close();
                return counter;
            }
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
        }
        return counter;
    }

    public boolean checkIfOrderExists(int orderId) {
        try {
            if (tryOpen()) {
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT * FROM Orders WHERE id = ?;");
                st.setInt(1, orderId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    conn.commit();
                    conn.close();
                    st.close();
                    return true;
                } else {
                    conn.rollback();
                    conn.close();
                    st.close();
                    return false;
                }
            }
        } catch (Exception e) {
            tryClose();
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;
    }

    public boolean updateOrderStatus(int orderId) {
        try {
            if (tryOpen()) {
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("UPDATE Orders SET status = ? WHERE id = ?;");
                st.setString(1, "Complete");
                st.setInt(2, orderId);
                int rowNum = st.executeUpdate();
                if (rowNum != 0) {
                    conn.commit();
                    conn.close();
                } else {
                    conn.rollback();
                    conn.close();
                    st.close();
                    return false;
                }
                st.close();
                return true;
            } else return false;
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }

    }

    public Map<Pair<Integer,Integer>, Pair<Integer, Double>> getOrdersByDat(LocalDate day) {
        Map<Pair<Integer,Integer>, Pair<Integer, Double>> map = new HashMap<>();
        String date = day.toString();
        List<Pair<Integer,Integer>> ordersId = getOrdersIdByDate(date);
        try {
            if (tryOpen()) {
                for(int i=0 ; i<ordersId.size();i++)
                {
                    conn.setAutoCommit(false);
                    PreparedStatement st = conn.prepareStatement("SELECT itemId,itemQuantity,finalCost  FROM  OrderLines WHERE orderId = ?  ;");
                    st.setInt(1, ordersId.get(i).getKey());
                    ResultSet res = st.executeQuery();
                    while (res.next()) {
                        Pair<Integer,Integer> GidLid = new Pair(res.getInt("itemId"),getLocalItemId(res.getInt("itemId"), ordersId.get(i).getValue()));
                        Pair<Integer,Double> QuantityCost =new Pair(res.getInt("itemQuantity"),res.getDouble("finalCost"));
                        map.put(GidLid,QuantityCost);
                    }
                    st.close();
                }
                conn.close();
                return map;
            }
            else return null;
        } catch(Exception e){
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    private List<Pair<Integer,Integer>> getOrdersIdByDate(String date) {
        //TODO check date and if its waiting
        List<Pair<Integer,Integer>> ordersIds = new LinkedList<>();
        try {
            if (tryOpen()) {
                //Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT id,suppId  FROM  Orders WHERE orderDate = ? AND status = ?  ;");
                st.setString(1, date);
                st.setString(2, "Waiting");
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    Pair<Integer,Integer> p = new Pair(res.getInt("id"),res.getInt("suppId"));
                    ordersIds.add(p);
                }
                st.close();
                conn.close();
                return ordersIds;
            }
            else return ordersIds;
        } catch(Exception e){
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return ordersIds;
        }
    }

    public int getLocalItemId(int itemId,int suppId) {
        int localItemId = -1;
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT localItemId FROM SupplierItems WHERE itemId = ? AND SupplierId = ?;");
                st.setInt(1, itemId);
                st.setInt(2, suppId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    localItemId = res.getInt("localItemId");
                }
                st.close();
                conn.close();
                return localItemId;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
        }
        return localItemId;
    }


}

