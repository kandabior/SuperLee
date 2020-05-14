package InterfaceLayer;

import DataAccessLayer.SupplierMapper;
import LogicLayer.Agreement;
import LogicLayer.Items;
import LogicLayer.Supplier;
import javafx.util.Pair;

import java.util.*;

public class SupplierController {
    private List<Supplier> suppliers;
    private static SupplierController sp_instance = null;
    private Supplier supplier;
    private Agreement agreement;
    private Items item;

    public static SupplierController getSupplierController() {
        if (sp_instance == null)
            sp_instance = new SupplierController();
        return sp_instance;
    }

    private SupplierController() {
      this.suppliers = new LinkedList<>();
      this.supplier = new Supplier();
      this.agreement = new Agreement();
      this.item = new Items();
    }

    public static List<String> getSupplierItemsNames(int suppId) {
        return SupplierMapper.getSupplierItemsNames(suppId);
    }

    public List<Integer> getSupplierItemsId(int suppId) {
        return this.supplier.getSupplierItemsId(suppId);
    }

    public boolean deleteSupplier(int id) {
        return Supplier.deleteSupplier(id);

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
        return SupplierMapper.findSupplier(id);
        //if(getSuppById(id) != null) return true;
        //return false;
    }

    public boolean addSupplier(int id, String name, String phoneNum, int bankAccount, String payment, String supplySchedule, String supplyLocation , String address) {
        Supplier sup = new Supplier(id, name, phoneNum, bankAccount, payment, supplySchedule, supplyLocation, address);
        return sup.saveMe();
    }

    public void createBillOfQuantities(int supplierId ,Map<Integer, Pair<Integer, Double>> bill) { //item id, <quantity, discount>
        this.supplier.createBillOfQuantities(supplierId);
        Iterator<Integer> iter = bill.keySet().iterator();
        while (iter.hasNext()){
            int itemId = iter.next();
            this.agreement.addItemToBillOfQuantities(supplierId, itemId, bill.get(itemId).getKey(), bill.get(itemId).getValue());//TODO dorin was here
        }
    }

    public boolean addItemToAgreement(Integer supp_id ,Integer item_id,Double cost){
        return this.supplier.addItemToAgreement(supp_id,item_id,cost);
    }

    public void changeInBillOfQuantities(int supplierId, Integer itemId, Pair<Integer, Double> quantity_disc) {

    /*   if(getSuppById(supplierId)!=null)
       {
           getSuppById(supplierId).updateBillOfQuantities(itemId,quantity_disc);
       }*/
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
        return this.supplier.showSuppItems(suppId);
        /*Supplier supplier = getSuppById(suppId);
        return supplier.getAgreement().getTerms();*/
    }

    public double getPriceOfItem(int suppId, int index) {
        return getSuppById(suppId).getPriceOfItem(index);
    }

    public boolean checkBillOfQuantity(int suppId) {
        return this.supplier.checkBillOfQuantity(suppId);
    }

    public void addItemToBillOfQuantities(int suppId, int itemId, int itemQuantity, Double itemDiscount) {
        this.agreement.addItemToBillOfQuantities(suppId, itemId, itemQuantity, itemDiscount);
       // getSuppById(suppId).addItemToBillOfQuantities(itemId,itemQuantity,itemDiscount);
    }

    public Map<Integer, Pair<Integer, Double>> getBillOfQuantities(int suppId) {
        return getSuppById(suppId).getBillOfQuantities();
    }

    public String getItemName( Integer itemId) {
        return this.item.getName(itemId);
    }

    public String getItemNameByIndex(int suppId, int i) {
        return this.item.getName(getSuppById(suppId).getItemIdByIndex(i));
    }


    public int getItemIdByIndex(int suppId, int i) {
        return getSuppById(suppId).getItemIdByIndex(i);
    }

    public void addItemToSupplier(int suppId, int itemId) {
        this.supplier.addItemToSupplier(suppId,itemId);
        //this.getSuppById(suppId).addItemsToSupplier(itemId);
    }

    public int getItemsListSize(int suppId) {
        return this.supplier.getItemsListSize(suppId);
        //return this.getSuppById(suppId).getItemsListSize();
    }

    public void setItemPrice(int suppId, int itemId, double newPrice) {
        this.supplier.setItemPrice(suppId, itemId, newPrice);
        //getSuppById(suppId).setItemPrice(itemId, newPrice);
    }

    public boolean validateItemId(int suppId, int itemId) {
        return this.supplier.validateItemId(suppId,itemId);
        //return getSuppById(suppId).validateItemId(itemId);
    }

    public int bestSuppForItem(Integer itemId, Integer quantity) {


        Double min=100000000.0;
        int bestSuppId=-1;
        int size = this.supplier.getSupplierSize();
        List<Integer> suppId = this.supplier.getSupplierIds();
        for(int i=0 ; i<size;i++)
        {
             Double temp =getPriceOfAmountOfItem(suppId.get(i),itemId,quantity);
             if(temp<min) {
                 min = temp;
                 bestSuppId=suppId.get(i);
             }
        }

        return bestSuppId;



      /*  List<Integer> suppId = this.supplier.getSupplierId();
        for(int i=0 ; i<size;i++)
        {
             Double temp =suppliers.get(i).getPriceOfAmountOfItem(itemId,quantity);
             if(temp<min) {
                 min = temp;
                 bestSuppId=i;
             }
        }

        return bestSuppId+1;*/
    }

    public Double getPriceOfAmountOfItem(int bestSuppForItem,Integer itemId, Integer amount) {
         return this.supplier.getPriceOfAmountOfItem(bestSuppForItem,itemId,amount);
       /* return getSuppById(bestSuppForItem).getPriceOfAmountOfItem(itemId,amount);*/
    }

    public boolean checkIfItemExist(int itemId) {
        return this.item.checkIfItemExist(itemId);

        //return (Items.getName(itemId)!=null);
    }

    public Double getPriceOfAmountOfItemBeforeDiscount(int suppId , int itemId , int amount) {

        return this.supplier.getPriceOfAmountOfItemBeforeDiscount(suppId,itemId,amount);
        //return getSuppById(suppId).getPriceOfAmountOfItemBeforeDiscount(itemId, amount);
    }

    public Double getDiscountOfItem(int bestSuppForItem, int itemId , int amount) {
        return this.supplier.getDiscount(bestSuppForItem,itemId,amount);
        //return getSuppById(bestSuppForItem).getDiscountOfItem(itemId, amount);
    }

    public List<Object> getSuppDetails(int bestSuppForItem) {
        return this.supplier.getSuppDetails(bestSuppForItem);// getSuppById(bestSuppForItem).();
    }

    public int getSuppliersCounter() {
        return this.supplier.getSuppliersCounter();
    }
}
