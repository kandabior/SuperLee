import javafx.util.Pair;

import java.util.List;

public class Printer {

    private static Printer instance;

    private Inventory inventory;

    private Printer(Inventory inventory){
        this.inventory=inventory;
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
        String output="";
        output= output+"Report name:"+report.getTitle()+"\n";
        output=output+"Report Id: "+ report.getReportId()+"\n";
        Integer count=1;
        for (ReportLine reportLine: report.getLines()){
            output=output+count.toString()+". "+reportLine.toString()+"\n";
        }
        return output;
    }

    public String printByCategories(){
        List<Product> products=inventory.getProductsByCategories();
        Report report= Report.makeCategoryReport(products);
        String output="Report Id: "+report.getReportId()+"\n"+
                        "Report Name: "+report.getTitle()+"\n";
        for(ReportLine line : report.getLines()){

        }
        return output;

    }
}
