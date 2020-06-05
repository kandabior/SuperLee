package src.BusinessLayer.Suppliers;


import src.DataAccessLayer.Suppliers.DTO.OrderDTO;
import DataAccessLayer.Suppliers.DTO.OrderLineDTO;
import src.DataAccessLayer.Suppliers.OrderMapper;

import java.util.List;


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

    public boolean updateOrderStatus(int orderId) {
        return this.orderMapper.updateOrderStatus(orderId);
    }
}
