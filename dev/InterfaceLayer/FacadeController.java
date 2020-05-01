package InterfaceLayer;

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

    public void addSupplier(int id, String name, String phoneNum, int bankAccount, String payment, String supplySchedule, String supplyLocation) {
        supplierController.addSupplier(id, name, phoneNum, bankAccount, payment, supplySchedule, supplyLocation);
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

    public Map<Integer,Pair<Integer,Integer>> makeOrder(List<Pair<Integer,Integer>> list)//return itemId , <Quantity , FinalCost>
    {

        //list.get(i).getKey() - id
        //list.get(i).getValue() = quantity
        Map<Integer,Pair<Integer,Integer>> map = new HashMap();
        Map<Integer , List<Object>> orderMap= new HashMap<>();
        for( int i =0 ; i < list.size() ; i++)
        {
            int bestSuppForItem = supplierController.bestSuppForItem(list.get(i).getKey(),list.get(i).getValue());
            if(bestSuppForItem>0)
            {
                Double costForItem = supplierController.getPriceOfAmountOfItem(bestSuppForItem ,list.get(i).getKey(),list.get(i).getValue() );
                Pair<Integer,Integer> p = new Pair(list.get(i).getValue(),costForItem);
                map.put(list.get(i).getKey() , p);
                //add orders
                if(orderMap.containsKey(bestSuppForItem))
                {
                   // orderMap.put(bestSuppForItem,orderList);
                }
                else
                {
                    List<Object> orderList = new LinkedList<>();
                    orderList.add(list.get(i).getKey());// Item Id
                    orderList.add(supplierController.getItemName(list.get(i).getKey()));//Item Name
                    orderList.add(list.get(i).getValue());// Item quantity
                    orderList.add(list.get(i).getKey());// Item cost
                    orderList.add(list.get(i).getKey());// Item discount
                    orderList.add(list.get(i).getKey());// Item finalCost

                }


            }
        }
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

  /*  public double getTotalOrderMoney(int orderId) {
       return orderController.getTotalOrderMoney(orderId);
    }*/

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
}
