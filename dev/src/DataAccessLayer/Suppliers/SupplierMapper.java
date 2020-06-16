package src.DataAccessLayer.Suppliers;
import java.sql.*;
import DataAccessLayer.Suppliers.DTO.SupplierDTO;
import org.sqlite.SQLiteConfig;

import java.util.*;

public class SupplierMapper {

    private static Connection conn;

    public static boolean tryOpen() {
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db", config.toProperties());
            conn.setAutoCommit(false);

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

    public boolean addSupplier(SupplierDTO supp) {
        try {
            if (tryOpen()) {
                //add supplier to "Suppliers"
                PreparedStatement st = conn.prepareStatement("INSERT INTO Suppliers VALUES (?,?,?,?,?,?,?);");
                st.setInt(1, supp.getId());
                st.setString(2, supp.getName());
                st.setString(3, supp.getPhoneNumber());
                st.setInt(4, supp.getBankAccount());
                st.setString(5, supp.getPayment());
                st.setString(6, supp.getSupplySchedule());
                st.setString(7, supp.getSupplyLocation());
                int rowNum = st.executeUpdate();
                if (rowNum != 0) {
                    st = conn.prepareStatement("SELECT counter FROM Counters WHERE name = ?;");
                    st.setString(1, "suppliers");
                    ResultSet res = st.executeQuery();
                    int suppCount = 0;
                    if (res.next())
                        suppCount = res.getInt("counter");
                    //increase supplier's counter in "Counters"
                    st = conn.prepareStatement("UPDATE Counters SET counter = ? WHERE name = ?;");
                    st.setInt(1, suppCount + 1);
                    st.setString(2, "suppliers");
                    st.executeUpdate();
                    //add supplier's agreement to "Agreements"
                    st = conn.prepareStatement("INSERT INTO Agreements VALUES (?,?,?);");
                    st.setInt(1, supp.getId());
                    st.setInt(2, 0);
                    st.setInt(3, supp.getId());
                    st.executeUpdate();
                    conn.commit();
                    conn.close();
                    st.close();
                    return true;
                } else {
                    conn.rollback();
                    conn.close();
                    st.close();
                }
            } else return false;
        } catch (Exception e) {
            tryClose();
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;
    }

    public boolean deleteSupplier(int suppId) {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("DELETE FROM Suppliers WHERE id= (?);");
                st.setInt(1, suppId);
                int rowNum = st.executeUpdate();
                st.close();
                if (rowNum != 0) {
                    conn.commit();
                    conn.close();
                    return true;
                } else {
                    conn.rollback();
                    conn.close();
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

    public static boolean findSupplier(int id) {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT * FROM Suppliers WHERE id = ?;");
                st.setInt(1, id);
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

    public boolean addItemToSupplier(int suppId, int itemId, int itemLocalId) {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("INSERT INTO SupplierItems VALUES (?,?,?);");
                st.setInt(1, suppId);
                st.setInt(2, itemId);
                st.setInt(3, itemLocalId);
                int rowNum = st.executeUpdate();
                if (rowNum != 0) {
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

    public int getItemsListSize(int suppId) {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT count(*) as num FROM SupplierItems WHERE SupplierId = ?;");
                st.setInt(1, suppId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    int ans = res.getInt("num");
                    conn.close();
                    st.close();
                    return ans;
                }
                conn.close();
                return 0;
            } else return 0;
        } catch (Exception e) {
            tryClose();
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return 0;
        }
    }

    public static List<String> getSupplierItemsNames(int suppId) {
        List<String> supplierItemsId = new LinkedList<>();
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT Items.itemName  FROM SupplierItems JOIN Items ON SupplierItems.ItemId =Items.itemId WHERE SupplierItems.SupplierId= ?  ;");
                st.setInt(1, suppId);
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    supplierItemsId.add(res.getString("itemName"));
                }
                st.close();
                conn.close();
                return supplierItemsId;
            } else return null;
        } catch (Exception e) {
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public List<Integer> getSupplierItemsId(int suppId) {
        List<Integer> supplierItemsId = new LinkedList<>();
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT ItemId  FROM SupplierItems WHERE SupplierItems.SupplierId= ?  ;");
                st.setInt(1, suppId);
                ResultSet res = st.executeQuery();

                while (res.next()) {
                    supplierItemsId.add(res.getInt("itemId"));
                }
                st.close();
                conn.close();
                return supplierItemsId;
            } else return null;
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public boolean addItemToAgreement(Integer suppId, Integer itemId, Double cost) {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("INSERT INTO ItemsInAgreement VALUES (?,?,?);");
                st.setInt(1, suppId);
                st.setInt(2, itemId);
                st.setDouble(3, cost);
                int rowNum = st.executeUpdate();
                st.close();
                if (rowNum != 0) {
                    conn.commit();
                    conn.close();
                } else {
                    conn.rollback();
                    conn.close();
                    return false;
                }
                return true;
            } else return false;
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public boolean setItemPrice(int suppId, int itemId, double newPrice) {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("UPDATE ItemsInAgreement SET price = ? WHERE agreementId = ? AND itemId= ?;");
                st.setDouble(1, newPrice);
                st.setInt(2, suppId);
                st.setInt(3, itemId);
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

    public boolean validateItemId(int suppId, int itemId) {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT * FROM ItemsInAgreement WHERE agreementId = ? AND itemId = ?;");
                st.setInt(1, suppId);
                st.setInt(2, itemId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    conn.close();
                    st.close();
                    return true;
                } else {
                    conn.close();
                    st.close();
                    return false;
                }
            } else {
                conn.close();
                return false;
            }
        } catch (Exception e) {
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return false;
        }
    }

    public int getSupplierSize() {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT count(*) as num FROM Suppliers;");
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    int ans = res.getInt("num");
                    conn.close();
                    st.close();
                    return ans;
                }
                conn.close();
                return 0;
            } else return 0;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return 0;
        }

    }

