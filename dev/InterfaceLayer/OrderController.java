package InterfaceLayer;

import LogicLayer.Order;

import java.util.LinkedList;
import java.util.List;

public class OrderController {

    List<Order> orders;
    private static OrderController order_sp = null;

    private OrderController() {
        this.orders = new LinkedList<>();
    }

    public static OrderController getOrderController() {
        if (order_sp == null)
            order_sp = new OrderController();
        return order_sp;
    }

 /*   public boolean addOrder(int id, List<Pair<Integer, Integer>> items, int supplierId) {
        Order order = new Order(id, items, supplierId);
        boolean result = order.makeOrder();
        if (result) {
            orders.add(order);
            return true;
        }
        return false;
    }*/

    public int getOrdersSize() {
        return this.orders.size();
    }

   /* public List<Pair<Integer, Integer>> getItemsInOrderById(int id) {
        return this.orders.get(id).getItemsInOrderById();
    }*/

/*    public int getSupplierIdOfOrder(int i) {
        return this.orders.get(i).getSupplierIdOfOrder();
    }

    public double getTotalOrderMoney(int orderId) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == orderId) {
                return orders.get(i).getTotalOrderMoney();
            }
        }
        return -1;

    }*/

    public void setOrderCost(int orderId, double totalMoney) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == orderId) {
                orders.get(i).setTotalCost(totalMoney);
            }

        }
    }
    public int getOrderIdByIndex(int i) {
        return this.orders.get(i).getId();
    }

    public boolean checkIfOrderExists(int orderId) {
        for (int i =0 ;i<orders.size();i++)
        {
            if (orders.get(i).getId()==orderId)
                return true;
        }
        return false;
    }

    public void updateOrderStatus(int orderId) {
        for (int i =0;i<orders.size();i++)
        {
            if(orders.get(i).getId()==orderId)
            {
                orders.get(i).setStatus();
                break;
            }
        }
    }

    public String getOrderStatus(int orderId) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == orderId) {
                return orders.get(i).getStatus();
            }
        }
        return "";
    }
}
