package DataAccessLayer;

import DTO.ReportDTO;
import DTO.ReportLineDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportMapper {

    private Connection c;
    public ReportMapper(){}


    public void addReport(ReportDTO reportDTO) {
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
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

                }
            }
        } catch (Exception e) {
            if (c != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    c.rollback();
                } catch (SQLException excep) {
                }
            }
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}

