package LogicLayer;

import DTO.SupplierDTO;
import DataAccessLayer.SupplierMapper;

import java.sql.SQLException;
import java.text.DecimalFormat;
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
    private SupplierMapper supplierMapper = new SupplierMapper();


    public Supplier() {
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

    public boolean validateItemId(int suppId, int itemId) {
       return this.supplierMapper.validateItemId(suppId,itemId);
    }

    public void addItemToSupplier(int suppId, int itemId) {
        this.supplierMapper.addItemToSupplier(suppId,itemId);
    }

    public int getItemsListSize(int suppId) {
        return this.supplierMapper.getItemsListSize(suppId);
    }

    public List<Integer> getSupplierItemsId(int suppId) {
        return this.supplierMapper.getSupplierItemsId(suppId);
    }

    public boolean addItemToAgreement(Integer supp_id, Integer item_id, Double cost) {
        return this.supplierMapper.addItemToAgreement(supp_id,item_id,cost);
    }

    public int getId() { return this.id; }

    public Boolean checkBillOfQuantity(int agreementId) {
        return this.supplierMapper.checkIfBillExists(agreementId);
    }


    public int getItemIdByIndex(int i) {
        return this.items.get(i);
    }


    public void setItemPrice(int suppId, int itemId, double newPrice) { this.supplierMapper.setItemPrice(suppId, itemId, newPrice); }


    public Double getPriceOfAmountOfItem(int supplierId, Integer itemId, Integer amount) {
        if (this.supplierMapper.getSupplierItemsId(supplierId).contains(itemId)) {
            if (this.supplierMapper.checkIfBillExists(supplierId) && this.supplierMapper.checkInBillForDiscount(supplierId, itemId, amount)) {
                double discount = this.supplierMapper.getDiscount(supplierId, itemId);
                double cost = this.supplierMapper.getPriceOfItem(supplierId, itemId);// terms.get(itemId);
                double costMulQuantity = (cost * amount);
                double x = costMulQuantity * (1 - discount);
                DecimalFormat df = new DecimalFormat("#.##");
                String dx = df.format(x);
                x = Double.valueOf(dx);
                return x;
            } else {
                double cost = this.supplierMapper.getPriceOfItem(supplierId, itemId);
                return (cost * amount);
            }
        } else return 100000001.0;
    }


    public Double getPriceOfAmountOfItemBeforeDiscount(int suppId , int itemId , int amount) {
        Double cost = this.supplierMapper.getPriceOfItem(suppId,itemId);
        return cost*amount;
    }

    public List<Object> getSuppDetails(int bestSuppId) {
        return this.supplierMapper.getSuppDetails(bestSuppId);
    }

    public boolean saveMe() {
        return this.supplierMapper.addSupplier(new SupplierDTO(id, name, phoneNum, bankAccount, payment, supplySchedule, supplyLocation));
    }

    public int getSupplierSize() {
        return this.supplierMapper.getSupplierSize();
    }

    public LinkedHashMap<Integer, Double> showSuppItems(int suppId) {
        return this.supplierMapper.showSuppItems(suppId);
    }

    public List<Integer> getSupplierIds() {
        return this.supplierMapper.getSupplierIds();
    }

    public Double getDiscount(int billId, int itemId, int amount) {
        return this.supplierMapper.getDiscount(billId,itemId);
    }

    public int getSuppliersCounter() {
        return this.supplierMapper.getSuppliersCounter();
    }

    public void createBillOfQuantities(int suppId) {
        this.supplierMapper.createBillOfQuantities(suppId);
    }

    public boolean validateItemIdInBill(int suppId, int itemId) {
        return this.supplierMapper.validateItemIdInBill(suppId, itemId);

    }

    public List<List<Object>> getAllSuppliers() {
        return this.supplierMapper.getAllSuppliers();
    }
}
