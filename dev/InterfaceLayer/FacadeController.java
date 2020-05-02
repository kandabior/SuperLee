package InterfaceLayer;

import javafx.util.Pair;

import java.time.LocalDate;
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

    public void addSupplier(int id, String name, String phoneNum, int bankAccount, String payment, String supplySchedule, String supplyLocation , String address) {
        supplierController.addSupplier(id, name, phoneNum, bankAccount, payment, supplySchedule, supplyLocation , address);
    }

    public boolean deleteSupplier(int suppId) { return supplierController.deleteSupplier(suppId); }

    public void addBillOfQuantities (int supplierId, Map<Integer, Pair<Integer, Double>> bill ) {
        supplierController.addBillOfQuantities(supplierId, bill);
    }

    public void updateBillOfQuantities(int supplierId, Integer itemId, Pair<Integer, Double> quantity_disc) {
        this.supplierController.updateBillOfQuantities(supplierId, itemId, quantity_disc);
    }

    public void deleteFromBillOfQuantities(int supplierId,Integer itemId) {
        this.supplierController.deleteFromBillOfQuantities(supplierId, itemId);
    }

    public void deleteBillOfQuantities(int suppId) {
        this.supplierController.deleteBillOfQuantities(suppId);
    }

    public int getBillSize(int suppId) { return supplierController.getBillSize(suppId); }

  /*  public boolean addOrder(int id, List<Pair<Integer, Integer>> items, int supplierId) {
        return orderController.addOrder(id, items, supplierId);
    }*/

    public LinkedHashMap<Integer, Double> showSuppItems(int suppId) {
        return supplierController.showSuppItems(suppId);
    }

    public Boolean checkBillOfQuantity(int suppId) {
        return supplierController.checkBillOfQuantity(suppId);
    }

    public void addItemToBillOfQuantities(int suppId, int itemId, int itemQuantity, Double itemDiscount) {
        supplierController.addItemToBillOfQuantities(suppId, itemId, itemQuantity, itemDiscount);
    }


    public String getItemNameByIndex(int suppId, int index) {
        return supplierController.getItemNameByIndex(suppId, index);
    }

    public Map<Integer,Pair<Integer,Integer>> makeOrder(int branchId, List<Pair<Integer,Integer>> list)//return itemId , <Quantity , FinalCost>
    {

        Map<Integer,Pair<Integer,Integer>> map = new HashMap();
        Map<Integer , List<List<Object>>> orderMap= new HashMap<>();
        Map<Integer , List<Object>> suppliersMap= new HashMap<>();
        List<Object> suppliersList = new LinkedList<>();
        for( int i =0 ; i < list.size() ; i++)
        {
            int itemId = list.get(i).getKey();
            int quantity = list.get(i).getValue();
            int bestSuppForItem = supplierController.bestSuppForItem(itemId,quantity);
            if(bestSuppForItem>0)
            {
                Double costForItem = supplierController.getPriceOfAmountOfItem(bestSuppForItem ,itemId,quantity );
                Pair<Integer,Integer> p = new Pair(quantity,costForItem);
                map.put(itemId , p);
                //add orders
                if(orderMap.containsKey(bestSuppForItem))
                {
                    List<Object> orderList = new LinkedList<>();
                    orderList.add(itemId);// Item Id
                    orderList.add(supplierController.getItemName(itemId));//Item Name
                    orderList.add(quantity);// Item quantity
                    Double firstCost = supplierController.getPriceOfAmountOfItemBeforeDiscount(bestSuppForItem, itemId , quantity);
                    orderList.add(firstCost);// Item cost
                    Double discount = supplierController.getDiscountOfItem(bestSuppForItem,itemId,quantity);
                    orderList.add(discount);// Item discount
                    orderList.add(costForItem);// Item finalCost
                    orderMap.get(bestSuppForItem).add(orderList);

                }
                else
                {
                    List<List<Object>> bigList = new LinkedList<>();
                    List<Object> orderList = new LinkedList<>();
                    orderList.add(itemId);// Item Id
                    orderList.add(supplierController.getItemName(itemId));//Item Name
                    orderList.add(quantity);// Item quantity
                    Double firstCost = supplierController.getPriceOfAmountOfItemBeforeDiscount(bestSuppForItem, itemId , quantity);
                    orderList.add(firstCost);// Item cost
                    Double discount = supplierController.getDiscountOfItem(bestSuppForItem,itemId,quantity);
                    orderList.add(discount);// Item discount
                    orderList.add(costForItem);// Item finalCost
                    bigList.add(orderList);
                    orderMap.put(bestSuppForItem,bigList);

                    //add supplier Details
                    List<Object> suppList = supplierController.getSuppDetails(bestSuppForItem);
                    suppliersMap.get(bestSuppForItem).add(suppList);

                }

            }
        }
        orderController.makeOrders(branchId, orderMap , suppliersMap);

        return map;
    }



    public int getItemIdByIndex(int suppId, int index) { return supplierController.getItemIdByIndex(suppId, index); }

    public double getPriceOfItem(int suppId, int index) { return supplierController.getPriceOfItem(suppId, index); }

    public int getOrdersSize() { return orderController.getOrdersSize(); }

