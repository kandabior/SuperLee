package src.DataAccessLayer.Inventory.DTO;

import java.util.List;

public class ReportDTO {

    private int reportId;
    private String title;
    private List<ReportLineDTO> lines;

    public ReportDTO(int reportId, String title){
        this.reportId=reportId;
        this.title=title;
    }

    public void setLines(List<ReportLineDTO> lines) {
        this.lines = lines;
    }

    public int getReportId() {
        return reportId;
    }

    public List<ReportLineDTO> getLines() {
        return lines;
    }

    public String getTitle() {
        return title;
    }
}
