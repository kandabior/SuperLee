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
                statement.setString(2, "PENDING");
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
                statement.setString(2, "PENDING");
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
        List<Map<Integer, Integer>> items = new LinkedList<>();

        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM TransportDocs WHERE status = ?;");
                statement.setString(1, docStatus);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    int transportId = result.getInt("ID");
                    suppliers = getTransportsSuppliers(transportId);
                    stores = getTransportsStores(transportId);
                    items = new LinkedList<>();
                    for (int i = 0; i < stores.size(); i++) {
                        items.add(getTransportsItems(transportId, stores.get(i)));
                    }
                    DTO_TransportDoc s = new DTO_TransportDoc(transportId, result.getInt("area"),
                            result.getString("date"), result.getString("truckId"), result.getInt("driverId"),
                            result.getString("driverName"), stores, suppliers, items, result.getString("status"), result.getDouble("finalWeight"));
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
        Map<Integer, Integer> storeItems = new HashMap<>();
        try {
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Items WHERE DocId = ? AND SID = ?;");
                statement.setInt(1, transportId);
                statement.setInt(2, storeId);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    storeItems.put(result.getInt("item"), result.getInt("quantity"));
                }
                statement.close();
                return storeItems;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }


    /* public List<Integer> getTransportsStores( int transportId) {
         List<Integer> sites = new LinkedList<>();
         try {
             if (tryOpen()) {
                 Class.forName("org.sqlite.JDBC");
                 con.setAutoCommit(false);
                 PreparedStatement statement = con.prepareStatement("SELECT SID FROM DocStores WHERE DocID = ?;");
                 statement.setInt(1,transportId);
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
     }*/
    public List<Integer> getTransportsStores(int transportId) {
        List<Integer> sites = new LinkedList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            con.setAutoCommit(false);
            PreparedStatement statement = con.prepareStatement("SELECT SID FROM DocStores WHERE DocID = ?;");
            statement.setInt(1, transportId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                sites.add(result.getInt("SID"));
            }
            statement.close();
            return sites;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    public List<Integer> getTransportsSuppliers(int transportId) {
        List<Integer> sites = new LinkedList<>();
        try {
           /* if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);*/
            PreparedStatement statement = con.prepareStatement("SELECT SID FROM DocSuppliers WHERE DocID = ?;");
            statement.setInt(1, transportId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                sites.add(result.getInt("SID"));
            }
            statement.close();
            return sites;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    public boolean validTransport(int docId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM TransportDocs WHERE id = ? AND status = ? ");
                statement.setInt(1, docId);
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

    public DTO_TransportDoc getTransportDoc(int docId) {
        List<Integer> stores = new LinkedList<>();
        List<Integer> suppliers = new LinkedList<>();
        List<Map<Integer, Integer>> items = new LinkedList<>();

        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM TransportDocs WHERE ID = ? AND status = ?;");
                statement.setInt(1, docId);
                statement.setString(2, "PENDING");
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    suppliers = getTransportsSuppliers(docId);
                    stores = getTransportsStores(docId);
                    for (int i = 0; i < stores.size(); i++) {
                        items.add(getTransportsItems(docId, stores.get(i)));
                    }
                    DTO_TransportDoc s = new DTO_TransportDoc(docId, result.getInt("area"),
                            result.getString("date"), result.getString("truckId"), result.getInt("driverId"),
                            result.getString("driverName"), stores, suppliers, items, "PENDING", result.getDouble("finalWeight"));
                    statement.close();
                    con.close();
                    return s;
                } else
                    return null;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public void updateTransportDoc(double finalWeight, String status, int docId) {
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
                    statement.close();
                    ;
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
                Iterator<Map<Integer, Integer>> itr2 = allItems.iterator();
                int i = 0;
                while (itr1.hasNext()) {
                    int storeId = itr1.next();
                    Map<Integer, Integer> storesItems = itr2.next();
                    for (Map.Entry<Integer, Integer> entry : storesItems.entrySet()) {
                        /*System.out.println("Key = " + entry.getKey() +
                                ", Value = " + entry.getValue());*/
                        PreparedStatement statement1 = con.prepareStatement("INSERT INTO Items VALUES (?,?,?,?);");
                        statement1.setInt(1, docId);
                        statement1.setInt(2, entry.getKey());
                        statement1.setInt(3, entry.getValue());
                        statement1.setInt(4, storeId);
                        int rowNum = statement1.executeUpdate();
                        if (rowNum != 0) {
                            con.commit();
                            statement1.close();
                        } else {
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
                PreparedStatement statement1 = con.prepareStatement("SELECT finalWeight FROM TransportDocs WHERE ID = ?;");
                statement1.setInt(1, docId);
                ResultSet res = statement1.executeQuery();
                if (res.next()) {
                    double finalWeight = res.getDouble("finalWeight");
                    statement1.close();
                    if (finalWeight != -1) {
                        PreparedStatement statement2 = con.prepareStatement("UPDATE TransportDocs SET status = ? WHERE ID = ? ;");
                        statement2.setString(1, "SUCCESS");
                        statement2.setInt(2, docId);
                        int rowNum = statement2.executeUpdate();
                        if (rowNum != 0) {
                            con.commit();
                            statement2.close();
                        } else {
                            con.rollback();
                            statement2.close();
                            System.err.println("Update failed");
                        }
                    }
                } else {
                    con.rollback();
                    statement1.close();
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
                PreparedStatement statement = con.prepareStatement("SELECT max(ID)  FROM TransportDocs");
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
                statement.setString(5, d.getDriverName());
                statement.setString(6, d.getStringStatus());
                statement.setDouble(7, d.getFinalWeight());
                statement.setInt(8, d.getArea());
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
        }
        addDocStore(d.getId(), d.getStores());
        addDocSuppliers(d.getId(), d.getSuppliers());
    }


    private void addDocStore(int docId, List<Integer> sites) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                Iterator<Integer> itr1 = sites.iterator();
                while (itr1.hasNext()) {
                    int siteId = itr1.next();
                    PreparedStatement statement = con.prepareStatement("INSERT INTO DocStores VALUES (?,?);");
                    statement.setInt(1, siteId);
                    statement.setInt(2, docId);
                    int rowNum = statement.executeUpdate();
                    if (rowNum != 0) {
                        con.commit();
                        statement.close();
                    } else {
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

    private void addDocSuppliers(int docId, List<Integer> sites) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                Iterator<Integer> itr1 = sites.iterator();
                while (itr1.hasNext()) {
                    int siteId = itr1.next();
                    PreparedStatement statement = con.prepareStatement("INSERT INTO DocSuppliers VALUES (?,?);");
                    statement.setInt(1, siteId);
                    statement.setInt(2, docId);
                    int rowNum = statement.executeUpdate();
                    if (rowNum != 0) {
                        con.commit();
                        statement.close();
                    } else {
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

    public List<Integer> getTStores(int docId) {
        List<Integer> sites = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                sites = getTransportsStores(docId);
                con.close();
                return sites;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public DTO_TransportDoc getFailDoc(int docId) {
        List<Integer> stores = new LinkedList<>();
        List<Integer> suppliers = new LinkedList<>();
        List<Map<Integer, Integer>> items = new LinkedList<>();

        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM TransportDocs WHERE ID = ? AND status = ?;");
                statement.setInt(1, docId);
                statement.setString(2, "FAIL");
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    suppliers = getTransportsSuppliers(docId);
                    stores = getTransportsStores(docId);
                    for (int i = 0; i < stores.size(); i++) {
                        items.add(getTransportsItems(docId, stores.get(i)));
                    }
                    DTO_TransportDoc s = new DTO_TransportDoc(docId, result.getInt("area"),
                            result.getString("date"), result.getString("truckId"), result.getInt("driverId"),
                            result.getString("driverName"), stores, suppliers, items, "PENDING", result.getDouble("finalWeight"));
                    statement.close();
                    con.close();
                    return s;
                } else
                    return null;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }
}