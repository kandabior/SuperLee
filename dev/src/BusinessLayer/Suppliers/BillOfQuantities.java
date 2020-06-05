package src.BusinessLayer.Suppliers;
import javafx.util.Pair;
import java.util.Map;

public class BillOfQuantities {

    private Map<Integer, Pair<Integer, Double>> bill; //itemId, <quantity, discount>

    public BillOfQuantities(Map<Integer, Pair<Integer, Double>> bill) {
        this.bill = bill;
    }

    public boolean checkItemInBill(int itemId, int quantity) {
        if(bill.get(itemId)!=null)
        {
            if(bill.get(itemId).getKey()<=quantity)
                return true;
        }
        return false;
    }

}