/*    public List<Pair<Integer, Integer>> getItemsInOrderById(int id) { return orderController.getItemsInOrderById(id); }

    public int getSupplierIdOfOrder(int i) { return orderController.getSupplierIdOfOrder(i); }*/

    public void addItemToSupplier(int suppId, int itemId) {
        supplierController.addItemToSupplier(suppId, itemId);
    }

    public int getItemsListSize(int suppId) { return supplierController.getItemsListSize(suppId); }

    public void addItemToAgreement(Integer suppId ,Integer itemId,Double price){ supplierController.addItemToAgreement(suppId, itemId, price);}

    public boolean findSupplier(int id) { return supplierController.findSupplier(id); }

    public Map<Integer, Pair<Integer, Double>> getBillOfQuantities(int suppId) { return supplierController.getBillOfQuantities(suppId); }

    public String getItemNameById(Integer itemId) { return supplierController.getItemName(itemId); }

    public void setItemPrice(int suppId, int itemId, double newPrice) { supplierController.setItemPrice(suppId, itemId, newPrice);}

    public boolean validateItemId(int suppId, int itemId) { return supplierController.validateItemId(suppId, itemId); }

     public double getTotalOrderMoney(int orderId) {
       return orderController.getTotalOrderMoney(orderId);
    }

    public void setOrderCost(int orderIdCounter, double totalMoney) {
        this.orderController.setOrderCost(orderIdCounter,totalMoney);
    }

    public int getOrderIdByIndex(int i) {
       return orderController.getOrderIdByIndex(i);
    }


    public boolean checkIfOrderExists(int orderId) {
       return orderController.checkIfOrderExists(orderId);
    }

    public void updateOrderStatus(int orderId) {
        orderController.updateOrderStatus(orderId);
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

    public int getSupplierIdOfOrderByIndex(int index) {
        return orderController.getSupplierIdOfOrderByIndex(index);
    }

    public String getSupplierNameOfOrderByIndex(int index) {
        return orderController.getSupplierNameOfOrderByIndex(index);
    }

    public String getSupplierAddOfOrderByIndex(int index) {
        return orderController.getSupplierAddOfOrderByIndex(index);
    }

    public int getOrderIdbyIndex(int i) {
        return orderController.getOrderIdByIndex(i);
    }

    public LocalDate getOrderDatebyIndex(int i) {
        return orderController.getOrderDatebyIndex(i);
    }

    public String getSupplierPhoneOfOrderByIndex(int i) {
        return orderController.getSupplierPhoneOfOrderByIndex(i);
    }
}
