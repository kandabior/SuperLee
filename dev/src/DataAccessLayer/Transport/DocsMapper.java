package DataAccessLayer.Transport;

import BusinessLayer.TransportModule.Store;
import DataAccessLayer.Transport.DTO.DTO_TransportDoc;

import java.sql.*;
import java.util.*;

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


    public void addItemsToTransport(int docId, List<Integer> stores, List<Map<Integer, Integer>> allItems) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                Iterator<Integer> itr1 = stores.iterator();
                Iterator<Map<Integer,Integer>> itr2 = allItems.iterator();
                int i = 0;
                while (itr1.hasNext()) {
                    int storeId = itr1.next();
                    Map<Integer, Integer> storesItems = itr2.next();
                    for (Map.Entry<Integer, Integer> entry : storesItems.entrySet()) {
                        /*System.out.println("Key = " + entry.getKey() +
                                ", Value = " + entry.getValue());*/
                        PreparedStatement statement1 = con.prepareStatement("INSERT INTO Order VALUES (?,?,?,?);");
                        statement1.setInt(1, docId);
                        statement1.setInt(2, entry.getKey());
                        statement1.setInt(3, entry.getValue());
                        statement1.setInt(4, storeId);
                        int rowNum = statement1.executeUpdate();
                        if (rowNum != 0) {
                            con.commit();
                            statement1.close();
                        }
                        else {
                            con.rollback();
                            con.close();
                            System.err.println("Problem in inserting items");
                            break;
                        }
                    }
                }
                setNewStatus(docId);
                con.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void setNewStatus(int docId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement1 = con.prepareStatement("SELECT maxWeight FROM TransportDocs WHERE ID = ?;");
                statement1.setInt(1, docId);
                ResultSet res = statement1.executeQuery();
                if (res.next()) {
                    con.commit();
                    statement1.close();
                    double maxWeight = res.getDouble("maxWeight");
                    if(maxWeight != -1){
                        PreparedStatement statement2 = con.prepareStatement("UPDATE TransportDocs SET status = ? WHERE ID = ? ;");
                        statement2.setString(1,"SUCCESS");
                        statement2.setInt(2,docId);
                        int rowNum = statement2.executeUpdate();
                        if (rowNum != 0) {
                            statement2.close();;
                            con.commit();
                            con.close();
                        } else {
                            con.rollback();
                            statement2.close();
                            con.close();
                            System.err.println("Update failed");
                        }
                    }
                    con.close();
                } else {
                    con.rollback();
                    statement1.close();
                    con.close();
                }
            }
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public int getMaxdocsId() {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT max(ID)  FROM TransportDoc");
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    int maxId = result.getInt(1);
                    con.close();
                    statement.close();
                    return maxId;
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

    public void addNewDoc(DTO_TransportDoc d) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("INSERT INTO TransportDocs VALUES (?,?,?,?,?,?,?,?);");
                statement.setInt(1, d.getId());
                statement.setString(2, d.getDate());
                statement.setString(3, d.getTruckId());
                statement.setInt(4, d.getDriverId());
                statement.setString(5, d.getStringStatus());
                statement.setDouble(6, d.getFinalWeight());
                statement.setInt(7, d.getArea());
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
        } 
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        addDocSite(d.getId(), d.getStores(), "DocStores");
        addDocSite(d.getId(),d.getSuppliers(),"DocSuppliers");
    }


    private void addDocSite(int docId, List<Integer> sites, String tableName) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                Iterator<Integer> itr1 = sites.iterator();
                while (itr1.hasNext()) {
                    int siteId = itr1.next();
                        PreparedStatement statement = con.prepareStatement("INSERT INTO ? VALUES (?,?);");
                        statement.setString(1, tableName);
                        statement.setInt(2,siteId);
                        statement.setInt(3, docId);
                        int rowNum = statement.executeUpdate();
                        if (rowNum != 0) {
                            con.commit();
                            statement.close();
                        }
                        else {
                            con.rollback();
                            con.close();
                            System.err.println("Problem in inserting items");
                            break;
                        }
                    }
                con.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}