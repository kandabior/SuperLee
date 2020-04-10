import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Printer {

    private static Printer instance;

    private Inventory inventory;
    private List<Report> reports;

    private Printer(Inventory inventory){
        this.inventory=inventory;
        this.reports=new LinkedList<>();
    }

    public static Printer getPrinter(Inventory inventory){
        if(instance==null){
            instance=new Printer(inventory);
        }
        return instance;
    }

    public String printStock(){
        List<Pair<Integer,Integer>> quantity= inventory.getQuantity();
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
        Report report=Report.makeDefectiveReport(quantity);


    }
}
