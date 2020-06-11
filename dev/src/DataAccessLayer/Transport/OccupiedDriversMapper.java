package src.DataAccessLayer.Transport;

import src.DataAccessLayer.Transport.DTO.DTO_OccupiedDriver;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OccupiedDriversMapper {
    private Connection con;

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

    public List<DTO_OccupiedDriver> getOccupiedDriver() {
        List<DTO_OccupiedDriver> output = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT DISTINCT EID FROM OccupiedDrivers;");
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    DTO_OccupiedDriver od = new DTO_OccupiedDriver(result.getInt(1));
                    output.add(od);
                }
                statement.close();
                con.close();
                return output;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public List<String> getOccupiedDriverDates(int id) {
        List<String> output = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT date FROM OccupiedDrivers WHERE EID = ?;");
                statement.setInt(1, id);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    output.add(result.getString(1));
                }
                statement.close();
                con.close();
                return output;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public void addDateToOccupiedDriver(int id, String date) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("INSERT INTO OccupiedDrivers VALUES (?,?);");
                statement.setInt(1, id);
                statement.setString(2, date);
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

    public void removeDriverAtDate(int driverId, String date) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement st = con.prepareStatement("DELETE FROM OccupiedDrivers WHERE DATE = (?) AND EID= (?) ;");
                st.setString(1, date);
                st.setInt(2, driverId);
                int rowNum = st.executeUpdate();

                if (rowNum != 0) {
                    con.commit();
                    con.close();
                } else {
                    con.rollback();
                    con.close();
                }
                st.close();
            }
        } catch (Exception e) {
            tryClose();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
