package LogicLayer;

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
        Report report=new Report("Total Stock LogicLayer.Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProdactName(line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }
    public static Report makeShelfReport(List<Pair<Integer, Integer>> shelfStock) {
        Report report=new Report("Shelf LogicLayer.Report");
        for(Pair<Integer,Integer> line: shelfStock){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProdactName(line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }

    public static Report makeStorageReport(List<Pair<Integer, Integer>> storageStock) {
        Report report=new Report("Storage LogicLayer.Report");
        for(Pair<Integer,Integer> line: storageStock){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProdactName(line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }

    private void addLine(ReportLine reportLine) {
        lines.add(reportLine);
    }

    public static Report makeMissingReport(List<Pair<Integer, Integer>> quantity){
        Report report= new Report("Missing LogicLayer.Product LogicLayer.Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProdactName(line.getKey()),line.getValue());
            reportLine.addToLine("minimum amount: "+ Inventory.getProductMin(line.getKey()));
            report.addLine(reportLine);
        }
        return report;
    }

    public static Report makeDefectiveReport(List<Pair<Integer, Integer>> quantity){
        Report report= new Report("Defective LogicLayer.Product LogicLayer.Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProdactName(line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }

    public static Report makeCategoryReport(List<Pair<Integer,Integer>> quantity){
        Report report= new Report("Category LogicLayer.Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProdactName(line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }


    public static Report makeSalePriceReport(Pair<Integer, List<Integer>> prices) {
        Report report= new Report("Sale Prices LogicLayer.Report");
        ReportLine reportLine=new ReportLine(prices.getKey(),Inventory.getProdactName(prices.getKey()),Inventory.getAmount(prices.getKey()));
        reportLine.addToLine("Prices: "+prices.getValue().toString());

        report.addLine(reportLine);
        return report;
    }
    public static Report makeCostPriceReport(Pair<Integer, List<Integer>> prices) {
        Report report= new Report("Cost Prices LogicLayer.Report");
        ReportLine reportLine=new ReportLine(prices.getKey(),Inventory.getProdactName(prices.getKey()),Inventory.getAmount(prices.getKey()));
        reportLine.addToLine("Prices: "+prices.getValue().toString());

        report.addLine(reportLine);
        return report;
    }


    public String toString(){
        String output="--------------------------------------------------\n";

        output= output+"LogicLayer.Report name:"+getTitle()+"\n";
        output=output+"LogicLayer.Report Id: "+ getReportId()+"\n";
        int count=1;
        for (ReportLine reportLine: getLines()){
            output=output+ count +". "+reportLine.toString()+"\n";
            count++;
        }
        output=output+"--------------------------------------------------\n";
        return output;
    }


}
