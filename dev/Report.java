import java.util.LinkedList;
import java.util.List;

public class Report {
    private static  int globalId=1;

    private int reportId;
    private String title;
    private List<ReportLine> lines;

    private Report(String title){
        reportId=globalId++;
        this.title=title;
        lines=new LinkedList<>();
    }

    public int getReportId() {
        return reportId;
    }
    public String getTitle() {
        return title;
    }
    public List<ReportLine> getLines() {
        return lines;
    }

    public static Report makeCategoryReport(List<Product> products){
        Report report= new Report("Category Report");
        report.lines= makeReportLines(products);
        return report;
    }

    public static Report makeDefectiveReport(List<Product> products){
        Report report= new Report("Defective Product Report");
        report.lines= makeReportLines(products);
        return report;
    }

    public static Report makeMissingReport(List<Product> products){
        Report report= new Report("Missing Product Report");
        report.lines= makeReportLines(products);
        return report;
    }

    public static List<ReportLine> makeReportLines(List<Product> products){
        return ReportLine.makeReportLines(products);
    }


}
