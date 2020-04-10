import java.util.LinkedList;
import java.util.List;

public class ReportLine {

    private int prodId;
    private String prodName;
    private int storageAmount;
    private int shelfAmount;
    private List<String> categories;

    private ReportLine(int prodId, String name, int StorageAmount,int shelffAmount, List<String> categories ){
        this.prodId= prodId;
        this.prodName=name;
        this.storageAmount=StorageAmount;
        this.shelfAmount=shelffAmount;
        this.categories=categories;
    }

    public static List<ReportLine> makeReportLines(List<Product> products){
        List<ReportLine> output= new LinkedList<>();
        ReportLine line;
        for (Product product:products) {
            line=new ReportLine(product.getId(), product.getName(),product. , product.categories);
            output.add(line);
        }
        return output;
    }


    @Override
    public String toString() {
        return "{" +
                "prodId=" + prodId +
                ", prodName='" + prodName + '\'' +
                ", storageAmount=" + storageAmount +
                ", shelfAmount=" + shelfAmount +
                ", categories=" + categories +
                '}';
    }
}
