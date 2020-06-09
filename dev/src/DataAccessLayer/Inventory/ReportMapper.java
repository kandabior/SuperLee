package src.DataAccessLayer.Inventory;


import src.DataAccessLayer.Inventory.DTO.ReportDTO;
import src.DataAccessLayer.Inventory.DTO.ReportLineDTO;

import java.sql.*;

public class ReportMapper {

    private Connection c;
    public ReportMapper(){}

    public int getReportId() {
        PreparedStatement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("SELECT counter FROM Counters WHERE name=?;");
            stmt.setString(1,"reports");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int output= rs.getInt("counter");
                rs.close();
                stmt.close();
                stmt = c.prepareStatement("UPDATE Counters SET counter=? WHERE name=?;");
                stmt.setInt(1,output+1);
                stmt.setString(2,"reports");
                stmt.executeUpdate();
                c.commit();
                c.close();
                stmt.close();
                return output;
            } else {
                rs.close();
                stmt.close();
                stmt = c.prepareStatement("INSERT Counters VALUES (reports,2);");
                stmt.executeUpdate();
                stmt.close();
                c.commit();
                c.close();
                return 1;
            }
        } catch (Exception e) {
            if (c != null) {
                try {
                    //System.err.print("Transaction is being rolled back");
                    c.rollback();
                } catch (SQLException excep) {
                }
            }
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return 0;

    }


    public void addReport(ReportDTO reportDTO) {
        PreparedStatement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            c.setAutoCommit(false);
            stmt = c.prepareStatement("INSERT INTO Reports VALUES (?,?);");
            stmt.setInt(1, reportDTO.getReportId());
            stmt.setString(2, reportDTO.getTitle());
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            for( ReportLineDTO reportLineDTO : reportDTO.getLines()) {
                stmt = c.prepareStatement("INSERT INTO ReportLines VALUES (?,?,?,?);");
                stmt.setInt(1, reportDTO.getReportId());
                stmt.setInt(2, reportLineDTO.getProdId());
                stmt.setString(3, reportLineDTO.getProdName());
                stmt.setInt(4, reportLineDTO.getAmount());
                stmt.executeUpdate();
                stmt.close();
                c.commit();
                for(String str: reportLineDTO.getOptional()) {
                    stmt = c.prepareStatement("INSERT INTO ReportLineOptional VALUES (?,?,?);");
                    stmt.setInt(1, reportDTO.getReportId());
                    stmt.setInt(2, reportLineDTO.getProdId());
                    stmt.setString(3, str);
                    stmt.executeUpdate();
                    stmt.close();
                    c.commit();
                }
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (c != null) {
                try {
                    //System.err.println("Transaction is being rolled back");
                    c.rollback();
                } catch (SQLException excep) {
                }
            }
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}

