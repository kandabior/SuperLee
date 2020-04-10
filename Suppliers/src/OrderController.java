import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class OrderController {

    List<Order> orders;
    private static OrderController order_sp = null;

    private OrderController()
    {
        this.orders= new LinkedList<>();
    }

    public static OrderController getOrderController() {
        if (order_sp == null)
            order_sp = new OrderController();
        return order_sp;
    }

    public boolean addOrder(int id, List<Pair<Integer, Integer>> items, int supplierId) {
       Order order = new Order(id, items, supplierId);
       boolean result = order.makeOrder();
       if (result)
       {
           orders.add(order);
           return true;
       }
       else return false;
    }

    public int getOrdersSize() {
        return this.orders.size();
    }

    public List<Pair<Integer, Integer>> getItemsInOrderById(int i) {
        return this.orders.get(i).getItemsInOrderById();
    }

    public int getSupplierIdOfOrder(int i) {
        return this.orders.get(i).getSupplierIdOfOrder();
    }
}
