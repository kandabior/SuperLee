import javafx.util.Pair;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FacadeController {

    private static FacadeController fc_instance=null;
    SupplierController supplierController;
    OrderController orderController;

    private FacadeController() {
        this.supplierController = SupplierController.getSupplierController();
        this.orderController = OrderController.getOrderController();
    }

    public static FacadeController getFacadeController() {
        if (fc_instance == null) fc_instance = new FacadeController();
        return fc_instance;
    }

    public void addSupplier(int id, String name, String phoneNum, int bankAccount, String payment, String supplySchedule, String supplyLocation) {
        supplierController.addSupplier(id, name, phoneNum, bankAccount, payment, supplySchedule, supplyLocation);
    }

    public void addBillOfQuantities (int supplierId,Map<Integer, Pair<Integer, Double>> bill ) {
        supplierController.addBillOfQuantities(supplierId, bill);
    }

    public void insertBillOfQuantities(int supplierId,Integer itemId, Pair<Integer, Double> quantity_disc) {
        this.supplierController.insertBillOfQuantities(supplierId, itemId, quantity_disc);
    }

    public void updateBillOfQuantities(int supplierId,Integer itemId, Pair<Integer, Double> quantity_disc) {
        this.supplierController.updateBillOfQuantities(supplierId, itemId, quantity_disc);
    }

    public void deleteBillOfQuantities(int supplierId,Integer itemId) {
        this.supplierController.deleteBillOfQuantities(supplierId, itemId);
    }

    public boolean addOrder(int id, List<Pair<Integer, Integer>> items, int supplierId) {
        return orderController.addOrder(id, items, supplierId);
    }


}
