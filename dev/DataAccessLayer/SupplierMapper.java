package DataAccessLayer;
import java.sql.*;
import DTO.SupplierDTO;
import java.util.*;

public class SupplierMapper {

    private static Connection conn;

    public boolean addSupplier(SupplierDTO supp) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
                    st = conn.prepareStatement("INSERT INTO Agreements VALUES (?,?);");
                    st.setInt(1, supp.getId());
                    st.setInt(2, 0);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;
    }

    public static boolean deleteSupplier(int suppId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("DELETE FROM Suppliers WHERE id= (?);");
                st.setInt(1, suppId);
                int rowNum = st.executeUpdate();
                st.close();
                int billId = getBillId(suppId);
                if (rowNum != 0 && (billId != -1) && deleteBill((billId))) {
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;
    }

    private static int getBillId(int agreementId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT billId  FROM Agreements WHERE suppId = ?  ;");
                st.setInt(1, agreementId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    st.close();
                    conn.close();
                    return res.getInt("billId");
                }
                st.close();
                conn.close();
                return -1;
            } else return -1;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return -1;
        }

    }

    public static boolean deleteBill(int billId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("DELETE FROM BillsOfQuantities WHERE id= (?);");
                st.setInt(1, billId);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;
    }

    public static boolean findSupplier(int id) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;
    }

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

    public void addItemToSupplier(int suppId, int itemId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("INSERT INTO SupplierItems VALUES (?,?);");
                st.setInt(1, suppId);
                st.setInt(2, itemId);
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

    public int getItemsListSize(int suppId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return 0;
        }
    }

    public static List<String> getSupplierItemsNames(int suppId) {
        List<String> supplierItemsId = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public List<Integer> getSupplierItemsId(int suppId) {
        List<Integer> supplierItemsId = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public boolean addItemToAgreement(Integer suppId, Integer itemId, Double cost) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public boolean setItemPrice(int suppId, int itemId, double newPrice) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public boolean validateItemId(int suppId, int itemId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT * FROM ItemsInAgreement WHERE agreementId = ? AND itemId = ?;");
                st.setInt(1, suppId);
                st.setInt(2, itemId);
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    conn.close();
                    st.close();
                    return true;
                }
                else {
                    conn.close();
                    st.close();
                    return false;
                }
            } else {
                conn.close();
                return false;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return false;
        }
    }

    public int getSupplierSize() {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            }
            else return 0;
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return 0;
        }

    }

    public LinkedHashMap<Integer, Double> showSuppItems(int suppId) {
        LinkedHashMap<Integer, Double> items = new LinkedHashMap<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        return items;
    }

    public List<Integer> getSupplierIds() {
        List<Integer> supplierId = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT id  FROM Suppliers ;");
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    supplierId.add(res.getInt("id"));
                }
                st.close();
                conn.close();
                return supplierId;
            }
            else return null;
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }


    }

    public boolean checkIfBillExists(int BillId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;
    }

    public boolean checkInBillForDiscount(int BillId, Integer itemId, Integer amount) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;


    }

    public double getDiscount(int billId, Integer itemId) {
        Double discount = 0.0;
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            }
            else return 0.0;
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return 0.0;
        }

    }

    public double getPriceOfItem(int agreementId, Integer itemId) {
        Double cost = 0.0;
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            }
            else return 0.0;
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return 0.0;
        }

    }

    public int getSuppliersCounter() {
        int counter = 0;
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
        }
        return counter;
    }

    public List<Object> getSuppDetails(int bestSuppId) {
        List<Object> supplierDetails = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public void createBillOfQuantities(int suppId) {
        try{
            if(tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("INSERT INTO BillsOfQuantities VALUES (?);");
                st.setInt(1, suppId);
                st.executeUpdate();

                st = conn.prepareStatement("UPDATE Agreements SET billId = ? WHERE suppId = ?;");
                st.setInt(1, suppId);
                st.setInt(2, suppId);
                st.executeUpdate();

                if(st.executeUpdate() != 0) {
                    conn.commit();
                    conn.close();
                    st.close();
                    return;
                }
                else {
                    conn.close();
                    st.close();
                }
            }

            else return;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
        }
    }

    public boolean validateItemIdInBill(int suppId, int itemId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;
    }

    public List<List<Object>> getAllSuppliers() {
        List<List<Object>> suppliers = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT *  FROM Suppliers ;");
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    List<Object> temp= new LinkedList<>();
                    temp.add(res.getInt("id"));
                    temp.add(res.getString("name"));
                    temp.add(res.getString("phoneNum"));
                    temp.add(res.getString("supplyLocation"));
                    suppliers.add(temp);
                }
                st.close();
                conn.close();
                return suppliers;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }



    }
}