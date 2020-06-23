package src.DataAccessLayer.Employee;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ShiftMapper {

    private Connection con;

    public List<Shift_DTO> loadBranch(int branch) {
        List<Shift_DTO> shifts = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Shifts where branch =(?);");
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
                PreparedStatement statement = con.prepareStatement("SELECT * FROM ShiftsManager where branch =(?);");
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


    public List<String[]> loadReq(int branch) {
        List<String[]> req = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT * FROM Requirments where branch =(?);");
                statement.setInt(1, branch);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    String[] s = {result.getString(1), result.getString(2),result.getString(3), String.valueOf(result.getInt(4)),String.valueOf(result.getInt(5))};
                    req.add(s);
                }
                statement.close();
                con.close();
                return req;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

        public void deleteShift(String date , String shiftType, int branch) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement st = con.prepareStatement("DELETE FROM Shifts WHERE date = (?) AND shiftType= (?) AND branch = (?);");
                st.setString(1, date);
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

    public void deleteSM(String date, String shiftType , int branch) {
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement st = con.prepareStatement("DELETE FROM ShiftsManager WHERE date = (?) AND shiftType= (?) AND branch = (?);");
                st.setString(1, date);
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

    public void add(List<Shift_DTO> shift) {

            try {
                if (tryOpen()) {
                    Class.forName("org.sqlite.JDBC");
                    con.setAutoCommit(false);
                    for (Shift_DTO s : shift) {
                        PreparedStatement statement = con.prepareStatement("INSERT INTO Shifts VALUES (?,?,?,?,?);");
                        statement.setString(1, s.getDate());
                        statement.setString(2, s.getShiftType());
                        statement.setString(3, s.getRole());
                        statement.setString(4, s.getName());
                        statement.setInt(5, s.getBranch());
                        int rowNum = statement.executeUpdate();
                        if (rowNum != 0) {
                            con.commit();
                            statement.close();
                        } else {
                            con.rollback();
                            con.close();
                        }
                    }
                    con.close();
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
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
                statement.setInt(5, branch);
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
                PreparedStatement statement = con.prepareStatement("SELECT distinct branch FROM Shifts where role =(?) and shiftType =(?) and date = (?);");
                statement.setString(1, "storekeeper");
                statement.setString(    2, "Morning");
                statement.setString(3, dateToString(date));

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

    public List<String> getDriversAvailableAtDate(String date) {
        List <String> drM = getDriversMaAvailableAtDate(date);
        List<String> drivers = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT EID FROM Shifts where date =(?) and branch = (?) ;");
                statement.setString(1, date);
                statement.setInt(2,0);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    drivers.add(result.getString(1));
                }
                statement.close();
                con.close();
                return distDriver(drM,drivers);
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }

    }

    private List<String> getDriversMaAvailableAtDate(String date) {
        List<String> drMa = new LinkedList<>();
        try {
            if (tryOpen()) {
                Class.forName("org.sqlite.JDBC");
                con.setAutoCommit(false);
                PreparedStatement statement = con.prepareStatement("SELECT managerEID FROM ShiftsManager where date =(?) and branch = (?) ;");
                statement.setString(1, date);
                statement.setInt(2,0);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    drMa.add(result.getString(1));
                }
                statement.close();
                con.close();
                return drMa;
            } else return null;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            tryClose();
            return null;
        }
    }

    private List<String> distDriver(List<String> drM, List<String> drivers) {
        for (String d: drM) {
            if(!drivers.contains(d))
                drivers.add(d);
        }
        return drivers;
    }

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

    private String dateToString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1) +"/" + calendar.get(Calendar.YEAR);
    }

}

