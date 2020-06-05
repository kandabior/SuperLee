package src.InterfaceLayer.Suppliers;

import javafx.util.Pair;

import java.util.*;

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

    public boolean addSupplier(int id, String name, String phoneNum, int bankAccount, String payment, String supplySchedule, String supplyLocation) {
        return supplierController.addSupplier(id, name, phoneNum, bankAccount, payment, supplySchedule, supplyLocation);
    }

    public boolean deleteSupplier(int suppId) { return supplierController.deleteSupplier(suppId); }

    public void createBillOfQuantities (int supplierId, Map<Integer, Pair<Integer, Double>> bill) {
        this.supplierController.createBillOfQuantities(supplierId, bill);
    }

    public void changeInBillOfQuantities(int supplierId, Integer itemId, Pair<Integer, Double> quantity_disc) {
        this.supplierController.changeInBillOfQuantities(supplierId, itemId, quantity_disc);
    }

    public void deleteFromBillOfQuantities(int supplierId,Integer itemId) {
        this.supplierController.deleteFromBillOfQuantities(supplierId, itemId);
    }

    public void deleteBillOfQuantities(int suppId) {
        this.supplierController.deleteBillOfQuantities(suppId);
    }

    public int getBillSize(int suppId) { return this.supplierController.getBillSize(suppId); }

    public LinkedHashMap<Integer, Double> showSuppItems(int suppId) {
        return supplierController.showSuppItems(suppId);
    }

    public Boolean checkBillOfQuantity(int suppId) {
        return this.supplierController.checkBillOfQuantity(suppId);
    }

    public void addItemToBillOfQuantities(int suppId, int itemId, int itemQuantity, Double itemDiscount) {
        this.supplierController.addItemToBillOfQuantities(suppId, itemId, itemQuantity, itemDiscount);
    }


    public Map<Integer,Pair<Integer,Double>> makeOrder(int branchId, List<Pair<Integer,Integer>> list , int day)//return itemId , <Quantity , FinalCost>
    {
        Map<Integer, Pair<Integer, Double>> map = new HashMap();
        Map<Integer, List<List<Object>>> orderMap = new HashMap<>();
        Map<Integer, List<Object>> suppliersMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            int itemId = list.get(i).getKey();
            int quantity = list.get(i).getValue();
            int bestSuppForItem = supplierController.bestSuppForItem(itemId, quantity);
            if (bestSuppForItem > 0) {
                Double costForItem = supplierController.getPriceOfAmountOfItem(bestSuppForItem, itemId, quantity);
                Pair<Integer, Double> p = new Pair(quantity, costForItem);
                int localItemId = getLocalItemId(itemId);
                map.put(localItemId, p);
                //add orders
                if (orderMap.containsKey(bestSuppForItem)) {
                    List<Object> orderList = new LinkedList<>();
                    orderList.add(localItemId);// Item Id
                    orderList.add(supplierController.getItemName(itemId));//Item Name
                    orderList.add(quantity);// Item quantity
                    Double firstCost = supplierController.getPriceOfAmountOfItemBeforeDiscount(bestSuppForItem, itemId, quantity);
                    orderList.add(firstCost);// Item cost
                    Double discount = supplierController.getDiscountOfItem(bestSuppForItem, itemId, quantity);
                    orderList.add(discount);// Item discount
                    orderList.add(costForItem);// Item finalCost
                    orderMap.get(bestSuppForItem).add(orderList);
                } else {
                    List<List<Object>> bigList = new LinkedList<>();
                    List<Object> orderList = new LinkedList<>();
                    orderList.add(localItemId);// Item Id
                    orderList.add(supplierController.getItemName(itemId));//Item Name
                    orderList.add(quantity);// Item quantity
                    Double firstCost = supplierController.getPriceOfAmountOfItemBeforeDiscount(bestSuppForItem, itemId, quantity);
                    orderList.add(firstCost);// Item cost
                    Double discount = supplierController.getDiscountOfItem(bestSuppForItem, itemId, quantity);
                    orderList.add(discount);// Item discount
                    orderList.add(costForItem);// Item finalCost
                    bigList.add(orderList);
                    orderMap.put(bestSuppForItem, bigList);

                    //add supplier details
                    List<Object> suppList = supplierController.getSuppDetails(bestSuppForItem);
                    suppliersMap.put(bestSuppForItem, suppList);
                }
            }
        }
        orderController.makeOrders(branchId, orderMap, suppliersMap, day);
        return map;
    }

    private int getLocalItemId(int itemId) {
      return  supplierController.getLocalItemId(itemId);
    }

    public int getOrdersSize() { return orderController.getOrdersSize(); }

    public void addItemToSupplier(int suppId, int itemId, int itemLocalId) {
        supplierController.addItemToSupplier(suppId, itemId,itemLocalId);
    }

    public int getItemsListSize(int suppId) { return supplierController.getItemsListSize(suppId); }

    public boolean addItemToAgreement(Integer suppId ,Integer itemId,Double price){ return supplierController.addItemToAgreement(suppId, itemId, price);}

    public boolean findSupplier(int id) { return supplierController.findSupplier(id); }

    public Map<Integer, Pair<Integer, Double>> getBillOfQuantities(int suppId) { return this.supplierController.getBillOfQuantities(suppId); }

    public String getItemNameById(Integer itemId) { return this.supplierController.getItemName(itemId); }

    public void setItemPrice(int suppId, int itemId, double newPrice) { this.supplierController.setItemPrice(suppId, itemId, newPrice);}

    public boolean validateItemId(int suppId, int itemId) { return supplierController.validateItemId(suppId, itemId); }

     public double getTotalOrderMoney(int orderId) {
       return orderController.getTotalOrderMoney(orderId);
    }

    public boolean checkIfOrderExists(int orderId) {
       return orderController.checkIfOrderExists(orderId);
    }

    public boolean updateOrderStatus(int orderId) {
        return orderController.updateOrderStatus(orderId);
    }

    public String getOrderStatus(int orderId) {
        return orderController.getOrderStatus(orderId);
    }

    public boolean checkIfItemExist(int itemId) {
       return this.supplierController.checkIfItemExist(itemId);
    }

    public List<List<Object>> getOrdersLineByOrderIndex(int id) {
         return orderController.getOrdersLineByOrderIndex(id);
    }

    public List<String> getSupplierItemsNames(int suppId) {
        return supplierController.getSupplierItemsNames(suppId);
    }

    public List<Integer> getSupplierItemsId(int suppId) {
        return supplierController.getSupplierItemsId(suppId);
    }

    public List<Object> getSupplierDetails(int id) {
        return orderController.getSupplierDetails(id);
    }

    public int getSuppliersCounter() {
        return this.supplierController.getSuppliersCounter();
    }

    public boolean validateItemIdInBill(int suppId, int itemId) {

        return supplierController.validateItemIdInBill(suppId, itemId);
    }

    public List<List<Object>> getAllSuppliers() {
        return this.supplierController.getAllSuppliers();
    }

    public boolean addSupplierDays(int suppId,int[] daysInt) {
        return supplierController.addSupplierDays(suppId, daysInt);
    }
}
