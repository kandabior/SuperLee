package DataAccessLayer;

import java.sql.*;

public class OrderMapper {
    private static Connection conn;

    public int getSize() {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                conn.setAutoCommit(false);
                PreparedStatement st = conn.prepareStatement("SELECT count(*) as num FROM Orders;");
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    int ans = res.getInt("num");
                    st.close();
                    conn.close();
                    return ans;
                }
                st.close();
                conn.close();
                return 0;
            }
            else return 0;
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return 0;
        }
    }

    public static boolean tryOpen()
    {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    }
