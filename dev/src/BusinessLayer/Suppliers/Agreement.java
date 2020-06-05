package src.BusinessLayer.Suppliers;

import src.DataAccessLayer.Suppliers.SupplierMapper;
import src.DataAccessLayer.Suppliers.AgreementMapper;
import javafx.util.Pair;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class Agreement {

    private LinkedHashMap<Integer, Double> terms; //item id, price
    private BillOfQuantities bill;
    private AgreementMapper agreementMapper = new AgreementMapper();

    public Agreement() {
        this.terms = new LinkedHashMap<>();
        this.bill = null;
    }

    public void changeInBillOfQuantities(int suppId, Integer itemId, Pair<Integer, Double> quantity_disc) {
        this.agreementMapper.changeInBillOfQuantities(suppId, itemId, quantity_disc);
    }

    public void deleteFromBillOfQuantities(int suppId, Integer itemId) {
        this.agreementMapper.deleteFromBillOfQuantities(suppId, itemId);
    }
    public void deleteBillOfQuantities(int suppId) {
        this.agreementMapper.deleteBillOfQuantities(suppId);
    }

    public void addItemToBillOfQuantities(int supplierId, int itemId, int itemQuantity, Double itemDiscount) {
        this.agreementMapper.addItemToBillOfQuantities(supplierId, itemId, itemQuantity, itemDiscount);
    }

    public Map<Integer, Pair<Integer, Double>> getBillOfQuantities(int suppId) {
        return this.agreementMapper.getBillOfQuantities(suppId);
    }

    public int getBillSize(int suppId) {
        return this.agreementMapper.getBillSize(suppId);
    }

}
