package LogicLayer;

import DTO.SupplierDTO;
import DataAccessLayer.SupplierMapper;

import java.sql.SQLException;
import java.util.*;
import javafx.util.Pair;

public class Supplier {
    private  int id;
    private String name;
    private String phoneNum;
    private int bankAccount;
    private String payment;
    private String supplySchedule;
    private String supplyLocation;
    private List<Integer> items;
    private Agreement agreement;
    private SupplierMapper supplierMapper;


    public Supplier()
    {
        this.supplierMapper= new SupplierMapper();
    }



    public Supplier(int id, String name, String phoneNum, int bankAccount, String payment, String supplySchedule, String supplyLocation, String address) {
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

    public static boolean deleteSupplier(int id) {
        return SupplierMapper.deleteSupplier(id);
    }

    public static boolean validateItemId(int suppId, int itemId) {
       return SupplierMapper.validateItemId(suppId,itemId);
    }

    public static void addItemToSupplier(int suppId, int itemId) {
        SupplierMapper.addItemToSupplier(suppId,itemId);

    }

    public static int getItemsListSize(int suppId) {
        return SupplierMapper.getItemsListSize(suppId);
    }

    public static List<String> getSupplierItemsNames(int suppId) {
        return SupplierMapper.getSupplierItemsNames(suppId);
    }

    public static List<Integer> getSupplierItemsId(int suppId) {
        return SupplierMapper.getSupplierItemsId(suppId);
    }

    public boolean addItemToAgreement(Integer supp_id, Integer item_id, Double cost) {
        return this.supplierMapper.addItemToAgreement(supp_id,item_id,cost);
    }

    public int getId() { return this.id; }

    public Agreement getAgreement() { return this.agreement; }

   /* public boolean makeOrder(List<ItemInOrder> items) {
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
    }*/

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



    public int getItemIdByIndex(int i) {
        return this.items.get(i);
    }

    public void addItemsToSupplier(int itemId) {
            this.items.add(itemId);
    }

    public int getItemsListSize() {
        return this.items.size();
    }

    public void setItemPrice(int itemId, double newPrice) { getAgreement().setPrice(itemId, newPrice);}

    public boolean validateItemId(int itemId){
        if(this.items.contains(itemId)) return true;
        else return false;
    }

    public int getBillSize() { return this.agreement.getBillSize(); }

   /* public double getOrderCost(int itemId, int quantity) {
        return this.agreement.getOrderCost(itemId,quantity);
    }*/

    public Double getPriceOfAmountOfItem(Integer itemId, Integer amount) {
        if(agreement.checkIfItemExist(itemId))
            return this.agreement.getPriceOfAmountOfItem(itemId,amount);
        else return 100000001.0;
    }

    public Double getPriceOfAmountOfItemBeforeDiscount(int itemId , int amount) {
        return this.agreement.getPriceOfAmountOfItemBeforeDiscount(itemId,amount);
    }

    public Double getDiscountOfItem(int itemId , int amount) {
        return this.agreement.getDiscountOfItem(itemId , amount);
    }

    public List<Object> getSuppDetails() {
        List<Object> list = new LinkedList<>();
        list.add(id);
        list.add(name);
        list.add(phoneNum);
        list.add(supplyLocation);
        return list;
    }

    public boolean saveMe() {
        return SupplierMapper.addSupplier(new SupplierDTO(id, name, phoneNum, bankAccount, payment, supplySchedule, supplyLocation));
    }

    public int getSupplierSize() {
        return this.supplierMapper.getSupplierSize();
    }
}
