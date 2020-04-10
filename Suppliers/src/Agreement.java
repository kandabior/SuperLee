import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Agreement {

    private LinkedHashMap<Integer, Double> terms;
    private BillOfQuantities bill;

    public Agreement() {
        this.terms = new LinkedHashMap<>();
        this.bill = null;
    }

    public void addBillOfQuantities(Map<Integer, Pair<Integer, Double>> bill) {
        this.bill = new BillOfQuantities(bill);
    }

    public void insertBillOfQuantities(Integer itemId, Pair<Integer, Double> quantity_disc) {
        this.bill.insert(itemId,quantity_disc);
    }

    public void updateBillOfQuantities( Integer itemId, Pair<Integer, Double> quantity_disc) {
        this.bill.updateBillOfQuantities(itemId,quantity_disc);
    }

    public void deleteBillOfQuantities(Integer itemId) {
        this.bill.deleteBillOfQuantities(itemId);
    }

    public void insertItem(Integer item_id, Double cost) {
        terms.put(item_id,cost);
    }

    public LinkedHashMap<Integer, Double> getTerms() { return this.terms; }

    public void setPrice(int id, double newPrice) {
        terms.replace(id, newPrice);
    }

    public double getPriceOfItem(int index) {
        return terms.get(index);
    }

    public Boolean checkBillOfQuantity() {
        if(this.bill==null) return false;
        return true;
    }

    public void addItemToBillOfQuantities(int itemId, int itemQuantity, Double itemDiscount) {
        this.bill.addItemToBillOfQuantities(itemId,itemQuantity,itemDiscount);
    }

    public Map<Integer, Pair<Integer, Double>> getbillOfQuantities() {
        return this.bill.getbillOfQuantities();
    }

}
