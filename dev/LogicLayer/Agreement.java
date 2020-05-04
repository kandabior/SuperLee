package LogicLayer;

import javafx.util.Pair;

import java.text.DecimalFormat;
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

    public void updateBillOfQuantities( Integer itemId, Pair<Integer, Double> quantity_disc) {
        this.bill.updateBillOfQuantities(itemId,quantity_disc);
    }

    public void deleteFromBillOfQuantities(Integer itemId) {
        this.bill.deleteFromBillOfQuantities(itemId);
    }

    public void deleteBillOfQuantities() { this.bill = null; }

    public void insertItem(Integer itemId, Double price) {
        terms.put(itemId, price);
    }

    public LinkedHashMap<Integer, Double> getTerms() { return this.terms; }

    public void setPrice(int id, double newPrice) {
        terms.replace(id, newPrice);
    }

    public double getPriceOfItem(int index) {
        return terms.get(index);
    }

    public Boolean checkBillOfQuantity() {
        return this.bill != null;
    }

    public void addItemToBillOfQuantities(int itemId, int itemQuantity, Double itemDiscount) {
        this.bill.addItemToBillOfQuantities(itemId,itemQuantity,itemDiscount);
    }

    public Map<Integer, Pair<Integer, Double>> getBillOfQuantities() {
        return this.bill.getBillOfQuantities();
    }

    public int getBillSize() { return this.bill.getBillSize(); }

    public double getPriceOfAmountOfItem(int itemId, int quantity) {
        if (bill != null && bill.checkItemInBill(itemId, quantity) == true) {
            double discount = bill.getMoneyAmountofItemInBill(itemId, quantity);
            double cost = terms.get(itemId);
            double costMulQuantity = (cost * quantity);
             double x = costMulQuantity * (1 - discount);
            DecimalFormat df = new DecimalFormat("#.##");
            String dx=df.format(x);
            x=Double.valueOf(dx);
            return x ;
        } else {
            double cost = terms.get(itemId);
            return (cost * quantity);
        }
    }

    public boolean checkIfItemExist(int itemId) {

       if(this.terms.get(itemId)!=null) return true;
       return false;

    }

    public Double getPriceOfAmountOfItemBeforeDiscount(int itemId, int amount) {
        double cost = terms.get(itemId);
        double ans = (cost * amount);
        return  ans;
    }

    public Double getDiscountOfItem(int itemId , int amount) {
        if (bill != null && bill.checkItemInBill(itemId, amount) == true) {
            return bill.getDiscount(itemId);
        }
        else
        {
            return 0.0;
        }
    }
}
