import javafx.util.Pair;

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

    public static Report totalStockReport(List<Pair<Integer, Integer>> quantity) {
        Report report=new Report("Total Stock Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProdactName(line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }

    private void addLine(ReportLine reportLine) {
        lines.add(reportLine);
    }

    public static Report makeMissingReport(List<Pair<Integer, Integer>> quantity){
        Report report= new Report("Missing Product Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProdactName(line.getKey()),line.getValue());
            reportLine.addToLine("minimum amount: "+ Inventory.getProductMin(line.getKey()));
            report.addLine(reportLine);
        }
        return report;
    }

    public static Report makeDefectiveReport(List<Pair<Integer, Integer>> quantity){
        Report report= new Report("Defective Product Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProdactName(line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }

    public static Report makeCategoryReport(List<Pair<Integer,Integer>> quantity){
        Report report= new Report("Category Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProdactName(line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }


    public String toString(){
        String output="";
        output= output+"Report name:"+getTitle()+"\n";
        output=output+"Report Id: "+ getReportId()+"\n";
        Integer count=1;
        for (ReportLine reportLine: getLines()){
            output=output+count.toString()+". "+reportLine.toString()+"\n";
        }
        return output;
    }


}
