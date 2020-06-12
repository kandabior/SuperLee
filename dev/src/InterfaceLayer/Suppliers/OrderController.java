package src.InterfaceLayer.Suppliers;
import javafx.util.Pair;
import src.BusinessLayer.Suppliers.Order;
import src.BusinessLayer.TransportModule.Pool;
import src.DataAccessLayer.Suppliers.DTO.OrderDTO;
import DataAccessLayer.Suppliers.DTO.OrderLineDTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderController {

    static int orderIdCounter ;
    Map<Integer, OrderDTO> pendingOrders = new HashMap<>(); //supp id, order_dto

    private Order order ;
    private static OrderController order_sp = null;

    private OrderController() {
        this.order= new Order();
        orderIdCounter = this.order.getOrderIdCounter();

    }

    public static OrderController getOrderController() {
        if (order_sp == null)
            order_sp = new OrderController();
        return order_sp;
    }

    public List<Object> getSupplierDetails(int id) {
       return this.order.getSupplierDetails(id);
    }

    public int getOrdersSize() {
        return this.order.getSize();
    }

    public double getTotalOrderMoney(int orderId) {
        return this.order.getTotalOrderMoney(orderId);
    }

    public boolean checkIfOrderExists(int orderId) {
       return this.order.checkIfOrderExists(orderId);
    }

    public boolean updateOrderStatus(int orderId,boolean status) {
        return this.order.updateOrderStatus(orderId,status);
    }

    public String getOrderStatus(int orderId) {
        return this.order.getStatus(orderId);
    }

    public void makeOrders(int branchId, Map<Integer, List<List<Object>>> orderMap, Map<Integer, List<Object>> suppliersList, int day) {
        for (Integer key : orderMap.keySet())//go over suppliers
        {
            //String d =(String)orderMap.get(key).get(0).get(6);
            int orderId =(Integer) suppliersList.get(key).get(4);
            String suppName =(String) suppliersList.get(key).get(1);
            String PhoneNu=  (String) suppliersList.get(key).get(2);
            String Address=  (String) suppliersList.get(key).get(3);
            LocalDate date = (LocalDate)orderMap.get(key).get(0).get(7);
            String status = (String)orderMap.get(key).get(0).get(6);

            OrderDTO o = new OrderDTO(branchId, (Integer) suppliersList.get(key).get(4), (Integer) suppliersList.get(key).get(0), (String) suppliersList.get(key).get(1), (String) suppliersList.get(key).get(2), (String) suppliersList.get(key).get(3), date, (String)orderMap.get(key).get(0).get(6) );
            List<OrderLineDTO> lines = new LinkedList<>();
            for (int j = 0; j < orderMap.get(key).size(); j++)//go over the orders line
            {
                int itemId = (int) orderMap.get(key).get(j).get(0);
                String itemName = (String) orderMap.get(key).get(j).get(1);
                int itemQuantity = (int) orderMap.get(key).get(j).get(2);
                Double itemCost = (Double) orderMap.get(key).get(j).get(3);
                Double itemDiscount = (Double) orderMap.get(key).get(j).get(4);
                Double itemFinalCost = (Double) orderMap.get(key).get(j).get(5);
                OrderLineDTO orderLine = new OrderLineDTO(o.getId(), itemId, itemName, itemQuantity, itemCost, itemDiscount, itemFinalCost);
                lines.add(orderLine);
            }
            o.setItems(lines);
            o.setTotalCost(getTotalCost(lines));
            this.order.insertOrder(o);
        }
    }

    private double getTotalCost(List<OrderLineDTO> lines) {
        double ans = 0.0;
        for(OrderLineDTO ot : lines)
        {
            ans+=ot.getFinalCost();
        }
        return ans;
    }


    public List<List<Object>> getOrdersLineByOrderIndex(int id) {
        return this.order.getOrdersLineByOrderID(id);
    }

    public int getOrderIdCounter() {
        return this.order.getOrderIdCounter();
    }


    public Map<Pair<Integer,Integer>, List<Object>> getOrdersByDat(LocalDate day) {
        return this.order.getOrdersByDat(day);
    }

//    public void PromoteDay(int day) {
//        List<List<Object>> orders = new LinkedList<>();
//        Map<Integer, List<List<Object>>> orderMap = new HashMap<>();
//        Map<Integer, List<Object>> suppliersMap = new HashMap<>();
//        for(Integer suppID : pendingOrders.keySet()) {
//            for(Integer o_day : pendingOrders.get(suppID).getOrderDays()) {
//                if(o_day == day) {
//                    for(OrderLineDTO ol : pendingOrders.get(suppID).getOrderLines()) {
//                        List<Object> order = new LinkedList<>();
//                        order.add(suppID);
//                        order.add(pendingOrders.get(suppID).getBranchId());
//                        order.add(ol.getItemId());
//                        order.add(ol.getItemQuantity());
//                        order.add(pendingOrders.get(suppID).getId());
//                        order.add(null);
//                        List<List<Object>> out = new LinkedList<>();
//                        out.add(order);
//                        orderMap.put(pendingOrders.get(suppID).getSuppId(), out);
//                    }
//                    makeOrders(pendingOrders.get(suppID).getBranchId(), orderMap, suppliersMap, day);
//                    pendingOrders.remove(suppID);
//                }
//            }
//        }
//        Pool.getInstance().makeOrders(1,orders);//todo change all the unction!
//    }
}

