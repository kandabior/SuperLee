package src.InterfaceLayer.Suppliers;

import javafx.util.Pair;
import src.BusinessLayer.Inventory.Inventory;
import src.BusinessLayer.TransportModule.Pool;
import src.InterfaceLayer.Inventory.InventoryController;
import src.PresentationLayer.Main;

import java.time.LocalDate;
import java.util.*;

public class FacadeController {

    private static FacadeController fc_instance=null;
    SupplierController supplierController;
    OrderController orderController;
    static int orderIdCounter ;

    private FacadeController() {
        this.supplierController = SupplierController.getSupplierController();
        this.orderController = OrderController.getOrderController();
        orderIdCounter = this.orderController.getOrderIdCounter();
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


    public Map<Pair<Integer,Integer>,Pair<Integer,Double>> makeOrder(int branchId, List<Pair<Integer,Integer>> list , int day) //return <itemId,local Item Id> , <Quantity , FinalCost>
    {
        Map<Pair<Integer,Integer>, Pair<Integer, Double>> map = new HashMap();
        Map<Integer, List<List<Object>>> orderMap = new HashMap<>();
        Map<Integer, List<Object>> suppliersMap = new HashMap<>();
        List<List<Object>> transportOrders = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            int itemId = list.get(i).getKey();
            int quantity = list.get(i).getValue();
            int bestSuppForItem = supplierController.bestSuppForItem(itemId, quantity);
            if (bestSuppForItem > 0) {
                String type = supplierController.getSupplierType(bestSuppForItem);
                switch (type){
                    case "A": { //fixed day , supply solo
                        Double costForItem = supplierController.getPriceOfAmountOfItem(bestSuppForItem, itemId, quantity);
                        Pair<Integer, Double> p = new Pair(quantity, costForItem);
                        int localItemId = getLocalItemId(itemId,bestSuppForItem);
                        //add orders
                        if (orderMap.containsKey(bestSuppForItem)) {
                            orderMap.get(bestSuppForItem).add(addToOrder(localItemId,itemId,quantity,bestSuppForItem,costForItem,"Waiting"));
                            //pendingOrders.get(bestSuppForItem).add(addToOrder(localItemId,itemId,quantity,bestSuppForItem,costForItem));
                        } else {
                            List<Object> suppList = supplierController.getSuppDetails(bestSuppForItem);
                            suppList.add(orderIdCounter);
                            suppList.add(supplierController.getSupplyDays(bestSuppForItem));
                                int day1 =getBestDay((List<Integer>)suppList.get(5),getCurrentDay(LocalDate.now().plusDays(Main.plusDay)));
                                       // ((List<Integer>)suppList.get(5)).get(0)-getCurrentDay(LocalDate.now().plusDays(Main.plusDay));
                                LocalDate date;
                                if(day1>=0)
                                   date =  LocalDate.now().plusDays(Main.plusDay+day1);
                                else
                                    date =LocalDate.now().plusDays(Main.plusDay+(7+day1));
                            orderMap.put(bestSuppForItem, addNewSuppOrder(localItemId, itemId, quantity, bestSuppForItem, costForItem,"Waiting",date));
                            //pendingOrders.put(bestSuppForItem, addNewSuppOrder(localItemId, itemId, quantity, bestSuppForItem, costForItem));
                            //add supplier details

                            orderIdCounter++;
                            suppliersMap.put(bestSuppForItem, suppList);
                        }
                    } break;
                    case "B": {//fixed days, supply with transport
                        boolean found = false;
                        int currentOrder =-1;
                        for(List<Object> o : transportOrders) {
                            if ((int) o.get(0) == bestSuppForItem) {
                                found = true;
                                currentOrder = (int) o.get(4);
                            }
                        }
                        List<Object> order = new LinkedList<>();
                        Double costForItem = supplierController.getPriceOfAmountOfItem(bestSuppForItem, itemId, quantity);
                        int localItemId = getLocalItemId(itemId,bestSuppForItem);
                        if(!found) {
                            order.add(orderIdCounter);
                            order.add(bestSuppForItem);
                            orderIdCounter++;
                            order.add(supplierController.getSupplyDays(bestSuppForItem));//no date);
                            Map<Pair<Integer,Integer>,Pair<Integer,Double>> tempMap = new HashMap();
                            Pair<Integer,Integer> p1  = new Pair(itemId,getLocalItemId(itemId,bestSuppForItem));
                            Pair<Integer,Double> p2 = new Pair(quantity,costForItem);
                            tempMap.put(p1,p2);
                            order.add(tempMap);
                        }
                        else {
                            for(List<Object> o : transportOrders) {
                                if ((int) o.get(0) == bestSuppForItem) {
                                    Map<Pair<Integer,Integer>,Pair<Integer,Double>> ot = (Map<Pair<Integer,Integer>,Pair<Integer,Double>>)o.get(3);
                                    Pair<Integer,Integer> p1  = new Pair(itemId,getLocalItemId(itemId,bestSuppForItem));
                                    Pair<Integer,Double> p2 = new Pair(quantity,costForItem);
                                    ot.put(p1,p2);
                                    o.remove(3);
                                    o.add(ot);
                                }
                            }
                            order.add(currentOrder);
                        }
                        transportOrders.add(order);

                        //add orders
                        if (orderMap.containsKey(bestSuppForItem)) {
                            orderMap.get(bestSuppForItem).add(addToOrder(localItemId,itemId,quantity,bestSuppForItem,costForItem,"Pending"));
                        } else {
                            orderMap.put(bestSuppForItem, addNewSuppOrder(localItemId, itemId, quantity, bestSuppForItem, costForItem,"Pending",LocalDate.now().plusDays(Main.plusDay)));
                            //add supplier details
                            List<Object> suppList = supplierController.getSuppDetails(bestSuppForItem);
                            suppList.add(orderIdCounter-1);
                            suppliersMap.put(bestSuppForItem, suppList );
                        }
                    } break;
                    case "C": { //when needed , supply alone
                        Double costForItem = supplierController.getPriceOfAmountOfItem(bestSuppForItem, itemId, quantity);
                        Pair<Integer, Double> p = new Pair(quantity, costForItem);
                        int localItemId = getLocalItemId(itemId,bestSuppForItem);
                        Pair<Integer,Integer> pair = new Pair<>(itemId,localItemId);
                        map.put(pair, p);
                        //add orders
                        if (orderMap.containsKey(bestSuppForItem)) {
                            orderMap.get(bestSuppForItem).add(addToOrder(localItemId,itemId,quantity,bestSuppForItem,costForItem,"Pending"));
                        } else {
                            orderMap.put(bestSuppForItem, addNewSuppOrder(localItemId, itemId, quantity, bestSuppForItem, costForItem,"Pending",LocalDate.now().plusDays(Main.plusDay)));
                            //add supplier details
                            List<Object> suppList = supplierController.getSuppDetails(bestSuppForItem);
                            suppList.add(orderIdCounter);
                            suppliersMap.put(bestSuppForItem, suppList );
                            orderIdCounter++;
                        }
                    } break;
                    case "D": {//when needed , supply  with transport
                        boolean found = false;
                        int currentOrder =-1;
                        for(List<Object> o : transportOrders) {
                            if ((int) o.get(0) == bestSuppForItem) {
                                found = true;
                                currentOrder = (int) o.get(4);
                            }
                        }
                        List<Object> order = new LinkedList<>();
                        Double costForItem = supplierController.getPriceOfAmountOfItem(bestSuppForItem, itemId, quantity);
                        int localItemId = getLocalItemId(itemId,bestSuppForItem);
                        if(!found) {
                            order.add(orderIdCounter);
                            order.add(bestSuppForItem);
                            orderIdCounter++;
                            order.add(null);
                            Map<Pair<Integer,Integer>,Pair<Integer,Double>> tempMap = new HashMap();
                            Pair<Integer,Integer> p1  = new Pair(itemId,getLocalItemId(itemId,bestSuppForItem));
                            Pair<Integer,Double> p2 = new Pair(quantity,costForItem);
                            tempMap.put(p1,p2);
                            order.add(tempMap);
                        }
                        else {
                            for(List<Object> o : transportOrders) {
                                if ((int) o.get(0) == bestSuppForItem) {
                                    Map<Pair<Integer,Integer>,Pair<Integer,Double>> ot = (Map<Pair<Integer,Integer>,Pair<Integer,Double>>)o.get(3);
                                    Pair<Integer,Integer> p1  = new Pair(itemId,getLocalItemId(itemId,bestSuppForItem));
                                    Pair<Integer,Double> p2 = new Pair(quantity,costForItem);
                                    ot.put(p1,p2);
                                     o.remove(3);
                                     o.add(ot);
                                }
                            }
                            order.add(currentOrder);
                        }
                        transportOrders.add(order);
                        //add orders
                        if (orderMap.containsKey(bestSuppForItem)) {
                            orderMap.get(bestSuppForItem).add(addToOrder(localItemId,itemId,quantity,bestSuppForItem,costForItem,"Pending"));
                        } else {
                            orderMap.put(bestSuppForItem, addNewSuppOrder(localItemId, itemId, quantity, bestSuppForItem, costForItem,"Pending",LocalDate.now().plusDays(Main.plusDay)));
                            //add supplier details
                            List<Object> suppList = supplierController.getSuppDetails(bestSuppForItem);
                            suppList.add(orderIdCounter-1);
                            suppliersMap.put(bestSuppForItem, suppList );
                        }
                    } break;
                }
            }
        }
        //orderController.addToPendingOrders(branchId, pendingOrders, suppliersMap, day);
        orderController.makeOrders(branchId, orderMap, suppliersMap, day);
        try {
            Pool.getInstance().makeOrders(branchId, transportOrders);
        }
        catch (Exception e){
            System.out.println(e);

        }
             return map;
    }

