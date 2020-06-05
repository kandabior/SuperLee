package src.BusinessLayer.Inventory;

import src.DataAccessLayer.Inventory.DTO.ReportDTO;
import src.DataAccessLayer.Inventory.DTO.ReportLineDTO;
import src.DataAccessLayer.Inventory.ReportMapper;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Report {
    private static  int globalId=1;
    private static ReportMapper reportMapper=new ReportMapper();

    private int reportId;
    private String title;
    private List<ReportLine> lines;

    private Report(String title){
        reportId=reportMapper.getReportId();
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

    public static Report makeWeeklyOrder(Integer branchId, List<Pair<Integer, Integer>> weeklyOrder) {
        Report report= new Report("Weekly Order");
        for(Pair<Integer,Integer> line: weeklyOrder){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProductNameStatic(branchId,line.getKey()),line.getValue());
             report.addLine(reportLine);
        }
        return report;
    }

    public static Report totalStockReport(int branchId,List<Pair<Integer, Integer>> quantity) {
        Report report=new Report("Total Stock Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProductNameStatic(branchId,line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }
    public static Report makeShelfReport(int branchId,List<Pair<Integer, Integer>> shelfStock) {
        Report report=new Report("Shelf Report");
        for(Pair<Integer,Integer> line: shelfStock){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProductNameStatic(branchId,line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }

    public static Report makeStorageReport(int branchId,List<Pair<Integer, Integer>> storageStock) {
        Report report=new Report("Storage Report");
        for(Pair<Integer,Integer> line: storageStock){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProductNameStatic(branchId,line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }

    private void addLine(ReportLine reportLine) {
        lines.add(reportLine);
    }

    public static Report makeMissingReport(int branchId,List<Pair<Integer, Integer>> quantity){
        Report report= new Report("Missing Product Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProductNameStatic(branchId,line.getKey()),line.getValue());
            reportLine.addToLine("minimum amount: "+ Inventory.getProductMinStatic(branchId,line.getKey()));
            report.addLine(reportLine);
        }
        return report;
    }

    public static Report makeDefectiveReport(int branchId,List<Pair<Integer, Integer>> quantity){
        Report report= new Report("Defective Product Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProductNameStatic(branchId,line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }

    public static Report makeCategoryReport(int branchId,List<Pair<Integer,Integer>> quantity){
        Report report= new Report("Category Report");
        for(Pair<Integer,Integer> line: quantity){
            ReportLine reportLine= new ReportLine(line.getKey(),Inventory.getProductNameStatic(branchId,line.getKey()),line.getValue());
            report.addLine(reportLine);
        }
        return report;
    }


    public static Report makeSalePriceReport(int branch,Pair<Integer, List<Double>> prices) {
        Report report= new Report("Sale Prices Report");
        ReportLine reportLine=new ReportLine(prices.getKey(),Inventory.getProductNameStatic(branch,prices.getKey()),Inventory.getAmountStatic(branch,prices.getKey()));
        reportLine.addToLine("Prices: "+prices.getValue().toString());

        report.addLine(reportLine);
        return report;
    }
    public static Report makeCostPriceReport(int branch,Pair<Integer, List<Double>> prices) {
        Report report= new Report("Cost Prices Report");
        ReportLine reportLine=new ReportLine(prices.getKey(),Inventory.getProductNameStatic(branch,prices.getKey()),Inventory.getAmountStatic(branch,prices.getKey()));
        reportLine.addToLine("Prices: "+prices.getValue().toString());

        report.addLine(reportLine);
        return report;
    }


    public String toString(){
        String output="--------------------------------------------------\n";

        output= output+"Report name:"+getTitle()+"\n";
        output=output+"Report Id: "+ getReportId()+"\n";
        int count=1;
        for (ReportLine reportLine: getLines()){
            output=output+ count +". "+reportLine.toString()+"\n";
            count++;
        }
        output=output+"--------------------------------------------------\n";
        return output;
    }


    public void saveMe() {
        ReportDTO reportDTO= new ReportDTO(reportId,title);
        List<ReportLineDTO> lineDTOS= new LinkedList<>();
        for( ReportLine line: lines ){
            lineDTOS.add( new ReportLineDTO(line.getProdId(),line.getProdName(),line.getAmount(),line.getOptional()));
        }
        reportDTO.setLines(lineDTOS);
        reportMapper.addReport(reportDTO);
    }
}
