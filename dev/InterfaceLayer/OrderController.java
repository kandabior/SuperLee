package InterfaceLayer;

import DTO.OrderDTO;
import DTO.OrderLineDTO;
import LogicLayer.Order;
import LogicLayer.OrderLine;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderController {

    static int orderIdCounter = 1;
    private Order order ;
    List<Order> orders;
    private static OrderController order_sp = null;

    private OrderController() {

        this.orders = new LinkedList<>();
        this.order= new Order();
    }

    public static OrderController getOrderController() {
        if (order_sp == null)
            order_sp = new OrderController();
        return order_sp;
    }

    public List<Object> getSupplierDeatails(int id) {
       return this.order.getSupplierDeatails(id);
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
        return this.order.getSize();
        //return this.orders.size();
    }

   /* public List<Pair<Integer, Integer>> getItemsInOrderById(int id) {
        return this.orders.get(id).getItemsInOrderById();
    }*/

/*    public int getSupplierIdOfOrder(int i) {
        return this.orders.get(i).getSupplierIdOfOrder();
    }
*/

    public double getTotalOrderMoney(int orderId) {
        return this.order.getTotalOrderMoney(orderId);


/*


        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == orderId) {
                return orders.get(i).getTotalOrderMoney();
            }
        }
        return -1;*/
    }

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
        return this.order.getStatus(orderId);

//        for (int i = 0; i < orders.size(); i++) {
//            if (orders.get(i).getId() == orderId) {
//                return orders.get(i).getStatus();
//            }
//        }

//        return "";
    }

    public void makeOrders(int branchId, Map<Integer, List<List<Object>>> orderMap , Map<Integer , List<Object>> suppliersList) {
        for (Integer key : orderMap.keySet())//go over suppliers
        {
            
            OrderDTO o = new OrderDTO(branchId, orderIdCounter, (Integer) suppliersList.get(key).get(0), (String) suppliersList.get(key).get(1), (String) suppliersList.get(key).get(2), (String) suppliersList.get(key).get(3), LocalDate.now());
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
            this.order.insertOrder(o);
           // orders.add(o);
        }
    }

    public List<List<Object>> getOrdersLineByOrderIndex(int id) {
        return this.order.getOrdersLineByOrderID(id);
        //return this.orders.get(index).getOrdersLineByOrderIndex();

    }

    public int getSupplierIdOfOrderByIndex(int index) {
         return orders.get(index).getSupplierId();
    }

    public String getSupplierNameOfOrderByIndex(int index) {
        return orders.get(index).getSupplierName();
    }

    public String getSupplierAddOfOrderByIndex(int index) {
        return orders.get(index).getSupplierAdd();
    }

    public LocalDate getOrderDateByIndex(int index) {
        return orders.get(index).getOrderDate();
    }

    public String getSupplierPhoneOfOrderByIndex(int index) {
        return orders.get(index).getSupplierPhone();
    }

}
