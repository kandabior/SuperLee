package DataAccessLayer.Transport;

import DataAccessLayer.DTO.DTO_Supplier;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SuppliersMapper {
    private Connection con;

    public void addSupplier(DTO_Supplier s) {
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
    }


    public int getSupplierId() {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT max(ID)  FROM Suppliers;");
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

    public void removeSupplier(int id) {
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
    }

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


    public List<String> getSuppliersString() {
        List<String> suppliers = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Suppliers;");
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    DTO_Supplier s = new DTO_Supplier(result.getInt(1), result.getString(2),result.getString(3), result.getString(4), result.getInt(5));
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

    public void updateSupplier(int supplierId) {



    }
}