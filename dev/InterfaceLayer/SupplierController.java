package InterfaceLayer;

import LogicLayer.Items;
import LogicLayer.Supplier;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class SupplierController {
    private List<Supplier> suppliers;
    private static SupplierController sp_instance = null;

    public static SupplierController getSupplierController() {
        if (sp_instance == null)
            sp_instance = new SupplierController();
        return sp_instance;
    }

    private SupplierController() {
      this.suppliers = new LinkedList<>();
    }

    public boolean deleteSupplier(int id) {
        return Supplier.deleteSuppplier(id);

//
//
//
//        for (int i = 0; i < suppliers.size(); i++) {
//            if (suppliers.get(i).getId() == id) {
//                suppliers.remove(i);
//                return true;
//            }
//        }
//        return false;
    }

    public Supplier getSuppById(int id) {
        for(int i= 0; i<suppliers.size(); i++) {
            if(suppliers.get(i).getId() == id)
                return suppliers.get(i);
        }
        return null;
    }

    public boolean findSupplier(int id) {
        if(getSuppById(id) != null) return true;
        return false;
    }

    public boolean addSupplier(int id, String name, String phoneNum, int bankAccount, String payment, String supplySchedule, String supplyLocation , String address) {
        Supplier sup = new Supplier(id, name, phoneNum, bankAccount, payment, supplySchedule, supplyLocation, address);
        return sup.saveMe();
        /*this.suppliers.add(sup);*/
    }

    public void addBillOfQuantities(int supplierId,Map<Integer, Pair<Integer, Double>> bill) {
        if (getSuppById(supplierId) != null) {
            getSuppById(supplierId).getAgreement().addBillOfQuantities(bill);
        }
    }

    public boolean addItemToAgreement(Integer supp_id ,Integer item_id,Double cost){
        return Supplier.addItemToAgreement(supp_id,item_id,cost);
        //getSuppById(supp_id).addItemToAgreement(item_id,cost);
    }

    public void updateBillOfQuantities(int supplierId, Integer itemId, Pair<Integer, Double> quantity_disc) {
       if(getSuppById(supplierId)!=null)
       {
           getSuppById(supplierId).updateBillOfQuantities(itemId,quantity_disc);
       }
    }

    public void deleteFromBillOfQuantities(int suppId, Integer itemId) {
        if (getSuppById(suppId) != null) {
            getSuppById(suppId).deleteFromBillOfQuantities(itemId);
        }
    }

    public void deleteBillOfQuantities(int suppId) {
        if (getSuppById(suppId) != null) {
            getSuppById(suppId).deleteBillOfQuantities();
        }
    }

    public int getBillSize(int suppId) { return getSuppById(suppId).getBillSize(); }

    public LinkedHashMap<Integer, Double> showSuppItems(int suppId){
        Supplier supplier = getSuppById(suppId);
        return supplier.getAgreement().getTerms();
    }

    public double getPriceOfItem(int suppId, int index) {
        return getSuppById(suppId).getPriceOfItem(index);
    }

    public boolean checkBillOfQuantity(int suppId) {
        return getSuppById(suppId).checkBillOfQuantity();
    }

    public void addItemToBillOfQuantities(int suppId, int itemId, int itemQuantity, Double itemDiscount) {
        getSuppById(suppId).addItemToBillOfQuantities(itemId,itemQuantity,itemDiscount);
    }

    public Map<Integer, Pair<Integer, Double>> getBillOfQuantities(int suppId) {
        return getSuppById(suppId).getBillOfQuantities();
    }

    public String getItemName( Integer itemId) {
        return Items.getName(itemId);
    }

    public String getItemNameByIndex(int suppId, int i) {
        return Items.getName(getSuppById(suppId).getItemIdByIndex(i));
    }


    public int getItemIdByIndex(int suppId, int i) {
        return getSuppById(suppId).getItemIdByIndex(i);
    }

    public void addItemToSupplier(int suppId, int itemId) {
        Supplier.addItemToSupplier(suppId,itemId);
        //this.getSuppById(suppId).addItemsToSupplier(itemId);
    }

    public int getItemsListSize(int suppId) {
        return Supplier.getItemsListSize(suppId);
        //return this.getSuppById(suppId).getItemsListSize();
    }

    public void setItemPrice(int suppId, int itemId, double newPrice) {
        getSuppById(suppId).setItemPrice(itemId, newPrice);
    }

    public boolean validateItemId(int suppId, int itemId) {
        return Supplier.validateItemId(suppId,itemId);
        //return getSuppById(suppId).validateItemId(itemId);
    }

    public int bestSuppForItem(Integer itemId, Integer quantity) {
        Double min=100000000.0;
        int bestSuppId=-1;
        for(int i=0 ; i<suppliers.size();i++)
        {
             Double temp =suppliers.get(i).getPriceOfAmountOfItem(itemId,quantity);
             if(temp<min) {
                 min = temp;
                 bestSuppId=i;
             }
        }

        return bestSuppId+1;
    }

    public Double getPriceOfAmountOfItem(int bestSuppForItem,Integer itemId, Integer amount) {
        return getSuppById(bestSuppForItem).getPriceOfAmountOfItem(itemId,amount);
    }

    public boolean checkIfItemExist(int itemId) {
        return Items.checkIfItemExist(itemId);

        //return (Items.getName(itemId)!=null);
    }

    public Double getPriceOfAmountOfItemBeforeDiscount(int suppId , int itemId , int amount) {
        return getSuppById(suppId).getPriceOfAmountOfItemBeforeDiscount(itemId, amount);
    }

    public Double getDiscountOfItem(int bestSuppForItem, int itemId , int amount) {
        return getSuppById(bestSuppForItem).getDiscountOfItem(itemId, amount);
    }

    public List<Object> getSuppDetails(int bestSuppForItem) {
        return getSuppById(bestSuppForItem).getSuppDetails();
    }
}
