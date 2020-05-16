package DataAccessLayer.Transport;

import DataAccessLayer.Transport.DTO.DTO_TransportDoc;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DocsMapper {
    private Connection con;

    private boolean tryOpen() {
        try {
            String url = "jdbc:sqlite:transportsAndWorkers.db";
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

    public boolean truckIsBusy(String truckId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM TransportDocs WHERE truckID = ? AND status = ?;");
                statement.setString(1, truckId);
                statement.setString(2,"PENDING");
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

    public boolean supplierIsBusy(int supplierId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM DocSuppliers join TransportDocs on DocId = ID WHERE SID = ? AND status = ?;");
                statement.setInt(1, supplierId);
                statement.setString(2,"PENDING");
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

    public List<String> getDocs(String docStatus) {
        List<String> pendingDocs = new LinkedList<>();
        List<Integer> stores = new LinkedList<>();
        List<Integer> suppliers = new LinkedList<>();
        List<Map<Integer,Integer>> items = new LinkedList<>();

        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM TransportDocs WHERE status = ?;");
                statement.setString(1,docStatus);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    int transportId = result.getInt("ID");
                    suppliers = getTransportsSites("DocSuppliers",transportId);
                    stores = getTransportsSites("DocStores", transportId);
                    for (int i = 0; i < stores.size(); i++) {
                        items.add(getTransportsItems(transportId, stores.get(i)));
                    }
                   /* DTO_TransportDoc.Status status ;
                    switch (result.getString("status")){
                        case "PENDING":
                            status = Status.PENDING;
                            break;
                        case "SUCCESS":
                            status = DTO_TransportDoc.Status.SUCCESS;
                            break;
                        default: //FAIL
                            status = DTO_TransportDoc.Status.FAIL;
                    }*/
                    DTO_TransportDoc s = new DTO_TransportDoc(transportId, result.getInt("area"),
                            result.getString("date"),result.getString("truckId"), result.getInt("driverId"),
                            result.getString("driverName"),stores, suppliers, items ,result.getString("status"), result.getDouble("finalWeight"));
                    pendingDocs.add(s.toString());
                }
                statement.close();
                con.close();
                return pendingDocs;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    private Map<Integer, Integer> getTransportsItems(int transportId, int storeId) {
        Map<Integer,Integer> storeItems = new HashMap<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT item,quantity FROM Order WHERE DocID = ? AND SID = ?;");
                statement.setInt(1,transportId);
                statement.setInt(2,storeId);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    storeItems.put(result.getInt("item"),result.getInt("quantity"));
                }
                statement.close();
                con.close();
                return storeItems;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public List<Integer> getTransportsSites(String tableName, int transportId) {
        List<Integer> sites = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT SID FROM ? WHERE DocID = ?;");
                statement.setString(1,tableName);
                statement.setInt(2,transportId);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    sites.add(result.getInt("SID"));
                }
                statement.close();
                con.close();
                return sites;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public boolean validTransport(int docId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM TransportDocs WHERE id = ?;");
                statement.setInt(1, docId);
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

    public DTO_TransportDoc getTransportDoc(int docId){
        List<Integer> stores = new LinkedList<>();
        List<Integer> suppliers = new LinkedList<>();
        List<Map<Integer,Integer>> items = new LinkedList<>();

        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM TransportDocs WHERE docId = ? AND status = ?;");
                statement.setInt(1,docId);
                statement.setString(2,"PENDING");
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    suppliers = getTransportsSites("DocSuppliers", docId);
                    stores = getTransportsSites("DocStores", docId);
                    for (int i = 0; i < stores.size(); i++) {
                        items.add(getTransportsItems(docId, stores.get(i)));
                    }
                    DTO_TransportDoc s = new DTO_TransportDoc(docId, result.getInt("area"),
                            result.getString("date"), result.getString("truckId"), result.getInt("driverId"),
                            result.getString("driverName"), stores, suppliers, items, "PENDING", result.getDouble("finalWeight"));
                    statement.close();
                    con.close();
                    return s;
                }
                else
                    return null;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public void updateTransportDoc(double finalWeight, String status, int docId ) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);

                PreparedStatement statement = con.prepareStatement("UPDATE TransportDocs SET finalWeight = ? , status = ? WHERE ID = ? ;");
                statement.setDouble(1, finalWeight);
                statement.setString(2, status);
                statement.setInt(3, docId);
                int rowNum = statement.executeUpdate();
                if (rowNum != 0) {
                    statement.close();;
                    con.commit();
                    con.close();
                } else {
                    con.rollback();
                    con.close();
                    statement.close();
                    System.err.println("Update failed");
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }


    public void addItemsToTransport(int docId, List<Map<Integer, Integer>> allItems) {
       /* try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);

                PreparedStatement statement = con.prepareStatement("INSERT INTO Order VALUES (?,?,?,?);");
                statement.setInt(1, docId);
                statement.setString(2, s.getAddress());
                statement.setString(3, s.getPhoneNumber());
                statement.setString(4, s.getContactName());
                statement.setInt(5, s.getArea());
                int rowNum = statement.executeUpdate();
                if (rowNum != 0) {
                    con.commit();
                    con.close();
                } else {
                    con.rollback();
                    con.close();
                }
                statement.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }*/
    }

}
