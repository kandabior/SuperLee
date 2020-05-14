package DataAccessLayer.Transport;

import BusinessLayer.TransportModule.DTO.DTO_Supplier;
import BusinessLayer.TransportModule.DTO.DTO_Truck;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TrucksMapper {
    private Connection con;
    public List<String> getTrucksString() {
        List<String> trucks = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Trucks;");
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    DTO_Truck t = new DTO_Truck(result.getString(1), result.getString(2),result.getDouble(3), result.getDouble(4));
                    trucks.add(t.toString());
                }
                statement.close();
                con.close();
                return trucks;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public boolean isUniqueTruck(String TruckId) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Trucks WHERE id = ?;");
                statement.setString(1, TruckId);
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

    public void addTruck(DTO_Truck t) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("INSERT INTO Trucks VALUES (?,?,?,?);");
                statement.setString(1, t.getId());
                statement.setString(2, t.getModel());
                statement.setDouble(3, t.getWeight());
                statement.setDouble(4, t.getMaxWeight());
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

    public void removeTruck(String id) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement st = con.prepareStatement("DELETE FROM Trucks WHERE ID= (?);");
                st.setString(1, id);
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


}
