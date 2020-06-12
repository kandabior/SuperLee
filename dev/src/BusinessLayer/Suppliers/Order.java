package src.BusinessLayer.Suppliers;


import javafx.util.Pair;
import src.DataAccessLayer.Suppliers.DTO.OrderDTO;
import DataAccessLayer.Suppliers.DTO.OrderLineDTO;
import src.DataAccessLayer.Suppliers.OrderMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public class Order {

    private OrderMapper orderMapper;

    public Order()
    {
        this.orderMapper=new OrderMapper();
    }


    public String getStatus(int orderId) {
        return this.orderMapper.getStatus(orderId);
    }

    public double getTotalOrderMoney(int orderId) {
        return this.orderMapper.getTotalOrderMoney(orderId);

    }

    public int getSize() {
       return this.orderMapper.getSize();

    }

    public List<List<Object>> getOrdersLineByOrderID(int id) {
        return this.orderMapper.getOrdersLineByOrderID( id);
    }

    public List<Object> getSupplierDetails(int id) {
        return this.orderMapper.getSupplierDetails(id);
    }

    public boolean insertOrder(OrderDTO o) {
        if (this.orderMapper.insertOrder(o)) {
            for (OrderLineDTO ot : o.getOrderLines()) {
                this.orderMapper.insertOrderLines(ot);
            }
            return true;
        }
        return false;
    }

    public int getOrderIdCounter() {
        return this.orderMapper.getOrderIdCounter();
    }

    public boolean checkIfOrderExists(int orderId) {
        return this.orderMapper.checkIfOrderExists(orderId);
    }

    public boolean updateOrderStatus(int orderId,boolean status) {
        return this.orderMapper.updateOrderStatus(orderId,status);
    }

    public Map<Pair<Integer,Integer>, List<Object>> getOrdersByDat(LocalDate day) {
        return this.orderMapper.getOrdersByDat(day);
    }
}