    private int getBestDay(List<Integer> integers, Integer currentDay) {
        int min =8;
        for(int i=0;i<integers.size();i++)
        {
         if(Math.abs(currentDay-integers.get(i))<min)
             min =integers.get(i);
        }
        return min;
    }


    private int getLocalItemId(int itemId,int suppId) {
      return  supplierController.getLocalItemId(itemId,suppId);
    }

    private List<Object> addToOrder(int localItemId,int itemId,int quantity,int bestSuppForItem,double costForItem,String status)
    {
        List<Object> orderList = new LinkedList<>();
        orderList.add(localItemId);// Item Id
        orderList.add(supplierController.getItemName(itemId));//Item Name
        orderList.add(quantity);// Item quantity
        Double firstCost = supplierController.getPriceOfAmountOfItemBeforeDiscount(bestSuppForItem, itemId, quantity);
        orderList.add(firstCost);// Item cost
        Double discount = supplierController.getDiscountOfItem(bestSuppForItem, itemId, quantity);
        orderList.add(discount);// Item discount
        orderList.add(costForItem);// Item finalCost
        orderList.add(status);// Order Status
        return orderList;
    }

    private List<List<Object>> addNewSuppOrder(int localItemId,int itemId,int quantity,int bestSuppForItem,double costForItem,String status , LocalDate date) {
        List<List<Object>> outOrders = new LinkedList<>();
        List<Object> orderList = new LinkedList<>();
        orderList.add(localItemId);// Item Id
        orderList.add(supplierController.getItemName(itemId));//Item Name
        orderList.add(quantity);// Item quantity
        Double firstCost = supplierController.getPriceOfAmountOfItemBeforeDiscount(bestSuppForItem, itemId, quantity);
        orderList.add(firstCost);// Item cost
        Double discount = supplierController.getDiscountOfItem(bestSuppForItem, itemId, quantity);
        orderList.add(discount);// Item discount
        orderList.add(costForItem);// Item finalCost
        orderList.add(status);// Order Status
        orderList.add(date);//orderDate
        outOrders.add(orderList);
        return outOrders;
    }


