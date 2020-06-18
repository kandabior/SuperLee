package src.DataAccessLayer.Transport;

import src.DataAccessLayer.Transport.DTO.DTO_Store;
import src.DataAccessLayer.Transport.DTO.DTO_Supplier;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SuppliersMapper {
    private Connection con;

   /* public void addSupplier(DTO_Supplier s) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("INSERT INTO Suppliers VALUES (?,?,?,?,?);");
                statement.setInt(1, s.getId());
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
        }
    }*/


    public int getSupplierId() {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT max(id)  FROM Suppliers;");
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

    public boolean isExist(int supplierId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Suppliers WHERE id = ?;");
                statement.setInt(1, supplierId);
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

    /*public void removeSupplier(int id) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement st = con.prepareStatement("DELETE FROM Suppliers WHERE id= (?);");
                st.setInt(1, id);
                int rowNum = st.executeUpdate();
                st.close();
                if (rowNum != 0) {
                    con.commit();
                    con.close();
                } else {
                    con.rollback();
                    con.close();
                }
            }
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }*/

    private boolean tryOpen() {
        try {
            String url = "jdbc:sqlite:dev\\EOEDdatabase.db";
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


    public List<String> getSuppliersString() {
        List<String> suppliers = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT id,supplyLocation,phoneNum,name FROM Suppliers;");
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    DTO_Supplier s = new DTO_Supplier(result.getInt(1), result.getString(2),result.getString(3), result.getString(4), 0);
                    suppliers.add(s.toString());
                }
                statement.close();
                con.close();
                return suppliers;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

   /* public void updateSupplier(int supplierId, String address, String phoneNumber, String contactName) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);

                PreparedStatement statement = con.prepareStatement("UPDATE Suppliers SET ID = ?, address = ?, phoneNumber = ?, contactName = ?  WHERE ID = ? ;");
                statement.setInt(1, supplierId);
                statement.setString(2, address);
                statement.setString(3, phoneNumber);
                statement.setString(4, contactName);
                statement.setInt(5, supplierId);
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

    }*/

    /*public boolean validArea(int area) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Suppliers WHERE area = ?;");
                statement.setInt(1, area);
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
    }*/

    /*public List<DTO_Supplier> getSupplierInArea(int area) {
        List<DTO_Supplier> SuppliersInArea = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Suppliers WHERE area = ?;");
                statement.setInt(1,area);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    DTO_Supplier s = new DTO_Supplier(result.getInt("ID"), result.getString("address"),
                            result.getString("phoneNumber"), result.getString("contactName"), result.getInt("area"));
                    SuppliersInArea.add(s);
                }
                statement.close();
                con.close();
                return SuppliersInArea;

            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }*/
}
