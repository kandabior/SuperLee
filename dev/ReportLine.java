import java.util.LinkedList;
import java.util.List;

public class ReportLine {

    private int prodId;
    private String prodName;
    private int amount;
    private List<String> optional;

    public ReportLine(int prodId, String name, int amount){
        this.prodId= prodId;
        this.prodName=name;
        this.amount=amount;
        optional=new LinkedList<>();
    }

    public boolean addToLine(String toAdd){
        return optional.add(toAdd);
    }

    @Override
    public String toString() {
        if(optional.size()==0){
            return  "prodId: " + prodId +
                    ", prodName: " + prodName +
                    ", amount: " + amount +
                    ".";
        }
        else{
            return  "prodId: " + prodId +
                    ", prodName: " + prodName +
                    ", amount: " + amount +
                    ", "+optional;
        }
    }
}
