package classs;

import javafx.util.Pair;

import java.util.Map;

public class BillOfQuantities {

    private Map<Integer, Pair<Integer, Double>> bill;

    public BillOfQuantities(Map<Integer, Pair<Integer, Double>> bill) {
        this.bill = bill;
    }

    public void updateBillOfQuantities(Integer itemId, Pair<Integer, Double> quantity_disc) {
        bill.remove(itemId);
        bill.put(itemId, quantity_disc);
    }

    public void deleteFromBillOfQuantities(Integer itemId) {
        bill.remove(itemId);
    }

    public void addItemToBillOfQuantities(int itemId, int itemQuantity, Double itemDiscount) {
        Pair<Integer,Double> p = new Pair(itemQuantity,itemDiscount);
        this.bill.put(itemId,p);
    }

    public Map<Integer, Pair<Integer, Double>> getBillOfQuantities() {
        return this.bill;
    }

    public int getBillSize() { return bill.size(); }
}