    private Integer getCurrentDay(String day) {

        switch (day) {
            case "SUNDAY":
                return 1;
            case "MONDAY":
                return 2;
            case "TUESDAY":
                return 3;
            case "WEDNESDAY":
                return 4;
            case "THURSDAY":
                return 5;
            case "FRIDAY":
                return 6;
            case "SATURDAY":
                return 0;
        }
        return 1;
    }

    private Integer getCurrentDay(LocalDate date) {
        String day = date.getDayOfWeek().toString();
        switch (day) {
            case "SUNDAY":
                return 1;
            case "MONDAY":
                return 2;
            case "TUESDAY":
                return 3;
            case "WEDNESDAY":
                return 4;
            case "THURSDAY":
                return 5;
            case "FRIDAY":
                return 6;
            case "SATURDAY":
                return 0;
        }
        return 1;
    }
    public int getOrdersSize() { return orderController.getOrdersSize(); }

    public boolean addItemToSupplier(int suppId, int itemId, int itemLocalId) {
       return supplierController.addItemToSupplier(suppId, itemId,itemLocalId);
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

    public boolean updateOrderStatus(int orderId,boolean status) {
        return orderController.updateOrderStatus(orderId,status);
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

    public void PromoteDay(LocalDate day) {
        Map<Pair<Integer,Integer>, List<Object>> map = new HashMap();//list[0]= quantity, list[1]=cost, list[2]=branchid
        map=this.orderController.getOrdersByDat(day);
        InventoryController.getInventoryController().getProducts(map);
    }

    public List<Integer> getLocalItemsIds(int suppId,List<Integer> temp) {
        return supplierController.getLocalItemsIds(suppId,temp);
    }
}
