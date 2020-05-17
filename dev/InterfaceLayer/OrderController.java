package InterfaceLayer;
import DTO.OrderDTO;
import DTO.OrderLineDTO;
import LogicLayer.Order;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderController {

    static int orderIdCounter ;


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

    public boolean updateOrderStatus(int orderId) {
        return this.order.updateOrderStatus(orderId);
    }

    public String getOrderStatus(int orderId) {
        return this.order.getStatus(orderId);
    }

    public void makeOrders(int branchId, Map<Integer, List<List<Object>>> orderMap , Map<Integer , List<Object>> suppliersList, int day) {
        for (Integer key : orderMap.keySet())//go over suppliers
        {


            OrderDTO o = new OrderDTO(branchId, orderIdCounter, (Integer) suppliersList.get(key).get(0), (String) suppliersList.get(key).get(1), (String) suppliersList.get(key).get(2), (String) suppliersList.get(key).get(3), LocalDate.now().plusDays(day));
            orderIdCounter++;
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
        //return this.orders.get(index).getOrdersLineByOrderIndex();

    }
}
