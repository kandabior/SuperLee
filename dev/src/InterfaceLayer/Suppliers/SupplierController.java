package src.InterfaceLayer.Suppliers;

import org.omg.CORBA.INTERNAL;
import src.BusinessLayer.Suppliers.Agreement;
import src.BusinessLayer.Suppliers.Items;
import src.BusinessLayer.Suppliers.Supplier;
import src.DataAccessLayer.Suppliers.SupplierMapper;

import javafx.util.Pair;

import java.util.*;

public class SupplierController {
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
        return this.supplier.deleteSupplier(id);
    }

    public boolean findSupplier(int id) {
        return SupplierMapper.findSupplier(id);
    }

    public boolean addSupplier(int id, String name, String phoneNum, int bankAccount, String payment, String supplySchedule, String supplyLocation) {
        Supplier sup = new Supplier(id, name, phoneNum, bankAccount, payment, supplySchedule, supplyLocation);
        return sup.saveMe();
    }

    public void createBillOfQuantities(int supplierId ,Map<Integer, Pair<Integer, Double>> bill) { //item id, <quantity, discount>
        this.supplier.createBillOfQuantities(supplierId);
        Iterator<Integer> iter = bill.keySet().iterator();
        while (iter.hasNext()){
            int itemId = iter.next();
            this.agreement.addItemToBillOfQuantities(supplierId, itemId, bill.get(itemId).getKey(), bill.get(itemId).getValue());
        }
    }

    public boolean addItemToAgreement(Integer supp_id ,Integer item_id,Double cost){
        return this.supplier.addItemToAgreement(supp_id,item_id,cost);
    }

    public void changeInBillOfQuantities(int supplierId, Integer itemId, Pair<Integer, Double> quantity_disc) {
        this.agreement.changeInBillOfQuantities(supplierId, itemId, quantity_disc);
    }

    public void deleteFromBillOfQuantities(int suppId, Integer itemId) {
        this.agreement.deleteFromBillOfQuantities(suppId, itemId);
    }

    public void deleteBillOfQuantities(int suppId) {
        this.agreement.deleteBillOfQuantities(suppId);
    }

    public int getBillSize(int suppId) { return this.agreement.getBillSize(suppId); }

    public LinkedHashMap<Integer, Double> showSuppItems(int suppId){
        return this.supplier.showSuppItems(suppId);

    }

    public boolean checkBillOfQuantity(int suppId) {
        return this.supplier.checkBillOfQuantity(suppId);
    }

    public void addItemToBillOfQuantities(int suppId, int itemId, int itemQuantity, Double itemDiscount) {
        this.agreement.addItemToBillOfQuantities(suppId, itemId, itemQuantity, itemDiscount);
    }

    public Map<Integer, Pair<Integer, Double>> getBillOfQuantities(int suppId) {
        return this.agreement.getBillOfQuantities(suppId);
    }

    public String getItemName( Integer itemId) {
        return this.item.getName(itemId);
    }

    public boolean addItemToSupplier(int suppId, int itemId, int itemLocalId) {
        return this.supplier.addItemToSupplier(suppId,itemId,itemLocalId);
    }

    public int getItemsListSize(int suppId) {
        return this.supplier.getItemsListSize(suppId);
    }

    public void setItemPrice(int suppId, int itemId, double newPrice) {
        this.supplier.setItemPrice(suppId, itemId, newPrice);
    }

    public boolean validateItemId(int suppId, int itemId) {
        return this.supplier.validateItemId(suppId,itemId);
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

    }

    public Double getPriceOfAmountOfItem(int bestSuppForItem,Integer itemId, Integer amount) {
         return this.supplier.getPriceOfAmountOfItem(bestSuppForItem,itemId,amount);
    }

    public boolean checkIfItemExist(int itemId) {
        return this.item.checkIfItemExist(itemId);
    }

    public Double getPriceOfAmountOfItemBeforeDiscount(int suppId , int itemId , int amount) {
        return this.supplier.getPriceOfAmountOfItemBeforeDiscount(suppId,itemId,amount);
    }

    public Double getDiscountOfItem(int bestSuppForItem, int itemId , int amount) {
        return this.supplier.getDiscount(bestSuppForItem,itemId,amount);
    }

    public List<Object> getSuppDetails(int bestSuppForItem) {
        return this.supplier.getSuppDetails(bestSuppForItem);// getSuppById(bestSuppForItem).();
    }

    public int getSuppliersCounter() {
        return this.supplier.getSuppliersCounter();
    }

    public boolean validateItemIdInBill(int suppId, int itemId) {
        return this.supplier.validateItemIdInBill(suppId,itemId);
    }

    public List<List<Object>> getAllSuppliers() {
        return this.supplier.getAllSuppliers();
    }

    public int getLocalItemId(int itemId,int suppId) {
        return this.supplier.getLocalItemId(itemId,suppId);
    }

    public boolean addSupplierDays(int suppId, int[] daysInt) {
        return this.supplier.addSupplierDays(suppId, daysInt);
    }

    public String getSupplierType(int suppId) {
        return this.supplier.getSupplierType(suppId);
    }

    public List<Integer> getSupplyDays(int suppId) {
        return this.supplier.getSupplyDays(suppId);
    }

    public List<Integer> getLocalItemsIds(int suppId,List<Integer> temp) {
        return this.supplier.getLocalItemsIds(suppId,temp);
    }
}
