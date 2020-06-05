package src.BusinessLayer.Inventory;

import javafx.util.Pair;

import java.util.List;

public class ReportMaker {

    private static ReportMaker instance;

    //private List<Report> reports;

    private ReportMaker(){

    }

    public static ReportMaker getPrinter(){
        if(instance==null){
            instance=new ReportMaker();
        }
        return instance;
    }

    public String printStock(int branchId,List<Pair<Integer,Integer>> quantity){
        Report report=Report.totalStockReport(branchId,quantity);
        report.saveMe();
        return report.toString();
    }

    public String printMissingProducts(int branchId,List<Pair<Integer,Integer>> quantity){
        Report report=Report.makeMissingReport(branchId,quantity);
        report.saveMe();
        return report.toString();
    }

    public String MakeDefectiveReport(int branchId,List<Pair<Integer,Integer>> quantity){
        Report report=Report.makeDefectiveReport(branchId,quantity);
        report.saveMe();
        return report.toString();
    }



    public String printByCategories(int branchId,List<Pair<Integer,Integer>> quantity) {
        Report report = Report.makeCategoryReport(branchId, quantity);
        report.saveMe();
        return report.toString();

    }


    public String printSaleProductPrices(int branchId,Pair<Integer, List<Double>> prices) {
        Report report= Report.makeSalePriceReport(branchId,prices);
        report.saveMe();
        return report.toString();
    }
    public String printCostProductPrices(int branchId,Pair<Integer, List<Double>> prices) {
        Report report= Report.makeCostPriceReport(branchId,prices);
        report.saveMe();
        return report.toString();
    }


    public String printShelfReport(int branchId,List<Pair<Integer, Integer>> shelfStock) {
        Report report= Report.makeShelfReport(branchId,shelfStock);
        report.saveMe();
        return report.toString();
    }

    public String printStorageReport(int branchId,List<Pair<Integer, Integer>> storageStock) {
        Report report= Report.makeStorageReport(branchId,storageStock);
        report.saveMe();
        return report.toString();
    }

    public String PrintWeeklyOrder(Integer branchId,List<Pair<Integer, Integer>> weeklyOrder) {
        Report report= Report.makeWeeklyOrder(branchId,weeklyOrder);
        report.saveMe();
        return report.toString();
    }
}
