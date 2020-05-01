package LogicLayer;

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

    public String printStock(int branchId,List<Pair<Integer,Integer>> quantity){
        Report report=Report.totalStockReport(branchId,quantity);
        reports.add(report);
        return report.toString();
    }

    public String printMissingProducts(int branchId,List<Pair<Integer,Integer>> quantity){
        Report report=Report.makeMissingReport(branchId,quantity);
        reports.add(report);
        return report.toString();
    }

    public String MakeDefectiveReport(int branchId,List<Pair<Integer,Integer>> quantity){
        Report report=Report.makeDefectiveReport(branchId,quantity);
        reports.add(report);
        return report.toString();
    }



    public String printByCategories(int branchId,List<Pair<Integer,Integer>> quantity){
        Report report=Report.makeCategoryReport(branchId,quantity);
        reports.add(report);
        return report.toString();

    }


    public String printSaleProductPrices(int branchId,Pair<Integer, List<Double>> prices) {
        Report report= Report.makeSalePriceReport(branchId,prices);
        reports.add(report);
        return report.toString();
    }
    public String printCostProductPrices(int branchId,Pair<Integer, List<Double>> prices) {
        Report report= Report.makeCostPriceReport(branchId,prices);
        reports.add(report);
        return report.toString();
    }


    public String printShelfReport(int branchId,List<Pair<Integer, Integer>> shelfStock) {
        Report report= Report.makeShelfReport(branchId,shelfStock);
        reports.add(report);
        return report.toString();
    }

    public String printStorageReport(int branchId,List<Pair<Integer, Integer>> storageStock) {
        Report report= Report.makeStorageReport(branchId,storageStock);
        reports.add(report);
        return report.toString();
    }
}
