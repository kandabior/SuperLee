package DataAccessLayer.Employee;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShiftMapper {

    private Connection con;

    public List<Shift_DTO> loadBranch(int branch) {
        List<Shift_DTO> shifts = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Shifts where Shifts.branch =(?);");
                statement.setInt(1, branch);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    Shift_DTO t = new Shift_DTO(result.getString(1), result.getString(2),result.getString(3), result.getString(4),result.getInt(5));
                    shifts.add(t);
                }
                statement.close();
                con.close();
                return shifts;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public List<String[]> loadShiftManager(int branch) {
        List<String[]> shifts = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM ShiftsManager where Shifts.branch =(?);");
                statement.setInt(1, branch);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    String[] s = {result.getString(1), result.getString(2),result.getString(3), String.valueOf(result.getInt(4))};
                    shifts.add(s);
                }
                statement.close();
                con.close();
                return shifts;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    public void deleteShift(String date , String shiftType, int branch) {
        deleteShift("Shifts",date,shiftType,branch);
    }

    public void deleteSM(String date, String shiftType , int branch) {
        deleteShift("ShiftsManager",date,shiftType,branch);
    }

    void deleteShift(String table, String date, String shiftType, int branch){
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement st = con.prepareStatement("DELETE FROM (?) WHERE date = (?) AND shiftType= (?) AND branch = (?);");
                st.setString(1, table);
                st.setString(2, date);
                st.setString(3, shiftType);
                st.setInt(3, branch);
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

    public void add(List<Shift_DTO> shift) {
        for (Shift_DTO s:shift) {
            try {
                if (tryOpen()) {
                    Class.forName("org.sqlite.JDBC");
                    con.setAutoCommit(false);
                    PreparedStatement statement = con.prepareStatement("INSERT INTO Shifts VALUES (?,?,?,?,?);");
                    statement.setString(1,s.getDate() );
                    statement.setString(2, s.getShiftType());
                    statement.setString(3, s.getRole());
                    statement.setString(4, s.getName());
                    statement.setInt(4, s.getBranch());
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
    }

    public void addSM(String date, String shiftType, String employeeManager, int currentBranch) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("INSERT INTO ShiftsManager VALUES (?,?,?,?);");
                statement.setString(1,date);
                statement.setString(2, shiftType);
                statement.setString(3, employeeManager);
                statement.setInt(4, currentBranch);
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

    public void addRequirements(String day, String shiftType,String role, int quantity,int branch) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("INSERT INTO Requirments VALUES (?,?,?,?,?);");
                statement.setString(1,day);
                statement.setString(2, shiftType);
                statement.setString(3, role);
                statement.setInt(4, quantity);
                statement.setInt(4, branch);
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

    public void deleteReq(String day, String shiftType,int branch) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement st = con.prepareStatement("DELETE FROM Requirments WHERE day = (?) AND shiftType = (?) AND branch = (?);");
                st.setString(1, day);
                st.setString(2, shiftType);
                st.setInt(3, branch);
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

    public List<Integer> getStoresWithStoreKeeperAtDate(Date date) {
        List<Integer> Stores = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT distinct branch FROM Shifts where Shifts.role =(?);");
                statement.setString(1, "storekeeper");
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    Stores.add(result.getInt(1));
                }
                statement.close();
                con.close();
                return Stores;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
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
