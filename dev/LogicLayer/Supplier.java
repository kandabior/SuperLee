package LogicLayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import LogicLayer.Agreement;
import LogicLayer.OrderLine;
import javafx.util.Pair;

public class Supplier {
    private  int id;
    private String name;
    private String phoneNum;
    private int bankAccount;
    private String payment;
    private String supplySchedule;
    private String supplyLocation;
    private List<Item> items;
    private Agreement agreement;

    public Supplier(int id, String name, String phoneNum, int bankAccount, String payment, String supplySchedule, String supplyLocation) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.bankAccount = bankAccount;
        this.payment = payment;
        this.supplySchedule = supplySchedule;
        this.supplyLocation = supplyLocation;
        this.items = new LinkedList<>();
        this.agreement = new Agreement();
    }

    public int getId() { return this.id; }

    public Agreement getAgreement() { return this.agreement; }

    public boolean makeOrder(List<ItemInOrder> items) {
        int counter = 0;
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < this.items.size(); j++) {
                if (this.items.get(j).getId() == items.get(i).getItemId()) {
                        counter++;
                }
            }
        }
        if (counter != items.size()) return false;
        return true;
    }

    public void updateBillOfQuantities( Integer itemId, Pair<Integer, Double> quantity_disc) {
        this.agreement.updateBillOfQuantities(itemId,quantity_disc);
    }

    public void deleteFromBillOfQuantities(Integer itemId) {
        this.agreement.deleteFromBillOfQuantities(itemId);
    }

    public void deleteBillOfQuantities() { this.agreement.deleteBillOfQuantities(); }

    public void addItemToAgreement(Integer item_id, Double cost) {
        this.agreement.insertItem(item_id,cost);
    }

    public double getPriceOfItem(int index) {
        return  this.agreement.getPriceOfItem(index);
    }

    public Boolean checkBillOfQuantity() {
        return this.agreement.checkBillOfQuantity();
    }

    public void addItemToBillOfQuantities(int itemId, int itemQuantity, Double itemDiscount) {
        this.agreement.addItemToBillOfQuantities(itemId,itemQuantity,itemDiscount);
    }

    public Map<Integer, Pair<Integer, Double>> getBillOfQuantities() {
        return this.agreement.getBillOfQuantities();
    }

    public String getItemName(Integer itemId) {
        for(Item item:items)
        {
            if(item.getId()==itemId) return item.getName();
            break;
        }
        return "";
    }

    public String getItemNameByIndex(int i) {
        return this.items.get(i).getName();
    }


    public int getItemIdByIndex(int i) {
        return this.items.get(i).getId();
    }

    public void addItemsToSupplier(int itemId, String itemName) {
            Item item = new Item(itemId,itemName);
            this.items.add(item);
    }

    public int getItemsListSize() {
        return this.items.size();
    }

    public void setItemPrice(int itemId, double newPrice) { getAgreement().setPrice(itemId, newPrice);}

    public boolean validateItemId(int itemId){
        for(int i=0; i<items.size();i++) {
            if(items.get(i).getId() == itemId)
                return false;
        }
        return true;
    }

    public int getBillSize() { return this.agreement.getBillSize(); }

    public double getOrderCost(int itemId, int quantity) {
        return this.agreement.getOrderCost(itemId,quantity);
    }

}
