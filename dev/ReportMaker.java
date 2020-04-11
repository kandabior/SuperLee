import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class ReportMaker {

    private static ReportMaker instance;

    private List<Report> reports;

    private ReportMaker(){

        this.reports=new LinkedList<>();
    }

    public static ReportMaker getPrinter(){
        if(instance==null){
            instance=new ReportMaker();
        }
        return instance;
    }

    public String printStock(List<Pair<Integer,Integer>> quantity){
        Report report=Report.totalStockReport(quantity);
        reports.add(report);
        return report.toString();
    }

    public String printMissingProducts(List<Pair<Integer,Integer>> quantity){
        Report report=Report.makeMissingReport(quantity);
        reports.add(report);
        return report.toString();
    }

    public String MakeDefectiveReport(List<Pair<Integer,Integer>> quantity){
        Report report=Report.makeDefectiveReport(quantity);
        reports.add(report);
        return report.toString();
    }



    public String printByCategories(List<Pair<Integer,Integer>> quantity){
        Report report=Report.makeCategoryReport(quantity);
        reports.add(report);
        return report.toString();

    }
}
