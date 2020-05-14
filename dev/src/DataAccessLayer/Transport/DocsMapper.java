package DataAccessLayer.Transport;

import java.sql.*;

public class DocsMapper {
    private Connection con;

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