    public LinkedHashMap<Integer, Double> showSuppItems(int suppId) {
        LinkedHashMap<Integer, Double> items = new LinkedHashMap<>();
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT itemId, price FROM ItemsInAgreement WHERE agreementId = ?;");
                st.setInt(1, suppId);
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    items.put(res.getInt("itemId"), res.getDouble("price"));
                }
                conn.commit();
                conn.close();
                st.close();
                tryClose();
            }
        } catch (Exception e) {
            tryClose();
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        return items;
    }

    public List<Integer> getSupplierIds() {
        List<Integer> supplierId = new LinkedList<>();
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT id  FROM Suppliers ;");
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    supplierId.add(res.getInt("id"));
                }
                st.close();
                conn.close();
                return supplierId;
            } else return null;
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }


    }

    public boolean checkIfBillExists(int BillId) {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT * FROM BillsOfQuantities WHERE billId = ?;");
                st.setInt(1, BillId);
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

    public boolean checkInBillForDiscount(int BillId, Integer itemId, Integer amount) {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT * FROM ItemsInBills WHERE billId = ? AND quantity<= ? AND itemId = ?;");
                st.setInt(1, BillId);
                st.setInt(2, amount);
                st.setInt(3, itemId);
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

    public double getDiscount(int billId, Integer itemId) {
        Double discount = 0.0;
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT discount  FROM ItemsInBills WHERE itemid =? AND billId = ? ;");
                st.setInt(1, itemId);
                st.setInt(2, billId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    discount = (res.getDouble("discount"));
                }
                st.close();
                conn.close();
                return discount;
            } else return 0.0;
        } catch (Exception e) {
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return 0.0;
        }

    }

    public double getPriceOfItem(int agreementId, Integer itemId) {
        Double cost = 0.0;
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT price  FROM ItemsInAgreement WHERE itemid =? AND agreementId = ? ;");
                st.setInt(1, itemId);
                st.setInt(2, agreementId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    cost = (res.getDouble("price"));
                }
                st.close();
                conn.close();
                return cost;
            } else return 0.0;
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return 0.0;
        }

    }

    public int getSuppliersCounter() {
        int counter = 0;
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT counter  FROM Counters WHERE name = ?;");
                st.setString(1, "suppliers");
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    counter = res.getInt("counter");
                }
                st.close();
                conn.close();
                return counter;
            }
        } catch (Exception e) {
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
        }
        return counter;
    }

    public List<Object> getSuppDetails(int bestSuppId) {
        List<Object> supplierDetails = new LinkedList<>();
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT *  FROM Suppliers WHERE id= ?  ;");
                st.setInt(1, bestSuppId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    supplierDetails.add(res.getInt("id"));
                    supplierDetails.add(res.getString("name"));
                    supplierDetails.add(res.getString("phoneNum"));
                    supplierDetails.add(res.getString("supplyLocation"));
                }
                st.close();
                conn.close();
                return supplierDetails;
            } else return null;
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public void createBillOfQuantities(int suppId) {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("INSERT INTO BillsOfQuantities VALUES (?,?);");
                st.setInt(1, suppId);
                st.setInt(2, suppId);
                st.executeUpdate();

                st = conn.prepareStatement("UPDATE Agreements SET billId = ? WHERE suppId = ?;");
                st.setInt(1, suppId);
                st.setInt(2, suppId);
                st.executeUpdate();

                if (st.executeUpdate() != 0) {
                    conn.commit();
                    conn.close();
                    st.close();
                    return;
                } else {
                    conn.close();
                    st.close();
                }
            } else return;
        } catch (Exception e) {
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
        }
    }

    public boolean validateItemIdInBill(int suppId, int itemId) {
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT * FROM ItemsInBills WHERE billId = ? AND itemId = ?;");
                st.setInt(1, suppId);
                st.setInt(2, itemId);
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

    public List<List<Object>> getAllSuppliers() {
        List<List<Object>> suppliers = new LinkedList<>();
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT *  FROM Suppliers ;");
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    List<Object> temp = new LinkedList<>();
                    temp.add(res.getInt("id"));
                    temp.add(res.getString("name"));
                    temp.add(res.getString("phoneNum"));
                    temp.add(res.getString("payment"));
                    temp.add(res.getString("supplySchedule"));
                    temp.add(res.getString("supplyLocation"));
                    suppliers.add(temp);
                }
                st.close();
                conn.close();
                return suppliers;
            } else return null;
        } catch (Exception e) {
           // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
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
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
        }
        return localItemId;
    }

    public boolean addSupplierDays(int suppId, int[] daysInt) {
        int counter = 0;
        try {
            if (tryOpen()) {
                for (int i = 0; i < daysInt.length; i++) {
                    PreparedStatement st = conn.prepareStatement("INSERT INTO SupplierDays VALUES (?,?);");
                    st.setInt(1, suppId);
                    st.setInt(2, daysInt[i]);
                    int rowNum = st.executeUpdate();
                    if (rowNum != 0) {
                        st.close();
                        counter++;
                    } else {
                        conn.rollback();
                        conn.close();
                        st.close();
                    }
                }
                if (counter == daysInt.length) {
                    conn.commit();
                    conn.close();
                    return true;
                } else {
                    conn.rollback();
                    conn.close();
                    return false;
                }
            } else return false;
        } catch (Exception e) {
            tryClose();
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public String getSupplierType(int suppId) {
            String type ="";
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT supplySchedule FROM Suppliers WHERE id = ?;");
                st.setInt(1, suppId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    type = res.getString("supplySchedule");
                }
                st.close();
                conn.close();
                return type;
            }
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
        }
        return type;
    }

    public List<Integer> getSupplyDays(int suppId) {
        List<Integer> days = new LinkedList();
        try {
            if (tryOpen()) {
                PreparedStatement st = conn.prepareStatement("SELECT day FROM SupplierDays WHERE suppId = ?;");
                st.setInt(1, suppId);
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    days.add(res.getInt("day"));
                }
                st.close();
                conn.close();
                return days;
            }
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
        }
        return days;
    }

    public List<Integer> getLocalItemsIds(int suppId,List<Integer> temp) {
        List<Integer> ids = new LinkedList();
        try {
            if (tryOpen()) {
                for (int i = 0; i < temp.size(); i++) {
                    PreparedStatement st = conn.prepareStatement("SELECT localItemId FROM SupplierItems WHERE SupplierId = ? AND ItemId =?;");
                    st.setInt(1, suppId);
                    st.setInt(2, temp.get(i));
                    ResultSet res = st.executeQuery();
                    if (res.next()) {
                        ids.add(res.getInt("localItemId"));
                    }
                    st.close();

                }
                conn.close();
                return ids;
            }
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
        }
        return ids;

    }
}