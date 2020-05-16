package DataAccessLayer.Transport;

import BusinessLayer.TransportModule.Store;
import DataAccessLayer.Transport.DTO.DTO_Store;

import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StoresMapper {
    private Connection con = null;

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

    public List<DTO_Store> getAllStores() {
        List<DTO_Store> allStores = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Stores;");
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    DTO_Store s = new DTO_Store(result.getInt("ID"), result.getString("address"),
                            result.getString("phoneNumber"), result.getString("contactName"), result.getInt("area"));
                    allStores.add(s);
                }
                statement.close();
                con.close();
                return allStores;

            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }
}
