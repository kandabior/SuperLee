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
